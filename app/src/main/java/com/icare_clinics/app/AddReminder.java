package com.icare_clinics.app;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.icare_clinics.app.BaseActivity;
import com.icare_clinics.app.ReminderReciever;
import com.icare_clinics.app.SelectRateTypeBottomSheet;
import com.icare_clinics.app.SelectTimePickerBottomFragment;
import com.icare_clinics.app.adapter.RadioDayAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.ReminderDA;
import com.icare_clinics.app.dataobject.ReminderDo;
import com.icare_clinics.app.utilities.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.attr.editable;
import static android.R.attr.format;
import static android.R.id.input;
import static com.google.android.gms.analytics.internal.zzy.ch;
import static com.icare_clinics.app.R.drawable.cal;
import static com.icare_clinics.app.R.string.enter_medicine;

public class AddReminder extends BaseActivity {

    LinearLayout llAddReminder,llTimings,llMonths,llDays,lldaysRoutine,llEdit;
    private TextView tvMonthDuration,tvDaysTotal,tvCapsulesDose,numOfCapsules,tvMedicineHeader,tvMedicineTime1,tvNumberOfTimings,tvMedicineTime2,tvMedicineTime3,tvMedicineTime4,tvMedicineTime5;
    private ImageView ivSubtractCapsules,ivAddCapsules,ivSelecttedTablets,ivunSelecttedCapsules,ivunSelecttedDroplets,ivunSelecttedInjection,ivEdit;
    private EditText etMedicineName;
     private ImageView ivClear;
    private SeekBar seekBarMonth;
    private int selPagerPostion = 0;
    String alltiming;
    private ReminderDA mReminderDA;
    private ArrayList<String> arrDays;
    HashMap<String,Boolean> hmDaysSattus= new HashMap<String, Boolean>();
    RadioDayAdapter adapter;
    public ReminderDo reminderDo;
    private String from="",numOfTime;
    private ListView lvRadioButton;
    AlertDialog alertDialog1;
    CharSequence days;
    private Button btnDone;
    String tmp,tmp2,tmp3,tmp4,tmp5;
    String temp1,temp2,temp3,temp4,temp5;
    private Intent intent;
    int prog;
    private final int MON=2,TUE=3,WED=4,THUR=5,FRI=6,SAT=7,SUN=1;

    String[] values = {"Everyday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private String time1,time2,time3,time4,time5;

    private String []  reminderTime1,reminderTime2,reminderTime3,reminderTime4,reminderTime5;

    boolean isDays=false,isMonth=false,isTablet=true,isCapsule=false,isDroplet=false,isInjection=false;
    String daysTotalDoase="",medicineName,numberOfCapsul,timeofDose,duration,typeOfMedicine;
    int count=1;
    private boolean isText1=true,isText2=false,isText3=false,isText4=false,isText5=false,isEveryday=true;
    private boolean isMon=true,isTue=true,isWed=true,isThus=true,isFri=true,isSat=true,isSun=true;
    String selectedDays="";
    String posData="100";
    protected Context context_new;


    @Override
    public void initialise() {
        context_new=AddReminder.this;
        llAddReminder = (LinearLayout) inflater.inflate(R.layout.activity_add_reminder, null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llAddReminder, param);
        setStatusBarColor();

        arrDays=new ArrayList<String>();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText("SAVE");
        tvCancel.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText("Add Reminder");
        iv_fab.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        tmp="8:00";
        temp1="8:00";

        mReminderDA=new ReminderDA(AddReminder.this);

        if(getIntent().hasExtra("ReminderDo")){
            reminderDo = (ReminderDo) getIntent().getSerializableExtra("ReminderDo");

            from="MyReminder";
        }
        if(getIntent().hasExtra("position")){
            posData=getIntent().getExtras().getString("position");

            from="MyReminder";
        }

        initialiseControls();
        initialiseHashMap();
        if(reminderDo!=null) {
            Boolean isEverday=false;
            String dayfrom[]=reminderDo.days.split(",");

            for (String dTemp : dayfrom) {
                if(!(dTemp.trim()).equalsIgnoreCase("Everyday")) {
                    isEverday=false;
                    if (hmDaysSattus.containsKey(dTemp.trim())) {
                        hmDaysSattus.remove(dTemp.trim());
                        hmDaysSattus.put(dTemp.trim(), true);
                    } else {
                        hmDaysSattus.put(dTemp.trim(), true);
                    }
                }else{
                    isEverday=true;
                }

            }
            if(!isEverday){
                if (hmDaysSattus.containsKey("Everyday")) {
                    hmDaysSattus.remove("Everyday");
                    hmDaysSattus.put("Everyday", false);
                } else {
                    hmDaysSattus.put("Everyday", false);
                }
            }
        }

        seekBarMonth.getProgressDrawable().setColorFilter(getResources().getColor(R.color.dark_blue2), PorterDuff.Mode.MULTIPLY);
        prog=seekBarMonth.getProgress();
        tvMonthDuration.setText(prog + " days");

        seekBarMonth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                tvMonthDuration.setText(progress + " days");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                prog=progress;
                tvMonthDuration.setText(progress + " days");
            }
        });
        ivSubtractCapsules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<=1)
                {
                    count=1;
                }else{
                    count--;
                    numOfCapsules.setText(String.valueOf(count));
                    //  tvCapsulesDose.setText(String.valueOf(count) + " capsules");
                }
            }
        });
        ivAddCapsules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                numOfCapsules.setText(String.valueOf(count));
                //   tvCapsulesDose.setText(String.valueOf(count) + " capsules");
            }
        });
        if(isTablet){
            typeOfMedicine="tablet";
            ivSelecttedTablets.setImageResource(R.drawable.select_tablets);
            ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
            ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
            ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
        }
        ivSelecttedTablets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTablet=true;
                isCapsule=false;
                isDroplet=false;
                isInjection=false;
                if(isTablet){
                    typeOfMedicine="Tablet";
                    ivSelecttedTablets.setImageResource(R.drawable.select_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }
            }
        });
        ivunSelecttedCapsules.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isTablet=false;
                isCapsule=true;
                isDroplet=false;
                isInjection=false;
                if (isCapsule){
                    typeOfMedicine="Capsule";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.select_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }
            }
        });
        ivunSelecttedDroplets.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isTablet=false;
                isCapsule=false;
                isDroplet=true;
                isInjection=false;
                if (isDroplet){
                    typeOfMedicine="Drop";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.select_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }
            }
        });
        ivunSelecttedInjection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isTablet=false;
                isCapsule=false;
                isDroplet=false;
                isInjection=true;
                if (isInjection){
                    typeOfMedicine="Injection";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.select_injection);
                }
            }
        });
        llDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custam_day_reminder);

                lvRadioButton=(ListView)dialog.findViewById(R.id.lvRadioButton);

                btnDone = (Button)dialog.findViewById(R.id.btnDone);
                adapter=new RadioDayAdapter(context,values,hmDaysSattus);
                lvRadioButton.setAdapter(adapter);
                days=tvDaysTotal.getText().toString();

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        selectedDays="";
                        for (String strSelected:values ) {
                            if(hmDaysSattus.get(strSelected)){
                                if(TextUtils.isEmpty(selectedDays)){
                                    selectedDays=strSelected;
                                }else{
                                    selectedDays+=", "+strSelected;
                                }
                            }
                        }
                        tvDaysTotal.setText(selectedDays);

                        dialog.dismiss();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
        tvMedicineTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                time1= (String) tvMedicineTime1.getTag(R.string.tag1_key);
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(time1,1);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });
        tvMedicineTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                time1= (String) tvMedicineTime1.getTag(R.string.tag1_key);*/
                time2= (String) tvMedicineTime2.getTag(R.string.tag2_key);


                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(time2,2);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        tvMedicineTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                time3= (String) tvMedicineTime3.getTag(R.string.tag3_key);

                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(time3,3);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        tvMedicineTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time4= (String) tvMedicineTime4.getTag(R.string.tag4_key);

                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(time4,4);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        tvMedicineTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time5= (String) tvMedicineTime5.getTag(R.string.tag5_key);

                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectTimePickerBottomFragment(time5,5);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* Bundle bundle = new Bundle();
                bundle.putInt("position", selPagerPostion);
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectRateTypeBottomSheet();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());*/
                final String[] strRateType=new String[]{"Once a day","Twice a day","Three times a day","Four times a day","Five times a day"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context_new);
                builder.setTitle("Select Number of Timings");
                builder.setItems(strRateType, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int item) {

                        selectTimingText(item);
                        dialog.dismiss();
                    }

                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        MyTextWatcher tw = new MyTextWatcher();
        tw.setView(etMedicineName);
        etMedicineName.addTextChangedListener(tw);

        if (etMedicineName.getText().toString().equalsIgnoreCase("")){
            ivClear.setVisibility(View.GONE);
        }
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMedicineName.setText("");
                etMedicineName.setHint(getResources().getString(R.string.enter_medicine));
                ivClear.setVisibility(View.GONE);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicineName=etMedicineName.getText().toString();
                numberOfCapsul=numOfCapsules.getText().toString();
                numOfTime=tvNumberOfTimings.getText().toString();
                if(isText1){
                    alltiming=tvMedicineTime1.getText().toString();
                }else if(isText2){
                    alltiming=tvMedicineTime1.getText().toString()+", "+tvMedicineTime2.getText().toString();
                }else if(isText3){
                    alltiming=tvMedicineTime1.getText().toString()+", "+tvMedicineTime2.getText().toString()+", "+tvMedicineTime3.getText().toString();
                }else if(isText4){
                    alltiming=tvMedicineTime1.getText().toString()+", "+tvMedicineTime2.getText().toString()+", "+tvMedicineTime3.getText().toString()+", "+tvMedicineTime4.getText().toString();
                }else if(isText5){
                    alltiming=tvMedicineTime1.getText().toString()+", "+tvMedicineTime2.getText().toString()+", "+tvMedicineTime3.getText().toString()+", "+tvMedicineTime4.getText().toString()+", "+tvMedicineTime5.getText().toString();
                }
                daysTotalDoase=tvDaysTotal.getText().toString();
                duration=String.valueOf(prog)/*tvMonthDuration.getText().toString()*/;
                if(medicineName.equalsIgnoreCase("")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_medicine_name), getResources().getString(R.string.ok), "", "");
                }else if(numberOfCapsul.equalsIgnoreCase("0")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plz_add_number_of_capsule), getResources().getString(R.string.ok), "", "");
                }else if(typeOfMedicine.equalsIgnoreCase("")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzselect_medicine_type), getResources().getString(R.string.ok), "", "");
                }
                /*else if(timeofDose.equalsIgnoreCase("")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plz_select_timing), getResources().getString(R.string.ok), "", "");
                }*/else if(daysTotalDoase.equalsIgnoreCase("")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plz_select_days), getResources().getString(R.string.ok), "", "");
                }else if(duration.equalsIgnoreCase("")){
                    showCustomDialog(AddReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.plz_add_duration), getResources().getString(R.string.ok), "", "");
                }else{

                    if(reminderDo!=null){

                        reminderDo.medicineName=medicineName;
                        reminderDo.numberOfCapsules=numberOfCapsul;
                        reminderDo.timing=alltiming;
                        reminderDo.medicineType=typeOfMedicine;
                        reminderDo.days=daysTotalDoase;
                        reminderDo.duration=duration;
                        reminderDo.timeType=numOfTime;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                mReminderDA.updateReminder(reminderDo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent();

                                        int pos=Integer.parseInt(posData);
                                        intent.putExtra("position",String.valueOf(pos));
                                        intent.putExtra("ADD_REMINDER", reminderDo);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            }
                        }).start();


                    }else {

                        final ReminderDo reminderDo=new ReminderDo();
                        reminderDo.medicineName=medicineName;
                        reminderDo.numberOfCapsules=numberOfCapsul;
                        reminderDo.timing=alltiming;
                        reminderDo.medicineType=typeOfMedicine;
                        reminderDo.days=daysTotalDoase;
                        reminderDo.duration=duration;
                        reminderDo.timeType=numOfTime;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                mReminderDA.insertReminder(reminderDo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent();
                                        String posData=getIntent().getExtras().getString("position");;
                                        int pos=Integer.parseInt(posData);
                                        intent.putExtra("position",String.valueOf(pos));
                                        intent.putExtra("ADD_REMINDER", reminderDo);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            }
                        }).start();


                    }
                    String [] daysRepeat=daysTotalDoase.split(",");
                    if(numOfTime.equals("Once a day")){
                        reminderTime1=temp1.split(":");

                        for(int i=0;i<daysRepeat.length;i++) {
                            setAlaram(StringUtils.getInt(reminderTime1[0]), StringUtils.getInt(reminderTime1[1]), daysRepeat[i]);
                        }
                    }else if(numOfTime.equals("Twice a day")){
                        reminderTime1=temp1.split(":");
                        reminderTime2=temp2.split(":");
                        for(int i=0;i<daysRepeat.length;i++) {
                            setAlaram(StringUtils.getInt(reminderTime1[0]), StringUtils.getInt(reminderTime1[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime2[0]), StringUtils.getInt(reminderTime2[1]),  daysRepeat[i]);
                        }

                    }else if(numOfTime.equals("Three times a day")){
                        reminderTime1=temp1.split(":");
                        reminderTime2=temp2.split(":");
                        reminderTime3=temp3.split(":");
                        for(int i=0;i<daysRepeat.length;i++) {
                            setAlaram(StringUtils.getInt(reminderTime1[0]), StringUtils.getInt(reminderTime1[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime2[0]), StringUtils.getInt(reminderTime2[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime3[0]), StringUtils.getInt(reminderTime3[1]), daysRepeat[i]);
                        }
                    }else if(numOfTime.equals("Four times a day")){
                        reminderTime1=temp1.split(":");
                        reminderTime2=temp2.split(":");
                        reminderTime3=temp3.split(":");
                        reminderTime4=temp4.split(":");
                        for(int i=0;i<daysRepeat.length;i++) {
                            setAlaram(StringUtils.getInt(reminderTime1[0]), StringUtils.getInt(reminderTime1[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime2[0]), StringUtils.getInt(reminderTime2[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime3[0]), StringUtils.getInt(reminderTime3[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime4[0]), StringUtils.getInt(reminderTime4[1]), daysRepeat[i]);
                        }
                    }else if(numOfTime.equals("Five times a day")){
                        reminderTime1=temp1.split(":");
                        reminderTime2=temp2.split(":");
                        reminderTime3=temp3.split(":");
                        reminderTime4=temp4.split(":");
                        reminderTime5=temp5.split(":");
                        for(int i=0;i<daysRepeat.length;i++) {
                            setAlaram(StringUtils.getInt(reminderTime1[0]), StringUtils.getInt(reminderTime1[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime2[0]), StringUtils.getInt(reminderTime2[1]),daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime3[0]), StringUtils.getInt(reminderTime3[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime4[0]), StringUtils.getInt(reminderTime4[1]), daysRepeat[i]);
                            setAlaram(StringUtils.getInt(reminderTime5[0]), StringUtils.getInt(reminderTime5[1]),daysRepeat[i]);
                        }
                    }

                }

            }
        });
    }
    public void initialiseHashMap(){
        hmDaysSattus.clear();
        int i=0;
        for (String strTemp:values ) {
            if(i==0)
                hmDaysSattus.put(strTemp,true);
            else
                hmDaysSattus.put(strTemp,false);
            i++;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
       /* String strTime;
        if(requestCode ==1)
        {
            if (resultCode == RESULT_OK) {
                strTime=intent.getStringExtra("TIME");
                tvTimingsFinal.setText(strTime);
            }

        }*/
    }
    @Override
    public void initialiseControls() {
        llTimings=(LinearLayout)llAddReminder.findViewById(R.id.llTimings);
        // lldaysRoutine=(LinearLayout)llAddReminder.findViewById(R.id.lldaysRoutine);
        llMonths=(LinearLayout)llAddReminder.findViewById(R.id.llMonths);
        llDays=(LinearLayout)llAddReminder.findViewById(R.id.llDays);
        llEdit=(LinearLayout)llAddReminder.findViewById(R.id.llEdit);
        tvMonthDuration=(TextView) llAddReminder.findViewById(R.id.tvMonthDuration);
        tvDaysTotal=(TextView) llAddReminder.findViewById(R.id.tvDaysTotal);

        tvMedicineHeader=(TextView) llAddReminder.findViewById(R.id.tvMedicineHeader);
        tvNumberOfTimings=(TextView) llAddReminder.findViewById(R.id.tvNumberOfTimings);

        tvMedicineTime1=(TextView) llAddReminder.findViewById(R.id.tvMedicineTime1);
        tvMedicineTime2=(TextView) llAddReminder.findViewById(R.id.tvMedicineTime2);
        tvMedicineTime3=(TextView) llAddReminder.findViewById(R.id.tvMedicineTime3);
        tvMedicineTime4=(TextView) llAddReminder.findViewById(R.id.tvMedicineTime4);
        tvMedicineTime5=(TextView) llAddReminder.findViewById(R.id.tvMedicineTime5);
        // tvTimingsFinal=(TextView) llAddReminder.findViewById(R.id.tvTimingsFinal);
        numOfCapsules=(TextView) llAddReminder.findViewById(R.id.numOfCapsules);
        ivSubtractCapsules=(ImageView) llAddReminder.findViewById(R.id.ivSubtractCapsules);
        ivAddCapsules=(ImageView) llAddReminder.findViewById(R.id.ivAddCapsules);
        etMedicineName=(EditText) llAddReminder.findViewById(R.id.etMedicineName);
        seekBarMonth=(SeekBar) llAddReminder.findViewById(R.id.seekBarMonth);

        ivSelecttedTablets=(ImageView) llAddReminder.findViewById(R.id.ivSelecttedTablets);
        ivunSelecttedCapsules=(ImageView) llAddReminder.findViewById(R.id.ivunSelecttedCapsules);
        ivunSelecttedDroplets=(ImageView) llAddReminder.findViewById(R.id.ivunSelecttedDroplets);
        ivunSelecttedInjection=(ImageView) llAddReminder.findViewById(R.id.ivunSelecttedInjection);
        ivEdit=(ImageView) llAddReminder.findViewById(R.id.ivEdit);

        ivClear=(ImageView) llAddReminder.findViewById(R.id.ivClear);

        tvMedicineHeader.setTypeface(AppConstants.UbuntuMedium);

        alltiming=tvMedicineTime1.getText().toString();

        if(reminderDo!=null){
            etMedicineName.setText(reminderDo.medicineName);
            numOfCapsules.setText(reminderDo.numberOfCapsules);
            count= StringUtils.getInt(reminderDo.numberOfCapsules);

            tvDaysTotal.setText(reminderDo.days);
            duration=reminderDo.duration;
            seekBarMonth.setProgress(Integer.parseInt(duration));
            tvMonthDuration.setText(duration + " days");
            String type=reminderDo.medicineType;
            String tp=reminderDo.timeType;
            String timeing=reminderDo.timing;
            if(tp.equals("Once a day")){
                isText1=true;
                isText2=false;
                isText3=false;
                isText4=false;
                isText5=false;
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.GONE);
                tvMedicineTime3.setVisibility(View.GONE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvNumberOfTimings.setText(tp);
                tvMedicineTime1.setText(timeing);
                tmp="8:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                temp1="8:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
            }else if(tp.equals("Twice a day")){
                isText1=false;
                isText2=true;
                isText3=false;
                isText4=false;
                isText5=false;
                String[] animalsArray = timeing.split(",");
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.GONE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvNumberOfTimings.setText(tp);
                tvMedicineTime1.setText(animalsArray[0]);
                tvMedicineTime2.setText(animalsArray[1]);

                tmp="8:00";
                tmp2="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                temp1="8:00";
                temp2="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
            }else if(tp.equals("Three times a day")){
                isText1=false;
                isText2=false;
                isText3=true;
                isText4=false;
                isText5=false;
                String[] animalsArray = timeing.split(",");
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvNumberOfTimings.setText(tp);
                tvMedicineTime1.setText(animalsArray[0]);
                tvMedicineTime2.setText(animalsArray[1]);
                tvMedicineTime3.setText(animalsArray[2]);

                tmp="8:00";
                tmp2="15:30";
                tmp3="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);
                temp1="8:00";
                temp2="15:30";
                temp3="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);


            }else if(tp.equals("Four times a day")){
                isText1=false;
                isText2=false;
                isText3=false;
                isText4=true;
                isText5=false;
                String[] animalsArray = timeing.split(",");
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.VISIBLE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvNumberOfTimings.setText(tp);
                tvMedicineTime1.setText(animalsArray[0]);
                tvMedicineTime2.setText(animalsArray[1]);
                tvMedicineTime3.setText(animalsArray[2]);
                tvMedicineTime4.setText(animalsArray[3]);

                tmp="8:00";
                tmp2="13:00";
                tmp3="19:00";
                tmp4="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);
                tvMedicineTime4.setTag(R.string.tag4_key,tmp4);
                temp1="8:00";
                temp2="13:00";
                temp3="19:00";
                temp4="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);
                tvMedicineTime4.setTag(R.string.tag4_new_key,temp4);

            }else if(tp.equals("Five times a day")){
                isText1=false;
                isText2=false;
                isText3=false;
                isText4=false;
                isText5=true;
                String[] animalsArray = timeing.split(",");
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.VISIBLE);
                tvMedicineTime5.setVisibility(View.VISIBLE);
                tvNumberOfTimings.setText(tp);
                tvMedicineTime1.setText(animalsArray[0]);
                tvMedicineTime2.setText(animalsArray[1]);
                tvMedicineTime3.setText(animalsArray[2]);
                tvMedicineTime4.setText(animalsArray[3]);
                tvMedicineTime5.setText(animalsArray[4]);

                tmp="8:00";
                tmp2="11:45";
                tmp3="15:30";
                tmp4="19:15";
                tmp5="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);
                tvMedicineTime4.setTag(R.string.tag4_key,tmp4);
                tvMedicineTime5.setTag(R.string.tag5_key,tmp5);

                temp1="8:00";
                temp2="11:45";
                temp3="15:30";
                temp4="19:15";
                temp5="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);
                tvMedicineTime4.setTag(R.string.tag4_new_key,temp4);
                tvMedicineTime5.setTag(R.string.tag5_new_key,temp5);
            }
            if(type.equals("Tablet")){

                isTablet=true;
                isCapsule=false;
                isDroplet=false;
                isInjection=false;
                if(isTablet){
                    typeOfMedicine="Tablet";
                    ivSelecttedTablets.setImageResource(R.drawable.select_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }
            }else  if(type.equals("Capsule")){
                isTablet=false;
                isCapsule=true;
                isDroplet=false;
                isInjection=false;
                if (isCapsule){
                    typeOfMedicine="Capsule";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.select_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }
            }else  if(type.equals("Drop")){
                isTablet=false;
                isCapsule=false;
                isDroplet=true;
                isInjection=false;
                if (isDroplet){
                    typeOfMedicine="Drop";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.select_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.unselect_injection);
                }

            }else  if(type.equals("Injection")){
                isTablet=false;
                isCapsule=false;
                isDroplet=false;
                isInjection=true;
                if (isInjection){
                    typeOfMedicine="Injection";
                    ivSelecttedTablets.setImageResource(R.drawable.unselect_tablets);
                    ivunSelecttedCapsules.setImageResource(R.drawable.unselect_capsules);
                    ivunSelecttedDroplets.setImageResource(R.drawable.unselect_droplet);
                    ivunSelecttedInjection.setImageResource(R.drawable.select_injection);
                }
            }
        }

    }

    @Override
    public void loadData() {

    }
    public void SetAlaramTime(int modifiedHour,int modifiedMin,int val){
        if(val==1) {
            String tm=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin)+format;
            temp1=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin);
            tvMedicineTime1.setTag(R.string.tag1_new_key, temp1);
            // tvMedicineTime1.setText(tm);
        }else if(val==2){
            String tm=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin)+format;
            temp2=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin);
            tvMedicineTime2.setTag(R.string.tag2_new_key, temp2);
            //  tvMedicineTime2.setText(tm);
        }else if(val==3){
            String tm=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin)+format;
            temp3=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin);
            tvMedicineTime3.setTag(R.string.tag3_new_key, temp3);
            // tvMedicineTime3.setText(tm);
        }else if(val==4){
            String tm=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin)+format;
            temp4=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin);
            tvMedicineTime4.setTag(R.string.tag4_new_key, temp4);
            //  tvMedicineTime4.setText(tm);
        }else if(val==5){
            String tm=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin)+format;
            temp5=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMin);
            tvMedicineTime5.setTag(R.string.tag5_new_key, temp5);
            // tvMedicineTime5.setText(tm);
        }
    }
    public void SelectedTime(int hour,int min,int val){
        String timeMint,format;
        if(min<10){
            timeMint=convert(min);
        }else {
            timeMint=String.valueOf(min);
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

        if(val==1) {
            String tm=String.valueOf(hour)+":"+String.valueOf(timeMint)+" "+format;
            tmp=String.valueOf(hour)+":"+String.valueOf(timeMint);
            tvMedicineTime1.setTag(R.string.tag1_key, tmp);
            tvMedicineTime1.setText(tm);
        }else if(val==2){
            String tm=String.valueOf(hour)+":"+String.valueOf(timeMint)+" "+format;
            tmp2=String.valueOf(hour)+":"+String.valueOf(timeMint);
            tvMedicineTime2.setTag(R.string.tag2_key, tmp2);
            tvMedicineTime2.setText(tm);
        }else if(val==3){
            String tm=String.valueOf(hour)+":"+String.valueOf(timeMint)+" "+format;
            tmp3=String.valueOf(hour)+":"+String.valueOf(timeMint);
            tvMedicineTime3.setTag(R.string.tag3_key, tmp3);
            tvMedicineTime3.setText(tm);
        }else if(val==4){
            String tm=String.valueOf(hour)+":"+String.valueOf(timeMint)+" "+format;
            tmp4=String.valueOf(hour)+":"+String.valueOf(timeMint);
            tvMedicineTime4.setTag(R.string.tag4_key, tmp4);
            tvMedicineTime4.setText(tm);
        }else if(val==5){
            String tm=String.valueOf(hour)+":"+String.valueOf(timeMint)+" "+format;
            tmp5=String.valueOf(hour)+":"+String.valueOf(timeMint);
            tvMedicineTime5.setTag(R.string.tag5_key, tmp5);
            tvMedicineTime5.setText(tm);
        }
    }
    public void showPosition(int position)
    {
        //Toast.makeText(getApplicationContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
        selectTimingText(position);
    }

    public void selectTimingText(int count){
        switch (count) {
            case 0:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.GONE);
                tvMedicineTime3.setVisibility(View.GONE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvMedicineTime1.setText("08:00 AM");

                tvNumberOfTimings.setText("Once a day");

                isText1=true;
                isText2=false;
                isText3=false;
                isText4=false;
                isText5=false;
                // alltiming=tvMedicineTime1.getText().toString();
                tmp="8:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);

                temp1="8:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                /// return @[@"08:00 AM"];
                break;
            case 1:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.GONE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvMedicineTime1.setText("08:00 AM");
                tvMedicineTime2.setText("11:00 PM");
                tvNumberOfTimings.setText("Twice a day");
                //   alltiming=tvMedicineTime1.getText().toString()+","+tvMedicineTime2.getText().toString();

                isText1=false;
                isText2=true;
                isText3=false;
                isText4=false;
                isText5=false;

                tmp="8:00";
                tmp2="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);

                temp1="8:00";
                temp2="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);

                //  time2= (String) tvMedicineTime2.getTag(R.string.tag2_key);
                //return @[@"08:00 AM",@"11:00 PM"];
                break;
            case 2:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvMedicineTime1.setText("08:00 AM");
                tvMedicineTime2.setText("03:30 PM");
                tvMedicineTime3.setText("11:00 PM");
                tvNumberOfTimings.setText("Three times a day");


                isText1=false;
                isText2=false;
                isText3=true;
                isText4=false;
                isText5=false;

                tmp="8:00";
                tmp2="15:30";
                tmp3="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);

                temp1="8:00";
                temp2="15:30";
                temp3="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);
                //return @[@"08:00 AM",@"03:30 PM",@"11:00 PM"];
                break;
            case 3:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.VISIBLE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvMedicineTime1.setText("08:00 AM");
                tvMedicineTime2.setText("01:00 PM");
                tvMedicineTime3.setText("07:00 PM");
                tvMedicineTime4.setText("11:00 PM");

                isText1=false;
                isText2=false;
                isText3=false;
                isText4=true;
                isText5=false;
                tvNumberOfTimings.setText("Four times a day");
                tmp="8:00";
                tmp2="13:00";
                tmp3="19:00";
                tmp4="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);
                tvMedicineTime4.setTag(R.string.tag4_key,tmp4);

                temp1="8:00";
                temp2="13:00";
                temp3="19:00";
                temp4="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);
                tvMedicineTime4.setTag(R.string.tag4_new_key,temp4);
                // return @[@"08:00 AM",@"01:00 PM",@"07:00 PM",@"11:00 PM"];
                break;
            case 4:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.VISIBLE);
                tvMedicineTime3.setVisibility(View.VISIBLE);
                tvMedicineTime4.setVisibility(View.VISIBLE);
                tvMedicineTime5.setVisibility(View.VISIBLE);


                isText1=false;
                isText2=false;
                isText3=false;
                isText4=false;
                isText5=true;
                tvNumberOfTimings.setText("Five times a day");
                tvMedicineTime1.setText("08:00 AM");
                tvMedicineTime2.setText("11:45 AM");
                tvMedicineTime3.setText("03:30 PM");
                tvMedicineTime4.setText("07:15 PM");
                tvMedicineTime5.setText("11:00 PM");

                tmp="8:00";
                tmp2="11:45";
                tmp3="15:30";
                tmp4="19:15";
                tmp5="23:00";
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);
                tvMedicineTime2.setTag(R.string.tag2_key,tmp2);
                tvMedicineTime3.setTag(R.string.tag3_key,tmp3);
                tvMedicineTime4.setTag(R.string.tag4_key,tmp4);
                tvMedicineTime5.setTag(R.string.tag5_key,tmp5);

                temp1="8:00";
                temp2="11:45";
                temp3="15:30";
                temp4="19:15";
                temp5="23:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);
                tvMedicineTime2.setTag(R.string.tag2_new_key,temp2);
                tvMedicineTime3.setTag(R.string.tag3_new_key,temp3);
                tvMedicineTime4.setTag(R.string.tag4_new_key,temp4);
                tvMedicineTime5.setTag(R.string.tag5_new_key,temp5);
                // return @[@"08:00 AM",@"11:45 AM",@"03:30 PM",@"07:15 PM",@"11:00 PM"];
                break;
            default:
                tvMedicineTime1.setVisibility(View.VISIBLE);
                tvMedicineTime2.setVisibility(View.GONE);
                tvMedicineTime3.setVisibility(View.GONE);
                tvMedicineTime4.setVisibility(View.GONE);
                tvMedicineTime5.setVisibility(View.GONE);
                tvMedicineTime1.setText("08:00 AM");
                tmp="8:00";
                tvNumberOfTimings.setText("Once a day");
                tvMedicineTime1.setTag(R.string.tag1_key,tmp);

                temp1="8:00";
                tvMedicineTime1.setTag(R.string.tag1_new_key,temp1);

                // return @[@"08:00 AM"];
                break;    }
    }
    public void setAlaram(int hr,int min,String day){

        Calendar calendar=Calendar.getInstance();
        if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        if(day.equals("Everyday")){
            //   calendar.set(Calendar.DAY_OF_WEEK, day);
           /* calendar.set(Calendar.DAY_OF_WEEK, MON);
            calendar.set(Calendar.DAY_OF_WEEK, TUE);
            calendar.set(Calendar.DAY_OF_WEEK, WED);
            calendar.set(Calendar.DAY_OF_WEEK, THUR);
            calendar.set(Calendar.DAY_OF_WEEK, FRI);
            calendar.set(Calendar.DAY_OF_WEEK, SAT);
            calendar.set(Calendar.DAY_OF_WEEK, SUN);*/
            setDateAlaram(calendar,hr,min);

        }else if(day.equals("Monday")){
            calendar.set(Calendar.DAY_OF_WEEK, MON);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Tuesday")){
            calendar.set(Calendar.DAY_OF_WEEK, TUE);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Wednesday")){
            calendar.set(Calendar.DAY_OF_WEEK, WED);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Thursday")){
            calendar.set(Calendar.DAY_OF_WEEK, THUR);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Friday")){
            calendar.set(Calendar.DAY_OF_WEEK, FRI);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Saturday")){
            calendar.set(Calendar.DAY_OF_WEEK, SAT);
            setDateAlaram(calendar,hr,min);
        }else if(day.equals("Sunday")){
            calendar.set(Calendar.DAY_OF_WEEK, SUN);
            setDateAlaram(calendar,hr,min);
        }




    }
    public void setDateAlaram(Calendar calendar,int hr,int min){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,00);
        if(calendar.before(test)){//if its in the past increment
            calendar.add(Calendar.DATE,1);
        }

        long timeCheck=0;
        Calendar now = Calendar.getInstance();
        if(calendar.getTimeInMillis() <= now.getTimeInMillis())
            timeCheck = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            timeCheck = calendar.getTimeInMillis();

        final int _id = (int) System.currentTimeMillis();
        Intent intent1=new Intent(getApplicationContext(),ReminderReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),_id,intent1,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeCheck,AlarmManager.INTERVAL_DAY*7,pendingIntent);
    }

    public static String convert(int n){
        return n < 10 ? "0" + n : "" + n;
    }

   private class MyTextWatcher implements TextWatcher
    {
        boolean isChange=false;
        EditText et;
        public void setView(EditText view)
        {
            et = (EditText)view;
        }
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            ivClear.setVisibility(View.VISIBLE);
            if (arg0.toString().equalsIgnoreCase("")){
                ivClear.setVisibility(View.GONE);
            }

            if(!isChange && arg0.toString().length()<=2)
            {
                String str = et.getText().toString();
                isChange = true;
                et.setText(str.substring(0,1).toUpperCase() + str.substring(1));
                et.setSelection( et.getText().length(),  et.getText().length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (arg0.toString().length()<=0){
                isChange = false;
            }
        }

        @Override
        public void afterTextChanged(Editable arg0) {
        }
    }
}
