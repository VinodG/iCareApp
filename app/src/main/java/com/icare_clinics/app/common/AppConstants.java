package com.icare_clinics.app.common;

import android.graphics.Typeface;
import android.os.Environment;

import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.ReminderDo;

import java.util.ArrayList;

public class AppConstants {

    public static Typeface DinproBold,DinproLight,DinproMedium,DinproRegular,UbuntuMedium,UbuntuRegular;
   // public static Typeface UbuntuMedium,UbuntuRegular;

    public static int SELECT_ADDRESS = 100;

    public static ArrayList<DoctorDo> arrDoctor;
    public static ArrayList<ClinicDO> arrClinicList;

    public static int destoryActivity = 1002;
    public static int SYNC_SERVICE = 1004;
    public static int HYDRATION_NOTIFICATION = 1003;

    public static int REMINDER_NOTIFICATION=101;

    public static String APP_LOCATION = Environment.getExternalStorageDirectory() + "/.iCare";
    public static String IMAGE_DIR = "/Images";
    public static String LOGIN = "wootp";
    public static String EXISTING = "wotp";

    public static String GET = "GET";
    public static String POST = "POST";

    public static String INTERNET_ERROR = "NO INTERNET";
    public static String GPS_ERROR = "NO GPS";
    public static String INTERNET_WARNING = "CHECK Internet";

    public static ArrayList<DoctorDo> DOCTORS_LIST = null; //arrDoctor
    public static ArrayList<String> SPECIALISTS_LIST  = null; //arrSpecilist
    public static ArrayList<ClinicDO> CLINICS_LIST = null;//arrClinic



    //Zoopim Chat
//    public final static String ZOOPIM_KEY = "43eu5sdE7OAjJfxxpjypOOnsDvh0D0Bi";
    public final static String ZOOPIM_KEY = "47wINgM7n1WskM7ZRwEqK3kqwpB1EPHz";

    /********************************************* Social Network Start****************************/
    public final static int NETWORKTYPE_GOOGLEPLUS = 1;
    public final static int NETWORKTYPE_TWITTER = 2;
    public final static int NETWORKTYPE_FACEBOOK= 3;
    public static String REDIRECT_URL = "https://www.google.com";
    public static String COMPANY_URL = "http://www.maidubaiwater.com/";

    public final static String GOOGLEPLUS_CLIENT_ID = "433986951092-jhqpdb3sdc30unjd24c6saabtf2rvkng.apps.googleusercontent.com";
    public final static String GOOGLEPLUS_CLIENT_SECRET = "htAXvc1yrEI9l72eJoXzNdoQ";
    public final static String TWITTER_CLIENT_ID = "8Ok9BwhyLEnAtVEZQQYIbjqmi";
    public final static String TWITTER_CLIENT_SECRET = "gn5466RefakWN4wcLFYARUa6ZN3KBwqVyCH8A32JGMwmtTPvJQ";
    public final static String FACEBOOK_CLIENT_ID = "741458845995497";
    public final static String FACEBOOK_CLIENT_SECRET = "184967167a4c83f4eeedc1a8288ab54d";

    //GOOGLEPLUS

    public static String AUTH_URL = "https://accounts.google.com/o/oauth2/token";
    public static String scope = "https://www.googleapis.com/auth/plus.login+"
            + "https://www.googleapis.com/auth/plus.me+"
            + "https://www.googleapis.com/auth/userinfo.email+"
            + "https://www.googleapis.com/auth/userinfo.profile+" +
            "https://www.googleapis.com/auth/plus.media.upload+" +
            "https://www.googleapis.com/auth/plus.profiles.read+" +
            "https://www.googleapis.com/auth/plus.stream.read+" +
            "https://www.googleapis.com/auth/plus.stream.write";
    public static String GOOGELPLUS_LOGIN_URL = "https://accounts.google.com/o/oauth2/auth?redirect_uri="+REDIRECT_URL
            + "&response_type=code"
            + "&client_id="+GOOGLEPLUS_CLIENT_ID+""
            + "&scope="+scope
            + "&approval_prompt=force&access_type=offline";
    public static String userDetailsUrl = "https://www.googleapis.com/plus/v1/people/me?access_token=";


    //Twiter
    public static String TWITTER_Request_Token_URL = "https://api.twitter.com/oauth/request_token";
    public static String TWITTER_Authorize_URL = "https://api.twitter.com/oauth/authorize";
    public static String TWITTER_Access_token_URL = "https://api.twitter.com/oauth/access_token";

    //Facebook

    public static String FACEBOOK_SCOPE = "publish_actions,public_profile,email,user_about_me,user_actions.video,user_birthday,user_actions.music,user_photos,user_posts,user_videos";
    public static String responseType = "token";
    public static String FACEBOOK_LOGIN_URL = "https://www.facebook.com/dialog/oauth?client_id=" + FACEBOOK_CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&scope=" + FACEBOOK_SCOPE + "&response_type=" + responseType+"&display=touch";
    public static String FACEBOOK_UserDetailsUrl = "https://graph.facebook.com/me?fields=birthday,email,first_name,gender,hometown,id,last_name,location,middle_name,name&access_token=";

    /********************************************* Social Network End****************************/
    public static int MYBASKET = 0;
    /********************************************* weather Network ****************************/
    public static int DAY_WEHEATER_TYPE = 0;
    public static int WEEK_WEHEATER_TYPE = 1;
    public static String open_weather_maps_app_id = "3362480061efe725f9a79728377d88a8";

    //************************I care *****************************************
    public static String auth_token="h174bff8ejphhow7mvlv2n7vy9gkgne885e7q6r108gmwbga8fyr9xr3hfgdk58oa";
    public static String auth_tocken_req="54234dfgdfgd2342dgds44324gf24f4fg324fh32gf";
    public static DoctorDo selectedDoctorDo;

    public static String WATER_INTAKE="TARGET WATER";
    public static String SET_TARGET="SET TARGET";
    public static String SET_REMAINDER="SET REMAINDER";
    public static String IMAGE_BMI_URL="http://dev3.winitsoftware.com/iCare/images/BMI-graph.jpg";
    public static String ACTION_LOGOUT	="com.icare_clinics.app.ACTION.LOGOUT";

}
