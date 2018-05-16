package com.icare_clinics.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;
import com.icare_clinics.app.adapter.ViewPagerAdapt;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.NewsDo;

import java.util.ArrayList;
import java.util.Collections;

public class WhatsNewActivity extends BaseActivity
{
    LinearLayout lLMain;

    private TabLayout tabLayout;
    ViewPagerAdapt adapter;
    private ViewPager viewPager;
    int dataSelection=3;  // for Testimonial fragment and its arraylist in Viewpager Adapter
    private ArrayList<NewsDo> arrNews;
    CirclePageIndicator vp_dashboard_circle;

    @Override
    public void initialise()
    {
        lLMain = (LinearLayout) inflater.inflate(R.layout.activity_whats_new, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);
        initialiseControls();
        loadData();

        toolbar.setVisibility(View.GONE);
        setStatusBarColor();
        iv_fab.setVisibility(View.GONE);
        viewPager.setPageMargin((int) (-1 * px) / 3);
        Log.d("margin", "  : " + (px) + "  : ");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);
        adapter = new ViewPagerAdapt(getSupportFragmentManager(), arrNews, WhatsNewActivity.this,dataSelection);
        viewPager.setPageMargin((int) (-1 * px) / 3);
        Log.d("margin", "  : " + (px) + "  : ");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View view, float position) {
                float scaleFactor = 0.0f;

                if (position < -1) {
                    view.setAlpha(0);
                } else if (position <= 1) {
                    scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    view.setAlpha(scaleFactor);
                } else {
                    view.setAlpha(0);
                }
            }
        });
        viewPager.setAdapter(adapter);
        vp_dashboard_circle.setViewPager(viewPager);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = preference.getStringFromPreference(Preference.LANGUAGE,"");
                if(language.equalsIgnoreCase("en") ){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }else
                {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    public void initialiseControls()
    {
        tabLayout = (TabLayout) lLMain.findViewById(R.id.tablayout);
        viewPager = (ViewPager) lLMain.findViewById(R.id.pager);
        ivMenu = (ImageView) lLMain.findViewById(R.id.ivBackImage);
        vp_dashboard_circle = (CirclePageIndicator) lLMain.findViewById(R.id.vp_dashboard_circle);
    }

    @Override
    public void loadData()
    {
        ExtraDA extraDA=new ExtraDA(WhatsNewActivity.this);
        arrNews=extraDA.getNews();
        if (arrNews!=null && arrNews.size()>0){
            Collections.reverse(arrNews);
//            adapter.refresh(arrNews);
        }
    }
}
