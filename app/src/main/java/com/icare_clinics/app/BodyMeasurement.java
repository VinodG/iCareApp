package com.icare_clinics.app;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class BodyMeasurement extends BaseActivity {

    private LinearLayout llMain;
    private TextView tvWeight_Unselect,tvWeight_Select,tvBmi_Unselect,tvBmi_Select,tvDaySelect,tvDayUnselect,tvWeekSelect,tvWeekUnselect,
            tvMonthSelect,tvMonthUnselect,tvYearSelect,tvYearUnselect;
    private TextView tvAvg,tvDate,tvHighest,tvLowest,tvAverage,tvYAxis,tvXaxis,tvNoDataFound;
    private LineChart lineChart;
    public USerDataDA uSerDataDA;
    private ArrayList<WeightDO>arrDay;
    private ArrayList<WeightDO>arrWeek;
    private ArrayList<WeightDO>arrMonth;
    private ArrayList<WeightDO>arrYear;
    private ArrayList<String> arrTemp=new ArrayList<>();
    private boolean isWeight=true;
    private RelativeLayout rlGraph;
    private  float sum = 0, avg=0;
    private String date="",strDate="";
    private String[] str={""};
    private int flag=1;

    @Override
    public void initialise() {

        llMain=(LinearLayout) inflater.inflate(R.layout.activity_body_measurement,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f);

        llBody.addView(llMain,params);
        ivMenu.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.body_measurement);
        setStatusBarColor();
        initialiseControls();
        loadData();

        tvWeight_Unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeight=true;
                flag=1;
                tvWeight_Unselect.setVisibility(View.GONE);
                tvWeight_Select.setVisibility(View.VISIBLE);
                tvBmi_Unselect.setVisibility(View.VISIBLE);
                tvBmi_Select.setVisibility(View.GONE);

                setVisibilityForDay();
                if (arrDay!=null && arrDay.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    tvYAxis.setText("Weight->");
                    tvXaxis.setText("per 3hrs->");
                    showBarGraphForDay(arrDay);
                    getWeights(arrDay);
                    calMaxMin(arrTemp);
                    tvAvg.setText("Average: "+new DecimalFormat("##.##").format(avg));;
                    tvDate.setText(date);
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
        tvBmi_Unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeight=false;
                flag=1;
                tvWeight_Unselect.setVisibility(View.VISIBLE);
                tvWeight_Select.setVisibility(View.GONE);
                tvBmi_Unselect.setVisibility(View.GONE);
                tvBmi_Select.setVisibility(View.VISIBLE);

                setVisibilityForDay();
                if (arrDay!=null && arrDay.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    showBarGraphForDay(arrDay);
                    tvYAxis.setText("BMI->");
                    tvXaxis.setText("per 3hrs->");
                    getWeights(arrDay);
                    calMaxMin(arrTemp);
                    tvAvg.setText("Average: "+new DecimalFormat("##.##").format(avg));;
                    tvDate.setText(date);
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
        tvDayUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                if (isWeight){
                    tvWeight_Select.setVisibility(View.VISIBLE);
                    tvWeight_Unselect.setVisibility(View.GONE);
                }
                setVisibilityForDay();
                if (arrDay!=null && arrDay.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    showBarGraphForDay(arrDay);
                    tvXaxis.setText("per 3hrs->");
                    getWeights(arrDay);
                    calMaxMin(arrTemp);
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
        tvWeekUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                if (isWeight){
                    tvWeight_Select.setVisibility(View.VISIBLE);
                    tvWeight_Unselect.setVisibility(View.GONE);
                }
                setVisibilityForWeek();
                if (arrWeek!=null && arrWeek.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    showBarGraphForDay(arrWeek);
                    tvXaxis.setText("per Day->");
                    getWeights(arrWeek);
                    calMaxMin(arrTemp);
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
        tvMonthUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=3;
                if (isWeight){
                    tvWeight_Select.setVisibility(View.VISIBLE);
                    tvWeight_Unselect.setVisibility(View.GONE);
                }
                setVisibilityForMonth(v);
                if (arrMonth!=null && arrMonth.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    tvXaxis.setText("per Week->");
                    showBarGraphForDay(arrMonth);
                    getWeights(arrMonth);
                    calMaxMin(arrTemp);
                }else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
        tvYearUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=4;
                if (isWeight){
                    tvWeight_Select.setVisibility(View.VISIBLE);
                    tvWeight_Unselect.setVisibility(View.GONE);
                }
                setVisibilityForYear(v);
                if (arrYear!=null && arrYear.size()>0) {
                    rlGraph.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    tvXaxis.setText("per Month->");
                    showBarGraphForDay(arrYear);
                    getWeights(arrYear);
                    calMaxMin(arrTemp);
                }else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rlGraph.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    public void initialiseControls() {

        lineChart           =(LineChart)llMain.findViewById(R.id.lineChart);
        tvWeight_Unselect   =(TextView)llMain.findViewById(R.id.tvWeight_Unselect);
        tvWeight_Select     =(TextView)llMain.findViewById(R.id.tvWeight_Select);
        tvBmi_Unselect      =(TextView)llMain.findViewById(R.id.tvBmi_Unselect);
        tvBmi_Select        =(TextView)llMain.findViewById(R.id.tvBmi_Select);
        tvDaySelect         =(TextView)llMain.findViewById(R.id.tvDaySelect);
        tvDayUnselect       =(TextView)llMain.findViewById(R.id.tvDayUnselect);
        tvWeekSelect        =(TextView)llMain.findViewById(R.id.tvWeekSelect);
        tvWeekUnselect      =(TextView)llMain.findViewById(R.id.tvWeekUnselect);
        tvMonthSelect       =(TextView)llMain.findViewById(R.id.tvMonthSelect);
        tvMonthUnselect     =(TextView)llMain.findViewById(R.id.tvMonthUnselect);
        tvYearSelect        =(TextView)llMain.findViewById(R.id.tvYearSelect);
        tvYearUnselect      =(TextView)llMain.findViewById(R.id.tvYearUnselect);
        tvAvg            =(TextView)llMain.findViewById(R.id.tvAvg);
        tvDate           =(TextView)llMain.findViewById(R.id.tvDate);
        tvHighest        =(TextView)llMain.findViewById(R.id.tvHighest);
        tvLowest         =(TextView)llMain.findViewById(R.id.tvLowest);
        tvAverage        =(TextView)llMain.findViewById(R.id.tvAverage);
        tvYAxis          =(TextView)llMain.findViewById(R.id.tvYAxis);
        tvXaxis          =(TextView)llMain.findViewById(R.id.tvXaxis);
        tvNoDataFound    =(TextView)llMain.findViewById(R.id.tvNoDataFound);
        rlGraph          =(RelativeLayout) llMain.findViewById(R.id.rlGraph);
    }

    private void showBarGraphForDay(ArrayList<WeightDO>arrgraph) {
        try {

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);//remove xAxis lable names
            xAxis.setTextColor(Color.WHITE);

            YAxis rightAxis = lineChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setTextColor(Color.WHITE);

            lineChart.setPinchZoom(false);
            lineChart.zoom(1.0f, 0, 0, 0);
            lineChart.setTouchEnabled(false);
            lineChart.setHorizontalScrollBarEnabled(false);
            lineChart.setVerticalScrollBarEnabled(false);
            lineChart.setBackgroundColor(getResources().getColor(R.color.statusbarblue));
            lineChart.setDescription("");
            lineChart.setDrawGridBackground(false); // set the bar graph color along with its background
            lineChart.getLegend().setEnabled(false);//to remove legends
            lineChart.getAxisLeft().setTextColor(Color.WHITE);//to set the YAxis Lable colour

            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            ArrayList<String> xVals = new ArrayList<String>();

            if ((yVals1!=null && yVals1.size()>0)&&((xVals!=null && xVals.size()>0))){
                yVals1.clear();
                xVals.clear();
            }
            for (int i = 0; i < arrgraph.size(); i++) {
                if (isWeight) {
                    yVals1.add(new Entry(Float.parseFloat(arrgraph.get(i).weight.toString()), i));

                }else {
                    yVals1.add(new Entry(Float.parseFloat(arrgraph.get(i).bmi.toString()), i));
                }

                String str=arrgraph.get(i).date.toString();

                if (flag==1){
                    xVals.add(" ");

                }else if (flag==2){

                    String day=getDayfromDate(str);
                    xVals.add(day);

                }else if (flag==3){

                    xVals.add("week"+i);

                }else if (flag==4){
                    String month=getMonthString(str);
                    xVals.add(month);
                }
            }

            LineDataSet set1;

            set1 = new LineDataSet(yVals1, "planned");
            set1.setDrawValues(true);
            set1.setDrawCubic(true);//for getting smooth curves
            set1.setValueTextColor(getResources().getColor(R.color.green_dark));
            set1.setColor(Color.WHITE);

            lineChart.getAxisRight().setEnabled(false);

            lineChart.getXAxis().setAxisMinValue(0);
            lineChart.getAxisRight().setAxisMinValue(0);
            lineChart.getAxisLeft().setAxisMinValue(0);

//            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);

            lineChart.animateX(3000);
            LineData data = new LineData(xVals,dataSets);
//            data.setValueFormatter(new LargeValueFormatter());
            lineChart.setData(data);

            lineChart.invalidate();// refresh after setting data

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDayfromDate(String str) {

        String[] str1=str.split(" ");
        String[] str2=str1[0].split("-");
        String month=getMonth(Integer.parseInt(str2[1])-1);
        String firstthree=month.substring(0,3);
        String day=str2[2]+firstthree;
        return day;
    }
    private String getMonthString(String str) {

        String[] str1=str.split(" ");
        String[] str2=str1[0].split("-");
        String month=getMonth(Integer.parseInt(str2[1])-1);
        String firstthree=month.substring(0,3);
        return firstthree;
    }

    @Override
    public void loadData()
    {
        uSerDataDA = new USerDataDA(BodyMeasurement.this);

        String startTime=CalendarUtil.getStartOfDay();
        String endTime=CalendarUtil.getEndOfDay();

        String endDay= CalendarUtil.getToday();

        arrDay=uSerDataDA.getWeightsBetweenDates(startTime,endTime,0);

        String startDay=CalendarUtil.getlastSevenDays();
        arrWeek=uSerDataDA.getWeightsBetweenDates(startDay,endTime,1);

        String FirstDayOfMonth=CalendarUtil.getFirstDayOfMonth();
        arrMonth=uSerDataDA.getWeightsBetweenDates(FirstDayOfMonth,endDay,2);

        String FirstDayOfYear=CalendarUtil.getFirstDayOfYear();
        arrYear=uSerDataDA.getWeightsBetweenDates(FirstDayOfYear,endDay,3);

        if (arrDay!=null && arrDay.size()>0){

            isWeight=true;
            tvWeight_Unselect.setVisibility(View.GONE);
            tvWeight_Select.setVisibility(View.VISIBLE);
            tvBmi_Unselect.setVisibility(View.VISIBLE);
            tvBmi_Select.setVisibility(View.GONE);

            setVisibilityForDay();
            showBarGraphForDay(arrDay);

            if (arrDay!=null && arrDay.size()>0) {
                tvYAxis.setText("Weight->");
                tvXaxis.setText("per 3hrs->");
                getWeights(arrDay);
                calMaxMin(arrTemp);
            }
            strDate=arrDay.get(0).date;
            str=strDate.split(" ");
            date=str[0];
            tvAvg.setText("Average: "+new DecimalFormat("##.##").format(avg));
            tvDate.setText(date);
        }
        else {
            tvNoDataFound.setVisibility(View.VISIBLE);
            rlGraph.setVisibility(View.GONE);
        }
    }

    private void getWeights(ArrayList<WeightDO> arrDay) {

        if (arrTemp!=null && arrTemp.size()>0) {
            arrTemp.clear();
            for (int i = 0; i < arrDay.size(); i++) {

                if (isWeight) {
                    arrTemp.add(arrDay.get(i).weight);
                } else {
                    arrTemp.add(arrDay.get(i).bmi);
                }
            }
        }else {
            for (int i = 0; i < arrDay.size(); i++) {

                if (isWeight) {
                    arrTemp.add(arrDay.get(i).weight);
                } else {
                    arrTemp.add(arrDay.get(i).bmi);
                }
            }
        }
    }

    private void calMaxMin(ArrayList<String> arrTemp) {

        if (arrTemp!=null && arrTemp.size()>0) {

            int size = arrTemp.size();
            float max=0,min=0;
            float f1=Float.valueOf(arrTemp.get(0));
            float f2=Float.valueOf(arrTemp.get(0));
            max=f1;
            min=f2;
            sum = 0;
            avg = 0;

            for (int i = 0; i < size; i++) {

                float temp=0;
                float f=Float.valueOf(arrTemp.get(i));
                temp=f;
                sum = sum + temp;
                if (temp >= max) {
                    max = temp;
                }
                if (temp < min) {
                    min = temp;
                }
            }
            avg = sum / size;
            tvHighest.setText(new DecimalFormat("##.##").format(max));
            tvLowest.setText(new DecimalFormat("##.##").format(min));
            tvAverage.setText(new DecimalFormat("##.##").format(avg));
        }
    }

    private void setVisibilityForDay() {

        tvDayUnselect.setVisibility(View.GONE);
        tvDaySelect.setVisibility(View.VISIBLE);
        tvWeekUnselect.setVisibility(View.VISIBLE);
        tvWeekSelect.setVisibility(View.GONE);
        tvMonthUnselect.setVisibility(View.VISIBLE);
        tvMonthSelect.setVisibility(View.GONE);
        tvYearUnselect.setVisibility(View.VISIBLE);
        tvYearSelect.setVisibility(View.GONE);
    }
    private void setVisibilityForWeek() {

        tvDayUnselect.setVisibility(View.VISIBLE);
        tvDaySelect.setVisibility(View.GONE);
        tvWeekUnselect.setVisibility(View.GONE);
        tvWeekSelect.setVisibility(View.VISIBLE);
        tvMonthUnselect.setVisibility(View.VISIBLE);
        tvMonthSelect.setVisibility(View.GONE);
        tvYearUnselect.setVisibility(View.VISIBLE);
        tvYearSelect.setVisibility(View.GONE);
    }
    private void setVisibilityForMonth(View v) {

        tvDayUnselect.setVisibility(View.VISIBLE);
        tvDaySelect.setVisibility(View.GONE);
        tvWeekUnselect.setVisibility(View.VISIBLE);
        tvWeekSelect.setVisibility(View.GONE);
        tvMonthUnselect.setVisibility(View.GONE);
        tvMonthSelect.setVisibility(View.VISIBLE);
        tvYearUnselect.setVisibility(View.VISIBLE);
        tvYearSelect.setVisibility(View.GONE);
    }
    private void setVisibilityForYear(View v) {

        tvDayUnselect.setVisibility(View.VISIBLE);
        tvDaySelect.setVisibility(View.GONE);
        tvWeekUnselect.setVisibility(View.VISIBLE);
        tvWeekSelect.setVisibility(View.GONE);
        tvMonthUnselect.setVisibility(View.VISIBLE);
        tvMonthSelect.setVisibility(View.GONE);
        tvYearUnselect.setVisibility(View.GONE);
        tvYearSelect.setVisibility(View.VISIBLE);
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

}
