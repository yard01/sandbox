package com.github.yard01.sandbox.crib.ui_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.controls.ControlsTabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.DialogsTabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.coordinatorlayout.CoordinatorLayoutTabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.ListsNTreesTabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.themes.ThemesTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {
    //Массив классов-закладок

    private FragmentActivity activity;

    public TabContentCreator(FragmentActivity activity){
        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public static void createContent(FragmentActivity activity, int tabNumber) {
        switch (tabNumber) {
            case 0: ControlsTabContentCreator.createContent(activity); break;
            case 1: DialogsTabContentCreator.createContent(activity); break;
            case 2: ListsNTreesTabContentCreator.createContent(activity); break;


        }
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.fragment_tab_controls, container, false);
                ControlsTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
                //break;
            case 1:
                rootView = inflater.inflate(R.layout.fragment_tab_dialogs, container, false);
                DialogsTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_tab_lists_and_trees, container, false);
                ListsNTreesTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            //break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_tab_coordinatorlayout, container, false);
                CoordinatorLayoutTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

            case 4:
                rootView = inflater.inflate(R.layout.fragment_tab_themes, container, false);
                ThemesTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

        }
        return null;
    }
}
