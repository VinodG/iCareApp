package com.icare_clinics.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.fragments.DatePickerFragment;
import com.icare_clinics.app.utilities.CalendarUtil;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Created by WINIT on 01-05-2017.
 */

public class ProfileSetupActivityNew extends BaseActivity {

    LinearLayout lLmain, llProfileName, llGender, llDob, llHeight, llWeight, llProfileInput, llGenderSelect, llDobEnter, llWeightEnter, llHeightEnter,llMale,llFemale;
    private TextView btnSetProfile,profileButtonNext,dobNext,genderNext,weightNext,HeightNext;
    private EditText etFirstName,etLastName, etProfileMob, etProfileidEmail, etMonth, etDate, etyear, etKg, etHeight;
    private TextView tvDob, tvgender, tvWeight, tvHeight, tvNameAndMobileNum, tvEmail, tvMale, tvFemale;
    private ImageView ivEditProfile, ivEditGender, ivEditDob, ivEditHeight, ivEditWeight, ivMale, ivFemale;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker datePicker;
    private Calendar calendar;
    public UserDo userDo;
    private USerDataDA mUserDa;
    private String from;
    private float bmi;

    public boolean ismale = false, isFemale = false;

    private String firstName,lastName,mobileNum,email,gender,dateOfBirth,height,weight,mm,dd,yy,strHeight,strWeight;

    boolean isCm=true,isKg=true,isLds=false,isInch=false;
    private ImageView iv_kgSelect,iv_lbsUnselect,iv_cmSelect,iv_inchUnselect;

    private int decimalHeight,decimalWeight;
    float inchCm,ldsPound;
    private WeightDO weightDO;

    @Override
    public void initialise() {

        lLmain = (LinearLayout) inflater.inflate(R.layout.activity_profile_setup_new, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLmain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText(R.string.profile_setup);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);
        mUserDa=new USerDataDA(ProfileSetupActivityNew.this);

        firstName = preference.getStringFromPreference(Preference.NAME,"");
        lastName = preference.getStringFromPreference(Preference.LASTNAME,"");
        mobileNum=preference.getStringFromPreference(Preference.MOBILE_NUM,"");
        email=preference.getStringFromPreference(Preference.EMAIL_ID,"");
        gender=preference.getStringFromPreference(Preference.GENDER,"");
        dateOfBirth=preference.getStringFromPreference(Preference.DOB,"");
        height=preference.getStringFromPreference(Preference.HEIGHT,"");
        weight=preference.getStringFromPreference(Preference.WEIGHT,"");
        strHeight=preference.getStringFromPreference(preference.HEIGHT_MEASUREMENT, "");
        strWeight=preference.getStringFromPreference(preference.WEIGHT_MEASUREMENT, "");

        setStatusBarColor();
        initialiseControls();

        MyTextWatcher tw = new MyTextWatcher();
        tw.setView(etFirstName);
        etFirstName.addTextChangedListener(tw);

        MyTextWatcher tw2 = new MyTextWatcher();
        tw2.setView(etLastName);
        etLastName.addTextChangedListener(tw2);

    }

    @Override
    public void initialiseControls() {

        llProfileName = (LinearLayout) lLmain.findViewById(R.id.llProfileName);
        llGender = (LinearLayout) lLmain.findViewById(R.id.llGender);
        llDob = (LinearLayout) lLmain.findViewById(R.id.llDob);
        llHeight = (LinearLayout) lLmain.findViewById(R.id.llHeight);
        llWeight = (LinearLayout) lLmain.findViewById(R.id.llWeight);
        llMale = (LinearLayout) lLmain.findViewById(R.id.llMale);
        llFemale = (LinearLayout) lLmain.findViewById(R.id.llFemale);

        llProfileInput = (LinearLayout) lLmain.findViewById(R.id.llProfileInput);
        llGenderSelect = (LinearLayout) lLmain.findViewById(R.id.llGenderSelect);
        llDobEnter = (LinearLayout) lLmain.findViewById(R.id.llDobEnter);
        llWeightEnter = (LinearLayout) lLmain.findViewById(R.id.llWeightEnter);
        llHeightEnter = (LinearLayout) lLmain.findViewById(R.id.llHeightEnter);

        btnSetProfile = (TextView) lLmain.findViewById(R.id.btnSetProfile);
        weightNext = (TextView) lLmain.findViewById(R.id.weightNext);
        HeightNext = (TextView) lLmain.findViewById(R.id.HeightNext);
        dobNext = (TextView) lLmain.findViewById(R.id.dobNext);
        genderNext = (TextView) lLmain.findViewById(R.id.genderNext);
        profileButtonNext = (TextView) lLmain.findViewById(R.id.profileButtonNext);

        etFirstName = (EditText) lLmain.findViewById(R.id.etFirstName);
        etLastName = (EditText) lLmain.findViewById(R.id.etLastName);
        etProfileMob = (EditText) lLmain.findViewById(R.id.etProfileMob);
        etProfileidEmail = (EditText) lLmain.findViewById(R.id.etProfileidEmail);
        etMonth = (EditText) lLmain.findViewById(R.id.etMonth);
        etDate = (EditText) lLmain.findViewById(R.id.etDate);
        etyear = (EditText) lLmain.findViewById(R.id.etyear);
        etKg = (EditText) lLmain.findViewById(R.id.etKg);
        etHeight = (EditText) lLmain.findViewById(R.id.etHeight);

        tvDob = (TextView) lLmain.findViewById(R.id.tvDob);
        tvgender = (TextView) lLmain.findViewById(R.id.tvgender);
        tvWeight = (TextView) lLmain.findViewById(R.id.tvWeight);
        tvHeight = (TextView) lLmain.findViewById(R.id.tvHeight);
        tvNameAndMobileNum = (TextView) lLmain.findViewById(R.id.tvNameAndMobileNum);
        tvEmail = (TextView) lLmain.findViewById(R.id.tvEmail);
        tvMale = (TextView) lLmain.findViewById(R.id.tvMale);
        tvFemale = (TextView) lLmain.findViewById(R.id.tvFemale);

        ivEditProfile = (ImageView) lLmain.findViewById(R.id.ivEditProfile);
        ivEditGender = (ImageView) lLmain.findViewById(R.id.ivEditGender);
        ivEditDob = (ImageView) lLmain.findViewById(R.id.ivEditDob);
        ivEditHeight = (ImageView) lLmain.findViewById(R.id.ivEditHeight);
        ivEditWeight = (ImageView) lLmain.findViewById(R.id.ivEditWeight);
        ivMale = (ImageView) lLmain.findViewById(R.id.ivMale);
        ivFemale = (ImageView) lLmain.findViewById(R.id.ivFemale);

        iv_kgSelect = (ImageView) lLmain.findViewById(R.id.iv_kgSelect);
        iv_lbsUnselect = (ImageView) lLmain.findViewById(R.id.iv_lbsUnselect);
        iv_cmSelect = (ImageView) lLmain.findViewById(R.id.iv_cmSelect);
        iv_inchUnselect = (ImageView) lLmain.findViewById(R.id.iv_inchUnselect);


        Bundle extras=getIntent().getExtras();
        if (extras !=null) {
            from = extras.getString("from");
            if(from!=null) {
                if (from.equalsIgnoreCase("Height")) {
                    llHeightEnter.setVisibility(View.VISIBLE);
                }
                if (from.equalsIgnoreCase("Weight")) {
                    llWeightEnter.setVisibility(View.VISIBLE);
                }
                if (from.equalsIgnoreCase("HealthTrackerIntro")){
                    llProfileInput.setVisibility(View.VISIBLE);
                }
            }
        }
        if (getIntent().hasExtra("HealthTracker")){
            ivEditProfile.setVisibility(View.VISIBLE);
            ivEditGender.setVisibility(View.VISIBLE);
            ivEditDob.setVisibility(View.VISIBLE);
            ivEditHeight.setVisibility(View.VISIBLE);
            ivEditWeight.setVisibility(View.VISIBLE);
        }

        loadData();

        mm = preference.getStringFromPreference(Preference.MM, "");
        dd = preference.getStringFromPreference(Preference.DD, "");
        yy = preference.getStringFromPreference(Preference.YY, "");
        etDate.setText(dd);
        etMonth.setText(mm);
        etyear.setText(yy);

        String gen = tvgender.getText().toString();
        if (gen.equalsIgnoreCase("Male"))
        {
            ivMale.setBackgroundResource(R.drawable.male_s);
            tvMale.setTextColor(getResources().getColor(R.color.dark_blue2));
            ivFemale.setBackgroundResource(R.drawable.female_us);
            tvFemale.setTextColor(getResources().getColor(R.color.hintcolor));
            tvgender.setText("Male");
            gender=tvgender.getText().toString();
        }else if(gen.equalsIgnoreCase("Female")){
            ivMale.setBackgroundResource(R.drawable.male_us);
            tvMale.setTextColor(getResources().getColor(R.color.hintcolor));
            ivFemale.setBackgroundResource(R.drawable.female_s);
            tvFemale.setTextColor(getResources().getColor(R.color.dark_blue2));
            tvgender.setText("Female");
            gender=tvgender.getText().toString();
        }
        llProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llProfileInput.setVisibility(view.VISIBLE);
                llGenderSelect.setVisibility(view.GONE);
                llDobEnter.setVisibility(view.GONE);
                llHeightEnter.setVisibility(view.GONE);
                llWeightEnter.setVisibility(view.GONE);
            }
        });
        llGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llProfileInput.setVisibility(view.GONE);
                llGenderSelect.setVisibility(view.VISIBLE);
                llDobEnter.setVisibility(view.GONE);
                llHeightEnter.setVisibility(view.GONE);
                llWeightEnter.setVisibility(view.GONE);
            }
        });
        llDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llProfileInput.setVisibility(view.GONE);
                llGenderSelect.setVisibility(view.GONE);
                llDobEnter.setVisibility(view.VISIBLE);
                llHeightEnter.setVisibility(view.GONE);
                llWeightEnter.setVisibility(view.GONE);
            }
        });
        llHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llProfileInput.setVisibility(view.GONE);
                llGenderSelect.setVisibility(view.GONE);
                llDobEnter.setVisibility(view.GONE);
                llHeightEnter.setVisibility(view.VISIBLE);
                llWeightEnter.setVisibility(view.GONE);

            }
        });
        llWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llProfileInput.setVisibility(view.GONE);
                llGenderSelect.setVisibility(view.GONE);
                llDobEnter.setVisibility(view.GONE);
                llHeightEnter.setVisibility(view.GONE);
                llWeightEnter.setVisibility(view.VISIBLE);

            }
        });
        profileButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(lLmain);
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                mobileNum = etProfileMob.getText().toString();
                email = etProfileidEmail.getText().toString();

                if (firstName.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_fst), getResources().getString(R.string.ok), "", "");
                }else if (lastName.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_lst), getResources().getString(R.string.ok), "", "");
                }
                else if (mobileNum.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_mob), getResources().getString(R.string.ok), "", "");
                } else if (email.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_email), getResources().getString(R.string.ok), "", "");
                } else if(!isEmailValid(email)){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_valid_email), getResources().getString(R.string.ok), "", "");
                } else {
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.VISIBLE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.GONE);
                    String nameAndMobileNumber = firstName + " "+lastName+", " + mobileNum;
                    tvNameAndMobileNum.setVisibility(view.VISIBLE);
                    tvEmail.setVisibility(view.VISIBLE);
                    tvNameAndMobileNum.setText(nameAndMobileNumber);
                    tvEmail.setText(email);
                    ivEditProfile.setVisibility(View.VISIBLE);
                }
            }
        });
        genderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvgender.getText().toString().equalsIgnoreCase("")){
                    ismale=true;
                    tvgender.setText("Male");
                    gender=tvgender.getText().toString();
                }

                ivEditGender.setVisibility(View.VISIBLE);
                llProfileInput.setVisibility(view.GONE);
                llGenderSelect.setVisibility(view.GONE);
                llDobEnter.setVisibility(view.VISIBLE);
                llHeightEnter.setVisibility(view.GONE);
                llWeightEnter.setVisibility(view.GONE);
            }
        });
        dobNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mm=etMonth.getText().toString();
                dd=etDate.getText().toString();
                yy=etyear.getText().toString();
                if(mm.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_month), getResources().getString(R.string.ok), "", "");

                }else if(dd.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_dat), getResources().getString(R.string.ok), "", "");
                }else if(yy.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_year), getResources().getString(R.string.ok), "", "");

                }else {
                    tvDob.setText(mm + " " + dd + " " + yy);
                    dateOfBirth=tvDob.getText().toString();
                    ivEditDob.setVisibility(View.VISIBLE);
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.GONE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.VISIBLE);
                    llWeightEnter.setVisibility(view.GONE);
                }
            }
        });

        iv_cmSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCm=true;
                isInch=false;
                iv_inchUnselect.setBackgroundResource(R.drawable.inchunselect);
                iv_cmSelect.setBackgroundResource(R.drawable.cm_select);
                strHeight="cm";
            }
        });
        iv_inchUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCm=false;
                isInch=true;
                iv_cmSelect.setBackgroundResource(R.drawable.cm_unselect);
                iv_inchUnselect.setBackgroundResource(R.drawable.inchselect);
                strHeight="cm";
//                strHeight="inch";
            }
        });

        iv_kgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isKg=true;
                isLds=false;
                iv_kgSelect.setBackgroundResource(R.drawable.kgselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_unselect);
                strWeight="kg";
            }
        });
        iv_lbsUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isKg=false;
                isLds=true;
                iv_kgSelect.setBackgroundResource(R.drawable.kgunselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_select);
                strWeight="kg";
//                strWeight="lds";
            }
        });

        HeightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(lLmain);
                height=etHeight.getText().toString();
                if(height.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_height), getResources().getString(R.string.ok), "", "");

                }else {
                    ivEditHeight.setVisibility(View.VISIBLE);
                    if (strHeight.equalsIgnoreCase("")) {
                        isCm=true;
                        strHeight="cm";
                        tvHeight.setText(height + "cm");
                    } else{
                        if (isCm){
                            tvHeight.setText(height+"cm");
                        }else {
                             float f = Float.parseFloat(height);
                            inchCm = (float) ((float) f*2.54);
//                            decimalHeight=(int)inchCm;
                            decimalHeight= (int) Math.ceil(inchCm);
                            height=String.valueOf(decimalHeight);

                            tvHeight.setText(height + "cm");
                        }
                    }
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.GONE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.VISIBLE);
                }
            }
        });

       /* llDobEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });*/
        etMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        etyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        weightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(lLmain);
                weight=etKg.getText().toString();
                if(weight.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_weight), getResources().getString(R.string.ok), "", "");
                }else {

                    /*DecimalFormat format = new DecimalFormat("##.##");
                    String formatted = format.format(22.123);
                    editText.setText(formatted);*/
                    ivEditWeight.setVisibility(View.VISIBLE);
                    if (strWeight.equalsIgnoreCase("")) {
                        strWeight="kg";
                        isKg=true;
                        tvWeight.setText(weight + "kg");
                    }else {
                        if (isKg){
                            tvWeight.setText(weight +"kg");
                        }else {
                            decimalWeight=Integer.parseInt(weight);
                            ldsPound=(float)((float)decimalWeight* 0.45359237 );
                            decimalWeight=(int)ldsPound;
                            weight=String.valueOf(decimalWeight);

                            tvWeight.setText(weight +"kg");
                        }
                    }
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.GONE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.GONE);
                }
            }
        });

        llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ismale = true;
                isFemale = false;
                if (ismale = true) {
                    ivMale.setBackgroundResource(R.drawable.male_s);
                    tvMale.setTextColor(getResources().getColor(R.color.dark_blue2));
                    ivFemale.setBackgroundResource(R.drawable.female_us);
                    tvFemale.setTextColor(getResources().getColor(R.color.hintcolor));
                    tvgender.setText("Male");
                    gender=tvgender.getText().toString();

                }
            }
        });

        llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ismale = false;
                isFemale = true;
                if (isFemale == true) {
                    ivMale.setBackgroundResource(R.drawable.male_us);
                    tvMale.setTextColor(getResources().getColor(R.color.hintcolor));
                    ivFemale.setBackgroundResource(R.drawable.female_s);
                    tvFemale.setTextColor(getResources().getColor(R.color.dark_blue2));
                    tvgender.setText("Female");
                    gender=tvgender.getText().toString();
                }
            }
        });
        btnSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firstName.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_fst), getResources().getString(R.string.ok), "", "");
                }else if (lastName.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_lst), getResources().getString(R.string.ok), "", "");
                }
                else if (mobileNum.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_mob), getResources().getString(R.string.ok), "", "");
                } else if (email.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_email), getResources().getString(R.string.ok), "", "");
                }
                else if(mm.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_month), getResources().getString(R.string.ok), "", "");

                }else if(dd.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_dat), getResources().getString(R.string.ok), "", "");
                }else if(yy.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_year), getResources().getString(R.string.ok), "", "");

                } else if(height.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_height), getResources().getString(R.string.ok), "", "");

                } else if(weight.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetupActivityNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_weight), getResources().getString(R.string.ok), "", "");
                }else {
                    preference.saveStringInPreference(preference.EMAIL_ID, email);
                    preference.saveStringInPreference(preference.NAME, firstName);
                    preference.saveStringInPreference(preference.LASTNAME, lastName);
                    preference.saveStringInPreference(preference.MOBILE_NUM, mobileNum);
                    preference.saveStringInPreference(preference.GENDER, gender);
                    preference.saveStringInPreference(preference.DOB, dateOfBirth);
                    preference.saveStringInPreference(preference.HEIGHT, height);
                    preference.saveStringInPreference(preference.WEIGHT, weight);
                    preference.saveStringInPreference(preference.HEIGHT_MEASUREMENT, strHeight);
                    preference.saveStringInPreference(preference.WEIGHT_MEASUREMENT, strWeight);
                    mm = etMonth.getText().toString();
                    dd = etDate.getText().toString();
                    yy = etyear.getText().toString();
                    preference.saveStringInPreference(Preference.DD, dd);
                    preference.saveStringInPreference(Preference.MM, mm);
                    preference.saveStringInPreference(Preference.YY, yy);

                    if (userDo != null) {
                        userDo.name = firstName;
                        userDo.lastName = lastName;
                        userDo.mobileNumber = mobileNum;
                        userDo.email = email;
                        userDo.gender = gender;
                        userDo.dob = dateOfBirth;
                        userDo.height = height;
                        userDo.weight = weight;
                        userDo.strHeight = strHeight;
                        userDo.strWeight = strWeight;
                        mUserDa.updateUserData(userDo);

                    } else {
                        userDo = new UserDo();
                        userDo.name = firstName;
                        userDo.lastName = lastName;
                        userDo.mobileNumber = mobileNum;
                        userDo.email = email;
                        userDo.gender = gender;
                        userDo.dob = dateOfBirth;
                        userDo.height = height;
                        userDo.weight = weight;
                        userDo.strHeight = strHeight;
                        userDo.strWeight = strWeight;
                        mUserDa.insertUserDetail(userDo);

                    }

                    if (height != null && !"".equals(height)
                            && weight != null  &&  !"".equals(weight)) {
                        float heightValue = Float.parseFloat(height) / 100;
                        float weightValue = Float.parseFloat(weight);
                        bmi = weightValue / (heightValue * heightValue);
                    }

                    String date= CalendarUtil.getPresentDate();
                    weightDO=new WeightDO();
                    weightDO.weight=weight;
                    weightDO.date=date;
                    weightDO.bmi= String.valueOf(bmi);
                    mUserDa.insertWeight(weightDO);

                    Intent intent=new Intent(ProfileSetupActivityNew.this,HealthTracker.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("USERDO", (Serializable) userDo);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void loadData() {

        userDo = mUserDa.getUserData();

        if (userDo != null) {
            tvNameAndMobileNum.setText(userDo.name+","+userDo.mobileNumber);
            etFirstName.setText(userDo.name);
            etLastName.setText(userDo.lastName);
            etProfileMob.setText(userDo.mobileNumber);
            etProfileidEmail.setText(userDo.email);
            tvEmail.setText(userDo.email);
            tvgender.setText(userDo.gender);
            tvDob.setText(userDo.dob);
            etHeight.setText(userDo.height);
            etKg.setText(userDo.weight);
            tvHeight.setText(userDo.height + userDo.strHeight);
            tvWeight.setText(userDo.weight + userDo.strWeight);
            if ((userDo.strWeight).equalsIgnoreCase("kg")){
                iv_kgSelect.setBackgroundResource(R.drawable.kgselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_unselect);
            }else if ((userDo.strWeight).equalsIgnoreCase("lds")){
                iv_kgSelect.setBackgroundResource(R.drawable.kgunselect);
                iv_lbsUnselect.setBackgroundResource(R.drawable.lbs_select);
            }
            if ((userDo.strHeight).equalsIgnoreCase("cm")){
                iv_inchUnselect.setBackgroundResource(R.drawable.inchunselect);
                iv_cmSelect.setBackgroundResource(R.drawable.cm_select);
            }else if((userDo.strHeight).equalsIgnoreCase("inch")){
                iv_cmSelect.setBackgroundResource(R.drawable.cm_unselect);
                iv_inchUnselect.setBackgroundResource(R.drawable.inchselect);
            }
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String monthStr=showMonth(monthOfYear+1);
            etMonth.setText(monthStr);
            etyear.setText(String.valueOf(year));
            etDate.setText(String.valueOf(dayOfMonth));
        }
    };
    public String showMonth(int month){
        switch (month){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "No data found";
        }
    }
}
