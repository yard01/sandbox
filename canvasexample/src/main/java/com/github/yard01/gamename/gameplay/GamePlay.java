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

    private volatile XY fieldVelocity = new XY();

    private volatile float currentScale;
    private volatile float downX, downY;
    private View currentView;

    private volatile boolean isMove = false;
    private Thread flingProcess = null;

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
                    } else if (fieldShift.X > DEF_WIDTH) {
                        fieldVelocity.X = 0;
                        fieldShift.X = DEF_WIDTH;
                    } else //deceleration control for X
                        if (Math.abs(fieldVelocity.X) > Math.abs(dvX)) fieldVelocity.X = fieldVelocity.X + dvX; else fieldVelocity.X = 0;

                    //borders control for X
                    if (fieldShift.Y < 0 ) {
                        fieldVelocity.Y = 0;
                        fieldShift.Y = 0;
                    } else if (fieldShift.Y > DEF_HEIGHT) {
                        fieldVelocity.Y = 0;
                        fieldShift.Y = DEF_HEIGHT;
                    } else //deceleration control for X
                        if (Math.abs(fieldVelocity.Y) > Math.abs(dvY)) fieldVelocity.Y = fieldVelocity.Y + dvY; else fieldVelocity.Y = 0;

                    isMove = fieldVelocity.X != 0 | fieldVelocity.Y != 0;

                    //Log.d("debug->", "" + vX + ", " + vY +"   :   " + dvX + ", " + dvY);
                    Thread.sleep(USER_REFRESH_DELAY);
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

    public GamePlay() {
        //flingProcess = new FlingThread();
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
