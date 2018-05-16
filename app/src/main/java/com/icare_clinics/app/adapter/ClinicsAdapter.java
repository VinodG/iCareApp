package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.ClinicLocation;
import com.icare_clinics.app.ClinicDetailsActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by Mallikarjuna.K on 26-Dec-16.
 */

public class ClinicsAdapter extends RecyclerView.Adapter<ClinicsAdapter.ViewHolder> {
    //List<ClinicsDO> mDataset = new ArrayList<ClinicsDO>();
    private Context context;
    private ArrayList<ClinicDO> arrClinic;

    public ClinicsAdapter(ArrayList<ClinicDO> arrClinic, Context context) {
        this.context = context;
        this.arrClinic = arrClinic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ClinicDO clinicDO = arrClinic.get(position);
        holder.tvTimings.setText(StringUtils.fromHtml(clinicDO.timing));
        holder.tvplace.setText(clinicDO.description);
        holder.tvAddress.setText(StringUtils.fromHtml(clinicDO.add1) + " | " + StringUtils.fromHtml(clinicDO.add2));

        if (!StringUtils.isEmpty(clinicDO.Banners750x374)) {
            String imageurl = ServiceUrls.IMAGE_BASE_URL + clinicDO.Banners750x374;
            imageurl = imageurl.replaceAll(" ","%20");
            setClinicImage(imageurl,holder.ivHospital);
            /*Picasso.with(context)
                    .load(imageurl)
                    .into(holder.ivHospital);*/
        }else{
            //use image not avaiable
        }
        holder.rll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickForDetails(clinicDO);
            }
        });
        holder.ivDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callToClinicsDirectionMap(clinicDO);
            }
        });

    }

    private void callToClinicsDirectionMap(ClinicDO clinicDO) {
        Intent intent = new Intent(context, ClinicLocation.class);
      //  intent.putExtra("latitude", clinicDO.latitude);
        //intent.putExtra("longitude", clinicDO.longitude);
        intent.putExtra("ClinicDo",clinicDO);
        context.startActivity(intent);
    }

    private void clickForDetails(ClinicDO clinicDO) {
        //Log.d(" Items ", "clicked on " + position);
        Intent intent = new Intent(context, ClinicDetailsActivity.class);
         intent.putExtra("ClinicDo", clinicDO);
        context.startActivity(intent);

    }


    @Override
    public int getItemCount() {
        if (arrClinic != null)
            return arrClinic.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvplace, tvAddress, tvTimings, tvFriday;
        ImageView ivHospital, ivDirection;
        RelativeLayout rll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvplace = (TextView) itemView.findViewById(R.id.tvplace);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvTimings = (TextView) itemView.findViewById(R.id.tvTimings);
            tvFriday = (TextView) itemView.findViewById(R.id.tvFriday);
            ivHospital = (ImageView) itemView.findViewById(R.id.ivHospital);
            ivDirection = (ImageView) itemView.findViewById(R.id.ivDirection);
            rll= (RelativeLayout) itemView.findViewById(R.id.rll);
            tvplace.setTypeface(AppConstants.UbuntuRegular);
            tvAddress.setTypeface(AppConstants.UbuntuRegular);
            tvTimings.setTypeface(AppConstants.UbuntuRegular);
            tvFriday.setTypeface(AppConstants.UbuntuRegular);

        }

    }

    public void refresh(ArrayList<ClinicDO> arrClinic) {
        this.arrClinic = arrClinic;
        notifyDataSetChanged();
    }

    public void setClinicImage(String imageurl, ImageView imageView) {
        if (!StringUtils.isEmpty(imageurl)) {
            int size = R.drawable.img1;
            BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
            int mHeight = bd.getBitmap().getHeight();
            int mWidth = bd.getBitmap().getWidth();

            LogUtils.errorLog("Image Url:", imageurl);
            Picasso.with(context).load(imageurl)
                    .resize(mWidth, mHeight)
                    .error(size)
                    .into(imageView);
        }
    }
}
