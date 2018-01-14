package com.github.yard01.gamename.gameplay;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.github.yard01.gamename.gameplay.textures.TextureConstants;
import com.github.yard01.graphicbase.XY;
import com.github.yard01.tools.GameNameTools;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by yard on 14.04.17.
 */


public class GamePlay implements IGamePlay, IGamePlayDefaultProperties {

    private volatile XY fieldShift = new XY();
    private volatile XY fieldSize = new XY(20000, 20000);

    private volatile XY fieldVelocity = new XY();
    private volatile boolean isRefresh = false;
    private volatile float currentScale;
    private volatile float downX, downY;
    private View currentView;
    public int pixels[];
    //private ByteBuffer screenBuffer;
    public long time1 = 0;
        private volatile boolean isMove = false;
    private Thread flingProcess = null;

    public volatile Bitmap shadowBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
    public volatile Bitmap btm = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

    private int row_count = 0;//(int)(this.fieldSize.Y / IGamePlayDefaultProperties.CELL_HEIGHT);
    private int column_count = 0;//(int)(this.fieldSize.X / IGamePlayDefaultProperties.CELL_WIDTH);
    private int width = 0;
    private int height = 0;

    //RGB565 -> RGB888 using tables
    //int[] Table5 = {0, 8, 16, 25, 33, 41, 49, 58, 66, 74, 82, 90, 99, 107, 115, 123, 132,
    // 140, 148, 156, 165, 173, 181, 189, 197, 206, 214, 222, 230, 239, 247, 255};

    //int[] Table6 = {0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 45, 49, 53, 57, 61, 65, 69,
     //73, 77, 81, 85, 89, 93, 97, 101, 105, 109, 113, 117, 121, 125, 130, 134, 138,
     //142, 146, 150, 154, 158, 162, 166, 170, 174, 178, 182, 186, 190, 194, 198,
     //202, 206, 210, 215, 219, 223, 227, 231, 235, 239, 243, 247, 251, 255};


    private ByteBuffer screenBuffer;

    static {
        Random rand  = new Random();

        for (int i=0; i < IGamePlayDefaultProperties.ROW_COUNT; i++)
            for (int j=0; j < IGamePlayDefaultProperties.COLUMN_COUNT; j++) {
                cells[i][j] = rand.nextInt(32768);
        }
    }

    class FlingThread extends Thread {

        public void run() {
            isMove = true;
            float dvX = -Math.signum(fieldVelocity.X) * DECELERATION * USER_REFRESH_DELAY_SECOND; //delta velocity for X on 1 second
            float dvY = -Math.signum(fieldVelocity.Y) * DECELERATION * USER_REFRESH_DELAY_SECOND; //delta velocity for Y on 1 second

            while (isMove) {
                try {
                    //Log.d("debug->", "BAMS!!!" + vX + ", " + vY );
                    fieldShift.X = fieldShift.X + fieldVelocity.X;
                    fieldShift.Y = fieldShift.Y + fieldVelocity.Y;
                    //borders control for X
                    if (fieldShift.X < 0 ) {
                        fieldVelocity.X = 0;
                        fieldShift.X = 0;
                    } else if (fieldShift.X > GamePlayDefaults.FIELD_WIDTH - width) {
                        fieldVelocity.X = 0;
                        fieldShift.X = GamePlayDefaults.FIELD_WIDTH - width; //DEF_WIDTH;
                    } else //deceleration control for X
                        if (Math.abs(fieldVelocity.X) > Math.abs(dvX)) fieldVelocity.X = fieldVelocity.X + dvX; else fieldVelocity.X = 0;

                    //borders control for X
                    if (fieldShift.Y < 0 ) {
                        fieldVelocity.Y = 0;
                        fieldShift.Y = 0;
                    } else if (fieldShift.Y > DEF_HEIGHT - height) {
                        fieldVelocity.Y = 0;
                        fieldShift.Y = DEF_HEIGHT - height;
                    } else //deceleration control for X
                        if (Math.abs(fieldVelocity.Y) > Math.abs(dvY)) fieldVelocity.Y = fieldVelocity.Y + dvY; else fieldVelocity.Y = 0;

                    fillBuffer2(screenBuffer, (int)fieldShift.X, (int)fieldShift.Y);
                    btm = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
                    btm.copyPixelsFromBuffer(screenBuffer);//dstbuf);

                    isMove = fieldVelocity.X != 0 | fieldVelocity.Y != 0;
                    isRefresh = true;
                    //Log.d("debug->", "" + vX + ", " + vY +"   :   " + dvX + ", " + dvY);
                    Thread.sleep(USER_REFRESH_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class ShadowBitmapBuilder extends Thread {

        public void run() {
            Random rand  = new Random();
            while (true) {
                if (isRefresh) {

                    isRefresh = false;


                } else try {

                    Thread.sleep(IGamePlayDefaultProperties.USER_REFRESH_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startFling() {

        flingProcess = new FlingThread();
        flingProcess.start();

    }

    protected void fillPixels21() {
        int r = 90;//160;
        int g = 0;
        int b = 157;//227;
        int row_count = (int)(this.fieldSize.Y / IGamePlayDefaultProperties.CELL_HEIGHT);
        int column_count = (int)(this.fieldSize.X / IGamePlayDefaultProperties.CELL_WIDTH);
        Random rnd  = new Random();
        long ms = System.currentTimeMillis();
        for (int i=0; i < row_count; i++) {
            for (int j=0; j < column_count; j++) {
                int byte_color = cells[i][j];
               // Log.d("debug8->", i + ", " + j +" = " + byte_color);
                //int r = rnd.nextInt(255);
                //int g = rnd.nextInt(255);
                //int b = rnd.nextInt(255);
                //Log.d("colors->", "-------["+byte_color+"]-------");

                int cy = 0;
                for (int y=i*IGamePlayDefaultProperties.CELL_HEIGHT; y < i*IGamePlayDefaultProperties.CELL_HEIGHT + IGamePlayDefaultProperties.CELL_HEIGHT; y++ ) {
                    cy++;
                    int cx = 0;
                    for (int x = j * IGamePlayDefaultProperties.CELL_WIDTH; x < j * IGamePlayDefaultProperties.CELL_WIDTH + IGamePlayDefaultProperties.CELL_WIDTH; x++) {
                        //byte_color = (int)GameNameTools.getLongRand(byte_color); // IGamePlayDefaultProperties.MIN_PAPEER_BRIGHTNESS + GameNameTools.getNextIntRand(byte_color,IGamePlayDefaultProperties.DELTA_PAPEER_BRIGHTNESS);
                        cx++;
                        int dif = 0;
                        int dif_x = 2;
                        int dif_y = 2;
                        boolean border_x = false;
                        if (cx < 3) {
                            dif_x = cx;
                            border_x = true;
                        } else if (cx > IGamePlayDefaultProperties.CELL_WIDTH - 3) {
                          dif_x = IGamePlayDefaultProperties.CELL_WIDTH - cx;
                        }

                        if (cy < 3) {
                            dif_y = cy;
                        } else if (cy > IGamePlayDefaultProperties.CELL_HEIGHT - 3) {
                            dif_y = IGamePlayDefaultProperties.CELL_HEIGHT - cy;
                        }

                        //dif_x = 2;

                        dif = dif_x+dif_y;
                        if (dif < 2) dif = 2;


                        //if (dif == 4) dif = 3;
                        //dif = dif - 2;
                        byte_color = 248 + rnd.nextInt(7); //int)GameNameTools.getLongRand(byte_color); // IGamePlayDefaultProperties.MIN_PAPEER_BRIGHTNESS + GameNameTools.getNextIntRand(byte_color,IGamePlayDefaultProperties.DELTA_PAPEER_BRIGHTNESS);
                        int d_r = byte_color - r;
                        int d_g = byte_color - g;
                        int d_b = byte_color - b;
                        ///!!!!//
                        //dif = 4 ;
                        //;
                        /////////
                        int bc_r = byte_color - (d_r>>>dif) ;//235 + byte_color*19/32767;
                        int bc_g = byte_color - (d_g>>>dif);
                        int bc_b = byte_color - (d_b>>>dif);
                        //Log.d("colors->", ""+byte_color);
                        //try {
                        //if (bc_r == byte_color) continue;

                        //if (!(dif_x == 2 & dif_y == 2)) continue;

                        //if (!border_x) continue;

                        pixels[x + y * (int) this.fieldSize.X] = Color.rgb(bc_r, bc_g, bc_b);
                        //}
                        //catch (Exception e) {
                        //    Log.d("array", x+ ", "+y);
                        //   System.exit(1);
                        //}
//Log.d("debug8->", x+ ", " + y);
                    }
                }
            }
        }
        Log.d("time->", "time of " + (System.currentTimeMillis() - ms));
       /* int r = 21;
        int g = 21;//63;
        int b = 21;
        //Color.RED; //.rgb()
        int c = 200 + GameNameTools.getIntRand(32);
        Log.d("debug7->",""+pixels.length);
        int cur_k = -1;
        int cur_l = -1;
        int cur_cell = 0;
        int byte_color = 0;

        for (int i=0; i < pixels.length; i++){
            int row = i/(int)fieldSize.X;
            int k =  row / IGamePlayDefaultProperties.CELL_HEIGHT;
            int l = (i - row*(int)fieldSize.X)/IGamePlayDefaultProperties.CELL_WIDTH;
            if (k!= cur_k | l != cur_l) {
                cur_k = k;
                cur_l = l;
                byte_color = cells[k][l]; //cur_cell;
            }
            byte_color = 150 + GameNameTools.getNextIntRand(byte_color,100);
            //Log.d("debug8->", i +": "+ k+", "+l);
            //int cell = cells[k][l];
            pixels[i] = Color.rgb(byte_color, byte_color, byte_color); //(r << 12) + (g << 6) + b;
        }*/
    }

    protected void fillPixels3() {
        long ms = System.currentTimeMillis();
        //int color = Color.rgb(0,0,255);
        for (int i = 0; i < (pixels.length>>2); i=i+8) {
            pixels[i] = Color.GREEN;
            pixels[i+1] = Color.GREEN;
            pixels[i+2] = Color.GREEN;
            pixels[i+3] = Color.GREEN;
            pixels[i+4] = Color.GREEN;
            pixels[i+5] = Color.GREEN;
            pixels[i+6] = Color.GREEN;
            pixels[i+7] = Color.GREEN;

        }
        //Log.d("time->", "time3 = " +(System.currentTimeMillis() - ms));
    }

    protected void fillPixels() {
        int r = 21;
        int g = 21;//63;
        int b = 21;
        //Color.RED; //.rgb()
        int c = 200 + (int)GameNameTools.getLongRand(32);
        Log.d("debug7->",""+pixels.length);
        int cur_k = -1;
        int cur_l = -1;
        int cur_cell = 0;
        int byte_color = 0;
        for (int i=0; i < pixels.length; i++){
            int row = i/(int)fieldSize.X;
            int k =  row / IGamePlayDefaultProperties.CELL_HEIGHT;
            int l = (i - row*(int)fieldSize.X)/IGamePlayDefaultProperties.CELL_WIDTH;
            if (k!= cur_k | l != cur_l) {
                cur_k = k;
                cur_l = l;
                byte_color = cells[k][l]; //cur_cell;
            }
            byte_color = 150 + GameNameTools.getNextIntRand(byte_color,100);
            //Log.d("debug8->", i +": "+ k+", "+l);
            //int cell = cells[k][l];
            pixels[i] = Color.rgb(byte_color, byte_color, byte_color); //(r << 12) + (g << 6) + b;
        }
     }

    private void fillTextureColors_ARGB8888( long[] texture_colors) {
             long alpha = 0xFFl;//4 bytes: 1 - alpha, 4,3,2 - rgb
             long color_template = 31l << 3; //11110000
             for (int i = 0; i < texture_colors.length; i++) {

                 long high_color =  color_template | (i >> 3);
                 long high_pixel = alpha | (high_color << 24) | (high_color << 16) | (high_color << 8);

                 long low_color = color_template | (i & 7);
                 long low_pixel = alpha | (low_color << 24) | (low_color << 16) | (low_color << 8);
                 //long t = 0x00FF00FF; //~(int)0;

                 texture_colors[i] = (high_pixel <<  32) | low_pixel;
                 //Log.d("pixel", ""+Long.toHexString(texture_colors[i]));
             }

    }

    private void fillTextureColors_RGB565( long[] texture_colors) {
                 //long alpha = 0xFFl;//4 bytes: 1 - alpha, 4,3,2 - rgb
                 long color_template_5 = 28l; //11000
                 long color_template_6 = 56l;//56l; //111000

                 for (int i = 0; i < texture_colors.length; i++) {

                     long color0_5 = 31l; // color_template_5 | (i & 7);
                     long color0_6 = 63l; //color_template_6 | (i & 7);


                     long color1_5 = 31l;// color_template_5 | (i >> 3) & 7;
                     long color1_6 = 63l;// color_template_6 | (i >> 3) & 7;

                     long color2_5 =  29l; //color_template_5 | (i >> 6) & 7;
                     long color2_6 =  60l;//color_template_6 | (i >> 6) & 7;

                     long color3_5 =  29l; //color_template_5 | (i >> 9) & 7;
                     long color3_6 =  60l; //color_template_6 | (i >> 9) & 7;

                     long pixel0 = (color0_5 << 11) | (color0_6 << 5) | (color0_5);
                     long pixel1= (color1_5 << 11) | (color1_6 << 5) | (color1_5);
                     long pixel2 = (color2_5 << 11) | (color2_6 << 5) | (color2_5);
                     long pixel3 = (color3_5 << 11) | (color3_6 << 5) | (color3_5);

                     texture_colors[i] = -2380188721862951177l;// -2608184625542537754l;//(pixel3 <<  48) | (pixel2 <<  32) | (pixel1 << 16) | pixel0;
                     //Log.d("pixel", ""+Long.toHexString(texture_colors[i]));
                 }

    }

    protected ByteBuffer fillPixels4() {
        int width = (int)this.fieldSize.X;
        int height = (int)this.fieldSize.Y;
        ByteBuffer screen = ByteBuffer.allocateDirect(width * height * 2); //RGB_565
        screen.rewind();
        long _4pixels = -2380188721862951177l;//0xFFFFl; //8 bytes
        int position = 0;//2 * width + 2 * 4;
        int k = 0;
//        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < width; i += 8) {
                position += i;

//                if (k == 24*2) {
//                    k = 0;
//                    continue;
             //}
                k += 8;
                screen.putLong(position, _4pixels);

            }
            //screen.position(width * 2 * IGamePlayDefaultProperties.CELL_HEIGHT);
//        }
        return screen;
    }

    public void fillBuffer2(ByteBuffer buffer, int x, int y) {

        int di = y / GamePlayDefaults.CELL_HEIGHT;
        int dj = x / GamePlayDefaults.CELL_WIDTH;
        int offset_x = x - dj * GamePlayDefaults.CELL_WIDTH;
        int offset_y = y - di * GamePlayDefaults.CELL_HEIGHT;

        //int row_count = (int)(this.fieldSize.Y / IGamePlayDefaultProperties.CELL_HEIGHT);
        //int column_count = (int)(this.fieldSize.X / IGamePlayDefaultProperties.CELL_WIDTH);

        int position = 0;
        int i = di; //current row
        int j = dj; //current column
        int cell_x = offset_x; //current X offset inside cell
        int cell_y = offset_y; //current Y offset inside cell
        int line_length = 0;
        long cell = GamePlayDefaults.cells[i][j];
        int col_index = 0;

        while (position < buffer.limit()) {

            if (line_length >= width) { //go to next line
                line_length = 0;
                j = dj;
                //cell = GamePlayDefaults.cells[i][j] ^ TextureConstants.CELL_COLOR_MATRIX[cell_y];
                cell_y++;
                cell_x = offset_x;
                //continue;
                if (cell_y >= GamePlayDefaults.CELL_HEIGHT) {
                    cell_y = 0;
                    i++;
                    cell = GamePlayDefaults.cells[i][j]; //random long of cell
                }// else cell_y++;

            }
            //long c = GamePlayDefaults.cells[i][j];
            switch (cell_y) {
                case 0: case 31: buffer.putLong(position, TextureConstants.VIOLET_COLORS[col_index]);
                    break;
                //case 1: case 30: buffer.putLong(position, TextureConstants.LIGHT_VIOLET_COLORS[col_index]);
                //    break;
                default: if (cell_x == 0) buffer.putLong(position, TextureConstants.VIOLET_GRAY_COLORS[col_index]);
                    else if (cell_x == GamePlayDefaults.CELL_WIDTH - GamePlayDefaults.DRAW_PIXEL_COUNT) buffer.putLong(position, TextureConstants.GRAY_VIOLET_COLORS[col_index]);
                    else buffer.putLong(position, TextureConstants.GRAY_COLORS[col_index]);
            }


            if (cell_x >= GamePlayDefaults.CELL_WIDTH) { //go to next right cell
                cell_x = 0;
                //cell = GamePlayDefaults.cells[i][j];
                j++;
                cell = GamePlayDefaults.cells[i][j] ^ TextureConstants.CELL_COLOR_MATRIX[cell_y];
                //j++;
                //    if (j == column_count + dj - 1) {
                //        j = dj;
                //        cell_y++;
                //        //cell_x = 24;
                //    } else j++;
            }


            cell_x += GamePlayDefaults.DRAW_PIXEL_COUNT;

            //if (j >= column_count + dj && cell_x >= offset_x - GamePlayDefaults.DRAW_PIXEL_COUNT
            //        )
            position += 8; //long size
            //cell_x += GamePlayDefaults.DRAW_PIXEL_COUNT;
            line_length += GamePlayDefaults.DRAW_PIXEL_COUNT;
            col_index = (int) ((cell >>> (cell_x << 1)) & 255);

        }

    }

    public void fillBuffer(ByteBuffer buffer) {
        //buffer.limit()
        int row_count = (int)(this.fieldSize.Y / IGamePlayDefaultProperties.CELL_HEIGHT);
        int column_count = (int)(this.fieldSize.X / IGamePlayDefaultProperties.CELL_WIDTH);
        long c = -2380152403065315329l; //~0l;
        //byte[] arr = buffer.array();
        //Arrays.fill(arr, (byte)0b11111111);
        //if (true) return;
        int position = 0; //position of reading

        for (int i = 0; i < row_count; i++) {
            //for (int x = j * IGamePlayDefaultProperties.CELL_WIDTH; x < j*IGamePlayDefaultProperties.CELL_WIDTH + IGamePlayDefaultProperties.CELL_WIDTH; x+=4) { //4 pixels on iteration
            //    int col_index = (int) ((GamePlayDefaults.cells[i][j] >>> offset) & 255);
            //    //buffer.putLong(position, TextureConstants.VIOLET_COLORS[col_index]);
            //    position+=8;
            //offset+=8;
            //}

            for (int j = 0; j < 2 /*column_count*/; j++) {
                //position = j * IGamePlayDefaultProperties.CELL_WIDTH + i * ;
                //long cell = GamePlayDefaults.cells[i][j];
                int cell_x = 0; // line inside a cell
                int offset = 0;
                //for (int x = j * IGamePlayDefaultProperties.CELL_WIDTH; x < j*IGamePlayDefaultProperties.CELL_WIDTH + IGamePlayDefaultProperties.CELL_WIDTH; x+=4) { //4 pixels on iteration
                //    int col_index = (int) ((GamePlayDefaults.cells[i][j] >>> offset) & 255);
                //    //buffer.putLong(position, TextureConstants.VIOLET_COLORS[col_index]);
                //    position+=8;
                    //offset+=8;
                //}
                //position =
                for (int y = 1 + i * IGamePlayDefaultProperties.CELL_HEIGHT; y < i*IGamePlayDefaultProperties.CELL_HEIGHT + IGamePlayDefaultProperties.CELL_HEIGHT; y++) {
                    //long mask = TextureConstants.CELL_COLOR_MATRIX[cell_x];
                    long cell = GamePlayDefaults.cells[i][j] ^ TextureConstants.CELL_COLOR_MATRIX[cell_x];
                    offset = 0; // offset inside a line of cell
                    int col_index = (int) ((cell >>> offset) & 255);
                    buffer.putLong(position, TextureConstants.VIOLET_GRAY_COLORS[col_index]);
                    position+=8;
                    offset+=8;
                    for (int x = 4 + j * IGamePlayDefaultProperties.CELL_WIDTH; x < j*IGamePlayDefaultProperties.CELL_WIDTH + IGamePlayDefaultProperties.CELL_WIDTH - 4; x+=4) { //4 pixels on iteration
                        col_index = (int) ((cell >>> offset) & 255);
                        buffer.putLong(position, TextureConstants.GRAY_COLORS[col_index]);
                        position+=8;
                        offset+=8;
                    }
                    col_index = (int) ((cell >>> offset) & 255);
                    buffer.putLong(position, TextureConstants.GRAY_VIOLET_COLORS[col_index]);
                    position+=8;
                    cell_x++;
                }
            }
        }
    }

    public GamePlay(int width, int height) {

        row_count = (int)(height/ IGamePlayDefaultProperties.CELL_HEIGHT);
        column_count = (int)(width / IGamePlayDefaultProperties.CELL_WIDTH);
        this.width = width;
        this.height = height;
        int buffersize = width * height * 2;// rgb_565
//        long[] texture_colors  = new long[64];
        long[] texture_colors2 = new long[4095];

        long alpha = 0xFFl;//4 bytes: 1 - alpha, 4,3,2 - rgb
        long color_template = 31l << 3; //11110000

        //fillTextureColors_ARGB8888(texture_colors);
        // fillTextureColors_RGB565(texture_colors2);
        //byte[] bbb = new byte[buffersize];
        ////3

        //Arrays.fill(bbb, (byte) ( (1 << 7) + (1 << 6) + (1 << 5) + (1 << 4) + (1 << 3) + (1 << 2) + (1 << 1) + 1 ));//Byte.MAX_VALUE);

    ////////////////////////////////////////////////////////////////////
        screenBuffer = ByteBuffer.allocateDirect(buffersize);
        //byte bt[] = pixelsRaw.array();
        //Arrays.fill(pixelsRaw.array(), (byte)~0);
        screenBuffer.rewind();
    ////////////////////////////////////////////////////////////////////

        //pixelsRaw.put(bbb);
        //byte bt = (byte)((1 << 7) + (1 << 6) + (1 << 5) + (1 << 4) + (1 << 3) + (1 << 2) + (1 << 1) + 1 );

       // long px =  0xFF0000AA;//texture_colors[1]; //~ (long) 0;
        Random col = new Random();
        //pixelsRaw.putLong(0, px);
/*
        pixelsRaw.put(0,(byte) -1);
        pixelsRaw.put(1,(byte) 0);
        pixelsRaw.put(2,(byte) 0);
        pixelsRaw.put(3,(byte) -1);

        pixelsRaw.put(4,(byte) -1);
        pixelsRaw.put(5,(byte) 0);
        pixelsRaw.put(6,(byte) 0);
        pixelsRaw.put(7,(byte) -1);

        pixelsRaw.put(8,(byte) -1);
        pixelsRaw.put(9,(byte) 0);
        pixelsRaw.put(10,(byte) 0);
        pixelsRaw.put(11,(byte) -1);*/


        //int c565 = 31; //63 << 5;//31 << 11;//(31 << 11) | (63 << 5) | (31);
        //long px565 = c565;
        //pixelsRaw.rewind();
        //for (int i = 0 ; i < buffersize; i+=8) {
            //long px = texture_colors2[0]; //col.nextInt(4095)];//col.nextInt(64)];
        //    long px = texture_colors2[col.nextInt(4095)];//col.nextInt(64)];
        //    pixelsRaw.putLong(i, px);
        //
        //}
         //Log.d("long->", ""+ (System.currentTimeMillis() - ms));
        //pixelsRaw.rewind();

        //Log.d("long->", ""+ (System.currentTimeMillis() - ms));
        //                          1    1
        fieldSize.X = width;
        fieldSize.Y = height;
        //ByteBuffer pixelsRaw4 = fillPixels4();

        shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //**pixels = new int[width * height];
        //**fillPixels2();
        //**shadowBitmap.setPixels(pixels, 0, shadowBitmap.getWidth(), 0, 0, shadowBitmap.getWidth(), shadowBitmap.getHeight());

        //shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        //shadowBitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.RGB_565);

        ByteBuffer dstbuf = ByteBuffer.allocate(buffersize);
        dstbuf.rewind();

        shadowBitmap.copyPixelsToBuffer(dstbuf);

        long ms = System.currentTimeMillis();
        fillBuffer2(screenBuffer,0, 0);//dstbuf);

        time1 = System.currentTimeMillis() - ms;

        long cc = 134565545l;


        //pixelsRaw.putLong(0, cc);
        //pixelsRaw.putLong(8, cc);
        //pixelsRaw.putLong(16, cc);
        //pixelsRaw.putLong(24, cc);
        //pixelsRaw.putLong(32, cc);
        //pixelsRaw.putLong(40, cc);


        //pixelsRaw.rewind();
        //shadowBitmap.copyPixelsToBuffer(pixelsRaw);//dstbuf);
        HashSet<Long> hsl = new HashSet<Long>();
        //if (false)
        int offset = 0;
        int position = 0;
        for (int i = width * 2 * 32; i < buffersize ; i += 8) {
           // String s1 = dstbuf.get()
            //

            //if (i >= buffersize - 64) break;
           long lo = dstbuf.getLong(i);
           hsl.add(lo);

           /*
           if (i == 0
                   || i == 64 - 8
                   || i == 128 - 8
                   || i == 192 - 8

                   || i == width * 2 - 8
                   || i == 64 + width * 2 - 8
                   || i == 128 + width * 2 - 8
                   || i == 192 + width * 2 - 8

                   || i == 2 * width * 2 - 8
                   || i == 64 + 2 * width * 2 - 8
                   || i == 128 + 2 * width * 2 - 8
                   || i == 192 + 2 * width * 2 - 8

                   || i == 3 * width * 2 - 8
                   || i == 64 + 3 * width * 2 - 8
                   || i == 128 + 3 * width * 2 - 8
                   || i == 192 + 3 * width * 2 - 8

                   ) //& i / (width * 2)  =*/
                   //pixelsRaw.putLong(i, lo);
                   // offset += 8;
                   if (offset >= width * 2) {
                     i += width * 2 * 31;
                     offset = 0;
                    // continue;
                   }


            //   String s = Long.toBinaryString(lo);
        //   if (s.length() < 64) break;
        //   s =
        //    s.substring(0, 5) + " " + s.substring(5, 11) + " " + s.substring(11, 16)
        //   +"\n\r"
        //   +s.substring(16,21)+ " "+s.substring(21,27)+ " "+ s.substring(27, 32)
        //   +"\n\r"
        //   +s.substring(32,37)+ " "+s.substring(37,43)+ " "+ s.substring(43, 48)
        //   +"\n\r"
        //   +s.substring(48,53)+ " "+s.substring(53,59)+ " "+ s.substring(59, 64)
        //   ;
        //   s = s + "\n\r";
        }
        long sz = hsl.size();
        Log.d("colong", "size = " + sz);

        for (Long ll: hsl) {
            Log.d("colong",""+ll+"l,");
        }

        btm = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        btm.copyPixelsFromBuffer(screenBuffer);//dstbuf);

        //for (int i = 0; i < sz; i++) {
        //    Log.d("colorl", "" + hsl.)
        //}

        //long pi = dstbuf.getLong(0);
        //String hex = Long.toHexString(pi);
        //String bin = Long.toBinaryString(pi);
        //byte a = dstbuf.get(0);
        //byte r = dstbuf.get(1);
        //byte g = dstbuf.get(2);
        //byte b = dstbuf.get(3);

        //byte alpha1 = dstbuf.get(4) ;
        //byte r1 = dstbuf.get(5) ;
        //byte g1 = dstbuf.get(6) ;
        //byte b1 = dstbuf.get(7) ;

        //
//  Log.d("debug7->", width+","+height);
        //shadowBitmap.setPixels(pixels, 0, shadowBitmap.getWidth(), 0, 0, shadowBitmap.getWidth(), shadowBitmap.getHeight());
        ///shadowBitmap.sameAs(btm);

        Thread t = new ShadowBitmapBuilder();
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();

    }

    @Override
    public void setFieldShift(XY xy) {
        this.fieldShift.X = xy.X;
        this.fieldShift.Y = xy.Y;
    }

    @Override
    public void setXFieldShift(float x) {
        this.fieldShift.X = x;
    }

    @Override
    public void setYFieldShift(float y) {
        this.fieldShift.Y = y;
    }

    @Override
    public XY getGameFieldSize() {
        return fieldSize;
    }

    @Override
    public XY getFieldShift() {
        return fieldShift;
    }

    @Override
    public float getScale() {
        return 0;
    }

    @Override
    public void setScale(float scale) {
        currentScale = scale;
    }

    @Override
    public void init(View view) {
        currentView = view;
        Log.d("debug->", view.toString());
        //fieldSize.X = view.getWidth();
        //fieldSize.Y = view.getHeight();
    }

    @Override
    public void flingField(final float velocityX, final  float velocityY) {
        Log.d("debug->", "Inc velocity");
        this.fieldVelocity.X += velocityX;
        this.fieldVelocity.Y += velocityY;
        if (this.flingProcess == null || !this.flingProcess.isAlive()) {
           startFling();
            Log.d("debug->", "Start moving: " + this.fieldVelocity.X +", " + this.fieldVelocity.Y);

        } else  {
            Log.d("debug->", "It's already moving: " + this.fieldVelocity.X +", " + this.fieldVelocity.Y);

        }

    }

    @Override
    public void stopFling() {
        isMove = false;
    }

    @Override
    public void setTouchDown(float x, float y) {
        this.downX = x;
        this.downY = y;
        stopFling();
    }

    @Override
    public void touchWorking(float x, float y) {
        fieldShift.X += x - downX;
        fieldShift.Y += y - downY;

    }

    public XY getFieldVelocity() {
        return fieldVelocity;
    }

}
