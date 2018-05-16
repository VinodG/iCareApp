package com.icare_clinics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Baliram.Kumar on 21-03-2017.
 */

public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, Integer> expandableListDetail;
    public CustomExpandableAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, Integer> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
       return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
    }
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);
        int img=(int)getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_profile_group, null);
        }
        TextView tvTittle=(TextView) convertView.findViewById(R.id.tvTittle);
        ImageView iconProfile=(ImageView) convertView.findViewById(R.id.iconProfile);
        ImageView ivEditProfile=(ImageView) convertView.findViewById(R.id.ivEditProfile);
        tvTittle.setText(listTitle);
        iconProfile.setImageResource(img);
        return null;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
