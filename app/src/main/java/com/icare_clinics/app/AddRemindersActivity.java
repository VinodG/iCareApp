package com.icare_clinics.app;

import android.content.Intent;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddRemindersActivity extends BaseActivity {

    TextView tvMedicineName,tvDosage,tvTimings,tvQty,tvDays,tvDuration;
    LinearLayout llTimings,llDays,llDuration;
    ImageView ivNegative,ivPositive,ivDownArrow_Timings,ivDownArrow_Days,ivDownArrow_Duration;
    Button btnSave;
    TableLayout tbDays,tbDuration,tbTimings;
    TableRow tr;
    ArrayList<String> listTimings=new ArrayList<>();
    ArrayList<String> listDays=new ArrayList<>();
    ArrayList<String> listDuration=new ArrayList<>();
    TypedArray allTimings,allDays,allDuration;
    int columns  = 4;
    LinearLayout llAddRemider;
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminders);


        //initializeControls();
        addlist();



        ivDownArrow_Timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTimings.setVisibility(View.VISIBLE);
            }
        });
        ivDownArrow_Duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDuration.setVisibility(View.VISIBLE);
            }
        });

        ivDownArrow_Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDays.setVisibility(View.VISIBLE);
            }
        });

        tvMedicineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddRemindersActivity.this,MedicineListActivity.class);
                startActivityForResult(intent,3);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MedicineName=tvMedicineName.getText().toString();
                String Dosage=tvDosage.getText().toString();
                String Days=tvDays.getText().toString();
                String Timings=tvTimings.getText().toString();
                String Duration=tvDuration.getText().toString();
                Intent intent=new Intent(AddRemindersActivity.this,MyRemindersActivity.class);
                intent.putExtra("MED_NAME",MedicineName);
                intent.putExtra("DOSAGE",Dosage);
                intent.putExtra("DAYS",Days);
                intent.putExtra("TIMINGS",Timings);
                intent.putExtra("DURATION",Duration);
                setResult(2,intent);
                finish();
            }
        });
    }*/



    @Override
    public void initialise() {

        llAddRemider=(LinearLayout)inflater.inflate(R.layout.add_reminders,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llAddRemider, param);
        setStatusBarColor();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText("Save");
        tvCancel.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText("Add Reminder");
        ivBack.setVisibility(View.VISIBLE);
        initialiseControls();
        addlist();
        loadData();

        ivDownArrow_Timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTimings.setVisibility(View.VISIBLE);
            }
        });
        ivDownArrow_Duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDuration.setVisibility(View.VISIBLE);
            }
        });

        ivDownArrow_Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDays.setVisibility(View.VISIBLE);
            }
        });

        tvMedicineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddRemindersActivity.this,MedicineListActivity.class);
                startActivityForResult(intent,3);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MedicineName=tvMedicineName.getText().toString();
                String Dosage=tvDosage.getText().toString();
                String Days=tvDays.getText().toString();
                String Timings=tvTimings.getText().toString();
                String Duration=tvDuration.getText().toString();
                Intent intent=new Intent(AddRemindersActivity.this,MyRemindersActivity.class);
                intent.putExtra("MED_NAME",MedicineName);
                intent.putExtra("DOSAGE",Dosage);
                intent.putExtra("DAYS",Days);
                intent.putExtra("TIMINGS",Timings);
                intent.putExtra("DURATION",Duration);
                setResult(2,intent);
                finish();
            }
        });
    }

    @Override
    public void initialiseControls() {
        tvMedicineName          =(TextView)llAddRemider.findViewById(R.id.tvMedicineName);
        tvDosage                =(TextView)llAddRemider.findViewById(R.id.tvDosage);
        tvTimings               =(TextView)llAddRemider.findViewById(R.id.tvTimings);
        tvQty                   =(TextView)llAddRemider.findViewById(R.id.tvQty);
        tvDays                  =(TextView)llAddRemider.findViewById(R.id.tvDays);
        tvDuration              =(TextView)llAddRemider.findViewById(R.id.tvDuration);

        ivNegative              =(ImageView)llAddRemider.findViewById(R.id.ivNegative);
        ivPositive              =(ImageView)llAddRemider.findViewById(R.id.ivPositive);
        ivDownArrow_Timings     =(ImageView)llAddRemider.findViewById(R.id.ivDownArrow_Timings);
        ivDownArrow_Days        =(ImageView)llAddRemider.findViewById(R.id.ivDownArrow_Days);
        ivDownArrow_Duration    =(ImageView)llAddRemider.findViewById(R.id.ivDownArrow_Duration);

        llTimings       =(LinearLayout)llAddRemider.findViewById(R.id.llTimings);
        llDays          =(LinearLayout)llAddRemider.findViewById(R.id.llDays);
        llDuration      =(LinearLayout)llAddRemider.findViewById(R.id.llDuration);

       // btnSave         =(Button) findViewById(R.id.btnSave);

        tbDays          =(TableLayout)llAddRemider.findViewById(R.id.tbDays);
        tbDuration      =(TableLayout)llAddRemider.findViewById(R.id.tbDuration);
        tbTimings       =(TableLayout)llAddRemider.findViewById(R.id.tbTimings);
    }

    @Override
    public void loadData() {

    }

    private void addlist() {
        allTimings=getResources().obtainTypedArray(R.array.all_timings);
        for (int i=0;i<allTimings.length();i++){
            listTimings.add(allTimings.getString(i));
        }
        allDays=getResources().obtainTypedArray(R.array.all_days);
        for (int i=0;i<allDays.length();i++){
            listDays.add(allDays.getString(i));
        }
        allDuration=getResources().obtainTypedArray(R.array.duration);
        for (int i=0;i<allDuration.length();i++){
            listDuration.add(allDuration.getString(i));
        }

        createTableTimings( R.id.tbTimings,   columns,  allTimings.length(), listTimings);
        createTableDays( R.id.tbDays,   columns,  allDays.length(), listDays);
        createTableDuration( R.id.tbDuration,   columns,  allDuration.length(), listDuration);
    }

    private void createTableDays(int tableLayoutid, int columns, int sizeOfList,ArrayList<String> list)
    {
        TableRow tr;
        tbDays= (TableLayout)findViewById(tableLayoutid);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        for (int i=0;i<sizeOfList;)
        {
            tr  = new TableRow(this);
            tr.setId(1000+i);
            for (int  j=0;(j<columns) && (i<sizeOfList);i++,j++)
            {
                tr.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT ));
                final TextView textview = new TextView(this);
                textview.setText(list.get(i).toString());
                textview.setId(1000+i);
                textview.setWidth((int)(width/columns));
                textview.setTextColor(getResources().getColor(R.color.dark_blue2));//getColor(R.color.dark_blue2));
                tr.addView(textview);
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDays(view);
                    }
                });
            }
            tbDays.addView(tr , new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void createTableTimings(int tableLayoutid, int columns, int sizeOfList,ArrayList<String> list)
    {
        TableRow tr;
        tbTimings= (TableLayout)findViewById(tableLayoutid);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        for (int i=0;i<sizeOfList;)
        {
            tr  = new TableRow(this);
            tr.setId(2000+i);
            for (int  j=0;(j<columns) && (i<sizeOfList);i++,j++)
            {
                tr.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT ));
                final TextView textview = new TextView(this);
                textview.setText(list.get(i).toString());
                textview.setId(2000+i);
                textview.setWidth((int)(width/columns));
                //textview.setTextColor(Color.BLACK);
                textview.setTextColor(getResources().getColor(R.color.dark_blue2));
                tr.addView(textview);
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setTimings(view);
                    }
                });
            }
            tbTimings.addView(tr , new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
    private void createTableDuration(int tableLayoutid, int columns, int sizeOfList,ArrayList<String> list)
    {
        TableRow tr;
        tbDuration= (TableLayout)findViewById(tableLayoutid);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        for (int i=0;i<sizeOfList;)
        {
            tr  = new TableRow(this);
            tr.setId(3000+i);
            for (int  j=0;(j<columns) && (i<sizeOfList);i++,j++)
            {
                tr.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT ));
                final TextView textview = new TextView(this);
                textview.setText(list.get(i).toString());
                textview.setId(3000+i);
                textview.setWidth((int)(width/columns));
               // textview.setTextColor(Color.BLACK);
                textview.setTextColor(getResources().getColor(R.color.dark_blue2));
                tr.addView(textview);
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDuration(view);
                    }
                });
            }
            tbDuration.addView(tr , new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
/*
    private void initializeControls() {

        tvMedicineName          =(TextView)findViewById(R.id.tvMedicineName);
        tvDosage                =(TextView)findViewById(R.id.tvDosage);
        tvTimings               =(TextView)findViewById(R.id.tvTimings);
        tvQty                   =(TextView)findViewById(R.id.tvQty);
        tvDays                  =(TextView)findViewById(R.id.tvDays);
        tvDuration              =(TextView)findViewById(R.id.tvDuration);

        ivNegative              =(ImageView) findViewById(R.id.ivNegative);
        ivPositive              =(ImageView) findViewById(R.id.ivPositive);
        ivDownArrow_Timings     =(ImageView) findViewById(R.id.ivDownArrow_Timings);
        ivDownArrow_Days        =(ImageView) findViewById(R.id.ivDownArrow_Days);
        ivDownArrow_Duration    =(ImageView) findViewById(R.id.ivDownArrow_Duration);

        llTimings       =(LinearLayout) findViewById(R.id.llTimings);
        llDays          =(LinearLayout) findViewById(R.id.llDays);
        llDuration      =(LinearLayout) findViewById(R.id.llDuration);

        btnSave         =(Button) findViewById(R.id.btnSave);

        tbDays          =(TableLayout)findViewById(R.id.tbDays);
        tbDuration      =(TableLayout)findViewById(R.id.tbDuration);
        tbTimings       =(TableLayout)findViewById(R.id.tbTimings);

    }
*/

    private void setTimings(View v) {
        String s=((TextView)v).getText().toString();

        if(!tvTimings.getText().toString().contains(s))
        {
            tvTimings.setText(tvTimings.getText().toString()+", "+s);
        }else {
            Toast.makeText(getApplicationContext(),"AllReady Selected !!!",Toast.LENGTH_SHORT).show();
        }

        llTimings.setVisibility(View.GONE);
    }

    private void setDays(View v) {
        String s=((TextView)v).getText().toString();
        tvDays.setText(s);
        llDays.setVisibility(View.GONE);

    }

    private void setDuration(View v) {
        String s=((TextView)v).getText().toString();
        tvDuration.setText(s);
        llDuration.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3){
            String s=data.getStringExtra("MED_NAME");
            tvMedicineName.setText(s);
        }
    }
}
