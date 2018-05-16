package com.icare_clinics.app.fragments;

import android.annotation.SuppressLint;
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
import java.util.Locale;

import static com.icare_clinics.app.utilities.CalendarUtil.YYYY_MM_DD_FULL_PATTERN;
import static com.icare_clinics.app.utilities.CalendarUtil.getDateWith3letters;
import static com.icare_clinics.app.utilities.CalendarUtil.getYear;

/**
 * Created by Sreekanth.P on 22-06-2017.
 */

public class WaterIntakeWeeklyFragment extends Fragment {

    private Context context;
    private int dataSelection;
    private ArrayList<String>arrgraph;
    private BarChart barChart;
    private ArrayList<WaterDO>arrGraph = new ArrayList<WaterDO>();
    private TextView tvdate;
    private TextView tvXAxis,tvYAxis;
    private String type,startDate,endDate;
    private boolean isDecrement=false;
    Calendar startCalender;
    Calendar endCalender;
    View view=null;
    public Calendar startCal;
    Preference preference;
    private String date=CalendarUtil.getPresentDate();
    private String SelectedDate;

    @SuppressLint("ValidFragment")
    public WaterIntakeWeeklyFragment(Context context, int i,Calendar startCal) {
        this.context=context;
        this.dataSelection=i;
        this.startCal=startCal;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        preference=new Preference(context);
        View view = inflater.inflate(R.layout.weekly_fragment, container, false);

        SelectedDate =preference.getStringFromPreference(Preference.DATE_WATER_IN_TAKE_GRAPH,date);
        startCalender=CalendarUtil.stringToCalender(SelectedDate);

        barChart=(BarChart)view.findViewById(R.id.barChart);
        tvYAxis=(TextView)view.findViewById(R.id.tvYAxis);
        tvXAxis=(TextView)view.findViewById(R.id.tvXAxis);
        tvdate=(TextView)view.findViewById(R.id.tvdate);
        ImageView ivLeftArrow=(ImageView)view.findViewById(R.id.ivLeftArrow);
        ImageView ivRightArrow=(ImageView)view.findViewById(R.id.ivRightArrow);

        if (dataSelection==0)
        {
            tvXAxis.setText("Day->");
            tvYAxis.setText("WaterIntake(ml)->");
            if (!isDecrement) {
                setCalenderForDecrement();
                setTimes(startCalender, endCalender);
                getDataforgraph();
            }
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

    /*private void getStartAndEndTimes() {

        Date start=startCalender.getTime();
        int numOfDaysInMonth = startCalender.getActualMaximum(Calendar.DAY_OF_WEEK);
        startCalender.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
        Date end=startCalender.getTime();

        startCalender=CalendarUtil.toCalendar(start);
        endCalender=CalendarUtil.toCalendar(end);
    }
*/
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
    // pager calendar
    private void generateData(int dataSelection,boolean isDecrement)
    {
        if(dataSelection ==0) //tabsType.WEEK
        {
            if(isDecrement) {
                setCalenderForDecrement();
            } else {
                setCalenderForIncrement();
            }
            setTimes(startCalender,endCalender);
            getDataforgraph();
        }
    }

    private void setCalenderForIncrement() {
        endCalender.add(Calendar.DAY_OF_MONTH, 6);
        startCalender= (Calendar) endCalender.clone();
    }
    private void setCalenderForDecrement() {
        endCalender= (Calendar) startCalender.clone();
        startCalender.add(Calendar.DAY_OF_MONTH, -6);
    }

    private void getDataforgraph() {
        String startDate,endDate;
        arrGraph.clear();
        startDate = CalendarUtil.getDateAndTime(startCalender);
        endDate = CalendarUtil.getDateAndTime(endCalender);
        if (dataSelection==2){
            tvdate.setText(getYear(startCalender));
        }else {
            tvdate.setText(getDateWith3letters(startCalender) + "-" + getDateWith3letters(endCalender));
        }
        arrGraph= ((WaterIntakeHistory)context).uSerDataDA.getWaterBetweenDatesNew(startDate,endDate,dataSelection);
        plotGraph(arrGraph);

    }
    private void setTimes(Calendar startCal, Calendar endCal)
    {
        startCalender.set(startCal.get(Calendar.YEAR),startCal.get(Calendar.MONTH),startCal.get(Calendar.DAY_OF_MONTH),00,00,00);
        endCalender.set(endCal.get(Calendar.YEAR),endCal.get(Calendar.MONTH),endCal.get(Calendar.DAY_OF_MONTH),23,59,59);
    }
    /*  public String getMonth(int month) {
          return new DateFormatSymbols().getMonths()[month];
      }
      public int numberOfDaysInMonth(int year, int month) {
          Calendar monthStart = new GregorianCalendar(year, month, 1);
          return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
      }*/
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
