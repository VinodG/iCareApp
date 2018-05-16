package com.icare_clinics.app;

import android.content.Intent;
import android.content.res.TypedArray;
import java.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.SetReminderDo;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.TimingDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.icare_clinics.app.adapter.CustomTimeAdapter;
import com.icare_clinics.app.adapter.CustomWaterAdapter;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.TimeListDO;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.dataobject.WaterMlDO;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.R.attr.data;
import static com.icare_clinics.app.R.string.tag1_key;
import static com.icare_clinics.app.utilities.CalendarUtil.getTimeinAMorPM;

public class WaterIntakeNew extends BaseActivity {

    private LinearLayout llSelectTime, llTarget, llReminder, llhumanbody, llTimings;
    private RelativeLayout llMain;
    RelativeLayout rlhumanbody;
    private RecyclerView lvWaterML;
    private TextView tvTime, tvDailyFill, tvViewDetails, tvYourRemainder, tvAutomatic, tvYourTarget, tvTarget,tvConsumedWater,tvTargetWater;
    private ImageView iv_eight, iv_seven, iv_six, iv_five, iv_four, iv_three, iv_two, iv_one;
    private EditText etCustom_ml;
    private CircularProgressBar circularProgressbar;
    private Button btnAdd;
    private ArrayList<TimeListDO> arrTimeList = new ArrayList<TimeListDO>();
    //    private ArrayList<WaterMlDO> arrWaterList = new ArrayList<WaterMlDO>();
    private ArrayList<WaterDO> arrWaterList = new ArrayList<WaterDO>();
    int waterConsumption = 0;
    int targetWaterConsumption = 0;
    int tempWaterConsumption = 0, tempWater = 0;
    private String tmp, time,timeOfIntake="";
    private CustomTimeAdapter timeAdapter;
    private CustomWaterAdapter waterAdapter;
    private WaterDO waterDO;
    private TimeListDO listDO;
    private USerDataDA mUserDa;
    private Preference preference;
    private ArrayList<WaterDO> arrReminder;
    String currenttime = "00:00:00";
    private String strMin = "";
    private ArrayList<WaterDO> arrTemp;
    private ArrayList<TimingDo> arrTempTiming = new ArrayList<TimingDo>();
    private boolean isFromBtnClick = false;
    private static final String YYYY_MM_DD_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private SettingDO settingDO;
    int rlhumanbody_height ;//= rlhumanbody.getHeight();
    private String imgBottle;
    private ArrayList<SetReminderDo>arrRemainders;
    private ArrayList<SetReminderDo> arrRem;
    private SetReminderDo setReminderDo;

    @Override
    public void initialise() {

        llMain = (RelativeLayout) inflater.inflate(R.layout.activity_water_intake_new, null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        setStatusBarColor();
        if(llBody!=null && llBody.getChildCount()>0)
        {
            llBody.removeAllViews();
        }
        llBody.addView(llMain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(R.string.water_intake);
        ivMenu.setVisibility(View.GONE);
        iv_fab.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setImageResource(R.drawable.refresh);

        arrTemp = new ArrayList<WaterDO>();
        mUserDa = new USerDataDA(WaterIntakeNew.this);
        preference = new Preference(WaterIntakeNew.this);

        setRemaindersAndWaterIntake();
        initialiseControls();
        getArrayListForTime();
        loadData();
        setAnimationForBoxes();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String target = b.getString("DAYLY_TARGET");
            if (target != null) {
                tvConsumedWater.setText("0 ml");
                tvTargetWater.setText(target+"ml");
                tvTarget.setText(target + " ml");
                targetWaterConsumption = Integer.parseInt(target);
            }
        }

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        int spanCount = arrWaterList.size();
        int spacing = (int) getResources().getDimension(R.dimen.spacing_bottles); // 50px
        final boolean includeEdge = true;
        lvWaterML.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        lvWaterML.setLayoutManager(manager);
        waterAdapter = new CustomWaterAdapter(WaterIntakeNew.this, arrWaterList);
        lvWaterML.setAdapter(waterAdapter);

        llReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaterIntakeNew.this, SetReminder.class);
                startActivity(intent);
            }
        });
        llTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaterIntakeNew.this, SetTargetActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        llSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvTime.setTag(tag1_key, tmp);
//                time = (String) tvTime.getTag(tag1_key);
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(tmp, 1);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempWaterConsumption > 0) {

                    String strTempDate="";
                    String strDate = CalendarUtil.getPresentDate();
                    String strTime = CalendarUtil.getPresentTime();

                    if(timeOfIntake.equalsIgnoreCase("")){
                        strTempDate=strDate;

                    }else {
                        strTempDate=CalendarUtil.getDate()+" "+timeOfIntake;
                        String[] str=timeOfIntake.split(":");
                        strTime=str[0]+":"+str[1];
                        timeOfIntake="";
                    }
                    waterDO = new WaterDO();
                    waterDO.strWater = String.valueOf(tempWaterConsumption);
                    if (imgBottle!=null) {
                        waterDO.imgBottle = imgBottle;
                    }else{
                        imgBottle= String.valueOf(R.drawable.bottle);
                        waterDO.imgBottle = imgBottle;
                    }
                    waterDO.date = strTempDate;
                    waterDO.time = strTime;
                    mUserDa.insertWater(waterDO);

                    waterConsumption += tempWaterConsumption;
                    tempWater = tempWaterConsumption;
                    int per = calculatePercentage(waterConsumption, targetWaterConsumption);
                    tempWaterConsumption = 0;
                    tvConsumedWater.setText(waterConsumption + " ml");
                    tvTargetWater.setText(targetWaterConsumption+"ml");
//                    tvDaily.setText(waterConsumption + " ml" + "\n \n " + "Daily " + "\n \n" + targetWaterConsumption + "ml");

                    currenttime = getTimeinAMorPM();
                    isFromBtnClick = true;
                    etCustom_ml.setText("");
                    etCustom_ml.setHint("00");
                    hideKeyboard(llMain);

                    settingDO=new SettingDO();
                    settingDO.date=strDate;
                    settingDO.value1=String.valueOf(targetWaterConsumption);
                    settingDO.entityName= AppConstants.WATER_INTAKE;
                    settingDO.value2=String.valueOf(rlhumanbody_height);
                    mUserDa.insertSettings(settingDO);
                    loadData();
                    setAnimationForBoxes();

                } else {
                    showCustomDialog(WaterIntakeNew.this, "", getString(R.string.please_select), getString(R.string.OK), "", "");
                }
            }
        });
        etCustom_ml.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String str = etCustom_ml.getText().toString();
                str = str.contains(" ") ? str.replace(" ", "") : str;
                str = TextUtils.isEmpty(str) ? "0" : str;

                tempWaterConsumption = Integer.parseInt(str);
//                imgBottle= String.valueOf(R.drawable.bottle);
            }
        });
        tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterIntakeNew.this, ViewDetailsActivity.class);
                startActivity(intent);
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.saveStringInPreference(Preference.DATE_WATER_IN_TAKE_GRAPH,CalendarUtil.getPresentDate());
                Intent intent = new Intent(WaterIntakeNew.this, WaterIntakeHistory.class);
                startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterIntakeNew.this,HealthTracker.class);
                intent.putExtra("TARGET_WATER",targetWaterConsumption);
//                setResult(2, intent);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setRemaindersAndWaterIntake()
    {
        boolean isAutomatic = preference.getbooleanFromPreference(Preference.IS_AUTOMATIC_REMAINDER, true);

        if (arrTempTiming !=null && arrTempTiming.size()>0){
            arrTempTiming.clear();
        }
        if (isAutomatic) {
            arrReminder = (ArrayList<WaterDO>) preference.getListFromPreference("LIST", 0);
            for (int i = 0; (arrReminder != null && i < arrReminder.size()); i++) {
                TimingDo timingDo = new TimingDo();
                timingDo.timing = arrReminder.get(i).date;
                arrTempTiming.add(timingDo);
            }
        } else {
            String strDate=CalendarUtil.getPresentDate();
            arrRemainders=new ArrayList<>();
            arrRem=new ArrayList<SetReminderDo>();
            arrRemainders=mUserDa.getRemainders(strDate);

            if (arrRemainders!=null && arrRemainders.size()>0){

                for (int i = 0; i < arrRemainders.size(); i++) {
                    TimingDo timingDo = new TimingDo();
                    timingDo.timing = arrRemainders.get(i).date;
                    arrTempTiming.add(timingDo);
                }
            }else {
                preference.saveBooleanInPreference(Preference.IS_AUTOMATIC_REMAINDER, true);
                preference.saveStringInPreference(Preference.IS_REMAINDER_SET,"Not Set");

                setRemaindersAndWaterIntake();
            }


          /*  ArrayList<SetReminderDo> arrSetReminder = (ArrayList<SetReminderDo>) preference.getListFromPreference("MANUAL_LIST", 1);

            for (int i = 0; (arrSetReminder != null && i < arrSetReminder.size()); i++) {

                TimingDo timingDo = new TimingDo();
                timingDo.timing = arrSetReminder.get(i).date;
                arrTempTiming.add(timingDo);
             *//*   TimingDo timingDo = new TimingDo();
                String hh, mm, formate,strTime;
                hh = arrSetReminder.get(i).hr;
                mm = arrSetReminder.get(i).mm;

                strTime=hh+":"+mm;
                Calendar calendar = Calendar.getInstance();
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("HH:mm").parse(strTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), date1.getHours(), date1.getMinutes(), 00);

                Date date2 = calendar.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
                String date3 = format1.format(date2);
                timingDo.timing = date3;
                arrTempTiming.add(timingDo);*//*
            }*/
        }
        Collections.reverse(arrTempTiming);
    }

    @Override
    public void initialiseControls() {

        llSelectTime = (LinearLayout) llMain.findViewById(R.id.llSelectTime);
        llTarget = (LinearLayout) llMain.findViewById(R.id.llTarget);
        llReminder = (LinearLayout) llMain.findViewById(R.id.llReminder);
        llhumanbody = (LinearLayout) llMain.findViewById(R.id.llhumanbody);
        llTimings = (LinearLayout) llMain.findViewById(R.id.llTimings);
        rlhumanbody = (RelativeLayout) llMain.findViewById(R.id.rlhumanbody);
        lvWaterML = (RecyclerView) llMain.findViewById(R.id.lvWaterML);
        circularProgressbar = (CircularProgressBar) llMain.findViewById(R.id.circularProgressbar);
        btnAdd = (Button) llMain.findViewById(R.id.btnAdd);
        tvTime = (TextView) llMain.findViewById(R.id.tvTime);
        tvDailyFill = (TextView) llMain.findViewById(R.id.tvDailyFill);
        tvConsumedWater = (TextView) llMain.findViewById(R.id.tvConsumedWater);
        tvTargetWater = (TextView) llMain.findViewById(R.id.tvTargetWater);
        tvViewDetails = (TextView) llMain.findViewById(R.id.tvViewDetails);
        tvYourRemainder = (TextView) llMain.findViewById(R.id.tvYourRemainder);
        tvAutomatic = (TextView) llMain.findViewById(R.id.tvAutomatic);
        tvYourTarget = (TextView) llMain.findViewById(R.id.tvYourTarget);
        tvTarget = (TextView) llMain.findViewById(R.id.tvTarget);
        etCustom_ml = (EditText) llMain.findViewById(R.id.etCustom_ml);

        String strReminder=preference.getStringFromPreference(Preference.IS_REMAINDER_SET,"Not Set");
        if(strReminder.equalsIgnoreCase("Automatic")){
            tvAutomatic.setText("Automatic");
        }else if(strReminder.equalsIgnoreCase("Manual")){
            tvAutomatic.setText("Manual");
        }else {
            tvAutomatic.setText("Not Set");
        }

        llTimings.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Tha below code is executed after setting data to all views
                        // The effected data is not binding to views
                        // So after modification of all view's parameter again we are setting data to views
                        // i.e. calling loaddata() and setAnimationForBoxes() again
                        setAnimationForBoxes();
                        llTimings.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        rlhumanbody_height = rlhumanbody.getHeight();
                        loadData();
                    }
                });
    }
    private void setAnimation(int per, int height) {
        if (per >= 100) {
            per = 100;
        }
        int modifiedheight = (int) ((height * per) / 100);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, modifiedheight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llhumanbody.setLayoutParams(params);
    }
    private void setProgressBar(int per) {
        circularProgressbar.setProgressWithAnimation(per, 2000); //2000 animation time
    }

    private int calculatePercentage(int temp, int targetWater) {
        int per = 0;
        if (targetWater > 0) {
            per = (int) Math.round(temp * 100.0 / targetWater);
            String strPer = String.valueOf(per);
            tvDailyFill.setText("Your Daily fill is" + "\n" + strPer + " %");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter your target ,your target water is " + targetWater, Toast.LENGTH_SHORT).show();
        }
        return per;
    }

    private void getArrayListForTime() {
        arrWaterList.clear();
        String[] strMl = getResources().getStringArray(R.array.array_ml);
        TypedArray ivIcons = getResources().obtainTypedArray(R.array.array_mlIcons);
        for (int i = 0; i < strMl.length; i++) {
            WaterDO items = new WaterDO(strMl[i], ivIcons.getResourceId(i, 0));
            arrWaterList.add(items);
        }
    }

    @Override
    public void loadData() {
        int strConsumedWater=0,strTargetWater=0,rlHeight,per=0;
        String strTimeOfIntake="";
        String date = CalendarUtil.getDate();
        arrTemp = mUserDa.getWaterData(date);

        if (arrTemp == null) {
            arrTemp = new ArrayList<>();
        }
        if (arrTemp!=null && arrTemp.size()>0){
            for (int i=0;i<arrTemp.size();i++){
                strConsumedWater+=Integer.valueOf(arrTemp.get(i).strWater);
            }
            strTimeOfIntake= String.valueOf(arrTemp.get(arrTemp.size()-1).time);

//            setRemaindersAndWaterIntake();
        }

        if (!StringUtils.isEmpty(strTimeOfIntake)) {
//            tvTime.setText(strTimeOfIntake);
            String[] str1=strTimeOfIntake.split(":");
            tmp = str1[0]+":"+str1[1];
            int hh =Integer.valueOf(str1[0]);
            int mm =Integer.valueOf(str1[1]);
            setTimeOfintake(hh,mm);

        } else {
            int hr,mm;
            tvTime.setText(CalendarUtil.getTimeinAMorPM());
            Calendar calendar = Calendar.getInstance();
            hr=calendar.get(Calendar.HOUR_OF_DAY);
            mm=calendar.get(Calendar.MINUTE);
            tmp = hr+":"+mm;
        }


        settingDO =new SettingDO();
        String strDate=CalendarUtil.getPresentDate();
        settingDO=mUserDa.getSettings(strDate,AppConstants.WATER_INTAKE);
        if(settingDO.entityName.equalsIgnoreCase(AppConstants.WATER_INTAKE)){
            strTargetWater=Integer.valueOf(settingDO.value1);//for target Water

            per=calculatePercentage(strConsumedWater,strTargetWater);

            setProgressBar(per);
            setAnimation(per, rlhumanbody_height);

            tvDailyFill.setText("Your Daily fill is" + "\n" + per + " %");
            tvConsumedWater.setText(strConsumedWater + "ml");
            tvTargetWater.setText(strTargetWater+"ml");

            tvTarget.setText(strTargetWater + " ml");
            targetWaterConsumption = strTargetWater;
            waterConsumption = strConsumedWater;
        }else {
            String strTarget=preference.getStringFromPreference(Preference.NEW_DAY_TARGET,"");
            if (StringUtils.isEmpty(strTarget)){
                strTarget="0";
            }
            tvConsumedWater.setText( "0" + "ml");
            tvTargetWater.setText(strTarget+"ml");

            tvTarget.setText(strTarget + " ml");
            targetWaterConsumption =Integer.parseInt(strTarget);
            waterConsumption = 0;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
//                String s = data.getStringExtra("DAYLY_TARGET");
                initialise();
            }
        }
    }

    private void setTimeOfintake(int hour, int min) {

        String mm="",format,time;
        if (min < 10) {
            mm = "0" + min;
        } else {
            mm = "" + min;
        }
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time=hour+":"+mm+" "+format;
        tvTime.setText(time);
    }

    public void SelectedTime(int hour, int min, int val) {
        String format;
        if (min < 10) {
            strMin = "0" + min;
        } else {
            strMin = "" + min;
        }

        timeOfIntake =hour+":"+strMin+":"+"00";

        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        if (val == 1) {
            String tm = String.valueOf(hour) + ":" + strMin +" "+ format;
            tmp = String.valueOf(hour) + ":" + strMin;
            tvTime.setTag(tag1_key, tmp);
            tvTime.setText(tm);
            preference.saveStringInPreference(Preference.SELECTED_DATE, tm);
            preference.saveStringInPreference(Preference.SELECTEDDATE, tmp);
        }
    }

    public void sendData( WaterDO waterDO) {
        String string=waterDO.strWater;
        String str[] = string.split(" ml");
        tempWaterConsumption = Integer.parseInt(str[0]);
        imgBottle =waterDO.imgBottle;
        etCustom_ml.setText(str[0]);
    }

    @Override
    public void onBackPressed() {
        // Write your code here
        Intent intent = new Intent(WaterIntakeNew.this,HealthTracker.class);
        intent.putExtra("TARGET_WATER",targetWaterConsumption);
//        setResult(2, intent);
        startActivity(intent);
        super.onBackPressed();
    }

    private void setAnimationForBoxes() {
        llTimings.removeAllViews();
        int j = arrTempTiming.size();
        String string1 = "", string2 = "", string3 = "";
        if (arrTempTiming != null && arrTempTiming.size() > 0) {
            boolean isVisited = false;
            for (int i = 0; i < j; i++) {
                int height = llTimings.getHeight();
                int width = llTimings.getWidth();

                boolean isGreen = false;

                int modifiedheight = (int) (height / (arrTempTiming.size()));

                LinearLayout ll = new LinearLayout(this);
                ImageView imageView = new ImageView(this);
                TextView tv2 = new TextView(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width - 35, modifiedheight);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                imageView.setLayoutParams(params);
                ///////////////////////
                try {
                    Calendar calendar1 = Calendar.getInstance();
                    if (i == 0) {
                        string2 = arrTempTiming.get(i).timing.toString();
                        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
                    } else {
                        string1 = string2;
                        string2 = arrTempTiming.get(i).timing.toString();
                        Date time1 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(string1);
                        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), time1.getHours(), time1.getMinutes(), time1.getSeconds());
                    }
                    Date time2 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH), time2.getHours(), time2.getMinutes(), time2.getSeconds());

                    for (int k = 0; k < arrTemp.size(); k++) {
                        if (arrTemp != null && arrTemp.size() > 0)
                            currenttime = arrTemp.get(k).date.toString();
                        else
                            currenttime = "00:00:00";

                        String time;
                        Date x;
                        Calendar calendar3 = null;
                        time = currenttime;
                        x = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(time);
                        calendar3 = Calendar.getInstance();

                        calendar3.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), x.getHours(), x.getMinutes(), x.getSeconds());
                        x = calendar3.getTime();
                        if (x.after(calendar2.getTime()) && x.before(calendar1.getTime())) {
//                            imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_green));
                            isVisited = true;
                            isGreen = true;
                        }
                    }
                    if (arrTemp.size() == 0)
                        imageView.setBackground(getResources().getDrawable(R.drawable.custom_border));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ///////////////////////////
                tv2.setPadding(10, modifiedheight / 3,0, 0);
                tv2.setText((j - i)+"");
                ll.addView(imageView);
                ll.addView(tv2);
                if (isGreen)
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_green));
                else if (isVisited) {
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_pink));
                } else {
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border));
                }
                llTimings.addView(ll);
            }
        }
    }
}



/*

    private void addToArrayList(String strTime) {
        try {
            TimingDo timingDo = new TimingDo();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateParser = new SimpleDateFormat("h:mm a");
            Date date = dateParser.parse(strTime);
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");
            String time = dateFormater.format(date);
            Date date1 = new SimpleDateFormat("HH:mm").parse(time);
            calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), date1.getHours(), date1.getMinutes(), 00);

            Date date2 = calendar.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
            String date3 = format1.format(date2);
            timingDo.timing = date3;
            arrTempTiming.add(timingDo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    private void setAnimationForBoxes() {
        llTimings.removeAllViews();
        int j=arrReminder.size();
        String string1="",string2="",string3="";
        if (arrReminder!=null && arrReminder.size()>0){
            boolean isVisited= false;
            for (int i = 0; i < j; i++) {
                int height = llTimings.getHeight();
                int width = llTimings.getWidth();

                boolean isGreen=false;

                int modifiedheight = (int) (height / (arrReminder.size()));
                LinearLayout ll = new LinearLayout(this);
                ImageView imageView = new ImageView(this);
                TextView tv2 = new TextView(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width - 20, modifiedheight);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                imageView.setLayoutParams(params);
                ///////////////////////
                try {
                    Calendar calendar1 = Calendar.getInstance();
                    if (i == 0) {
                        string2 = arrReminder.get(i).date.toString();
                        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
                    } else {
                        string1 = string2;
                        string2 = arrReminder.get(i).date.toString();
                        Date time1 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(string1);
                        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), time1.getHours(), time1.getMinutes(), time1.getSeconds());
                    }
                    Date time2 = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH), time2.getHours(), time2.getMinutes(),  time2.getSeconds());

                    for (int k = 0; k < arrTemp.size(); k++) {
                        if (arrTemp != null && arrTemp.size() > 0)
                            currenttime = arrTemp.get(k).date.toString();
                        else
                            currenttime = "00:00:00";

                        String time;
                        Date x;
                        Calendar calendar3=null;
                        time = currenttime;
                        x = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN).parse(time);
                        calendar3 = Calendar.getInstance();

                        calendar3.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), x.getHours(), x.getMinutes(),  x.getSeconds());
                        x = calendar3.getTime();
                        if (x.after(calendar2.getTime()) && x.before(calendar1.getTime())) {
                            imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_green));
                            isVisited=true;
                            isGreen= true;
                        }
                    }
                    if(arrTemp.size()==0 )
                        imageView.setBackground(getResources().getDrawable(R.drawable.custom_border));

                } catch(ParseException e){
                    e.printStackTrace();
                }
                ///////////////////////////
                tv2.setPadding(0, modifiedheight / 2, 0, 0);
                tv2.setText("" + (j - i));
                ll.addView(imageView);
                ll.addView(tv2);
                if(isGreen)
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_green));
                else if(isVisited)
                {
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border_pink));
                }
                else
                {
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_border));
                }
                llTimings.addView(ll);
            }
        }
    }
*/


