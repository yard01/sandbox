package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page1;

import android.view.View;
import android.widget.ExpandableListView;

import com.example.ui_crib.R;

public class ListsNTreesPage1Creator {

    public static void createContent(final View view) {
        ExpandableListView expandableListView = view.findViewById(R.id.lists_and_trees_expandableview);
        expandableListView.setAdapter(new WriterAdapter());
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupExpandListener(new NodeAutocollapser(expandableListView));
    }
}
