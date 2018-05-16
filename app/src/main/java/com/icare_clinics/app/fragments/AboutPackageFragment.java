package com.icare_clinics.app.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.VaccinationDo;

import java.util.ArrayList;
import java.util.HashMap;

import static com.icare_clinics.app.R.id.lvWellNess;

/**
 * Created by WINIT on 30-04-2017.
 */

public class AboutPackageFragment extends Fragment{
    ExpandableListView elv;
    TextView tvNotFound;
    ExpandableListAdapter adapter;
    HashMap<String,ArrayList<String>> hmAboutPackages = new HashMap<String,ArrayList<String>>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_package_fragment, container, false);
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        tvNotFound = (TextView) view.findViewById(R.id.tvNotFound);
        ArrayList<String> list = new ArrayList<String>();
        list.add(getString(R.string.icare_health_packages));
//        ArrayList<String> list2 = new ArrayList<String>();
//        list2.add("ONE child item");
        hmAboutPackages.put("iCare Health Packages", list);
//        hmAboutPackages.put("GROUP2", list2);
        adapter = new ExpandableListAdapter(getActivity(),hmAboutPackages);
        elv.setAdapter(adapter);
        elv.expandGroup(0);
        return view;
    }
    public class  ExpandableListAdapter extends BaseExpandableListAdapter
    {
        ArrayList<String> listGroup = new ArrayList<String>();
        HashMap<String,ArrayList<String>> hmAboutPackages= new HashMap<String,ArrayList<String>>();
        Context context;
        public ExpandableListAdapter(Context context , HashMap<String,ArrayList<String>> hmAboutPackages )
        {
            this.context= context;
            this.hmAboutPackages =hmAboutPackages;
            listGroup.addAll(hmAboutPackages.keySet());//addall(hmAboutPackages.keySet());
        }



        @Override
        public int getGroupCount()
        {
            return listGroup.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return  hmAboutPackages.get(listGroup.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return hmAboutPackages.get(groupPosition);

        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return hmAboutPackages.get(listGroup.get(groupPosition).toString()).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.health_pkg_grp_item_layout, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvTitle);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(listGroup.get(groupPosition).toString());

            if(isExpanded) {
                ((ImageView)convertView.findViewById(R.id.ivArr)).setImageResource(R.drawable.go_backword);
            }
            else {
                ((ImageView)convertView.findViewById(R.id.ivArr)).setImageResource(R.drawable.go_forword);
            }
            return  convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.health_pkg_child_item_layout, null);
            }

            TextView tvTitle = (TextView) convertView .findViewById(R.id.tvTitle);
            ImageView ivLine=(ImageView) convertView.findViewById(R.id.ivLine);
            tvTitle.setText(hmAboutPackages.get(listGroup.get(groupPosition)).get(childPosition).toString());

            if(hmAboutPackages.size()-1==childPosition){
                ivLine.setVisibility(View.VISIBLE);
            }else{
                ivLine.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
