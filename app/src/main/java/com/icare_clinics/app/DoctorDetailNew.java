package com.icare_clinics.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

//import static com.icare_clinics.app.R.id.collapsingToolbar;
import static com.icare_clinics.app.R.id.collapsingToolbar;
import static com.icare_clinics.app.R.id.tvLocation;

public class DoctorDetailNew extends BaseActivity  implements AppBarLayout.OnOffsetChangedListener {

    CollapsingToolbarLayout     collapsingToolbarLayout;

    //    private ScrollView llMain;
    private ViewGroup llMain;
    private DoctorDo doctorDo;
    private ImageView ivDoctor,iv_fab_appoint,iv_fab_call,ivCircular;
    private TextView drName,tvDegree,tvAddress,tvDetails,tvDate,tvTime;
    private Button btnRequestAppo;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.6f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer,lltitle,lltoolbar;
    private TextView mTitle,tvName,title;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    @Override
    public void initialise() {
        llMain = (ViewGroup) inflater.inflate(R.layout.activity_doctor_details_new, null);
        llMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        llBody.addView(llMain);
//        toolbar.setVisibility(View.GONE);
        doctorDo = (DoctorDo) getIntent().getSerializableExtra("doctorDO");
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Doctors Details");
        ivBack.setVisibility(View.VISIBLE);
        llBack.setVisibility(View.VISIBLE);
//        ivBack.setImageResource(R.drawable.back_white);
        iv_fab.setVisibility(View.GONE);
        setStatusBarColor();
        initialiseControls();

        mAppBarLayout.addOnOffsetChangedListener(this);

        //mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(lltitle, 0, View.INVISIBLE);

    }

    @Override
    public void initialiseControls() {
//        drName= (TextView) llMain.findViewById(R.id.drName);
//        tvDegree= (TextView) llMain.findViewById(R.id.tvDegree);
//        tvAddress= (TextView) llMain.findViewById(R.id.tvAddress);
        tvDate= (TextView) llMain.findViewById(R.id.tvDate);
        tvTime= (TextView) llMain.findViewById(R.id.tvTime);
        tvDetails= (TextView) llMain.findViewById(R.id.tvDetails);
        ivDoctor= (ImageView) llMain.findViewById(R.id.ivDoctor);
        ivCircular= (ImageView) llMain.findViewById(R.id.ivCircular);
//        btnRequestAppo= (Button) llMain.findViewById(R.id.btnRequestAppo);
        iv_fab_appoint=(ImageView) llMain.findViewById(R.id.iv_fab_appoint);
        iv_fab_call=(ImageView)llMain.findViewById(R.id.iv_fab_call);
        collapsingToolbarLayout=(CollapsingToolbarLayout) llMain.findViewById(collapsingToolbar);


        mToolbar        = (Toolbar) findViewById(R.id.toolbarNew);
        drName          = (TextView) findViewById(R.id.drName1);
        tvDegree          = (TextView) findViewById(R.id.tvDegree1);
        tvAddress          = (TextView) findViewById(R.id.tvAddress1);
        mTitleContainer = (LinearLayout) findViewById(R.id.llMainTitle);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.appbar);
        lltitle   = (LinearLayout) findViewById(R.id.lltitle);
        lltoolbar   = (LinearLayout) findViewById(R.id.lltoolbar);

        imageurl= ServiceUrls.IMAGE_BASE_URL+doctorDo.photo;
//        drName.setTypeface(AppConstants.UbuntuRegular);
//        tvDegree.setTypeface(AppConstants.UbuntuRegular);
//        tvAddress.setTypeface(AppConstants.UbuntuRegular);

        if(doctorDo!=null){
            AppConstants.selectedDoctorDo=doctorDo;
            drName.setText(doctorDo.name);
            tvDegree.setText(doctorDo.qualification);
            tvAddress.setText(doctorDo.location);
            tvDetails.setText(StringUtils.fromHtml(doctorDo.details));
            if(!StringUtils.isEmpty(imageurl)){
                Picasso.with(DoctorDetailNew.this).load(imageurl)
                        .into(ivDoctor);
                setImageCircle(imageurl, ivCircular, R.drawable.docto2);
            }else{
                //image not found
            }
        }
     /*   btnRequestAppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorDetail.this,DoctorsAppointmentForm.class);
                startActivity(intent);
            }
        });*/

        iv_fab_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoardActivity.this,"clicked",Toast.LENGTH_LONG).show();

                BottomSheetDialogFragment bottomSheetDialogFragment = new SelectDoctorAppointmentBottomFragment();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());

            }
        });
        iv_fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1800 iCARE(42273)"));
                    if (ActivityCompat.checkSelfPermission(DoctorDetailNew.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    public DoctorDo getDoctorDO(){
        return doctorDo;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        Log.e("Percentage ","    :  "+percentage+"%");
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }
    private void handleToolbarTitleVisibility(float percentage)
    {
        if(percentage>=0.60) {
            lltitle.setBackgroundColor(getResources().getColor(R.color.white));
            lltitle.setVisibility(View.VISIBLE);
            ivCircular.setVisibility(View.VISIBLE);

            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.white));
            mToolbar.setBackgroundColor(getResources().getColor(R.color.white));

        }
        else
        {
            lltitle.setBackgroundColor(getResources().getColor(R.color.transparent));
            lltitle.setVisibility(View.GONE);
            ivCircular.setVisibility(View.GONE);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        }

        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(lltitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
//                startAlphaAnimation(lltitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(lltitle, ALPHA_ANIMATIONS_DURATION, View.GONE);
                mIsTheTitleVisible = false;
            }
        }
    }
    private void handleAlphaOnTitle(float percentage) {

        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT);
            int pe= (int )(percentage*(deviceWidth/2));
//            mTitleContainer.setPadding(10,10,10,-10);
            layoutParams .setMargins(pe, 0,0,0);
            mTitleContainer.setGravity(Gravity.BOTTOM);

            if(mTitleContainer.getChildCount()>0)
            {
                TextView tvName =((TextView)mTitleContainer.getChildAt(0));
                TextView tvDegree = ((TextView)mTitleContainer.getChildAt(1));
                TextView tvLocation  = ((TextView)mTitleContainer.getChildAt(2));
                mTitleContainer.removeAllViews();
                tvName.setText(doctorDo.name);
                tvDegree   .setText(doctorDo.qualification);
                tvLocation .setText(doctorDo.location);
                mTitleContainer.addView(tvName);
                mTitleContainer.addView(tvDegree);
                mTitleContainer.addView(tvLocation);
            }

            mTitleContainer.setLayoutParams(layoutParams);



            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


}
