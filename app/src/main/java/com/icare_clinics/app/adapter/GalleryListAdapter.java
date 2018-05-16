package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.GalleryDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.List;

/**
 * Sample adapter implementation extending from AsymmetricGridViewAdapter<GalleryDO> This is the
 * easiest way to get started.
 */
public class GalleryListAdapter extends ArrayAdapter<GalleryDO> implements GalleryAdapter {

  private final LayoutInflater layoutInflater;
  private Context context;

  public GalleryListAdapter(Context context, List<GalleryDO> items) {
    super(context, 0, items);
    this.context = context;
    layoutInflater = LayoutInflater.from(context);
  }

  public GalleryListAdapter(Context context) {
    super(context, 0);
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView,  ViewGroup parent) {
    View v;

    GalleryDO item = getItem(position);
    boolean isRegular = getItemViewType(position) == 0;

    if (convertView == null) {
      v = layoutInflater.inflate(R.layout.gallery_cell, parent, false);
    } else {
      v = convertView;
    }

    //Picasso.with(context).load(ServiceUrls.MAIN_URL + item.image)
    if(!StringUtils.isEmpty(item.image)){
      /*Picasso.with(context).load(ServiceUrls.IMAGE_BASE_URL + item.image)
              .error(R.drawable.no_image)
              .placeholder(R.drawable.loader)
              .into((ImageView)v);*/
    }else{
      ( (ImageView)v).setImageResource(R.drawable.clinics);
     // ( (ImageView)v).setImageResource(R.drawable.no_image);
    }


    return v;
  }

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public int getItemViewType(int position) {
    return position % 2 == 0 ? 1 : 0;
  }

  public void appendItems(List<GalleryDO> newItems) {
    addAll(newItems);
    notifyDataSetChanged();
  }

  public void setItems(List<GalleryDO> moreItems) {
    clear();
    appendItems(moreItems);
  }
}