package com.icare_clinics.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;

import java.io.InputStream;

public class PlayingVideoActivity extends BaseActivity {

    private VideoView vvGal;
    private Intent intent;
    private LinearLayout llVideo;
    DisplayMetrics dm;
    @Override
    public void initialise() {
        // tvTitle.setVisibility(View.VISIBLE);
        // tvCancel.setVisibility(View.VISIBLE);
        // tvTitle.setText(R.string.gallery);
        // tvCancel.setText(R.string.done);
        toolbar.setVisibility(View.GONE);
        llVideo = (LinearLayout) inflater.inflate(R.layout.activity_playing_video, null);
        llBody.addView(llVideo,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        vvGal=(VideoView)findViewById(R.id.vvGal);
        vvGal.setMediaController(new MediaController(this));
        iv_fab.setVisibility(View.GONE);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public void initialiseControls() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        vvGal.setMinimumWidth(width);
        vvGal.setMinimumHeight(height);
        vvGal.setMediaController(new MediaController(this));
        String url = getIntent().getStringExtra("URI");
       // vvGal.setVideoURI(Uri.parse(ServiceUrls.MAIN_URL+url));
        vvGal.setVideoURI(Uri.parse(url));
        vvGal.start();
        vvGal.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                hideLoader();
            }
        });
        vvGal.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    public void loadData() {
        showLoader(getString(R.string.Loading_Data));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = getIntent().getStringExtra("URI");
                if (!StringUtils.isEmpty(url)) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }else{
                    showCustomDialog(PlayingVideoActivity.this,"","Unable to play..",getString(R.string.OK),"","");
                }
            }
        }).start();
    }

    public void playVideo(InputStream inputStream){

    }

    @Override
    public void dataRetrieved(ResponseDO response) {

        /*if (response.method != null && (response.method == ServiceMethods.WS_DOWNLOAD &&response.data != null)) {
            if (!response.isError && response.data instanceof InputStream) {
                if (!response.isError && response.data instanceof InputStream) {

                }
            }
        }*/

      /*  BufferedInputStream bis = new BufferedInputStream(is);
        FileOutputStream fos = new FileOutputStream("/sdcard/Response.xml");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte byt[] = new byte[1024];
        int noBytes;

        while ((noBytes = bis.read(byt)) != -1)
            bos.write(byt, 0, noBytes);

        bos.flush();
        bos.close();
        fos.close();
        bis.close();*/
    }
}
