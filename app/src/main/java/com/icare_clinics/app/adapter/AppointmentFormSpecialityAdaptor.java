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
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 09-03-2017.
 */

public class AppointmentFormSpecialityAdaptor extends RecyclerView.Adapter<AppointmentFormSpecialityAdaptor.ViewHolder>  {
    private Context context;
    private ArrayList<SpecializationDO> arrSearchSpeciality;
    public  String specialities;
    public AppointmentFormSpecialityAdaptor(ArrayList<SpecializationDO> arrSearchSpeciality, Context context) {
        this.context = context;
        this.arrSearchSpeciality = arrSearchSpeciality;
    }

    @Override
    public AppointmentFormSpecialityAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppointmentFormSpecialityAdaptor.ViewHolder holder, int position) {
        final SpecializationDO specialityDo = arrSearchSpeciality.get(position);
        holder.tvplace.setText(specialityDo.name);
        specialities=holder.tvplace.getText().toString();
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  clickForSpecialities(specialityDo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrSearchSpeciality != null)
            return arrSearchSpeciality.size();
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

    public void refresh(ArrayList<SpecializationDO> arrSearchSpeciality) {
        this.arrSearchSpeciality = arrSearchSpeciality;
        notifyDataSetChanged();
    }

    public void clickForSpecialities(SpecializationDO specialityDo)
    {
        Intent intent=new Intent();
        intent.putExtra("SPECIALITIES",specialityDo);
        ((Activity)context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).finish();
    }
}
