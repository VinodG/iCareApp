package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by WINIT on 01-Mar-17.
 */

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<InsuranceDo> arrInsurance;

    public InsuranceAdapter(Context context, ArrayList<InsuranceDo> arrInsurance) {
        this.context = context;
        this.arrInsurance = arrInsurance;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivInsu;

        public MyViewHolder(View view) {
            super(view);
            ivInsu = (ImageView) view.findViewById(R.id.ivInsu);

        }
    }

    @Override
    public int getItemCount() {
        if (arrInsurance != null && arrInsurance.size() > 0)
            return arrInsurance.size();
        else
            return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.insurence_cell, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // final DoctorDo doctorDo = arrDoctor.get(position);
        final InsuranceDo insuranceDo = arrInsurance.get(position);
        //  holder.tvName.setText(specialityDo.name);
        String imageurl = ServiceUrls.IMAGE_BASE_URL + insuranceDo.image;
        if (!StringUtils.isEmpty(imageurl)) {
            imageurl = imageurl.replaceAll(" ", "%20");
            Picasso.with(context).load(imageurl)
                    .into(holder.ivInsu);
        } else {
            //image not found
        }
    }

    public void refresh(ArrayList<InsuranceDo> arrInsurance) {
        this.arrInsurance = arrInsurance;
        notifyDataSetChanged();
    }


}
