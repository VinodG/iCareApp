package com.icare_clinics.app;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icare_clinics.app.adapter.MyRemindersAdapter;
import com.icare_clinics.app.dataobject.MyRemindersDo;

import java.util.ArrayList;

/**
 * Created by Mallikarjuna.K on 05-01-2017.
 */

public class MyRemindersActivity extends BaseActivity {
    Button button;
    ListView listView;
    MyRemindersAdapter adapter = null;
    ArrayList<MyRemindersDo> list = new ArrayList<MyRemindersDo>();
    LinearLayout llMyRemiders;

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reminders);

        button = (Button) findViewById(R.id.btnAddReminder);
        listView = (ListView) findViewById(R.id.lv);

        adapter = new MyRemindersAdapter(MyRemindersActivity.this, list);
        listView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRemindersActivity.this, AddRemindersActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }*/



    @Override
    public void initialise() {
        llMyRemiders=(LinearLayout)inflater.inflate(R.layout.my_reminders,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMyRemiders,param);
        setStatusBarColor();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText("Save");
        tvCancel.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText("MyReminders");
        ivBack.setVisibility(View.VISIBLE);
        initialiseControls();
    }

    @Override
    public void initialiseControls() {

        button = (Button)llMyRemiders.findViewById(R.id.btnAddReminder);
        listView = (ListView)llMyRemiders.findViewById(R.id.lv);

        adapter = new MyRemindersAdapter(MyRemindersActivity.this, list);
        listView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRemindersActivity.this, AddRemindersActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String MedicineName = null, Dosage = null, Days = null, Timings = null, Duration = null;
        if (requestCode == 2) {
            MedicineName = data.getStringExtra("MED_NAME");
            Dosage = data.getStringExtra("DOSAGE");
            Days = data.getStringExtra("DAYS");
            Timings = data.getStringExtra("TIMINGS");
            Duration = data.getStringExtra("DURATION");

        }
        list.add(new MyRemindersDo(MedicineName, Dosage, Days, Timings, Duration));
        //Log.d("List size :", list.size()+"" );
        adapter.notifyDataSetChanged();
//        adapter = new MyRemindersAdapter(MyRemindersActivity.this, list);
//        listView.setAdapter(adapter);

    }
}