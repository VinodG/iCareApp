package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.icare_clinics.app.fragments.WaterIntakeMonthlyFragment;
import com.icare_clinics.app.fragments.WaterIntakeWeeklyFragment;
import com.icare_clinics.app.fragments.WaterIntakeYearlyFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sreekanth.P on 22-06-2017.
 */

public class ViewPagerAdapterWaterIntake extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private String[] tabTitles = new String[]{"WEEKLY", "MONTHLY", "YEARLY"};
    public Calendar startCal;
    private Context context;

    public ViewPagerAdapterWaterIntake(Context context, FragmentManager fm , Calendar startCal) {
        super(fm);
        this.context=context;
        this.startCal=startCal;
    }

    public void Refresh(){
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                WaterIntakeWeeklyFragment weeklyFragment=new WaterIntakeWeeklyFragment(context,0,startCal);
                return weeklyFragment;
            case 1:
                WaterIntakeMonthlyFragment monthlyFragment=new WaterIntakeMonthlyFragment(context,1,startCal);
                return monthlyFragment;
            case 2:
                WaterIntakeYearlyFragment yearlyFragment=new WaterIntakeYearlyFragment(context,2,startCal);
                return yearlyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
