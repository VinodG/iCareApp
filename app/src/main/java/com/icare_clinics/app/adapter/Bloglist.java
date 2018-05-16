package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.BlogDetailActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.BlogDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satish.Babu on 1/2/2017.
 */

public class Bloglist extends BaseAdapter {

        public int img[] = {R.drawable.blogimage1, R.drawable.blogimage2, R.drawable.blogimage1, R.drawable.blogimage2};
        public String[] town = {"Hyderabad", "Delhi", "Mumbai", "Banglore"};
        public String[] address = {"white field\nKothaguda\nHyderabad", "DLF Building\nGurugram\nNCR", "Taj\nNavi Mumbai\nMumbai", "Whitefield\nElectronics city\nBanglore"};
        private List<BlogDO> blogDOs;

        private Intent intent;

        private LayoutInflater inflater;
        private Context context;

        public Bloglist(Context context)
        {
            this.context=context;
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final Holder holder;
            if(view==null) {
                view = inflater.inflate(R.layout.blogitem, null);
                holder = new Holder();
                holder.tvtown = (TextView)view. findViewById(R.id.tv_blogitem);
                holder.tvaddress = (TextView)view.findViewById(R.id.textView);
                holder.blogimage = (ImageView) view.findViewById(R.id.iv_blogitem);
                view.setTag(holder);

            }

            else {
                holder=(Holder)view.getTag();
            }

            holder.tvtown.setText(town[i]);
            holder.tvaddress.setText(address[i]);
            holder.blogimage.setImageResource(img[i]);
            holder.tvtown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent=new Intent(context,BlogDetailActivity.class);
                    context.startActivity(intent);
                }
            });


            holder.tvaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent=new Intent(context,BlogDetailActivity.class);
                    context.startActivity(intent);
                }
            });

            holder.blogimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     intent=new Intent(context,BlogDetailActivity.class);
                     context.startActivity(intent);
                }
            });

            return view;
        }


        static class Holder
        {
            private TextView tvtown,tvaddress;
            private ImageView blogimage;
        }

    public void refresh(ArrayList<BlogDO> blogDOs)
    {
        this.blogDOs=blogDOs;
        notifyDataSetChanged();
    }

}
