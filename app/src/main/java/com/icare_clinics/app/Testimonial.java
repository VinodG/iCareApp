package com.icare_clinics.app;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.adapter.TestimonialAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.AboutDo;

import java.util.ArrayList;

public class Testimonial extends BaseActivity {
  private RecyclerView mRecyclerView;
  private TestimonialAdapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  //List<ClinicsDO> list = new ArrayList<>();
  private ArrayList<AboutDo> arrTestimonial;
  LinearLayout lLMain;
  AppConstants appConstants;
    @Override
    public void initialise() {
      lLMain = (LinearLayout) inflater.inflate(R.layout.activity_testimonial, null);

      LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
      llBody.addView(lLMain, param);
      ivMenu.setVisibility(View.GONE);
      tvTitle.setVisibility(View.VISIBLE);
      tvTitle.setBackground(null);
      tvCancel.setVisibility(View.GONE);
      llBack.setVisibility(View.VISIBLE);
      ivBack.setVisibility(View.VISIBLE);
      tvTitle.setText("Testimonial");
      tvTitle.setTextColor(Color.WHITE);
      setStatusBarColor();
      mRecyclerView = (RecyclerView) lLMain.findViewById(R.id.rvTestimonial);
      mRecyclerView.setHasFixedSize(true);
      mAdapter = new TestimonialAdapter(Testimonial.this,arrTestimonial);
      mLayoutManager = new LinearLayoutManager(getApplicationContext());
      mRecyclerView.setLayoutManager(mLayoutManager);
      mRecyclerView.setAdapter(mAdapter);

      loadData();
    }

    @Override
    public void initialiseControls() {

    }

    @Override
    public void loadData() {
      ExtraDA extraDA=new ExtraDA(Testimonial.this);
      arrTestimonial=extraDA.getAbout();
      if (arrTestimonial != null && arrTestimonial.size() > 0)
        mAdapter.refresh(arrTestimonial);

    }
}
