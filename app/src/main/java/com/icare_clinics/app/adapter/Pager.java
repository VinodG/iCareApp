package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.icare_clinics.app.DoctorsFragment;
import com.icare_clinics.app.SpecialitiesFragment;

/**
 * Created by Mallikarjuna.K on 27-Dec-16.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabcnt;
    Context context;
    public Pager(FragmentManager fm, int tabCount,Context context)
    {
        super(fm);
        this.tabcnt=tabCount;
        this.context= context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DoctorsFragment tab1 =new DoctorsFragment();
                return  tab1;
            case 1:
                SpecialitiesFragment tab2 =new SpecialitiesFragment();
                return  tab2;


            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcnt;
    }
}
