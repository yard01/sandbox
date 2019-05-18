package com.github.yard01.sandbox.crib.service_crib.wallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.example.service_crib.R;

public class SettingsWallpaperActivity extends PreferenceActivity {

    Preference.OnPreferenceChangeListener numberCheckListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // check that the string is an integer
            if (newValue != null && newValue.toString().length() > 0
                    && newValue.toString().matches("\\d*")) {
                return true;
            }
            // If now create a message to the user
            Toast.makeText(SettingsWallpaperActivity.this, "Invalid Input",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.service_crib_settings_activity_wallpaper);
        addPreferencesFromResource(R.xml.service_crib_wallpaper_settings);

        // add a validator to the "numberofCircles" preference so that it only
        // accepts numbers
        Preference circlePreference = getPreferenceScreen().findPreference(
                "numberOfCircles");

        // add the validator
        circlePreference.setOnPreferenceChangeListener(numberCheckListener);

    }
}
