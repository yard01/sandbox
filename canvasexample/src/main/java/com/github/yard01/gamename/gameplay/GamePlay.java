package com.github.yard01.gamename.gameplay;

import android.util.Log;
import android.view.View;
import com.github.yard01.graphicbase.XY;

/**
 * Created by yard on 14.04.17.
 */



public class GamePlay extends GamePlayDefaults implements IGamePlay {

    private volatile XY fieldShift = new XY();
    private volatile XY fieldSize = new XY(2000, 2000);
    private volatile float currentScale;
    private volatile float downX, downY;

    private View currentView;

    private volatile boolean isMove =false;
/*
    private class FlingProcess extends Thread {
        private float vX;
        private float vY;

        public void setVelosity(float velosityX, float velosityY) {
            this.vX = velosityX;
            this.v
        }
    }
*/
    public GamePlay() {

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

        Thread flingProcess = new Thread() {

            float vX = velocityX ;
            float vY = velocityY;
            float dvX = -Math.signum(vX) * DECELERATION * USER_REFRESH_DELAY_SECOND;
            float dvY = -Math.signum(vY) * DECELERATION * USER_REFRESH_DELAY_SECOND;
            public void run() {
                isMove = true;
                while (isMove) {
                    try {
                        //Log.d("debug->", "BAMS!!!" + vX + ", " + vY );
                        fieldShift.X = fieldShift.X + vX;
                        fieldShift.Y = fieldShift.Y + vY;
                        if (fieldShift.X < 0 ) {
                            vX = 0;
                            fieldShift.X = 0;
                        } else if (fieldShift.X > DEF_WIDTH) {
                            vX = 0;
                            fieldShift.X = DEF_WIDTH;
                        } else if (Math.abs(vX) > Math.abs(dvX)) vX = vX + dvX; else vX = 0;


                        if (fieldShift.Y < 0 ) {
                            vY = 0;
                            fieldShift.Y = 0;
                        } else if (fieldShift.Y > DEF_HEIGHT) {
                            vY = 0;
                            fieldShift.Y = DEF_HEIGHT;
                        } else if (Math.abs(vY) > Math.abs(dvY)) vY = vY + dvY; else vY = 0;

                        isMove = vX > 0 | vY > 0;

                        Log.d("debug->", "" + vX + ", " + vY +"   :   " + dvX + ", " + dvY);
                        Thread.sleep(USER_REFRESH_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        flingProcess.start();
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


}
