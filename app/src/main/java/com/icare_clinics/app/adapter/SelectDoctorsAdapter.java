package com.icare_clinics.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 10-03-2017.
 */

public class SelectDoctorsAdapter  extends RecyclerView.Adapter<SelectDoctorsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DoctorDo> arrSearchDoctor;
    public  String specialities;
    public SelectDoctorsAdapter(ArrayList<DoctorDo> arrSearchDoctor, Context context) {
        this.context = context;
        this.arrSearchDoctor = arrSearchDoctor;
    }

    @Override
    public SelectDoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SelectDoctorsAdapter.ViewHolder holder, int position) {
        final DoctorDo doctorDo = arrSearchDoctor.get(position);
        holder.tvplace.setText(doctorDo.name);
        specialities=holder.tvplace.getText().toString();
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickForDoctors(doctorDo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrSearchDoctor != null)
            return arrSearchDoctor.size();
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvplace;
        LinearLayout llmain;
        public ViewHolder(View itemView) {
            super(itemView);
            tvplace = (TextView) itemView.findViewById(R.id.tvdetails);
            llmain= (LinearLayout) itemView.findViewById(R.id.llmain);
        }

    }
    public void refresh(ArrayList<DoctorDo> arrSearchDoctor) {
        this.arrSearchDoctor = arrSearchDoctor;
        notifyDataSetChanged();
    }

    public void clickForDoctors(DoctorDo doctorDo)
    {
        Intent intent=new Intent();
        intent.putExtra("DOCTORS",doctorDo);
        ((Activity)context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).finish();
    }

}
