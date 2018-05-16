package com.icare_clinics.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.icare_clinics.app.R.id.btnDone;

/**
 * Created by Baliram.Kumar on 13-06-2017.
 */

public class WaterIntakeBottomBarFragment extends BottomSheetDialogFragment {
    private TextView tvDone,tvCancel;
    private Context context;
    private TimePicker timePicker;
    Calendar calendar,c;
    private String format = "";
    private TextView time;
    String time1,time2,time3,time4,time5;
    int val;
    int hr,mm;

    public WaterIntakeBottomBarFragment(){

    }
    public WaterIntakeBottomBarFragment(int val) {
        this.val=val;
        calendar = Calendar.getInstance();
        hr=calendar.get(Calendar.HOUR_OF_DAY);
        mm=calendar.get(Calendar.MINUTE);

    }
    public WaterIntakeBottomBarFragment(String time1,int val) {
        this.time1=time1;
        this.val=val;
        // Log.d("XXXX",time1);
        String namepass[] = time1.split(":");
        hr= Integer.parseInt(namepass[0]);
        mm= Integer.parseInt(namepass[1]);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_timepicker_fragment, container, false);
        initializeControls(view);
        return view;
    }
    private void initializeControls(final View view) {
        timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
        tvDone = (TextView) view.findViewById(R.id.tvDone);
//        time=(TextView) view.findViewById(R.id.time);
        tvCancel=(TextView) view.findViewById(R.id.tvCancel);
        context=getActivity();
        calendar = Calendar.getInstance();
        timePicker.setCurrentHour(hr);
        timePicker.setCurrentMinute(mm);
       /* int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);*/
        //  showTime(hour, min);
//        showTime(hr, mm);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int hour1 = /*c.get(Calendar.HOUR_OF_DAY);*/timePicker.getCurrentHour();
                int minute1 = /*c.get(Calendar.MINUTE);*/timePicker.getCurrentMinute();
                showTime(hour1, minute1);

                dismiss();
            }
        });
    }

    public void setTime(View view) {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        showTime(hour, min);
    }
    public void showTime(int hour, int min) {
        int modifiedHour=hour;
        int modifiedMin=min;

     /*   if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }*/
        if (context instanceof SetReminder){
            if(val>=0){
                ((SetReminder) context).SelectedModifiedTime(hour, min,val,modifiedHour,modifiedMin);

            }else {
                ((SetReminder) context).SelectedTime(hour, min,modifiedHour,modifiedMin);

            }
          /*  time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                    .append(" ").append(format));*/
        }
    }
}
