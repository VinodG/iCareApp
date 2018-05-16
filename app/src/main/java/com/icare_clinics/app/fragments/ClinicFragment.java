package com.icare_clinics.app.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icare_clinics.app.ClinicDetailsActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.adapter.ClinicTabAdapter;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by sudheer.jampana on 2/25/2017.
 */

public class ClinicFragment extends Fragment {
   // private GridView rvClinics;
    private Context context;
    private TextView tvNotFound;
    private ClinicTabAdapter tabAdapter;
    private ArrayList<ClinicDO> arrClinic;
    private RecyclerView rvClinics;
    private ClinicDO clinicDO;
    private SpecializationDO specializationDO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.specialities_tab, container, false);
        context = getActivity();
        //rvClinics = (GridView) view.findViewById(R.id.gvSpecialist);
        rvClinics=(RecyclerView) view.findViewById(R.id.rvSpecialist);

        tvNotFound = (TextView) view.findViewById(R.id.tvNotFoundSpe);
        specializationDO=((ClinicDetailsActivity)context).getSpecialityDO();

//        tabAdapter = new ClinicTabAdapter(context, arrClinic,clinicDO,specializationDO);
        tabAdapter = new ClinicTabAdapter(rvClinics,context, arrClinic,specializationDO);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvClinics.setLayoutManager(mLayoutManager);
        rvClinics.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        rvClinics.setItemAnimator(new DefaultItemAnimator());
        rvClinics.setAdapter(tabAdapter);

       /* ImageView mLogo = (ImageView)getActivity().findViewById(R.id.ivSearch);
        mLogo.setVisibility(View.VISIBLE);
        mLogo.setImageResource(R.drawable.dir);*/
        
       // rvClinics.setAdapter(tabAdapter);
        /*context = getActivity();
        getAllWidgets(view);
        setAdapter();*/
        return view;
    }


    public void onRefresh(ArrayList<ClinicDO> arrClinic) {
        if (arrClinic != null && arrClinic.size() > 0) {
            tvNotFound.setVisibility(View.GONE);
            rvClinics.setVisibility(View.VISIBLE);
            tabAdapter.refresh(arrClinic);
        } else {
            tvNotFound.setVisibility(View.VISIBLE);
            rvClinics.setVisibility(View.GONE);
        }

    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
