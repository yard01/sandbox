package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page1;

import android.widget.ExpandableListView;

public class NodeAutocollapser implements ExpandableListView.OnGroupExpandListener {
    private ExpandableListView _expandableListView;
    int previousGroup = -1;

    public NodeAutocollapser(ExpandableListView expandableListView) {
        _expandableListView = expandableListView;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (groupPosition != previousGroup)
            _expandableListView.collapseGroup(previousGroup);
        previousGroup = groupPosition;
    }
}
