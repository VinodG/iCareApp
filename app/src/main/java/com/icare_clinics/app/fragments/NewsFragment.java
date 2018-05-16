package com.icare_clinics.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.dataobject.TestimonialDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

/**
 * Created by WINIT on 28-04-2017.
 */

public class NewsFragment extends Fragment
{
    Context context;
    int layoutId;
    TestimonialDo testimonialDo;

    LayoutInflater inflater;
    ViewGroup container;
    View view;
    NewsDo newDo= null;

    TextView tvTittleNews,tvDescriptionNews;
    ImageView ivNews;
    public NewsFragment(int layoutId, NewsDo newDo, Context context)
    {
        this.layoutId=layoutId;
        this.newDo = newDo;
        this.context = context;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        return super.onCreateView(inflater, container, savedInstanceState);

        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(layoutId, container, false);
        tvTittleNews = (TextView) view.findViewById(R.id.tvTittleNews);
        tvDescriptionNews = (TextView) view.findViewById(R.id.tvDescriptionNews);
        ivNews = (ImageView) view.findViewById(R.id.ivNews);
        tvDescriptionNews.setText(Html.fromHtml(newDo.description).toString());
        tvTittleNews.setText(newDo.title);

        //holder.ivNews
        if (!StringUtils.isEmpty(newDo.image)) {
            Picasso.with(context)
                    .load(ServiceUrls.IMAGE_BASE_URL + newDo.image).placeholder(R.drawable.profile_pic)
                    .into(ivNews);
        }else{
            ivNews.setImageResource(R.drawable.profile_pic);
        }

        return  view;

    }


}
