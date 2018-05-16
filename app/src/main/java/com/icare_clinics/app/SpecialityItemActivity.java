package com.icare_clinics.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.adapter.SpecialityItemAdapter;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by srikrishna.nunna on 26-04-2017.
 */

public class SpecialityItemActivity extends BaseActivity{
    private LinearLayout llMain;
    private SpecializationDO specializationDO;
    private ImageView iv_clinics;
    private TextView tvAddress,tvWeekEnd,tvWorkingDays;
    private RecyclerView rvDoctors;
    private ClinicDO clinicDO;
    private Context context;
    public SpecialityItemAdapter tabAdapter;
    private ArrayList<DoctorDo> arrDoctor;
    @Override
    public void initialise() {
        context=SpecialityItemActivity.this;
        llMain = (LinearLayout) inflater.inflate(R.layout.speciality_item_activity, null);
        setTypeFaceNormal(llMain);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain, param);

        toolbar.setVisibility(View.GONE);

        View clinicToobar = inflater.inflate(R.layout.tool_bar, null);
        clinicToobar.setBackgroundColor(Color.TRANSPARENT);

        ivMenu = (ImageView) clinicToobar.findViewById(R.id.ivMenu);
        ivSearch = (ImageView) clinicToobar.findViewById(R.id.ivSearch);
        ivBack = (ImageView) clinicToobar.findViewById(R.id.ivBack);
        llBack = (LinearLayout) clinicToobar.findViewById(R.id.llBack);
        tvTitle = (TextView) clinicToobar.findViewById(R.id.tvTitle);
        tvTitle.setBackground(null);

//        ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setTextColor(Color.WHITE);
//        tvCancel.setVisibility(View.GONE);

        setStatusBarTransparent();
        LinearLayout llClinicToobar = (LinearLayout) findViewById(R.id.llClinicToobar);
        tvWeekEnd=(TextView) llMain.findViewById(R.id.tvWeekEnd);
        llClinicToobar.removeAllViews();
        llClinicToobar.addView(clinicToobar);

        String from="";
        if (getIntent().hasExtra("from")){
            from =getIntent().getStringExtra("from");
            if (from.equalsIgnoreCase("SpecialityTabAdapter"))
            {
                llBack.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
            }else
            {
                ivSearch.setVisibility(View.GONE);
                ivSearch.setBackgroundResource(R.drawable.dir);
                llBack.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            ivSearch.setVisibility(View.GONE);
            ivSearch.setBackgroundResource(R.drawable.dir);
            llBack.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
        }
        if(getIntent().hasExtra("specializationDO")){
            specializationDO = (SpecializationDO) getIntent().getSerializableExtra("specializationDO");
        }
        if(getIntent().hasExtra("clinicDO")){
            clinicDO = (ClinicDO) getIntent().getSerializableExtra("clinicDO");
        }
        initialiseControls();
        loadData();
        tabAdapter= new SpecialityItemAdapter(context,arrDoctor);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        rvDoctors.setLayoutManager(mLayoutManager);
        rvDoctors.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        rvDoctors.setItemAnimator(new DefaultItemAnimator());
        rvDoctors.setAdapter(tabAdapter);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initialiseControls() {

        iv_clinics=(ImageView) llMain.findViewById(R.id.iv_clinics);
        tvAddress=(TextView) llMain.findViewById(R.id.tvAddress);
        tvWorkingDays=(TextView) llMain.findViewById(R.id.tvWorkingDays);
        rvDoctors =(RecyclerView)llMain.findViewById(R.id.rvDoctors);

//        tvTitle.setText(clinicDO.description);
        tvTitle.setText( clinicDO.description +" >> "+specializationDO.name);
//        tvTitle.setTextSize(getResources().getDimension(R.dimen.text_size_very_small_8));
//        tvTitle.setText(clinicDO.description);
        tvAddress.setText(clinicDO.add1 + " " + clinicDO.add2 + " " + clinicDO.add3);
        tvWorkingDays.setText(StringUtils.fromHtml(clinicDO.timing));
        String imageurl = ServiceUrls.IMAGE_BASE_URL + specializationDO.SpecialtyBanner750x482;
        imageurl = imageurl.replaceAll(" ","%20");
        setClinicImage(imageurl,iv_clinics);

    }

    @Override
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DoctorDA doctorDA = new DoctorDA(SpecialityItemActivity.this);
                arrDoctor = doctorDA.getFilterDoctor(specializationDO.id,clinicDO.description);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arrDoctor!=null && arrDoctor.size()>0){
                            tabAdapter.refresh(arrDoctor);
                        }

                    }
                });

            }
        }).start();

    }
    public void setClinicImage(String imageurl, ImageView imageView) {
        if (!StringUtils.isEmpty(imageurl)) {
            int size = R.drawable.img1;
            BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
            int mHeight = bd.getBitmap().getHeight();
            int mWidth = bd.getBitmap().getWidth();

            LogUtils.errorLog("Image Url:", imageurl);
            Picasso.with(context).load(imageurl)
                    .resize(mWidth, mHeight)
                    .error(size)
                    .into(imageView);
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
