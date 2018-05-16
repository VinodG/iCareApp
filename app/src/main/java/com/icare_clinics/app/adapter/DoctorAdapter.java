package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.DoctorDetail;
import com.icare_clinics.app.DoctorDetailNew;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.DoctorDo;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 21-02-2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{
    private Context context;
    private ArrayList<DoctorDo> arrSearchDoctor;

    public DoctorAdapter(ArrayList<DoctorDo> arrSearchDoctor, Context context) {
        this.context=context;
        this.arrSearchDoctor=arrSearchDoctor;
    }

    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DoctorAdapter.ViewHolder holder, int position) {
        final DoctorDo doctorDo = arrSearchDoctor.get(position);
        holder.tvplace.setText(doctorDo.name);
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickForDetails(doctorDo);
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
            llmain = (LinearLayout) itemView.findViewById(R.id.llmain);
        }

    }
    private void clickForDetails(DoctorDo doctorDo) {
        Intent intent=new Intent(context, DoctorDetail.class);
        intent.putExtra("doctorDO",doctorDo);
        context.startActivity(intent);
    }
    public void refresh(ArrayList<DoctorDo> arrSearchDoctor) {
        this.arrSearchDoctor = arrSearchDoctor;
        notifyDataSetChanged();
    }
}
