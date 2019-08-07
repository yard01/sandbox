package com.github.yard01.sandbox.crib.service_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.service_crib.R;
import com.github.yard01.sandbox.crib.service_crib.simpleservice.SimpleServiceTabContentCreator;
import com.github.yard01.sandbox.crib.service_crib.wallpaper.WallpaperTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {
    private FragmentActivity activity;

    public TabContentCreator(FragmentActivity activity){
        this.activity = activity;
    }

    public static void destroyContent() {
        SimpleServiceTabContentCreator.destroyContent();
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public static void createContent(FragmentActivity activity, int tabNumber) {
        switch (tabNumber) {
            case 0: SimpleServiceTabContentCreator.createContent(activity); break;


        }
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.service_fragment_tab_simpleservice, container, false);
                SimpleServiceTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 2:
                rootView = inflater.inflate(R.layout.service_fragment_tab_wallpaper, container, false);
                WallpaperTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

        }
        return null;
    }

}
