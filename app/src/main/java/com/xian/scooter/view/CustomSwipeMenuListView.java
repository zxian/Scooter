package com.xian.scooter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.baoyz.swipemenulistview.SwipeMenuListView;

public class CustomSwipeMenuListView extends SwipeMenuListView {

    public CustomSwipeMenuListView(Context context) {
        super(context);
    }

    public CustomSwipeMenuListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwipeMenuListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}

