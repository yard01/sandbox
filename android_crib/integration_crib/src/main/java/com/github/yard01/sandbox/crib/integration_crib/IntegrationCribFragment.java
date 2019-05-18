package com.github.yard01.sandbox.crib.integration_crib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.integration_crib.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class IntegrationCribFragment extends Fragment {
    public static final int TAB_COUNT = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_integration, container, false);
        //if (true) return;
        //result.findViewById()
        //result

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getActivity().getSupportFragmentManager()); //создали адптер страниц ViewPager

        TabLayout tabLayout = (TabLayout) result.findViewById(R.id.integration_tabs); //нашли TabLayout (закладки)
        ViewPager viewPager = (ViewPager) result.findViewById(R.id.integration_container); // нашли наш ViewPager
        //mViewPager.setTag(new );

        viewPager.setAdapter(sectionsPagerAdapter); //установили ViewPager-у адпатер страниц

        viewPager.addOnPageChangeListener(new PageChangeListener(tabLayout));//TabLayout.TabLayoutOnPageChangeListener(tabLayout)); // ViewPager-у мы установили обработчик событий на переключение вкладок
        //чтобы при перелистывании ViewPager переключались бы и вкладки на TabLayout

        viewPager.setTag(this.getActivity());

        tabLayout.addOnTabSelectedListener(new TabSelectedListener(viewPager));//TabLayout.ViewPagerOnTabSelectedListener(mViewPager)); // TabLayout-у мы установили обработчик событий на перелистывание ViewPager
        //чтобы при переключении вкладок перелистывался бы и ViewPager


        return result;//inflater.inflate(R.layout.fragment_uicrib, container, false);

    }

    class PageChangeListener extends TabLayout.TabLayoutOnPageChangeListener {
        //ViewPager vpgr;
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

        }

        public PageChangeListener(TabLayout tabLayout) {
            super(tabLayout);
        }

    }

    class TabSelectedListener extends  TabLayout.ViewPagerOnTabSelectedListener {
        final ViewPager vp;
        public TabSelectedListener(ViewPager viewPager) {
            super(viewPager);
            vp = viewPager;
            //vp.get
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            super.onTabSelected(tab);

            TabContentCreator.createContent((FragmentActivity) vp.getTag(), tab.getPosition());

        }
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return TAB_COUNT;//PlaceholderFragment.adapter_classes.length + 1;
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     * Этот фрагмент вставляется в container которым является ViewPager,
     * наш ViewPager размещен на fragment_integation
     * Фрагмент PlaceholderFragment создается в SectionsPagerAdapter
     * всякий раз при вызове getItem() (перелистывание)
     *
     * В конструкторе этого фрагмента должны быть установлены и адаптеры для подфрагментов
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static boolean isCreated = false;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;

        }

        //Отображение содержимого фрагмента
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            return TabContentCreator.inflateContent(this.getActivity().getSupportFragmentManager(), inflater, container, sectionNumber);

        }


    }
}