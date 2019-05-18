package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui_crib.R;

import com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page0.ListsNTreesPage0Creator;
import com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page1.ListsNTreesPage1Creator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ListsNTreesTabContentCreator {

    public static void createContent(FragmentActivity activity) {
        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.lists_and_trees_insidepager);
        viewPager.setAdapter(new ListsNTreesTabContentCreator.ListsNTreePagerAdapter(activity.getSupportFragmentManager(), activity.getBaseContext()));
        //activity.getBaseContext().
    }

    public static void createContent(FragmentManager fragmentManager, View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.lists_and_trees_insidepager);
        viewPager.setAdapter(new ListsNTreesTabContentCreator.ListsNTreePagerAdapter(fragmentManager, viewPager.getContext()));
    }


    public static class ListsNTreesPageFragment extends Fragment  {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ListsNTreesTabContentCreator.ListsNTreesPageFragment newInstance(int sectionNumber) {
            ListsNTreesTabContentCreator.ListsNTreesPageFragment fragment = new ListsNTreesTabContentCreator.ListsNTreesPageFragment();
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
                case 0: rootView = inflater.inflate(R.layout.lists_and_trees_page0_list, container, false);
                    ListsNTreesPage0Creator.createContent(rootView);
                    break;
                case 1: rootView = inflater.inflate(R.layout.lists_and_trees_page1_tree, container, false);
                    ListsNTreesPage1Creator.createContent(rootView);
                    break;
                case 2: rootView = inflater.inflate(R.layout.lists_and_trees_page2_grid, container, false);
                    break;// id_fragment = R.layout.subfragment_controls_3; break;
                case 3: rootView = inflater.inflate(R.layout.lists_and_trees_page3_cards, container, false);
                    break;// id_fragment = R.layout.subfragment_controls_3; break;
            }

            //View rootView = inflater.inflate(id_fragment, container, false);
            Log.d("fragment_inside", "sub fragment: " + getArguments().getInt(ARG_SECTION_NUMBER) + ", " + container);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }

    public static class ListsNTreePagerAdapter extends FragmentStatePagerAdapter {

        private static int COUNT = 3;
        private Context context;

        public ListsNTreePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return ListsNTreesTabContentCreator.ListsNTreesPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case 0: return context.getString(R.string.listsntree_page0);
                case 1: return context.getString(R.string.listsntree_page1);
                case 2: return context.getString(R.string.listsntree_page2);

            }

            return ("tab " + position);


        }

    }

}
