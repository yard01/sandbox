package com.github.yard01.gamename.gameplay;

/**
 * Created by yard on 18.09.17.
 */

public interface IGamePlayDefaultProperties {
    public static int STRING_COUNT = 66;
    public static int COLUMN_COUNT = 40;
    public static int[][] cells = new int[STRING_COUNT][COLUMN_COUNT]; //size of a paper blank in cells
    public static int CELL_HEIGHT = 200;
    public static int CELL_WIDTH = 200;

    public static int DEF_WIDTH = COLUMN_COUNT * CELL_HEIGHT;
    public static int DEF_HEIGHT = STRING_COUNT * CELL_WIDTH;
    public static float DECELERATION = 2000; //velocity deceleration in 1 sec
    public static int USER_REFRESH_DELAY = 100; //msec
    public static float ONE_SECOND = 1000; //msec
    public static float USER_REFRESH_DELAY_SECOND = (float)USER_REFRESH_DELAY / ONE_SECOND;
}
