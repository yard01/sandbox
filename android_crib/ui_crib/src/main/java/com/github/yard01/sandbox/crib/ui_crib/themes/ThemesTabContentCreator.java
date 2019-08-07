package com.github.yard01.sandbox.crib.ui_crib.themes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ui_crib.R;

import androidx.fragment.app.FragmentManager;

public class ThemesTabContentCreator {

    static class ButtonClickListener implements View.OnClickListener {
        int themeId = 0;
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ui_crib_themes_btn_winter)  themeId = R.style.UI_Crib_CommonAppThemeWinter;
            else if (v.getId() == R.id.ui_crib_themes_btn_summer) themeId = R.style.UI_Crib_CommonAppThemeSummer;
            else if (v.getId() == R.id.ui_crib_themes_btn_spring) themeId = R.style.UI_Crib_CommonAppThemeSpring;
            else if (v.getId() == R.id.ui_crib_themes_btn_autumn) themeId = R.style.UI_Crib_CommonAppThemeAutumn;
            else if (v.getId() == R.id.ui_crib_themes_btn_default) themeId = R.style.UI_Crib_CommonAppTheme;

            // .UI_Crib_CommonAppThemeAutumn;

            Intent i = new Intent(v.getContext(), UICribThemeDemoActivity.class);
            //Bundle bnd = new Bundle();
            //bnd.putInt(UICribThemeDemoActivity.THEME_PARAMETER, themeId);
            i.putExtra(UICribThemeDemoActivity.THEME_PARAMETER, themeId);
            v.getContext().startActivity(i);
        }
    }
    public static void createContent(FragmentManager fragmentManager, final View view) {
        ButtonClickListener bcl = new ButtonClickListener();
        ((Button)view.findViewById(R.id.ui_crib_themes_btn_winter)).setOnClickListener(bcl);
        ((Button)view.findViewById(R.id.ui_crib_themes_btn_summer)).setOnClickListener(bcl);
        ((Button)view.findViewById(R.id.ui_crib_themes_btn_spring)).setOnClickListener(bcl);
        ((Button)view.findViewById(R.id.ui_crib_themes_btn_autumn)).setOnClickListener(bcl);
        ((Button)view.findViewById(R.id.ui_crib_themes_btn_default)).setOnClickListener(bcl);

    }

}