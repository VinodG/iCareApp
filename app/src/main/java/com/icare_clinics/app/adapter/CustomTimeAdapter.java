package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.TimeListDO;

import java.util.ArrayList;

/**
 * Created by rajeshc on 6/6/2017.
 */

public class CustomTimeAdapter extends RecyclerView.Adapter<CustomTimeAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<TimeListDO> arrTimeList;

    public CustomTimeAdapter(Context context, ArrayList<TimeListDO> arrTimeList) {
        this.context=context;
        this.arrTimeList=arrTimeList;
    }

    @Override
    public CustomTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_time_item,null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomTimeAdapter.MyViewHolder holder, int position) {
        TimeListDO timeListDO=arrTimeList.get(position);
        holder.iv_num.setImageResource(timeListDO.ivRectid);
        holder.tv_num.setText(timeListDO.number);
    }

    @Override
    public int getItemCount() {
        if (arrTimeList!=null && arrTimeList.size()>0){
            return arrTimeList.size();
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_num;
        private TextView tv_num;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_num=(ImageView)itemView.findViewById(R.id.iv_num);
            tv_num=(TextView)itemView.findViewById(R.id.tv_num);
        }
    }
}
