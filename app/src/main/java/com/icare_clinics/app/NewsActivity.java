package com.icare_clinics.app;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.icare_clinics.app.adapter.NewsAdapter;
import com.icare_clinics.app.dataaccesslayer.ExtraDA;
import com.icare_clinics.app.dataobject.NewsDo;

import java.util.ArrayList;

public class NewsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //List<ClinicsDO> list = new ArrayList<>();
    private ArrayList<NewsDo> arrNews;
    LinearLayout lLMain;

    @Override
    public void initialise() {
        lLMain = (LinearLayout) inflater.inflate(R.layout.activity_news, null);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(lLMain, param);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setBackground(null);
        tvCancel.setVisibility(View.GONE);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setText("what's New");
        tvTitle.setTextColor(Color.WHITE);
        setStatusBarColor();

        iv_fab.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) lLMain.findViewById(R.id.rvNews);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NewsAdapter(NewsActivity.this,arrNews);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        loadData();
    }

    @Override
    public void initialiseControls() {

    }

    @Override
    public void loadData() {
        ExtraDA extraDA=new ExtraDA(NewsActivity.this);
        arrNews=extraDA.getNews();
        if (arrNews != null && arrNews.size() > 0)
            mAdapter.refresh(arrNews);

    }
}
