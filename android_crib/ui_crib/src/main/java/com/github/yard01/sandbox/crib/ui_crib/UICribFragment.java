package com.github.yard01.sandbox.crib.ui_crib;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui_crib.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UICribFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UICribFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class UICribFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int TAB_COUNT = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static TabContentCreator tabcc;
    /*private OnFragmentInteractionListener mListener;*/



    public UICribFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UICribFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UICribFragment newInstance(String param1, String param2) {
        UICribFragment fragment = new UICribFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        //mViewPager.addOnP

        // Set up the ViewPager with the sections adapter.

        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager)); // TabLayout-у мы установили обработчик событий на перелистывание ViewPager
        //чтобы при перелистывании ViewPager переключались бы и вкладки на TabLayout

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
            //TabContentCreator tcc = (TabContentCreator)vp.getTag();
            //tcc.createContent(tab.getPosition());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_uicrib, container, false);
        //if (true) return;
        //result.findViewById()
        //result

        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getActivity().getSupportFragmentManager()); //создали адптер страниц ViewPager

        TabLayout tabLayout = (TabLayout) result.findViewById(R.id.ui_crib_tabs); //нашли TabLayout (закладки)
        mViewPager = (ViewPager) result.findViewById(R.id.ui_crib_container); // нашли наш ViewPager
        //mViewPager.setTag(new );

        mViewPager.setAdapter(mSectionsPagerAdapter); //установили ViewPager-у адпатер страниц

        mViewPager.addOnPageChangeListener(new PageChangeListener(tabLayout));//TabLayout.TabLayoutOnPageChangeListener(tabLayout)); // ViewPager-у мы установили обработчик событий на переключение вкладок
        //чтобы при перелистывании ViewPager переключались бы и вкладки на TabLayout

        mViewPager.setTag(this.getActivity());

        tabLayout.addOnTabSelectedListener(new TabSelectedListener(mViewPager));//TabLayout.ViewPagerOnTabSelectedListener(mViewPager)); // TabLayout-у мы установили обработчик событий на перелистывание ViewPager
        //чтобы при переключении вкладок перелистывался бы и ViewPager


        return result;//inflater.inflate(R.layout.fragment_uicrib, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
     * наш ViewPager размещен на fragment_uicrib
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
