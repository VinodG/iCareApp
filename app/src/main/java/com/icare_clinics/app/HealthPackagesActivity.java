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
import com.icare_clinics.app.dataobject.VaccinationDo;
import com.icare_clinics.app.dataobject.WellnessPackageDo;
import com.icare_clinics.app.fragments.AboutPackageFragment;
import com.icare_clinics.app.fragments.AnteNatalPackagesFragmentNew;
import com.icare_clinics.app.fragments.WellnessPackagesFragmentNew;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HealthPackagesActivity extends BaseActivity {
    private LinearLayout llMain;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public LinkedHashMap<String, ArrayList<WellnessPackageDo>> wellnessPackageDos;

    public LinkedHashMap<String, ArrayList<VaccinationDo>> anteNatalDos;
    public LinkedHashMap<String, ArrayList<VaccinationDo>> vaccinationDos;

    @Override
    public void initialise() {
        llMain = (LinearLayout) inflater.inflate(R.layout.health_packages_layout, null);

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
        tvTitle.setText(getString(R.string.health_packages));
        initialiseControls();
        loadData();
    }

    @Override
    public void initialiseControls() {
        viewPager = (ViewPager) llMain.findViewById(R.id.viewPager);
        tabLayout = (TabLayout)llMain. findViewById(R.id.tabs);
    }

    @Override
    public void loadData() {
        showLoader(getString(R.string.Loading_Data));
        new Thread(new Runnable() {
            @Override
            public void run() {
                hideLoader();
                wellnessPackageDos = new ExtraDA(HealthPackagesActivity.this).getWellnessHealthPackages();
                anteNatalDos = new ExtraDA(HealthPackagesActivity.this).getAnteNatalHealthPackages();
                vaccinationDos = new ExtraDA(HealthPackagesActivity.this).getVaccinationHealthPackages();
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
//        WellnessPackagesFragment insCoverFragment = new WellnessPackagesFragment();
        WellnessPackagesFragmentNew insCoverFragment = new WellnessPackagesFragmentNew();
        Bundle photosBundle = new Bundle();
        photosBundle.putSerializable("Wellness", wellnessPackageDos);
        insCoverFragment.setArguments(photosBundle);

//        AnteNatalPackagesFragment insuAffiliatesFragment = new AnteNatalPackagesFragment();
        AnteNatalPackagesFragmentNew insuAffiliatesFragment = new AnteNatalPackagesFragmentNew();
        Bundle videoBundle = new Bundle();
        videoBundle.putSerializable("AnteNatal", anteNatalDos);
        insuAffiliatesFragment.setArguments(videoBundle);

//        AnteNatalPackagesFragment vacciFrag = new AnteNatalPackagesFragment();
        AnteNatalPackagesFragmentNew vacciFrag = new AnteNatalPackagesFragmentNew();
        Bundle vvideoBundle = new Bundle();
        vvideoBundle.putSerializable("Vaccination", vaccinationDos);
        vacciFrag.setArguments(vvideoBundle);

        AboutPackageFragment aboutPackageFrag =new AboutPackageFragment();
        adapter.addFragment(insCoverFragment,getString(R.string.wellness_pkg));
        adapter.addFragment(insuAffiliatesFragment, getString(R.string.antenatal_pkg));
        adapter.addFragment(vacciFrag, getString(R.string.vaccination_pkg));
        adapter.addFragment(aboutPackageFrag, getString(R.string.about_pkg));
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
        public Fragment getItem(int position)
        {
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
