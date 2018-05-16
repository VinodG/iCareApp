package com.icare_clinics.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.WellnessPackageDo;

/**
 * Created by WINIT on 15-Mar-17.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<WellnessPackageDo>> wellness;
    private List<String> grps;

    public MyExpandableListAdapter(Context context, HashMap<String, ArrayList<WellnessPackageDo>> wellness) {
        this._context = context;
        this.wellness = wellness;
        this.grps = new ArrayList<>();
        this.grps.addAll(this.wellness.keySet());
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.wellness.get(this.grps.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final WellnessPackageDo childText = (WellnessPackageDo) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.health_pkg_child_item_layout, null);
        }

        int size=wellness.get(this.grps.get(groupPosition)).size();
        TextView txtListChild = (TextView) convertView .findViewById(R.id.tvTitle);
        ImageView ivLine=(ImageView)convertView.findViewById(R.id.ivLine);
        txtListChild.setTypeface(AppConstants.UbuntuRegular);
        txtListChild.setText(childText.name);
        if(size-1==childPosition){
            ivLine.setVisibility(View.VISIBLE);
        }else{
            ivLine.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.wellness.get(this.grps.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.grps.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.wellness.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.health_pkg_grp_item_layout, null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvTitle);
        lblListHeader.setTypeface(AppConstants.UbuntuRegular);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        if(headerTitle.equals("1"))
            lblListHeader.setText("Basic Wellnes Plan : @ AED 799");
        if(headerTitle.equals("2"))
            lblListHeader.setText("Executive Wellness Plan : @ AED 2199");
        if(headerTitle.equals("3"))
            lblListHeader.setText("Wellnes Health Plan : @ AED 3099");

        if(isExpanded) {
            ((ImageView)convertView.findViewById(R.id.ivArr)).setImageResource(R.drawable.go_backword);
        }
        else {
            ((ImageView)convertView.findViewById(R.id.ivArr)).setImageResource(R.drawable.go_forword);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

