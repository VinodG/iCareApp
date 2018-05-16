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

import com.icare_clinics.app.PinnedHeaderAnteNantal;
import com.icare_clinics.app.PinnedHeaderExpListView;
import com.icare_clinics.app.PinnedHeaderWellness;
import com.icare_clinics.app.R;
import com.icare_clinics.app.adapter.AnteNatalExpandableListAdapter;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.VaccinationDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sreekanth.P on 01-09-2017.
 */

public class AnteNatalPackagesFragmentNew extends Fragment{

//    private ExpandableListView lvWellNess;
    private TextView tvNotFound;
    private HashMap<String, ArrayList<VaccinationDo>> arrCoverInsu, vaccinations;
    private Context context;
//    private AnteNatalExpandableListAdapter mAdapter;

    private AnteNantalAdapter mAdapter;
    PinnedHeaderAnteNantal lvWellNess;
    private int mPinnedHeaderBackgroundColor;
    private int mPinnedHeaderTextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View view = inflater.inflate(R.layout.antenanatal_layout, container, false);
        lvWellNess = (PinnedHeaderAnteNantal) view.findViewById(R.id.list);
        tvNotFound = (TextView) view.findViewById(R.id.tvNotFound);

        mPinnedHeaderTextColor = getResources().getColor(R.color.statusbarblue);

        Bundle insuCoverBundle = getArguments();
        if(insuCoverBundle.containsKey("Vaccination"))
            vaccinations = (HashMap<String, ArrayList<VaccinationDo>>) insuCoverBundle.getSerializable("Vaccination");
        else if(insuCoverBundle.containsKey("AnteNatal"))
            arrCoverInsu = (HashMap<String, ArrayList<VaccinationDo>>) insuCoverBundle.getSerializable("AnteNatal");

        if(arrCoverInsu != null) {
            if (arrCoverInsu != null && arrCoverInsu.size() > 0) {
                lvWellNess.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
//                mAdapter = new AnteNatalExpandableListAdapter(context, arrCoverInsu);
                mAdapter = new AnteNantalAdapter(context, arrCoverInsu);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
                lvWellNess.setAdapter(mAdapter);

                lvWellNess.setGroupIndicator(null);
                View h = LayoutInflater.from(context).inflate(R.layout.health_packages_header, (ViewGroup) view.findViewById(R.id.root), false);
                lvWellNess.setPinnedHeaderView(h);
                lvWellNess.setOnScrollListener((AbsListView.OnScrollListener) mAdapter);
                lvWellNess.setDividerHeight(0);

                lvWellNess.expandGroup(0);

            } else {
                lvWellNess.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }

        }
        else if(vaccinations != null) {
            if (vaccinations != null && vaccinations.size() > 0) {
                lvWellNess.setVisibility(View.VISIBLE);
                tvNotFound.setVisibility(View.GONE);
                mAdapter = new AnteNantalAdapter(context, vaccinations);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
                lvWellNess.setAdapter(mAdapter);

                lvWellNess.setGroupIndicator(null);
                View h = LayoutInflater.from(context).inflate(R.layout.health_packages_header, (ViewGroup) view.findViewById(R.id.root), false);
                lvWellNess.setPinnedHeaderView(h);
                lvWellNess.setOnScrollListener((AbsListView.OnScrollListener) mAdapter);
                lvWellNess.setDividerHeight(0);

                lvWellNess.expandGroup(0);
            } else {
                lvWellNess.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
            }
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


    public class AnteNantalAdapter extends BaseExpandableListAdapter implements PinnedHeaderAnteNantal.PinnedHeaderAdapter, AbsListView.OnScrollListener {

        private Context _context;
        // header titles
        // child data in format of header title, child title
        private HashMap<String, ArrayList<VaccinationDo>> wellness;
        private List<String> grps;

        public AnteNantalAdapter(Context context, HashMap<String, ArrayList<VaccinationDo>> wellness) {
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

            final VaccinationDo childText = (VaccinationDo) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.health_pkg_child_item_layout, null);
            }
            int size=wellness.get(this.grps.get(groupPosition)).size();
            TextView txtListChild = (TextView) convertView .findViewById(R.id.tvTitle);
            ImageView ivLine=(ImageView)convertView.findViewById(R.id.ivLine);
            txtListChild.setText(childText.packageVac);
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
            lblListHeader.setTypeface(null, Typeface.BOLD);


            if(headerTitle.equals("9"))
                lblListHeader.setText("Antenatal (Upto 32 Weeks): @ AED 3399");
            if(headerTitle.equals("10"))
                lblListHeader.setText("Antenatal Low Risk: @ AED  3999");
            if(headerTitle.equals("11"))
                lblListHeader.setText("Antenatal High Risk: @ AED 4999");

            if(headerTitle.equals("1"))
                lblListHeader.setText("6 Months Package: @ AED 2399");
            if(headerTitle.equals("2"))
                lblListHeader.setText("12 Months Package: @ AED 2699");
            if(headerTitle.equals("3"))
                lblListHeader.setText("18 Months Package: @ AED 3499");

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
        public void configurePinnedHeader(View v, int position, int alpha, int state) {
            TextView header = (TextView) v;
            final String title = (String) getGroup(position);

            header.setTypeface(AppConstants.UbuntuRegular);
            header.setTypeface(null, Typeface.BOLD);

            if (state==1){
                Drawable drawable=getResources().getDrawable(R.drawable.go_backword);
                header.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
            }else {
                Drawable draw=getResources().getDrawable(R.drawable.go_forword);
                header.setCompoundDrawablesWithIntrinsicBounds(null,null,draw,null);
            }

            if (title.equals("9")){
                header.setText("Antenatal (Upto 32 Weeks): @ AED 3399");
            }
            if (title.equals("10")){
                header.setText("Antenatal Low Risk: @ AED  3999");
            }
            if (title.equals("11")){
                header.setText("Antenatal High Risk: @ AED 4999");
            }

            if (title.equals("1")){
                header.setText("6 Months Package: @ AED 2399");
            }
            if (title.equals("2")){
                header.setText("12 Months Package: @ AED 2699");
            }
            if (title.equals("3")){
                header.setText("18 Months Package: @ AED 3499");
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
            if (absListView instanceof PinnedHeaderAnteNantal) {
                ((PinnedHeaderAnteNantal) absListView).configureHeaderView(firstVisibleItem);
            }
        }
    }
}
