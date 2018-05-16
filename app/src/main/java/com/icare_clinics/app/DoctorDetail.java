package com.icare_clinics.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.fragments.SelectDateTimeBottomSheetFragment;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

public class DoctorDetail extends BaseActivity {

//    private ScrollView llMain;
    private ViewGroup llMain;
    private DoctorDo doctorDo;
    private ImageView ivDoctor,iv_fab_appoint,iv_fab_call;
    private TextView drName,tvDegree,tvAddress,tvDetails,tvDate,tvTime;
    private Button btnRequestAppo;

    @Override
    public void initialise() {
        llMain = (ViewGroup) inflater.inflate(R.layout.activity_doctor_detail, null);
        llMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llMain);
//        toolbar.setVisibility(View.GONE);
        doctorDo = (DoctorDo) getIntent().getSerializableExtra("doctorDO");
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.doctors_details);
        ivBack.setVisibility(View.VISIBLE);
        llBack.setVisibility(View.VISIBLE);
//        ivBack.setImageResource(R.drawable.back_white);
        iv_fab.setVisibility(View.GONE);
        setStatusBarColor();
        initialiseControls();
    }

    @Override
    public void initialiseControls() {
        drName= (TextView) llMain.findViewById(R.id.drName);
        tvDegree= (TextView) llMain.findViewById(R.id.tvDegree);
        tvAddress= (TextView) llMain.findViewById(R.id.tvAddress);
        tvDate= (TextView) llMain.findViewById(R.id.tvDate);
        tvTime= (TextView) llMain.findViewById(R.id.tvTime);
        tvDetails= (TextView) llMain.findViewById(R.id.tvDetails);
        ivDoctor= (ImageView) llMain.findViewById(R.id.ivDoctor);
//        btnRequestAppo= (Button) llMain.findViewById(R.id.btnRequestAppo);
        iv_fab_appoint=(ImageView) llMain.findViewById(R.id.iv_fab_appoint);
        iv_fab_call=(ImageView)llMain.findViewById(R.id.iv_fab_call);

        imageurl= ServiceUrls.IMAGE_BASE_URL+doctorDo.photo;
        drName.setTypeface(AppConstants.DinproBold);
        tvDegree.setTypeface(AppConstants.UbuntuMedium);
        tvAddress.setTypeface(AppConstants.UbuntuMedium);

        if(doctorDo!=null){
            AppConstants.selectedDoctorDo=doctorDo;
            drName.setText(doctorDo.name);
            tvDegree.setText(doctorDo.qualification);
            tvAddress.setText(doctorDo.location);
            tvDetails.setText(StringUtils.fromHtml(doctorDo.details));
            if(!StringUtils.isEmpty(imageurl)){
                Picasso.with(DoctorDetail.this).load(imageurl)
                        .into(ivDoctor);
            }else{
                //image not found
            }
        }
     /*   btnRequestAppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorDetail.this,DoctorsAppointmentForm.class);
                startActivity(intent);
            }
        });*/

        iv_fab_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoardActivity.this,"clicked",Toast.LENGTH_LONG).show();

              /*  BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDateTimeBottomSheetFragment();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());*/
                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDoctorAppointmentBottomFragment();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());

            }
        });
        iv_fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1800 iCARE(42273)"));
                    if (ActivityCompat.checkSelfPermission(DoctorDetail.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    public DoctorDo getDoctorDO(){
        return doctorDo;
    }

}
