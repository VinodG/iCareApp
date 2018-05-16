package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.WaterDO;

import java.util.ArrayList;

/**
 * Created by Sreekanth.P on 21-06-2017.
 */

public class ViewDetailsAdapter extends RecyclerView.Adapter<ViewDetailsAdapter.MyViewHolder>{

     private Context context;
     private ArrayList<WaterDO>arrWaterDO;

    public ViewDetailsAdapter(Context context, ArrayList<WaterDO> arrWaterDO) {
        this.context=context;
        this.arrWaterDO=arrWaterDO;
    }

    public void refresh(ArrayList<WaterDO> arrWaterDO){
        this.arrWaterDO=arrWaterDO;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_details_item,parent,false);
        int height = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(height);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WaterDO waterDO=arrWaterDO.get(position);
        holder.tvWaterMl.setText(waterDO.strWater+" ml");
        String[] strDate=waterDO.date.split(" ");
        String[]str=strDate[1].split(":");
        String strTime=convertTo12hrsFormate(str[0],str[1]);
        holder.tvTime.setText(strTime);
        holder.ivBottle.setImageResource(Integer.parseInt(waterDO.imgBottle));
    }

    @Override
    public int getItemCount() {
        if (arrWaterDO!=null && arrWaterDO.size()>0){
            return arrWaterDO.size();
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivBottle;
        public TextView tvWaterMl;
        public TextView tvTime;
        public MyViewHolder(View itemView) {
            super(itemView);

            ivBottle=(ImageView) itemView.findViewById(R.id.ivBottle);
            tvWaterMl=(TextView) itemView.findViewById(R.id.tvWaterMl);
            tvTime=(TextView) itemView.findViewById(R.id.tvTime);
        }
    }
    private String convertTo12hrsFormate(String s1, String s2) {

        String format="",mm,strTime;
        int hour =Integer.valueOf(s1);
        int min =Integer.valueOf(s2);

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
        strTime=hour+":"+mm+" "+format;
        return strTime;
    }
}
