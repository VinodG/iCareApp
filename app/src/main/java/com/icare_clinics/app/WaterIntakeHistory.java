package com.icare_clinics.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.adapter.ViewPagerAdapterWaterIntake;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.fragments.WaterIntakeMonthlyFragment;
import com.icare_clinics.app.fragments.WaterIntakeWeeklyFragment;
import com.icare_clinics.app.fragments.WaterIntakeYearlyFragment;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sreekanth.P on 22-06-2017.
 */

public class WaterIntakeHistory extends BaseActivity implements TabLayout.OnTabSelectedListener{

    private LinearLayout llMain;
    private ViewPager vp;
    private TabLayout tabLayout;
    private CircularProgressBar cpbDay, cpbAvg;
    private TextView tvDayPer, tvDayml, tvAvgPer, tvAvgml, tvMonthAvg, tvDay;
    public static ViewPagerAdapterWaterIntake viewPagerAdapter;
    private WaterIntakeWeeklyFragment fragmentWeekly;//fragmentMonthly,fragmentYearly;
    private WaterIntakeMonthlyFragment fragmentMonthly;
    private WaterIntakeYearlyFragment fragmentYearly;
    public  Calendar pagerCalendar;
    public USerDataDA uSerDataDA;
    private ArrayList<WaterDO> arrTemp;
    private USerDataDA mUserDa;
    private SettingDO settingDO;
    private ArrayList<WaterDO> arrGraph;
    private String SelecetdYear ="";
    private String SelectedMonth = "";
    private boolean isYearSelected,isMonthSelected;
    private Bundle Mainbundle;
    private Bundle DummyBundle;
    public Calendar startCal=Calendar.getInstance();

    @Override
    public void initialise() {
        llMain = (LinearLayout) inflater.inflate(R.layout.activity_water_intake_graph, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain, layoutParams);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(R.string.water_intake);
        ivMenu.setVisibility(View.GONE);
        iv_fab.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        setStatusBarColor();
        initialiseControls();

        arrTemp = new ArrayList<WaterDO>();
        mUserDa = new USerDataDA(WaterIntakeHistory.this);
        loadData();
        DummyBundle = new Bundle();
        DummyBundle.putString("Demo","Demo");
        uSerDataDA = new USerDataDA(WaterIntakeHistory.this);

        viewPagerAdapter = new ViewPagerAdapterWaterIntake(WaterIntakeHistory.this,getSupportFragmentManager(),startCal);
        vp.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setOnTabSelectedListener(this);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void initialiseControls() {

        vp = (ViewPager) llMain.findViewById(R.id.vp);
        tabLayout = (TabLayout) llMain.findViewById(R.id.tabLayout);
        cpbDay = (CircularProgressBar) llMain.findViewById(R.id.cpbDay);
        cpbAvg = (CircularProgressBar) llMain.findViewById(R.id.cpbAvg);
        tvDayPer = (TextView) llMain.findViewById(R.id.tvDayPer);
        tvDayml = (TextView) llMain.findViewById(R.id.tvDayml);
        tvAvgPer = (TextView) llMain.findViewById(R.id.tvAvgPer);
        tvAvgml = (TextView) llMain.findViewById(R.id.tvAvgml);
        tvMonthAvg = (TextView) llMain.findViewById(R.id.tvAvg);
        tvDay = (TextView) llMain.findViewById(R.id.tvDay);
    }

    @Override
    public void loadData() {

        int strConsumedWater = 0, strTargetWater = 0, rlHeight, per = 0;
        String strTimeOfIntake = "";
        String date = CalendarUtil.getDate();
        arrTemp = mUserDa.getWaterData(date);

        if (arrTemp == null) {
            arrTemp = new ArrayList<>();
        }
        if (arrTemp != null && arrTemp.size() > 0) {
            for (int i = 0; i < arrTemp.size(); i++) {
                strConsumedWater += Integer.valueOf(arrTemp.get(i).strWater);
            }
        }
        pagerCalendar = Calendar.getInstance();
        settingDO = new SettingDO();
        String strDate = CalendarUtil.getPresentDate();
        settingDO = mUserDa.getSettings(strDate, AppConstants.WATER_INTAKE);
        if (settingDO.entityName.equalsIgnoreCase(AppConstants.WATER_INTAKE)) {
            strTargetWater = Integer.valueOf(settingDO.value1);//for target Water

            per = calculatePercentage(strConsumedWater, strTargetWater);

            String strPer = String.valueOf(per);
            tvDayPer.setText(strPer + " %");
            tvDayml.setText("ML"+ "\n"+strTargetWater);

            setProgressBar(per);
        }

        ////////////////gettting data for Month target //////////////////

        int targetWater=0,consumedWater=0;
        targetWater= mUserDa.getTargetWater(strDate, AppConstants.WATER_INTAKE);

        String startDate=CalendarUtil.getFirstDayOfMonth();
        String endDate=CalendarUtil.getPresentDate();
        String[]str=endDate.split("-");
        String strMonth="";

        consumedWater =mUserDa.getConsumedWater(startDate, endDate);
        if (targetWater>0){
            per=0;
            per = calculatePercentage(consumedWater, targetWater);

            String strPer = String.valueOf(per);
            tvAvgPer.setText(strPer + " %");
            tvAvgml.setText("ML"+ "\n"+targetWater);

            cpbAvg.setProgressWithAnimation(per, 2000);

            strMonth= getMonth(Integer.valueOf(str[1])-1);
            tvMonthAvg.setText(strMonth+" Avg.");
        }
    }

    private void setProgressBar(int per) {
        cpbDay.setProgressWithAnimation(per, 2000); //2000 animation time
    }

    private int calculatePercentage(int temp, int targetWater) {
        int per = 0;
        if (targetWater > 0) {
            per = (int) Math.round(temp * 100.0 / targetWater);
        }
        return per;
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];  }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}