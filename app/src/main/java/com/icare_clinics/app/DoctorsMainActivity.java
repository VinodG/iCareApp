package com.icare_clinics.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.icare_clinics.app.adapter.ViewPagerAdapt;
import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataaccesslayer.DoctorDA;
import com.icare_clinics.app.dataaccesslayer.SpecialityDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.ItemDo;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.fragments.FragmentList;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sudheer.jampana on 1/2/2017.
 */

public class DoctorsMainActivity extends BaseActivity {

    static String SPECIALITY = "Speciality";
    static String LOCATION = "Location";

    TextView tvfilterItem, tvNumOfPages; // to show the current page numberivFilter
    LinearLayout llForFilter;

    //    ScrollView scrollViewFilter;
    private FilteredItemsAdapter filteredItemsAdapter;
    private RecyclerView rvForFilter;
    View viewToAddInLayout;
    static String KEY_VALUES[] = {SPECIALITY, LOCATION};

    ArrayList<ItemDo> listSpecilities = new ArrayList<ItemDo>();
    ArrayList<ItemDo> listLocations = new ArrayList<ItemDo>();
    private ArrayList<ClinicDO> arrClinic;
    ArrayList<String> listFilteringItems = new ArrayList<String>();
    public HashMap<String, ArrayList<ItemDo>> entireList = new HashMap<String, ArrayList<ItemDo>>();

    SlidingMenu slidingMenu;
    LinearLayout lLMain;
    private ArrayList<SpecializationDO> spcialistList;
    private ArrayList<String> arrLocation;

    private ArrayList<DoctorDo> arrDoctor;
    private TabLayout tabLayout,tabsForFilter;
    ViewPagerAdapt adapter;
    private ViewPager viewPager,viewPagerForFilter;

    @Override
    public void initialise() {

        lLMain = (LinearLayout) inflater.inflate(R.layout.doctros_main_activity , null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);
        rvForFilter = (RecyclerView) lLMain.findViewById(R.id.rvForFilter);

        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.doctors));
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setImageResource(R.drawable.filter);
        setStatusBarColor();

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tvNumOfPages = (TextView) findViewById(R.id.tvNumOfPages);
        tvfilterItem = (TextView) findViewById(R.id.tvfilterItem);
//        ivFilter = (ImageView) findViewById(R.id.ivFilter);
//        ivBackImage = (ImageView) findViewById(R.id.ivBackImage);
        llForFilter = (LinearLayout) findViewById(R.id.llForFilter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        viewToAddInLayout = (View) tvfilterItem;
        llForFilter.setVisibility(View.INVISIBLE);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapt(getSupportFragmentManager(), arrDoctor, DoctorsMainActivity.this,1);

        loadData();

        viewPager.setPageMargin((int) (-1 * px) / 3);
        Log.d("margin", "  : " + (px) + "  : ");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View view, float position) {
                float scaleFactor = 0.0f;

                if (position < -1) {
                    view.setAlpha(0);
                } else if (position <= 1) {
                    scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    view.setAlpha(scaleFactor);
                } else {
                    view.setAlpha(0);
                }
            }
        });
        viewPager.setAdapter(adapter);

        slidingMenu = new SlidingMenu(this);

        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindWidth(deviceWidth - 50);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        slidingMenu.setMenu(R.layout.sliding_menu);
        slidingMenu.setMenu(R.layout.sliding_menu_doctors);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SlidingMenuDynamicListView();
                SlidingMenuViewPager();
                slidingMenu.toggle();
            }
        });
    }

    private void updateBottom(final ArrayList<DoctorDo> arrDoctorTemp) {
        if (arrDoctorTemp != null)
        {

            LayoutInflater inflater = getLayoutInflater();
            if (tabLayout.getTabCount() > 0) {
                tabLayout.removeAllTabs();
            }
            for (int i = 0; i < arrDoctorTemp.size(); i++) {
                View tabItem = null;
                tabItem = (View) inflater.inflate(R.layout.tbitem, null);
                LinearLayout lltabImgBg=(LinearLayout)tabItem.findViewById(R.id.lltabImgBg);
                ImageView iv = ((ImageView) tabItem.findViewById(R.id.ivTabItem));/*.setImageResource(doctorBigImages[0]);*/

                if (i == 0) {
                    tvNumOfPages.setText("1 of " + arrDoctorTemp.size());
                    ((ImageView) (tabItem).findViewById(R.id.ivTabIndicator)).setVisibility(View.GONE);
                    lltabImgBg.setBackgroundResource(R.drawable.tab_background_selected);
                    viewPager.setCurrentItem(0);
                }
                String url = ServiceUrls.IMAGE_BASE_URL + arrDoctorTemp.get(i).photo;
                if (!StringUtils.isEmpty(arrDoctorTemp.get(i).photo)) {
                    setImageCircle(url, iv, R.drawable.docto2);
                }
                tabLayout.addTab(tabLayout.newTab().setCustomView(tabItem));
            }
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((LinearLayout)(tab.getCustomView().findViewById(R.id.lltabImgBg))).setBackgroundResource(R.drawable.tab_background_selected);
//                ((ImageView) (tab.getCustomView()).findViewById(R.id.ivTabIndicator)).setVisibility(View.GONE);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((LinearLayout)(tab.getCustomView().findViewById(R.id.lltabImgBg))).setBackgroundResource(R.drawable.tab_background_unselected);
//                ((ImageView) (tab.getCustomView()).findViewById(R.id.ivTabIndicator)).setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvNumOfPages.setText((position + 1) + " of " + arrDoctorTemp.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment  = new FragmentList();;
            Bundle bundle= new Bundle();
            switch(position)
            {
                case 0:
                    bundle.putString("FROM", "SPECIALITY");
                    break;

                case 1:
                    bundle.putString("FROM", "LOCATION");
                    break;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return KEY_VALUES[position].toString();
        }
    }

    private void SlidingMenuViewPager()
    {
        viewPagerForFilter = (ViewPager) findViewById(R.id.viewPagerForFilter);
        tabsForFilter = (TabLayout) findViewById(R.id.tabsForFilter);
        TextView tvSlidingMenuApply = (TextView) findViewById(R.id.tvSlidingMenuApply);
        TextView tvSlidingMenuCancel = (TextView) findViewById(R.id.tvSlidingMenuCancel);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerForFilter.setAdapter(viewPagerAdapter);
        tabsForFilter.setupWithViewPager(viewPagerForFilter);
        tabsForFilter.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabsForFilter.setTabGravity(TabLayout.GRAVITY_CENTER);

        viewPagerForFilter.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabsForFilter));
        tabsForFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerForFilter.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tvSlidingMenuApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)

            {
                rvForFilter.setVisibility(View.VISIBLE);
                slidingMenu.toggle();

                filteredItemsAdapter = new FilteredItemsAdapter(DoctorsMainActivity.this,entireList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(DoctorsMainActivity.this, 3);
                rvForFilter.setLayoutManager(mLayoutManager);
//        rvForFilter.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(7), true));
                rvForFilter.setItemAnimator(new DefaultItemAnimator());
                rvForFilter.setAdapter(filteredItemsAdapter);

                int  count_TAB = 0;
                for (int i = 0; i < entireList.size(); i++) {
                    String key = KEY_VALUES[i];
                    for (int j = 0; j < entireList.get(key).size(); j++) {
                        if (entireList.get(key).get(j).status == true ) {
                            count_TAB = count_TAB + 1;
                        }
                    }
                }

                if (count_TAB > 0) {
                    updateAdapter(listFilteringItems);
                } else {
                    Toast.makeText(DoctorsMainActivity.this, getString(R.string.you_have_not_choosen), Toast.LENGTH_SHORT).show();
                }
            }


        });

        tvSlidingMenuCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toggle();

            }
        });
    }

    private void clearListItemsInSlidingMenu(ArrayList<ItemDo> list, String str, MyArrayAdapter adapter, ListView lv) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).status = false;
        }
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    private void addItemsToLinearLayout(ArrayList<String> key) {
        Log.d("Apply button: ", "is clicked");
        if (key.size() < 1) {
            llForFilter.setVisibility(View.GONE);

        } else {
            llForFilter.setVisibility(View.VISIBLE);
        }
        ((ViewGroup) llForFilter).setMinimumHeight(llForFilter.getHeight());
        ((LinearLayout) llForFilter).removeAllViews();
        for (int i = 0; i < key.size(); i++) {
            LinearLayout layoutToBeAdded = (LinearLayout) getLayoutInflater().inflate(R.layout.filtered_items_with_x_image, null);
//            layoutToBeAdded.setId(i + textCountId);

            ImageView ivfiltered_x = (ImageView) layoutToBeAdded.findViewById(R.id.ivfiltered_x);
            TextView tvfilteredItem = (TextView) layoutToBeAdded.findViewById(R.id.tvfilteredItem);

//            ivfiltered_x.setId(i + textCountId);
//            tvfilteredItem.setId(i + textCountId);

            tvfilteredItem.setText(key.get(i));
            tvfilteredItem.setTextColor(i + getResources().getColor(R.color.gray));

            ivfiltered_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ViewGroup) ((ViewGroup) view.getParent()).getParent()).removeView((LinearLayout) view.getParent());
                    LinearLayout l = (LinearLayout) view.getParent();
                    TextView textV = (TextView) l.getChildAt(0);
                    String strToRemove = textV.getText().toString();
                    // removing item from filtered list and update adapter
                    for (int i = 0; i < listFilteringItems.size(); i++) {
                        if (listFilteringItems.get(i).equalsIgnoreCase(strToRemove)) {
                            listFilteringItems.remove(i);
                        }
                    }
                    if(listFilteringItems.size()==0)
                    {
                        rvForFilter.setVisibility(View.GONE);
                    }
                    updateAdapter(listFilteringItems);
//                    ((ViewGroup) ((ViewGroup) view.getParent()).getParent()).removeView((LinearLayout) view.getParent());
                }
            });

            ((LinearLayout) llForFilter).addView((LinearLayout) layoutToBeAdded);
        }
    }

    private void updateAdapter(ArrayList<String> listFilteringItems)
    {
        if(getFiltererDoctorsList(listFilteringItems)!= null && getFiltererDoctorsList(listFilteringItems).size()>0)
        {
            adapter = new ViewPagerAdapt(getSupportFragmentManager(), getFiltererDoctorsList(listFilteringItems), DoctorsMainActivity.this,1);
            updateBottom(getFiltererDoctorsList(listFilteringItems));
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);
            addItemsToLinearLayout(listFilteringItems);
            ((TextView)findViewById(R.id.tvListNotFound)).setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            tvNumOfPages.setVisibility(View.VISIBLE);
            tvNumOfPages.setText(  "1 of " + getFiltererDoctorsList(listFilteringItems).size());
            tabLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            ((TextView)findViewById(R.id.tvListNotFound)).setVisibility(View.VISIBLE);
            rvForFilter.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            tvNumOfPages.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }
    }

    public ArrayList<DoctorDo> getFiltererDoctorsList(ArrayList<String> filterItemsList) {
        return (new DoctorDA(this)).getFilteredDoctor(entireList);
    }
    @Override
    public void initialiseControls() {
    }
    @Override
    public void loadData() {

        spcialistList=new ArrayList<SpecializationDO>();
        arrLocation = new ArrayList<String>();
        showLoader("");
        DoctorDA doctorDA = new DoctorDA(DoctorsMainActivity.this);
        arrDoctor = doctorDA.getDoctor();
        if (arrDoctor != null && arrDoctor.size() > 0) {

            Set<DoctorDo> set=new HashSet<DoctorDo>(arrDoctor);
            arrDoctor=new ArrayList<DoctorDo>(set);

            adapter.refresh(arrDoctor);
            updateBottom(arrDoctor);
        }
        hideLoader();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SpecialityDA specialityDA = new SpecialityDA(DoctorsMainActivity.this);
                spcialistList=specialityDA.getSpeciality();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (spcialistList != null && spcialistList.size() > 0) {
                            for (int i = 0; i < spcialistList.size(); i++) {
                                listSpecilities.add(new ItemDo(spcialistList.get(i).name, false));
                            }
                            entireList.put(SPECIALITY, listSpecilities);
                        }
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ClinicsDA clinicsDA=new ClinicsDA(DoctorsMainActivity.this);
                arrClinic=clinicsDA.getClinics();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<arrClinic.size();i++)
                        {
                            arrLocation.add( arrClinic.get(i).description.toString());
                        }

                        if (arrLocation != null && arrLocation.size() > 0) {
                            int sizeOfLocationslist = arrLocation.size();
                            Log.d("locationsList Size", sizeOfLocationslist + "");
                            for (int i = 0; i < sizeOfLocationslist; i++) {
                                listLocations.add(new ItemDo(arrLocation.get(i), false));
                            }
                            entireList.put(LOCATION, listLocations);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void dataRetrieved(ResponseDO response) {
        super.dataRetrieved(response);
    }

    //sliding menu adapter
    public class MyArrayAdapter extends BaseAdapter {
        LayoutInflater inflater = null;
        Context context;
        ArrayList<ItemDo> list = new ArrayList<ItemDo>();

        MyArrayAdapter(Context context, ArrayList<ItemDo> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final CheckBox chk;
            final TextView tv;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View inflatedView = inflater.inflate(R.layout.list_item_with_checkbox, null);
            chk = (CheckBox) inflatedView.findViewById(R.id.chk);
            tv = (TextView) inflatedView.findViewById(R.id.tv);
            if (list.get(i).status == false) {
                chk.setButtonDrawable(R.drawable.uncheckedfilter);
            } else {
                chk.setButtonDrawable(R.drawable.checkedfilter);
            }

            chk.setChecked(list.get(i).getAStatus());
            tv.setText(list.get(i).getName());
            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(i).status == false) {
                        list.get(i).status = true;
                        chk.setChecked(true);
                        chk.setButtonDrawable(R.drawable.checkedfilter);
//                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    } else {
                        chk.setButtonDrawable(R.drawable.uncheckedfilter);
                        list.get(i).status = false;
                        chk.setChecked(false);
//                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    }
                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(i).status == false) {
                        list.get(i).status = true;
                        chk.setChecked(true);
                        chk.setButtonDrawable(R.drawable.checkedfilter);
//                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    } else {
                        chk.setButtonDrawable(R.drawable.uncheckedfilter);
                        list.get(i).status = false;
                        chk.setChecked(false);
//                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    }
                }
            });
//            Log.d("Position of GetView  ", i + " with checked values " + chk.isChecked());
            return inflatedView;
        }
    }

    private class FilteredItemsAdapter  extends    RecyclerView.Adapter<FilteredItemsAdapter.MyViewHolder>
    {
        Context context;
        ArrayList<String> listFilteringItems = new ArrayList<String>();

        public FilteredItemsAdapter(Context context,  HashMap<String, ArrayList<ItemDo>> entireList   )
        {
            this.context= context;
            this.listFilteringItems=getListFilteringItems( entireList );
        }
        public void refresh()
        {
            this.listFilteringItems=getListFilteringItems( entireList );
            updateAdapterNew(listFilteringItems);
            notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
        private void updateAdapterNew(ArrayList<String> listFilteringItems)
        {
            if(getFiltererDoctorsList(listFilteringItems)!= null && getFiltererDoctorsList(listFilteringItems).size()>0)
            {
                adapter = new ViewPagerAdapt(getSupportFragmentManager(), getFiltererDoctorsList(listFilteringItems), DoctorsMainActivity.this,1);
                updateBottom(getFiltererDoctorsList(listFilteringItems));
                adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);
//                addItemsToLinearLayout(listFilteringItems);
                ((TextView)findViewById(R.id.tvListNotFound)).setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                tvNumOfPages.setVisibility(View.VISIBLE);
                tvNumOfPages.setText(  "1 of " + getFiltererDoctorsList(listFilteringItems).size());
                tabLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                ((TextView)findViewById(R.id.tvListNotFound)).setVisibility(View.VISIBLE);
                rvForFilter.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                tvNumOfPages.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);

            }
        }

        private ArrayList<String> getListFilteringItems( HashMap<String, ArrayList<ItemDo>> entireList )
        {
            this.listFilteringItems.clear();
            for (int i = 0; i < entireList.size(); i++) {
                String key = KEY_VALUES[i];
                for (int j = 0; j < entireList.get(key).size(); j++) {
                    if (entireList.get(key).get(j).status == true  ) {
                        this.listFilteringItems.add(entireList.get(key).get(j).name);
                    }
                }
            }
            return  this.listFilteringItems;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtered_items_with_x_image, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FilteredItemsAdapter.MyViewHolder holder, int position)
        {
            holder.tvfilteredItem.setText( listFilteringItems.get(position));
            ((ImageView)holder.ivfiltered_x).setTag(R.id.tvfilteredItem, listFilteringItems.get(position).toString());
            holder.ivfiltered_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String keyValue =  ((ImageView)v).getTag(R.id.tvfilteredItem).toString();


                    for (int i = 0; i < entireList.size(); i++)
                    {
                        String key = KEY_VALUES[i];
                        for (int j = 0; j < entireList.get(key).size(); j++)
                        {

                            if ((entireList.get(key).get(j).name).toString().equalsIgnoreCase(keyValue) )
                            {
                                entireList.get(key).get(j).status = false;
                            }
                        }
                    }

                    refresh();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            if (listFilteringItems != null && listFilteringItems.size() > 0)
                return listFilteringItems.size();
            else
                return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView tvfilteredItem;
            ImageView ivfiltered_x;
            public MyViewHolder(View itemView)
            {
                super(itemView);
                tvfilteredItem = (TextView)itemView.findViewById(R.id.tvfilteredItem);
                ivfiltered_x = (ImageView) itemView.findViewById(R.id.ivfiltered_x);
            }
        }
    }
}

