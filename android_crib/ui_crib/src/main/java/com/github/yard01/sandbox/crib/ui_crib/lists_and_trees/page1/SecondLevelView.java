package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page1;

import android.content.Context;
import android.widget.ExpandableListView;

public class SecondLevelView extends ExpandableListView {

    public SecondLevelView(Context context) {
        super(context);
    }
//////////////////////////////////////////////////
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
//////////////////////////////////////////////////
}

