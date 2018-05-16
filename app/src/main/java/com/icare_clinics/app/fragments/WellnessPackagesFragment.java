package com.icare_clinics.app.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.adapter.MyExpandableListAdapter;
import com.icare_clinics.app.dataobject.WellnessPackageDo;

import java.util.ArrayList;
import java.util.HashMap;

public class WellnessPackagesFragment extends Fragment {
    private ExpandableListView lvWellNess;
    private TextView tvNotFound;
    private HashMap<String, ArrayList<WellnessPackageDo>> arrCoverInsu;
    private Context context;
    private MyExpandableListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View view = inflater.inflate(R.layout.wellness_pkgs_layout, container, false);
        lvWellNess = (ExpandableListView) view.findViewById(R.id.lvWellNess);
        tvNotFound = (TextView) view.findViewById(R.id.tvNotFound);
        Bundle insuCoverBundle = getArguments();
        arrCoverInsu = (HashMap<String, ArrayList<WellnessPackageDo>>)insuCoverBundle.getSerializable("Wellness");
        if(arrCoverInsu != null && arrCoverInsu.size() >0) {
            lvWellNess.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            mAdapter=new MyExpandableListAdapter(context, arrCoverInsu);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            lvWellNess.setChildDivider(getResources().getDrawable(R.drawable.custom_border));
            lvWellNess.setAdapter(mAdapter);
            lvWellNess.expandGroup(0);

        }else{
            lvWellNess.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
