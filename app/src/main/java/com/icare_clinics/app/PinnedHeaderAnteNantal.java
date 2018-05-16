package com.icare_clinics.app;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.icare_clinics.app.fragments.AnteNatalPackagesFragmentNew;
import com.icare_clinics.app.fragments.WellnessPackagesFragmentNew;

/**
 * Created by Sreekanth.P on 01-09-2017.
 */

public class PinnedHeaderAnteNantal extends ExpandableListView{

    public interface PinnedHeaderAdapter {


        public static final int PINNED_HEADER_GONE = 0;


        public static final int PINNED_HEADER_VISIBLE = 1;

        public static final int PINNED_HEADER_PUSHED_UP = 2;

        void configurePinnedHeader(View header, int position, int alpha, int state);
    }

    private static final int MAX_ALPHA = 255;

    private AnteNatalPackagesFragmentNew.AnteNantalAdapter mAdapter;
    private View mHeaderView;
    private boolean mHeaderViewVisible;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    private String from;

    public PinnedHeaderAnteNantal(Context context) {
        super(context);
    }

    public PinnedHeaderAnteNantal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedHeaderAnteNantal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPinnedHeaderView(View view) {
        mHeaderView = view;

        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (AnteNatalPackagesFragmentNew.AnteNantalAdapter) adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            configureHeaderView(getFirstVisiblePosition());
        }
    }

    public void configureHeaderView(int position) {
        final int group = getPackedPositionGroup(getExpandableListPosition(position));
        int groupView = getFlatListPosition(getPackedPositionForGroup(group));

        if (mHeaderView == null) {
            return;
        }

        mHeaderView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View header) {
                if(!expandGroup(group)) collapseGroup(group);
            }
        });

        int state,nextSectionPosition = getFlatListPosition(getPackedPositionForGroup(group+1));

        if (mAdapter.getGroupCount()== 0) {
            state = PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }else if (position < 0) {
            state = PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }else if (nextSectionPosition != -1 && position == nextSectionPosition - 1) {
            state= PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP;
        }else  state= PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;

        switch (state) {
            case PinnedHeaderAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }

            case PinnedHeaderAdapter.PINNED_HEADER_VISIBLE: {
                mAdapter.configurePinnedHeader(mHeaderView, group, MAX_ALPHA,state);
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderViewVisible = true;
                break;
            }

            case PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                if(firstView==null){
                    if (mHeaderView.getTop() != 0) {
                        mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                    }
                    mHeaderViewVisible = true;
                    break;
                }
                int bottom = firstView.getBottom();
                int itemHeight = firstView.getHeight();
                int headerHeight = mHeaderView.getHeight();
                int y;
                int alpha;
                if (bottom < headerHeight) {
                    y = (bottom - headerHeight);
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }
                mAdapter.configurePinnedHeader(mHeaderView, group, alpha,state);
                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }
                mHeaderViewVisible = true;
                break;
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }
}
