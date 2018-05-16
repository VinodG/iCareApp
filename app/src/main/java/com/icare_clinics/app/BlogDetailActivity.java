package com.icare_clinics.app;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Satish.Babu on 1/4/2017.
 */

public class BlogDetailActivity extends BaseActivity {

    private ScrollView llmain;
    private ImageView backarrow;

    @Override
    public void initialise() {

        llmain=(ScrollView)inflater.inflate(R.layout.blog,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        ivCancel.setVisibility(View.GONE);

        initialiseControls();


        llBody.addView(llmain);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

       // setStatusBarColor();
    }

    @Override
    public void initialiseControls() {

        backarrow=(ImageView)llmain.findViewById(R.id.iv_backarrow);

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
