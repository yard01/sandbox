package com.github.yard01.sandbox.crib.devices_crib.qrscan;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.devices_crib.R;

import androidx.fragment.app.FragmentManager;

public class QRScanTabContentCreator {
    private static View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int i = v.getId();
            if (i == R.id.devices_crib_btn_takepicture) {
                v.getContext().startActivity(new Intent(v.getContext(), PictureBarcodeActivity.class));

            } else if (i == R.id.devices_crib_btn_scanbarcode) {
                //startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));

            }
        }
    } ;

    public static void createContent(FragmentManager fragmentManager, View rootView) {
        Button btnTakePicture = rootView.findViewById(R.id.devices_crib_btn_takepicture);
        btnTakePicture.setOnClickListener(clickListener);
        Button btnScanBarcode = rootView.findViewById(R.id.devices_crib_btn_scanbarcode);
        btnScanBarcode.setOnClickListener(clickListener);
    }
}

