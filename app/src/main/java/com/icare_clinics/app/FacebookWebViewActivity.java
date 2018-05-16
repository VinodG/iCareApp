package com.icare_clinics.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.icare_clinics.app.utilities.StringUtils;

public class FacebookWebViewActivity extends BaseActivity{

    LinearLayout facebookWebViewActivity;
    WebView wv1;
    String url;
    Activity activity ;
    @Override
    public void initialise() {
        facebookWebViewActivity = (LinearLayout) inflater.inflate(R.layout.activity_facebook_web_view, null);
        llBody.addView(facebookWebViewActivity, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        toolbar.setVisibility(View.GONE);
        ivLogo.setVisibility(View.VISIBLE);
        // llBack.setVisibility(View.VISIBLE);
        /*int maxSize= (int) getResources().getDimension(R.dimen.logo_width);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap converetdImage = getResizedBitmap(largeIcon, maxSize);
        ivLogo.setImageBitmap(converetdImage);*/
        setTypeFaceNormal(facebookWebViewActivity);
        setStatusBarColor();
        initialiseControls();
        loadData();
    }


    @Override
    public void initialiseControls() {
        wv1 = (WebView)findViewById(R.id.webViewFacebook);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebViewClient(new WebClient());

    }

    @Override
    public void loadData() {
        if(getIntent().hasExtra("url") && !StringUtils.isEmpty(getIntent().getStringExtra("url")))
        {
            url = getIntent().getStringExtra("url");
            wv1.loadUrl(url);
        }
    }

    private class WebClient extends WebViewClient{

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
