package com.icare_clinics.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.icare_clinics.app.adapter.DoctorsTabAdapter;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by Mallikarjuna.K on 27-Dec-16.
 */
public class DoctorsFragment extends Fragment {

    // private GridView rvDoctors;
//    ArrayList<Drawable>allDrawableImages=new ArrayList<>();
    ArrayList<Integer>allDrawableImages=new ArrayList<>();
    ArrayList<String>allStringNames=new ArrayList<>();
    private TypedArray allImages,allNames;
    private Context context;
    private TextView tvNotFound;
    private  DoctorsTabAdapter tabAdapter;
    private  ArrayList<DoctorDo> arrDoctor;
    private RecyclerView rvDoctors;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.doctors_grid_items,container,false);
        context = getActivity();
        //rvDoctors=(GridView)view.findViewById(R.id.gvDoctors);
        rvDoctors=(RecyclerView) view.findViewById(R.id.rvDoctors);
        tvNotFound=(TextView)view.findViewById(R.id.tvNotFoundDoctor);
        tabAdapter=new DoctorsTabAdapter(context,arrDoctor);
        
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        rvDoctors.setLayoutManager(mLayoutManager);
        rvDoctors.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        rvDoctors.setItemAnimator(new DefaultItemAnimator());
        rvDoctors.setAdapter(tabAdapter);
       // rvDoctors.setAdapter(tabAdapter);
       /* getAllWidgets(view);
        setAdapter();*/
        return view;
    }

    public void onRefresh(ArrayList<DoctorDo> arrDoctor) {
        if (arrDoctor != null && arrDoctor.size() > 0) {
            tvNotFound.setVisibility(View.GONE);
            rvDoctors.setVisibility(View.VISIBLE);
            tabAdapter.refresh(arrDoctor);
        } else {
            tvNotFound.setVisibility(View.VISIBLE);
            rvDoctors.setVisibility(View.GONE);
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

  /*  private void getAllWidgets(View view) {
        rvDoctors=(GridView)view.findViewById(R.id.gvDoctors);
        tvNotFound=(TextView)view.findViewById(R.id.tvNotFound);
        allImages =getResources().obtainTypedArray(R.array.all_images);
        allNames =getResources().obtainTypedArray(R.array.all_names);
    }*/

    /*private void setAdapter() {
        for (int i=0;i<allNames.length();i++){
            allDrawableImages.add(allImages.getResourceId(i,0));
            allStringNames.add(allNames.getString(i));
        }
         tabAdapter=new DoctorsTabAdapter(context,allDrawableImages,allStringNames);
        rvDoctors.setAdapter(tabAdapter);
    }*/
}