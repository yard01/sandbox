package com.github.yard01.sandbox.crib.dwh_crib.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.example.dwh_crib.R;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class PreferencesTabContentCreator {
    public static final String SAVE_PREFERENCES = "SAVE";
    private static final String TEXT_KEY = "TEXT_KEY";
    private static EditText editText;
    private static SharedPreferences sharedPreferences;
    private static Context context;

    private static View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.dwh_crib_preferences_btn_load) {
                load();
            } else if (i == R.id.dwh_crib_preferences_btn_save) {
                save();
            }
        }
    };
    public static void createContent(FragmentActivity activity) {
        //ViewPager viewPager = (ViewPager) activity.findViewById(R.id.dwh_crib_insidepager);
        //viewPager.setAdapter(new FilesTabContentCreator.ControlsPagerAdapter(activity.getSupportFragmentManager(), activity.getBaseContext()));
        //activity.getBaseContext().
    }

    public static void save() {
        //context.getP
        //SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_KEY, editText.getText().toString());
        editor.commit();
        Toast.makeText(context, "This text was saved", Toast.LENGTH_SHORT).show();

    }

    public static void load() {
        String text = sharedPreferences.getString(TEXT_KEY,"");
        editText.setText(text);

    }

    public static void createContent(FragmentManager fragmentManager, View view) {
        context = view.getContext();
        editText = (EditText) view.findViewById(R.id.dwh_crib_preferences_et_text);
        Button buttonLoad = view.findViewById(R.id.dwh_crib_preferences_btn_load);
        Button buttonSave = view.findViewById(R.id.dwh_crib_preferences_btn_save);
        buttonLoad.setOnClickListener(listener);
        buttonSave.setOnClickListener(listener);
        sharedPreferences = view.getContext().getSharedPreferences(SAVE_PREFERENCES, Context.MODE_PRIVATE);

        //ViewPager viewPager = (ViewPager) view.findViewById(R.id.dwh_crib_insidepager);
        //ZoomButtonsController
    }

}
