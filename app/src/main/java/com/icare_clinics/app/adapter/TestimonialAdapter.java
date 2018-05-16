package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 24-02-2017.
 */

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.ViewHolder> {
    private ArrayList<AboutDo> arrTestimonial;
    private LayoutInflater inflater;
    Context context;
    public TestimonialAdapter(Context context,ArrayList<AboutDo> arrTestimonial) {
        this.context = context;
        this.arrTestimonial = arrTestimonial;
    }

    @Override
    public TestimonialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_testimonial, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TestimonialAdapter.ViewHolder holder, int position) {
        final AboutDo aboutDo = arrTestimonial.get(position);
        holder.tvTittle.setText(aboutDo.title);
        holder.tvDescription.setText(StringUtils.fromHtml(aboutDo.description));
    }
    @Override
    public int getItemCount() {
        if (arrTestimonial != null)
            return arrTestimonial.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvDescription,tvTittle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTittle = (TextView) itemView.findViewById(R.id.tvTittle);
        }

    }

    public void refresh(ArrayList<AboutDo> arrTestimonial) {
        this.arrTestimonial = arrTestimonial;
        notifyDataSetChanged();
    }
}
