package com.github.yard01.sandbox.crib.dwh_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dwh_crib.R;
import com.github.yard01.sandbox.crib.dwh_crib.files.FileDWHTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {

    public static void createContent(FragmentActivity activity, int tabNumber) {
        switch (tabNumber) {
            case 0: FileDWHTabContentCreator.createContent(activity); break;

        }
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.dwh_crib_fragment_tab_files, container, false);
                FileDWHTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

        }
        return null;
    }

}
