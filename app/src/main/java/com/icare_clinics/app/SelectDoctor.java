package com.icare_clinics.app;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.icare_clinics.app.adapter.SelectDoctorsAdapter;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataobject.DoctorDo;
import java.util.ArrayList;

/**
 * Created by Ganesh.D on 24-01-2017.
 */
public class SelectDoctor extends BaseActivity
{
    private RecyclerView rvsearchSelectDoctors;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout llDoctors, llDoctorslist, llList;
    private SelectDoctorsAdapter dAdapter;
    private ArrayList<DoctorDo> arrDoctor, arrDoctorTemp;
    private EditText ed_search;
    private TextView tvNoDataFound;
    @Override
    public void initialise()
    {
        llDoctors = (LinearLayout) inflater.inflate(R.layout.activity_select_doctors, null);
        llDoctors.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llDoctors);
        setTypeFaceUbuntuRegular(llDoctors);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Select Doctors");
        ivBack.setVisibility(View.VISIBLE);
//        ivBack.setImageResource(R.drawable.back_white);
        setStatusBarColor();
        initialiseControls();
        loadData();

    }

    @Override
    public void initialiseControls()
    {
        rvsearchSelectDoctors = (RecyclerView) llDoctors.findViewById(R.id.rvsearchSelectDoctors);
        llDoctorslist = (LinearLayout) llDoctors.findViewById(R.id.llDoctorslist);
        llList = (LinearLayout) llDoctors.findViewById(R.id.llList);
        tvNoDataFound = (TextView) llDoctors.findViewById(R.id.tvNoDataFound);
        ed_search = (EditText) llDoctors.findViewById(R.id.ed_search);

        rvsearchSelectDoctors.setHasFixedSize(true);
        dAdapter = new SelectDoctorsAdapter(arrDoctor, SelectDoctor.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvsearchSelectDoctors.setLayoutManager(mLayoutManager);
        rvsearchSelectDoctors.setAdapter(dAdapter);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = ed_search.getText().toString();
                //  SearchActivity.this.adapter.getFilter().filter(charSequence);

                if (text.length() > 0) {
                    // tvCancel.setVisibility(View.VISIBLE);
                } else {
                    //tvCancel.setVisibility(View.GONE);

                }

                filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public void filter(String text) {
        if (text.equalsIgnoreCase("")) {
            dAdapter.refresh(arrDoctor);
        } else {
            arrDoctorTemp = new ArrayList<>();
            for (DoctorDo doctorDo : arrDoctor) {
                if (doctorDo.name.toLowerCase().contains(text.toLowerCase())) {
                    arrDoctorTemp.add(doctorDo);
                }
            }
        }
        if (arrDoctorTemp.size() > 0) {
            llList.setVisibility(View.VISIBLE);
            tvNoDataFound.setVisibility(View.GONE);
            if (arrDoctorTemp.size() > 0) {
                llDoctorslist.setVisibility(View.VISIBLE);
                dAdapter.refresh(arrDoctorTemp);

            } else {
                llDoctorslist.setVisibility(View.GONE);
            }
        } else {
            llList.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadData()
    {
        DoctorDA doctorDA = new DoctorDA(SelectDoctor.this);
        Intent intent=getIntent();
        String id=intent.getStringExtra("GET_DOCTOR");
        arrDoctor = doctorDA.getDoctorSpecialityWise(id);
        if (arrDoctor != null && arrDoctor.size() > 0)
            dAdapter.refresh(arrDoctor);

    }
}
