package com.icare_clinics.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.icare_clinics.app.AddReminder;

import java.util.Calendar;

/**
 * Created by Baliram.Kumar on 02-06-2017.
 */

public class SelectTimePickerBottomFragment  extends BottomSheetDialogFragment {
    private Button btnDone;
    private Context context;
    private TimePicker timePicker;
    Calendar calendar,c;
    private String format = "";
    private TextView time,tvDone,tvCancel,tvHeader;
    String time1,time2,time3,time4,time5;
    int val;
    int hr,mm;

    public SelectTimePickerBottomFragment(String time1,int val) {
        this.time1=time1;
        this.val=val;
        // Log.d("XXXX",time1);
        String namepass[] = time1.split(":");
        hr= Integer.parseInt(namepass[0]);
        mm= Integer.parseInt(namepass[1]);
        Log.d("XXXX", String.valueOf(hr));
        Log.d("XXXX", String.valueOf(mm));

    }
    public SelectTimePickerBottomFragment(int val) {
        this.val=val;

    }
    public SelectTimePickerBottomFragment(String time1) {
        this.time1=time1;
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
        // btnDone = (Button) view.findViewById(R.id.set_button);
        time=(TextView) view.findViewById(R.id.time);
        tvCancel=(TextView) view.findViewById(R.id.tvCancel);
        tvDone=(TextView) view.findViewById(R.id.tvDone);
        tvHeader=(TextView) view.findViewById(R.id.tvHeader);
        context=getActivity();

        if (context instanceof AddReminder){
            tvHeader.setText("Edit Remainder");
        }
      /*  Calendar calendar = Calendar.getInstance();
        hr=calendar.get(Calendar.HOUR_OF_DAY);
        mm=calendar.get(Calendar.MINUTE);*/
        timePicker.setCurrentHour(hr);
        timePicker.setCurrentMinute(mm);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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

        if (context instanceof AddReminder) {
            ((AddReminder) context).SelectedTime(hour, min, val);
           /* time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                    .append(" ").append(format));*/
            ((AddReminder)context).SetAlaramTime(modifiedHour,modifiedMin,val);
        }
        if (context instanceof WaterIntakeNew){
            ((WaterIntakeNew) context).SelectedTime(hour,min,val);
           /* time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                    .append(" ").append(format));*/
        }
    }
}
