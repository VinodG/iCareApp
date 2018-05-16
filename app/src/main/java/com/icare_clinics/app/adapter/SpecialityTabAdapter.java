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
 * Created by WINIT on 24-Feb-17.
 */

public class SpecialityTabAdapter extends RecyclerView.Adapter<SpecialityTabAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    Context context;
    ArrayList<SpecializationDO> arrSpeciality;
    private ClinicDO clinicDO;
    public SpecialityTabAdapter(Context context, ArrayList<SpecializationDO> arrSpeciality, ClinicDO clinicDO) {
        this.context = context;
        this.arrSpeciality = arrSpeciality;
        this.clinicDO=clinicDO;
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
            tvDegee.setVisibility(View.GONE);
            tvName.setTypeface(AppConstants.UbuntuRegular);
            tvDegee.setTypeface(AppConstants.UbuntuRegular);
        }
    }
    @Override
    public int getItemCount() {
        if (arrSpeciality != null && arrSpeciality.size() > 0)
            return arrSpeciality.size();
        else
            return 0;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.speciality_tab, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // final DoctorDo doctorDo = arrDoctor.get(position);
        final SpecializationDO specialityDo = arrSpeciality.get(position);
        holder.tvName.setText(specialityDo.name);
        String imageurl = ServiceUrls.IMAGE_BASE_URL + specialityDo.SpecialtyIcons241x262;
        if (!StringUtils.isEmpty(imageurl)) {
            imageurl = imageurl.replaceAll(" ", "%20");
            ((BaseActivity) context).setImageCircle(imageurl, holder.ivDoctor, R.drawable.specalities);

        } else {
            //image not found
        }
        holder.llDoctorsTabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecialityItemActivity.class);
                intent.putExtra("specializationDO", specialityDo);
                intent.putExtra("clinicDO",clinicDO);
                intent.putExtra("from","SpecialityTabAdapter");
                context.startActivity(intent);
            }
        });
    }

    public void refresh(ArrayList<SpecializationDO> arrSpeciality) {
        this.arrSpeciality = arrSpeciality;
        notifyDataSetChanged();
    }

}
