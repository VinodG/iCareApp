package com.icare_clinics.app;

import android.content.Context;
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
import com.icare_clinics.app.dataobject.ResponseDO;

import java.util.ArrayList;

public class SelectSpeciality extends BaseActivity
{



    private LinearLayout llSpeciality;
    private EditText searchSpeciality;
    private ListView listSpeciality;
    ArrayAdapter adapter;

    Context context = SelectSpeciality.this;
    private ArrayList<String> arraySpeciality;


    @Override
    public void initialise()
    {
        llSpeciality = (LinearLayout) inflater.inflate(R.layout.activity_select_speciality, null);
        llSpeciality.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llSpeciality);
        initialiseControls();
        loadData();

    }

    @Override
    public void initialiseControls() {
        searchSpeciality=(EditText)llSpeciality.findViewById(R.id.searchSpeciality);
        listSpeciality=(ListView)llSpeciality.findViewById(R.id.listSpeciality);

    }

    @Override
    public void loadData()
    {


        //vinod
        arraySpeciality =  AppConstants.SPECIALISTS_LIST;
        if(arraySpeciality!=null&&arraySpeciality.size()>0) {
            adapter = new ArrayAdapter<String>(SelectSpeciality.this, R.layout.list_speciality, R.id.specialityTitles, arraySpeciality);
            listSpeciality.setAdapter(adapter);
        }
        listSpeciality.setTextFilterEnabled(true);
        searchSpeciality.addTextChangedListener(new TextWatcher() {
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
        listSpeciality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.specialityTitles);
                String s=tv.getText().toString();
                searchSpeciality.setText(s);
                Intent intent=new Intent(SelectSpeciality.this,AppointmentForm.class);
                intent.putExtra("SPECIALITY",s);
                setResult(1,intent);
                finish();
            }
        });

    }
    @Override
    public void dataRetrieved(ResponseDO response) {


    }

}

    /*private class SpecialityFilterAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<String> list = new ArrayList<String>();

        public SpecialityFilterAdapter(Context context,  ArrayList<String> arraySpeciality)
        {
            this.context = context;
            list = arraySpeciality;
        }


        @Override
        public int getCount()
        {
            if(list == null)
            {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {

            SpecialityAdapter.MyViewHolder mViewHolder;

            if (view == null)
            {
                view = inflater.inflate(R.layout.list_speciality, viewGroup, false);
            }
            TextView tvSpeciality =  (TextView) view.findViewById(R.id.specialityTitles);
            ImageView ivUnderLine = (ImageView)view.findViewById(R.id.ivUnderLine);
            tvSpeciality.setText(list .get(i).toString());
            return view;
        }*/


   /* private void getDataInList() {
        for (int i = 0; i<title.length; i++) {
            // Create a new object for each list item
            SpecialityDo ld = new SpecialityDo();
            ld.setSpeciality(title[i]);
            // Add this object into the ArrayList myList
            myList.add(ld);
        }*/

