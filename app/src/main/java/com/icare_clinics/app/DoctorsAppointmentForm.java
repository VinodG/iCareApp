package com.icare_clinics.app;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

public class DoctorsAppointmentForm extends BaseActivity {
    private LinearLayout llDoctorApointmentForm;
    private ImageView doctorImg,male,female;
    private EditText firstName,lastName,contactNumber,emailId,edDate,edTime;
    private TextView doctorName,doctorSpeciality;
    private Button btnRequestAppointment;
    private String selectedTime, selectedDate,date;
    private boolean isMaleSelect=false,isFemaleSelect=false;

    @Override
    public void initialise() {
        llDoctorApointmentForm = (LinearLayout) inflater.inflate(R.layout.activity_doctors_appointment_form, null);
        llDoctorApointmentForm.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llDoctorApointmentForm);

       // selectedTime = getIntent().getStringExtra("selectedTime");
       // date = getIntent().getStringExtra("selectedDate");
      //  selectedDate = CalendarUtil.getDate(date, CalendarUtil.DD_MMM_YYYY_PATTERN, CalendarUtil.DATE_STD_PATTERN, Locale.getDefault());


        initialiseControls();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Request Appointment");
        llBack.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        setStatusBarColor();
    }

    @Override
    public void initialiseControls() {
        doctorImg=(ImageView) llDoctorApointmentForm.findViewById(R.id.doctorImg);
        male=(ImageView) llDoctorApointmentForm.findViewById(R.id.male);
        female=(ImageView) llDoctorApointmentForm.findViewById(R.id.female);

        firstName=(EditText)llDoctorApointmentForm.findViewById(R.id.firstName);
        lastName=(EditText)llDoctorApointmentForm.findViewById(R.id.lastName);
        contactNumber=(EditText)llDoctorApointmentForm.findViewById(R.id.contactNumber);
        emailId=(EditText)llDoctorApointmentForm.findViewById(R.id.emailId);
        edDate=(EditText)llDoctorApointmentForm.findViewById(R.id.date);
        edTime=(EditText)llDoctorApointmentForm.findViewById(R.id.time);

        doctorName=(TextView)llDoctorApointmentForm.findViewById(R.id.doctorName);
        doctorSpeciality=(TextView)llDoctorApointmentForm.findViewById(R.id.doctorSpeciality);


      /* if(!StringUtils.isEmpty(selectedDate))
        edDate.setText(date);
        if(!StringUtils.isEmpty(selectedTime))
        edTime.setText(selectedTime);*/
        doctorName.setText(AppConstants.selectedDoctorDo.name);
        doctorSpeciality.setText(AppConstants.selectedDoctorDo.qualification);
        imageurl= ServiceUrls.IMAGE_BASE_URL+ AppConstants.selectedDoctorDo.photo;

        if(!StringUtils.isEmpty(imageurl)){
            setImageCircle(imageurl,doctorImg,R.drawable.docto2);

        }else{
            //image not found
        }

        btnRequestAppointment=(Button)llDoctorApointmentForm.findViewById(R.id.btnRequestAppointment);

        btnRequestAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashboardScreen();

                String fst_name=firstName.getText().toString();
                String last_name=lastName.getText().toString();
                String contact_num=contactNumber.getText().toString();
                String email_id=emailId.getText().toString();
              /*  String strLocation = location.getText().toString();
                String strSelectDoctor = selectDoctor.getText().toString();
                String strGender = gender.getText().toString();*/


                if(StringUtils.isEmpty(fst_name))
                {
                    showCustomDialog(DoctorsAppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_fst), getResources().getString(R.string.ok), "", "");
                }else if(StringUtils.isEmpty(last_name))
                {
                    showCustomDialog(DoctorsAppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_lst), getResources().getString(R.string.ok), "", "");
                }else if(StringUtils.isEmpty(contact_num))
                {
                    showCustomDialog(DoctorsAppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_contact), getResources().getString(R.string.ok), "", "");
                }else if(StringUtils.isEmpty(email_id))
                {
                    showCustomDialog(DoctorsAppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenteremail), getResources().getString(R.string.ok), "", "");
                }else{
                    if (NetworkUtil.isNetworkConnectionAvailable(DoctorsAppointmentForm.this)) {
                       // showLoader("Request appointment....");
                        String auth_token = AppConstants.auth_token;
                        String Action = "requestAppointment";
                       /* new CommonBL(DoctorsAppointmentForm.this, DoctorsAppointmentForm.this).requestAppointment(
                                Action, auth_token, fst_name, last_name, contact_num,
                                strGender, strLocation, email_id, special, strSelectDoctor,
                                selectedDate, selectedTime
                        );*/
                    } else {
                        hideLoader();
                      Toast.makeText(getApplicationContext(),"Your Appointment is Fix",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
      male.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              isMaleSelect=true;
              isFemaleSelect=false;
              if(isMaleSelect)
              {
                  male.setBackgroundResource(R.drawable.male);
                  female.setBackgroundResource(R.drawable.female);
                  String male="male";
                  Log.d("male_male",male);
              }
          }
      });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMaleSelect=false;
                isFemaleSelect=true;
                if(isFemaleSelect)
                {
                    male.setBackgroundResource(R.drawable.male_unselect);
                    female.setBackgroundResource(R.drawable.female_select);
                    String female="female";
                    Log.d("female_female",female);
                }
            }
        });

    }

    @Override
    public void loadData() {

    }


    @Override
    public void dataRetrieved(ResponseDO response) {
        hideLoader();
        if (response.method != null && (response.method == ServiceMethods.WS_REQUEST_APPOINTMENT)) {
            String str = (String) response.data;
            if (str.equals("")) {
                // showPopUp(getString());
                Toast.makeText(this, "Request appointment success", Toast.LENGTH_SHORT).show();
                goToDashboardScreen();

            } else
                showCustomDialog(DoctorsAppointmentForm.this, "", (String) response.data, getString(R.string.OK), "", "");
        }
        super.dataRetrieved(response);
    }

    private void goToDashboardScreen() {
        Intent intent = new Intent(DoctorsAppointmentForm.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
