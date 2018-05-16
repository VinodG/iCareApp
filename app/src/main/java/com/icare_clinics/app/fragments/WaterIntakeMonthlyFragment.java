package com.icare_clinics.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.icare_clinics.app.R;
import com.icare_clinics.app.WaterIntakeHistory;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.icare_clinics.app.utilities.CalendarUtil.YYYY_MM_DD_FULL_PATTERN;
import static com.icare_clinics.app.utilities.CalendarUtil.getDateWith3letters;

/**
 * Created by Sreekanth.P on 25-07-2017.
 */

public class WaterIntakeMonthlyFragment extends Fragment {
    private Context context;
    private int dataSelection;
    private BarChart barChart;
    private ArrayList<WaterDO> arrGraph = new ArrayList<WaterDO>();
    private TextView tvdate;
    private TextView tvXAxis,tvYAxis;
    private boolean isDecrement=false;
    Calendar startCalender,tempCal;
    Calendar endCalender;
    private String startDate,endDate;
    private String tempDate,SelectedDate;
    public Calendar startCal;
    Preference preference;
    private String date=CalendarUtil.getPresentDate();

    public WaterIntakeMonthlyFragment(Context context, int i,Calendar startCal) {
        this.context=context;
        this.dataSelection=i;
        this.startCal=startCal;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preference=new Preference(context);

        View view = inflater.inflate(R.layout.weekly_fragment, container, false);

        SelectedDate =preference.getStringFromPreference(Preference.DATE_WATER_IN_TAKE_GRAPH,date);

        barChart=(BarChart)view.findViewById(R.id.barChart);
        tvYAxis=(TextView)view.findViewById(R.id.tvYAxis);
        tvXAxis=(TextView)view.findViewById(R.id.tvXAxis);
        tvdate=(TextView)view.findViewById(R.id.tvdate);
        ImageView ivLeftArrow=(ImageView)view.findViewById(R.id.ivLeftArrow);
        ImageView ivRightArrow=(ImageView)view.findViewById(R.id.ivRightArrow);

        if(dataSelection==1)
        {
            tvXAxis.setText("Week->");
            tvYAxis.setText("WaterIntake(ml)->");
            tempCal=CalendarUtil.stringToCalender(SelectedDate);
            startCalender=CalendarUtil.stringToCalender(SelectedDate);
            startCalender.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),1,00,00,00);
            getStartAndEndTimes();
            setTimes(startCalender, endCalender);
            getDataforgraph();
        }
        ivLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                isDecrement = true;
                generateData( dataSelection ,isDecrement);
            }
        });
        ivRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                isDecrement = false;
                generateData(dataSelection,isDecrement);
            }
        });
        return view;
    }

    private void getStartAndEndTimes() {

        Date start=startCalender.getTime();
        int numOfDaysInMonth = startCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        startCalender.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
        Date end=startCalender.getTime();

        startCalender=CalendarUtil.toCalendar(start);
        endCalender=CalendarUtil.toCalendar(end);
    }

    private void plotGraph(ArrayList<WaterDO> arrgraph)
    {
        final XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);//remove xAxis lable names
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(getResources().getColor(R.color.gray1));
//        rightAxis.setAxisLineWidth(2);
        barChart.setPinchZoom(false);
        barChart.zoom(1.0f, 0, 0, 0);
        barChart.setTouchEnabled(false);
        barChart.enableScroll();
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setVerticalScrollBarEnabled(true);
        barChart.setBackgroundColor(getResources().getColor(R.color.white));
        barChart.setDescription("");
        barChart.setDrawGridBackground(false); // set the bar graph color along with its background
        barChart.getLegend().setEnabled(false);//to remove legends
        barChart.getAxisLeft().setTextColor(getResources().getColor(R.color.gray1));//to set the YAxis Lable colour
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        final ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < arrgraph.size(); i++) {
            yVals1.add(new BarEntry(Float.parseFloat(arrgraph. get(i).strWater.toString()), i));
            if (dataSelection==0) {
                xVals.add(getCalendarFromString(arrgraph.get(i).date.toString()));
            }else if (dataSelection==1){
                xVals.add("week"+i);
            }else if (dataSelection==2){
                xVals.add(" ");
            }
        }
        BarDataSet set1;
        set1 = new BarDataSet(yVals1, "planned");
        set1.setDrawValues(true);
        set1.setValueTextColor(getResources().getColor(R.color.gray1));
        set1.setHighLightAlpha(220);
        set1.setBarSpacePercent(10f);
        set1.setColor(getResources().getColor(R.color.light_drak));
        barChart.getAxisRight().setEnabled(false);

        barChart.getXAxis().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);
        barChart.getAxisLeft().setAxisMinValue(0);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        barChart.animateY(500);
        BarData data = new BarData(xVals,dataSets);
//        data.setValueFormatter(new LargeValueFormatter());
        barChart.setData(data);
        barChart.invalidate();// refresh after setting data
    }

    private void generateData(int dataSelection,boolean isDecrement)
    {
        if(dataSelection ==1) //tabsType.Month
        {
            if(isDecrement) {
                setCalenderForDecrement();
            } else {
                setCalenderForIncrement();
            }
            setTimes(startCalender,endCalender);
            getDataforgraph();


            if (WaterIntakeHistory.viewPagerAdapter!=null)
                WaterIntakeHistory.viewPagerAdapter.Refresh();
        }
    }


    private void getDataforgraph() {
        arrGraph.clear();
        startDate = CalendarUtil.getDateAndTime(startCalender);
        endDate = CalendarUtil.getDateAndTime(endCalender);
        tvdate.setText(getDateWith3letters(startCalender) + "-" + getDateWith3letters(endCalender));
        arrGraph= ((WaterIntakeHistory)context).uSerDataDA.getWaterBetweenDatesNew(startDate,endDate,dataSelection);
        plotGraph(arrGraph);
        setDateForPref();
        preference.saveStringInPreference(Preference.DATE_WATER_IN_TAKE_GRAPH,tempDate);
    }
    private void setDateForPref() {
        tempCal.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH),00,00,00);
        tempDate=CalendarUtil.getDateAndTime(tempCal);
    }

    private void setCalenderForIncrement() {
        startCalender= (Calendar) endCalender.clone();
        endCalender.add(Calendar.MONTH, 1);
        startCalender.set(endCalender.get(Calendar.YEAR),endCalender.get(Calendar.MONTH),1,00,00,00);
        endCalender.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),numberOfDaysInMonth(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH)),23,59,59);
    }

    private void setCalenderForDecrement() {
        endCalender= (Calendar) startCalender.clone();
        startCalender.add(Calendar.MONTH, -1);
        startCalender.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),1,00,00,00);
        endCalender.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),numberOfDaysInMonth(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH)),23,59,59);
    }

    private void setTimes(Calendar startCalender, Calendar endCalender)
    {
        startCalender.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),startCalender.get(Calendar.DAY_OF_MONTH),00,00,00);
        endCalender.set(endCalender.get(Calendar.YEAR),endCalender.get(Calendar.MONTH),endCalender.get(Calendar.DAY_OF_MONTH),23,59,59);
    }

    public int numberOfDaysInMonth(int year, int month) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    private String getCalendarFromString(String s)
    {
        String date = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN, Locale.ENGLISH);
        try
        {
            cal.setTime(sdf.parse(s));// all done
            date=getDateWith3letters(cal);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return s;
        }
        return  date;
    }

}
