package com.github.yard01.canvastest;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//import android.support.v4.app.Fragment;
import com.github.yard01.canvastest.listener.OnSwipeTouchListener;
import com.github.yard01.gamename.gameplay.GamePlay;
import com.github.yard01.gamename.gameplay.IGamePlayDefaultProperties;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by yard on 09.04.17.
 */

public class MainActivity extends Activity {
    GamePlay gp;
    Byte[] bytesImage = {0,1,120, 0,1,120, 0,1,120, 0,1,120};
    int[] colors = new int[300*300];
    int[] screen;
    int[] screen2;

    int intWidth = 2;
    int intHeight = 2;
    int intByteCount = bytesImage.length;
    int[] intColors = new int[intByteCount / 3];
    int width;
    int height;
    Bitmap bitmap;
    Bitmap bitmapAlpha;
    Bitmap screenBitmap;
    int[] byte_array = new int[10000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.activity_list_item);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
        calculateMetrics();
        gp = new GamePlay(width, height);
        gp.init(drawView);
        drawView.setOnTouchListener(new OnSwipeTouchListener(gp));
        screen = new int[width*height];
        screen2 = new int[width*height];
        Arrays.fill(colors, 0, 300*100, Color.argb(85, 255, 0, 0));
        Arrays.fill(colors, 300*100, 300*200, Color.GREEN);
        Arrays.fill(colors, 300*200, 300*300, Color.BLUE);

        Arrays.fill(screen, 0, width * height, Color.BLUE);


        bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
        bitmapAlpha = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888);
        screenBitmap = Bitmap.createBitmap(screen, width, height, Bitmap.Config.RGB_565);

        Arrays.fill(screen2, 0, width * height, Color.RED);

//        Byte[] bytesImage = {0,1,2, 0,1,2, 0,1,2, 0,1,2};
        final int intAlpha = 255;
        if ((intByteCount / 3) != (intWidth * intHeight)) {
            throw new ArrayStoreException();
        }
        for (int intIndex = 0; intIndex < intByteCount - 2; intIndex = intIndex + 3) {
            intColors[intIndex / 3] = (intAlpha << 24) | (bytesImage[intIndex] << 16) | (bytesImage[intIndex + 1] << 8) | bytesImage[intIndex + 2];
        }

    }

    private void calculateMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;

        float density = outMetrics.density;

        float dpHeight = outMetrics.heightPixels / density; //dp
        float dpWidth = outMetrics.widthPixels / density; //dp

        float inHeight = Math.round(outMetrics.heightPixels / outMetrics.ydpi); //inch
        float inWidth =  Math.round(outMetrics.widthPixels / outMetrics.xdpi); //inch

        float mmHeight = inHeight * (float)24.5; //Math.round(outMetrics.heightPixels / outMetrics.ydpi); //inch
        float mmWidth =  inWidth * (float)24.5; //Math.round(outMetrics.widthPixels / outMetrics.xdpi); //inch

        float px1mmHeight = height / mmHeight;
        float px1mmWidth = width / mmWidth;

        Log.d("debug->", px1mmWidth + ", " +px1mmHeight);

        long mss = System.currentTimeMillis();
        int[] px = new int[200000];
        for (int i = 0; i < px.length - 1; i++) px[i] = i;
        Log.d("debug4->", "time, ms: " + (System.currentTimeMillis() - mss));

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    }


    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
            //this.setOnTouchListener(new OnSwipeTouchListener(gp));

            //this.getDisplay().
            //view.setOnt
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        //Bitmap b = new Bitmap();
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null)
                            continue;
                        //canvas.drawRGB();

                        //canvas.drawColor(Color.WHITE);
                        //Bitmap bmpImage = Bitmap.createBitmap(intColors, intWidth, intHeight, Bitmap.Config.ARGB_8888);


                        //Paint paint = new Paint();
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        //canvas.drawBitmap(screenBitmap, 0, 0, paint);
                        paint.setColor(Color.GREEN);
                        //canvas.drawBitmap(gp.shadowBitmap, 0, 0, paint);
                        canvas.drawBitmap(gp.btm, 0, 0, paint);

                        //canvas.drawRGB(200, 70, 100);

                       // screenBitmap.setPixels(screen2, 0,40,40,40,width-1,height-1);

                        canvas.drawText("Смещение:" + gp.getFieldShift().X + ", " + gp.getFieldShift().Y, 50,50, paint);
                        canvas.drawText("Смещение в ячейках:" + gp.getFieldShift().X / IGamePlayDefaultProperties.CELL_WIDTH  + ", " + gp.getFieldShift().Y / IGamePlayDefaultProperties.CELL_HEIGHT, 50,150, paint);
                        canvas.drawText("Скорость:" + gp.getFieldVelocity().X + ", " + gp.getFieldVelocity().Y, 50,100, paint);
                        canvas.drawText("time1: " + gp.time1, 32,180, paint);

                        //paint.setColor(Color.rgb(20, 127, 100));
                        int x_r = 20;
                        int y_r = 200;
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawRect(x_r, y_r, x_r + IGamePlayDefaultProperties.CELL_WIDTH, y_r + IGamePlayDefaultProperties.CELL_HEIGHT, paint);
                        /*
                        IntBuffer bb; // = ByteBuffer.allocateDirect(2*100); //allocate(pixels.length * 2);
                        Random rand = new Random();
                        for (int i=0; i < (byte_array.length-1); i++) {
                            byte_array[i] =  (rand.nextInt(63)<<11) + (rand.nextInt(31)<<6) + (rand.nextInt(63));
                        }
                        bb = IntBuffer.wrap(byte_array);
                        //Bitmap.createBitmap()
                        Bitmap shadowBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
                        //shadowBitmap.copyPixelsFromBuffer(bb);
                        Matrix matrix = new Matrix();
                        matrix.postScale(10, 15);
                        matrix.postRotate(45);
                        //matrix.
                        shadowBitmap.copyPixelsFromBuffer(bb);
                        //canvas.drawBitmap(shadowBitmap, 200, 200, paint);
                        canvas.drawBitmap(shadowBitmap, matrix, paint) ;*/
                        //canvas.drawBitmap(bitmap, 150, 150, paint);
                        //canvas.drawBitmap(bitmapAlpha, 150, 150, paint);


                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }
}
