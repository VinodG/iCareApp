package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.BaseActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.SpecialityItemActivity;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by sudheer.jampana on 2/25/2017.
 */

public class ClinicTabAdapter extends RecyclerView.Adapter<ClinicTabAdapter.MyViewHolder>  {
    private LayoutInflater inflater;
    Context context;
    ArrayList<ClinicDO> arrClinics;
    private ClinicDO clinicDO;
    private SpecializationDO specializationDO;
    RecyclerView rv;

    public ClinicTabAdapter(RecyclerView rv,Context context, ArrayList<ClinicDO> arrClinics,SpecializationDO specializationDO) {
        this.context = context;
        this.arrClinics = arrClinics;
//        this.clinicDO = clinicDO;
        this.specializationDO = specializationDO;
        this.rv=rv;
        //  inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvDegee;
        public ImageView ivDoctor;
        public LinearLayout llDoctorsTabItem;

        public MyViewHolder(View view) {
            super(view);
            ivDoctor = (ImageView) view.findViewById(R.id.ivDoctor);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDegee = (TextView) view.findViewById(R.id.tvDegee);
            llDoctorsTabItem = (LinearLayout) view.findViewById(R.id.llDoctorsTabItem);
            tvName.setTypeface(AppConstants.DinproBold);
            tvDegee.setTypeface(AppConstants.UbuntuRegular);
        }

    }

    @Override
    public int getItemCount() {
        if (arrClinics != null && arrClinics.size() > 0)
            return arrClinics.size();
        else
            return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinics_tab, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rv.indexOfChild(v);
                ClinicDO clinicDO = arrClinics.get(position);
                Intent intent=new Intent(context, SpecialityItemActivity.class);
                intent.putExtra("clinicDO",clinicDO);
                intent.putExtra("specializationDO",specializationDO);
                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClinicDO clinicDO = arrClinics.get(position);
        holder.tvName.setText(clinicDO.description);

        String imageurl = ServiceUrls.IMAGE_BASE_URL + clinicDO.Banners750x374;

        if (!StringUtils.isEmpty(imageurl)){
            ((BaseActivity)context).getImageClinicsImage(imageurl,holder.ivDoctor);
        }
      /*  if (!StringUtils.isEmpty(imageurl)) {
            imageurl = imageurl.replaceAll(" ", "%20");
            ((BaseActivity) context).setImageCircle(imageurl, holder.ivDoctor, R.drawable.clinics);

        } else {
            //image not found
        }*/
      /*  holder.llDoctorsTabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SpecialityItemActivity.class);
                intent.putExtra("clinicDO",clinicDO);
                intent.putExtra("specializationDO",specializationDO);
                context.startActivity(intent);
            }
        });*/
    }

    public void refresh(ArrayList<ClinicDO> arrClinics) {
        this.arrClinics = arrClinics;
        notifyDataSetChanged();
    }
}
