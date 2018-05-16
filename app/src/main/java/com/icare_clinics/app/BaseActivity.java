package com.icare_clinics.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.icare_clinics.app.businesslayer.CommonBL;
import com.icare_clinics.app.businesslayer.DataListener;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.common.CustomDialog;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.CommonResponseDO;
import com.icare_clinics.app.dataobject.DataSyncDO;
import com.icare_clinics.app.dataobject.GalleryDO;
import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.httpimage.HttpImageManager;
import com.icare_clinics.app.utilities.BitmapUtilsLatLang;
import com.icare_clinics.app.utilities.CalendarUtil;
import com.icare_clinics.app.utilities.CircleTransform;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.NetworkUtil;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.HttpHelper;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.icare_clinics.app.R.id.imageView;


public abstract class BaseActivity extends AppCompatActivity implements DataListener, ComponentCallbacks {
    protected ArrayList<DrawerRowList> arrMenu;
    private ListView mDrawerList;
    public DrawerLayout drawerLayout;

    private ProgressDialog progressdialog;
    protected LinearLayout llBody;
    protected LinearLayout llBase;
    protected LinearLayout lldrawerHeader;
    protected LinearLayout llToggleLeft;
    protected LinearLayout llToggleRight;
    protected LinearLayout llToggle;
    protected LinearLayout llMainCenter,llForCall;
    protected LinearLayout llMenuFooter;
    protected LinearLayout llBack;
    protected LinearLayout llDrawerHeader;
    protected ImageView icareContact;
    protected Toolbar toolbar;
    protected TextView tvTitle, tvCArtCount, tvUserNameLoggedIn, tvCancel,tvfilteredItem1,tvSave;
    protected ImageView ivMenu, ivSearch, ivCancel, ivCart, ivLogo, ivDrawerClose, ivFirstTime, ivBack, ivUserImage, ivLogout,ivfiltered_xy,iv_fab,ivFb;
    Button btnGallery, btnCamera;
    protected RelativeLayout rlCArt;
    protected LayoutInflater inflater;
    protected Preference preference;
    protected boolean isLoggedIn;
    protected static String userName = "";
    public DecimalFormat decimalFormat;

    protected String otpString = "";
    protected static final int CAMERA_REQUEST = 1888;
    protected static int RESULT_LOAD_IMAGE = 1;
    protected static int GALLERY_KITKAT_INTENT_CALLED = 2;
    public static int NOTIFICATION_ID = 10000;
    public String language = "";
    protected Resources resources;
    protected String imageurl = "";

    protected Context context;
    public Bundle bundle;

    protected static int i = 0;

    protected SwitchCompat switchOnOff;

    Resources r;
    int deviceWidth, deviceHeight;
    float px;

    //private PendingIntent pIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  pIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(getBaseContext(), SplashActivity.class), getIntent().getFlags());
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(BaseActivity.this));
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        if (availableMegs < 10) {
            deleteDirectoryTree((BaseActivity.this).getCacheDir());
        }
        setContentView(R.layout.activity_base);
        resources = getResources();
        context=this;



        /************************************ Unique for every Active *********************************/

        inflater = this.getLayoutInflater();
        preference = new Preference(BaseActivity.this);
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        decimalFormat = (DecimalFormat) nf;
        decimalFormat.applyPattern("##.###");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);


        /***************************************** UI ****************************/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        llBody = (LinearLayout) findViewById(R.id.llBody);
//        llToggle = (LinearLayout) findViewById(R.id.llToggle);
//        llToggleLeft = (LinearLayout) findViewById(R.id.llToggleLeft);
//        llToggleRight = (LinearLayout) findViewById(R.id.llToggleRight);
        llMainCenter = (LinearLayout) findViewById(R.id.llMainCenter);
        iv_fab=(ImageView)findViewById(R.id.iv_fab);
        llForCall=(LinearLayout)findViewById(R.id.llForCall);
        ivfiltered_xy=(ImageView)llForCall.findViewById(R.id.ivfiltered_xy);
        //llMenuFooter                = (LinearLayout)inflater.inflate(R.layout.menu_footer, null);
        llMainCenter.setVisibility(View.VISIBLE);
        ivMenu = (ImageView) toolbar.findViewById(R.id.ivMenu);
        llBack = (LinearLayout) toolbar.findViewById(R.id.llBack);
        ivLogo = (ImageView) toolbar.findViewById(R.id.ivLogo);
        ivCancel = (ImageView) toolbar.findViewById(R.id.ivCancel);
//        rlCArt = (RelativeLayout) toolbar.findViewById(R.id.rlCArt);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        tvSave=(TextView)toolbar.findViewById(R.id.tvSave);
//        ivCart = (ImageView) findViewById(R.id.ivCart);
        llBase = (LinearLayout) findViewById(R.id.llBase);
//        ivFirstTime = (ImageView) findViewById(R.id.ivFirstTime);
//        tvCArtCount = (TextView) findViewById(R.id.tvCArtCount);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        icareContact = (ImageView) findViewById(R.id.icareContact);
        ivFb = (ImageView) findViewById(R.id.ivFb);
        switchOnOff=(SwitchCompat)toolbar.findViewById(R.id.switchOnOff);
//*************************************for left drawer************************************************
        llDrawerHeader = (LinearLayout) findViewById(R.id.llDrawerHeader);
        ivDrawerClose = (ImageView) findViewById(R.id.ivDrawerClose);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        tvUserNameLoggedIn = (TextView) findViewById(R.id.tvUserNameLoggedIn);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerListClickListner();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        arrMenu = new ArrayList<DrawerRowList>();
        getMenuListUser();
        mDrawerList.setAdapter(new DrawerAdapter());
        // TextView tvDeliveryDays = (TextView)llMenuFooter.findViewById(R.id.tvDeliveryDays);
        // TextView tvDelieryHead = (TextView)llMenuFooter.findViewById(R.id.tvDelieryHead);
        //tvDelieryHead.setPadding(0,10,0,0);
        int width = preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - width / 4, LinearLayout.LayoutParams.WRAP_CONTENT);
        //tvDelieryHead.setTypeface(AppConstants.DinproMedium);
        //tvDeliveryDays.setTypeface(AppConstants.DinproMedium);
        //tvDelieryHead.setLayoutParams(params);

        r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        deviceWidth = r.getDisplayMetrics().widthPixels;
        deviceHeight = r.getDisplayMetrics().heightPixels;


        String deliveryDays = preference.getStringFromPreference(Preference.DELIVERY_DAYS, "");
        if (!preference.getStringFromPreference(Preference.LANGUAGE, "").equalsIgnoreCase("en")) {
            deliveryDays = getDelivarydatesArabic(deliveryDays);
        }
        if (StringUtils.isEmpty(deliveryDays))
            //tvDeliveryDays.setText("N/A");
            //else
            //tvDeliveryDays.setText(deliveryDays);
            //mDrawerList.addFooterView(llMenuFooter,null,false);

            llBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCaptureImageOptions();
            }
        });
        llDrawerHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.exactDatabase();
                    }
                }).start();
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = preference.getStringFromPreference(Preference.LANGUAGE,"");
                if(language.equalsIgnoreCase("en") ){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }else
                {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        ivDrawerClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = preference.getStringFromPreference(Preference.LANGUAGE,"");
                if(language.equalsIgnoreCase("en")){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = preference.getStringFromPreference(Preference.LANGUAGE,"");
                if(language.equalsIgnoreCase("en")){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                showCustomDialog(BaseActivity.this,getString(R.string.alert),getString(R.string.logout_msg),getString(R.string.logout),getString(R.string.cancel),"LOGOUT");
            }
        });
        icareContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "2222444466"));
                    if (ActivityCompat.checkSelfPermission(BaseActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();
                }

            }
        });
        iv_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_fab.setVisibility(View.GONE);
                llForCall.setVisibility(View.VISIBLE);

            }
        });
        llForCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1800 iCARE(42273)"));
                    if (ActivityCompat.checkSelfPermission(BaseActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();
                }
                llForCall.setVisibility(View.GONE);
                iv_fab.setVisibility(View.VISIBLE);
            }
        });
        ivfiltered_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llForCall.setVisibility(View.GONE);
                iv_fab.setVisibility(View.VISIBLE);
            }
        });
        ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://www.facebook.com/icare.clinics";
                Intent intent=new Intent(BaseActivity.this,FacebookWebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        initialise();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstants.ACTION_LOGOUT);
        registerReceiver(LogoutReceiver, filter);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
//        return null;
    }


    public String getDelivarydatesArabic(String deliverydays)
    {
        String deliveryday = "";
        if(deliverydays.contains(","))
        {
            String[] delarr = deliverydays.split(",");
            for(String day:delarr)
            {
                if(StringUtils.isEmpty(deliveryday))
                {
                    deliveryday = getArabicDay(day);
                }
                else
                {
                    deliveryday = ","+getArabicDay(day);
                }
            }
        }
        else
        {
            deliveryday = getArabicDay(deliverydays);
        }

        return deliveryday;
    }

    public String getArabicDay(String day)
    {
        String arabicDay = "";
        if(day.equalsIgnoreCase("sunday"))
        {
            arabicDay = getResources().getString(R.string.Sunday);
        }
        else if(day.equalsIgnoreCase("monday"))
        {
            arabicDay = getResources().getString(R.string.Monday);
        }
        else if(day.equalsIgnoreCase("tuesday"))
        {
            arabicDay = getResources().getString(R.string.Tuesday);
        }
        else if(day.equalsIgnoreCase("Wednesday"))
        {
            arabicDay = getResources().getString(R.string.Wednesday);
        }
        else if(day.equalsIgnoreCase("Thursday"))
        {
            arabicDay = getResources().getString(R.string.Thursday);
        }
        else if(day.equalsIgnoreCase("Friday"))
        {
            arabicDay = day;
        }
        else if(day.equalsIgnoreCase("Saturday"))
        {
            arabicDay = getResources().getString(R.string.Saturday);
        }

        return arabicDay;
    }
    public String userImage = "";

    protected boolean isProfilePicUpload = false;

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            final Uri uri = data.getData();
            showLoader("uploading....");
            isProfilePicUpload = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String filepath = getRealPathFromURI(uri);

                    File f = new File(filepath);
                    Bitmap bmp = BitmapUtilsLatLang.decodeSampledBitmapFromResource(f, 320,480);
                    filepath = BitmapUtilsLatLang.saveVerifySignature(bmp,filepath);

                    imageurl =  new HttpHelper().uploadImage(ServiceUrls.MAIN_URL,preference.getIntFromPreference(Preference.CUSTOMER_ID,0)+"",filepath);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoader();
                            isProfilePicUpload = false;
                            if(!StringUtils.isEmpty(imageurl))
                            {
                                preference.saveStringInPreference(Preference.USER_IMAGE,imageurl);
                                setImage(imageurl,ivUserImage,R.drawable.draweruser);
                            }
                            else
                            {
                                showCustomDialog(BaseActivity.this,"","Something went wrong. Please try again...","Ok","","");
                            }
                        }
                    });
                }
            }).start();

                /*ivUserImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap,189,189,true));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,40,0,0);
                ivUserImage.setLayoutParams(layoutParams);*/
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ) {
            showLoader("uploading....");
            isProfilePicUpload = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String filepath = userImage;

                    File f = new File(filepath);
                    Bitmap bmp = BitmapUtilsLatLang.decodeSampledBitmapFromResource(f, 320,480);
                    filepath = BitmapUtilsLatLang.saveVerifySignature(bmp,filepath);

                    imageurl =  new HttpHelper().uploadImage(ServiceUrls.MAIN_URL,preference.getIntFromPreference(Preference.CUSTOMER_ID,0)+"",filepath);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoader();
                            isProfilePicUpload = false;
                            if(!StringUtils.isEmpty(imageurl))
                            {
                                preference.saveStringInPreference(Preference.USER_IMAGE,imageurl);
                                setImage(imageurl,ivUserImage,R.drawable.draweruser);
                            }
                            else
                            {
                                showCustomDialog(BaseActivity.this,"","Something went wrong. Please try again...","Ok","","");
                            }
                        }
                    });
                }
            }).start();




            //bitmap = Bitmap.createScaledBitmap(bitmap,189,189,true);
            // ivUserImage.setImageBitmap(bitmap);
            /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(40,40,0,0);
            ivUserImage.setLayoutParams(layoutParams);*/
        }
        if(requestCode == GALLERY_KITKAT_INTENT_CALLED && resultCode == RESULT_OK){
            showLoader("uploading....");
            isProfilePicUpload = true;
            final Uri originalUri = data.getData();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String filepath = getRealPathFromURI(originalUri);

                    File f = new File(filepath);
                    Bitmap bmp = BitmapUtilsLatLang.decodeSampledBitmapFromResource(f, 320,480);
                    filepath = BitmapUtilsLatLang.saveVerifySignature(bmp,filepath);

                    imageurl =  new HttpHelper().uploadImage(ServiceUrls.MAIN_URL,preference.getIntFromPreference(Preference.CUSTOMER_ID,0)+"",filepath);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoader();
                            isProfilePicUpload = false;
                            if(!StringUtils.isEmpty(imageurl))
                            {
                                preference.saveStringInPreference(Preference.USER_IMAGE,imageurl);
                                setImage(imageurl,ivUserImage,R.drawable.draweruser);
                            }
                            else
                            {
                                showCustomDialog(BaseActivity.this,"","Something went wrong. Please try again...","Ok","","");
                            }
                        }
                    });
                }
            }).start();

        }
    }


    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

//*******************************************************************************************************


    public void showSnackMessage(String message){
        Snackbar snackbar = Snackbar.make(llBody,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public boolean checkNetworkConnection() {
        if (!NetworkUtil.isNetworkConnectionAvailable(BaseActivity.this)) {
            showCustomDialog(BaseActivity.this, getString(R.string.alert), getString(R.string.No_Network), getString(R.string.OK), "", AppConstants.INTERNET_ERROR,false);
            return false;
        }
        return true;
    }

    public boolean checkGpsStatus() {
        if (!NetworkUtil.isGpsOn(BaseActivity.this)) {
            showCustomDialog(BaseActivity.this, getString(R.string.alert), getString(R.string.No_GPS), getString(R.string.OK), "", AppConstants.GPS_ERROR,false);
            return false;
        }
        return true;
    }

    public void clearPreference(){
        preference.removeFromPreference(Preference.IS_LOGGED_IN);
        preference.removeFromPreference(Preference.EMAIL_ID);
//        preference.removeFromPreference(Preference.CUSTOMER_ID);
        preference.removeFromPreference(Preference.SESSION_ID);
        preference.removeFromPreference(Preference.CUSTOMER_NAME);
        preference.removeFromPreference(Preference.CUSTOMER_MOBILE);
        preference.removeFromPreference(Preference.USER_IMAGE);
        preference.removeFromPreference(Preference.SHOWDELIVERY);
        preference.removeFromPreference(Preference.NOTIFICATION_FLAG);
        preference.removeFromPreference(Preference.HYDRATION_TIME_PERIOD);
        //       preference.removeFromPreference(Preference.FIRST_TIME_DISPLAY);
        preference.removeFromPreference(Preference.IS_DISCLAIMER);
    }

    public Preference getPreference(){
        return preference;
    }
    //Placed your drawer menu click listener
    private void drawerListClickListner() {
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menuName = (String) view.getTag(R.string.menu_name);
                Intent intent;
                String language = preference.getStringFromPreference(Preference.LANGUAGE,"");
                if(language.equalsIgnoreCase("en")){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                boolean stop = true;
                switch (menuName){
                    case "Home":
                        if(!(BaseActivity.this instanceof DashBoardActivity))
                        {
                            intent = new Intent(BaseActivity.this, DashBoardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            stop = false;
                        }
                        break;
                    case "Doctors":
                        if(!(BaseActivity.this instanceof DoctorsMainActivity))
                        {
                            intent = new Intent(BaseActivity.this, DoctorsMainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            stop = false;
                        }
                        break;
                    case "Clinics":
                        if(!(BaseActivity.this instanceof ClinicsMainActivity)) {
                            intent = new Intent(BaseActivity.this, ClinicsMainActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Specialities":
                        if(!(BaseActivity.this instanceof SpecialitiesActivity)) {
                            intent = new Intent(BaseActivity.this, SpecialitiesActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "What's New":
                        if(!(BaseActivity.this instanceof WhatsNewActivity)) {
                            intent = new Intent(BaseActivity.this, WhatsNewActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Health Packages":
                        if(!(BaseActivity.this instanceof HealthPackagesActivity)) {
                            intent = new Intent(BaseActivity.this, HealthPackagesActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Testimonials":
                        if(!(BaseActivity.this instanceof Testimonial)) {
                            intent = new Intent(BaseActivity.this, TestimonialsActivityNew.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Insurance Details":
                        if(!(BaseActivity.this instanceof InsuranceActivity)) {
                            intent = new Intent(BaseActivity.this, InsuranceActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;

                    case "About iCARE":
                        if(!(BaseActivity.this instanceof AboutIcareActivityNew)) {
                            intent = new Intent(BaseActivity.this, AboutIcareActivityNew.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;

                    case "Contact Us":
                        if(!(BaseActivity.this instanceof ContactUsActivity)) {
                            intent = new Intent(BaseActivity.this, ContactUsActivity.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Medicine Reminder":
                        if(!(BaseActivity.this instanceof MyReminder)) {
                            intent = new Intent(BaseActivity.this, MyReminder.class);
                            startActivity(intent);
                        }else{
                            stop = false;
                        }
                        break;
                    case "Health Tracker":
                        String name = preference.getStringFromPreference(Preference.NAME,"");
                        String mobileNUM=preference.getStringFromPreference(Preference.MOBILE_NUM,"");
                        String email=preference.getStringFromPreference(Preference.EMAIL_ID,"");
                        String gender=preference.getStringFromPreference(Preference.GENDER,"");
                        String dateOfBirth=preference.getStringFromPreference(Preference.DOB,"");
                        String height=preference.getStringFromPreference(Preference.HEIGHT,"");
                        String weight=preference.getStringFromPreference(Preference.WEIGHT,"");
                        if(name.equalsIgnoreCase("")&&mobileNUM.equalsIgnoreCase("")&&email.equalsIgnoreCase("")){
                            if(!(BaseActivity.this instanceof ProfileSetup)) {
                                intent = new Intent(BaseActivity.this, HealthTrackerIntro.class);
                                startActivity(intent);
                            }else{
                                stop = false;
                            }
                            break;
                        }else {
                            if (!(BaseActivity.this instanceof ProfileSetup)) {
                                intent = new Intent(BaseActivity.this, HealthTracker.class);
                                startActivity(intent);
                            } else {
                                stop = false;
                            }
                            break;
                        }

                    case "Sync":
                        if (checkNetworkConnection()) {
                           /* refreshData(new SyncIntentService.SyncListener() {
                                @Override
                                public void onStart() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showLoader("Syncing...");
                                        }
                                    });
                                }

                                @Override
                                public void onError(String message) {
                                    hideLoader();
                                    ();
                                }

                                @Override
                                public void onEnd() {
                                    hideLoader();
                                    ();
                                }
                            });*/
                        }
                        stop = false;
                        break;
                   /* case "Logout" :
                        stop = false;
                        showCustomDialog(BaseActivity.this,getString(R.string.alert),getString(R.string.logout_msg),getString(R.string.logout),getString(R.string.cancel),"LOGOUT");
                        break;*/
                    default:
                        Toast.makeText(BaseActivity.this,"Under Development",Toast.LENGTH_LONG).show();
                        break;
                }
                if(!(BaseActivity.this instanceof DashBoardActivity) && stop){
                    finish();
                }
            }
        });
    }

    public void lockDrawer(final String from) {
        LogUtils.errorLog("Menu Drawer Locked in ", from);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    //FOR STATUS BAR COLOR
    public void setStatusBarColor(){
        Window window = BaseActivity.this.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(BaseActivity.this.getResources().getColor(R.color.statusbarblue));
        }
    }

   /* public void setStatusBarTransparent(){
        Window window = BaseActivity.this.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }*/

  /*  public void setStatusBarTransparent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            final Window window  = getWindow();
           *//*  window.getDecorView().setSystemUiVisibility(
                     View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                             View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                               window.setStatusBarColor(Color.TRANSPARENT);*//*
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE
            );
        }
    }*/
    public void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w =  BaseActivity.this.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    public void setTypeFaceBold(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.DinproBold);
            else if (v instanceof ViewGroup)
                setTypeFaceBold((ViewGroup) v);
        }
    }

    public void setTypeFaceNormal(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.DinproRegular);
            else if (v instanceof ViewGroup)
                setTypeFaceNormal((ViewGroup) v);
        }
    }

    public void setTypeFaceLight(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.DinproLight);
            else if (v instanceof ViewGroup)
                setTypeFaceNormal((ViewGroup) v);
        }
    }

    public void setTypeFaceMedium(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.DinproMedium);
            else if (v instanceof ViewGroup)
                setTypeFaceNormal((ViewGroup) v);
        }
    }

    public void setTypeFaceUbuntuMedium(ViewGroup group)
    {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.UbuntuMedium);
            else if (v instanceof ViewGroup)
                setTypeFaceUbuntuRegular((ViewGroup) v);
        }
    }
    public void setTypeFaceUbuntuRegular(ViewGroup group)
    {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
                ((TextView) v).setTypeface(AppConstants.UbuntuRegular);
            else if (v instanceof ViewGroup)
                setTypeFaceUbuntuRegular((ViewGroup) v);
        }
    }
    private void getMenuListUser() {
        arrMenu.clear();
        String[]titles=getResources().getStringArray(R.array.drawer_menu_titles);
        String[]ar_titles=getResources().getStringArray(R.array.drawer_menu_titles_ar);
        TypedArray icons = getResources().obtainTypedArray(R.array.drawer_menu_icons);
        for (int i=0;i<titles.length;i++){
            DrawerRowList items=new DrawerRowList(icons.getResourceId(i,0),titles[i],ar_titles[i]);
            arrMenu.add(items);
        }
    }

    public void setLocale(String language){
        if(StringUtils.isEmpty(language))
            language = "en";
        preference.saveStringInPreference(Preference.LANGUAGE,language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               /* boolean isLocaleUpdated = preference.getbooleanFromPreference(Preference.LOCALE_UPDATED,false);
                if(isLocaleUpdated){
                    setLocale(preference.getStringFromPreference(Preference.LANGUAGE,"en"));
                    Intent intent = new Intent(BaseActivity.this, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {*/
                //TextView tvDeliveryDays = (TextView) llMenuFooter.findViewById(R.id.tvDeliveryDays);
                //tvDeliveryDays.setTypeface(AppConstants.DinproMedium);
                String deliveryDays = preference.getStringFromPreference(Preference.DELIVERY_DAYS, "");
                if(!preference.getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase("en"))
                {
                    deliveryDays = getDelivarydatesArabic(deliveryDays);
                }
                    /*if (StringUtils.isEmpty(deliveryDays))
                        tvDeliveryDays.setText("N/A");
                    else
                        tvDeliveryDays.setText(deliveryDays);*/
                loadData();
//                }
            }
        });
    }

    class DrawerAdapter extends BaseAdapter
    {
        String lang = "";

        DrawerAdapter(){
            lang = preference.getStringFromPreference(Preference.LANGUAGE,"");
        }

        @Override
        public int getCount() {
            if (arrMenu!=null && arrMenu.size()>0) {
                return arrMenu.size();
            }
            else
                return 0;
        }

        @Override
        public Object getItem(int arg0) {
            return arrMenu.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }


        @Override
        public View getView(int pos, View convertView, ViewGroup arg2) {
            ViewHolder viewHolder;
            if(convertView==null){

                // inflate the layout
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.drawer_menu_item, null);
                viewHolder = new ViewHolder();
                //  viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.tvDrawerListItem);
                viewHolder.icons= (ImageView) convertView.findViewById(R.id.drawerIcon);
                viewHolder.titles= (TextView) convertView.findViewById(R.id.drawerTitles);
                convertView.setTag(viewHolder);

            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DrawerRowList rowItem = (DrawerRowList) getItem(pos);
            viewHolder.icons.setImageResource(rowItem.getImageid());
            if(lang.equalsIgnoreCase("ar"))
                viewHolder.titles.setText(rowItem.getArbicTitle());
            else
                viewHolder.titles.setText(rowItem.getTitles());
            viewHolder.titles.setTypeface(AppConstants.DinproMedium);
            convertView.setTag(R.string.menu_name,rowItem.getTitles());
            return convertView;
        }
    }

    class ViewHolder {
        ImageView icons;
        TextView titles;
    }

    public abstract void initialise();
    public abstract void initialiseControls();
    public abstract void loadData();

    public void showLoader(String msg) {
        runOnUiThread(new RunShowLoader(msg, ""));
    }
    public void showLoader(String msg, String title) {
        runOnUiThread(new RunShowLoader(msg, title));
    }

    /** For hiding progress dialog (Loader ). **/
    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressdialog != null && progressdialog.isShowing())
                        progressdialog.dismiss();
                    progressdialog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* Custom Runnable for showing loaders */
    class RunShowLoader implements Runnable {
        private String strMsg;
        private String title;

        public RunShowLoader(String strMsg, String title) {
            this.strMsg = strMsg;
            this.title = title;
        }

        @Override
        public void run() {
            try {
                if (progressdialog == null
                        || (progressdialog != null && !progressdialog
                        .isShowing())) {
                    progressdialog = ProgressDialog.show(BaseActivity.this,
                            title, strMsg);
                } else if (progressdialog == null
                        || (progressdialog != null && progressdialog
                        .isShowing())) {
                    progressdialog.setMessage(strMsg);
                }
            } catch (Exception e) {
                progressdialog = null;
            }
        }
    }

    public void OTPSuccessfull(){
        showCustomDialog(BaseActivity.this, "", "OTP Suceesfull", getString(R.string.OK), "", "");
    }

    public static void deleteDirectoryTree(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteDirectoryTree(child);
            }
        }

        fileOrDirectory.delete();
    }

    @Override
    public void onTrimMemory(int level) {
        if(level == TRIM_MEMORY_RUNNING_LOW) {
            deleteDirectoryTree((BaseActivity.this).getCacheDir());
        }
        super.onTrimMemory(level);
    }

    public void showAlert(String message, String from){
        showCustomDialog(BaseActivity.this, getString(R.string.alert), message,getString(R.string.OK), "", from);
    }

    /** Method to Show the alert dialog **/
    public void showCustomDialog(Context context, String strTitle,String strMessage, String firstBtnName, String secondBtnName,String from)
    {
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage,firstBtnName, secondBtnName, from, true));
    }
    /** Method to Show the alert dialog **/
    public void showCustomDialog(Context context, String strTitle,String strMessage, String firstBtnName, String secondBtnName,String from, boolean isCancelable)
    {
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage,firstBtnName, secondBtnName, from, isCancelable));
    }

    // For showing Dialog message.
    private class ShowCustomDialogs implements Runnable
    {
        private CustomDialog customDialog;
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private boolean isCancelable=false;
        private View.OnClickListener posClickListener;
        private View.OnClickListener negClickListener;

        public ShowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable)
        {
            this.strTitle 		= strTitle;
            this.strMessage 	= strMessage;
            this.firstBtnName 	= firstBtnName;
            this.secondBtnName	= secondBtnName;
            this.isCancelable 	= isCancelable;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setTitle(strTitle);
                alertDialog.setMessage(strMessage);
                alertDialog.setCancelable(isCancelable);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, firstBtnName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onButtonYesClick(from);
                    }
                });
                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, secondBtnName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onButtonNoClick(from);
                        }
                    });
                }
                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.dark_blue2));
                        Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        if(button != null) {
                            button.setTextColor(getResources().getColor(R.color.dark_blue2));
                        }
                    }
                });
                alertDialog.show();
            } else {
                if (customDialog != null && customDialog.isShowing())
                    customDialog.dismiss();

                View view = inflater.inflate(R.layout.custom_common_popup, null);
                customDialog = new CustomDialog(BaseActivity.this, view, preference
                        .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                customDialog.setCancelable(isCancelable);
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitlePopup);
                View ivDivider = view.findViewById(R.id.ivDividerPopUp);
                View view_middle = view.findViewById(R.id.view_middle);
                TextView tvMessage = (TextView) view.findViewById(R.id.tvMessagePopup);
                Button btnYes = (Button) view.findViewById(R.id.btnYesPopup);
                Button btnNo = (Button) view.findViewById(R.id.btnNoPopup);

                tvTitle.setTypeface(AppConstants.DinproMedium);
                tvMessage.setTypeface(AppConstants.DinproRegular);
                btnYes.setTypeface(AppConstants.DinproMedium);
                btnNo.setTypeface(AppConstants.DinproMedium);
                if (!StringUtils.isEmpty(strTitle)) {
                    if (strTitle.equalsIgnoreCase(getString(R.string.alert)) || strTitle.equalsIgnoreCase(getString(R.string.warning)) || strTitle.equalsIgnoreCase(getString(R.string.confirm)))
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.alert, 0, 0);
                    else if (strTitle.equalsIgnoreCase(getString(R.string.success)))
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.verified, 0, 0);
                    else
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tvTitle.setText("" + strTitle);
                } else {
                    tvTitle.setVisibility(View.GONE);
                    ivDivider.setVisibility(View.GONE);
                }
                tvMessage.setText("" + strMessage);
                btnYes.setText("" + firstBtnName);

                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase(""))
                    btnNo.setText("" + secondBtnName);
                else {
                    btnNo.setVisibility(View.GONE);
                    view_middle.setVisibility(View.GONE);
                }

                if (posClickListener == null)
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            onButtonYesClick(from);
                        }
                    });
                else
                    btnYes.setOnClickListener(posClickListener);

                if (negClickListener == null)
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            onButtonNoClick(from);
                        }
                    });
                else
                    btnNo.setOnClickListener(negClickListener);
                try {
                    if (!customDialog.isShowing())
                        customDialog.showCustomDialog();
                } catch (Exception e) {
                }
            }
        }
    }

    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase("LOGOUT")){
            clearPreference();
            finish();
            Intent intentBrObj = new Intent();
            intentBrObj.setAction(AppConstants.ACTION_LOGOUT);
            sendBroadcast(intentBrObj);
            /*Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);*/
        }

    }
    public void onButtonNoClick(String from) {

    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        LogUtils.errorLog("valid", "called");
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{1,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /*
     * size must be drawable
     */
    public void setImageCircle(String imageurl,ImageView imageView,int size) {
        if (!StringUtils.isEmpty(imageurl)) {

            Drawable d = getResources().getDrawable(size);
            int mHeight = d.getIntrinsicHeight();
            int mWidth = d.getIntrinsicWidth();

            LogUtils.errorLog("Image Url:",imageurl);
            imageurl = imageurl.replaceAll(" ","%20");
            Picasso.with(BaseActivity.this).load(imageurl).transform(new CircleTransform())
                    .resize(mWidth, mHeight)
                    .error(size)
                    .into(imageView);
        }
    }

    public void getImageClinicsImage(String imageurl,ImageView imageViews){
        LogUtils.errorLog("Image Url:",imageurl);
        imageurl = imageurl.replaceAll(" ","%20");
        Picasso.with(BaseActivity.this).load(imageurl).resize(240, 120).placeholder(R.drawable.clinics)
                .into(imageViews);
    }
    public void setDoctorsImage(String imageurl,ImageView imageViews){
        LogUtils.errorLog("Image Url:",imageurl);
        imageurl = imageurl.replaceAll(" ","%20");
        Picasso.with(BaseActivity.this).load(imageurl).placeholder(R.drawable.doctors_small)
                .into(imageViews);
    }

    public class SetImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public SetImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){

            imageView.setImageBitmap(Bitmap.createScaledBitmap(result, 240, 120, false));
        }
    }

    public class SetClinicImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public SetClinicImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);

                if (!StringUtils.isEmpty(urlOfImage)) {
                    int size = R.drawable.img1;
                    BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
                    int mHeight = bd.getBitmap().getHeight();
                    int mWidth = bd.getBitmap().getWidth();

                    LogUtils.errorLog("Image Url:", urlOfImage);
                    Picasso.with(context).load(urlOfImage)
                            .resize(mWidth, mHeight)
                            .error(size)
                            .into(imageView);
                }

            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


    public void setClinicImage(final String imageurl, final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!StringUtils.isEmpty(imageurl)) {
                    int size = R.drawable.img1;
                    BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
                    int mHeight = bd.getBitmap().getHeight();
                    int mWidth = bd.getBitmap().getWidth();

                    LogUtils.errorLog("Image Url:", imageurl);
                    Picasso.with(context).load(imageurl)
                            .resize(mWidth, mHeight)
                            .error(size)
                            .into(imageView);
                }
            }
        }).start();

    }

    public void setImage(String fileUri,ImageView imageView,int size){
        if (!StringUtils.isEmpty(fileUri)) {

            Drawable d =getResources().getDrawable(size);
            int mHeight = d.getIntrinsicHeight();
            int mWidth = d.getIntrinsicWidth();
           /* try {
                if(fileUri.contains("content://")) {

                    if (Build.VERSION.SDK_INT <19){
                        Uri photoUri = Uri.parse(fileUri);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        imageView.setImageBitmap(GetRoundedShape(bitmap,size));
                    }
                    else
                    {
                        Uri photoUri = Uri.parse(fileUri);
                        String wholeID = DocumentsContract.getDocumentId(photoUri);

                        // Split at colon, use second item in the array
                        String id = wholeID.split(":")[1];

                        String[] column = { MediaStore.Images.Media.DATA };

                        // where id is equal to
                        String sel = MediaStore.Images.Media._ID + "=?";

                        Cursor cursor = getContentResolver().
                                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        column, sel, new String[]{ id }, null);

                        String filePath = "";

                        int columnIndex = cursor.getColumnIndex(column[0]);

                        if (cursor.moveToFirst()) {
                            filePath = cursor.getString(columnIndex);
                        }

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath,bmOptions);
                        imageView.setImageBitmap(GetRoundedShape(bitmap,size));

                        cursor.close();
                    }


                   *//* String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(GetRoundedShape(bitmap,size));*//*

                }else{
                    File image = new File(fileUri);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                    imageView.setImageBitmap(GetRoundedShape(bitmap,size));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            LogUtils.errorLog("Image Url:",ServiceUrls.MAIN_URL + fileUri+"?t="+ i++);
            Picasso.with(BaseActivity.this).load(ServiceUrls.MAIN_URL + fileUri+"?t="+ i++).transform(new CircleTransform())
                    .resize(mWidth, mHeight)
                    .error(size)
                    .into(imageView);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        String path = "";
        try {
            if(contentUri.toString().contains("content://")) {

                if (Build.VERSION.SDK_INT < 19) {

                    String[] proj = { MediaStore.Images.Media.DATA };
                    cursor = getContentResolver().query(contentUri,  proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                }
                else {
                    String wholeID = DocumentsContract.getDocumentId(contentUri);

                    // Split at colon, use second item in the array
                    String id = wholeID.split(":")[1];

                    String[] column = { MediaStore.Images.Media.DATA };

                    // where id is equal to
                    String sel = MediaStore.Images.Media._ID + "=?";

                    Cursor cursornew = getContentResolver().
                            query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    column, sel, new String[]{ id }, null);


                    int columnIndex = cursornew.getColumnIndex(column[0]);

                    if (cursornew.moveToFirst()) {
                        path = cursornew.getString(columnIndex);
                    }
                    cursornew.close();
                }
            }
            else
            {
                File image = new File(contentUri.toString());
                path = image.getAbsolutePath();
            }

            return path;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoggedIn = preference.getbooleanFromPreference(Preference.IS_LOGGED_IN,false);
        userName = StringUtils.isEmpty(preference.getStringFromPreference(Preference.CUSTOMER_NAME,""))? preference.getStringFromPreference(Preference.EMAIL_ID,"Guest@gmail.com"):preference.getStringFromPreference(Preference.CUSTOMER_NAME,"Guest");
        tvUserNameLoggedIn.setText(userName);
//        setImage(preference.getStringFromPreference(Preference.USER_IMAGE,""),ivUserImage, R.drawable.draweruser);
    }

    private MyPagerAdapter imageAdaper;
    private Dialog imageDialog;
    public void showVideoPopUp(Uri uri)
    {
        LinearLayout convertView		=  (LinearLayout) inflater.inflate(R.layout.image_popup, null);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.llPhotos);
        VideoView videoView = (VideoView)convertView.findViewById(R.id.videoView);
        linearLayout.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        videoView.setMinimumWidth(width);
        videoView.setMinimumHeight(height);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        if(imageDialog!=null && imageDialog.isShowing())
            imageDialog.dismiss();

        imageDialog= new Dialog(BaseActivity.this);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageDialog.setContentView(convertView);
        imageDialog.getWindow().setLayout(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 800) - 200, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT, 1200) - 400);
        imageDialog.show();

    }

    public HttpImageManager getHttpImageManager() {
        return ((MaiDubaiApplication) (BaseActivity.this)
                .getApplication()).getHttpImageManager();
    }

    public void hideKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    //********************************for roundes images*************************************
    protected Bitmap GetRoundedShape(Bitmap scaleBitmapImage,int imgId) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),imgId);
        int targetWidth = bitmap.getWidth();
        int targetHeight = bitmap.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    public String generateOTP()
    {
        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int)(Math.random()*9000)+1000;
        String PINString = String.valueOf(randomPIN);

        return PINString;
    }

    public void generateNotification(Context context,String text,String OTP)
    {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = null;

        int count = (int) System.currentTimeMillis();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Mai Dubai")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentText(text)
                .setSubText(OTP);
        mBuilder.setAutoCancel(true);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void dataRetrieved(ResponseDO response) {
     /*   if (response.method != null && (response.method == ServiceMethods.WS_MASTER_TABLE &&response.data != null)) {
            *//*if(response.data instanceof MasterTableDO){
                MasterTableDO masterTableDO = (MasterTableDO) response.data;
                String masterDataUrl = masterTableDO.masterTablePath;
                masterDataUrl = masterDataUrl.substring(0,masterDataUrl.lastIndexOf("."));
                masterDataUrl = ServiceUrls.MAIN_URL + masterDataUrl+".zip";
                new CommonBL(BaseActivity.this,BaseActivity.this).downloadMasterTable(masterDataUrl);
                String syncTime = CalendarUtil.getDate(masterTableDO.serverTime, CalendarUtil.DATE_PATTERN_dd_MM_YYYY, CalendarUtil.SYNCH_DATE_TIME_PATTERN,10*60*1000,Locale.ENGLISH);
                preference.saveStringInPreference(Preference.LAST_ORDERS_SYNC,syncTime);
            }else{
                showCustomDialog(BaseActivity.this,"",(String)response.data,getString(R.string.OK),"","");
            }*//*
        }else if (response.method != null && (( response.method == ServiceMethods.WS_OTP_MAIL || response.method == ServiceMethods.WS_OTP_MAIL_LOGIN ) &&response.data != null)) {
            if (!response.isError) {
                OTPSuccessfull();
            }else{
//                Toast.makeText(BaseActivity.this,(String)response.data,Toast.LENGTH_LONG).show();
                showCustomDialog(BaseActivity.this,"",(String)response.data,getString(R.string.OK),"","");
            }
        }else if (response.method != null && (response.method == ServiceMethods.WS_DOWNLOAD_MASTER_TABLE &&response.data != null)) {
            if (!response.isError && response.data instanceof InputStream) {
                if (!response.isError && response.data instanceof InputStream) {
                }else{
                    showCustomDialog(BaseActivity.this,"",(String)response.data,getString(R.string.OK),"","");
                }
            }
        }else if (response.method != null && (response.method == ServiceMethods.WS_RESEND_OTP &&response.data != null)) {
            if (response.data instanceof CommonResponseDO && ((CommonResponseDO)response.data).status != 0) {
                Toast.makeText(BaseActivity.this,getString(R.string.OTP_sent),Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(BaseActivity.this,getString(R.string.OTP_sent_fail),Toast.LENGTH_LONG).show();
            }
        }else if (response.method != null && response.method == ServiceMethods.WS_DATASYNC) {
            if (response.data != null && response.data instanceof DataSyncDO) {
                DataSyncDO dataSyncDO = (DataSyncDO)response.data;
                String syncTime = CalendarUtil.getDate(dataSyncDO.serverTime, CalendarUtil.YYYY_MM_DD_FULL_PATTERN, CalendarUtil.SYNCH_DATE_TIME_PATTERN,5*60*1000,Locale.ENGLISH);
                preference.saveStringInPreference(Preference.LAST_ORDERS_SYNC,syncTime);
            }else{
                showCustomDialog(BaseActivity.this, "", (String) response.data, getString(R.string.OK), "", "");
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        MaiDubaiApplication.picassoLruCache.clear();
        unregisterReceiver(LogoutReceiver);
        super.onDestroy();
    }
    /* image popup by balram */
    public void showImagePopUp(ArrayList<GalleryDO> images, int position)
    {
        LinearLayout convertView		=  (LinearLayout) inflater.inflate(R.layout.image_popup, null);
        final LinearLayout llPagerTab 	=  (LinearLayout) convertView.findViewById(R.id.llPagerTab);
        ViewPager pager 				=  (ViewPager) convertView.findViewById(R.id.pager);
        ImageView btnClose				=  (ImageView) convertView.findViewById(R.id.btnClose);
        imageAdaper = new MyPagerAdapter(images);
        pager.setAdapter(imageAdaper);
//        pager.setOffscreenPageLimit(galleryImages.length);
//        refreshPageController(llPagerTab,pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setEnabled(false);
        pager.setClickable(false);
        pager.setCurrentItem(position);
/*        pager.setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int postion)
            {

                for (int i = 0; i <= (imageAdaper.getCount()-1); i++)
                {
                    ((ImageView)llPagerTab.getChildAt(i)).setImageResource(R.drawable.pager_dot);
                }
                ((ImageView)llPagerTab.getChildAt(postion)).setImageResource(R.drawable.pager_dot_h);
            }

            @Override
            public void onPageScrolled(int postion, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int postion)
            {
            }
        });*/

        if(imageDialog!=null && imageDialog.isShowing())
            imageDialog.dismiss();

        imageDialog= new Dialog(BaseActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageDialog.setContentView(convertView);
        // imageDialog.getWindow().setLayout(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 800)-200,preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT, 1200)-400);
        imageDialog.show();
        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter
    {
        private ArrayList<GalleryDO> galleryImages;
        public MyPagerAdapter(ArrayList<GalleryDO> galleryImages)
        {
            this.galleryImages = galleryImages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getCount() {
            if(galleryImages != null)
                return galleryImages.size();
            return 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((LinearLayout) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }
    }
    BroadcastReceiver LogoutReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            finish();
        }
    };
}


