package com.icare_clinics.app.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.BannerDo;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by Satish.Babu on 12/27/2016.
 */

public class CustomviewAdapter extends PagerAdapter {

    private int img[];
    Context context;
    private LayoutInflater mLayoutInflater;
    public ArrayList<BannerDo> arrBanner;

    public CustomviewAdapter(Context context, ArrayList<BannerDo> arrBanner) {
        this.context = context;
        this.arrBanner = arrBanner;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (arrBanner != null) {
            return arrBanner.size();
//            return 3;
        } else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dashboard_viewpage, container, false);
        BannerDo bannerDo = arrBanner.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_doctorphoto);
        String imageurl = ServiceUrls.IMAGE_BASE_URL + bannerDo.image;
        setBannerImage(imageurl,imageView);

       /* final Uri uri = Uri.parse(imageurl);
        Bitmap bitmap = null;
        if (uri != null) {
            bitmap = ((BaseActivity) context).getHttpImageManager().loadImage(new HttpImageManager.LoadRequest(uri, imageView, imageurl));
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        } else {
            imageView.setImageResource(R.drawable.wallpaper);
        }*/
        // imageView.setImageResource(img[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void refresh(ArrayList<BannerDo> arrBanner) {
        this.arrBanner = arrBanner;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public void setBannerImage(String imageurl, ImageView imageView) {
        if (!StringUtils.isEmpty(imageurl)) {
            int size = R.drawable.banner_contact_us;
            BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.banner_contact_us);
            int mHeight = bd.getBitmap().getHeight();
            int mWidth = bd.getBitmap().getWidth();

            LogUtils.errorLog("Image Url:", imageurl);
            Picasso.with(context).load(imageurl)
                    .resize(mWidth, mHeight)
                    .error(size)
                    .into(imageView);
        }
    }
}