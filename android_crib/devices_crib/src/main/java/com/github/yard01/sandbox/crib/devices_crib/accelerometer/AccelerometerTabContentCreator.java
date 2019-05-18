package com.github.yard01.sandbox.crib.devices_crib.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.devices_crib.R;

import androidx.fragment.app.FragmentManager;

public class AccelerometerTabContentCreator {
    private static final String LOG_TAG = "accelerometer";
    private static Sensor accelerometer;
    //private static View parentView;
    private static TextView tv_X;
    private static TextView tv_Y;
    private static TextView tv_Z;

    private static SensorEventListener listener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            tv_X.setText(Float.toString(x));
            tv_Y.setText(Float.toString(y));
            tv_Z.setText(Float.toString(z));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static void createContent(FragmentManager fragmentManager, View rootView) {
        Log.d(LOG_TAG, "create accelerometer");
        SensorManager sm =  (SensorManager) rootView.getContext().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv_X = rootView.findViewById(R.id.devices_crib_accel_value_X);
        tv_Y = rootView.findViewById(R.id.devices_crib_accel_value_Y);
        tv_Z = rootView.findViewById(R.id.devices_crib_accel_value_Z);

        sm.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
