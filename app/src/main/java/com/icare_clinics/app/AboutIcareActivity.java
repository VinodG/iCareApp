package com.icare_clinics.app;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.icare_clinics.app.adapter.AboutUslist;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by Satish.Babu on 1/2/2017.
 */

public class AboutIcareActivity extends BaseActivity{

    private LinearLayout llmain;
    public AutoScrollViewpager viewpager;
    private AboutUslist aboutUslist;
    private CirclePageIndicator pageIndicator;
    private ImageView ivAboutIcare;
    private TextView tvabout,tvabout2,tvHeader;

   // private ArrayList<AboutDo> arrAbout;
    ArrayList<AboutDo> arrAbout = new ArrayList<AboutDo>();

//    public int img[]={R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};
    @Override
    public void initialise() {

        llmain=(LinearLayout) inflater.inflate(R.layout.activity_about_us,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        llBody.addView(llmain,param);
        ivMenu.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        //ivCancel.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);

        tvTitle.setText("About iCare");
       // viewpager=(AutoScrollViewpager) llmain.findViewById(R.id.vp_pager);
       // pageIndicator=(CirclePageIndicator)llmain.findViewById(R.id.circle_pagein);


      /*  aboutUslist=new AboutUslist(this,img);
        viewpager.setAdapter(aboutUslist);
        viewpager.setCurrentItem(0);
        pageIndicator.setViewPager(viewpager);
        viewpager.startAutoScrollPager(viewpager);
*/
        initialiseControls();
        setStatusBarColor();
    }

    @Override
    public void initialiseControls() {
        ivAboutIcare=(ImageView)llmain.findViewById(R.id.ivAboutIcare);
        tvabout=(TextView)llmain.findViewById(R.id.tvabout);
        tvabout2=(TextView)llmain.findViewById(R.id.tvabout2);
        tvHeader=(TextView)llmain.findViewById(R.id.tvHeader);
        loadData();
    }

    @Override
    public void loadData() {
        ExtraDA extraDA = new ExtraDA(AboutIcareActivity.this);
        arrAbout=extraDA.getAbout();
        if(arrAbout!=null && arrAbout.size()>0)
        {
           for (int i=0;i<arrAbout.size();i++)
           {
               AboutDo aboutDo=new AboutDo();
               aboutDo=arrAbout.get(i);
               imageurl= ServiceUrls.IMAGE_BASE_URL+aboutDo.bh_photo;
               tvabout.setText(StringUtils.fromHtml(aboutDo.bh_msg));
               if(!StringUtils.isEmpty(imageurl)){
                   Picasso.with(AboutIcareActivity.this).load(imageurl)
                           .into(ivAboutIcare);
               }else{
                   //image not found
               }
           }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
