package com.github.yard01.canvastest;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    }


    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
            this.setOnTouchListener(new OnSwipeTouchListener(gp));

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
                        canvas = surfaceHolder.lockCanvas(null);

                        if (canvas == null)
                            continue;
                        canvas.drawColor(Color.GREEN);
                        Paint paint = new Paint();
                        canvas.drawText("Смещение:" + gp.getFieldShift().X + ", " + gp.getFieldShift().Y, 50,50, paint);

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
