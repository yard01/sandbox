package com.github.yard01.sandbox.crib.ui_crib.controls;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.controls.page0.ControlsPage0Creator;
import com.github.yard01.sandbox.crib.ui_crib.controls.page1.ControlsPage1Creator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ControlsTabContentCreator {
    public static String[] titles = {""};

    public static void createContent(FragmentActivity activity) {
        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.insidepager);
        viewPager.setAdapter(new ControlsPagerAdapter(activity.getSupportFragmentManager(), activity.getBaseContext()));
        //activity.getBaseContext().
    }

    public static void createContent(FragmentManager fragmentManager, View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.insidepager);
        viewPager.setAdapter(new ControlsPagerAdapter(fragmentManager, viewPager.getContext()));
    }

    public static class ControlsPageFragment extends Fragment  {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ControlsPageFragment newInstance(int sectionNumber) {
            ControlsPageFragment fragment = new ControlsPageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            Log.d("fragment_inside_create", "create sub: " + sectionNumber);
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
                case 0: rootView = inflater.inflate(R.layout.controls_page0_anything, container, false);
                        ControlsPage0Creator.createContent(rootView);
                    break;
                case 1: rootView = inflater.inflate(R.layout.controls_page1_chips, container, false);
                        ControlsPage1Creator.createContent(rootView);
                        break;
                case 2: rootView = inflater.inflate(R.layout.subfragment_controls_3, container, false);
                        break;// id_fragment = R.layout.subfragment_controls_3; break;
            }

            //View rootView = inflater.inflate(id_fragment, container, false);
            Log.d("fragment_inside", "sub fragment: " + getArguments().getInt(ARG_SECTION_NUMBER) + ", " + container);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }

    public static class ControlsPagerAdapter extends FragmentStatePagerAdapter {
        private static int COUNT = 3;
        private Context context;
        public ControlsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return ControlsPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case 0: return context.getString(R.string.controls_tab0);
                case 1: return context.getString(R.string.controls_tab1);
            }

            return ("tab " + position);


        }

    }


}
