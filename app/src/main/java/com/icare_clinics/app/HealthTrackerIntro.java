package com.icare_clinics.app;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HealthTrackerIntro extends BaseActivity {
LinearLayout llProfileSetup,llGuestBmi;
    RelativeLayout llMain;

    @Override
    public void initialise() {
        llMain = (RelativeLayout) inflater.inflate(R.layout.activity_health_tracker_intro, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain, param);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText("Health Tracker Intro");
        ivMenu.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);

        setStatusBarColor();
        initialiseControls();
    }

    @Override
    public void initialiseControls() {
        llProfileSetup=(LinearLayout)llMain.findViewById(R.id.llProfileSetup);
        llGuestBmi=(LinearLayout)llMain.findViewById(R.id.llGuestBmi);
        llProfileSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthTrackerIntro.this,ProfileSetupActivityNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from","HealthTrackerIntro");
                startActivity(intent);
            }
        });
        llGuestBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthTrackerIntro.this,BMICalculator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("BMIDATASET",true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {

    }
}
