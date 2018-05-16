package com.icare_clinics.app;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.adapter.AppointmentFormSpecialityAdaptor;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;

public class SelectSpecialities extends BaseActivity {
    private RecyclerView rvselectSpeciality;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout llSelectSpeciality, llSpecialities, llList;
    private AppointmentFormSpecialityAdaptor sAdapter;
    private ArrayList<SpecializationDO> arrSpeciality, arrSpecialityTemp;
    private EditText ed_search;
    private TextView tvNoDataFound;
    String clinics[];


    @Override
    public void initialise() {
        llSelectSpeciality = (LinearLayout) inflater.inflate(R.layout.activity_select_specialities, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llSelectSpeciality, param);
        setTypeFaceUbuntuRegular(llSelectSpeciality);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Select Speciality");
        ivBack.setVisibility(View.VISIBLE);
//        ivBack.setImageResource(R.drawable.back_white);
        setStatusBarColor();
        initialiseControls();
        loadData();

    }

    @Override
    public void initialiseControls() {
        rvselectSpeciality = (RecyclerView) llSelectSpeciality.findViewById(R.id.rvsearchSelectSpeciality);
        llSpecialities = (LinearLayout) llSelectSpeciality.findViewById(R.id.llSpecialities);
        llList = (LinearLayout) llSelectSpeciality.findViewById(R.id.llList);
        tvNoDataFound = (TextView) llSelectSpeciality.findViewById(R.id.tvNoDataFound);
        ed_search = (EditText) llSelectSpeciality.findViewById(R.id.ed_search);

        rvselectSpeciality.setHasFixedSize(true);
        sAdapter = new AppointmentFormSpecialityAdaptor(arrSpeciality, SelectSpecialities.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvselectSpeciality.setLayoutManager(mLayoutManager);
        rvselectSpeciality.setAdapter(sAdapter);
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

    @Override
    public void loadData() {
        SpecialityDA specialityDA = new SpecialityDA(SelectSpecialities.this);
        arrSpeciality = specialityDA.getSpeciality();
        if (arrSpeciality != null && arrSpeciality.size() > 0)
            sAdapter.refresh(arrSpeciality);
    }


    public void filter(String text) {
        if (text.equalsIgnoreCase("")) {
            sAdapter.refresh(arrSpeciality);
        } else {
            arrSpecialityTemp = new ArrayList<>();
            for (SpecializationDO specialityDo : arrSpeciality) {
                if (specialityDo.name.toLowerCase().contains(text.toLowerCase())) {
                    arrSpecialityTemp.add(specialityDo);
                }
            }
        }
        if (arrSpecialityTemp.size() > 0) {
            llList.setVisibility(View.VISIBLE);
            tvNoDataFound.setVisibility(View.GONE);
            if (arrSpecialityTemp.size() > 0) {
                llSpecialities.setVisibility(View.VISIBLE);
                sAdapter.refresh(arrSpecialityTemp);

            } else {
                llSpecialities.setVisibility(View.GONE);
            }
        } else {
            llList.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.VISIBLE);
        }

    }


}
