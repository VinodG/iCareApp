package com.icare_clinics.app;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.icare_clinics.app.dataobject.StepCountDO;

import java.util.ArrayList;

import static com.icare_clinics.app.R.id.tvMonth;

/**
 * Created by srikrishna.nunna on 22-03-2017.
 */

public class StepsCountActivity extends BaseActivity {
    private LinearLayout llMain;
    private BarChart barChart;
    private ArrayList<StepCountDO>arrDays=new ArrayList<>();
    private ArrayList<StepCountDO>arrWeeks=new ArrayList<>();
    private ArrayList<StepCountDO>arrMonths=new ArrayList<>();
    private ArrayList<StepCountDO>arrYear=new ArrayList<>();
    private TextView tvDaySelect,tvDayUnselect,tvWeekSelect,tvWeekUnselect,tvMonthSelect,tvMonthUnselect,tvYearSelect,tvYearUnselect;
    private TextView tvStepsCount,tvDailyAvg,tvHighest,tvLowest,tvAverage;
    @Override
    public void initialise() {
        llMain=(LinearLayout) inflater.inflate(R.layout.activity_steps_count,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        llBody.addView(llMain,params);
        ivMenu.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        //ivCancel.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);

        tvTitle.setText("Steps Count");
        setStatusBarColor();
        initialiseControls();

        tvDayUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDayUnselect.setVisibility(View.GONE);
                tvDaySelect.setVisibility(View.VISIBLE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);
                getDataforDay();
                showBarGraph(arrDays);
                calMaxMin(arrDays);
            }
        });

        tvWeekUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.GONE);
                tvWeekSelect.setVisibility(View.VISIBLE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);
                getDataforWeek();
                showBarGraph(arrWeeks);
                calMaxMin(arrWeeks);
            }
        });

        tvMonthUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.GONE);
                tvMonthSelect.setVisibility(View.VISIBLE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);
                getDataforMonth();
                showBarGraph(arrMonths);
                calMaxMin(arrMonths);
            }
        });

        tvYearUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.GONE);
                tvYearSelect.setVisibility(View.VISIBLE);
                getDataforYear();
                showBarGraph(arrYear);
                calMaxMin(arrYear);
            }
        });


    }

    @Override
    public void initialiseControls() {
        barChart=(BarChart)findViewById(R.id.chart);

        tvDaySelect       =(TextView)llMain.findViewById(R.id.tvDaySelect);
        tvDayUnselect     =(TextView)llMain.findViewById(R.id.tvDayUnselect);
        tvWeekSelect      =(TextView)llMain.findViewById(R.id.tvWeekSelect);
        tvWeekUnselect    =(TextView)llMain.findViewById(R.id.tvWeekUnselect);
        tvMonthSelect     =(TextView)llMain.findViewById(R.id.tvMonthSelect);
        tvMonthUnselect   =(TextView)llMain.findViewById(R.id.tvMonthUnselect);
        tvYearSelect      =(TextView)llMain.findViewById(R.id.tvYearSelect);
        tvYearUnselect    =(TextView)llMain.findViewById(R.id.tvYearUnselect);
        tvStepsCount      =(TextView)llMain.findViewById(R.id.tvStepsCount);
        tvDailyAvg        =(TextView)llMain.findViewById(R.id.tvDailyAvg);
        tvHighest         =(TextView)llMain.findViewById(R.id.tvHighest);
        tvLowest          =(TextView)llMain.findViewById(R.id.tvLowest);
        tvAverage         =(TextView)llMain.findViewById(R.id.tvAverage);
    }

    @Override
    public void loadData() {

    }
    private void calMaxMin(ArrayList<StepCountDO>arrMaxMin) {

        int size=arrMaxMin.size();
        int max=arrMaxMin.get(0).days;
        int min=arrMaxMin.get(0).days;
        int sum=0,avg=0;
        for (int i=0;i<size;i++){
            int temp=arrMaxMin.get(i).days;
            sum=sum+temp;
            if (temp >=max){
                max=temp;
            }
            if (temp<min){
                min=temp;
            }
        }
        avg=sum/size;
        tvHighest.setText(String.valueOf(max));
        tvLowest.setText(String.valueOf(min));
        tvAverage.setText(String.valueOf(avg));
    }


    private void showBarGraph(ArrayList<StepCountDO>arrgraph) {
        try {

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(false);//remove xAxis lable names

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setTextColor(Color.WHITE);

            barChart.setPinchZoom(false);
            barChart.zoom(1.0f, 0, 0, 0);
            barChart.setTouchEnabled(false);
            barChart.enableScroll();
            barChart.setHorizontalScrollBarEnabled(true);
            barChart.setVerticalScrollBarEnabled(true);
            barChart.setBackgroundColor(getResources().getColor(R.color.statusbarblue));
            barChart.setDescription("");
            barChart.setDrawGridBackground(false); // set the bar graph color along with its background
            barChart.getLegend().setEnabled(false);//to remove legends
            barChart.getAxisLeft().setTextColor(Color.WHITE);//to set the YAxis Lable colour

            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            ArrayList<String> xVals = new ArrayList<String>();

         /*   for (int i = 0; i < arrgraph.size(); i++) {
                xVals.add(arrgraph.get(i).monthName);
            }*/

            for (int i = 0; i < arrgraph.size(); i++) {
                yVals1.add(new BarEntry(arrgraph.get(i).days, i));
                xVals.add(" ");
            }

            BarDataSet set1;

            set1 = new BarDataSet(yVals1, "planned");
            set1.setDrawValues(false);
            set1.setBarSpacePercent(10f);
            set1.setColor(Color.WHITE);

            barChart.getAxisRight().setEnabled(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            barChart.animateY(3000);
            BarData data = new BarData(xVals,dataSets);
//            data.setValueFormatter(new LargeValueFormatter());
            barChart.setData(data);
            barChart.invalidate();// refresh after setting data

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataforDay() {
        int[] strdays = {20, 250, 200, 300, 70};
        arrDays.clear();
        for (int i = 0; i < strdays.length; i++) {
            StepCountDO stepCountDO = new StepCountDO();
            stepCountDO.days = strdays[i];
            arrDays.add(stepCountDO);
        }
    }
    private void getDataforWeek() {
        int[] strdays = {80,30,40,50,60,70,20, 250, 200, 300, 320, 350,400};
        arrWeeks.clear();
        for (int i = 0; i < strdays.length; i++) {
            StepCountDO stepCountDO = new StepCountDO();
            stepCountDO.days = strdays[i];
            arrWeeks.add(stepCountDO);
        }
    }
    private void getDataforMonth() {
        int[] strdays = {80,30,40,50,60,70,20,20, 250, 200, 300, 320, 350, 70,80,30,40,50,60,70,20,80,30,40,50,60,70,20};
        arrMonths.clear();
        for (int i = 0; i < strdays.length; i++) {
            StepCountDO stepCountDO = new StepCountDO();
            stepCountDO.days = strdays[i];
            arrMonths.add(stepCountDO);
        }
    }
    private void getDataforYear() {
        int[] strdays = {20, 250, 200, 30, 20, 50, 70,80,30,40,50,60,70,20,20, 250, 200, 30, 20, 50,
                70,80,30,40,50,60,70,20,80,30,40,50,60,70,20,
                70,80,30,40,50,60,70,20,80,30,40,50,60,70,20,
                70,80,30,40,50,60,70,20,80,30,40,50,60,70,20};
        arrYear.clear();
        for (int i = 0; i < strdays.length; i++) {
            StepCountDO stepCountDO = new StepCountDO();
            stepCountDO.days = strdays[i];
            arrYear.add(stepCountDO);
        }
    }
}
