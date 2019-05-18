package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page0;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ui_crib.R;

public class ListsNTreesPage0Creator {

    public static int ITEM_COUNT = 3;
    private static String[] writers = null;

    public static class LAdapter implements ListAdapter {

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return ITEM_COUNT;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //LayoutInflater.
            View view = LayoutInflater.from(parent.getContext()) //в родительское окно встраивается View с LinearLayout, на котором лежат визуальные элементы строки
                    .inflate(R.layout.lists_and_trees_listrow, parent, false);
            TextView tv = view.findViewById(R.id.lists_and_trees_page0_row_text);
            tv.setText(writers[position]);
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            //должен возвращать варианта отображения строки в зависимости от позиции
            return 1;
        }

        @Override
        public int getViewTypeCount() {
            //количество возможных вариантов отображения строки списка
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public static void createContent(final View view) {
        ListView lv = view.findViewById(R.id.lists_and_trees_listview);
        writers = view.getContext().getResources().getStringArray(R.array.lists_and_trees_writers);
        ITEM_COUNT = writers.length;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.lists_and_trees_textrow, writers);
        lv.setAdapter(adapter);//new LAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Log.d("listview", "" + view);

            }
        });

    }
}
