package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.AboutIcareActivity;
import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;

/**
 * Created by srikrishna.nunna on 14-03-2017.
 */

public class AboutDA {
    private Context context;

    public AboutDA(Context context) {
        this.context=context;
    }

    public ArrayList<AboutDo> getAboutIcare() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "AboutDA - getAboutIcare() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<AboutDo> arrAboutDO = null;
            try {
                // columns retriveing
                String query = "SELECT id,title,description,vision,bh_photo,bh_msg,ip,date,dateymd,deleted,Action  FROM tblAbout";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrAboutDO = new ArrayList<AboutDo>();
                    do {
                        AboutDo aboutDo = new AboutDo();
                        aboutDo.id = cursor.getString(0);
                        aboutDo.title = cursor.getString(1);
                        aboutDo.description = cursor.getString(2);
                        aboutDo.vision = cursor.getString(3);
                        aboutDo.bh_photo = cursor.getString(4);
                        aboutDo.bh_msg = cursor.getString(5);
                        aboutDo.ip = cursor.getString(6);
                        aboutDo.date = cursor.getString(7);
                        aboutDo.dateymd = cursor.getString(8);
                        aboutDo.deleted = cursor.getString(9);
                        aboutDo.Action = cursor.getString(10);
                        aboutDo.image = "";//cursor.getString(11);
                        arrAboutDO.add(aboutDo);
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
                LogUtils.debug(LogUtils.LOG_TAG, "AboutDA - getAboutIcare() - ended");
            }

            return arrAboutDO;
        }
    }
}
