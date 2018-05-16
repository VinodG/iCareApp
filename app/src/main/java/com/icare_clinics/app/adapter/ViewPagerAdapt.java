package com.icare_clinics.app.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.dataobject.TestimonialDo;
import com.icare_clinics.app.fragments.DoctorFragment;
import com.icare_clinics.app.fragments.NewsFragment;
import com.icare_clinics.app.fragments.TestimonialFragment;

import java.util.ArrayList;

/**
 * Created by Ganesh.D on 06-12-2016.
 */

public class ViewPagerAdapt extends FragmentStatePagerAdapter {

//    private ArrayList<TestimonialDo> arrTestimonial= null;
//    private ArrayList<DoctorDo> arrDoctor=null;
    private Context context;
    private int dataSelection=0;
    Object object=null;

   /* public ViewPagerAdapt(android.support.v4.app.FragmentManager fm, Object arrDoctor, Context context) {
        super(fm);
        this.arrDoctor = (ArrayList<DoctorDo>) arrDoctor;
        this.context = context;
    }*/

    public ViewPagerAdapt(android.support.v4.app.FragmentManager fm, Object object, Context context,int dataSelection) {
        super(fm);
        this.context = context;
        this.dataSelection = dataSelection;
        this.object =object;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(dataSelection==1)
        {
            DoctorDo doctorDo = ( (ArrayList<DoctorDo>)object).get(position);
            DoctorFragment tab = new DoctorFragment(R.layout.fragment1, doctorDo, context);
            return tab;
        }
        else if(dataSelection==2)
        {
            TestimonialDo testimonialDo = ( (ArrayList<TestimonialDo>)object).get(position);
            TestimonialFragment tab = new TestimonialFragment(R.layout.testimonial_fragment, testimonialDo, context);
            return tab;
        }
        else if(dataSelection==3)
        {
            NewsDo newDo = ( (ArrayList<NewsDo>)object).get(position);
            NewsFragment tab = new NewsFragment(R.layout.news_fragment, newDo, context);
            return tab;
        }
        return  null;

    }

    @Override
    public int getCount() {
        if (object == null)
        {
            return  0;
        }
        else
            return ((ArrayList)object).size();
    }

    public void refresh(Object object) {
        this.object =object;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    public void reset(Object freshDoctorList) {
        object = freshDoctorList;
    }

}
