package com.github.yard01.sandbox.crib.dwh_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dwh_crib.R;
import com.github.yard01.sandbox.crib.dwh_crib.files.FilesTabContentCreator;
import com.github.yard01.sandbox.crib.dwh_crib.preferences.PreferencesTabContentCreator;
import com.github.yard01.sandbox.crib.dwh_crib.sql.SQLTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {
    public static void createContent(FragmentActivity activity, int tabNumber) {
        switch (tabNumber) {
            case 0: FilesTabContentCreator.createContent(activity); break;
            //case 1: DialogsTabContentCreator.createContent(activity); break;
            //case 2: ListsNTreesTabContentCreator.createContent(activity); break;


        }
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.dwh_crib_fragment_tab_files, container, false);
                FilesTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            //break;
            case 1:
                rootView = inflater.inflate(R.layout.dwh_crib_fragment_tab_sql, container, false);
                SQLTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 2:
                rootView = inflater.inflate(R.layout.dwh_crib_fragment_tab_preferences, container, false);
                PreferencesTabContentCreator.createContent(fragmentManager, rootView);
                //ListsNTreesTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            //break;
            case 3:
                //rootView = inflater.inflate(R.layout.fragment_tab_coordinatorlayout, container, false);
                //CoordinatorLayoutTabContentCreator.createContent(fragmentManager, rootView);
                //return rootView;

            case 4:
                //rootView = inflater.inflate(R.layout.fragment_tab_themes, container, false);
                //ThemesTabContentCreator.createContent(fragmentManager, rootView);
                //return rootView;

        }
        return null;
    }
}

