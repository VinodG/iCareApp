package com.icare_clinics.app.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Baliram.Kumar on 19-06-2017.
 */

public class ExpandableHeightRecyclerView extends RecyclerView {
    private boolean isExpandHeight = true;


    public ExpandableHeightRecyclerView(Context context){
        super(context);
    }

    public ExpandableHeightRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandableHeightRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if(isExpandHeight) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
            setNestedScrollingEnabled(false);
            setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public boolean isExpandHeight() {
        return isExpandHeight;
    }

    public void setExpandHeight(boolean expandHeight) {
        isExpandHeight = expandHeight;
    }
}