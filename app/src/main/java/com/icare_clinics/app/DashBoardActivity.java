package com.icare_clinics.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.viewpagerindicator.CirclePageIndicator;
import com.icare_clinics.app.adapter.CustomviewAdapter;
import com.icare_clinics.app.businesslayer.CommonBL;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.BannerDo;
import com.icare_clinics.app.dataobject.DataSyncDO;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;

import java.util.ArrayList;
import java.util.Collections;



public class DashBoardActivity extends BaseActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ViewPager.OnPageChangeListener{

    private ViewGroup llMain;
    private AutoScrollViewpager viewPager;
    private CustomviewAdapter customviewAdapter;
    private CirclePageIndicator circlePageIndicator;
    private ImageView fabImageView,iv_fab_call;
    private EditText et_search;
    private ArrayList<BannerDo> arrBanner;
    private TextView tv_doctors,tv_clinics,tv_specialties,tv_offers,tv_healthpackages,tv_testmonial,tv_medicineReminder,tv_healthTracker;
    public int currentpage=0;
    public LinearLayout llDoctors,llClinics,llSpecialities,llHealthpkgs,lloffers,lltestmonial,ll_search,llMedicineReminder,llhealthTracker;
    private ScrollView scroll_dasboard;
    private  Boolean isFromSplash=false,defaultValue = false;;
    float bmi;
    private WeightDO weightDO;
    private USerDataDA mUserDa;
    //    private GPSLocationService gpsLocationService;
    @Override
    public void initialise() {

        llMain= (ViewGroup) inflater.inflate(R.layout.activity_dash_board,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain,param );
        setTypeFaceUbuntuRegular(llMain);
//        gpsLocationService = new GPSLocationService(DashBoardActivity.this);
        hideKeyboard(llMain);
        ivMenu.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.VISIBLE);
        ivFb.setVisibility(View.VISIBLE);

        tvTitle.setVisibility(View.GONE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        ivSearch.setVisibility(View.GONE);
        llBack.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);

        isFromSplash = getIntent().getBooleanExtra("isFromSplash",defaultValue);

  /*      int maxSize= (int) getResources().getDimension(R.dimen.logo_width);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap converetdImage = getResizedBitmap(largeIcon, maxSize);
        ivLogo.setImageBitmap(converetdImage);
*/
        mUserDa=new USerDataDA(DashBoardActivity.this);

        initialiseControls();
        loadData();
        setStatusBarColor();
        getBanners();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void initialiseControls() {

        llDoctors = (LinearLayout) llMain.findViewById(R.id.llDoctors);
        llClinics = (LinearLayout) llMain.findViewById(R.id.llClinics);
        llMedicineReminder = (LinearLayout) llMain.findViewById(R.id.llMedicineReminder);
        llhealthTracker = (LinearLayout) llMain.findViewById(R.id.llhealthTracker);

        llSpecialities = (LinearLayout) llMain.findViewById(R.id.llSpecialities);
        llHealthpkgs = (LinearLayout) llMain.findViewById(R.id.llhealthpackages);
        ll_search = (LinearLayout) llMain.findViewById(R.id.ll_search);
        lloffers     =(LinearLayout)llMain.findViewById(R.id.lloffers);
        lltestmonial =(LinearLayout)llMain.findViewById(R.id.lltestmonial);
        viewPager=(AutoScrollViewpager) llMain.findViewById(R.id.vp_dashboard);
        circlePageIndicator=(CirclePageIndicator)llMain.findViewById(R.id.vp_dashboard_circle);
        fabImageView=(ImageView)llMain.findViewById(R.id.iv_fab_appoint);
        iv_fab_call=(ImageView)llMain.findViewById(R.id.iv_fab_call);
        et_search = (EditText)llMain.findViewById(R.id.et_search);

        tv_doctors=(TextView)llMain.findViewById(R.id.tv_clinics);
        tv_clinics=(TextView)llMain.findViewById(R.id.tv_doctors);
        tv_specialties=(TextView)llMain.findViewById(R.id.tv_specialties);
        tv_offers=(TextView)llMain.findViewById(R.id.tv_offers);
        tv_healthpackages=(TextView)llMain.findViewById(R.id.tv_healthpackages);
        tv_testmonial=(TextView)llMain.findViewById(R.id.tv_testmonial);
        tv_medicineReminder=(TextView)llMain.findViewById(R.id.tv_medicineReminder);
        tv_healthTracker=(TextView)llMain.findViewById(R.id.tv_healthTracker);

        tv_doctors.setTypeface(AppConstants.UbuntuMedium);
        tv_clinics.setTypeface(AppConstants.UbuntuMedium);
        tv_specialties.setTypeface(AppConstants.UbuntuMedium);
        tv_offers.setTypeface(AppConstants.UbuntuMedium);
        tv_healthpackages.setTypeface(AppConstants.UbuntuMedium);
        tv_testmonial.setTypeface(AppConstants.UbuntuMedium);
        tv_medicineReminder.setTypeface(AppConstants.UbuntuMedium);
        tv_healthTracker.setTypeface(AppConstants.UbuntuMedium);

        customviewAdapter=new CustomviewAdapter(this,arrBanner);
        viewPager.setAdapter(customviewAdapter);
        viewPager.setCurrentItem(currentpage);
        viewPager.startAutoScrollPager(viewPager);
        circlePageIndicator.setViewPager(viewPager);

        llDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, DoctorsMainActivity.class));
//                startActivity(new Intent(DashBoardActivity.this,MainActivity.class));
            }
        });
        llClinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, ClinicsMainActivity.class);
                startActivity(intent);
            }
        });
        llSpecialities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, SpecialitiesActivity.class);
                startActivity(intent);
            }
        });
        llMedicineReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, MyReminder.class);
                startActivity(intent);
            }
        });

        llhealthTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = preference.getStringFromPreference(Preference.NAME,"");
                String mobileNUM=preference.getStringFromPreference(Preference.MOBILE_NUM,"");
                String email=preference.getStringFromPreference(Preference.EMAIL_ID,"");
                String gender=preference.getStringFromPreference(Preference.GENDER,"");
                String dateOfBirth=preference.getStringFromPreference(Preference.DOB,"");
                String height=preference.getStringFromPreference(Preference.HEIGHT,"");
                String weight=preference.getStringFromPreference(Preference.WEIGHT,"");
                if(name.equalsIgnoreCase("")&&mobileNUM.equalsIgnoreCase("")&&email.equalsIgnoreCase("")){
                         Intent intent = new Intent(DashBoardActivity.this, HealthTrackerIntro.class);
                        startActivity(intent);

                }else {
                    preference.saveStringInPreference(Preference.NEW_DAY_TARGET,"1888");
                    Intent   intent = new Intent(DashBoardActivity.this, HealthTracker.class);
                    intent.putExtra("DashBoard","DashBoard");
                    startActivity(intent);
                }
            }
        });
        llHealthpkgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, HealthPackagesActivity.class);
                startActivity(intent);
            }
        });

        fabImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashBoardActivity.this,AppointmentForm.class);
                intent.putExtra("from","DashBoard");
                startActivity(intent);
                //Toast.makeText(DashBoardActivity.this,"clicked",Toast.LENGTH_LONG).show();
            }
        });
        iv_fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "800 iCARE(42273)"));
                    if (ActivityCompat.checkSelfPermission(DashBoardActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
        lltestmonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashBoardActivity.this,TestimonialsActivityNew.class);
                startActivity(intent);
            }
        });

        lloffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashBoardActivity.this, NewsActivity.class);
                Intent intent = new Intent(DashBoardActivity.this, WhatsNewActivity.class);
                startActivity(intent);
            }
        });

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(DashBoardActivity.this, SearchActivity.class);
                Intent intent=new Intent(DashBoardActivity.this, SearchActivityNew.class);
                startActivity(intent);
            }
        });

       iv_fab.setVisibility(View.GONE);
    }

    private void RequestLocationUpdates() {

        /*int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(DashBoardActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashBoardActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (apiClient != null && apiClient.isConnected()) {
            if (_currentLocation == null)
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, mLocationRequest, this);
        }*/
    }

    @Override
    public void loadData() {

        if (isFromSplash) {
            showLoader(getString(R.string.loding));
            Thread timerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (NetworkUtil.isNetworkConnectionAvailable(DashBoardActivity.this)) {
                                    String lastSyncDate = "";
                                    String Action = "getMasterDetailsWithSync";
                                    new CommonBL(DashBoardActivity.this, DashBoardActivity.this).syncData(Action, lastSyncDate);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            timerThread.start();
        }
    }

    public void getBanners(){
           ExtraDA extraDA = new ExtraDA(DashBoardActivity.this);

        arrBanner=extraDA.getBanner();
        Collections.reverse(arrBanner);
        customviewAdapter.refresh(arrBanner);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    @Override
    protected void onStart() {
//        gpsLocationService.connectGoogleClient();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        showCustomDialog(DashBoardActivity.this,getString(R.string.alert),getString(R.string.exit_confirm),getString(R.string.OK),getString(R.string.cancel),"Exit");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        gpsLocationService.stopLocationUpdate();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
        if(from.equalsIgnoreCase("Exit")){
            finish();
            Intent intentBrObj = new Intent();
            intentBrObj.setAction(AppConstants.ACTION_LOGOUT);
            sendBroadcast(intentBrObj);
        }
    }

    @Override
    public void onButtonNoClick(String from) {
        if(from.equalsIgnoreCase("DeliveryPopup")){
            preference.saveBooleanInPreference(Preference.SHOWDELIVERY,false);
        }
    }

    @Override
    public void dataRetrieved(ResponseDO response) {

        if(response.method != null && response.method == ServiceMethods.WS_SYNCDATA){
            if(response.data != null && response.data instanceof DataSyncDO){
                hideLoader();
                DataSyncDO dataSyncDO = (DataSyncDO)response.data;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        RequestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
