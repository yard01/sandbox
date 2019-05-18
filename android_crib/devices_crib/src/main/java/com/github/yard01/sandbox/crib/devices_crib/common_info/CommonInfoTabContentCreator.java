package com.github.yard01.sandbox.crib.devices_crib.common_info;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

import com.example.devices_crib.R;

import androidx.fragment.app.FragmentManager;

public class CommonInfoTabContentCreator {
    public static final String yes = "YES";
    public static final String no = "NO";

    public static void createContent(FragmentManager fragmentManager, View rootView) {
        TextView tv = rootView.findViewById(R.id.devices_crib_common_info_tv);
        StringBuilder strInfoBuilder = new StringBuilder();

        SensorManager sm =  (SensorManager) rootView.getContext().getSystemService(Context.SENSOR_SERVICE);

        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        String eos = System.getProperty("line.separator");
        strInfoBuilder.append("Accelerometer - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        strInfoBuilder.append("Ambient Temperature - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        strInfoBuilder.append("Gravity - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        strInfoBuilder.append("Gyroscope - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        strInfoBuilder.append("Gyroscope Uncalibrated - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        strInfoBuilder.append("Light - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        strInfoBuilder.append("Magnetic Field - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        strInfoBuilder.append("Step Detector - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        strInfoBuilder.append("Rotation Vector - ").append((sensor !=  null)? yes : no).append(eos);

        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION );
        strInfoBuilder.append("Orientation - ").append((sensor !=  null)? yes : no).append(eos);


        tv.setText(strInfoBuilder.toString());

    }
}