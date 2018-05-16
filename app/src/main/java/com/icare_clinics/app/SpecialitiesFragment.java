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
import android.widget.TextView;

import com.icare_clinics.app.adapter.SpecialityTabAdapter;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by Mallikarjuna.K on 27-Dec-16.
 */

public class SpecialitiesFragment extends Fragment {
   // private GridView rvSpecialist;
    ArrayList<Integer> allDrawableImages=new ArrayList<>();
    ArrayList<String>allStringNames=new ArrayList<>();
    private TypedArray allImages,allNames;

    private Context context;
    private TextView tvNotFound;
   // private  SpecialityTabAdapter tabAdapter;
    private  ArrayList<SpecializationDO> arrSpeciality;
    private RecyclerView rvSpecialist;
    //private SpecialityAdapter tabAdapter;
    private SpecialityTabAdapter tabAdapter;
    private ClinicDO clinicDO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.specialities_tab,container,false);
        context = getActivity();
        rvSpecialist=(RecyclerView) view.findViewById(R.id.rvSpecialist);
       // rvSpecialist=(GridView)view.findViewById(R.id.gvSpecialist);
        tvNotFound=(TextView)view.findViewById(R.id.tvNotFoundSpe);
        //tabAdapter=new SpecialityTabAdapter(context,arrSpeciality);
        clinicDO=((ClinicDetailsActivity)context).getClinicDO();
        tabAdapter = new SpecialityTabAdapter(context,arrSpeciality,clinicDO);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        rvSpecialist.setLayoutManager(mLayoutManager);
        rvSpecialist.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        rvSpecialist.setItemAnimator(new DefaultItemAnimator());
        rvSpecialist.setAdapter(tabAdapter);
       /* ImageView mLogo = (ImageView)getActivity().findViewById(R.id.ivSearch);
        mLogo.setVisibility(View.GONE);
        mLogo.setImageResource(R.drawable.dir);
     */
        return view;
    }



    public void onRefresh(ArrayList<SpecializationDO> arrSpeciality) {
        if (arrSpeciality != null && arrSpeciality.size() > 0) {
            tvNotFound.setVisibility(View.GONE);
            rvSpecialist.setVisibility(View.VISIBLE);
            tabAdapter.refresh(arrSpeciality);
        } else {
            tvNotFound.setVisibility(View.VISIBLE);
            rvSpecialist.setVisibility(View.GONE);
        }

    }

    /*private void getAllWidgets(View view) {
        rvSpecialist=(GridView)view.findViewById(R.id.gvSpecialist);
        allImages =getResources().obtainTypedArray(R.array.all_images);
        allNames =getResources().obtainTypedArray(R.array.all_names);
    }
    private void setAdapter() {
        for (int i=0;i<allNames.length();i++){
            allDrawableImages.add(allImages.getResourceId(i,0));
            allStringNames.add(allNames.getString(i));
        }
        //DoctorsTabAdapter tabAdapter=new DoctorsTabAdapter(context,allDrawableImages,allStringNames);
        rvSpecialist.setAdapter(tabAdapter);
    }*/
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
