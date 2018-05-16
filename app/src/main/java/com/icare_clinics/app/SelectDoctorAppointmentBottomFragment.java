package com.icare_clinics.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.adapter.DoctorsTimeAdapter;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.util.ArrayList;

import static com.icare_clinics.app.BaseActivity.i;

/**
 * Created by Baliram.Kumar on 22-06-2017.
 */

public class SelectDoctorAppointmentBottomFragment extends BottomSheetDialogFragment {

    private TabLayout tabLayout;
    private Button morningTime,afternoonTime,eveningTime;
    private Context context,contextMain;
    private ArrayList<View> arrview;
    private GridView gridViewTime;
    private String[] appointmentTime;
    private DoctorsTimeAdapter adapter;
    private boolean isMorning = true, isAfternoon = false, isEvening = false;
    private String selectedTime, selectedDate,date;
    private TextView tvDay, tvDate, tvMonth,tvConfirmDateTime,tvMorningTime,tvAfterNoonTime,tvEveningTime;
    private LinearLayout llTab;
    private String[] nameOfDay = new String[7];
    private String[] dates = new String[7];
    private String[] month = new String[7];
    private String[] year = new String[7];
    private Button btnProceed;
    private DoctorDo doctorDo=new DoctorDo();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_date_bottom, container, false);
        initializeControls(view);
        return view;
    }
    private void initializeControls(final View view) {
        tabLayout= (TabLayout) view.findViewById(R.id.tabs);
        morningTime= (Button) view.findViewById(R.id.morningTime);
        eveningTime= (Button) view.findViewById(R.id.eveningTime);
        afternoonTime= (Button) view.findViewById(R.id.afternoonTime);
        tvConfirmDateTime = (TextView) view.findViewById(R.id.tvConfirmDateTime);
        tvMorningTime = (TextView) view.findViewById(R.id.tvMorningTime);
        tvAfterNoonTime = (TextView) view.findViewById(R.id.tvAfterNoonTime);
        tvEveningTime = (TextView) view.findViewById(R.id.tvEveningTime);
        btnProceed = (Button) view.findViewById(R.id.btnProceed);
        context=getActivity();


        if(context instanceof DoctorDetail){
            btnProceed.setText("Proceed");
            selectedTime = ((DoctorDetail)context).getPreference().getStringFromPreference(Preference.REQ_TIME,"7am-12noon");
            selectedDate = ((DoctorDetail)context).getPreference().getStringFromPreference(Preference.REQ_YY, CalendarUtil.getTodayformatNew());
        }else {
            selectedTime = ((AppointmentForm) context).getPreference().getStringFromPreference(Preference.REQ_TIME, "7am-12noon");
            selectedDate = ((AppointmentForm) context).getPreference().getStringFromPreference(Preference.REQ_YY, CalendarUtil.getTodayformatNew());
        }
        tvConfirmDateTime.setText("Confirm-" + selectedTime + ",  " + selectedDate + "?");

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(context instanceof AppointmentForm)
                {
                    ((AppointmentForm) context).showdateTime(selectedDate, selectedTime);
                    ((AppointmentForm) context).getPreference().saveStringInPreference(Preference.REQ_TIME, selectedTime);
                    ((AppointmentForm) context).getPreference().saveStringInPreference(Preference.REQ_YY, selectedDate);
                    dismiss();
                }
                if(context instanceof  DoctorDetail)
                {
                    ((DoctorDetail) context).getPreference().saveStringInPreference(Preference.REQ_TIME, selectedTime);
                    ((DoctorDetail) context).getPreference().saveStringInPreference(Preference.REQ_YY, selectedDate);
                    doctorDo=((DoctorDetail) context).getDoctorDO();
//                         Log.d("NewActivity :", "launched");
                    Intent intent = new Intent(  context ,RequestAppointmentNew.class );
                    intent.putExtra("date",selectedDate);
                    intent.putExtra("time",selectedTime);
                    intent.putExtra("doctorDO",doctorDo);
                    startActivity(intent);
                    dismiss();
                }
            }
        });
        if (isMorning == true) {
            getMorningTime();
        }
        morningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                morningTime.setBackgroundResource(R.drawable.morning_select);
                afternoonTime.setBackgroundResource(R.drawable.afternoon_unselect);
                eveningTime.setBackgroundResource(R.drawable.evening_unselect);
                eveningTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));
                morningTime.setTextColor(ContextCompat.getColor(context, R.color.white));
                afternoonTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));
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
                eveningTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));
                morningTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));
                afternoonTime.setTextColor(ContextCompat.getColor(context, R.color.white));

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
                eveningTime.setTextColor(ContextCompat.getColor(context, R.color.white));
                morningTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));
                afternoonTime.setTextColor(ContextCompat.getColor(context, R.color.black_light));

                isMorning = false;
                isAfternoon = false;
                isEvening = true;
                if (isEvening == true) {
                    getEveningTime();
                }
            }
        });

        arrview=new ArrayList<View>();
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
            llTab = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.list_appointment_dmy, null);
            tvDate = (TextView) llTab.findViewById(R.id.date);
            tvDay = (TextView) llTab.findViewById(R.id.day);
            tvMonth = (TextView) llTab.findViewById(R.id.month);

            tvDate.setText(dates[i].toString());
            tvDay.setText(nameOfDay[i].toString());
            tvMonth.setText(month[i].toString());
            tabLayout.getTabAt(i).setCustomView(llTab);
            arrview.add(llTab);
        }

        selectedDate = ( dates[0]+"-"+ month[0] +"-"+year[0] );
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();

                selectedDate = ( dates[i]+"-"+ month[i] +"-"+year[i] );
                tvConfirmDateTime.setText("Confirm-" + selectedTime + ",  " + selectedDate + "?");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);

    }

    private void getMorningTime() {

        selectedTime=tvMorningTime.getText().toString();
        tvConfirmDateTime.setText("Confirm-" + selectedTime+ ",  " + selectedDate + "?");

    }
    private void getAfternoonTime() {

        selectedTime=tvAfterNoonTime.getText().toString();
        tvConfirmDateTime.setText("Confirm-" + selectedTime + ",  " + selectedDate + "?");
    }
    private void getEveningTime() {

        selectedTime=tvEveningTime.getText().toString();
        tvConfirmDateTime.setText("Confirm-" + selectedTime+ ",  " + selectedDate + "?");
    }
}
