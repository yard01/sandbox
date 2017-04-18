package com.github.yard01.gamename.gameplay;

import android.view.View;

import com.github.yard01.graphicbase.XY;

/**
 * Created by yard on 14.04.17.
 */

public interface IGamePlay {

    public void setFieldShift(XY xy);
    public void setXFieldShift(float x);
    public void setYFieldShift(float y);
    public XY getGameFieldSize();
    public XY getFieldShift();
    public float getScale();
    public void  setScale(float scale);
    public void init(View view);
    public void flingField(final float velocityX, final  float velocityY);
    public void stopFling();
    public void setTouchDown(float x, float y);
    public void touchWorking(float x, float y);
}
