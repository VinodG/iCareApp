package com.icare_clinics.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.utilities.ImageConverter;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 23-01-2017.
 */

public class SpecialistTabAdapter extends BaseAdapter {
    public ArrayList<Integer> allImagesId = new ArrayList<>();
    public ArrayList<String>allNamesId= new ArrayList<String>();
    private LayoutInflater inflater;
    Context context;
    public SpecialistTabAdapter(Context context,/*ArrayList<Drawable>images*/ ArrayList<Integer> allImagesId,ArrayList<String>names){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.allImagesId= allImagesId;
        this.allNamesId=names;
    }
    @Override
    public int getCount() {
        if(allImagesId != null)
            return allImagesId.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = null;
        ImageView imageView = null;
        if (convertView == null) {
            grid = inflater.inflate(R.layout.doctors_tab, null);
            textView = (TextView) grid.findViewById(R.id.tvName);
            imageView = (ImageView)grid.findViewById(R.id.ivDoctor);
            //TypedArray imgs = context.getResources().obtainTypedArray(R.array.all_images);
        } else {
            grid = convertView;
            textView = (TextView) grid.findViewById(R.id.tvName);
            imageView = (ImageView)grid.findViewById(R.id.ivDoctor);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),allImagesId.get(i));
        //imgs.recycle();
        if(bitmap != null) {
            Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
            //imageView.setImageDrawable(allImagesId.get(i));
        /*if(textView != null)
            textView.setText(allNamesId.get(i));
        if(imageView != null)*/
            textView.setText(allNamesId.get(i));
            imageView.setImageBitmap(circularBitmap);
        }
        return grid;
    }
}
