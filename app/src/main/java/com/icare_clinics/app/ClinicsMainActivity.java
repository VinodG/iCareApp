package com.icare_clinics.app;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.adapter.ClinicsAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataobject.ClinicDO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ClinicsMainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ClinicsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //List<ClinicsDO> list = new ArrayList<>();
    private ArrayList<ClinicDO> arrClinic;
    LinearLayout lLMain;

    AppConstants appConstants;
    @Override
    public void initialise() {

        lLMain = (LinearLayout) inflater.inflate(R.layout.clinics_main_activity, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);

      //  appConstants=new AppConstants();
      //  ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        llBack.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.VISIBLE);

        tvTitle.setText(getString(R.string.clinics));

        setStatusBarColor();

        mRecyclerView = (RecyclerView) lLMain.findViewById(R.id.rvClinics);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ClinicsAdapter(arrClinic, ClinicsMainActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        loadData();
    }

    @Override
    public void initialiseControls() {

    }

    @Override
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClinicsDA clinicsDA=new ClinicsDA(ClinicsMainActivity.this);
                arrClinic=clinicsDA.getClinics();
//                Collections.reverse(arrClinic);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arrClinic != null && arrClinic.size() > 0)
                            mAdapter.refresh(arrClinic);
                    }
                });
            }
        }).start();
      /*  showLoader("Loading data.....");
        if (NetworkUtil.isNetworkConnectionAvailable(ClinicsMainActivity.this)) {
            String auth_token = AppConstants.auth_token;
            String Action = "getClinicsList";
            new CommonBL(ClinicsMainActivity.this, ClinicsMainActivity.this).getClinicsList(Action, auth_token);
        } else
            hideLoader();*/
    }

   /* @Override
    public void dataRetrieved(ResponseDO response) {
        hideLoader();
        if (response.method != null && (response.method == ServiceMethods.WS_CLINIC_LIST && response.data != null)) {
            if (response.data instanceof String) {
                showCustomDialog(ClinicsMainActivity.this, "", (String) response.data, getString(R.string.OK), "", "");
            } else {
                arrClinic = (ArrayList<ClinicDO>) response.data;
                if (arrClinic != null && arrClinic.size() > 0)
                    mAdapter.refresh(arrClinic);
                appConstants.arrClinicList=arrClinic;
            }
        }
        super.dataRetrieved(response);
    }*/
}
