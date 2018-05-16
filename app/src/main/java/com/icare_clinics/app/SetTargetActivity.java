package com.icare_clinics.app;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.SetTargetDO;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.icare_clinics.app.R.string.save;


public class SetTargetActivity extends BaseActivity {

    private LinearLayout llMain,llTarget;
    private TextView tvDailyTarget,tvWeeklyTarget,tvMonthlyTarget,tvRecom,tvRecomOunces,tvRecomTimes;
    private ImageView ivSelectDailyTarget,ivUnSelectDailyTarget, ivUnSelectRecomTarget,ivSelectRecomTarget,ivInfo;
    private Button bntSet;
    private EditText etDailyTarget;
    private  String dailyTarget;
    private String strFrom;
    private ArrayList<WaterDO>arrReminder;
    private WaterDO waterDO=new WaterDO();
    private Preference preference;
    private static final String YYYY_MM_DD_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private boolean isDailyTarget=false;
    private boolean isAutomaticRemainder=false;
    private USerDataDA mUserDa;
    private SettingDO settingDO;
    private int isRemainderAuto=0;
    private int isRecumTarget=0;

    @Override
    public void initialise() {

        llMain = (LinearLayout) inflater.inflate(R.layout.activity_set_target, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        setStatusBarColor();
        llBody.addView(llMain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(R.string.set_target);
        ivMenu.setVisibility(View.GONE);
        iv_fab.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.GONE);

        mUserDa = new USerDataDA(SetTargetActivity.this);
        preference=new Preference(SetTargetActivity.this);
        initialiseControls();
        loadData();

        if (getIntent().getStringExtra("from")==null || TextUtils.isEmpty(getIntent().getStringExtra("from").toString()) )
        {
        }
        else {
            strFrom=getIntent().getStringExtra("from");
        }
        bntSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                saveData();

            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
              /*  Intent intent=new Intent();
                intent.putExtra("DAYLY_TARGET",dailyTarget);
                setResult(1,intent);
                finish();*/
            }
        });
        ivUnSelectDailyTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsForDailyTarget();
            }
        });
        ivUnSelectRecomTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsForRecumTarget();
            }
        });
        etDailyTarget.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(TextUtils.isEmpty(etDailyTarget.getText().toString())) {
                        Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
                    }
                    else {
                        calculationForDailyTarget();
                        return true;
                    }
                }
                return false;
            }
        });

        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(SetTargetActivity.this,getString(R.string.info),getString(R.string.ml_of_water_popup), getString(R.string.OK), getString(R.string.cancel),"setReminder");

            }
        });
    }

    private void saveData() {

        if( (etDailyTarget.getText().toString().equalsIgnoreCase(""))&&(dailyTarget==null))
            Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
        else{
            if(!etDailyTarget.getText().toString().equalsIgnoreCase(""))
                calculationForDailyTarget();
            if (strFrom!=null){
                if (strFrom.equalsIgnoreCase("HealthTracker")) {
                    saveInPreference();
                    Intent intent = new Intent(SetTargetActivity.this,WaterIntakeNew.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }
            isAutomaticRemainder=true;
            insertData();
            preference.saveBooleanInPreference(Preference.IS_AUTOMATIC_REMAINDER,true);
            preference.saveBooleanInPreference(Preference.IS_FIRST_TIME,false);
            preference.saveStringInPreference(Preference.DAILY_TARGET,dailyTarget);
        }
    }

    private void insertData() {

        /////////inseting is Recummended target or not  with entity name Set target/////////

        settingDO =new SettingDO();
        settingDO.date=CalendarUtil.getPresentDate();
        settingDO.entityName= AppConstants.SET_TARGET;
        settingDO.value1=String.valueOf(isRemainderAuto);
        settingDO.value2=String.valueOf(isRecumTarget);
        mUserDa.insertSettings(settingDO);

        /////////inseting daily target with entity name Water Intake/////////

        settingDO =new SettingDO();
        settingDO.date=CalendarUtil.getPresentDate();
        settingDO.entityName= AppConstants.WATER_INTAKE;/// water Intake
        settingDO.value1=dailyTarget;//Daily target
        mUserDa.insertSettings(settingDO);
    }

    private void saveInPreference() {
        arrReminder=new ArrayList<>();

        Date time1,nextHrs;
        DateFormat sdf;
        String start;

        Calendar calendar1=Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), 8, 00,00);
        nextHrs = calendar1.getTime();
        sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        start = sdf.format(nextHrs);
        waterDO.date=start;
        waterDO.strWater=String.valueOf(0);
        arrReminder.add(waterDO);
        for (int i=0;i<7;i++){
            waterDO=new WaterDO();
            calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY)+2);
            nextHrs = calendar1.getTime();
            sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
            start = sdf.format(nextHrs);
            waterDO.date=start;
            waterDO.strWater=String.valueOf(0);
            arrReminder.add(waterDO);
        }
        preference.saveListInPreference(Preference.LIST,arrReminder,0);  // 0 for storage of WaterIntakeDO

    }
    @Override
    public void initialiseControls() {

        llTarget        =(LinearLayout)llMain.findViewById(R.id.llTarget);
        etDailyTarget   =(EditText)llMain.findViewById(R.id.etDailyTarget);
        tvRecom         =(TextView) llMain.findViewById(R.id.tvRecom);
        tvRecomOunces   =(TextView) llMain.findViewById(R.id.tvRecomOunces);
        tvRecomTimes    =(TextView) llMain.findViewById(R.id.tvRecomTimes);
        ivSelectDailyTarget     =(ImageView)llMain.findViewById(R.id.ivSelectDailyTarget);
        ivUnSelectDailyTarget   =(ImageView)llMain.findViewById(R.id.ivUnSelectDailyTarget);
        ivUnSelectRecomTarget   =(ImageView)llMain.findViewById(R.id.ivUnSelectRecomTarget);
        ivSelectRecomTarget     =(ImageView)llMain.findViewById(R.id.ivSelectRecomTarget);
        bntSet          =(Button) llMain.findViewById(R.id.bntSet);

        ivInfo      =(ImageView)llMain.findViewById(R.id.ivInfo);

        int i=8*236;
        preference.saveStringInPreference(Preference.TARGET,String.valueOf(i));
    }
    @Override
    public void loadData() {

        settingDO =new SettingDO();
        String strDate=CalendarUtil.getPresentDate();
        settingDO=mUserDa.getSettings(strDate,AppConstants.SET_TARGET);
        if(settingDO.entityName.equalsIgnoreCase(AppConstants.SET_TARGET))
        {
            int i=Integer.valueOf(settingDO.value2);//value2 indicates is Recummended target or not
            if (i==1){//is Recummended target

                setViewsForRecumTarget();

            }else {
                setViewsForDailyTarget();
            }
        }
    }
    private void calculationForDailyTarget() {
        dailyTarget  =etDailyTarget.getText().toString();
        float ml= Float.parseFloat(dailyTarget);
        int i= Integer.parseInt(dailyTarget);
        float Dayml=ml/1000;
        dailyTarget=String.valueOf(i);
    }

    private void setViewsForDailyTarget() {

        ivUnSelectDailyTarget.setImageResource(R.drawable.select_radio);
        ivUnSelectRecomTarget.setImageResource(R.drawable.unselect_radio);

        llTarget.setClickable(true);
        etDailyTarget.setClickable(true);
        etDailyTarget.setEnabled(true);
        isDailyTarget=true;
        isRecumTarget=0;
        isRemainderAuto=1;
    }

    private void setViewsForRecumTarget() {
        ivUnSelectDailyTarget.setImageResource(R.drawable.unselect_radio);
        ivUnSelectRecomTarget.setImageResource(R.drawable.select_radio);

        llTarget.setClickable(true);
        etDailyTarget.setEnabled(false);
        etDailyTarget.setClickable(false);

        int i=8*236;
        dailyTarget=String.valueOf(i);
        isDailyTarget=false;
        isRecumTarget=1;
        isRemainderAuto=1;
    }
}



/*
public class SetTargetActivity extends BaseActivity {

    private LinearLayout llMain,llTarget;
    private TextView tvDailyTarget,tvWeeklyTarget,tvMonthlyTarget,tvRecom,tvRecomOunces,tvRecomTimes;
    private ImageView ivSelectDailyTarget,ivUnSelectDailyTarget,ivUnSelectWeeklyTarget,ivSelectWeeklyTarget,ivUnSelectMonthlyTarget,ivSelectMonthTarget,
            ivUnSelectRecomTarget,ivSelectRecomTarget;
    private Button bntSet;
    private EditText etDailyTarget,etWeeklyTarget,etMonthlyTarget;
    private  String dailyTarget,weeklyTarget,monthlyTarget;
    private String strFrom;

    @Override
    public void initialise() {

        llMain = (LinearLayout) inflater.inflate(R.layout.activity_set_target, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        setStatusBarColor();
        llBody.addView(llMain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText("Set Target");
        ivMenu.setVisibility(View.GONE);
        iv_fab.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.VISIBLE);
        initialiseControls();

        if (getIntent().getStringExtra("from")==null || TextUtils.isEmpty(getIntent().getStringExtra("from").toString()) )
        {
        }
        else
        {
            strFrom=getIntent().getStringExtra("from");
        }

        bntSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if( (etDailyTarget.getText().toString().equalsIgnoreCase("") &&
                         etWeeklyTarget.getText().toString().equalsIgnoreCase("")&&
                        etMonthlyTarget.getText().toString().equalsIgnoreCase(""))&&(dailyTarget.equalsIgnoreCase("")))

                    Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
                else{

                    if(!etDailyTarget.getText().toString().equalsIgnoreCase(""))
                        calculationForDailyTarget();
                    if (strFrom!=null){
                        if (strFrom.equalsIgnoreCase("HealthTracker")) {
                            Intent intent = new Intent(SetTargetActivity.this,WaterIntakeNew.class);
                            intent.putExtra("DAYLY_TARGET", dailyTarget);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("DAYLY_TARGET", dailyTarget);
                        setResult(1, intent);
                        finish();
                    }
                }}
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("DAYLY_TARGET",dailyTarget);
                intent.putExtra("WeeklyLY_TARGET",weeklyTarget);
                intent.putExtra("MONTHLY_TARGET",monthlyTarget);
                setResult(1,intent);
                finish();
            }
        });

        ivUnSelectDailyTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivUnSelectDailyTarget.setVisibility(View.GONE);
                ivSelectDailyTarget.setVisibility(View.VISIBLE);
                ivUnSelectWeeklyTarget.setVisibility(View.VISIBLE);
                ivSelectWeeklyTarget.setVisibility(View.GONE);
                ivUnSelectMonthlyTarget.setVisibility(View.VISIBLE);
                ivSelectMonthTarget.setVisibility(View.GONE);
                ivUnSelectRecomTarget.setVisibility(View.VISIBLE);
                ivSelectRecomTarget.setVisibility(View.GONE);

                llTarget.setClickable(true);
                etDailyTarget.setClickable(true);
                etDailyTarget.setEnabled(true);

                etWeeklyTarget.setEnabled(false);
                etWeeklyTarget.setClickable(false);
                etMonthlyTarget.setEnabled(false);
                etMonthlyTarget.setClickable(false);

            }
        });
     */
/*   ivUnSelectWeeklyTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivUnSelectDailyTarget.setVisibility(View.VISIBLE);
                ivSelectDailyTarget.setVisibility(View.GONE);
                ivUnSelectWeeklyTarget.setVisibility(View.GONE);
                ivSelectWeeklyTarget.setVisibility(View.VISIBLE);
                ivUnSelectMonthlyTarget.setVisibility(View.VISIBLE);
                ivSelectMonthTarget.setVisibility(View.GONE);
                ivUnSelectRecomTarget.setVisibility(View.VISIBLE);
                ivSelectRecomTarget.setVisibility(View.GONE);

                llTarget.setClickable(true);
                etWeeklyTarget.setClickable(true);
                etWeeklyTarget.setEnabled(true);

                etDailyTarget.setClickable(false);
                etDailyTarget.setEnabled(false);
                etMonthlyTarget.setClickable(false);
                etMonthlyTarget.setEnabled(false);
            }
        });
        ivUnSelectMonthlyTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivUnSelectDailyTarget.setVisibility(View.VISIBLE);
                ivSelectDailyTarget.setVisibility(View.GONE);
                ivUnSelectWeeklyTarget.setVisibility(View.VISIBLE);
                ivSelectWeeklyTarget.setVisibility(View.GONE);
                ivUnSelectMonthlyTarget.setVisibility(View.GONE);
                ivSelectMonthTarget.setVisibility(View.VISIBLE);
                ivUnSelectRecomTarget.setVisibility(View.VISIBLE);
                ivSelectRecomTarget.setVisibility(View.GONE);

                llTarget.setClickable(true);
                etMonthlyTarget.setClickable(true);
                etMonthlyTarget.setEnabled(true);
                etMonthlyTarget.setFocusable(true);

                etDailyTarget.setEnabled(false);
                etDailyTarget.setClickable(false);
                etWeeklyTarget.setEnabled(false);
                etWeeklyTarget.setClickable(false);

            }
        });*//*

        ivUnSelectRecomTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivUnSelectDailyTarget.setVisibility(View.VISIBLE);
                ivSelectDailyTarget.setVisibility(View.GONE);
                ivUnSelectRecomTarget.setVisibility(View.GONE);
                ivSelectRecomTarget.setVisibility(View.VISIBLE);

                llTarget.setClickable(true);

                etDailyTarget.setEnabled(false);
                etDailyTarget.setClickable(false);

                int i=8*236;
                dailyTarget=String.valueOf(i);
            }
        });

     */
/*   etDailyTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                calculationForDailyTarget();
                etWeeklyTarget.setText(weeklyTarget);
                etMonthlyTarget.setText(monthlyTarget);
            }
        });
        etWeeklyTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
               *//*
*/
/* calculationForWeeklyTarget();
                etDailyTarget.setText(dailyTarget);
                etMonthlyTarget.setText(monthlyTarget);*//*
*/
/*
            }
        });
        etMonthlyTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
             *//*
*/
/*   calculationForMonthlyTarget();
                etDailyTarget.setText(dailyTarget);
                etWeeklyTarget.setText(weeklyTarget);*//*
*/
/*
            }
        });*//*

        etDailyTarget.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(TextUtils.isEmpty(etDailyTarget.getText().toString())) {
                        Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
                    }
                    else {
                        calculationForDailyTarget();
                        etWeeklyTarget.setText(weeklyTarget);
                        etMonthlyTarget.setText(monthlyTarget);
                        return true;
                    }
                }
                return false;
            }
        });
      */
/*  etWeeklyTarget.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(TextUtils.isEmpty(etWeeklyTarget.getText().toString())) {
                        Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
                    }
                    else {
                        calculationForWeeklyTarget();
                        etDailyTarget.setText(dailyTarget);
                        etMonthlyTarget.setText(monthlyTarget);
                        return true;
                    }
                }
                return false;
            }
        });
        etMonthlyTarget.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(TextUtils.isEmpty(etMonthlyTarget.getText().toString())) {
                        Toast.makeText(context, " Please Set Any Target",Toast.LENGTH_LONG).show();
                    }
                    else {
                        calculationForMonthlyTarget();
                        etDailyTarget.setText(dailyTarget);
                        etWeeklyTarget.setText(weeklyTarget);
                        return true;
                    }
                }
                return false;
            }
        });*//*

    }



    */
/*private void calculationForMonthlyTarget()
    {
        monthlyTarget=etMonthlyTarget.getText().toString();
        float liters = Float.parseFloat(monthlyTarget);
        float Dayml = (liters / 30) * 1000;
        float Weekml = (Dayml / 1000) * 7;
        dailyTarget = String.valueOf((int) Dayml);
        weeklyTarget = String.valueOf(Weekml);

    }

    private void calculationForWeeklyTarget() {

        weeklyTarget  =etWeeklyTarget.getText().toString();
        float liters= Float.parseFloat(weeklyTarget);
        float Dayml=(liters/7)*1000;
        float Monthml=(Dayml/1000)*30;
        dailyTarget=String.valueOf((int) Dayml);
        monthlyTarget=String.valueOf(Monthml);
    }
*//*


    @Override
    public void initialiseControls() {

        llTarget=(LinearLayout)llMain.findViewById(R.id.llTarget);

        etDailyTarget=(EditText)llMain.findViewById(R.id.etDailyTarget);
        etWeeklyTarget=(EditText)llMain.findViewById(R.id.etWeeklyTarget);
        etMonthlyTarget=(EditText) llMain.findViewById(R.id.etMonthlyTarget);

        tvRecom=(TextView) llMain.findViewById(R.id.tvRecom);
        tvRecomOunces=(TextView) llMain.findViewById(R.id.tvRecomOunces);
        tvRecomTimes=(TextView) llMain.findViewById(R.id.tvRecomTimes);

        ivSelectDailyTarget=(ImageView)llMain.findViewById(R.id.ivSelectDailyTarget);
        ivUnSelectDailyTarget=(ImageView)llMain.findViewById(R.id.ivUnSelectDailyTarget);
        ivUnSelectWeeklyTarget=(ImageView)llMain.findViewById(R.id.ivUnSelectWeeklyTarget);
        ivSelectWeeklyTarget=(ImageView)llMain.findViewById(R.id.ivSelectWeeklyTarget);
        ivUnSelectMonthlyTarget=(ImageView)llMain.findViewById(R.id.ivUnSelectMonthlyTarget);
        ivSelectMonthTarget=(ImageView)llMain.findViewById(R.id.ivSelectMonthTarget);
        ivUnSelectRecomTarget=(ImageView)llMain.findViewById(R.id.ivUnSelectRecomTarget);
        ivSelectRecomTarget=(ImageView)llMain.findViewById(R.id.ivSelectRecomTarget);

        bntSet=(Button) llMain.findViewById(R.id.bntSet);
    }

    @Override
    public void loadData() {

    }
    private void calculationForDailyTarget() {

        dailyTarget  =etDailyTarget.getText().toString();
        float ml= Float.parseFloat(dailyTarget);
        int i= Integer.parseInt(dailyTarget);
        float Dayml=ml/1000;
        float Weekml=Dayml*7;
        float Monthml=Dayml*30;
        dailyTarget=String.valueOf(i);
    }


}
*/
