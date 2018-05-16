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

import com.icare_clinics.app.ClinicDetailsActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 21-02-2017.
 */

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SpecializationDO> arrSearchSpeciality;
    public  String specialities;

    public SpecialistAdapter(ArrayList<SpecializationDO> arrSearchSpeciality, Context context) {
        this.context = context;
        this.arrSearchSpeciality = arrSearchSpeciality;
    }

    @Override
    public SpecialistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SpecialistAdapter.ViewHolder holder, int position) {
        final SpecializationDO specialityDo = arrSearchSpeciality.get(position);
        holder.tvplace.setText(specialityDo.name);
          specialities=holder.tvplace.getText().toString();
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClinicDetailsActivity.class);
                intent.putExtra("SpecializationDO", specialityDo);
                context.startActivity(intent);
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
}
