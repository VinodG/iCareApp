package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.GalleryDO;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryDA {

    private Context context;

    public GalleryDA(Context context) {
        this.context = context;
    }

    public HashMap<String, ArrayList<GalleryDO>> getMedia(String mediaType) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getMedia() - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            HashMap<String, ArrayList<GalleryDO>> hmMedia = null;
            try {
                String query = "select Type,Path from tblGallery";
                if (!StringUtils.isEmpty(mediaType))
                    query += " where Type = '" + mediaType + "'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmMedia = new HashMap<>();
                    do {
                        GalleryDO galleryDO = new GalleryDO();
                        String type = cursor.getString(0);

                        if (type.equalsIgnoreCase("Vedio"))
                            galleryDO.image = cursor.getString(1).replace(".mp4", ".png");
                        else
                            galleryDO.image = cursor.getString(1);


                        galleryDO.url = cursor.getString(1);
                        ArrayList<GalleryDO> mediaPaths = hmMedia.get(type);
                        if (mediaPaths == null)
                            mediaPaths = new ArrayList<>();
                        mediaPaths.add(galleryDO);
                        hmMedia.put(type, mediaPaths);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.close();
                }
                LogUtils.debug(LogUtils.LOG_TAG, "getMedia() - ended");
            }
            return hmMedia;
        }
    }

    public HashMap<String, ArrayList<GalleryDO>> getGallaryMedia() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getMedia() - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            HashMap<String, ArrayList<GalleryDO>> hmMedia = null;
            ArrayList<GalleryDO> mediaPaths = null;
            String type = "Image";
            try {
                String query = "select id,image from tblGallery";
                /*if(!StringUtils.isEmpty(mediaType))
                    query += " where Type = '"+mediaType+"'";*/
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmMedia = new HashMap<>();
                    mediaPaths = new ArrayList<>();
                    do {
                        GalleryDO galleryDO = new GalleryDO();
                        galleryDO.image = cursor.getString(1);

                        mediaPaths.add(galleryDO);

                    } while (cursor.moveToNext());
                    hmMedia.put(type, mediaPaths);
                }
                //**********************vedio*************
                String query1 = "select id,link from tblVideos";
                type = "Vedio";
                cursor = sqLiteDatabase.rawQuery(query1, null);
                if (cursor.moveToFirst()) {
                    //hmMedia = new HashMap<>();
                    mediaPaths = new ArrayList<>();
                    do {
                        GalleryDO galleryDO = new GalleryDO();
                        galleryDO.url = cursor.getString(1);
                        mediaPaths.add(galleryDO);

                    } while (cursor.moveToNext());
                    hmMedia.put(type, mediaPaths);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.close();
                }
                LogUtils.debug(LogUtils.LOG_TAG, "getMedia() - ended");
            }
            return hmMedia;
        }
    }

}
