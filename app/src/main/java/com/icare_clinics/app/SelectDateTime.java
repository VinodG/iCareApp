package com.icare_clinics.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.adapter.DoctorsTimeAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

public class SelectDateTime extends BaseActivity {
    private RadioGroup radioGroupEvening, radioGroupAfternoon, radioGroupMorning;
    private Button morningTime, afternoonTime, eveningTime, btnProceed;
    private ImageView img_doctor,ivBackImage;
    private LinearLayout llMorning, llAfternoon, llEvening;
    private LinearLayout llTab, llTab2;
    private ViewGroup llSelectDate;
    private TabLayout tabLayout;
    private GridView gridViewTime;
    private ArrayList<View> view;
    private TextView tvDay, tvDate, tvMonth, appoinmentDate, tvConfirmDateTime;
    private String d, m, selectedTime, selectedDate, date;
    private String[] nameOfDay = new String[7];
    private String[] dates = new String[7];
    private String[] month = new String[7];
    private String[] year = new String[7];
    private String[] morningAppointmentTime = {"8:00Am", "8:30Am", "9:00Am", "9:30Am", "10:00Am", "10:30Am", "11:00Am", "11:30Am"};
    private String[] appointmentTime;
    private boolean isMorning = true, isAfternoon = false, isEvening = false;
    private DoctorsTimeAdapter adapter;
    // private boolean isRadioFalg = true, isEditFalg = true;

    @Override
    public void initialise() {

        llSelectDate = (ViewGroup) inflater.inflate(R.layout.activity_select_date_time, null);
        llSelectDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llSelectDate);
        toolbar.setVisibility(View.GONE);
        initialiseControls();
        view = new ArrayList<View>();
        String[] sevenDayDate = CalendarUtil.getNextSevenDays();
        for (int i = 0; i < 7; i++) {
            String[] parts = sevenDayDate[i].split(",");
            nameOfDay[i] = parts[0];
            dates[i] = parts[1];
            month[i] = parts[2];
            year[i] = parts[3];
        }

        for (int i = 0; i < 7; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab " + (i + 1)));

            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            tabLayout.getTabAt(0).select();
                        }
                    }, 100);
        }
        for (int i = 0; i < 7; i++) {
            llTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_appointment_dmy, null);
            tvDate = (TextView) llTab.findViewById(R.id.date);
            tvDay = (TextView) llTab.findViewById(R.id.day);
            tvMonth = (TextView) llTab.findViewById(R.id.month);
            tvDate.setText(dates[i].toString());
            tvDay.setText(nameOfDay[i].toString());
            tvMonth.setText(month[i].toString());
            tabLayout.getTabAt(i).setCustomView(llTab);
            view.add(llTab);
        }
        //for first time selected date
        selectedDate = (dates[0] + "-" + month[0] + "-" + year[0]);
        //  selectedDate = CalendarUtil.getDate(date, CalendarUtil.DD_MMM_YYYY_PATTERN, CalendarUtil.DATE_STD_PATTERN, Locale.getDefault());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();

                selectedDate = (dates[i] + "-" + month[i] + "-" + year[i]);
                tvConfirmDateTime.setText("Confirm " + selectedTime + "," + selectedDate + "?");

                //  selectedDate = CalendarUtil.getDate(date, CalendarUtil.DD_MMM_YYYY_PATTERN, CalendarUtil.DATE_STD_PATTERN, Locale.getDefault());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //appoinmentDate.setText(appDate);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);


        if (isMorning == true) {

           // llMorning.setVisibility(View.VISIBLE);
          //  llAfternoon.setVisibility(View.GONE);
           // llEvening.setVisibility(View.GONE);
            getMorningTime();
        }
        morningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morningTime.setBackgroundResource(R.drawable.morning_select);
                afternoonTime.setBackgroundResource(R.drawable.afternoon_unselect);
                eveningTime.setBackgroundResource(R.drawable.evening_unselect);
                eveningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));
                morningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.white));
                afternoonTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));
               // llMorning.setVisibility(View.VISIBLE);
               // llAfternoon.setVisibility(View.GONE);
               // llEvening.setVisibility(View.GONE);

                isMorning = true;
                isAfternoon = false;
                isEvening = false;
                if (isMorning == true) {
                    getMorningTime();
                }

            }
        });
        afternoonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morningTime.setBackgroundResource(R.drawable.morning_unselect);
                afternoonTime.setBackgroundResource(R.drawable.afternoon_select);
                eveningTime.setBackgroundResource(R.drawable.evening_unselect);
                eveningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));
                morningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));
                afternoonTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.white));

                isMorning = false;
                isAfternoon = true;
                isEvening = false;
                if (isAfternoon == true) {
                    getAfternoonTime();
                }
            }
        });
        eveningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morningTime.setBackgroundResource(R.drawable.morning_unselect);
                afternoonTime.setBackgroundResource(R.drawable.afternoon_unselect);
                eveningTime.setBackgroundResource(R.drawable.evening_select);
                eveningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.white));
                morningTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));
                afternoonTime.setTextColor(ContextCompat.getColor(SelectDateTime.this, R.color.black_light));

                isMorning = false;
                isAfternoon = false;
                isEvening = true;
                if (isEvening == true) {
                    getEveningTime();
                }
            }
        });
        ivBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadData();
    }

    @Override
    public void initialiseControls() {

        tabLayout = (TabLayout) llSelectDate.findViewById(R.id.tabs);
        morningTime = (Button) llSelectDate.findViewById(R.id.morningTime);
        afternoonTime = (Button) llSelectDate.findViewById(R.id.afternoonTime);
        eveningTime = (Button) llSelectDate.findViewById(R.id.eveningTime);
        img_doctor = (ImageView) llSelectDate.findViewById(R.id.img_doctor);
        ivBackImage = (ImageView) llSelectDate.findViewById(R.id.ivBackImage);

       // llMorning = (LinearLayout) llSelectDate.findViewById(R.id.llMorning);
        btnProceed = (Button) llSelectDate.findViewById(R.id.btnProceed);
        tvConfirmDateTime = (TextView) llSelectDate.findViewById(R.id.tvConfirmDateTime);
        imageurl= ServiceUrls.IMAGE_BASE_URL+ AppConstants.selectedDoctorDo.photo;
        if(!StringUtils.isEmpty(imageurl)){
            Picasso.with(SelectDateTime.this).load(imageurl)
                    .into(img_doctor);
        }else{
            //image not found
        }

        gridViewTime = (GridView) llSelectDate.findViewById(R.id.gridViewTime);
        adapter = new DoctorsTimeAdapter(SelectDateTime.this, null);
        gridViewTime.setAdapter(adapter);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(selectedDate) && !StringUtils.isEmpty(selectedTime)) {
                    Intent intent = new Intent(SelectDateTime.this, DoctorsAppointmentForm.class);
                    intent.putExtra("selectedTime", selectedTime);
                    intent.putExtra("selectedDate", selectedDate);
                    startActivity(intent);
                } else {
                    showCustomDialog(SelectDateTime.this, getResources().getString(R.string.alert), getResources().getString(R.string.plz_select_slot), getResources().getString(R.string.ok), "", "");

                }

            }
        });
        gridViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectedPosition = position;
                adapter.refresh(appointmentTime);
                selectedTime = appointmentTime[position];
                tvConfirmDateTime.setText("Confirm " + selectedTime + "," + selectedDate + "?");

             //  String Str=(String)parent.getItemAtPosition(position);
               // parent.setBackgroundResource(R.drawable.selector);
               // String name = item.name;
          /*   View view1=  parent.getItemAtPosition(position);
                if (this.view != null) this.previousSelectedView.setSelected(false);
                v.setSelected(true);*/

            }
        });
    }

    @Override
    public void loadData() {

    }

    private void getMorningTime() {
        appointmentTime = new String[]{"07:00 AM", "07:30 AM", "08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM","11:00 AM","11:30 AM"};
        adapter.selectedPosition = 0;
        adapter.refresh(appointmentTime);
        selectedTime = appointmentTime[0];
        tvConfirmDateTime.setText("Confirm " + selectedTime + "," + selectedDate + "?");

    }

    private void getAfternoonTime() {
        appointmentTime = new String[]{"12:00 PM", "12:30 PM", "01:00 PM","01:30 PM","02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "4:30 PM"};
        adapter.selectedPosition = 0;
        adapter.refresh(appointmentTime);
        selectedTime = appointmentTime[0];
        tvConfirmDateTime.setText("Confirm " + selectedTime + "," + selectedDate + "?");

    }

    private void getEveningTime() {
        appointmentTime = new String[]{"05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM", "07:00 PM", "07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM",
                "09:30 PM", "10:00 PM", "10:30 PM"};

        adapter.selectedPosition = 0;
        adapter.refresh(appointmentTime);
        selectedTime = appointmentTime[0];
        tvConfirmDateTime.setText("Confirm " + selectedTime + "," + selectedDate + "?");


    }


}
