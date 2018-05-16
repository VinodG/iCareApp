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
import com.icare_clinics.app.R;
import com.icare_clinics.app.adapter.InsuranceAdapter;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by WINIT on 01-Mar-17.
 */

public class InsuranceAffiliatesFragment extends Fragment {
    private RecyclerView rvInsuranceCover;
    private TextView tvNotFound;
    private ArrayList<InsuranceDo> arrAffiliatesInsu;
    private Context context;
    private InsuranceAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View view = inflater.inflate(R.layout.insurance_cover_layout, container, false);
        rvInsuranceCover = (RecyclerView) view.findViewById(R.id.rvInsuranceCover);
        tvNotFound = (TextView) view.findViewById(R.id.tvNotFound);
        Bundle insuCoverBundle = getArguments();
        arrAffiliatesInsu = (ArrayList)insuCoverBundle.getSerializable("affiliatesInsurance");
        if(arrAffiliatesInsu != null && arrAffiliatesInsu.size() >0) {
            rvInsuranceCover.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            mAdapter=new InsuranceAdapter(context,arrAffiliatesInsu);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            rvInsuranceCover.setLayoutManager(mLayoutManager);
            rvInsuranceCover.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(7), true));
            rvInsuranceCover.setItemAnimator(new DefaultItemAnimator());
            rvInsuranceCover.setAdapter(mAdapter);

        }else{
            rvInsuranceCover.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
