package com.icare_clinics.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.TestimonialDo;

/**
 * Created by WINIT on 28-04-2017.
 */

public class TestimonialFragment  extends Fragment
{
    Context context;
    int laoutId;
    TestimonialDo testimonialDo;

    LayoutInflater inflater;
    ViewGroup container;
    View view;

    TextView tvName,tvAbout,tvDates;
    public TestimonialFragment(int laoutId, TestimonialDo testimonialDo, Context context)
    {
        this.context=context;
        this.laoutId=laoutId;
        this.testimonialDo= testimonialDo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        return super.onCreateView(inflater, container, savedInstanceState);

        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(laoutId, container, false);
        tvAbout = (TextView) view.findViewById(R.id.tvAbout);
        tvDates = (TextView) view.findViewById(R.id.tvDates);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAbout.setText(Html.fromHtml(testimonialDo.description).toString());
        tvName.setText(testimonialDo.title);
        tvDates.setText(testimonialDo.date);
        // ivDoctorBigImage.setImageResource(BigImageId);
        return  view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
