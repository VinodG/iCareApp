package com.icare_clinics.app;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.adapter.ViewDetailsAdapter;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.util.ArrayList;


public class ViewDetailsActivity extends BaseActivity {

    private LinearLayout llMain;
    private RecyclerView rvWater;
    private ViewDetailsAdapter adapter;

    private ArrayList<WaterDO> arrWaterDO;
    private USerDataDA mUserDa;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public void initialise() {
        llMain = (LinearLayout) inflater.inflate(R.layout.activity_view_details, null);
        llMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llMain);
//        toolbar.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.view_details);
        ivBack.setVisibility(View.VISIBLE);
        llBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);
        setStatusBarColor();

        initialiseControls();
        loadData();

        layoutManager=new GridLayoutManager(ViewDetailsActivity.this,2);
        rvWater.setLayoutManager(layoutManager);
        rvWater.setHasFixedSize(true);

        adapter=new ViewDetailsAdapter(ViewDetailsActivity.this,arrWaterDO);
        rvWater.setAdapter(adapter);
    }

    @Override
    public void initialiseControls() {
        rvWater=(RecyclerView)llMain.findViewById(R.id.rvWater);
    }

    @Override
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strDate= CalendarUtil.getDate();
                mUserDa=new USerDataDA(ViewDetailsActivity.this);
                arrWaterDO=mUserDa.getWaterData(strDate);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (arrWaterDO!=null && arrWaterDO.size()>0){
                            adapter.refresh(arrWaterDO);
                        }
                    }
                });
            }
        }).start();
    }
}
