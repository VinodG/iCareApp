package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.SetReminderDo;

import java.util.ArrayList;

import static android.R.attr.format;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Baliram.Kumar on 12-06-2017.
 */

public class ReminderTimeAdapter extends BaseAdapter {
    Context mCtx;
    private ArrayList<SetReminderDo> arrTime;
    LayoutInflater layoutInflater;
    public static int selectedPosition;
    private TextView tvTime,tvConfirmDateTime;
    public ReminderTimeAdapter(Context context, ArrayList<SetReminderDo> arrTime) {
        this.mCtx = context;
        this.arrTime = arrTime;
        // rgp = new RadioGroup(context);
        layoutInflater = (LayoutInflater) mCtx.getSystemService(LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        if(arrTime!=null&&arrTime.size()>0)
            return arrTime.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_reminder_time, null);
            holder = new Holder();

            holder.tvTime= (TextView) view.findViewById(R.id.tvTime);
           /* if ( holder.radioButton != null) {
                holder.radioButton.setChecked(true);

            }*/
            view.setTag(holder);

        } else {

            holder = (Holder) view.getTag();
        }
        if(i==selectedPosition){
            holder.tvTime.setSelected(true);

        }else{
            holder.tvTime.setSelected(false);
        }
        SetReminderDo setReminderDo=new SetReminderDo();
        setReminderDo=arrTime.get(i);

        String time=convertTo12hrsFormate(setReminderDo);
        holder.tvTime.setText(time);
        return view;
    }
    static class Holder {
        private TextView tvTime;
    }
    public void refresh(ArrayList<SetReminderDo> arrTime){
        this.arrTime = arrTime;
        notifyDataSetChanged();
    }
    private String convertTo12hrsFormate(SetReminderDo setReminderDo) {
        String format="",strTime="",mm="";
        int hour = Integer.parseInt(setReminderDo.hr);
        int min=Integer.parseInt(setReminderDo.mm);
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
        if (min<10){
            mm= "0" + min;
        }else {
            mm=""+min;
        }
        strTime=hour +":"+ mm+ " "+format;
        return strTime;
    }
}
