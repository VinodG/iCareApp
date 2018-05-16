package com.icare_clinics.app;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.adapter.TimingAdapter;

import java.util.ArrayList;

public class TimeActivity extends BaseActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }
*/
    LinearLayout llTiming;
    private RecyclerView rvTiming;
    private TimingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //List<ClinicsDO> list = new ArrayList<>();
    String[] sampleString = {"5:00 Am","6:00 Am","7:00 Am","8:00 Am","9:00 Am","10:00 Am","11:00 Am","12:00 pm","1:00 Pm","2:00 Pm","3:00 Pm","4:00 Pm","5:00 Pm","6:00 Pm","7:00 Pm","8:00 Pm","9:00 Pm","10:00 Pm","11:00 Pm","12:00 Am" ,"1:00 Am","2:00 Am","3:00 Am","4:00 Am"};
   // String[] items = sampleString.split(",");

   // List<String>  timingList = new ArrayList<String>();
   private ArrayList<String> arrTiming;


    @Override
    public void initialise() {
        llTiming = (LinearLayout) inflater.inflate(R.layout.activity_time, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llTiming, param);
        setStatusBarColor();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText("SET");
        tvCancel.setTextColor(getResources().getColor(R.color.white));
        tvTitle.setText("Timings");
        ivBack.setVisibility(View.VISIBLE);
      //  initialiseControls();
        arrTiming=new ArrayList<String>();
      /*  for (String item : items) {
            arrTiming.add(item);
        }*/
        for(int i =  0; i < sampleString.length; i++){
            arrTiming.add(sampleString[i]);  //something like this?
        }
        rvTiming = (RecyclerView) llTiming.findViewById(R.id.rvTiming);
        rvTiming.setHasFixedSize(true);
        mAdapter = new TimingAdapter(arrTiming, TimeActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTiming.setLayoutManager(mLayoutManager);
        rvTiming.setAdapter(mAdapter);
    //    loadData();
    }


    @Override
    public void initialiseControls() {
        rvTiming=(RecyclerView)llTiming.findViewById(R.id.rvTiming);
    }

    @Override
    public void loadData() {

    }
}
