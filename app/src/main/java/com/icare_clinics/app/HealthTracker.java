package com.icare_clinics.app;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.icare_clinics.app.R.drawable.bmi;
import static com.icare_clinics.app.R.drawable.preview;
import static com.icare_clinics.app.R.string.weight;

public class HealthTracker extends BaseActivity {
    LinearLayout lLMain,llWaterIntake,llBmiCalculator,llFootStepCounter;
    private TextView tvUserName,tvUserEmail,tvUserNumber;
    private ImageView ivEditProfile;
    public UserDo userDo;
    private USerDataDA mUserDa;
    private TextView tvHeight,tvWeight,tvBMI,tvWaterIntake,tvStepsCount;
    LinearLayout llheight,llWeight,llBMI;
    private Preference preference;
    private SettingDO settingDO;
    private ArrayList<WaterDO> arrTemp;
    int strConsumedWater=0,strTargetWater=0;
    private String strBMI="",strTarget;
    private WeightDO weightDO;
    private float bmi;
    private ArrayList<WeightDO>arrDay;

    @Override
    public void initialise() {

        lLMain = (LinearLayout) inflater.inflate(R.layout.activity_health_tracker, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        setStatusBarColor();
        llBody.addView(lLMain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(R.string.health_tracker);
        ivMenu.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);

        mUserDa=new USerDataDA(HealthTracker.this);
        preference=new Preference(HealthTracker.this);

        initialiseControls();
        loadData();

//        strBMI=preference.getStringFromPreference(Preference.BMI,"");

      /*  if (!strBMI.equalsIgnoreCase("")) {
            float v = Float.parseFloat(strBMI);
            if (!strBMI.equalsIgnoreCase("")) {
                tvBMI.setText(new DecimalFormat("##.##").format(v));
            } else {
                tvBMI.setText("00");
            }
        }*/
//        String dailtTarget=preference.getStringFromPreference(Preference.TARGET,"");
       /* if (!dailtTarget.equalsIgnoreCase("")){
            tvWaterIntake.setText(strConsumedWater+"/"+dailtTarget+"ML");
        }*/
        int stepsCount=preference.getIntFromPreference(Preference.STEPS_COUNT,0);
        if (stepsCount>0){
            tvStepsCount.setText(stepsCount+" Steps");
        }else {
            tvStepsCount.setText("");
        }
    }

    @Override
    public void initialiseControls() {
        llWaterIntake=(LinearLayout)lLMain.findViewById(R.id.llWaterIntake);
        llBmiCalculator=(LinearLayout)lLMain.findViewById(R.id.llBmiCalculator);
        llFootStepCounter=(LinearLayout)lLMain.findViewById(R.id.llFootStepCounter);
//        llWeightTracker=(LinearLayout)lLMain.findViewById(R.id.llWeightTracker);
        tvUserName=(TextView)lLMain.findViewById(R.id.tvUserName);
        tvUserEmail=(TextView)lLMain.findViewById(R.id.tvUserEmail);
        tvUserNumber=(TextView)lLMain.findViewById(R.id.tvUserNumber);
        ivEditProfile=(ImageView) lLMain.findViewById(R.id.ivEditProfile);

        llheight=(LinearLayout)lLMain.findViewById(R.id.llheight);
        llWeight=(LinearLayout)lLMain.findViewById(R.id.llWeight);
        llBMI=(LinearLayout)lLMain.findViewById(R.id.llBMI);
        tvHeight=(TextView) lLMain.findViewById(R.id.tvHeight);
        tvWeight=(TextView) lLMain.findViewById(R.id.tvWeight);
        tvBMI=(TextView) lLMain.findViewById(R.id.tvBMI);
        tvWaterIntake=(TextView) lLMain.findViewById(R.id.tvWaterIntake);
        tvStepsCount=(TextView) lLMain.findViewById(R.id.tvStepsCount);

        tvUserName.setTypeface(AppConstants.UbuntuRegular);
        tvUserEmail.setTypeface(AppConstants.UbuntuRegular);
        tvUserNumber.setTypeface(AppConstants.UbuntuRegular);

        // Log.d("**********",userDo.name);
        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthTracker.this,ProfileSetupActivityNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("HealthTracker","HealthTracker");
                startActivity(intent);

            }
        });
        llFootStepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthTracker.this,StepsCountActivityNew.class);
                startActivityForResult(intent,1);
            }
        });
        llWaterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFirstTime = preference.getbooleanFromPreference(Preference.IS_FIRST_TIME, true);
                if (isFirstTime){
                    Intent intent=new Intent(HealthTracker.this,SetTargetActivity.class);
                    intent.putExtra("from","HealthTracker");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(HealthTracker.this, WaterIntakeNew.class);
                    startActivityForResult(intent, 1);
                    finish();
                }
            }
        });
        llBmiCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthTracker.this,BodyMeasurement.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("BMIDATASET",false);
                startActivity(intent);
            }
        });

        llheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HealthTracker.this,ProfileSetupActivityNew.class);
                intent.putExtra("from","Height");
                startActivity(intent);
            }
        });
        llWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HealthTracker.this,ProfileSetupActivityNew.class);
                intent.putExtra("from","Weight");
                startActivity(intent);
            }
        });
        llBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HealthTracker.this,BMICalculator.class);
                intent.putExtra("from","HealthTracker");
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            if (resultCode == 1) {
                float str = data.getFloatExtra("BMI", 1);
                tvBMI.setText(new DecimalFormat("##.##").format(str));
                loadData();
            } else {
                if (resultCode == 2) {
//                    int steps=data.getIntExtra("DAILY_STEPS",2);
                    int steps=preference.getIntFromPreference(Preference.STEPS_COUNT,0);
                    if (steps>0) {
                        tvStepsCount.setText(steps + " Steps");
                    }else {
                        tvStepsCount.setText("");
                    }
                }
                else{
                    loadData();
                }
            }
        }
    }

    @Override
    public void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                userDo = mUserDa.getUserData();
                //////////////for getting water consumed in a day ///////////////////
                arrTemp=new ArrayList<>();
                String date = CalendarUtil.getDate();
                arrTemp = mUserDa.getWaterData(date);
                strConsumedWater=0;

                //////////////////for getting target water //////////////////////

                settingDO =new SettingDO();
                String strDate=CalendarUtil.getPresentDate();
                settingDO=mUserDa.getSettings(strDate,AppConstants.WATER_INTAKE);

                String startTime=CalendarUtil.getStartOfDay();
                String endTime=CalendarUtil.getEndOfDay();
                arrDay=mUserDa.getWeightsBetweenDates(startTime,endTime,0);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(userDo!=null){
                            tvUserName.setText(userDo.name+" "+userDo.lastName);
                            tvUserEmail.setText(userDo.email);
                            tvUserNumber.setText(userDo.mobileNumber);

                            String height=userDo.height;
                            String weight=userDo.weight;

                            if (height != null && !"".equals(height)
                                    && weight != null  &&  !"".equals(weight)) {

                                calculateBMI(height,weight);
//                                preference.saveStringInPreference(Preference.BMI,String.valueOf(bmi)) ;
                            }
                            tvHeight.setText(height+" cm");
                            tvWeight.setText(weight+" kg");
                        }

                        if (arrTemp!=null && arrTemp.size()>0){
                            for (int i=0;i<arrTemp.size();i++){
                                strConsumedWater+=Integer.valueOf(arrTemp.get(i).strWater);
                            }
                        }

                        if(settingDO.entityName.equalsIgnoreCase(AppConstants.WATER_INTAKE)) {
                            strTargetWater = Integer.valueOf(settingDO.value1);
                            tvWaterIntake.setText(strConsumedWater+"/"+strTargetWater+"ML");

                        }else {
                            String str=preference.getStringFromPreference(Preference.NEW_DAY_TARGET,"0");
                            if (str.equalsIgnoreCase("1888")){
                                tvWaterIntake.setText("0"+"/"+str+"ML");
                            }else {
                                tvWaterIntake.setText("0" + "/" + "0" + "ML");
                            }
                          /*  if (getIntent().hasExtra("DashBoard")){
                                tvWaterIntake.setText("0"+"/"+"1888ML");
                            }else {
                                tvWaterIntake.setText("0" + "/" + "0" + "ML");
                            }*/
                           /* strTarget=preference.getStringFromPreference(Preference.DAILY_TARGET,"0");
                            if (!strTarget.equalsIgnoreCase("")){
                                tvWaterIntake.setText("0"+"/"+strTarget+"ML");
                            }else {
                                tvWaterIntake.setText("0" + "/" + "0" + "ML");
                            }*/
                        }
                        if (!(arrDay!=null && arrDay.size()>0)){
                            insertWeightAndBmi();
                        }
                    }
                });
            }
        }).start();
    }

    private void calculateBMI(String height, String weight) {

        float heightValue = Float.parseFloat(height) / 100;
        float weightValue = Float.parseFloat(weight);
        bmi = weightValue / (heightValue * heightValue);
        tvBMI.setText(new DecimalFormat("##.##").format(bmi));
    }

    private void insertWeightAndBmi() {

        String date= CalendarUtil.getPresentDate();
        weightDO=new WeightDO();
        weightDO.weight=userDo.weight;
        weightDO.date=date;
        weightDO.bmi= String.valueOf(bmi);
        mUserDa.insertWeight(weightDO);
    }
}
