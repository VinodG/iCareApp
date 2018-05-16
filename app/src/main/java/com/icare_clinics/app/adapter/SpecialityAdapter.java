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
import com.icare_clinics.app.ClinicDetailsActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 24-01-2017.
 */

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.MyViewHolder> {
    private ArrayList<SpecializationDO> arrSpecialist;
    private LayoutInflater inflater;
    Context context;

    public SpecialityAdapter(Context context, ArrayList<SpecializationDO> arrSpecialist) {
        this.arrSpecialist = arrSpecialist;
        this.context = context;
        // inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView iv_specialists;
        public LinearLayout llSpecialities;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvSpecialityName);
            iv_specialists = (ImageView) view.findViewById(R.id.iv_specialists);
            llSpecialities = (LinearLayout) view.findViewById(R.id.llSpecialities);
            tvName.setTypeface(AppConstants.UbuntuRegular);
        }
    }

    @Override
    public int getItemCount() {
        if (arrSpecialist != null && arrSpecialist.size() > 0)
            return arrSpecialist.size();
        else
            return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialist_item, parent, false);*/
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SpecializationDO specialityDo = arrSpecialist.get(position);
        //  Album album = albumList.get(position);
        holder.tvName.setText(specialityDo.name);
        String imageurl = ServiceUrls.IMAGE_BASE_URL + specialityDo.SpecialtyIcons397x432;
//        if (!StringUtils.isEmpty(imageurl)) {
            imageurl = imageurl.trim().replaceAll(" ", "%20");
            ((BaseActivity) context).setImageCircle(imageurl, holder.iv_specialists, R.drawable.specalities);

    /*    } else {
             imageurl = ServiceUrls.IMAGE_BASE_URL + specialityDo.SpecialtyIcons397x432;
            //image not found
        }*/
        holder.llSpecialities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClinicDetailsActivity.class);
                intent.putExtra("SpecializationDO", specialityDo);
                context.startActivity(intent);
            }
        });
    }

    public void refresh(ArrayList<SpecializationDO> arrSpecialist) {
        this.arrSpecialist = arrSpecialist;
        notifyDataSetChanged();
    }

}
