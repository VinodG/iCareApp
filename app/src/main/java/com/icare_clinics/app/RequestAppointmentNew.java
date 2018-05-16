package com.icare_clinics.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.businesslayer.CommonBL;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.USerDataDA;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.Arrays;
import java.util.List;


/**
 * Created by srikrishna.nunna on 19-04-2017.
 */

public class RequestAppointmentNew extends BaseActivity {
    private LinearLayout llMain;
    private TextView tvDoctorName,tvSpeciality,tvDate,tvTime,tvlocation;
    private EditText etfirstName,etlastName,etcontactNumber,etEmailId;
    private ImageView ivMale,ivFemale,ivDoctor;
    private Button btnRequestAppointment;
    private DoctorDo doctorDo;
    public List<String> arrlocation;
    private  String male="Male";
    private String female="";
    public String gender="";
    public String last_name="";
    public String specialityId="";
    public UserDo userDo;
    private USerDataDA mUserDa;

    @Override
    public void initialise() {
        llMain=(LinearLayout)inflater.inflate(R.layout.request_appointment_new,null);
        llMain.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,1.0f));
        llBody.addView(llMain);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        ivSearch.setVisibility(View.GONE);
        llBack.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);

        tvTitle.setText("Request Appointment");
        tvTitle.setTextColor(Color.WHITE);
        setStatusBarColor();
        doctorDo = (DoctorDo) getIntent().getSerializableExtra("doctorDO");

        mUserDa=new USerDataDA(RequestAppointmentNew.this);
        initialiseControls();

        MyTextWatcher tw = new MyTextWatcher();
        tw.setView(etfirstName);
        etfirstName.addTextChangedListener(tw);

        MyTextWatcher tw1 = new MyTextWatcher();
        tw1.setView(etlastName);
        etlastName.addTextChangedListener(tw1);

    }

    @Override
    public void initialiseControls() {
        tvlocation      =(TextView) llMain.findViewById(R.id.tvlocation);
        tvDoctorName    =(TextView) llMain.findViewById(R.id.tvDoctorName);
        tvSpeciality    =(TextView) llMain.findViewById(R.id.tvSpeciality);

        etfirstName     =(EditText) llMain.findViewById(R.id.etfirstName);
        etlastName      =(EditText) llMain.findViewById(R.id.etlastName);
        etcontactNumber =(EditText) llMain.findViewById(R.id.etcontactNumber);
        etEmailId       =(EditText) llMain.findViewById(R.id.etEmailId);

        tvDate          =(TextView) llMain.findViewById(R.id.tvDate);
        tvTime          =(TextView) llMain.findViewById(R.id.tvTime);
        ivMale          =(ImageView) llMain.findViewById(R.id.ivMale);
        ivFemale        =(ImageView) llMain.findViewById(R.id.ivFemale);
        ivDoctor        =(ImageView) llMain.findViewById(R.id.ivDoctor);
        btnRequestAppointment   =(Button) llMain.findViewById(R.id.btnRequestAppointment);

        etfirstName.setTypeface(AppConstants.DinproRegular);
        etlastName.setTypeface(AppConstants.UbuntuRegular);
        etcontactNumber.setTypeface(AppConstants.UbuntuRegular);
        etEmailId.setTypeface(AppConstants.UbuntuRegular);
        tvDate.setTypeface(AppConstants.UbuntuRegular);
        tvTime.setTypeface(AppConstants.UbuntuRegular);


        ivMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male="Male";
                ivFemale.setImageResource(R.drawable.female);
                ivMale.setImageResource(R.drawable.male);
            }
        });
        ivFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male="Male";
                ivMale.setImageResource(R.drawable.male_unselect);
                ivFemale.setImageResource(R.drawable.female_select);
            }
        });

        userDo = mUserDa.getUserData();
        if(userDo!=null){
            String str=userDo.name;
            String[] name = str.split("\\s+");

//            tvfirstName.setText(name[0]);
//            tvlastName.setText(name[1]);
            etfirstName.setText(userDo.name);
            etlastName.setText(userDo.lastName);
            etEmailId.setText(userDo.email);
            etcontactNumber.setText(userDo.mobileNumber);
            gender=userDo.gender;
            if (gender.equalsIgnoreCase("male")){
                ivFemale.setImageResource(R.drawable.female);
                ivMale.setImageResource(R.drawable.male);
            }else {
                ivMale.setImageResource(R.drawable.male_unselect);
                ivFemale.setImageResource(R.drawable.female_select);
            }
        }


        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle !=null){
            String date=(String) bundle.get("date");
            String time=(String) bundle.get("time");
            tvDate.setText(date);
            tvTime.setText(time);
        }
        if(doctorDo!=null){
            AppConstants.selectedDoctorDo=doctorDo;
            imageurl= ServiceUrls.IMAGE_BASE_URL+doctorDo.photo;
            tvDoctorName.setText(doctorDo.name);
            tvSpeciality.setText(doctorDo.qualification);

            if (!StringUtils.isEmpty(imageurl)) {
                imageurl = imageurl.replaceAll(" ", "%50");
                ((BaseActivity) context).setImageCircle(imageurl, ivDoctor, R.drawable.docto2);

            } else {
                //image not found
            }
        }

        arrlocation= Arrays.asList(doctorDo.location.split(","));
        if(arrlocation!=null) {

            if (arrlocation.size() == 1) {
                tvlocation.setText(arrlocation.get(0));
            }
        }
        tvlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                arrlocation= Arrays.asList(doctorDo.location.split(","));
                if(arrlocation!=null){

                    if(arrlocation.size()>1)
                    {
//                        tvlocation.setText(arrlocation.get(0));
                        ShowAlertDialogWithListview();
                    }else if(arrlocation.size()==0){
                        showCustomDialog(RequestAppointmentNew.this,getString(R.string.alert),getString(R.string.location_not_found),getString(R.string.OK),getString(R.string.cancel),"Exit");

                    }/*else {
                        ShowAlertDialogWithListview();

                    }*/
                }else{
                    showCustomDialog(RequestAppointmentNew.this,getString(R.string.alert),getString(R.string.enter_doctor_speciality),getString(R.string.OK),getString(R.string.cancel),"Exit");
                }
            }
        });

        btnRequestAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strLocation = tvlocation.getText().toString();
                String special = tvSpeciality.getText().toString();
                String strSelectDoctor = tvDoctorName.getText().toString();
                String appointmentDate=tvDate.getText().toString();
                String appointmentTime=tvTime.getText().toString();
                String fst_name = etfirstName.getText().toString();
                String email_id = etEmailId.getText().toString();
                String lastName = etlastName.getText().toString();
                String contact_num = etcontactNumber.getText().toString();
                if (StringUtils.isEmpty(strLocation)) {
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_special), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(fst_name)) {
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_fst), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(lastName)) {
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_lst), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(contact_num)) {
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_contact), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(email_id)) {
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_email), getResources().getString(R.string.ok), "", "");
                }else if(!isEmailValid(email_id)){
                    showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_valid_email), getResources().getString(R.string.ok), "", "");
                }
                else {
//                    Toast.makeText(RequestAppointmentNew.this, "Work In Progress",Toast.LENGTH_LONG).show();
                    if(NetworkUtil.isNetworkConnectionAvailable(RequestAppointmentNew.this))
                    {
                        //  showLoader("Request Appointment....");
                        String auth_token = AppConstants.auth_tocken_req;
                        String Action = "requestAppointment";
                        new CommonBL(RequestAppointmentNew.this, RequestAppointmentNew.this).requestAppointment(Action,
                                male,strLocation,doctorDo.id,email_id,contact_num,lastName,auth_token,
                                appointmentDate,specialityId,appointmentTime,fst_name,"UAE"
                        );

                    }
                }
            }
        });
    }

    public void ShowAlertDialogWithListview()
    {
        //  List<String> mAnimals = new ArrayList<String>();
        //Create sequence of items
        final CharSequence[] locations_list = arrlocation.toArray(new String[arrlocation.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Location");
        dialogBuilder.setItems(locations_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = locations_list[item].toString();//Selected item in listview
                tvlocation.setText(selectedText);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }


    @Override
    public void loadData() {

    }
    @Override
    public void dataRetrieved(ResponseDO response)
    {
        if(response!=null)
        {
            showCustomDialog(RequestAppointmentNew.this, getResources().getString(R.string.success_appoint),getResources().getString(R.string.request_sent), getResources().getString(R.string.ok), "", "ConfirmAppointment");
        }
    }

    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
        if (from.equalsIgnoreCase("ConfirmAppointment")) {
            Intent intent1 = new Intent(RequestAppointmentNew.this, DashBoardActivity.class);
            startActivity(intent1);
            finish();
        }
    }

}
