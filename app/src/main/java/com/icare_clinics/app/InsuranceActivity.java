package com.icare_clinics.app;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.fragments.InsuranceAffiliatesFragment;
import com.icare_clinics.app.fragments.InsuranceCoveredFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InsuranceActivity extends BaseActivity {
    private LinearLayout llMain;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HashMap<String,ArrayList<InsuranceDo>> hmInsurance;

    @Override
    public void initialise() {
        llMain = (LinearLayout) inflater.inflate(R.layout.insurance_layout, null);

        setStatusBarColor();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain, param);
        setTypeFaceUbuntuRegular(llMain);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        llBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.insurance));
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
                hideLoader();
                hmInsurance = new ExtraDA(InsuranceActivity.this).getInsuranceDetails();
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
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        InsuranceCoveredFragment insCoverFragment = new InsuranceCoveredFragment();
        Bundle photosBundle = new Bundle();
        photosBundle.putSerializable("coveredInsurance",hmInsurance.get("coveredInsurance"));
        insCoverFragment.setArguments(photosBundle);

        InsuranceAffiliatesFragment insuAffiliatesFragment = new InsuranceAffiliatesFragment();
        Bundle videoBundle = new Bundle();
        videoBundle.putSerializable("affiliatesInsurance",hmInsurance.get("affiliatesInsurance"));
        insuAffiliatesFragment.setArguments(videoBundle);

        adapter.addFragment(insCoverFragment,getString(R.string.insurance_cover));
        adapter.addFragment(insuAffiliatesFragment, getString(R.string.insurance_third_party));
        viewPager.setAdapter(adapter);
        adapter.findList();
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void findList() {
            for (String i : mFragmentTitleList) {
                Log.d("mtitle", "" + i);
            }
            for (Fragment i : mFragmentList) {
                Log.d("mfragment", "" + i);
            }

        }
    }
}
