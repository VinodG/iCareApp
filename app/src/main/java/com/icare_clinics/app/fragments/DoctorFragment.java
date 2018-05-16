package com.icare_clinics.app.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.icare_clinics.app.DoctorDetail;
import com.squareup.picasso.Picasso;
import com.icare_clinics.app.DoctorDetailNew;
import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

/**
 * Created by Ganesh.D on 06-12-2016.
 */
@SuppressLint("ValidFragment")
public class DoctorFragment extends Fragment {
    ListView lv;
    int BigImageId, laoutId;
    ImageView ivDoctorBigImage;
    TextView tvDoctorName, tvDoctorQualification, tvDoctorLocation, tvAboutDoctor,tvViewDetails;
    LinearLayout lldoctorFragment;
    String dName, dQual, dLocation, dAbout;
    LayoutInflater inflater;
    ArrayAdapter<String> list;
    View view;
    ViewGroup container;
    private DoctorDo doctorDo;
    private Context context;
    public DoctorFragment() {
    }
    public DoctorFragment(int laoutId, int BigImageId, String dName, String dQual, String dLocation, String dAbout) {
        this.BigImageId = BigImageId;
        this.laoutId = laoutId;
        this.dName = dName;
        this.dQual = dQual;
        this.dLocation = dLocation;
        this.dAbout = dAbout;
    }
    public DoctorFragment(int laoutId,DoctorDo doctorDo,Context context) {
        this.laoutId = laoutId;
        this.doctorDo = doctorDo;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Fragment1", "OnCreateView");
        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(laoutId, container, false);
        ivDoctorBigImage = (ImageView) view.findViewById(R.id.ivDoctorBigImage);
        tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
        tvDoctorQualification = (TextView) view.findViewById(R.id.tvDoctorQualification);
        tvDoctorLocation = (TextView) view.findViewById(R.id.tvDoctorLocation);
        tvAboutDoctor = (TextView) view.findViewById(R.id.tvAboutDoctor);
        tvViewDetails = (TextView) view.findViewById(R.id.tvViewDetails);
        lldoctorFragment=(LinearLayout)view.findViewById(R.id.lldoctorFragment);

        tvDoctorName.setTypeface(AppConstants.UbuntuRegular);
        tvDoctorQualification.setTypeface(AppConstants.UbuntuRegular);
        tvDoctorLocation.setTypeface(AppConstants.UbuntuRegular);
        tvViewDetails.setTypeface(AppConstants.UbuntuMedium);
        tvAboutDoctor.setTypeface(AppConstants.UbuntuRegular);


       // ivDoctorBigImage.setImageResource(BigImageId);
        tvDoctorName.setText(doctorDo.name);
        tvDoctorQualification.setText(doctorDo.qualification);
        tvDoctorLocation.setText(doctorDo.location);
       // tvAboutDoctor.setText(doctorDo.details);
        tvAboutDoctor.setText(StringUtils.fromHtml(doctorDo.details));

        if (!StringUtils.isEmpty(doctorDo.photo)) {
            Picasso.with(context)
                    .load(ServiceUrls.IMAGE_BASE_URL + doctorDo.photo).placeholder(R.drawable.doctors)
                    .into(ivDoctorBigImage);
        }else{
            //use image not avaiable
        }
        lldoctorFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DoctorDetail.class);
                intent.putExtra("doctorDO",doctorDo);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Fragment1", "OnCreate");


    }
}
