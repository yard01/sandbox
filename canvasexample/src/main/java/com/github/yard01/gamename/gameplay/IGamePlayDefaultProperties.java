package com.github.yard01.gamename.gameplay;

/**
 * Created by yard on 18.09.17.
 */

public interface IGamePlayDefaultProperties {
    public static int ROW_COUNT = 40;
    public static int COLUMN_COUNT = 66;
    public static int[][] cells = new int[ROW_COUNT][COLUMN_COUNT]; //size of a paper blank in cells
    public static int CELL_HEIGHT = 32;
    public static int CELL_WIDTH = 32;

    public static int MIN_PAPEER_BRIGHTNESS = 210;
    public static int DELTA_PAPEER_BRIGHTNESS = 255 - MIN_PAPEER_BRIGHTNESS;


    public static int DEF_WIDTH = COLUMN_COUNT * CELL_WIDTH;
    public static int DEF_HEIGHT = ROW_COUNT * CELL_HEIGHT;
    public static float DECELERATION = 2000; //velocity deceleration in 1 sec
    public static int USER_REFRESH_DELAY = 100; //msec
    public static float ONE_SECOND = 1000; //msec
    public static float USER_REFRESH_DELAY_SECOND = (float)USER_REFRESH_DELAY / ONE_SECOND;

}
