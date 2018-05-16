package com.icare_clinics.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.fragments.AboutIcareFragment;

import java.util.ArrayList;
import java.util.List;

public class AboutIcareActivityNew extends BaseActivity {


    private LinearLayout llMain;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ArrayList<AboutDo> arrAbout = new ArrayList<AboutDo>();
    @Override
    public void initialise() {
        llMain=(LinearLayout) inflater.inflate(R.layout.activity_about_icare_new,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        llBody.addView(llMain,param);
        setTypeFaceUbuntuRegular(llMain);
        ivMenu.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        //ivCancel.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        tvTitle.setText(R.string.about_iCare);
        setStatusBarColor();

        initialiseControls();
        loadData();
    }

    @Override
    public void initialiseControls() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    public void loadData() {
        showLoader(getString(R.string.Loading_Data));
        new Thread(new Runnable() {
            @Override
            public void run() {
                ExtraDA extraDA = new ExtraDA(AboutIcareActivityNew.this);
                arrAbout=extraDA.getAbout();
                hideLoader();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager(viewPager);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                });
            }
        }).start();

    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addTitle("About iCARE");
        adapter.addTitle("Vision & Mission");
        adapter.addTitle(getString(R.string.message_from_coo));
        viewPager.setAdapter(adapter);
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public void addTitle(String strTitle)
        {
            mFragmentTitleList.add(strTitle);
        }


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

    /*    public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }*/

        @Override
        public Fragment getItem(int position)
        {
            AboutIcareFragment aboutIcareFragment = new AboutIcareFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ABOUT", arrAbout);
            bundle.putInt("TAB_POSITION",position);

            aboutIcareFragment.setArguments(bundle);

            return aboutIcareFragment;
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }

      /*  public void findList() {
            for (String i : mFragmentTitleList) {
                Log.d("mtitle", "" + i);
            }
            for (Fragment i : mFragmentList) {
                Log.d("mfragment", "" + i);
            }

        }*/
    }
}
