package com.icare_clinics.app.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.WellnessPackageDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;
import java.util.HashMap;

import static com.icare_clinics.app.R.color.red;

/**
 * Created by WINIT on 01-05-2017.
 */

public class AboutIcareFragment extends Fragment {
    Context context;
    ArrayList<AboutDo> arrAbout = new ArrayList<AboutDo>();
    TextView tvAbout, tvDescription, tvMission, tvVision;
    ImageView ivDoctorImage,ivVision;
    LinearLayout llAbout,llMsg_COO;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        context = getActivity();
        View view = inflater.inflate(R.layout.about_icare_layout, container, false);

        llAbout=(LinearLayout)view.findViewById(R.id.llAbout);
        llMsg_COO=(LinearLayout)view.findViewById(R.id.llMsg_COO);
        tvAbout = (TextView) view.findViewById(R.id.tvAbout);
        ivDoctorImage = (ImageView) view.findViewById(R.id.ivDoctorImage);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        ivVision=(ImageView)view.findViewById(R.id.ivVision);

        Bundle bundle = getArguments();
        arrAbout = (ArrayList<AboutDo>) bundle.getSerializable("ABOUT");
        int position = bundle.getInt("TAB_POSITION");
        if (position == 0) {
            llAbout.setVisibility(View.VISIBLE);
            tvAbout.setText(Html.fromHtml(arrAbout.get(0).description).toString());
        } else if (position == 1)
        {
            ivVision.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(arrAbout.get(0).vision)) {
                Picasso.with(context).load(ServiceUrls.IMAGE_BASE_URL + arrAbout.get(0).vision)
                        .into(ivVision);
            }

        }
        else if (position == 2){
            llMsg_COO.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(arrAbout.get(0).bh_photo)) {
                Picasso.with(context).load(ServiceUrls.IMAGE_BASE_URL + arrAbout.get(0).bh_photo)
                        .into(ivDoctorImage);
            }

            /************************** For setting block color *********************************/
            if (!StringUtils.isEmpty(arrAbout.get(0).bh_msg)) {
                String htmlText = arrAbout.get(0).bh_msg.replace("Message from Chief Operating Officer " + "About Dr. Sanjay Agrawal (Chief Operating Officer, iCARE Clinics) ",
                        "<font color='#000000'>Message from Chief Operating Officer </font>" + "<font color='#000000'>About Dr. Sanjay Agrawal (Chief Operating Officer, iCARE Clinics) </font>");

                tvDescription.setText(Html.fromHtml(htmlText));
            }
//            tvDescription.setText(Html.fromHtml(arrAbout.get(0).bh_msg).toString());
        }
        return view;
    }

}

