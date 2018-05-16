package com.icare_clinics.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Baliram.Kumar on 02-03-2017.
 */

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.ViewHolder>{
    private Context context;
   // private ArrayList<ClinicDO> arrClinic;
   ArrayList<String> timingList = new ArrayList<String>();

    public TimingAdapter(ArrayList<String> timingList, Context context) {
        this.context = context;
        this.timingList = timingList;
    }

    @Override
    public TimingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timeing, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TimingAdapter.ViewHolder holder, int position) {
        holder.tvTiming.setText(timingList.get(position));
      /*  holder.llScrollUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x=(int)motionEvent.getX();
                int y=(int)motionEvent.getY();
                int width= view.getLayoutParams().width;
                int height = view.getLayoutParams().height;
                if((x - width <= 50 && x - width > 0) ||(width - x <= 50 && width - x > 0)){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.e(">>","width:"+width+" height:"+height+" x:"+x+" y:"+y);
                            view.getLayoutParams().width = x;
                            view.getLayoutParams().height = y;
                            view.requestLayout();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                }
                return false;
            }
        });*/
        holder.llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // callScrollTime(holder.tvTimeInMinutes,holder.lltimeScroll);
                String time=holder.tvTiming.getText().toString();
                callsetTime(time);
            }
        });
    }
    public  void callScrollTime(TextView tvTimeInMinutes,RelativeLayout lltimeScroll)
    {
      /*tvTimeInMinutes.setVisibility(View.VISIBLE);
        tvTimeInMinutes.setMovementMethod(new ScrollingMovementMethod());*/
        lltimeScroll.setVisibility(View.VISIBLE);
        lltimeScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x=(int)motionEvent.getX();
                int y=(int)motionEvent.getY();
                int width= view.getLayoutParams().width;
                int height = view.getLayoutParams().height;
                if((x - width <= 50 && x - width > 0) ||(width - x <= 50 && width - x > 0)){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.e(">>","width:"+width+" height:"+height+" x:"+x+" y:"+y);
                            view.getLayoutParams().width = x;
                            view.getLayoutParams().height = y;
                            view.requestLayout();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                }
                return false;
            }
        });

    }
    @Override
    public int getItemCount() {
        if (timingList != null)
            return timingList.size();
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvTiming,tvTimeInMinutes;
        LinearLayout llScrollUp,llTime;
        RelativeLayout lltimeScroll;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTiming = (TextView) itemView.findViewById(R.id.tvTiming);
          //  tvTimeInMinutes = (TextView) itemView.findViewById(R.id.tvTimeInMinutes);
           /* lltimeScroll = (RelativeLayout) itemView.findViewById(R.id.rltimeScroll);
            llScrollUp = (LinearLayout) itemView.findViewById(R.id.llScrollUp);*/
            llTime = (LinearLayout) itemView.findViewById(R.id.llTime);


        }

    }
    public void refresh(ArrayList<String> timingList) {
        this.timingList = timingList;
        notifyDataSetChanged();
    }
    public void callsetTime(String tvTime)
    {
        Intent intent=new Intent();
        intent.putExtra("TIME",tvTime);
        ((Activity)context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).finish();
    }
}
