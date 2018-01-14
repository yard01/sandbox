package com.github.yard01.gamename.gameplay;

import java.util.Random;

/**
 * Created by yard on 14.04.17.
 */

public abstract class GamePlayDefaults {
    public static long[][] cells = new long[40][66]; //size of a paper blank in cells
//    public static int DEF_WIDTH = 20000;
//    public static int DEF_HEIGHT = 14000;
    public static float DECELERATION = 200; //velocity deceleration in 1 sec
    public static int USER_REFRESH_DELAY = 50; //msec
    public static float ONE_SECOND = 1000; //msec
    public static float USER_REFRESH_DELAY_SECOND = (float)USER_REFRESH_DELAY / ONE_SECOND;
    public static int CELL_HEIGHT = 32;
    public static int CELL_WIDTH = 32;
    public static int DRAW_PIXEL_COUNT= 4;
    public static int FIELD_WIDTH  = CELL_WIDTH  * cells[0].length;
    public static int FIELD_HEIGHT = CELL_HEIGHT * cells.length;

    static {
        Random r = new Random();
        for (int i = 0; i <cells.length ; i++) {
            for (int j = 0; j <cells[0].length ; j++) {
                cells[i][j] = r.nextLong();
            }
        }
     }
}
