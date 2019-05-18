package com.github.yard01.sandbox.crib.ui_crib.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.page0.DialogsPage0Creator;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.page1.DialogsPage1Creator;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DialogsTabContentCreator {

    public static void createContent(FragmentActivity activity) {
        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.dialogs_insidepager);
        viewPager.setAdapter(new DialogsTabContentCreator.DialogsPagerAdapter(activity.getSupportFragmentManager(), activity.getBaseContext()));
        //activity.getBaseContext().
    }

    public static void createContent(FragmentManager fragmentManager, View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.dialogs_insidepager);
        viewPager.setAdapter(new DialogsTabContentCreator.DialogsPagerAdapter(fragmentManager, viewPager.getContext()));
    }

    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static class DialogsPageFragment extends Fragment  {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static DialogsTabContentCreator.DialogsPageFragment newInstance(int sectionNumber) {
            DialogsTabContentCreator.DialogsPageFragment fragment = new DialogsTabContentCreator.DialogsPageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;

        }

        //Отображение содержимого фрагмента
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_blue, container, false);
            int id_fragment = 0;
            View rootView = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0: rootView = inflater.inflate(R.layout.dialogs_page0_alerts, container, false);
                    DialogsPage0Creator.createContent(rootView);
                    break;
                case 1: rootView = inflater.inflate(R.layout.dialogs_page1_fragmentdialog, container, false);
                    DialogsPage1Creator.createContent(rootView);
                    break;

            }

            //View rootView = inflater.inflate(id_fragment, container, false);
            Log.d("fragment_inside", "sub fragment: " + getArguments().getInt(ARG_SECTION_NUMBER) + ", " + container);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }



    public static class DialogsPagerAdapter extends FragmentStatePagerAdapter {
        private static int COUNT = 2;
        private Context context;
        public DialogsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return DialogsTabContentCreator.DialogsPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case 0: return context.getString(R.string.dialogs_tab0);
                case 1: return context.getString(R.string.dialogs_tab1);
            }

            return ("tab " + position);


        }

    }


}
