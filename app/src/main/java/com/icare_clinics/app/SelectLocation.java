package com.icare_clinics.app;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.icare_clinics.app.common.AppConstants;

import java.util.ArrayList;

/**
 * Created by Ganesh.D on 24-01-2017.
 */
public class SelectLocation  extends  BaseActivity
{
    ListView lvLocation;
    EditText searchLocation;
    LinearLayout llDoctors;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    public void initialise()
    {
        llDoctors = (LinearLayout) inflater.inflate(R.layout.activity_select_locations, null);
        llDoctors.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llDoctors);
        initialiseControls();
        loadData();

    }

    @Override
    public void initialiseControls()
    {
        searchLocation=(EditText)llDoctors.findViewById(R.id.searchLocation);
        lvLocation=(ListView)llDoctors.findViewById(R.id.lvLocation);

    }

    @Override
    public void loadData()
    {
        list = new ArrayList<String>();
        String locationByDoctor = getIntent().getStringExtra("GET_DOCTOR");
        if (AppConstants.DOCTORS_LIST.size()> 0)
        {
            for (int i=0;i<AppConstants.DOCTORS_LIST.size();i++)
            {
                if(locationByDoctor.equalsIgnoreCase(AppConstants.DOCTORS_LIST.get(i).name))
                {
                    list .add(AppConstants.DOCTORS_LIST.get(i).location);
                }
            }
        }
        if(list!=null&&list.size()>0) {
            adapter = new ArrayAdapter<String>(SelectLocation.this, R.layout.list_speciality, R.id.specialityTitles, list);
            lvLocation.setAdapter(adapter);
        }
        lvLocation.setTextFilterEnabled(true);
        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.specialityTitles);
                String s=tv.getText().toString();
                searchLocation.setText(s);
                Intent intent=new Intent(SelectLocation.this,AppointmentForm.class);
                intent.putExtra("LOCATION",s);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
