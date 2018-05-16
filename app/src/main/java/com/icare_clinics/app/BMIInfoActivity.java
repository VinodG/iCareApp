package com.icare_clinics.app;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.icare_clinics.app.common.AppConstants;

import static com.icare_clinics.app.R.id.webView;

/**
 * Created by Sreekanth.P on 22-07-2017.
 */

public class BMIInfoActivity extends BaseActivity {
    private LinearLayout llMain;
    private WebView webViewInfo;

    @Override
    public void initialise() {
        llMain=(LinearLayout) inflater.inflate(R.layout.activity_bmi_info,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        llBody.addView(llMain,params);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        iv_fab.setVisibility(View.GONE);
        tvTitle.setText("Info");
        setStatusBarColor();
        initialiseControls();
        loadData();
    }

    @Override
    public void initialiseControls() {
        webViewInfo = (WebView)findViewById(R.id.webViewInfo);
      /*  webViewInfo.getSettings().setLoadsImagesAutomatically(true);
        webViewInfo.getSettings().setJavaScriptEnabled(true);
        webViewInfo.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);*/
        webViewInfo.getSettings().setJavaScriptEnabled(true);
        webViewInfo.getSettings().setLoadWithOverviewMode(true);
        webViewInfo.getSettings().setUseWideViewPort(true);
        webViewInfo.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewInfo.setScrollbarFadingEnabled(true);
        webViewInfo.setWebViewClient(new WebClient());
    }

    @Override
    public void loadData() {
        String url= AppConstants.IMAGE_BMI_URL;
        webViewInfo.loadUrl(url);
    }

    private class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            showLoader("loading....");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideLoader();
            super.onPageFinished(view, url);
        }
    }
}
