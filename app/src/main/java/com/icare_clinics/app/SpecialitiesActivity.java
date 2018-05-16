package com.icare_clinics.app;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.adapter.SpecialityAdapter;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;

public class
SpecialitiesActivity extends BaseActivity {
    LinearLayout lLSpecialities;
    private GridView gvSpecialist;
    private RecyclerView rvSpeciality;
    private TextView tvNotFoundSpe;
    private SpecialityAdapter mAdapter;
    private ArrayList<SpecializationDO> arrSpeciality;
    @Override
    public void initialise() {
        lLSpecialities = (LinearLayout) inflater.inflate(R.layout.activity_specialities, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLSpecialities, param);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(getString(R.string.speciality));
        tvTitle.setTextColor(Color.WHITE);
        setStatusBarColor();
        initialiseControls();

    }

    @Override
    public void initialiseControls() {
        rvSpeciality=(RecyclerView) lLSpecialities.findViewById(R.id.rvSpeciality);
        //gvSpecialist=(GridView)lLSpecialities.findViewById(R.id.gvSpeciality);
        tvNotFoundSpe=(TextView)lLSpecialities.findViewById(R.id.tvNotFoundSpeciality);
        mAdapter = new SpecialityAdapter(SpecialitiesActivity.this,arrSpeciality);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvSpeciality.setLayoutManager(mLayoutManager);
        rvSpeciality.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
        rvSpeciality.setItemAnimator(new DefaultItemAnimator());
        rvSpeciality.setAdapter(mAdapter);
        // gvSpecialist.setAdapter(mAdapter);
       /* mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);*/
        loadData();
    }

    @Override
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                SpecialityDA specialityDA=new SpecialityDA(SpecialitiesActivity.this);
                arrSpeciality=specialityDA.getSpeciality();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (arrSpeciality != null && arrSpeciality.size() > 0)
                            mAdapter.refresh(arrSpeciality);
                    }
                });
            }
        }).start();

    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
