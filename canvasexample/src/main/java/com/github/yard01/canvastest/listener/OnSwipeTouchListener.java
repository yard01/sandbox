package com.github.yard01.canvastest.listener;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.yard01.gamename.gameplay.IGamePlay;

/**
 * Created by yard on 12.04.17.
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    @SuppressWarnings("deprecation")
    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    IGamePlay gamePlay;

    public OnSwipeTouchListener(IGamePlay gamePlay) {
        super();
        this.gamePlay = gamePlay;
    }

    public boolean onTouch(final View v, final MotionEvent event) {
        //Log.d("debug->", "TOUCH: " + event.getX() +", " + event.getY());
        gamePlay.stopFling();
        return gestureDetector.onTouchEvent(event);

    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        //private final class Brake extends Thread {

        ///}


        private static final int SWIPE_THRESHOLD = 10;
        private static final int SWIPE_VELOCITY_THRESHOLD = 10;
        public static final int ACCELERATION = -1;
        private int currentVelocutyX = 0;
        private int currentVelocutyY = 0;

        //Brake brake = new Brake();

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //onTouch(e)
            // ;
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                gamePlay.stopFling();
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                //if (Math.abs(diffX) > Math.abs(diffY))
                {
                    if ((Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                     || (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)){
                        //Log.d("debug->", "velocityX:" + velocityX);
                        gamePlay.flingField(velocityX, velocityY);
                        //if (diffX > 0) {
                        //    onSwipeRight();
                        //} else {
                        //    onSwipeLeft();
                        //}
                    }
                } //else {
                    // onTouch(e);
                //}
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    //@Override
    //public boolean onTouch(View v, MotionEvent event) {
    //    return false;
    //}




    public void onSwipeRight() {
        Log.d("debug->", "right" );
    }

    public void onSwipeLeft() {
        Log.d("debug->", "left" );

    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}