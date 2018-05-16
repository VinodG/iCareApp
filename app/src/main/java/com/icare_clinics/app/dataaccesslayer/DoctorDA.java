package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.ItemDo;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by WINIT on 24-Jan-17.
 */

public class DoctorDA {

    private Context context;

    public DoctorDA(Context context) {
        this.context = context;
    }

    public boolean insertDoctor(ArrayList<DoctorDo> arrDoctorDo) {

        LogUtils.debug(LogUtils.LOG_TAG, "ProductDOs-insertCustomer() - started");

        // SQLiteDatabase sqLiteDatabase = DatabaseHelper.openDataBase();
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblDoctor(id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages) values(?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblDoctor set name=?,qualification=?,location=?,details=?,photo=?," +
                    "Degree=?,AreasOfExpertise=?, Languages=? where id =?");

            for (DoctorDo doctorDo : arrDoctorDo) {
                updateSqLiteStatement.bindString(1, doctorDo.name);
                updateSqLiteStatement.bindString(2, doctorDo.qualification);
                updateSqLiteStatement.bindString(3, doctorDo.location);
                updateSqLiteStatement.bindString(4, doctorDo.details);
                updateSqLiteStatement.bindString(5, doctorDo.photo);
                updateSqLiteStatement.bindString(6, doctorDo.Degree);
                updateSqLiteStatement.bindString(7, doctorDo.AreasOfExpertise);
                updateSqLiteStatement.bindString(8, doctorDo.Languages);
                updateSqLiteStatement.bindString(9, doctorDo.id);


                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, doctorDo.id);
                    insertSqLiteStatement.bindString(2, doctorDo.name);
                    insertSqLiteStatement.bindString(3, doctorDo.qualification);
                    insertSqLiteStatement.bindString(4, doctorDo.location);
                    insertSqLiteStatement.bindString(5, doctorDo.details);
                    insertSqLiteStatement.bindString(6, doctorDo.photo);
                    insertSqLiteStatement.bindString(7, doctorDo.Degree);
                    insertSqLiteStatement.bindString(8, doctorDo.AreasOfExpertise);
                    insertSqLiteStatement.bindString(9, doctorDo.Languages);

                    insertSqLiteStatement.executeInsert();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-insert doctor() - ended");
        }
        return false;
    }

    public ArrayList<DoctorDo> getDoctor() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT \n" +
                        "id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages FROM tblDoctor where deleted = 0 ";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }
    }
    //get doctor according to clinic description
    public ArrayList<DoctorDo> getFilterDoctor(String location) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
//                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages FROM tblDoctor where location='"+location+"' and deleted='0' order by name";
                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages FROM tblDoctor where location like '%"+location+"%' and deleted='0' order by name";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }
    }
    //get doctor according to clinic description
    public ArrayList<String> getDoctorService(String location) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<String> arrDoctorService=null;
            try {
                //21 columns retriveing
//                String query = "SELECT DISTINCT service FROM tblDoctor where location='"+location+"' and deleted='0'";
                String query = "SELECT DISTINCT service FROM tblDoctor where location like '%"+location+"%' and deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorService = new ArrayList<String>();
                    do {
                        arrDoctorService.add(cursor.getString(0));
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
            return arrDoctorService;
        }
    }
    // get doctor location using specialition id
    public ArrayList<String> getDoctorlocation(String specialitionId) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<String> arrDoctorService=null;
            try {
                //21 columns retriveing
                String query = "SELECT DISTINCT location FROM tblDoctor where service='"+specialitionId+"' and deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorService = new ArrayList<String>();
                    do {
                        arrDoctorService.add(cursor.getString(0));
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
            return arrDoctorService;
        }
    }
    //***********************getDoctor using speciality id **********************
    public ArrayList<DoctorDo> getDoctorSpecialityWise(String id) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages FROM tblDoctor where service='"+id+"' and deleted='0' order by name";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }
    }
    //get Doctor specilization
    public ArrayList<String> getDoctorSpeciality(){
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<String> arrDoctorSpeciality=null;
            try {
                //21 columns retriveing
                String query = "SELECT DISTINCT qualification FROM tblDoctor";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorSpeciality = new ArrayList<String>();
                    do {
                        arrDoctorSpeciality.add(cursor.getString(0));
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
    public ArrayList<String> getDoctorLocation(){
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<String> arrDoctorLocation=null;
            try {
                //21 columns retriveing
                String query = "SELECT DISTINCT location FROM tblDoctor";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorLocation = new ArrayList<String>();
                    do {
                        arrDoctorLocation.add(cursor.getString(0));
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
            return arrDoctorLocation;
        }
    }

    public ArrayList<String> getSpecializationId(ArrayList<String> list){
        synchronized (MaiDubaiApplication.APP_DB_LOCK)
        {
            String query="";
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            String locQuery="";
            ArrayList<String> arrDoctorLocation=new ArrayList<String>();
            try {

                if (list.size()==0)
                {
                    locQuery=  " not in (' ')";

                }else {
                    locQuery= " in  (";
                    for (int i = 0; i < list.size(); i++) {
                        locQuery=locQuery+"'"+list.get(i).toString()+"'"+',';
                    }
                    locQuery = locQuery + "'--') ";
                }



                 query = "SELECT id  FROM tblSpecialization where   name  "+locQuery;
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorLocation = new ArrayList<String>();
                    do {
                        arrDoctorLocation.add(cursor.getString(0));
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
            return arrDoctorLocation;
        }
    }

    public ArrayList<DoctorDo> getFilterDoctor(String id,String location) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "DoctorDA - getDoctor() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages,service FROM tblDoctor where service='"+id+"' and location like '%"+location+"%' and deleted=0 order by name";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        doctorDo.service = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }
    }

    public ArrayList<DoctorDo> getFilteredDoctor(HashMap<String, ArrayList<ItemDo>> entireList)
    {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            String locQuery= "";
            String  specQuery= "";
            ArrayList<String> list = new ArrayList<String>();
            list = getSelectedItems(entireList.get("Speciality"));
            list = getSpecializationId(list);
            if (list.size()==0)
            {
                specQuery=" not in (-100) ";

            }else {
                specQuery= " in  (";
                for (int i = 0; i < list.size(); i++) {
                    specQuery=specQuery+list.get(i).toString()+',';
                }
                specQuery=specQuery+" -100)";
            }
            list = getSelectedItems(entireList.get("Location"));
          /*  if (list.size()==0)
            {
                locQuery=  " not in (' ')";

            }else {
                locQuery= " in  (";
                for (int i = 0; i < list.size(); i++) {
                    locQuery=locQuery+"'"+list.get(i).toString()+"'"+',';
                }
                locQuery = locQuery + "'--') ";
            }
*/


            if (list.size()==0)

                locQuery=  " location not in ('XX_NOLOCATION_XX')";
            else

            {
                for (int i = 0; i < list.size(); i++)
                {
                    if(i==0)
                        locQuery=locQuery+"( location like '%"+list.get(i).toString()+"%'";
                    else
                        locQuery=locQuery+" or location like'%"+list.get(i).toString()+"%'";
                }
                locQuery=locQuery+")";
            }



//        SELECT name , service,location  FROM tblDoctor where service in ( 1, 2) and location in ('Oasis Centre','Al Barsha 1' )and  deleted = 0




            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages,service FROM tblDoctor where service "+specQuery+" and "+locQuery+" and deleted = 0";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        doctorDo.service = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }

    }
/*
    public ArrayList<DoctorDo> getFilteredDoctor(HashMap<String, ArrayList<ItemDo>> entireList)
    {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            String locQuery= "";
            String  specQuery= "";
            ArrayList<String> list = new ArrayList<String>();
            list = getSelectedItems(entireList.get("Speciality"));
            list = getSpecializationId(list);
            if (list.size()==0)
            {
                specQuery=" not in (-100) ";

            }else {
                specQuery= " in  (";
                for (int i = 0; i < list.size(); i++) {
                    specQuery=specQuery+list.get(i).toString()+',';
                }
                specQuery=specQuery+" -100)";
            }
            list = getSelectedItems(entireList.get("Location"));
            if (list.size()==0)
            {
                locQuery=  " not in (' ')";

            }else {
                locQuery= " in  (";
                for (int i = 0; i < list.size(); i++) {
                    locQuery=locQuery+"'"+list.get(i).toString()+"'"+',';
                }
                locQuery = locQuery + "'--') ";
            }

//        SELECT name , service,location  FROM tblDoctor where service in ( 1, 2) and location in ('Oasis Centre','Al Barsha 1' )and  deleted = 0




            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<DoctorDo> arrDoctorDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages,service FROM tblDoctor where service "+specQuery+" and location "+locQuery+" and deleted = 0";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrDoctorDo = new ArrayList<DoctorDo>();
                    do {
                        DoctorDo doctorDo = new DoctorDo();

                        doctorDo.id = cursor.getString(0);
                        doctorDo.name = cursor.getString(1);
                        doctorDo.qualification = cursor.getString(2);
                        doctorDo.location = cursor.getString(3);
                        doctorDo.details = cursor.getString(4);
                        doctorDo.photo = cursor.getString(5);
                        doctorDo.Degree = cursor.getString(6);
                        doctorDo.AreasOfExpertise = cursor.getString(7);
                        doctorDo.Languages = cursor.getString(8);
                        doctorDo.service = cursor.getString(8);
                        arrDoctorDo.add(doctorDo);
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
            return arrDoctorDo;
        }

    }
*/

    private ArrayList<String> getSelectedItems(ArrayList<ItemDo> speciality)
    {
        ArrayList<String> list = new ArrayList<String>();
       for (int i = 0;i<speciality.size();i++)
        {
            if(speciality.get(i).status==true)
                list.add(speciality.get(i).getName());
        }
        return list;
    }

   /* public String getLastSyncTime() {

        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        Cursor cursor = null;
        String time=null;
        try {
            String query = "SELECT LastSyncDateTime FROM tblLastSyncDateTime";
            cursor = sqLiteDatabase.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                time = cursor.getString(0);
                    *//*String tt[] = time.split(" ");
                    return tt[0];*//*
                return  time;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return time;
    }*/


}
