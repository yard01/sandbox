package com.github.yard01.sandbox.crib.integration_crib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.integration_crib.R;
import com.github.yard01.sandbox.crib.integration_crib.app_starter.AppStarterTabContentCreator;
import com.github.yard01.sandbox.crib.integration_crib.content_provider.ContentProviderTabContentCreator;
import com.github.yard01.sandbox.crib.integration_crib.tasks.TasksTabContentCreator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class TabContentCreator {
    private FragmentActivity activity;

    public TabContentCreator(FragmentActivity activity){
        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public static void createContent(FragmentActivity activity, int tabNumber) {

        switch (tabNumber) {
        //    case 0: ControlsTabContentCreator.createContent(activity); break;
        //    case 1: DialogsTabContentCreator.createContent(activity); break;
        //    case 2: ListsNTreesTabContentCreator.createContent(activity); break;
        }
    }

    public static View inflateContent(FragmentManager fragmentManager, LayoutInflater inflater, ViewGroup container, int tabNumber) {
        //container.findBy
        final View rootView;
        switch (tabNumber) {
            case 0:
                rootView = inflater.inflate(R.layout.fragment_tab_app_starter, container, false);
                AppStarterTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            //break;
            case 1:
                rootView = inflater.inflate(R.layout.fragment_tab_tasks, container, false);
                TasksTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_tab_content_provider, container, false);
                ContentProviderTabContentCreator.createContent(fragmentManager, rootView);
                return rootView;

        }
        return null;
    }

}
