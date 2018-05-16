package com.icare_clinics.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.fragments.ClinicFragment;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mallikarjuna.K on 27-Dec-16.
 */

public class ClinicDetailsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    TabLayout tabLayout;
    ViewPager pager;
    TextView tvHplName, tvAddress, tvWorkingDays, tvWeekEnd;
    RelativeLayout rlHospital;
    int ivHospital;
    LinearLayout lLMain;
    private ClinicDO clinicDo;
    private SpecializationDO specializationDO;
    private ArrayList<DoctorDo> arrDoctor;
    private ArrayList<String> arrDoctorService,arrDoctorLocation;
    private ArrayList<SpecializationDO> arrSpeciality;
    private ArrayList<ClinicDO> arrClinics;
    private ViewPagerAdapter viewPagerAdapter;
    private DoctorsFragment doctorsFragment;
    private ClinicFragment clinicFragment;
    private SpecialitiesFragment specialitiesFragment;
    private String from="";
    private ImageView iv_clinics;

    @Override
    public void initialise() {

        context=ClinicDetailsActivity.this;
        lLMain = (LinearLayout) inflater.inflate(R.layout.clinics_details, null);
        setTypeFaceNormal(lLMain);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);

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

        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setImageResource(R.drawable.dir);
        ivSearch.setPadding(0,0,0,0);

        llBack.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);

//        setStatusBarColor();
        setStatusBarTransparent();

        LinearLayout llClinicToobar = (LinearLayout) findViewById(R.id.llClinicToobar);
        llClinicToobar.removeAllViews();
        llClinicToobar.addView(clinicToobar);

        if(getIntent().hasExtra("ClinicDo")){
            clinicDo = (ClinicDO) getIntent().getSerializableExtra("ClinicDo");
            from="ClinicActivity";
        }else if(getIntent().hasExtra("SpecializationDO")){
            specializationDO = (SpecializationDO) getIntent().getSerializableExtra("SpecializationDO");
            from="SpecialityActivity";
            ivSearch.setVisibility(View.GONE);
            ivSearch.setImageResource(R.drawable.dir);
            ivSearch.setPadding(0,0,0,0);
        }
        initialiseControls();
        loadData();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClinicLocation.class);
                //  intent.putExtra("latitude", clinicDO.latitude);
                //intent.putExtra("longitude", clinicDO.longitude);
                intent.putExtra("ClinicDo",clinicDo);
                startActivity(intent);
            }
        });
    }


    @Override
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DoctorDA doctorDA = new DoctorDA(ClinicDetailsActivity.this);
                SpecialityDA specialityDA = new SpecialityDA(ClinicDetailsActivity.this);
                if(from.equalsIgnoreCase("ClinicActivity")){
                    arrDoctor = doctorDA.getFilterDoctor(clinicDo.description);
                    arrDoctorService= doctorDA.getDoctorService(clinicDo.description);
                    arrSpeciality=specialityDA.getSpecialityClinicWise(arrDoctorService);

                }else{
                    arrDoctor = doctorDA.getDoctorSpecialityWise(specializationDO.id);
                    arrDoctorLocation= doctorDA.getDoctorlocation(specializationDO.id);
                    ClinicsDA clinicsDA=new ClinicsDA(ClinicDetailsActivity.this);
                    arrClinics=clinicsDA.getClinicSpecialityWise(arrDoctorLocation);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager(pager);
                        tabLayout.setupWithViewPager(pager);
                        doctorsFragment.onRefresh(arrDoctor);
                    }
                });
            }
        }).start();
    }
    public ClinicDO getClinicDO(){
        return clinicDo;
    }
    public SpecializationDO getSpecialityDO(){
        return specializationDO;
    }

    @Override
    public void initialiseControls() {
        tabLayout = (TabLayout) lLMain.findViewById(R.id.tabLayout);
        pager = (ViewPager) findViewById(R.id.vp);
        tvAddress = (TextView) lLMain.findViewById(R.id.tvAddress);
        tvWorkingDays = (TextView) lLMain.findViewById(R.id.tvWorkingDays);
        tvWeekEnd = (TextView) lLMain.findViewById(R.id.tvWeekEnd);
        iv_clinics = (ImageView) lLMain.findViewById(R.id.iv_clinics);
        rlHospital = (RelativeLayout) lLMain.findViewById(R.id.rlHospital);

        iv_clinics.setAlpha(0.8f);
        if(from.equalsIgnoreCase("ClinicActivity")){
            tvTitle.setText(clinicDo.description);
            tvAddress.setText(clinicDo.add1 + " " + clinicDo.add2 + " " + clinicDo.add3);
            tvWorkingDays.setText(StringUtils.fromHtml(clinicDo.timing));
            String imageurl = ServiceUrls.IMAGE_BASE_URL + clinicDo.Banners750x374;
            imageurl = imageurl.replaceAll(" ","%20");
            setClinicImage(imageurl,iv_clinics);

        }else{
            tvTitle.setText(specializationDO.name);
            tvAddress.setVisibility(View.GONE);
            tvWorkingDays.setVisibility(View.GONE);
            String imageurl = ServiceUrls.IMAGE_BASE_URL + specializationDO.SpecialtyBanner750x482;
            imageurl = imageurl.replaceAll(" ","%20");
            setClinicImage(imageurl,iv_clinics);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        doctorsFragment = new DoctorsFragment();
        viewPagerAdapter.addFragment(doctorsFragment, "DOCTORS");
        if(from.equalsIgnoreCase("ClinicActivity")){
            specialitiesFragment = new SpecialitiesFragment();
            viewPagerAdapter.addFragment(specialitiesFragment, "SPECIALITIES");
            /*ivSearch.setVisibility(View.GONE);
            ivSearch.setImageResource(R.drawable.dir);*/
        }
        else{
            clinicFragment=new ClinicFragment();
            viewPagerAdapter.addFragment(clinicFragment, "CLINICS");
           /* ivSearch.setVisibility(View.VISIBLE);
            ivSearch.setImageResource(R.drawable.dir);*/
        }
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                doctorsFragment.onRefresh(arrDoctor);
                break;
            case 1:
                if(from.equalsIgnoreCase("ClinicActivity")){
                    specialitiesFragment.onRefresh(arrSpeciality);
                }else {
                    clinicFragment.onRefresh(arrClinics);
                }
                //specialitiesFragment.onRefresh(arrDoctor);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
}
