package com.icare_clinics.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 25-02-2017.
 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<NewsDo> arrNews;
    private LayoutInflater inflater;
    Context context;
    public NewsAdapter(Context context,ArrayList<NewsDo> arrNews) {
        this.context = context;
        this.arrNews = arrNews;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsDo newsDo=arrNews.get(position);
        holder.tvTittleNews.setText(newsDo.title);
        holder.tvDescriptionNews.setText(StringUtils.fromHtml(newsDo.description));
        //holder.ivNews
        if (!StringUtils.isEmpty(newsDo.image)) {
            Picasso.with(context)
                    .load(ServiceUrls.IMAGE_BASE_URL + newsDo.image)
                    .into(holder.ivNews);
        }else{
            //use image not avaiable
        }
    }

    @Override
    public int getItemCount() {
        if (arrNews != null)
            return arrNews.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvDescriptionNews,tvTittleNews;
        ImageView ivNews;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescriptionNews = (TextView) itemView.findViewById(R.id.tvDescriptionNews);
            tvTittleNews = (TextView) itemView.findViewById(R.id.tvTittleNews);
            ivNews = (ImageView) itemView.findViewById(R.id.ivNews);
        }

    }

    public void refresh(ArrayList<NewsDo> arrNews) {
        this.arrNews = arrNews;
        notifyDataSetChanged();
    }
}
