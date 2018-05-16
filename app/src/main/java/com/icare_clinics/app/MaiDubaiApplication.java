package com.icare_clinics.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.icare_clinics.app.common.FilesStorage;
import com.icare_clinics.app.httpimage.FileSystemPersistence;
import com.icare_clinics.app.httpimage.HttpImageManager;

/**
 * Created by Girish Velivela on 12-07-2016.
 */
public class MaiDubaiApplication extends MultiDexApplication {

    public static String APP_DB_LOCK = "DB_LOCK";
    public static Picasso picasso;
    public static LruCache picassoLruCache;
    private HttpImageManager mHttpImageManager;
    public static Context mContext;
    public HttpImageManager getHttpImageManager() {
        return mHttpImageManager;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        ZopimChat.init(AppConstants.ZOOPIM_KEY);
        if(mContext ==null)
            mContext = this;
        FacebookSdk.sdkInitialize(getApplicationContext());

        picassoLruCache = new LruCache(this);
// Set cache
        picasso = new Picasso.Builder(this) //
                .memoryCache(picassoLruCache) //
                .build();

        mHttpImageManager = new HttpImageManager(
                HttpImageManager.createDefaultMemoryCache(),
                new FileSystemPersistence(FilesStorage.getImageCacheDirectory()));
    }
}
