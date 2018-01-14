package com.github.yard01.tools;

/**
 * Created by yard on 30.09.17.
 */

public class GameNameTools {
    public static long getLongRand(long xn) {
        long a = 1664525;
        long c = 1013904223;
        long m = (1 << 15) - 1; //maximim of random 32767
        return (a * xn + c) % m;
    }

    public static int getNextIntRand(int xn, int max) {
        int a = 1664525;
        int c = 1013904223;
        int m = max; //(1 << 15) - 1; //maximim of random 32767
        return (a * xn + c) % m;
    }

}
