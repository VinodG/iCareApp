package com.icare_clinics.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.DataSyncDO;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends BaseActivity {


    private LinearLayout llMain;
    private boolean isFirst;
    private  GpsLocationReceiver gpsReciever;
    @Override
    public void initialise() {
        LogUtils.debug(LogUtils.LOG_TAG,"initialise");
        llMain = (LinearLayout) inflater.inflate(R.layout.activity_splash, null);
        toolbar.setVisibility(View.GONE);
        llBody.addView(llMain, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        isLoggedIn = preference.getbooleanFromPreference(Preference.IS_LOGGED_IN,false);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.location.PROVIDERS_CHANGED");
//        gpsReciever = new GpsLocationReceiver();
//        registerReceiver(gpsReciever,intentFilter);
//        test();
        setStatusBarColor();
//        setStatusBarTransparent();
        iv_fab.setVisibility(View.GONE);
        hideKeyboard(llMain);
        if(checkPermission())
            initializeSplash();
    }

    public class GpsLocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                if (!NetworkUtil.isGpsOn(SplashActivity.this)) {
                    Toast.makeText(SplashActivity.this, "Please...! Turn on GPS", Toast.LENGTH_LONG);
                }else{
                    loadData();
                }
            }
        }
    }


    private void test()
    {
        String time = "1473705000";
        long timestampLong = Long.parseLong(time)*1000;
        Date d = new Date(timestampLong);
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        LogUtils.errorLog("date",year +"-"+month+"-"+date);
    }
    public void initializeSplash(){
        LogUtils.debug(LogUtils.LOG_TAG,"initializeSplash");
        lockDrawer("SplashActivity");
        initialiseControls();
        setTypeFaceNormal(llMain);
        createDataBase();
        loadData();
    }

    private final int REQUEST_CODE_ASK_PERMISSIONS = 1000;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LogUtils.debug(LogUtils.LOG_TAG,"onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeSplash();
                } else {
                    Toast.makeText(SplashActivity.this,"All permissions are required.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            final ArrayList<String> permissions = new ArrayList<>();
/*
*
*  CALL_PHONE
*  CALL_PHONE
*  CAMERA
*  ACCESS_FINE_LOCATION
*
* */
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CALL_PHONE);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);
            }

            if (permissions.size()>0) {

                int permissionLength = permissions.size();
                final String[] permissionArray = new String[permissionLength];
                for(int i=0; i<permissionLength; i++){
                    permissionArray[i] = permissions.get(i);
                }
                ActivityCompat.requestPermissions(SplashActivity.this,permissionArray, REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void initialiseControls() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displaymetrics.widthPixels);
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displaymetrics.heightPixels);

        AppConstants.DinproBold        = Typeface.createFromAsset(getApplicationContext().getAssets(), "DINPro_Bold.ttf");
        AppConstants.DinproLight        = Typeface.createFromAsset(getApplicationContext().getAssets(), "DINPro_Light.otf");
        AppConstants.DinproMedium        = Typeface.createFromAsset(getApplicationContext().getAssets(), "DINPro_Medium.otf");
        AppConstants.DinproRegular        = Typeface.createFromAsset(getApplicationContext().getAssets(), "DINPro_Regular.otf");
        AppConstants.UbuntuMedium        = Typeface.createFromAsset(getApplicationContext().getAssets(), "Ubuntu-M.ttf");
        AppConstants.UbuntuRegular        = Typeface.createFromAsset(getApplicationContext().getAssets(), "Ubuntu-R.ttf");

        isFirst = preference.getbooleanFromPreference(Preference.FIRST_TIME_DISPLAY, false);

    }

    @Override
    public void loadData() {
        LogUtils.debug(LogUtils.LOG_TAG, "loadData");
        setLocale(preference.getStringFromPreference(Preference.LANGUAGE,""));

      /*  Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                intent.putExtra("isFromSplash",true);
                startActivity(intent);
                finish();
            }
        },1000);


     /*   Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (NetworkUtil.isNetworkConnectionAvailable(SplashActivity.this)) {
                                String lastSyncDate = "";
                                String Action = "getMasterDetailsWithSync";
                                new CommonBL(SplashActivity.this, SplashActivity.this).syncData(Action, lastSyncDate);
                            }else{
                                goToDashboard();
                            }
//                            goToDashboard();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        timerThread.start();

*/

            /*Thread timerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                        FileUtils.exractZip(getAssets().open("Data.zip"),AppConstants.APP_LOCATION+AppConstants.IMAGE_DIR);
//                        if (isLoggedIn) {
                           *//* final String lastSyncTime = new CustomerDA(SplashActivity.this).getLastSyncTime();
                            final String syncTime = CalendarUtil.getDate(lastSyncTime, CalendarUtil.YYYY_MM_DD_FULL_PATTERN, CalendarUtil.YYYY_MM_DD_FULL_PATTERN, 5 * 60 * 1000,Locale.ENGLISH);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (syncTime != null && NetworkUtil.isNetworkConnectionAvailable(SplashActivity.this)) {
                                        new CommonBL(SplashActivity.this, SplashActivity.this).dataSync(preference.getStringFromPreference(Preference.CUSTOMER_CODE, ""), syncTime);
                                        LogUtils.errorLog("lastSyncTime", syncTime);
                                    } else {
                                        Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });*//*
                            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                            startActivity(intent);
                            finish();
//                        } else {
//                            sleep(2000);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        *//*if (!isFirst) {
                            Intent intent = new Intent(SplashActivity.this, HelpActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (!isLoggedIn) {
                                Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }*//*
                    }
                }
            });
            timerThread.start();*/
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase(AppConstants.GPS_ERROR)){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        super.onButtonYesClick(from);
    }

    @Override
    public void onButtonNoClick(String from) {
        super.onButtonNoClick(from);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(gpsReciever);
    }
    public void createDataBase() {
           boolean flag = new DatabaseHelper(getApplicationContext()).createDataBase();
        if(!flag){
            showCustomDialog(SplashActivity.this,"",getString(R.string.something_wrong),getString(R.string.OK),"","");
            return;
        }

    }
    @Override
    public void dataRetrieved(ResponseDO response) {
//        if (response.method != null && response.method == ServiceMethods.WS_DATASYNC) {
           /* if (response.data != null && response.data instanceof DataSyncDO) {
                DataSyncDO dataSyncDO = (DataSyncDO)response.data;
                String syncTime = CalendarUtil.getDate(dataSyncDO.serverTime, CalendarUtil.YYYY_MM_DD_FULL_PATTERN, CalendarUtil.SYNCH_DATE_TIME_PATTERN,10*60*1000,Locale.ENGLISH);
                preference.saveStringInPreference(Preference.LAST_ORDERS_SYNC, syncTime);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String deliveryDays = new CustomerDA(SplashActivity.this).getDeliveryDays();
                    preference.saveStringInPreference(Preference.DELIVERY_DAYS,deliveryDays);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                            intent.putExtra("ShowDeliveryPopup",true);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).start();*/
//        }
          if(response.method != null && response.method == ServiceMethods.WS_SYNCDATA){
            if(response.data != null && response.data instanceof DataSyncDO){
                DataSyncDO dataSyncDO = (DataSyncDO)response.data;
                if(dataSyncDO!=null){
                    goToDashboard();
                }
            }
        }
    }

    private void goToDashboard() {
        Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
        intent.putExtra("isFromSplash",true);
        startActivity(intent);
        finish();
    }
}
