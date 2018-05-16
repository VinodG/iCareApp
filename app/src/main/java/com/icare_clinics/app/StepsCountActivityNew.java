package com.icare_clinics.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.StepCountDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getTimeInstance;

/**
 * Created by srikrishna.nunna on 05-04-2017.
 */

public class StepsCountActivityNew extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private LinearLayout llMain;
    private BarChart barChart;
    private TextView tvDaySelect,tvDayUnselect,tvWeekSelect,tvWeekUnselect,tvMonthSelect,tvMonthUnselect,tvYearSelect,tvYearUnselect;
    private TextView tvStepsCount,tvDailyAvg,tvHighest,tvLowest,tvAverage;
    private static final String AUTH_PENDING = "auth_state_pending";
    private static boolean authInProgress = false;
    public static final String TAG = "BasicHistoryApi";
    private ArrayList<String>arrDay=new ArrayList<>();
    public static GoogleApiClient mClient  = null;
    private TextView tvPerHour,tvSteps;
    private static int flag=0;
    private int stepsSum=0,dailyAvg=0;
    private static int noOfDays=0;;
    private Preference preference;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    private int hr;

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
        tvTitle.setText(R.string.steps_count);
        iv_fab.setVisibility(View.GONE);
        setStatusBarColor();
        initialiseControls();
        preference=new Preference(StepsCountActivityNew.this);

        mClient  = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .enableAutoManage(this, 0, this)
                .build();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("DAILY_STEPS",stepsSum);
                setResult(2,intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("DAILY_STEPS",stepsSum);
        setResult(2,intent);
        super.onBackPressed();
    }

    @Override
    public void loadData() {
    }

    private void showBarGraphForDay(ArrayList<String>arrgraph) {
        try {

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);//remove xAxis lable names
            xAxis.setTextColor(Color.WHITE);

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
            barChart.getXAxis().setTextSize(getResources().getDimension(R.dimen.graph_xaxis_size));
//            barChart.setViewPortOffsets(-40f, 0f, 0f, 0f);
//            barChart.setExtraLeftOffset(15f);
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            ArrayList<String> xVals = new ArrayList<String>();

            hr=0;
            for (int i = 0; i < arrgraph.size(); i++) {
                yVals1.add(new BarEntry(Float.parseFloat(arrgraph. get(i).toString()), i));
                if (flag==1) {

                    if (i==0){
                        hr =hr+3;
                        String str=i+"-"+(hr)+"hrs";
                        xVals.add(str);
                    }else {
                        String str = hr + "-" + (hr + 3)+'\n'+"hrs";
                        xVals.add(str);
                        hr=hr+3;
                    }

                }else if (flag==2){

                    String date=CalendarUtil.firstDayOfWeek();
                    String[]str=date.split(" ");
                    String month=getMonth(Integer.parseInt(str[1]));
                    String firstthree=month.substring(0,3);
                    int day=Integer.parseInt(str[0]);
                    day+=i;
                    xVals.add(day+" "+firstthree);
                }

                else if (flag==3){

                    xVals.add("week"+i);

                }else if (flag==4){

                    String month=getMonth(i);
                    String firstthree=month.substring(0,3);
                    xVals.add(firstthree);
                }
            }

            BarDataSet set1;

            set1 = new BarDataSet(yVals1, "planned");
            set1.setDrawValues(true);
            set1.setValueTextColor(getResources().getColor(R.color.green_dark));
            set1.setHighLightAlpha(220);
            set1.setBarSpacePercent(10f);
            set1.setColor(Color.WHITE);

            barChart.getAxisRight().setEnabled(false);
            barChart.getXAxis().setAxisMinValue(0);
            barChart.getAxisRight().setAxisMinValue(0);
            barChart.getAxisLeft().setAxisMinValue(0);

//            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            barChart.animateY(500);
            BarData data = new BarData(xVals,dataSets);
//            data.setValueFormatter(new LargeValueFormatter());
            barChart.setData(data);
            barChart.invalidate();// refresh after setting data

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        tvPerHour         =(TextView)llMain.findViewById(R.id.tvPerHour);
        tvSteps           =(TextView)llMain.findViewById(R.id.tvSteps);

        tvDayUnselect.setOnClickListener(this);
        tvWeekUnselect.setOnClickListener(this);
        tvMonthUnselect.setOnClickListener(this);
        tvYearUnselect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvDayUnselect:

                arrDay.clear();
                tvDayUnselect.setVisibility(View.GONE);
                tvDaySelect.setVisibility(View.VISIBLE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);

                tvPerHour.setText("Per 3 hrs ->");
                tvSteps.setText("Steps ->");
                flag=1;
                noOfDays=1;
                new InsertAndVerifyDataTask().execute();

                break;
            case R.id.tvWeekUnselect:

                arrDay.clear();
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.GONE);
                tvWeekSelect.setVisibility(View.VISIBLE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);

                tvPerHour.setText("Per day ->");
                tvSteps.setText("Steps ->");
                flag=2;
                noOfDays=7;
                new InsertAndVerifyDataTask().execute();

                break;
            case R.id.tvMonthUnselect:
                arrDay.clear();
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.GONE);
                tvMonthSelect.setVisibility(View.VISIBLE);
                tvYearUnselect.setVisibility(View.VISIBLE);
                tvYearSelect.setVisibility(View.GONE);

                tvPerHour.setText("Per week ->");
                tvSteps.setText("Steps ->");
                flag=3;
                noOfDays=numberOfDaysInMonth(CalendarUtil.getMonthInt(),year);
                new InsertAndVerifyDataTask().execute();
                break;
            case R.id.tvYearUnselect:
                arrDay.clear();
                tvDayUnselect.setVisibility(View.VISIBLE);
                tvDaySelect.setVisibility(View.GONE);
                tvWeekUnselect.setVisibility(View.VISIBLE);
                tvWeekSelect.setVisibility(View.GONE);
                tvMonthUnselect.setVisibility(View.VISIBLE);
                tvMonthSelect.setVisibility(View.GONE);
                tvYearUnselect.setVisibility(View.GONE);
                tvYearSelect.setVisibility(View.VISIBLE);

                tvPerHour.setText("Per month ->");
                tvSteps.setText("Steps ->");
                flag=4;
                isLeapyear(year);
                showLoader("Loading..");
                new InsertAndVerifyDataTask().execute();
                break;
        }
    }

    private void isLeapyear(int year) {
        if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))){
            noOfDays=366;
        }else {
            noOfDays=365;
        }
    }

    private int calculateDailyAvg(int stepsSum, int noOfDays) {
        dailyAvg=0;
        if (stepsSum>0 && noOfDays!=0) {
            dailyAvg = stepsSum / noOfDays;
        }
        return dailyAvg;
    }

    private int calculateSumOfSteps(ArrayList<String> arrDay) {
        stepsSum=0;
        for (int i=0;i<arrDay.size();i++){
            int steps=Integer.parseInt(arrDay.get(i));
            stepsSum+=steps;
        }
        return stepsSum;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("HistoryAPI", "onConnected");

        arrDay.clear();
        tvDayUnselect.setVisibility(View.GONE);
        tvDaySelect.setVisibility(View.VISIBLE);
        tvWeekUnselect.setVisibility(View.VISIBLE);
        tvWeekSelect.setVisibility(View.GONE);
        tvMonthUnselect.setVisibility(View.VISIBLE);
        tvMonthSelect.setVisibility(View.GONE);
        tvYearUnselect.setVisibility(View.VISIBLE);
        tvYearSelect.setVisibility(View.GONE);

        tvPerHour.setText("Per 3 hrs ->");
        tvSteps.setText("Steps ->");
        flag=1;
        noOfDays=1;
        showLoader("Loading..");
        new InsertAndVerifyDataTask().execute();
    }

    private class InsertAndVerifyDataTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            DataSet dataSet = insertFitnessData();

            Log.d(TAG, "Inserting the dataset in the History API.");
            com.google.android.gms.common.api.Status insertStatus =
                    Fitness.HistoryApi.insertData(mClient, dataSet)
                            .await(1, TimeUnit.MINUTES);

            if (!insertStatus.isSuccess()) {
                Log.d(TAG, "There was a problem inserting the dataset.");
                return null;
            }
            Log.d(TAG, "Data insert was successful!");
            if (flag==4){
                Vector<DataReadRequest> vecReadRequest = queryFitnessDataYear();
                Vector<DataReadResult> vecReadResult = new Vector<DataReadResult>();
                if(arrDay!=null && arrDay.size() >0)
                {
                    arrDay.clear();
                }
                for (int i=0;i<12;i++)
                {
                    DataReadResult dataReadResult =
                            Fitness.HistoryApi.readData(mClient, vecReadRequest.get(i)).await(1, TimeUnit.MINUTES);
                    vecReadResult.add(dataReadResult);
                    Log.e("DATA_READ_RESULT : ",dataReadResult.toString());
                    String steps="0";
                    try
                    {
                        steps =""+dataReadResult.getBuckets().get(0).getDataSets().get(0).getDataPoints().get(0).getValue(dataReadResult.getBuckets().get(0).getDataSets().get(0).getDataPoints().get(0).getDataType().getFields().get(0));
                    }catch (Exception e)
                    {
                        steps="0";
                    }finally
                    {
                        arrDay.add(steps);
                    }
                }
            }else
            {
                DataReadRequest readRequest = queryFitnessData();
                DataReadResult dataReadResult =
                        Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);
                printData(dataReadResult);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hideLoader();
            if(arrDay.size()>0) {
                showBarGraphForDay(arrDay);
                calMaxMin(arrDay);

                if (arrDay!=null && arrDay.size()>0) {
                    stepsSum=calculateSumOfSteps(arrDay);
                    dailyAvg=calculateDailyAvg(stepsSum,noOfDays);
                    tvStepsCount.setText(""+stepsSum);
                    tvDailyAvg.setText(""+dailyAvg);
                }else {
                    tvStepsCount.setText("0");
                    tvDailyAvg.setText("0");
                }
                if (flag==1){
                    preference.saveIntInPreference(Preference.STEPS_COUNT,stepsSum);
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"NUll",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private DataSet insertFitnessData() {
        Log.d(TAG, "Creating a new data insert request.");

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();

        // Create a data source
        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(this)
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setStreamName(TAG + " - step count")
                .setType(DataSource.TYPE_RAW)
                .build();
        // Create a data set
        int stepCountDelta = 950;
        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint = dataSet.createDataPoint()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_STEPS).setInt(stepCountDelta);
        dataSet.add(dataPoint);

        return dataSet;
    }
    public   void printData(DataReadResult dataReadResult) {
        if (dataReadResult.getBuckets().size() > 0) {
            Log.d(TAG, "Number of returned buckets of DataSets is: "
                    + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    dumpDataSet(dataSet);
                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.d(TAG, "Number of returned DataSets is: "
                    + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet);
            }
        }
    }

    private   void dumpDataSet(DataSet dataSet) {
//        DateFormat dateFormat = getTimeInstance();

        if ((dataSet.getDataPoints()).size()==0){
            arrDay.add("0");
        }
        for (DataPoint dp : dataSet.getDataPoints()) {
            for(Field field : dp.getDataType().getFields()) {
                arrDay.add(String.valueOf(dp.getValue(field)));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String str=dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS));

                Log.d(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field) +
                        "StartTime" + dp.getTimestamp(TimeUnit.HOURS)+
                        "EndTime" + dp.getEndTime(TimeUnit.DAYS));
            }
        }
    }

    public  DataReadRequest queryFitnessData() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);

        long startTime=0;
        long endTime=0;
        if (flag==1) {
//            c.add(Calendar.DAY_OF_MONTH, -1);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
            startTime = (c).getTimeInMillis();
            endTime =(c).getTimeInMillis()+ (24 * 60 * 60 * 1000);
        }else if (flag==2){
            c.set(Calendar.DAY_OF_WEEK, 1);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
            startTime = (c).getTimeInMillis();
            endTime =(c).getTimeInMillis()+ (24 * 60 * 60 * 1000 * 7);
        } else if (flag==3){
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
            startTime = (c).getTimeInMillis();
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),numberOfDaysInMonth(Calendar.MONTH+1,year),23,59,59) ;
            endTime =c.getTimeInMillis();
        } else if (flag==4){
            c.set(Calendar.DAY_OF_YEAR, 1);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
            startTime = (c).getTimeInMillis();
            endTime = Calendar.getInstance().getTimeInMillis();
        }

        DateFormat dateFormat = getDateInstance();
        Log.d(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.d(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest=null;
        if (flag==1) {
            readRequest = new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(3, TimeUnit.HOURS)
                    .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                    .build();
        }else if (flag==2){
            readRequest = new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                    .build();
        } else if (flag==3){
            readRequest = new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(7, TimeUnit.DAYS)
                    .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                    .build();
        } else if (flag==4){

            for (int i=0;i<12;i++) {
//                String strmonth=getMonth(i);
                c.set(Calendar.MONTH,i);
                c.set(Calendar.YEAR,year);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
                int noofDays=numberOfDaysInMonth(i,year);
                c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),noofDays,23,59,59);

                readRequest = new DataReadRequest.Builder()
                        .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                        .bucketByTime(noofDays, TimeUnit.DAYS)
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build();
            }
        }
        return readRequest;
    }
    public Vector<DataReadRequest> queryFitnessDataYear() {
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
        long startTime = (c).getTimeInMillis();
        long endTime = Calendar.getInstance().getTimeInMillis();

        DateFormat dateFormat = getDateInstance();
        Log.d(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.d(TAG, "Range End: " + dateFormat.format(endTime));

        Vector<DataReadRequest> vecReadRequest=new Vector<DataReadRequest>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i=0;i<12;i++)
        {
            DataReadRequest readRequest=null;
            c.set(Calendar.MONTH,i);
            c.set(Calendar.YEAR,year);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),c .get(Calendar.DAY_OF_MONTH),00,00,00);
            long startTimeMilli = (c).getTimeInMillis();
            int noofDays=numberOfDaysInMonth(i,year);
            c.set(c .get(Calendar.YEAR),c .get(Calendar.MONTH),noofDays,23,59,59);
            long endTimeMilli = c.getTimeInMillis();
            readRequest = new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(noofDays, TimeUnit.DAYS)
                    .setTimeRange(startTimeMilli, endTimeMilli, TimeUnit.MILLISECONDS)
                    .build();
            vecReadRequest.add(readRequest);
        }
        return vecReadRequest;
    }

    public  int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    private void calMaxMin(ArrayList<String>arrMaxMin) {

        int size=arrMaxMin.size();
        int max=Integer.parseInt(arrMaxMin.get(0));
        int min=Integer.parseInt(arrMaxMin.get(0));
        int sum=0,avg=0;
        for (int i=0;i<size;i++){
            int temp=Integer.parseInt(arrMaxMin.get(i));
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
    @Override
    public void onConnectionSuspended(int i) {
        Log.e("HistoryAPI", "onConnectionSuspended");
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("HistoryAPI", "onConnectionFailed");
    }
}
