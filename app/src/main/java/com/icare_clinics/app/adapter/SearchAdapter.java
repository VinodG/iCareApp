package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.ClinicDetailsActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.ClinicDO;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 21-02-2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<ClinicDO> arrSearchLocation;

    public SearchAdapter(ArrayList<ClinicDO> arrSearchLocations, Context context) {
        this.context=context;
        this.arrSearchLocation=arrSearchLocations;
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        final ClinicDO searchDO = arrSearchLocation.get(position);
        holder.tvplace.setText(searchDO.description);
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickForDetails(searchDO);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrSearchLocation != null)
            return arrSearchLocation.size();
        return 0;
}

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvplace, tvAddress, tvTimings, tvFriday;
        ImageView ivHospital, ivDirection;
        LinearLayout llmain;

        public ViewHolder(View itemView) {
            super(itemView);
            tvplace = (TextView) itemView.findViewById(R.id.tvdetails);
            llmain= (LinearLayout) itemView.findViewById(R.id.llmain);
           /* tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvTimings = (TextView) itemView.findViewById(R.id.tvTimings);
            tvFriday = (TextView) itemView.findViewById(R.id.tvFriday);
            ivHospital = (ImageView) itemView.findViewById(R.id.ivHospital);
            ivDirection = (ImageView) itemView.findViewById(R.id.ivDirection);*/
        }

    }
    private void clickForDetails(ClinicDO clinicDO) {
        //Log.d(" Items ", "clicked on " + position);
        Intent intent = new Intent(context, ClinicDetailsActivity.class);
        intent.putExtra("ClinicDo", clinicDO);
        context.startActivity(intent);
    }
    public void refresh(ArrayList<ClinicDO> arrSearchLocation) {
        this.arrSearchLocation = arrSearchLocation;
        notifyDataSetChanged();
    }

}
