package com.icare_clinics.app;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by Satish.Babu on 12/30/2016.
 */

public class AutoScrollViewpager extends ViewPager {

    private int SCROLL_DURATION = 3500;
    private int TRANSITION_DELAY = 3000;
    private int scrolledPageCount=0;
    private int maxPagesCount = 0;
    private boolean isForward = true;
    private Handler handler;
    public boolean isStarted;

    public AutoScrollViewpager(Context context) {
        super(context);
        changePagerScroller(context);
        handler = new Handler();
    }

    public AutoScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        changePagerScroller(context);
        handler = new Handler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


    private void changePagerScroller(Context mContext) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(mContext);
            mScroller.set(this, scroller);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    /**This method is used to start the auto scrolling of pages in the pager*/
    public void startAutoScrollPager(final AutoScrollViewpager pager)
    {
        isStarted = true;
        maxPagesCount = this.getAdapter().getCount();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(scrolledPageCount < maxPagesCount  && isForward)
                {
                    scrolledPageCount++;
                    if(scrolledPageCount == maxPagesCount)
                    {
                        //isForward = false;
                        scrolledPageCount=0;
                        pager.setCurrentItem(scrolledPageCount, false);
                    }
                }
                else if(!isForward)
                {
                    scrolledPageCount--;
                    if(scrolledPageCount == 0)
                        isForward = true;

                }
                pager.setCurrentItem(scrolledPageCount, true);

                startAutoScrollPager(pager);
            }
        }, TRANSITION_DELAY);
    }

    class ViewPagerScroller extends Scroller {

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, SCROLL_DURATION);
        }
    }
}

