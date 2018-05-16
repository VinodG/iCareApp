package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.WaterIntakeNew;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.dataobject.WaterMlDO;

import java.util.ArrayList;


/**
 * Created by rajeshc on 6/6/2017.
 */

public class CustomWaterAdapter extends RecyclerView.Adapter<CustomWaterAdapter.MyViewHolder>{
    private Context context;
//    private ArrayList<WaterMlDO> arrWaterList;
    private ArrayList<WaterDO> arrWaterList;
    private String string;

    public CustomWaterAdapter(Context context, ArrayList<WaterDO> arrWaterList) {
        this.context=context;
        this.arrWaterList=arrWaterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_water_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        final WaterMlDO waterMlDO=arrWaterList.get(position);
        final WaterDO waterMlDO=arrWaterList.get(position);
        string=waterMlDO.strWater;
        holder.tvml.setText(waterMlDO.strWater);
        holder.ivMl.setImageResource(Integer.parseInt(waterMlDO.imgBottle));
//        holder.ivMl.setBackgroundResource(waterMlDO.ivBottleId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WaterMlDO waterMlDO=arrWaterList.get(position);
                WaterDO waterMlDO=arrWaterList.get(position);
//                String temp=((TextView)holder.itemView.findViewById(R.id.tv_ml)).getText().toString();
                ((WaterIntakeNew)context).sendData(waterMlDO);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (arrWaterList !=null && arrWaterList.size()>0){
            return arrWaterList.size();
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public  TextView tvml;
        public ImageView ivMl;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvml=(TextView)itemView.findViewById(R.id.tv_ml);
            ivMl=(ImageView)itemView.findViewById(R.id.iv_bottle);
        }
    }
}
