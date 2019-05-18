package com.github.yard01.sandbox.crib.ui_crib.themes;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.example.ui_crib.R;

public class UICribThemeDemoActivity extends AppCompatActivity {
    public static final String THEME_PARAMETER = "theme";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeId = getIntent().getIntExtra(THEME_PARAMETER, 0);

        if (themeId != 0) setTheme(themeId);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uicrib_theme_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //?????
        //setSupportActionBar(toolbar); ?????///////////////////
        //?????

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
