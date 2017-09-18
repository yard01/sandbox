package com.github.yard01.canvastest;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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

/**
 * Created by yard on 09.04.17.
 */

public class MainActivity extends Activity {
    GamePlay gp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.activity_list_item);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
        gp = new GamePlay();
        gp.init(drawView);
        drawView.setOnTouchListener(new OnSwipeTouchListener(gp));
        calculateMetrics();
    }

    private void calculateMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

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
                        canvas.drawColor(Color.GREEN);
                        Paint paint = new Paint();
                        canvas.drawText("Смещение:" + gp.getFieldShift().X + ", " + gp.getFieldShift().Y, 50,50, paint);
                        canvas.drawText("Скорость:" + gp.getFieldVelocity().X + ", " + gp.getFieldVelocity().Y, 50,100, paint);

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
