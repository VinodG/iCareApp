package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.MyRemindersDo;

import java.util.ArrayList;


/**
 * Created by Mallikarjuna.K on 05-01-2017.
 */
public class MyRemindersAdapter extends BaseAdapter {
    ArrayList<MyRemindersDo> arrayList=new ArrayList<>();
    private static LayoutInflater inflater=null;
    Context context;

    public MyRemindersAdapter(Context context, ArrayList<MyRemindersDo> list) {
        this.context=context;
        arrayList=list;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        View view=convertView;
        if (convertView==null) {

            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_reminders_list_item,null);
        }
        TextView tvMedicineName=(TextView)convertView.findViewById(R.id.tvMedicineName);
        TextView tvDosage=(TextView)convertView.findViewById(R.id.tvDosage);
        TextView tvDays=(TextView)convertView.findViewById(R.id.tvDays);
        TextView tvTimings=(TextView)convertView.findViewById(R.id.tvTimings);
        TextView tvDuration=(TextView)convertView.findViewById(R.id.tvDuration);

        tvMedicineName.setText(arrayList.get(position).MedicineName);
        tvDosage.setText(arrayList.get(position).Dosage);
        tvDays.setText(arrayList.get(position).Days);
        tvTimings.setText(arrayList.get(position).Timings);
        tvDuration.setText(arrayList.get(position).Duration);
        return convertView;
    }

}
