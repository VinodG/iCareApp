package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;

/**
 * Created by WINIT on 27-Jan-17.
 */

public class ClinicsDA {
    private Context context;

    public ClinicsDA(Context context) {
        this.context = context;
    }

    public ArrayList<ClinicDO> getClinics() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "ClinicsDA - getClinics() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<ClinicDO> arrClinicsDO=null;
            try {
                // columns retriveing
                String query = "SELECT id,code,description,contact,email,timing,add1,add2,add3,latitude,longitude,seo_url,seo_changed_url,seo_title,seo_description,seo_keywords,seo_h1,seo_alt_tag,seo_content,seo_page_name,date,deleted,sort,Banners750x374,Banners1242x620,Action FROM tblClinic where deleted = 0 order by description DESC";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrClinicsDO = new ArrayList<ClinicDO>();
                    do{
                        ClinicDO clinicsDO = new ClinicDO();
                        clinicsDO.id = cursor.getString(0);
                        clinicsDO.code = cursor.getString(1);
                        clinicsDO.description = cursor.getString(2);
                        clinicsDO.contact = cursor.getString(3);
                        clinicsDO.email = cursor.getString(4);
                        clinicsDO.timing = cursor.getString(5);
                        clinicsDO.add1 = cursor.getString(6);
                        clinicsDO.add2 = cursor.getString(7);
                        clinicsDO.add3 = cursor.getString(8);
                        clinicsDO.latitude = cursor.getString(9);
                        clinicsDO.longitude = cursor.getString(10);
                        clinicsDO.seo_url = cursor.getString(11);
                        clinicsDO.seo_changed_url = cursor.getString(12);
                        clinicsDO.seo_title = cursor.getString(13);
                        clinicsDO.seo_description = cursor.getString(14);
                        clinicsDO.seo_keywords = cursor.getString(15);
                        clinicsDO.seo_h1 = cursor.getString(16);
                        clinicsDO.seo_alt_tag = cursor.getString(17);
                        clinicsDO.seo_content = cursor.getString(18);
                        clinicsDO.seo_page_name = cursor.getString(19);
                        clinicsDO.date = cursor.getString(20);
                        clinicsDO.deleted = cursor.getString(21);
                        clinicsDO.sort = cursor.getString(22);
                        clinicsDO.Banners750x374 = cursor.getString(23);
                        clinicsDO.Banners1242x620 = cursor.getString(24);
                        clinicsDO.Action = cursor.getString(25);
                        arrClinicsDO.add(clinicsDO);
                    }while (cursor.moveToNext());


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
                LogUtils.debug(LogUtils.LOG_TAG, "ClinicsDA - getClinics() - ended");
            }
            return arrClinicsDO;
        }
    }
    //*************************
    public ArrayList<ClinicDO> getClinicSpecialityWise(ArrayList<String> arrDoctorLocation) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            String strLocation="";
          //  String strService="";

            LogUtils.debug(LogUtils.LOG_TAG, "ClinicsDA - ClinicsDA() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<ClinicDO> arrClinics=null;
            try {
                String tmp= "";
                for(String drLocation2:arrDoctorLocation) {
                  if(tmp.equalsIgnoreCase(""))
                      tmp=drLocation2;
                      else
                    tmp+= ","+drLocation2;


                }

                String[] temp2;
                temp2=tmp.split(",");
                for(String drLocation:temp2) {

                    if(StringUtils.isEmpty(strLocation)){
                        strLocation="'"+drLocation+"'";
                    }else{
                        strLocation+=", '"+drLocation+"'";
                    }

                }
/*
                for(String drLocation:arrDoctorLocation) {

                    if(StringUtils.isEmpty(strLocation)){
                        strLocation="'"+drLocation+"'";
                    }else{
                        strLocation+=", '"+drLocation+"'";
                    }

                }
*/
                String query = "SELECT DISTINCT id,code,description,contact,email,timing,add1,add2,add3,latitude,longitude,seo_url,seo_changed_url,seo_title,seo_description,seo_keywords,seo_h1,seo_alt_tag,seo_content,seo_page_name,date,deleted,sort,Banners750x374,Banners1242x620,Action FROM tblClinic where description IN("+strLocation+") and deleted='0' order by description DESC";

                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrClinics = new ArrayList<ClinicDO>();
                    do {
                        ClinicDO clinicsDO = new ClinicDO();
                        clinicsDO.id = cursor.getString(0);
                        clinicsDO.code = cursor.getString(1);
                        clinicsDO.description = cursor.getString(2);
                        clinicsDO.contact = cursor.getString(3);
                        clinicsDO.email = cursor.getString(4);
                        clinicsDO.timing = cursor.getString(5);
                        clinicsDO.add1 = cursor.getString(6);
                        clinicsDO.add2 = cursor.getString(7);
                        clinicsDO.add3 = cursor.getString(8);
                        clinicsDO.latitude = cursor.getString(9);
                        clinicsDO.longitude = cursor.getString(10);
                        clinicsDO.seo_url = cursor.getString(11);
                        clinicsDO.seo_changed_url = cursor.getString(12);
                        clinicsDO.seo_title = cursor.getString(13);
                        clinicsDO.seo_description = cursor.getString(14);
                        clinicsDO.seo_keywords = cursor.getString(15);
                        clinicsDO.seo_h1 = cursor.getString(16);
                        clinicsDO.seo_alt_tag = cursor.getString(17);
                        clinicsDO.seo_content = cursor.getString(18);
                        clinicsDO.seo_page_name = cursor.getString(19);
                        clinicsDO.date = cursor.getString(20);
                        clinicsDO.deleted = cursor.getString(21);
                        clinicsDO.sort = cursor.getString(22);
                        clinicsDO.Banners750x374 = cursor.getString(23);
                        clinicsDO.Banners1242x620 = cursor.getString(24);
                        clinicsDO.Action = cursor.getString(25);
                        arrClinics.add(clinicsDO);
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
                LogUtils.debug(LogUtils.LOG_TAG, "ClinicsDA - ClinicsDA() - ended");
            }
            return arrClinics;
        }
    }

}
