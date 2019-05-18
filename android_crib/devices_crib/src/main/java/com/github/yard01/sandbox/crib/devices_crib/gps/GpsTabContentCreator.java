package com.github.yard01.sandbox.crib.devices_crib.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devices_crib.R;

import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

public class GpsTabContentCreator {
    private static final String LOG_TAG = "location_info";
    private static Context context;
    private static TextView tvEnabledGPS;
    private static TextView tvEnabledNet;
    private static TextView tvStatusGPS;
    private static TextView tvStatusNet;
    private static LocationManager locationManager;
    private static TextView tvLocationNet;
    private static TextView tvLocationGPS;

    private static LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
                // ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                //        LocationService .MY_PERMISSION_ACCESS_COURSE_LOCATION );
            }
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(LOG_TAG, "status: " + provider);
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }

        private void showLocation(Location location) {
            if (location == null)
                return;
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                Log.d(LOG_TAG, "location gps: " + location);
                tvLocationGPS.setText(formatLocation(location));
            } else if (location.getProvider().equals(
                    LocationManager.NETWORK_PROVIDER)) {
                Log.d(LOG_TAG, "location network: " + location);
                tvLocationNet.setText(formatLocation(location));
            }
        }

        private String formatLocation(Location location) {

            if (location == null)
                return "";
            return String.format(
                    "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                    location.getLatitude(), location.getLongitude(), new Date(
                            location.getTime()));
        }


    };


    public static void createContent(FragmentManager fragmentManager, View rootView) {
        locationManager = (LocationManager) rootView.getContext().getSystemService(Context.LOCATION_SERVICE);

        StringBuilder sbGPS = new StringBuilder();
        StringBuilder sbNet = new StringBuilder();

        tvEnabledGPS = rootView.findViewById(R.id.devices_crib_gps_tvEnabledGPS);
        tvStatusGPS = rootView.findViewById(R.id.devices_crib_gps_tvStatusGPS);
        tvEnabledNet = rootView.findViewById(R.id.devices_crib_gps_tvEnabledNet);
        tvStatusNet = rootView.findViewById(R.id.devices_crib_gps_tvStatusNet);
        tvLocationNet = rootView.findViewById(R.id.devices_crib_gps_tvLocationNet);
        tvLocationGPS = rootView.findViewById(R.id.devices_crib_gps_tvLocationGPS);

        context = rootView.getContext();

        Button btn = rootView.findViewById(R.id.devices_crib_gps_btnLocationSettings);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });


        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);

        checkEnabled();

    }

    public static void checkEnabled() {
        tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }
}
