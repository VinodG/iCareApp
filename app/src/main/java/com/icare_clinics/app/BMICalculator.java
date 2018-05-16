package com.icare_clinics.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

import static com.icare_clinics.app.R.id.llTimings;
import static com.icare_clinics.app.R.id.rlhumanbody;
import static com.icare_clinics.app.R.id.textView;

/**
 * Created by srikrishna.nunna on 20-03-2017.
 */

public class BMICalculator extends BaseActivity {
    private LinearLayout llMain;
    private TextView tvGuestMode,tvResult,tvSeekBar;
    private EditText etHeight,etWeight;
    private ImageView iv_cmSelect,iv_cmUnselect,iv_inchUnselect,iv_inchSelect,iv_kgSelect,iv_kgUnselect,iv_lbsUnselect,iv_lbsSelect;
    //    private ImageView iv_femaleUnselect,iv_femaleSelect,iv_maleUnselect,iv_maleSelect;
    private ImageView ivCircle,ivOn,ivOff,ivInfo;
    private TextView btnCalculate;
    Boolean guestenable ,isGuest=true;
    private String weight;
    private String height;
    private int decimalHeight,decimalWeight;
    public UserDo userDo;
    private USerDataDA mUserDa;
    private WeightDO weightDO=new WeightDO();
    static int id=1;
    private SeekBar seekBarBmi;
    String bmiLabel = "",yearDate;
    float bmi,aFloatbmi;
    float inchCm,ldsPound;

    int birthYear,currentYear;
    private String strBmiValue="";

    boolean isCm=true,isKg=true,isLds=false,isInch=false;
    private Preference preference;
    private int seekbar_width;
    private int flag=0;
    @Override
    public void initialise() {

        llMain=(LinearLayout) inflater.inflate(R.layout.bmi_calculator,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        llBody.addView(llMain,param);
        ivMenu.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);
        tvTitle.setText(R.string.bmi_calculator);

        mUserDa=new USerDataDA(BMICalculator.this);
        setStatusBarColor();
        initialiseControls();
        preference=new Preference(BMICalculator.this);

//        seekbar_width=preference.getIntFromPreference(Preference.BMI_WIDTH,0);

//        String strBmi=preference.getStringFromPreference(Preference.BMI,"");

       /* if (!strBmi.equalsIgnoreCase("")) {
            aFloatbmi = Float.parseFloat(strBmi);
            if (aFloatbmi > 0) {
                bmi = aFloatbmi;
                displayBMI(aFloatbmi);
            }
        }*/

        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BMICalculator.this,BMIInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initialiseControls() {
        tvGuestMode = (TextView) llMain.findViewById(R.id.tvGuestMode);
        tvResult = (TextView) llMain.findViewById(R.id.tvResult);
        etHeight = (EditText) llMain.findViewById(R.id.etHeight);
        etWeight = (EditText) llMain.findViewById(R.id.etWeight);
//        etAge = (EditText) llMain.findViewById(R.id.etAge);
        iv_cmSelect = (ImageView) llMain.findViewById(R.id.iv_cmSelect);
        iv_cmUnselect = (ImageView) llMain.findViewById(R.id.iv_cmUnselect);
        iv_inchUnselect = (ImageView) llMain.findViewById(R.id.iv_inchUnselect);
        iv_inchSelect = (ImageView) llMain.findViewById(R.id.iv_inchSelect);
        iv_kgSelect = (ImageView) llMain.findViewById(R.id.iv_kgSelect);
        iv_kgUnselect = (ImageView) llMain.findViewById(R.id.iv_kgUnselect);
        iv_lbsUnselect = (ImageView) llMain.findViewById(R.id.iv_lbsUnselect);
        iv_lbsSelect = (ImageView) llMain.findViewById(R.id.iv_lbsSelect);
//        iv_femaleUnselect = (ImageView) llMain.findViewById(R.id.iv_femaleUnselect);
//        iv_maleUnselect = (ImageView) llMain.findViewById(R.id.iv_maleUnselect);
        // ivCircle = (ImageView) llMain.findViewById(R.id.ivCircle);
        ivOn = (ImageView) llMain.findViewById(R.id.ivOn);
        ivOff = (ImageView) llMain.findViewById(R.id.ivOff);
        ivInfo=(ImageView)llMain.findViewById(R.id.ivInfo);
        guestenable = getIntent().getExtras().getBoolean("BMIDATASET");
        btnCalculate = (TextView) llMain.findViewById(R.id.btnCalculate);
        tvSeekBar=(TextView)llMain.findViewById(R.id.tvSeekBar);
        tvResult.setVisibility(View.INVISIBLE);
        seekBarBmi=(SeekBar) llMain.findViewById(R.id.seekBarBmi);
        seekBarBmi.setClickable(false);
        seekBarBmi.setEnabled(false);


        tvSeekBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        flag=1;
                        tvSeekBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        seekbar_width = seekBarBmi.getWidth();
                        getDataFromDatabase();
                        displayBMI(aFloatbmi);
//                        preference.saveIntInPreference(Preference.BMI_WIDTH,seekbar_width);
                    }
                });

        if (guestenable) {
            ivOn.setClickable(false);
            ivOn.setEnabled(false);
            ivOn.setFocusable(false);

            ivOn.setBackgroundResource(R.drawable.on);
            etHeight.setText("");
            etWeight.setText("");
//            etAge.setText("");
        } else {
            ivOn.setBackgroundResource(R.drawable.off);
            getDataFromDatabase();
            ivOn.setClickable(true);
            isGuest = true;
        }
        ivOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGuest){
                    ivOn.setBackgroundResource(R.drawable.off);
                    getDataFromDatabase();
                    isGuest=false;
                    tvResult.setVisibility(View.INVISIBLE);
                }else{
                    etHeight.setText("");
                    etWeight.setText("");
//                    etAge.setText("");
                    tvResult.setVisibility(View.INVISIBLE);
                    ivOn.setBackgroundResource(R.drawable.on);
                    isGuest=true;
                }
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date= CalendarUtil.getPresentDate();
                weight=etWeight.getText().toString();
                height=etHeight.getText().toString();
                if(weight.equalsIgnoreCase("")){
                    showCustomDialog(BMICalculator.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_weight), getResources().getString(R.string.ok), "", "");
                }else if(height.equalsIgnoreCase("")){
                    showCustomDialog(BMICalculator.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_height), getResources().getString(R.string.ok), "", "");
                }else{
                    if(isInch){
//                        decimalHeight = Integer.parseInt(height);
                        float f = Float.parseFloat(height);
                        inchCm = (float) (f*2.54);
//                        decimalHeight=(int)inchCm;
                        decimalHeight= (int) Math.ceil(inchCm);
                        height=String.valueOf(decimalHeight);
                    }
                    if (isLds)
                    {
                        decimalWeight=Integer.parseInt(weight);
                        ldsPound=(float)((float)decimalWeight/2.20462262185);
                        decimalWeight=(int)ldsPound;
                        weight=String.valueOf(decimalWeight);
                    }
                    if (height != null && !"".equals(height)
                            && weight != null  &&  !"".equals(weight)) {

                        calculateBMI(height,weight);
//                        displayBMI(bmi);
                    }
                    weightDO.id=id++ +"";
                    weightDO.weight=weight;
                    weightDO.date=date;
                    weightDO.bmi= String.valueOf(bmi);
                    mUserDa.insertWeight(weightDO);

                    userDo=new UserDo();
                    userDo.height=height;
                    userDo.weight=weight;
                    mUserDa.updateWeightAndHeight(userDo);

                    Intent intent=new Intent();
                    intent.putExtra("BMI",bmi);
                    setResult(1,intent);

//                    preference.saveStringInPreference(Preference.BMI,String.valueOf(bmi));
//                    preference.saveStringInPreference(Preference.HEIGHT,height);
//                    preference.saveStringInPreference(Preference.WEIGHT,weight);
                }
            }
        });
        if (isCm){
            height=etHeight.getText().toString();
            iv_inchUnselect.setBackgroundResource(R.drawable.inchunselect);
            iv_cmSelect.setBackgroundResource(R.drawable.cm_select);
        }
        iv_cmSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCm=true;
                isInch=false;
                iv_inchUnselect.setBackgroundResource(R.drawable.inchunselect);
                iv_cmSelect.setBackgroundResource(R.drawable.cm_select);
            }
        });
        iv_inchUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCm=false;
                isInch=true;
                iv_cmSelect.setBackgroundResource(R.drawable.cm_unselect);
                iv_inchUnselect.setBackgroundResource(R.drawable.inchselect);
            }
        });
        if (isKg){
            weight=etWeight.getText().toString();
            iv_kgSelect.setBackgroundResource(R.drawable.kgselect);
            iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_unselect);
        }
        iv_kgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isKg=true;
                isLds=false;
                iv_kgSelect.setBackgroundResource(R.drawable.kgselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_unselect);
            }
        });
        iv_lbsUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isKg=false;
                isLds=true;
                iv_kgSelect.setBackgroundResource(R.drawable.kgunselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_select);
            }
        });

        seekBarBmi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){

                int val = (progress * (seekbar_width - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                tvSeekBar.setText(bmiLabel+" " + new DecimalFormat("##.##").format(bmi) );
                 tvSeekBar.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    public void  getDataFromDatabase(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDo = mUserDa.getUserData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (userDo != null) {
                            etHeight.setText(userDo.height);
                            etWeight.setText(userDo.weight);
//                            yearDate=userDo.dob;
                            calculateBMI(userDo.height,userDo.weight);

                        }
                    }
                });
            }
        }).start();

    }

    private void calculateBMI(String height, String weight) {

        float heightValue = Float.parseFloat(height) / 100;
        float weightValue = Float.parseFloat(weight);
        bmi = weightValue / (heightValue * heightValue);
        displayBMI(bmi);
    }

    @Override
    public void loadData() {

    }
    private void displayBMI(float bmi) {

        if (Float.compare(bmi, 18.5f) < 0 ) {
            bmiLabel = getString(R.string.Underweight);
            tvSeekBar.setTextColor(getResources().getColor(R.color.blue_bmi));
        } else if (Float.compare(bmi, 24.9f) <0) {
            bmiLabel = getString(R.string.Healthy);
            tvSeekBar.setTextColor(getResources().getColor(R.color.green_bmi));
        } else if (Float.compare(bmi, 29.9f) < 0) {
            bmiLabel = getString(R.string.overweight);
            tvSeekBar.setTextColor(getResources().getColor(R.color.orange_bmi));
        } else {
            bmiLabel = getString(R.string.Obese);
            tvSeekBar.setTextColor(getResources().getColor(R.color.red_bmi));
        }

       /* if (flag==1){//for setting seekbar to 0
            seekBarBmi.setProgress(0);
        }*/
        int result1=(int)bmi;
        seekBarBmi.setProgress(result1);
        seekBarBmi.setClickable(false);
        tvResult.setVisibility(View.VISIBLE);
    }
}