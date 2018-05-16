package com.icare_clinics.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icare_clinics.app.AddReminder;
import com.icare_clinics.app.adapter.MyReminderAdapter;
import com.icare_clinics.app.dataaccesslayer.ReminderDA;
import com.icare_clinics.app.dataobject.ReminderDo;

import java.util.ArrayList;

public class MyReminder extends BaseActivity{

  RelativeLayout lLMain;
  private RecyclerView rvReminder;
  private TextView tvNoDataFound;
  private Button btnreminder;
    private MyReminderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ReminderDo> arrReminder=new ArrayList<ReminderDo>();
    ReminderDo reminderDo;
    ReminderDA reminderDA;
    @Override
    public void initialise() {

        lLMain = (RelativeLayout) inflater.inflate(R.layout.activity_my_reminder, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);
        setStatusBarColor();
        initialiseControls();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.my_reminder);
        tvTitle.setTextColor(Color.WHITE);
        mLayoutManager = new LinearLayoutManager(MyReminder.this);
        mAdapter = new MyReminderAdapter(MyReminder.this,arrReminder);
        rvReminder.setHasFixedSize(true);
        rvReminder.setLayoutManager(mLayoutManager);
        rvReminder.setAdapter(mAdapter);
        reminderDA=new ReminderDA(MyReminder.this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                arrReminder=reminderDA.getReminderData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(arrReminder==null || arrReminder.size()<=0)
                        {
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            rvReminder.setVisibility(View.GONE);
                        }else{
                            tvNoDataFound.setVisibility(View.GONE);
                            rvReminder.setVisibility(View.VISIBLE);
                            mAdapter.refresh(arrReminder);
                        }
                    }
                });

            }
        }).start();

       // arrReminder=new ArrayList<ReminderDo>();


       /* if(arrReminder==null && arrReminder.size()<=0)
        {
            tvNoDataFound.setVisibility(View.VISIBLE);
            rvReminder.setVisibility(View.GONE);
        }else{
            tvNoDataFound.setVisibility(View.GONE);
            rvReminder.setVisibility(View.VISIBLE);
        }*/
      //  AppConstants.arrReminder=new ArrayList<ReminderDo>();
      //  mAdapter = new MyReminderAdapter(MyReminder.this,arrReminder);
        //  mLayoutManager = new LinearLayoutManager(getApplicationContext());


        loadData();
    }

    @Override
    public void initialiseControls() {
        rvReminder=(RecyclerView)lLMain.findViewById(R.id.rvReminder);
        tvNoDataFound=(TextView) lLMain.findViewById(R.id.tvNoDataFound);
        btnreminder=(Button) lLMain.findViewById(R.id.btnreminder);
        //rvReminder = (RecyclerView) lLMain.findViewById(R.id.rvNews);
        if(arrReminder!=null&&arrReminder.size()>0){
            tvNoDataFound.setVisibility(View.GONE);
            rvReminder.setVisibility(View.VISIBLE);
            mAdapter.refresh(arrReminder);
        }else{
            tvNoDataFound.setVisibility(View.VISIBLE);
            rvReminder.setVisibility(View.GONE);
        }

        btnreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyReminder.this,AddReminder.class);
                intent.putExtra("position",String.valueOf(100));
                startActivityForResult(intent,4);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        reminderDo=new ReminderDo();
        if(requestCode ==4) {
            if (resultCode == RESULT_OK) {
                reminderDo = (ReminderDo) intent.getSerializableExtra("ADD_REMINDER");
                String posData=intent.getExtras().getString("position");;
                int pos=Integer.parseInt(posData);

                if (reminderDo != null) {
                    if(pos!=100){
                      arrReminder.remove(pos);
                        arrReminder.add(pos,reminderDo);
                    }else{
                        if(arrReminder==null)
                            arrReminder=new ArrayList<ReminderDo>();

                            arrReminder.add(reminderDo);


                    }
                    if(arrReminder==null || arrReminder.size()<=0)
                    {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        rvReminder.setVisibility(View.GONE);
                    }else{
                        tvNoDataFound.setVisibility(View.GONE);
                        rvReminder.setVisibility(View.VISIBLE);
                        mAdapter.refresh(arrReminder);
                    }
//                    mAdapter.refresh(arrReminder);
                }

            }
        }
    }
    @Override
    public void loadData() {

    }

}
