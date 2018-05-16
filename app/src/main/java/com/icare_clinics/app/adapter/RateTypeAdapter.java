package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icare_clinics.app.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Baliram.Kumar on 05-05-2017.
 */

public class RateTypeAdapter extends BaseAdapter {

    Context mCtx;
    int[] mImg;
    private String[] arrTime;
    LayoutInflater layoutInflater;
    public static int selectedPosition;
    public RateTypeAdapter(Context context, String[] arrTime) {
        this.mCtx = context;
        this.arrTime = arrTime;
        // rgp = new RadioGroup(context);
        layoutInflater = (LayoutInflater) mCtx.getSystemService(LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        if(arrTime!=null&&arrTime.length>0)
            return arrTime.length;
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_rate_type_item, null);
            holder = new Holder();

            holder.tvTime= (TextView) view.findViewById(R.id.tvItem);
           /* if ( holder.radioButton != null) {
                holder.radioButton.setChecked(true);

            }*/
            view.setTag(holder);

        } else {

            holder = (Holder) view.getTag();
        }
        if(position==selectedPosition){
            holder.tvTime.setSelected(true);

        }else{
            holder.tvTime.setSelected(false);
        }
        holder.tvTime.setText(arrTime[position]);
        return view;
    }
    static class Holder {
        private TextView tvTime;
    }
    public void refresh(String[] arrTime){
        this.arrTime = arrTime;
        notifyDataSetChanged();
    }
}
