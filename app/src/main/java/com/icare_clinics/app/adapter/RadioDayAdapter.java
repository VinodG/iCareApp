package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.icare_clinics.app.AddReminder;
import com.icare_clinics.app.R;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by namashivaya.gangishe on 6/6/2017.
 */

public class RadioDayAdapter extends BaseAdapter {
    Context context;
    String[] dayList;
    public Boolean isEveryDay=true;
    HashMap<String,Boolean> hmDaysSattus;
    LayoutInflater inflater;
    public static ArrayList<String> selectedDays;
    public RadioDayAdapter (Context context, String[] dayList,HashMap<String,Boolean> hmDaysSattus){
        this.context=context;
        this.dayList=dayList;
       this.hmDaysSattus=hmDaysSattus;
        selectedDays=new ArrayList<>();
        for(int i=0;i<dayList.length;i++){
            selectedDays.add(dayList[i]);
        }

    }
    @Override
    public int getCount() {
        return dayList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater=(LayoutInflater.from(context));
        convertView=inflater.inflate(R.layout.list_radio_cell,null);
        LinearLayout llSunday= (LinearLayout)convertView.findViewById(R.id.llSunday);
        ImageView ivSunday= (ImageView) convertView.findViewById(R.id.ivSunday);
        TextView tvSunday= (TextView) convertView.findViewById(R.id.tvSunday);
        tvSunday.setText(dayList[position]);
        if(hmDaysSattus.get(dayList[position])) {
            ivSunday.setImageResource(R.drawable.select_radio);
            tvSunday.setTextColor(context.getResources().getColor(R.color.dark_blue));
        } else {
            tvSunday.setTextColor(context.getResources().getColor(R.color.gray));
            ivSunday.setImageResource(R.drawable.unselect_radio);
        }
        llSunday.setTag(position);
        llSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int pos=(int)v.getTag();
                LinearLayout llClicked= (LinearLayout)v;
                ImageView ivSundayClicked= (ImageView) llClicked.findViewById(R.id.ivSunday);
                TextView tvSundayClicked= (TextView) llClicked.findViewById(R.id.tvSunday);
                String str=tvSundayClicked.getText().toString();
                if(str.equalsIgnoreCase(dayList[0])){
                    if (hmDaysSattus.get(str)) {
                        hmDaysSattus.remove(str);
                        hmDaysSattus.put(str, false);

                    } else {
                        hmDaysSattus.remove(str);
                        hmDaysSattus.put(str, true);
                        ((AddReminder)context).initialiseHashMap();
                    }
                }else {
                    if (hmDaysSattus.get(str)) {
                        hmDaysSattus.remove(str);
                        hmDaysSattus.put(str, false);

                    } else {
                        hmDaysSattus.remove(str);
                        hmDaysSattus.put(str, true);
                        hmDaysSattus.remove(dayList[0]);
                        hmDaysSattus.put(dayList[0], false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {

        RadioButton radiobtn;
    }
}
