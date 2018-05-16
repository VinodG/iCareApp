package com.icare_clinics.app;

import android.content.Intent;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mallikarjuna.K on 05-01-2017.
 */

public class MedicineListActivity extends BaseActivity {

    EditText etMedicineName;
    ListView lv;
    ArrayAdapter <String>adapter=null;
    ArrayList<String>arrayList=new ArrayList<>();
    TypedArray allMedicines;
    LinearLayout llMedicine;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list_activity);

        etMedicineName=(EditText)findViewById(R.id.etMedicineName);
        lv=(ListView) findViewById(R.id.lvMedicineName);
        lv.setTextFilterEnabled(true);

        allMedicines=getResources().obtainTypedArray(R.array.all_medicines);

        for (int i=0;i<allMedicines.length();i++){
            arrayList.add(allMedicines.getString(i));
        }
        adapter=new ArrayAdapter<String>(MedicineListActivity.this,R.layout.medicine_list_item,R.id.tvListItem,arrayList);
        lv.setAdapter(adapter);
        etMedicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MedicineListActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.tvListItem);
                String s=tv.getText().toString();
                etMedicineName.setText(s);
                Intent intent=new Intent(MedicineListActivity.this,AddRemindersActivity.class);
                intent.putExtra("MED_NAME",s);
                setResult(3,intent);
                finish();
            }
        });
    }
*/

    @Override
    public void initialise() {
        llMedicine=(LinearLayout)inflater.inflate(R.layout.medicine_list_activity,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMedicine,param);
        setStatusBarColor();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
       // tvCancel.setText("Save");
        tvCancel.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText("Medicine List");
        ivBack.setVisibility(View.VISIBLE);
        initialiseControls();
    }

    @Override
    public void initialiseControls() {
        etMedicineName=(EditText)llMedicine.findViewById(R.id.etMedicineName);
        lv=(ListView)llMedicine.findViewById(R.id.lvMedicineName);
        lv.setTextFilterEnabled(true);

        allMedicines=getResources().obtainTypedArray(R.array.all_medicines);

        for (int i=0;i<allMedicines.length();i++){
            arrayList.add(allMedicines.getString(i));
        }
        adapter=new ArrayAdapter<String>(MedicineListActivity.this,R.layout.medicine_list_item,R.id.tvListItem,arrayList);
        lv.setAdapter(adapter);
        etMedicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MedicineListActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.tvListItem);
                String s=tv.getText().toString();
                etMedicineName.setText(s);
                Intent intent=new Intent(MedicineListActivity.this,AddRemindersActivity.class);
                intent.putExtra("MED_NAME",s);
                setResult(3,intent);
                finish();
            }
        });
    }

    @Override
    public void loadData() {

    }
}
