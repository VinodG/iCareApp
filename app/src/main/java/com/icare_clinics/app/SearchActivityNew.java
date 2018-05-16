package com.icare_clinics.app;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.SpecializationDO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class SearchActivityNew extends BaseActivity {

    private LinearLayout llSearch ;

    MyExpandableListAdapterNew mAdapter;
    PinnedHeaderExpListView elv;

    private int mPinnedHeaderBackgroundColor;
    private int mPinnedHeaderTextColor;
    SpecialityDA specialityDA;
    DoctorDA doctorDA;
    ClinicsDA clinicsDA;
    private ArrayList<ClinicDO> arrSearchLocations, arrSearchLocationsTemp;
    private ArrayList<DoctorDo> arrSearchDoctor, arrSearchDoctorTemp;
    private ArrayList<SpecializationDO> arrSpeciality, arrSpecialityTemp;
    private LinkedHashMap<String,Object> map=new LinkedHashMap<String, Object>();
    private LinkedHashMap<String,Object> tempMap=new LinkedHashMap<String, Object>();
    //    private String[] groups = { "Clinics", "Specialities", "Doctors"};
    private EditText ed_search;
    private TypedArray ivIcons;

    @Override
    public void initialise() {

        llSearch = (LinearLayout) inflater.inflate(R.layout.activity_search_new, null);

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
//        tvTitle.setTextSize(getResources().getDimension(R.dimen.text_size_very_small_8));

        setStatusBarColor();
        initialiseControls();
        loadData();

        mAdapter = new MyExpandableListAdapterNew(map);
        elv.setAdapter(mAdapter);

        mPinnedHeaderBackgroundColor = getResources().getColor(R.color.white);
        mPinnedHeaderTextColor = getResources().getColor(android.R.color.black);

        elv.setGroupIndicator(null);
        View h = LayoutInflater.from(this).inflate(R.layout.search_list_header, (ViewGroup) findViewById(R.id.root), false);
        elv.setPinnedHeaderView(h);
        elv.setOnScrollListener((AbsListView.OnScrollListener) mAdapter);
        elv.setDividerHeight(0);
        for (int i=0;i<3;i++)
            elv.expandGroup(i);

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });
    }

    @Override
    public void initialiseControls() {

        elv = (PinnedHeaderExpListView)llSearch. findViewById(R.id.list);
        ed_search=(EditText) llSearch.findViewById(R.id.ed_search);

        ivIcons = getResources().obtainTypedArray(R.array.array_search);

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

    public void filter(String text) {
        if (text.equalsIgnoreCase("")) {
            mAdapter.refresh(map);
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
            }
            for (SpecializationDO specialityDo : arrSpeciality) {
                if (specialityDo.name.toLowerCase().contains(text.toLowerCase())) {
                    arrSpecialityTemp.add(specialityDo);
                }
            }
            //for setting data
            if(arrSearchLocationsTemp.size()>0 || arrSearchDoctorTemp.size()>0 || arrSpecialityTemp.size()>0) {

                tempMap.put("clinics",arrSearchLocationsTemp);
                tempMap.put("specialities",arrSpecialityTemp);
                tempMap.put("doctors",arrSearchDoctorTemp);
                mAdapter.refresh(tempMap);
            }
        }

    }


    @Override
    public void loadData() {

        clinicsDA = new ClinicsDA(SearchActivityNew.this);
        doctorDA = new DoctorDA(SearchActivityNew.this);
        specialityDA = new SpecialityDA(SearchActivityNew.this);

        arrSearchLocations = clinicsDA.getClinics();
        arrSearchDoctor = doctorDA.getDoctor();
        arrSpeciality = specialityDA.getSpeciality();

        if (arrSearchDoctor != null && arrSearchDoctor.size() > 0) {
            Set<DoctorDo> set = new HashSet<DoctorDo>(arrSearchDoctor);
            arrSearchDoctor = new ArrayList<DoctorDo>(set);
        }
        map.put("clinics",arrSearchLocations);
        map.put("specialities",arrSpeciality);
        map.put("doctors",arrSearchDoctor);

/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                clinicsDA = new ClinicsDA(SearchActivityNew.this);
                doctorDA = new DoctorDA(SearchActivityNew.this);
                specialityDA = new SpecialityDA(SearchActivityNew.this);

                arrSearchLocations = clinicsDA.getClinics();
                arrSearchDoctor = doctorDA.getDoctor();
                arrSpeciality = specialityDA.getSpeciality();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (arrSearchDoctor != null && arrSearchDoctor.size() > 0) {
                            Set<DoctorDo> set = new HashSet<DoctorDo>(arrSearchDoctor);
                            arrSearchDoctor = new ArrayList<DoctorDo>(set);
                        }
                            map.put("clinics",arrSearchLocations);
                            map.put("specialities",arrSpeciality);
                            map.put("doctors",arrSearchDoctor);

                            mAdapter.refresh(map);
                    }
                });
            }
        }).start();
*/
    }

    public class MyExpandableListAdapterNew extends BaseExpandableListAdapter implements PinnedHeaderExpListView.PinnedHeaderAdapter, AbsListView.OnScrollListener {

        public LinkedHashMap<String,Object>map;
        private List<String> grps;
        private String[] groups = { "Clinics", "Specialities", "Doctors"};
        private  String [] keys ={ "clinics","specialities","doctors"};
        private ClinicDO clinicDO;
        private SpecializationDO specializationDO;
        private DoctorDo doctorDo;

        public MyExpandableListAdapterNew(LinkedHashMap<String, Object> map) {
            this.map=map;
            this.grps=new ArrayList<>();
            this.grps.addAll(this.map.keySet());
        }

        public void refresh(LinkedHashMap<String,Object> hashMap){
            this.map=hashMap;
            this.grps=new ArrayList<>();
            this.grps.addAll(this.map.keySet());
            notifyDataSetChanged();
        }
        public Object getChild(int groupPosition, int childPosition) {
            return  ((List<String>) map.get(this.grps.get(groupPosition))).get(childPosition);
        }


        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition)
        {
            String key= keys[groupPosition];
            if(groupPosition==0) {
                if (map.get(key) != null)
                    return ((ArrayList<ClinicDO>) map.get(key)).size();
            }
            if(groupPosition==1) {
                if (map.get(key) != null)
                    return ((ArrayList<SpecializationDO>) map.get(key)).size();
            }
            if(groupPosition==2) {
                if (map.get(key) != null)
                    return ((ArrayList<DoctorDo>) map.get(key)).size();
            }

            return  0;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_new,null);
            TextView textView = (TextView)view.findViewById(R.id.tvdetails);
            LinearLayout llChild=(LinearLayout)view.findViewById(R.id.llChild);
            view.setTag(R.string.tag_group,groupPosition);
            view.setTag(R.string.tag_child,childPosition);

            if (groupPosition==0) {
                clinicDO = (ClinicDO) getChild(groupPosition, childPosition);
                textView.setText(clinicDO.description);

            }else if (groupPosition==1){
                specializationDO = (SpecializationDO) getChild(groupPosition, childPosition);
                textView.setText(specializationDO.name);

            } else if (groupPosition==2){
                doctorDo = (DoctorDo) getChild(groupPosition, childPosition);
                textView.setText(doctorDo.name);
//                textView.setText(getChild(groupPosition, childPosition).toString());
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int groupPos =  (Integer) view.getTag(R.string.tag_group);
                    int childPos =  (Integer) view.getTag(R.string.tag_child);
//                    Toast.makeText(SearchActivityNew.this, position+" is clicked ",Toast.LENGTH_SHORT).show();
                    if (groupPos==0){
                        clinicDO = (ClinicDO) getChild(groupPos, childPos);
                        clickOnClinicDetails(clinicDO);
                    }else if (groupPos==1){
                        specializationDO = (SpecializationDO) getChild(groupPos, childPos);
                        clickOnSpecialityDetails(specializationDO);
                    }else if (groupPos==2){
                        doctorDo = (DoctorDo) getChild(groupPos, childPos);
                        clickOnDoctorDetails(doctorDo);
                    }
                }
            });
            return view;
        }

        public Object getGroup(int groupPosition) {
            return grps.get(groupPosition);
        }

        public int getGroupCount() {
            return map.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.title,null);
            TextView textView=(TextView)view.findViewById(R.id.tvClinics);
            ImageView imageView=(ImageView)view.findViewById(R.id.ivClinics);

            textView.setText(getGroup(groupPosition).toString());
            if (groupPosition==1){
                imageView.setImageResource(ivIcons.getResourceId(groupPosition,0));
            }
//            imageView.setImageResource(R.drawable.clinics_small);
            return view;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

        public void configurePinnedHeader(View v, int position, int alpha) {
            TextView header = (TextView) v;
            final String title = (String) getGroup(position);

            header.setText(title);
//            Drawable drawable=getResources().getDrawable(R.drawable.clinics_small);
            Drawable drawable=ivIcons.getDrawable(position);
            header.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

            if (alpha == 255) {
                header.setBackground(getResources().getDrawable(R.drawable.custom_border));
                header.setTextColor(mPinnedHeaderTextColor);
            } else {
                header.setBackground(getResources().getDrawable(R.drawable.custom_border));
               /* header.setBackgroundColor(Color.argb(alpha,
                        Color.red(mPinnedHeaderBackgroundColor),
                        Color.green(mPinnedHeaderBackgroundColor),
                        Color.blue(mPinnedHeaderBackgroundColor)));*/
                header.setTextColor(Color.argb(alpha,
                        Color.red(mPinnedHeaderTextColor),
                        Color.green(mPinnedHeaderTextColor),
                        Color.blue(mPinnedHeaderTextColor)));
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (view instanceof PinnedHeaderExpListView) {
                ((PinnedHeaderExpListView) view).configureHeaderView(firstVisibleItem);
            }
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }

    private void clickOnDoctorDetails(DoctorDo doctorDo) {
        Intent intent=new Intent(context, DoctorDetail.class);
        intent.putExtra("doctorDO",doctorDo);
        startActivity(intent);
    }

    private void clickOnSpecialityDetails(SpecializationDO specialityDo) {
        Intent intent = new Intent(context, ClinicDetailsActivity.class);
        intent.putExtra("SpecializationDO", specialityDo);
        startActivity(intent);
    }

    private void clickOnClinicDetails(ClinicDO clinicDO) {
        Intent intent = new Intent(context, ClinicDetailsActivity.class);
        intent.putExtra("ClinicDo", clinicDO);
        startActivity(intent);
    }
}
