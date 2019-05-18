package com.github.yard01.sandbox.crib.devices_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devices_crib.R;
import com.github.yard01.sandbox.crib.devices_crib.accelerometer.AccelerometerTabContentCreator;
import com.github.yard01.sandbox.crib.devices_crib.common_info.CommonInfoTabContentCreator;
import com.github.yard01.sandbox.crib.devices_crib.gps.GpsTabContentCreator;
import com.github.yard01.sandbox.crib.devices_crib.qrscan.QRScanTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {
    private FragmentActivity activity;

    public TabContentCreator(FragmentActivity activity){
        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public static void createContent(FragmentActivity activity, int tabNumber) {

        //switch (tabNumber) {
        //    case 0: ControlsTabContentCreator.createContent(activity); break;
        //    case 1: DialogsTabContentCreator.createContent(activity); break;
        //    case 2: ListsNTreesTabContentCreator.createContent(activity); break;
        //}
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.devices_crib_common_info, container, false);
                CommonInfoTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            //break;
            case 1:
                rootView = inflater.inflate(R.layout.devices_crib_accelerometer, container, false);
                AccelerometerTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 2:
                rootView = inflater.inflate(R.layout.devices_crib_gps, container, false);
                GpsTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 3:
                rootView = inflater.inflate(R.layout.devices_crib_qrscanner, container, false);
                QRScanTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

        }
        return null;
    }
}
