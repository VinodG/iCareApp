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
import com.icare_clinics.app.DoctorDetail;
import com.icare_clinics.app.DoctorDetailNew;
import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by WINIT on 27-04-2017.
 */

public class SpecialityItemAdapter extends RecyclerView.Adapter<SpecialityItemAdapter.MyViewHolder>{

    private Context context;
    ArrayList<DoctorDo> arrDoctor;
    private SpecializationDO specializationDO=new SpecializationDO();
    public SpecialityItemAdapter(Context context, ArrayList<DoctorDo> arrDoctor){
        this.context=context;
        this.arrDoctor=arrDoctor;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_tab, parent, false);
        return new SpecialityItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DoctorDo doctorDo = arrDoctor.get(position);
        holder.tvName.setText(doctorDo.name);

//        getSpeciality(doctorDo.service);

        if (!StringUtils.isEmpty(doctorDo.qualification)) {
            holder.tvDegee.setText(doctorDo.qualification);
        }
        String imageurl = ServiceUrls.IMAGE_BASE_URL + doctorDo.photo;
      /*  if (!StringUtils.isEmpty(imageurl)){
            ((BaseActivity) context).new SetImageTask(holder.ivDoctor).execute(imageurl);
//            ((BaseActivity) context).SetImage(imageurl, holder.ivDoctor);
        }*/
        if (!StringUtils.isEmpty(imageurl)) {
            imageurl = imageurl.replaceAll(" ", "%20");
            ((BaseActivity) context).setImageCircle(imageurl, holder.ivDoctor, R.drawable.docto2);

        } else {
            //image not found
        }
        holder.llDoctorsTabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetail.class);
                intent.putExtra("doctorDO", doctorDo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrDoctor != null && arrDoctor.size() > 0)
            return arrDoctor.size();
        else
            return 0;
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

    public void refresh(ArrayList<DoctorDo> arrDoctor) {
        this.arrDoctor = arrDoctor;
        notifyDataSetChanged();
    }

    private void getSpeciality(String service) {

        SpecialityDA specialityDA = new SpecialityDA(context);
        specializationDO=specialityDA.getDoctorSpeciality(service);
    }

}
