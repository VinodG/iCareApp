package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 17-02-2017.
 */

public class SpecialityDA {
    private Context context;
    public SpecialityDA(Context context)
    {
        this.context=context;
    }

    public ArrayList<SpecializationDO> getSpeciality(){
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<SpecializationDO> arrDoctorSpeciality=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,seo_name,SpecialtyBanner750x482,SpecialtyIcons241x262,SpecialtyIcons397x432,SpedcialtyBanner1242x798,in_menu,date,path,deleted,sort,Action FROM tblSpecialization where deleted = 0 order by cast(sort as int) ASC";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorSpeciality = new ArrayList<SpecializationDO>();
                    do {
                        SpecializationDO specialityDo=new SpecializationDO();
                        specialityDo.id=cursor.getString(0);
                        specialityDo.name=cursor.getString(1);
                        specialityDo.seo_name=cursor.getString(2);
                        specialityDo.SpecialtyBanner750x482=cursor.getString(3);
                        specialityDo.SpecialtyIcons241x262=cursor.getString(4);
                        specialityDo.SpecialtyIcons397x432=cursor.getString(5);
                        specialityDo.SpedcialtyBanner1242x798=cursor.getString(6);
                        specialityDo.in_menu=cursor.getString(7);
                        specialityDo.date=cursor.getString(8);
                        specialityDo.path=cursor.getString(9);
                        specialityDo.deleted=cursor.getString(10);
                        specialityDo.sort=cursor.getString(11);
                        specialityDo.Action=cursor.getString(12);
                        arrDoctorSpeciality.add(specialityDo);
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
            return arrDoctorSpeciality;
        }
    }


    //get speciality according to clinic description
    public ArrayList<SpecializationDO> getSpecialityClinicWise(ArrayList<String> arrDoctorService) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            String location="";
            String strService="";

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<SpecializationDO> arrDoctorSpeciality=null;
            try {
                 for(String drService:arrDoctorService) {

                   if(StringUtils.isEmpty(strService)){
                       strService="'"+drService+"'";
                   }else{
                       strService+=", '"+drService+"'";
                   }

               }
                String query = "SELECT id,name,seo_name,SpecialtyBanner750x482,SpecialtyIcons241x262,SpecialtyIcons397x432,SpedcialtyBanner1242x798,in_menu,date,path,deleted,sort,Action FROM tblSpecialization where id IN("+strService+") and deleted='0' order by cast(sort as int) asc";

                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorSpeciality = new ArrayList<SpecializationDO>();
                    do {
                        SpecializationDO specializationDO = new SpecializationDO();

                        SpecializationDO specialityDo=new SpecializationDO();
                        specialityDo.id=cursor.getString(0);
                        specialityDo.name=cursor.getString(1);
                        specialityDo.seo_name=cursor.getString(2);
                        specialityDo.SpecialtyBanner750x482=cursor.getString(3);
                        specialityDo.SpecialtyIcons241x262=cursor.getString(4);
                        specialityDo.SpecialtyIcons397x432=cursor.getString(5);
                        specialityDo.SpedcialtyBanner1242x798=cursor.getString(6);
                        specialityDo.in_menu=cursor.getString(7);
                        specialityDo.date=cursor.getString(8);
                        specialityDo.path=cursor.getString(9);
                        specialityDo.deleted=cursor.getString(10);
                        specialityDo.sort=cursor.getString(11);
                        specialityDo.Action=cursor.getString(12);
                        arrDoctorSpeciality.add(specialityDo);
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
            return arrDoctorSpeciality;
        }
    }
    public SpecializationDO getDoctorSpeciality(String service) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            SpecializationDO specialityDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,seo_name,SpecialtyBanner750x482,SpecialtyIcons241x262,SpecialtyIcons397x432,SpedcialtyBanner1242x798,in_menu,date,path,deleted,sort,Action FROM tblSpecialization where id="+service+" deleted = 0 ";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    specialityDo=new SpecializationDO();
                    do {
                        specialityDo.id=cursor.getString(0);
                        specialityDo.name=cursor.getString(1);
                        specialityDo.seo_name=cursor.getString(2);
                        specialityDo.SpecialtyBanner750x482=cursor.getString(3);
                        specialityDo.SpecialtyIcons241x262=cursor.getString(4);
                        specialityDo.SpecialtyIcons397x432=cursor.getString(5);
                        specialityDo.SpedcialtyBanner1242x798=cursor.getString(6);
                        specialityDo.in_menu=cursor.getString(7);
                        specialityDo.date=cursor.getString(8);
                        specialityDo.path=cursor.getString(9);
                        specialityDo.deleted=cursor.getString(10);
                        specialityDo.sort=cursor.getString(11);
                        specialityDo.Action=cursor.getString(12);
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
            return specialityDo;
        }
    }

}
