package com.icare_clinics.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.icare_clinics.app.businesslayer.CommonBL;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.fragments.SelectDateTimeBottomSheetFragment;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.utilities.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.icare_clinics.app.R.id.etfirstName;
import static com.icare_clinics.app.R.id.llClinicToobar;

public class AppointmentForm extends BaseActivity
{
    private EditText firstName,contactNumber,location, speciality, selectDoctor, etDate, edTime, emailId,lastName;
    private TabLayout tabLayout;
    private GridView gridViewTime;
    // private DoctorsTimeAdapter adapter;
    private ArrayList<View> view;
    private Button btnRequestAppointment,morningTime, afternoonTime, eveningTime;
    private ImageView ivMale,ivFemale,ivBackImage;
    ListView list;
    private DoctorDo doctorDo;
    private LinearLayout llAppointmentForm;
    private boolean isMaleSelect=true,isFemaleSelect=false;
    public  SpecializationDO specialityDo;
    public  List<String> arrlocation;
    private  String male="";
    private String female="";
    public String doctorId="";
    public String last_name="";
    public String specialityId="";
    private String specialization="";

    @Override
    public void initialise() {

        llAppointmentForm = (LinearLayout) inflater.inflate(R.layout.activity_appointment_form_new, null);
        llAppointmentForm.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,1.0f));
        llBody.addView(llAppointmentForm);

        initialiseControls();
//        setStatusBarColor();
        setStatusBarTransparent();

        toolbar.setVisibility(View.GONE);
        View RequestAppointToobar = inflater.inflate(R.layout.tool_bar, null);
        RequestAppointToobar.setBackgroundColor(Color.TRANSPARENT);
        ivMenu = (ImageView) RequestAppointToobar.findViewById(R.id.ivMenu);
        ivSearch = (ImageView) RequestAppointToobar.findViewById(R.id.ivSearch);
        ivBack = (ImageView) RequestAppointToobar.findViewById(R.id.ivBack);
        llBack = (LinearLayout) RequestAppointToobar.findViewById(R.id.llBack);
        tvTitle = (TextView) RequestAppointToobar.findViewById(R.id.tvTitle);
        tvTitle.setBackground(null);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Request an Appointment");
        tvTitle.setTextColor(Color.WHITE);
        llBack.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);

        LinearLayout llRequestAppoint = (LinearLayout) findViewById(R.id.llRequestAppoint);
        llRequestAppoint.removeAllViews();
        llRequestAppoint.addView(RequestAppointToobar);

        specialityDo=new SpecializationDO();
        doctorDo=new DoctorDo();

        speciality.setFocusable(false);
        speciality.setClickable(true);
        selectDoctor.setFocusable(false);
        selectDoctor.setClickable(true);
        location.setFocusable(false);
        location.setClickable(true);

        btnRequestAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String special = speciality.getText().toString();
                String strSelectDoctor = selectDoctor.getText().toString();
                String strLocation = location.getText().toString();
                String appointmentDate=etDate.getText().toString();
                String appointmentTime=edTime.getText().toString();
                String fst_name = firstName.getText().toString();
                String lst_name=lastName.getText().toString();
                String email_id = emailId.getText().toString();
                String contact_num = contactNumber.getText().toString();

                if (StringUtils.isEmpty(special)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_special), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(strSelectDoctor)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_doctor), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(strLocation)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_location), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(appointmentDate)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_date), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(appointmentTime)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_time), getResources().getString(R.string.ok), "", "");
                } else if (StringUtils.isEmpty(fst_name)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_fst), getResources().getString(R.string.ok), "", "");
                }
                else if (StringUtils.isEmpty(lst_name)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_lst), getResources().getString(R.string.ok), "", "");
                }
                else if (StringUtils.isEmpty(contact_num)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_contact), getResources().getString(R.string.ok), "", "");
                }else if (StringUtils.isEmpty(email_id)) {
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenteremail), getResources().getString(R.string.ok), "", "");
                }else if(!isEmailValid(email_id)){
                    showCustomDialog(AppointmentForm.this, getResources().getString(R.string.alert), getResources().getString(R.string.plzenter_valid_email), getResources().getString(R.string.ok), "", "");
                }else
                {
//                    Toast.makeText(AppointmentForm.this, "Work In Progress",Toast.LENGTH_LONG);
                   /* if (NetworkUtil.isNetworkConnectionAvailable(AppointmentForm.this)) {
                        showLoader("Request appointment....");
                        String auth_token = AppConstants.auth_token;
                        String Action = "requestAppointment";
                        new CommonBL(AppointmentForm.this, AppointmentForm.this).requestAppointment(
                                Action, auth_token, fst_name, last_name, contact_num,
                                strGender, strLocation, email_id, special, strSelectDoctor,
                                selectedDate, selectedTime
                        );
                    } else
                        hideLoader();
*/
                    if(NetworkUtil.isNetworkConnectionAvailable(AppointmentForm.this))
                    {
                        //  showLoader("Request Appointment....");
                        String auth_token = AppConstants.auth_tocken_req;
                        String Action = "requestAppointment";
                        new CommonBL(AppointmentForm.this, AppointmentForm.this).requestAppointment(Action,
                                male,strLocation,doctorDo.id,email_id,contact_num,lst_name,auth_token,
                                appointmentDate,specialityId,appointmentTime,fst_name,"UAE"
                        );
                    }
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      showLocation();
                String strSpeciality=speciality.getText().toString();
                String strDoctor=selectDoctor.getText().toString();
                if ((doctorDo ==null && specialityDo==null) || strDoctor.equalsIgnoreCase("")){
                    showCustomDialog(AppointmentForm.this,getString(R.string.alert),getString(R.string.enter_doctor_speciality),getString(R.string.OK),getString(R.string.cancel),"Exit");
                }else {
                    arrlocation = Arrays.asList(doctorDo.location.split(","));
                    if (arrlocation != null) {

                      /*  if (arrlocation.size() == 1) {
                            location.setText(arrlocation.get(0));
                        } else */
                        if (arrlocation.size() == 0) {
                            showCustomDialog(AppointmentForm.this, getString(R.string.alert), getString(R.string.location_not_found), getString(R.string.OK), getString(R.string.cancel), "Exit");

                        } else if (arrlocation.size()>1){
                            ShowAlertDialogWithListview();
                        }
                    } else {
                        showCustomDialog(AppointmentForm.this, getString(R.string.alert), getString(R.string.enter_doctor_speciality), getString(R.string.OK), getString(R.string.cancel), "Exit");
                    }
                }
            }
        });
        speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDoctor.setText("");
                selectDoctor.setHint("Select Doctor");
                location.setText("");
                location.setHint("Location");
                showSpecialites();
            }
        });
        selectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                    location.setText("");
                    location.setHint("Location");
                showSelectDoctor();
            }
        });
        if(isMaleSelect==true)
        {
            male="Male";
            Log.d("male_male",male);
        }
        ivMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMaleSelect=true;
                isFemaleSelect=false;
                if(isMaleSelect==true)
                {
                    ivMale.setBackgroundResource(R.drawable.male);
                    ivFemale.setBackgroundResource(R.drawable.female);
                    male="Male";
                    Log.d("male_male",male);
                }
            }
        });
        ivFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMaleSelect=false;
                isFemaleSelect=true;
                if(isFemaleSelect==true)
                {
                    ivMale.setBackgroundResource(R.drawable.male_unselect);
                    ivFemale.setBackgroundResource(R.drawable.female_select);
                    male="Female";
                    Log.d("female_female",male);
                }
            }
        });
        ivBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        view = new ArrayList<View>();
//        loadData();
    }

    @Override
    public void initialiseControls() {
        firstName = (EditText) llAppointmentForm.findViewById(R.id.firstName);
        lastName  =(EditText) llAppointmentForm.findViewById(R.id.lastName);
        emailId = (EditText) llAppointmentForm.findViewById(R.id.emailId);
        contactNumber = (EditText) llAppointmentForm.findViewById(R.id.contactNumber);
        edTime = (EditText) llAppointmentForm.findViewById(R.id.edTime);
        etDate = (EditText) llAppointmentForm.findViewById(R.id.etDate);

        location = (EditText) llAppointmentForm.findViewById(R.id.location);
        speciality = (EditText) llAppointmentForm.findViewById(R.id.speciality);
        selectDoctor = (EditText) llAppointmentForm.findViewById(R.id.selectDoctor);
        btnRequestAppointment = (Button) llAppointmentForm.findViewById(R.id.btnRequestAppointment);
        ivMale=(ImageView)llAppointmentForm.findViewById(R.id.ivMale);
        ivFemale=(ImageView)llAppointmentForm.findViewById(R.id.ivFemale);
        ivBackImage=(ImageView)llAppointmentForm.findViewById(R.id.ivBackImage);

        tabLayout= (TabLayout) llAppointmentForm.findViewById(R.id.tabs);
        gridViewTime=(GridView)llAppointmentForm.findViewById(R.id.gridViewTime);
        morningTime=(Button)llAppointmentForm.findViewById(R.id.morningTime);
        eveningTime=(Button)llAppointmentForm.findViewById(R.id.eveningTime);
        afternoonTime=(Button)llAppointmentForm.findViewById(R.id.afternoonTime);
      /*  adapter = new DoctorsTimeAdapter(AppointmentForm.this, null);
        gridViewTime.setAdapter(adapter);
        gridViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectedPosition = position;
                adapter.refresh(appointmentTime);
                selectedTime = appointmentTime[position];
            }
        });*/
        MyTextWatcher tw = new MyTextWatcher();
        tw.setView(firstName);
        firstName.addTextChangedListener(tw);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDateTimeBottomSheetFragment();
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDoctorAppointmentBottomFragment();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDateTimeBottomSheetFragment();
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDoctorAppointmentBottomFragment();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
    }

    @Override
    public void loadData() {

    }
    protected void showLocation()
    {
       /* Intent intent2 = new Intent( AppointmentForm.this,SelectLocation.class);
        intent2.putExtra("GET_DOCTOR",selectDoctor.getText().toString());
        startActivityForResult(intent2,3);*/
    }
    protected void showSelectDoctor()
    {
        specialization=speciality.getText().toString();
        if(specialization.equals(""))
        {
            showCustomDialog(AppointmentForm.this,getString(R.string.alert),getString(R.string.doctor),getString(R.string.OK),getString(R.string.cancel),"Exit");

        }else {
            Intent intent3 = new Intent(AppointmentForm.this, SelectDoctor.class);
            intent3.putExtra("GET_DOCTOR", specialityDo.id);
            startActivityForResult(intent3, 2);
        }
    }
    protected void showSpecialites()
    {
        Intent intent=new Intent(AppointmentForm.this,SelectSpecialities.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void dataRetrieved(ResponseDO response)
    {
        if(response!=null)
        {
            showCustomDialog(AppointmentForm.this, getResources().getString(R.string.success_appoint),getResources().getString(R.string.request_sent), getResources().getString(R.string.ok), "", "SuccessAppoint");
        }
    }

    private void goToDashboardScreen() {
        Intent intent = new Intent(AppointmentForm.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void datePickerDialog(final EditText ed) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(AppointmentForm.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ed.setText(new SimpleDateFormat(("dd/MM/yy"), Locale.US).format(myCalendar.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // mDatePicker.setTitle("Select Birthday Date");
        mDatePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        String str;
//        specialityDo=new SpecializationDO();
//        doctorDo=new DoctorDo();
        if(requestCode ==1)
        {
            if (resultCode == RESULT_OK) {
                specialityDo = (SpecializationDO)intent.getSerializableExtra("SPECIALITIES");
                if(specialityDo!=null)
                    speciality.setText(specialityDo.name);
                specialityId=specialityDo.id;
            }
        }
        if(requestCode ==2 )
        {
            if (resultCode == RESULT_OK) {
                doctorDo = (DoctorDo) intent.getSerializableExtra("DOCTORS");
                if (doctorDo != null)
                    selectDoctor.setText(doctorDo.name);
                doctorId = doctorDo.id;

                arrlocation = Arrays.asList(doctorDo.location.split(","));
                if (arrlocation != null) {

                    if (arrlocation.size() == 1) {
                        location.setText(arrlocation.get(0));
                    }
                }
            }
        }
        if(requestCode ==3 )
        {
            if (resultCode == RESULT_OK) {
                str = intent.getStringExtra("LOCATION");
                location.setText(str);
            }
        }
    }
    public void ShowAlertDialogWithListview()
    {
        //Create sequence of items
        final CharSequence[] locations_list = arrlocation.toArray(new String[arrlocation.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Location");
        dialogBuilder.setItems(locations_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = locations_list[item].toString();//Selected item in listview
                location.setText(selectedText);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
    public void showdateTime(String date,String time)
    {
        etDate.setText(date);
        edTime.setText(time);
    }

    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);
        if (from.equalsIgnoreCase("SuccessAppoint")){
            Intent intent=new Intent(AppointmentForm.this,DashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
