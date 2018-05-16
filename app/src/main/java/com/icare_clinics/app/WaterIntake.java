package com.icare_clinics.app;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.icare_clinics.app.fragments.WaterIntakeWeeklyFragment;

public class WaterIntake extends BaseActivity {
    private RelativeLayout llMain;
    private FloatingActionButton fab_setReminder,fab_setTarget,fab_addDrink,floatingActionButton;
    @Override
    public void initialise() {
        llMain=(RelativeLayout) inflater.inflate(R.layout.activity_water_intake,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain,param);
        iv_fab.setVisibility(View.GONE);

        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText("Water Intake");
        setStatusBarColor();
        initialiseControls();

    }

    @Override
    public void initialiseControls() {
        fab_setReminder=(FloatingActionButton)llMain.findViewById(R.id.fab_setReminder);
        fab_setTarget=(FloatingActionButton)llMain.findViewById(R.id.fab_setTarget);
        fab_addDrink=(FloatingActionButton)llMain.findViewById(R.id.fab_addDrink);
        floatingActionButton=(FloatingActionButton)llMain.findViewById(R.id.floatingActionButton);

        final LinearLayout llsetReminder=(LinearLayout)llMain.findViewById(R.id.llsetReminder);
        final LinearLayout llSetTarget=(LinearLayout)llMain.findViewById(R.id.llSetTarget);
        final LinearLayout llAddDrink=(LinearLayout)llMain.findViewById(R.id.llAddDrink);

        final Animation mShowButton= AnimationUtils.loadAnimation(WaterIntake.this,R.anim.show_button);
        final Animation mHideButton= AnimationUtils.loadAnimation(WaterIntake.this,R.anim.hide_button);
        final Animation mHideLayout= AnimationUtils.loadAnimation(WaterIntake.this,R.anim.hide_layout);
        final Animation mShowLayout= AnimationUtils.loadAnimation(WaterIntake.this,R.anim.show_layout);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fab_addDrink.getVisibility()==View.VISIBLE && fab_setReminder.getVisibility()==View.VISIBLE && fab_setTarget.getVisibility()==View.VISIBLE){
                    fab_addDrink.setVisibility(View.GONE);
                    fab_setTarget.setVisibility(View.GONE);
                    fab_setReminder.setVisibility(View.GONE);

                    llsetReminder.setVisibility(View.GONE);
                    llSetTarget.setVisibility(View.GONE);
                    llAddDrink.setVisibility(View.GONE);

                    llsetReminder.startAnimation(mHideLayout);
                    llSetTarget.startAnimation(mHideLayout);
                    llAddDrink.startAnimation(mHideLayout);
                    floatingActionButton.startAnimation(mHideButton);

                }else{
                    fab_addDrink.setVisibility(View.VISIBLE);
                    fab_setTarget.setVisibility(View.VISIBLE);
                    fab_setReminder.setVisibility(View.VISIBLE);

                    llsetReminder.startAnimation(mShowLayout);
                    llSetTarget.startAnimation(mShowLayout);
                    llAddDrink.startAnimation(mShowLayout);

                    llsetReminder.setVisibility(View.VISIBLE);
                    llSetTarget.setVisibility(View.VISIBLE);
                    llAddDrink.setVisibility(View.VISIBLE);
                    floatingActionButton.startAnimation(mShowButton);
                }
            }
        });
    }

    @Override
    public void loadData() {

    }
}
