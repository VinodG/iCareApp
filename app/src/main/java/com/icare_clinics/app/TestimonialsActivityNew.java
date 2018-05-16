package com.icare_clinics.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.icare_clinics.app.adapter.ViewPagerAdapt;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.TestimonialDo;

import java.util.ArrayList;
import java.util.Collections;

public class TestimonialsActivityNew extends BaseActivity
{
    LinearLayout lLMain;

    private TabLayout tabLayout;
    ViewPagerAdapt adapter;
    private ViewPager viewPager;
    private TextView tvNumOfPages;
    int dataSelection=2;  // for Testimonial fragment and its arraylist in Viewpager Adapter
    ArrayList <TestimonialDo> arrayTextimonical;
    private ArrayList<TestimonialDo> arrTestimonial;
    CirclePageIndicator vp_dashboard_circle;


    @Override
    public void initialise()
    {
        lLMain = (LinearLayout) inflater.inflate(R.layout.activity_testimonials_new, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);
        initialiseControls();
        loadData();
      /*  if(arrTestimonial!=null&&arrTestimonial.size()>0){
            tvNumOfPages.setText("1 of " + arrTestimonial.size());
        }*/
        toolbar.setVisibility(View.GONE);
        setStatusBarColor();
        iv_fab.setVisibility(View.GONE);
        viewPager.setPageMargin((int) (-1 * px) / 3);
        Log.d("margin", "  : " + (px) + "  : ");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);
        adapter = new ViewPagerAdapt(getSupportFragmentManager(), arrTestimonial, TestimonialsActivityNew.this,dataSelection);
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
        // tvNumOfPages = (TextView) lLMain.findViewById(R.id.tvNumOfPages);
        vp_dashboard_circle = (CirclePageIndicator) lLMain.findViewById(R.id.vp_dashboard_circle);
        // tvNumOfPages.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadData()
    {
        ExtraDA extraDA=new ExtraDA(TestimonialsActivityNew.this);
        arrTestimonial=extraDA.getTestimonial();
        if (arrTestimonial!=null && arrTestimonial.size()>0){
            Collections.reverse(arrTestimonial);
//            adapter.refresh(arrTestimonial);
        }
    }
}
