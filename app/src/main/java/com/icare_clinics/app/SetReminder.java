package com.icare_clinics.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.adapter.ReminderTimeAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.SetReminderDo;
import com.icare_clinics.app.dataobject.SetTargetDO;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.TimingDo;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.icare_clinics.app.utilities.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.icare_clinics.app.R.id.view;

/**
 * Created by Sreekanth.P on 08-06-2017.
 */

public class SetReminder extends BaseActivity{
    private LinearLayout llMain,llTop,llManual;
    private ImageView ivUnSelectAuto,ivSelectAuto,ivUnSelectManual,ivSelectManual;
    private Button bntSet;
    private TextView tvManualReminder,bntAdd;
    private GridView gridViewTime;
    private ReminderTimeAdapter adapter;
    private ArrayList<SetReminderDo> arrReminder;
    private String temp,time;
    private String[] reminderTime;
    private Context context;
    private boolean isAutomatic=false,isManual=false;
    final Activity activity = this;
    private boolean isChecked=false;
    private USerDataDA mUserDa;
    //    private SetTargetDO setTargetDO;
    private SetReminderDo setReminderDo;
    private ArrayList<SetReminderDo>arrSetRemainder;
    private ArrayList<SetReminderDo>arrRemainders;
    private String date;
    private SettingDO settingDO;
    private int isRemainderSwitchOn=0;
    private int isRemainderAuto=1;
    @Override
    public void initialise() {
        llMain=(LinearLayout)inflater.inflate(R.layout.activity_set_reminder,null);

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        llMain.setLayoutParams(layoutParams);
        llBody.addView(llMain,layoutParams);
        context=SetReminder.this;
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.set_reminder);
        iv_fab.setVisibility(View.GONE);
        switchOnOff.setVisibility(View.VISIBLE);

        arrReminder=new ArrayList<SetReminderDo>();
        arrSetRemainder=new ArrayList<SetReminderDo>();

        setStatusBarColor();
        initialiseControls();
        loadData();

        adapter=new ReminderTimeAdapter(SetReminder.this,null);
        gridViewTime.setAdapter(adapter);

        gridViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SetReminderDo setReminderDo=new SetReminderDo();
                setReminderDo=arrReminder.get(i);
                view.setTag(R.string.water_intake_time_TAG,setReminderDo.hr + ":" + setReminderDo.mm);
                String time= (String) view.getTag(R.string.water_intake_time_TAG);
                BottomSheetDialogFragment bottomSheetDialogFragment = new WaterIntakeBottomBarFragment(time,i);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked=switchOnOff.isChecked();
                if (isChecked){
                    enableViews();
                }else {
                    disableViews();
                }
            }
        });

        ivUnSelectAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsForAuto();

                showCustomDialog(SetReminder.this,getString(R.string.reminder),getString(R.string.ml_of_water_popup), getString(R.string.OK), getString(R.string.cancel),"setReminder");
            }
        });
        ivUnSelectManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsForManual();
            }
        });
        bntSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAutomatic){
                    String time= (String) bntSet.getTag(R.string.tag_water_key);

                    preference.saveBooleanInPreference(Preference.IS_AUTOMATIC_REMAINDER,true);
                    preference.saveStringInPreference(Preference.IS_REMAINDER_SET,"Automatic");

                    insertDataIntoTable();
                    Intent intent=new Intent(SetReminder.this, WaterIntakeNew.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else if(isManual){
                    if(arrReminder==null||arrReminder.size()==0){
                        showCustomDialog(SetReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.pls_add_time), getResources().getString(R.string.ok), "", "");
                    }else{
                        if(temp!=null) {
                            isAutomatic = false;
                            for(int j=0;j<arrReminder.size();j++) {
                                time = arrReminder.get(j).hr + ":" + arrReminder.get(j).mm;
                                reminderTime = temp.split(":");
                                for (int i = 0; i < reminderTime.length; i++) {
                                    setAlaram(StringUtils.getInt(reminderTime[0]), StringUtils.getInt(reminderTime[1]));
                                }
                            }
//                        isAutomatic=false;
                            adapter.refresh(arrReminder);
//                            preference.saveListInPreference(Preference.MANUAL_LIST, arrReminder, 1);  // 0 for storage of SetRemainderDo
                            preference.saveBooleanInPreference(Preference.IS_AUTOMATIC_REMAINDER, false);
                            preference.saveStringInPreference(Preference.IS_REMAINDER_SET,"Manual");
                            insertDataIntoTable();
                            Intent intent = new Intent(SetReminder.this, WaterIntakeNew.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }else{
                    showCustomDialog(SetReminder.this, getResources().getString(R.string.alert), getResources().getString(R.string.select_Automatic_manual), getResources().getString(R.string.ok), "", "");
                }
            }
        });
        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvManualReminder.setVisibility(View.GONE);
                BottomSheetDialogFragment bottomSheetDialogFragment = new WaterIntakeBottomBarFragment(-1);
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
    }


    private void insertDataIntoTable() {

        mUserDa = new USerDataDA(SetReminder.this);
        mUserDa.insertWater(arrSetRemainder);

        settingDO=new SettingDO();
        settingDO.date=CalendarUtil.getPresentDate();
        settingDO.entityName= AppConstants.SET_REMAINDER;
        settingDO.value1=String.valueOf(isRemainderAuto);
        settingDO.value2=String.valueOf(isRemainderSwitchOn);
        mUserDa.insertSettings(settingDO);
    }

    @Override
    public void initialiseControls() {
        llTop=(LinearLayout)llMain.findViewById(R.id.llTop);
        llManual=(LinearLayout)llMain.findViewById(R.id.llManual);

        ivUnSelectAuto=(ImageView) llMain.findViewById(R.id.ivUnSelectAuto);
        ivUnSelectManual=(ImageView) llMain.findViewById(R.id.ivUnSelectManual);
        gridViewTime=(GridView) llMain.findViewById(R.id.gridViewTime);

        bntAdd=(TextView) llMain.findViewById(R.id.bntAdd);
        bntSet=(Button) llMain.findViewById(R.id.bntSet);

        tvManualReminder=(TextView) llMain.findViewById(R.id.tvManualReminder);

        ivUnSelectAuto.setEnabled(false);
        ivUnSelectManual.setEnabled(false);
    }
    @Override
    public void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String strDate=CalendarUtil.getPresentDate();
                String entityName=AppConstants.SET_REMAINDER;

                mUserDa = new USerDataDA(SetReminder.this);
                settingDO=new SettingDO();
                settingDO=mUserDa.getSettings(strDate,entityName);

                arrRemainders=new ArrayList<>();
                arrRemainders=mUserDa.getRemainders(strDate);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (settingDO.entityName.equalsIgnoreCase(AppConstants.SET_REMAINDER)){
                            int onOff=Integer.valueOf(settingDO.value2);//indicates switch is on or Off
                            int i=Integer.valueOf(settingDO.value1);//indicates is remainder is Automatic or not
                            if (onOff==1){//switch is on

                                switchOnOff.setChecked(true);
                                enableViews();
                                if (i==1){//is Remainder Auto
                                    setViewsForAuto();

                                }else {
                                    setViewsForManual();
                                }
                            }
                            else {
                                disableViews();
                            }
                        }

                        if (arrRemainders!=null && arrRemainders.size()>0){
                            for (int i=0;i<arrRemainders.size();i++){
                                String str1[]=(arrRemainders.get(i).date).split(" ");
                                String str2[]=str1[1].split(":");
                                setReminderDo=new SetReminderDo();
                                setReminderDo.hr=str2[0];
                                setReminderDo.mm=str2[1];
                                arrReminder.add(setReminderDo);
//                                arrSetRemainder.add(setReminderDo);
                            }

                            if (isRemainderSwitchOn==1 && isRemainderAuto!=1) {
                                tvManualReminder.setVisibility(View.GONE);
                                adapter.refresh(arrReminder);
                            }
                        }
                    }
                });
            }
        }).start();

    }

    private void enableViews() {
        isRemainderSwitchOn=1;
        ivUnSelectAuto.setClickable(true);
        ivUnSelectManual.setClickable(true);
        ivUnSelectAuto.setEnabled(true);
        ivUnSelectManual.setEnabled(true);
    }

    private void disableViews() {
        isRemainderSwitchOn=0;
        ivUnSelectManual.setImageResource(R.drawable.unselect_radio);
        ivUnSelectAuto.setImageResource(R.drawable.unselect_radio);
        ivUnSelectAuto.setClickable(false);
        ivUnSelectManual.setClickable(false);
        ivUnSelectAuto.setEnabled(false);
        ivUnSelectManual.setEnabled(false);
    }

    private void setViewsForManual() {
        isRemainderAuto=0;
        isAutomatic=false;
        isManual=true;
        ivUnSelectAuto.setImageResource(R.drawable.unselect_radio);
        ivUnSelectManual.setImageResource(R.drawable.select_radio);
        llManual.setVisibility(View.VISIBLE);
    }

    private void setViewsForAuto() {
        isRemainderAuto=1;
        isAutomatic=true;
        isManual=false;
        ivUnSelectAuto.setImageResource(R.drawable.select_radio);
        ivUnSelectManual.setImageResource(R.drawable.unselect_radio);
        llManual.setVisibility(View.GONE);

//        showCustomDialog(SetReminder.this,getString(R.string.reminder),getString(R.string.ml_of_water_popup), getString(R.string.OK), getString(R.string.cancel),"setReminder");
    }
    public void  SelectedTime(int hour,int min,int modifiedHour,int modifiedMinutes){
        temp=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMinutes);
        bntSet.setTag(R.string.tag_water_key, temp);
        SetReminderDo setReminderDo=new SetReminderDo();
        String mint;
        mint=convert(min);
        setReminderDo.hr=String.valueOf(hour);
        setReminderDo.mm=mint;
//        setReminderDo.formate=format;
        date=CalendarUtil.getDate()+" "+hour+":"+min+":"+"00";
        setReminderDo.date=date;
        arrSetRemainder.add(setReminderDo);
        sortArrayList(arrSetRemainder);

        arrReminder.add(setReminderDo);
        sortArrayList(arrReminder);
        adapter.refresh(arrReminder);
    }
    public void SelectedModifiedTime(int hour, final int min, int val, int modifiedHour, int modifiedMinutes){
        temp=String.valueOf(modifiedHour)+":"+String.valueOf(modifiedMinutes);
        bntSet.setTag(R.string.tag_water_key, temp);
        StringBuilder sb = new StringBuilder();
        SetReminderDo setReminderDo = new SetReminderDo();
        String timeMint;
        if(min<10){
            timeMint=convert(min);
        }else {
            timeMint=String.valueOf(min);
        }
        arrReminder.remove(val);
        setReminderDo.hr = String.valueOf(hour);
        setReminderDo.mm = timeMint;
//        setReminderDo.formate = format;
        date=CalendarUtil.getDate()+" "+hour+":"+min+":"+"00";
        setReminderDo.date=date;
        arrSetRemainder.add(setReminderDo);
        sortArrayList(arrSetRemainder);

        arrReminder.add(val, setReminderDo);
        sortArrayList(arrReminder);

        adapter.refresh(arrReminder);
    }

    public void sortArrayList(ArrayList<SetReminderDo> arrReminder) {
        Collections.sort(arrReminder, new Comparator<SetReminderDo>() {

            @Override
            public int compare(SetReminderDo o1, SetReminderDo o2) {

                String s1=o1.hr+":"+o1.mm;
                String s2=o2.hr+":"+o2.mm;
                try {
                    return new SimpleDateFormat("HH:mm").parse(s1).compareTo(new SimpleDateFormat("HH:mm").parse(s2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public static String convert(int n){
        return n < 10 ? "0" + n : "" + n;
    }
    public void setAlaram(int hr,int min){

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,00);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(calendar.getTime());
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
        Intent intent1=new Intent(getApplicationContext(),WaterReminderReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),_id,intent1,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,/*calendar.getTimeInMillis()*/timeCheck,AlarmManager.INTERVAL_DAY*7,pendingIntent);
    }
    @Override
    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase("setReminder")){

            if(isAutomatic){
                String [] eightTimes={"8:00","10:00","12:00","14:00","16:00","18:00","20:00","22:00"};
                for(int i=0;i<eightTimes.length;i++){
                    time=eightTimes[i];
                    reminderTime=time.split(":");
                    for(int j=0;j<reminderTime.length;j++) {
                        setAlaram(StringUtils.getInt(reminderTime[0]), StringUtils.getInt(reminderTime[1]));
                    }
                }
                insertDataIntoTable();
                if (arrRemainders!=null && arrRemainders.size()>0){
                    mUserDa.deleteReminders();
                }
                preference.saveBooleanInPreference(Preference.IS_AUTOMATIC_REMAINDER, true);
                preference.saveStringInPreference(Preference.IS_REMAINDER_SET,"Automatic");

                Intent intent=new Intent(SetReminder.this,WaterIntakeNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }
    @Override
    public void onButtonNoClick(String from) {
        ivUnSelectAuto.setImageResource(R.drawable.unselect_radio);
    }

}
