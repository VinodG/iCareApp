package com.icare_clinics.app.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.icare_clinics.app.PinnedHeaderExpListView;
import com.icare_clinics.app.PinnedHeaderWellness;
import com.icare_clinics.app.R;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.WellnessPackageDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WellnessPackagesFragmentNew extends Fragment {

    //    private ExpandableListView lvWellNess;
    private TextView tvNotFound;
    private HashMap<String, ArrayList<WellnessPackageDo>> arrCoverInsu;
    private Context context;
    //    private MyExpandableListAdapter mAdapter;
    private WellnessAdapter mAdapter;
    PinnedHeaderWellness lvWellNess;
    private int mPinnedHeaderBackgroundColor;
    private int mPinnedHeaderTextColor;
    private boolean isExpand=false;
    private boolean isOpen=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View view = inflater.inflate(R.layout.wellness_layout, container, false);
        lvWellNess = (PinnedHeaderWellness) view.findViewById(R.id.list);
        tvNotFound = (TextView) view.findViewById(R.id.tvNotFound);
        Bundle insuCoverBundle = getArguments();
        arrCoverInsu = (HashMap<String, ArrayList<WellnessPackageDo>>)insuCoverBundle.getSerializable("Wellness");


//        mPinnedHeaderBackgroundColor = getResources().getColor(R.color.white);
        mPinnedHeaderTextColor = getResources().getColor(R.color.statusbarblue);

        if(arrCoverInsu != null && arrCoverInsu.size() >0) {
            lvWellNess.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
//            mAdapter=new MyExpandableListAdapter(context, arrCoverInsu);
            mAdapter=new WellnessAdapter(context, arrCoverInsu);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            lvWellNess.setChildDivider(getResources().getDrawable(R.drawable.custom_border));
            lvWellNess.setAdapter(mAdapter);

            lvWellNess.setGroupIndicator(null);
            View h = LayoutInflater.from(context).inflate(R.layout.health_packages_header, (ViewGroup) view.findViewById(R.id.root), false);
            lvWellNess.setPinnedHeaderView(h);
            lvWellNess.setOnScrollListener((AbsListView.OnScrollListener) mAdapter);
            lvWellNess.setDividerHeight(0);
            lvWellNess.expandGroup(0);

        }else{
            lvWellNess.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }

        /*******************For opening only one group at a time**************************/
        lvWellNess.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = mAdapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        lvWellNess.collapseGroup(i);
                    }
                }
            }
        });
        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




    public class WellnessAdapter extends BaseExpandableListAdapter implements PinnedHeaderWellness.PinnedHeaderAdapter, AbsListView.OnScrollListener {

        private Context _context;
        // header titles
        // child data in format of header title, child title
        private HashMap<String, ArrayList<WellnessPackageDo>> wellness;
        private List<String> grps;

        public WellnessAdapter(Context context, HashMap<String, ArrayList<WellnessPackageDo>> wellness) {
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
                isOpen=true;
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
        public void configurePinnedHeader(View v, int position, int alpha,int state) {
            TextView header = (TextView) v;
            final String title = (String) getGroup(position);

            Drawable drawable=getResources().getDrawable(R.drawable.go_backword);
            if (state==1){
                header.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
            }else {
                Drawable draw=getResources().getDrawable(R.drawable.go_forword);
                header.setCompoundDrawablesWithIntrinsicBounds(null,null,draw,null);
            }

            header.setTypeface(AppConstants.UbuntuRegular);
            header.setTypeface(null, Typeface.BOLD);

            if (title.equals("1")){
                header.setText("Basic Wellnes Plan : @ AED 799");
            }
            if (title.equals("2")){
                header.setText("Executive Wellness Plan : @ AED 2199");
            }
            if (title.equals("3")){
                header.setText("Wellnes Health Plan : @ AED 3099");
            }

            if (alpha == 255) {
                header.setBackground(getResources().getDrawable(R.drawable.edit_boarder));
                header.setTextColor(mPinnedHeaderTextColor);
            } else {
                header.setBackground(getResources().getDrawable(R.drawable.edit_boarder));
               /* header.setBackgroundColor(Color.argb(alpha,
                        Color.red(mPinnedHeaderBackgroundColor),
                        Color.green(mPinnedHeaderBackgroundColor),
                        Color.blue(mPinnedHeaderBackgroundColor)));*/
                header.setTextColor(Color.argb(alpha,
                        Color.red(mPinnedHeaderTextColor),
                        Color.green(mPinnedHeaderTextColor),
                        Color.blue(mPinnedHeaderTextColor)));
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (absListView instanceof PinnedHeaderWellness) {
                ((PinnedHeaderWellness) absListView).configureHeaderView(firstVisibleItem);
            }
        }
    }
}
