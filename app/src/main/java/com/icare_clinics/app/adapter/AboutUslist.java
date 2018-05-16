package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.icare_clinics.app.R;

/**
 * Created by Satish.Babu on 1/3/2017.
 */

public class AboutUslist extends PagerAdapter{

    private Context context;
    public LayoutInflater layoutInflater;
    public int img[];

    public AboutUslist(Context context,int[]  img)
    {
        this.context=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.img=img;
    }


    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView=layoutInflater.inflate(R.layout.aboutus_item,container,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.ivbanner);
        imageView.setImageResource(img[position]);
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View)object);
    }
}
