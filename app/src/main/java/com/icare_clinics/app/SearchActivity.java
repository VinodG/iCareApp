package com.icare_clinics.app;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.icare_clinics.app.adapter.DoctorAdapter;
import com.icare_clinics.app.adapter.SearchAdapter;
import com.icare_clinics.app.adapter.SpecialistAdapter;
import com.icare_clinics.app.common.ExpandableHeightRecyclerView;
import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.icare_clinics.app.R.dimen.text_size_normal;
import static com.icare_clinics.app.common.AppConstants.arrDoctor;

public class SearchActivity extends BaseActivity {
    private RecyclerView rvsearchLocations;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout llSearch,llLocation,llDoctors,llSpecialities,llList;
    private SearchAdapter mAdapter;
    private DoctorAdapter dAdapter;
    private SpecialistAdapter sAdapter;
    private ArrayList<ClinicDO> arrSearchLocations, arrSearchLocationsTemp;
    private ArrayList<DoctorDo> arrSearchDoctor, arrSearchDoctorTemp;
    private ArrayList<SpecializationDO> arrSpeciality, arrSpecialityTemp;
    private EditText ed_search;
    private ArrayAdapter<String> adapter;
    private ListView list_view;
    private TextView tvNoDataFound;
    private ScrollView scrollView;
    String clinics[];
    SpecialityDA specialityDA;
    DoctorDA doctorDA;
    ClinicsDA clinicsDA;

    private ExpandableHeightRecyclerView   rvsearchDoctors,rvsearchSpeciality;

    @Override
    public void initialise() {
        llSearch = (LinearLayout) inflater.inflate(R.layout.activity_search, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llSearch, param);
        setTypeFaceUbuntuRegular(llSearch);
        ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        //  ivSearch.setVisibility(View.VISIBLE);
        llBack.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);
        tvTitle.setText("Search by Doctor, Speciality or Location");
//        tvTitle.setTextSize(getResources().getDimension(R.dimen.text_size_normal));
//        tvTitle.setTextSize(getResources().getDimension(R.dimen.text_size_very_small_10));
//        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_normal));

        setStatusBarColor();
        initialiseControls();
        loadData();
    }

    @Override
    public void initialiseControls() {

        ed_search = (EditText) llSearch.findViewById(R.id.ed_search);
        rvsearchLocations = (RecyclerView) llSearch.findViewById(R.id.rvsearchLocations);
        llLocation = (LinearLayout) llSearch.findViewById(R.id.llLocation);
        llDoctors = (LinearLayout) llSearch.findViewById(R.id.llDoctors);
        llSpecialities = (LinearLayout) llSearch.findViewById(R.id.llSpecialities);
        llList = (LinearLayout) llSearch.findViewById(R.id.llList);
        tvNoDataFound = (TextView) llSearch.findViewById(R.id.tvNoDataFound);
        //list_view= (ListView) llSearch.findViewById(R.id.list_view);
        rvsearchLocations.setHasFixedSize(false);
        rvsearchLocations.setNestedScrollingEnabled(false);
        mAdapter = new SearchAdapter(arrSearchLocations, SearchActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvsearchLocations.setLayoutManager(mLayoutManager);
        rvsearchLocations.setAdapter(mAdapter);

        //Doctors
        rvsearchDoctors = (ExpandableHeightRecyclerView) llSearch.findViewById(R.id.rvsearchDoctors);
        rvsearchDoctors.setHasFixedSize(false);
        rvsearchDoctors.setNestedScrollingEnabled(false);
//        rvsearchDoctors.setNestedScrollingEnabled(false);
        dAdapter = new DoctorAdapter(arrSearchDoctor, SearchActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvsearchDoctors.setLayoutManager(mLayoutManager);
        rvsearchDoctors.setAdapter(dAdapter);

        rvsearchSpeciality = (ExpandableHeightRecyclerView) llSearch.findViewById(R.id.rvsearchSpeciality);
        rvsearchSpeciality.setHasFixedSize(false);
        rvsearchSpeciality.setNestedScrollingEnabled(false);
        sAdapter = new SpecialistAdapter(arrSpeciality, SearchActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvsearchSpeciality.setLayoutManager(mLayoutManager);
        rvsearchSpeciality.setAdapter(sAdapter);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = ed_search.getText().toString();
//                  SearchActivity.this.adapter.getFilter().filter(charSequence);

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
         clinicsDA = new ClinicsDA(SearchActivity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                arrSearchLocations = clinicsDA.getClinics();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  clinics = new String[arrSearchLocations.size() + 1];
                        if (arrSearchLocations != null && arrSearchLocations.size() > 0)
                            mAdapter.refresh(arrSearchLocations);
                    }
                });
            }
        }).start();

       /* for(int i=0;i<arrSearchLocations.size();i++){
            clinics[i]= arrSearchLocations.get(i).description;
        }*/


        doctorDA = new DoctorDA(SearchActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                arrSearchDoctor = doctorDA.getDoctor();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arrSearchDoctor != null && arrSearchDoctor.size() > 0) {
                            Set<DoctorDo> set = new HashSet<DoctorDo>(arrSearchDoctor);
                            arrSearchDoctor = new ArrayList<DoctorDo>(set);

                            dAdapter.refresh(arrSearchDoctor);
                        }

                    }
                });
            }
        }).start();


         specialityDA = new SpecialityDA(SearchActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                arrSpeciality = specialityDA.getSpeciality();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arrSpeciality != null && arrSpeciality.size() > 0)
                            sAdapter.refresh(arrSpeciality);

                    }
                });
            }
        }).start();


       /* adapter = new ArrayAdapter<String>(this, R.layout.list_search, R.id.tvdetails, clinics);
        list_view.setAdapter(adapter);*/
    }

    public void filter(String text) {
        if (text.equalsIgnoreCase("")) {
            mAdapter.refresh(arrSearchLocations);
            dAdapter.refresh(arrSearchDoctor);
            sAdapter.refresh(arrSpeciality);
        } else {
            arrSearchLocationsTemp = new ArrayList<>();
            arrSearchDoctorTemp = new ArrayList<>();
            arrSpecialityTemp = new ArrayList<>();
            for (ClinicDO clinicDO : arrSearchLocations) {
                if (clinicDO.description.toLowerCase().contains(text.toLowerCase())) {
                    arrSearchLocationsTemp.add(clinicDO);
                }
            }
            for (DoctorDo doctorDo : arrSearchDoctor) {
                if (doctorDo.name.toLowerCase().contains(text.toLowerCase())) {
                    arrSearchDoctorTemp.add(doctorDo);
                }
                for (SpecializationDO specialityDo : arrSpeciality) {
                    if (specialityDo.name.toLowerCase().contains(text.toLowerCase())) {
                        arrSpecialityTemp.add(specialityDo);
                    }
                }
            }
            //for setting data
            if(arrSearchLocationsTemp.size()>0||arrSearchDoctorTemp.size()>0||arrSpecialityTemp.size()>0) {
                llList.setVisibility(View.VISIBLE);
                tvNoDataFound.setVisibility(View.GONE);
                if (arrSearchLocationsTemp.size() > 0) {
                    llLocation.setVisibility(View.VISIBLE);
                    mAdapter.refresh(arrSearchLocationsTemp);

                } else {
                    llLocation.setVisibility(View.GONE);
                }
                if (arrSearchDoctorTemp.size() > 0) {
                    llDoctors.setVisibility(View.VISIBLE);
                    dAdapter.refresh(arrSearchDoctorTemp);

                } else {
                    llDoctors.setVisibility(View.GONE);
                }
                if (arrSpecialityTemp.size() > 0) {
                    llSpecialities.setVisibility(View.VISIBLE);
                    sAdapter.refresh(arrSpecialityTemp);

                } else {
                    llSpecialities.setVisibility(View.GONE);
                }
            }else{
                llList.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.VISIBLE);
            }
        }

    }
}
