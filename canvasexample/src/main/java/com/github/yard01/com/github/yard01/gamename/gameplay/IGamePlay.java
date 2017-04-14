package com.github.yard01.com.github.yard01.gamename.gameplay;

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
    public void init(View view);
    public void flingField(final float velocityX, final  float velocityY);
    public void stopFling();
}
