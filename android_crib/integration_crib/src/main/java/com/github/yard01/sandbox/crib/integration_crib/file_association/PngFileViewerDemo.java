package com.github.yard01.sandbox.crib.integration_crib.file_association;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.integration_crib.R;

public class PngFileViewerDemo extends AppCompatActivity {
    private static final String LOG_TAG = "file_association";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integration_crib_activity_png_file_viewer_demo);
        //////////////////////////////////////
        //Here is an assotiation
        if (getIntent().getData() != null) {
            ImageView iw = findViewById(R.id.integration_crib_png_view);
            iw.setImageURI(getIntent().getData());
        }

        Log.d(LOG_TAG, getIntent().getData().toString());

    }
}
