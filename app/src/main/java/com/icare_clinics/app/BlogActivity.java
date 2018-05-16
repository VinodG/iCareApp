package com.icare_clinics.app;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icare_clinics.app.adapter.Bloglist;

/**
 * Created by Satish.Babu on 1/2/2017.
 */

public class BlogActivity extends BaseActivity {

    LinearLayout llmain;
    private ListView blogListView;
    private Bloglist bloglist;
    @Override
    public void initialise() {

        llmain = (LinearLayout) inflater.inflate(R.layout.bloglist, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.1f);
        llBody.addView(llmain,params);

        ivLogo.setVisibility(View.GONE);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.VISIBLE);

        tvTitle.setText("Blog");


        blogListView = (ListView) findViewById(R.id.lv_blog);
        bloglist=new Bloglist(BlogActivity.this);
        blogListView.setAdapter(bloglist);
        setStatusBarColor();


    }

    @Override
    public void initialiseControls() {


    }

    @Override
    public void loadData() {

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




