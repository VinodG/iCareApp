package com.icare_clinics.app;



import java.io.Serializable;
import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.fragments.DatePickerFragment;

public class ProfileSetup extends BaseActivity {
    LinearLayout lLmain, llProfileName, llGender, llDob, llHeight, llWeight, llProfileInput, llGenderSelect, llDobEnter, llWeightEnter, llHeightEnter,llMale,llFemale;
    private Button btnSetProfile, weightNext, HeightNext, dobNext, genderNext, profileButtonNext;
    private EditText etProfileName, etProfileMob, etProfileidEmail, etMonth, etDate, etyear, etKg, etHeight;
    private TextView tvDob, tvgender, tvWeight, tvHeight, tvNameAndMobileNum, tvEmail, tvMale, tvFemale;
    private ImageView ivEditProfile, ivEditGender, ivEditDob, ivEditHeight, ivEditWeight, ivMale, ivFemale;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePicker datePicker;
    private Calendar calendar;
    public UserDo userDo;
    private USerDataDA mUserDa;

    public boolean ismale = false, isFemale = false;

    private String name,mobileNum,email,gender,dateOfBirth,height,weight,mm,dd,yy;

    @Override
    public void initialise() {
        lLmain = (LinearLayout) inflater.inflate(R.layout.activity_profile_setup, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLmain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvTitle.setText("Profile Setup");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setImageResource(R.drawable.back_white);
        mUserDa=new USerDataDA(ProfileSetup.this);

        setStatusBarColor();
        initialiseControls();
    //  loadData();
       /* name=preference.getStringFromPreference(Preference.NAME,"");
        mobileNum=preference.getStringFromPreference(Preference.MOBILE_NUM,"");
        email=preference.getStringFromPreference(Preference.EMAIL_ID,"");
        dateOfBirth=preference.getStringFromPreference(Preference.DOB,"");
        height=preference.getStringFromPreference(Preference.HEIGHT,"");
        weight=preference.getStringFromPreference(Preference.WEIGHT,"");
        mm=preference.getStringFromPreference(Preference.MM,"");
        dd=preference.getStringFromPreference(Preference.DD,"");
        yy=preference.getStringFromPreference(Preference.YY,"");
        gender=preference.getStringFromPreference(Preference.GENDER,"");
    etProfileName.setText(name);
        etProfileMob.setText(mobileNum);
        etProfileidEmail.setText(email);
        etDate.setText(dd);
        etMonth.setText(mm);
        etyear.setText(yy);
        etHeight.setText(height);
        etKg.setText(weight);
        tvWeight.setText(weight + " " + "Kg");
        tvDob.setText(mm + " " + dd + " " + yy);
        tvgender.setText(gender);
        height=etHeight.getText().toString();*/



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

        btnSetProfile = (Button) lLmain.findViewById(R.id.btnSetProfile);
      //  weightNext = (Button) lLmain.findViewById(R.id.weightNext);
        HeightNext = (Button) lLmain.findViewById(R.id.HeightNext);
        dobNext = (Button) lLmain.findViewById(R.id.dobNext);
        genderNext = (Button) lLmain.findViewById(R.id.genderNext);
        profileButtonNext = (Button) lLmain.findViewById(R.id.profileButtonNext);

        etProfileName = (EditText) lLmain.findViewById(R.id.etProfileName);
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
                 name = etProfileName.getText().toString();
                mobileNum = etProfileMob.getText().toString();
                email = etProfileidEmail.getText().toString();

                if (name.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_name), getResources().getString(R.string.ok), "", "");
                } else if (mobileNum.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_mob), getResources().getString(R.string.ok), "", "");
                } else if (email.equalsIgnoreCase("")) {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_email), getResources().getString(R.string.ok), "", "");
                } else if(!isEmailValid(email)){
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_valid_email), getResources().getString(R.string.ok), "", "");
                } else{
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.VISIBLE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.GONE);
                    String nameAndMobileNumber = name + "," + mobileNum;
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
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_month), getResources().getString(R.string.ok), "", "");

                }else if(dd.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_dat), getResources().getString(R.string.ok), "", "");
                }else if(yy.equalsIgnoreCase("")){
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_year), getResources().getString(R.string.ok), "", "");

                }else {
                    tvDob.setText(mm + "/" + dd + "/" + yy);
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
        HeightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height=etHeight.getText().toString();
                if(height.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_height), getResources().getString(R.string.ok), "", "");

                }else {
                    ivEditHeight.setVisibility(View.VISIBLE);
                    tvHeight.setText(height + " " + "cm");
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.GONE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.VISIBLE);
                }
            }
        });
       /* weightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 weight=etKg.getText().toString();
                if(weight.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_weight), getResources().getString(R.string.ok), "", "");
                }else {
                    tvWeight.setText(weight + " " + "Kg");
                    ivEditWeight.setVisibility(View.VISIBLE);
                    llProfileInput.setVisibility(view.GONE);
                    llGenderSelect.setVisibility(view.GONE);
                    llDobEnter.setVisibility(view.GONE);
                    llHeightEnter.setVisibility(view.GONE);
                    llWeightEnter.setVisibility(view.GONE);
                }
            }
        });*/
        llDobEnter.setOnClickListener(new View.OnClickListener() {
            @Override
         public void onClick(View view) {
                showDatePicker();
            }
        });
       /* if (ismale == true) {
            getGenderData();
        }*/
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
      /*  etKg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                etKg.setFocusable(true);
            }
        });
        etHeight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                etHeight.setFocusable(true);
            }
        });*/

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
        btnSetProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                weight=etKg.getText().toString();
                if(weight.equalsIgnoreCase(""))
                {
                    showCustomDialog(ProfileSetup.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_weight), getResources().getString(R.string.ok), "", "");
                }else {
                    tvWeight.setText(weight + " " + "Kg");
                    ivEditWeight.setVisibility(View.VISIBLE);

                }

                preference.saveStringInPreference(preference.EMAIL_ID,email);
                preference.saveStringInPreference(preference.NAME,name);
                preference.saveStringInPreference(preference.MOBILE_NUM,mobileNum);
                preference.saveStringInPreference(preference.GENDER,gender);
                preference.saveStringInPreference(preference.DOB,dateOfBirth);
                preference.saveStringInPreference(preference.HEIGHT,height);
                preference.saveStringInPreference(preference.WEIGHT,weight);
                mm=etMonth.getText().toString();
                dd=etDate.getText().toString();
                yy=etyear.getText().toString();
                preference.saveStringInPreference(Preference.DD,dd);
                preference.saveStringInPreference(Preference.MM,mm);
                preference.saveStringInPreference(Preference.YY,yy);
                if(userDo!=null){
                    userDo.name=name;
                    userDo.mobileNumber=mobileNum;
                    userDo.email=email;
                    userDo.gender=gender;
                    userDo.dob=dateOfBirth;
                    userDo.height=height;
                    userDo.name=name;
                    userDo.weight=weight;
                    mUserDa.updateUserData(userDo);


                }else{
                    userDo=new UserDo();
                    userDo.name=name;
                    userDo.mobileNumber=mobileNum;
                    userDo.email=email;
                    userDo.gender=gender;
                    userDo.dob=dateOfBirth;
                    userDo.height=height;
                    userDo.name=name;
                    userDo.weight=weight;
                    mUserDa.insertUserDetail(userDo);

                }


                Intent intent=new Intent(ProfileSetup.this,HealthTracker.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("USERDO", (Serializable) userDo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {
        userDo = mUserDa.getUserData();
        if (userDo != null) {
            etProfileName.setText(userDo.name);
            etProfileMob.setText(userDo.mobileNumber);
            etProfileidEmail.setText(userDo.email);
            tvgender.setText(userDo.gender);
            tvDob.setText(userDo.dob);
            etHeight.setText(userDo.height);
            etKg.setText(userDo.weight);
            tvHeight.setText(userDo.height + "cm");
            tvWeight.setText(userDo.weight + "kg");
            //tvHeight.setText(height + " " + "meter");
            //tvWeight.setText(weight + " " + "Kg");
        }

    }

   /* public void getGenderData() {
        ivMale.setBackgroundResource(R.drawable.male_s);
        tvMale.setTextColor(getResources().getColor(R.color.dark_blue2));
        tvFemale.setTextColor(getResources().getColor(R.color.hintcolor));
        tvgender.setText("Male");
        ivFemale.setBackgroundResource(R.drawable.female_us);
    }
*/
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

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String monthStr=showMonth(monthOfYear+1);
            etMonth.setText(monthStr);
            etyear.setText(String.valueOf(year));
            etDate.setText(String.valueOf(dayOfMonth));
          /*  Toast.makeText(
                    ProfileSetup.this,
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth),
                    Toast.LENGTH_LONG).show();*/

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
