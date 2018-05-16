package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 24-02-2017.
 */

public class TestimonialDA {
    private Context context;
    public TestimonialDA(Context context)
    {
        this.context=context;
    }

    public ArrayList<AboutDo> getTestimonial(){
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "TestimonialDA - getTestimonial() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<AboutDo> arrTestimonial=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,image,description,title,date,ip,dateymd,deleted,Action FROM tblTestimonial";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrTestimonial = new ArrayList<AboutDo>();
                    do {
                        AboutDo aboutDo=new AboutDo();
                        aboutDo.id=cursor.getString(0);
                        aboutDo.image=cursor.getString(1);
                        aboutDo.description=cursor.getString(2);
                        aboutDo.title=cursor.getString(3);
                        aboutDo.date=cursor.getString(4);
                        aboutDo.ip=cursor.getString(5);
                        aboutDo.dateymd=cursor.getString(6);
                        aboutDo.deleted=cursor.getString(7);
                        aboutDo.Action=cursor.getString(8);
                        arrTestimonial.add(aboutDo);
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
                LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - ended");
            }
            return arrTestimonial;
        }
    }

}
