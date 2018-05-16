package com.icare_clinics.app.adapter;

import android.widget.ListAdapter;

import com.icare_clinics.app.dataobject.GalleryDO;

import java.util.List;

/**
 * Created by Jayasai on 7/19/2016.
 */
public interface GalleryAdapter extends ListAdapter {
    void appendItems(List<GalleryDO> newItems);
    void setItems(List<GalleryDO> moreItems);
}
