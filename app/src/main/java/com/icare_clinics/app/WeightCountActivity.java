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
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.StepCountDO;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.util.ArrayList;

/**
 * Created by srikrishna.nunna on 23-03-2017.
 */

public class WeightCountActivity extends BaseActivity {

    private LinearLayout llMain;
    private BarChart barChart;
    private ArrayList<StepCountDO> arrDays=new ArrayList<>();
    private TextView tvDaySelect,tvDayUnselect,tvWeekSelect,tvWeekUnselect,tvMonthSelect,tvMonthUnselect,tvYearSelect,tvYearUnselect;
    private TextView tvStepsCount,tvDailyAvg,tvHighest,tvLowest,tvAverage;
    private WeightDO weightDO;
    private ArrayList<WeightDO>arrWeightCnt = new ArrayList<WeightDO>();
    private ArrayList<WeightDO>arrWeightcntWeek = new ArrayList<WeightDO>() ;
    private ArrayList<WeightDO>arrWeightcntMonth= new ArrayList<WeightDO>() ;
    private ArrayList<WeightDO>arrWeightcntYear= new ArrayList<WeightDO>();
    private ArrayList<String>arrWeight=new ArrayList<String>();

    @Override
    public void initialise() {
        llMain=(LinearLayout) inflater.inflate(R.layout.activity_weight_count,null);
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

        tvTitle.setText("Weight Count");

        setStatusBarColor();
        initialiseControls();
        loadData();

      /*  tvDaySelect.setVisibility(View.VISIBLE);
        tvDayUnselect.setVisibility(View.GONE);
        tvWeekUnselect.setVisibility(View.VISIBLE);
        tvMonthUnselect.setVisibility(View.VISIBLE);
        tvYearUnselect.setVisibility(View.VISIBLE);
        getDataObjectforBarGraph();
         showBarGraph();
            calMaxMin();*/


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
                getDataObjectforBarGraph();
                showBarGraph();
                calMaxMin();
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
                getDataObjectforBarGraph();
                showBarGraph();
                calMaxMin();
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
                getDataObjectforBarGraph();
                showBarGraph();
                calMaxMin();
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
                getDataObjectforBarGraph();
                showBarGraph();
                calMaxMin();
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
        USerDataDA uSerDataDA=new USerDataDA(WeightCountActivity.this);
        arrWeightCnt=uSerDataDA.getWeightsForGivenDate(CalendarUtil.getToday());

        String endDay=CalendarUtil.getToday();
        String startDay=CalendarUtil.getlastSevenDays();
        arrWeightcntWeek=uSerDataDA.getWeightsBetweenDates(startDay,endDay,1);

        String FirstDayOfMonth=CalendarUtil.getFirstDayOfMonth();
        arrWeightcntMonth=uSerDataDA.getWeightsBetweenDates(FirstDayOfMonth,endDay,2);

        String FirstDayOfYear=CalendarUtil.getFirstDayOfYear();
        arrWeightcntYear=uSerDataDA.getWeightsBetweenDates(FirstDayOfYear,endDay,3);

    }

    private void calMaxMin() {

        int size=arrWeight.size();
        int max=Integer.parseInt(arrWeight.get(0));
        int min=Integer.parseInt(arrWeight.get(0));
        int sum=0,avg=0;
        for (int i=0;i<size;i++){
            int temp=Integer.parseInt(arrWeight.get(i));
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


    private void showBarGraph() {
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

            for (int i = 0; i < arrWeight.size(); i++) {
                yVals1.add(new BarEntry(Float.parseFloat(arrWeight.get(i).toString()), i));
                xVals.add(" ");
            }

            BarDataSet set1;

            set1 = new BarDataSet(yVals1, "planned");
            set1.setDrawValues(false);
            set1.setBarSpacePercent(90f);
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

    private void getDataObjectforBarGraph() {
        arrWeight.clear();

            for (int i = 0; i < arrWeightCnt.size(); i++)
            {
                arrWeight.add( arrWeightCnt.get(i).weight); ;
            }
    }
}
