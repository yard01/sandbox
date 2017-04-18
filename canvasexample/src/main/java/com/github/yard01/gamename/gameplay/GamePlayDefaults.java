package com.github.yard01.gamename.gameplay;

/**
 * Created by yard on 14.04.17.
 */

public abstract class GamePlayDefaults {
    public static int DEF_WIDTH = 20000;
    public static int DEF_HEIGHT = 14000;
    public static float DECELERATION = 200; //velocity deceleration in 1 sec
    public static int USER_REFRESH_DELAY = 50; //msec
    public static float ONE_SECOND = 1000; //msec
    public static float USER_REFRESH_DELAY_SECOND = (float)USER_REFRESH_DELAY / ONE_SECOND;
    public static int[][] cells = new int[40][66]; //size of a paper blank in cells
}
