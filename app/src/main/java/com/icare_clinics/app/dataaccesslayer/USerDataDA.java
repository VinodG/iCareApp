package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.ReminderDo;
import com.icare_clinics.app.dataobject.SetReminderDo;
import com.icare_clinics.app.dataobject.SetTargetDO;
import com.icare_clinics.app.dataobject.SettingDO;
import com.icare_clinics.app.dataobject.UserDo;
import com.icare_clinics.app.dataobject.WaterDO;
import com.icare_clinics.app.dataobject.WeightDO;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;


/**
 * Created by Baliram.Kumar on 23-03-2017.
 */

public class USerDataDA {
    private Context context;

    public USerDataDA(Context context) {
        this.context = context;
    }
    //****************************tblUser**********************************
    public boolean insertUserDetail(UserDo userDo) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertUserDetail() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblUser(Name,Mobile,Email,Gender,Dob,Height,Weight,LastName,HeightMeasurement,WeightMeasurement) values(?,?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblUser set Name=?,Mobile=?,Email=?,Gender=?,Dob=?,Height=?,Weight=?,LastName=?,HeightMeasurement=?,WeightMeasurement=?");

            updateSqLiteStatement.bindString(1, userDo.name);
            updateSqLiteStatement.bindString(2, userDo.mobileNumber);
            updateSqLiteStatement.bindString(3, userDo.email);
            updateSqLiteStatement.bindString(4, userDo.gender);
            updateSqLiteStatement.bindString(5, userDo.dob);
            updateSqLiteStatement.bindString(6, userDo.height);
            updateSqLiteStatement.bindString(7, userDo.weight);
            updateSqLiteStatement.bindString(8, userDo.lastName);
            updateSqLiteStatement.bindString(9, userDo.strHeight);
            updateSqLiteStatement.bindString(10, userDo.strWeight);

            if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                insertSqLiteStatement.bindString(1, userDo.name);
                insertSqLiteStatement.bindString(2, userDo.mobileNumber);
                insertSqLiteStatement.bindString(3, userDo.email);
                insertSqLiteStatement.bindString(4, userDo.gender);
                insertSqLiteStatement.bindString(5, userDo.dob);
                insertSqLiteStatement.bindString(6, userDo.height);
                insertSqLiteStatement.bindString(7, userDo.weight);
                insertSqLiteStatement.bindString(8, userDo.lastName);
                insertSqLiteStatement.bindString(9, userDo.strHeight);
                insertSqLiteStatement.bindString(10, userDo.strWeight);
                insertSqLiteStatement.executeInsert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-insertUserDetail - ended");
        }
        return false;
    }

    public boolean updateUserData(UserDo userDo) {
        LogUtils.debug(LogUtils.LOG_TAG, "updateUserData() - started");
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblUser set Name=?,Mobile=?,Email=?,Gender=?,Dob=?,Height=?,Weight=?,LastName=?,HeightMeasurement=?,WeightMeasurement=?");
            updateSqLiteStatement.bindString(1, userDo.name);
            updateSqLiteStatement.bindString(2, userDo.mobileNumber);
            updateSqLiteStatement.bindString(3, userDo.email);
            updateSqLiteStatement.bindString(4, userDo.gender);
            updateSqLiteStatement.bindString(5, userDo.dob);
            updateSqLiteStatement.bindString(6, userDo.height);
            updateSqLiteStatement.bindString(7, userDo.weight);
            updateSqLiteStatement.bindString(8, userDo.lastName);
            updateSqLiteStatement.bindString(9, userDo.strHeight);
            updateSqLiteStatement.bindString(10, userDo.strWeight);
            updateSqLiteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "updateUserData() - ended");
        }
        return false;
    }

    public boolean updateWeightAndHeight(UserDo userDo) {
        LogUtils.debug(LogUtils.LOG_TAG, "updateWeightAndHeight() - started");
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblUser set Height=?,Weight=?");
            updateSqLiteStatement.bindString(1, userDo.height);
            updateSqLiteStatement.bindString(2, userDo.weight);
            updateSqLiteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "updateWeightAndHeight() - ended");
        }
        return false;
    }
    //*************************** getUserdata *****************************************//
    public UserDo getUserData() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - getAbout() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            //ArrayList<AboutDo> arrAbout = null;
            UserDo  userDo=null;
            try {
                //21 columns retriveing //sqLiteDatabase.( "SELECT COUNT(*) FROM tbluser", null)
                String query = "SELECT Name,Mobile,Email,Gender,Dob,Height,Weight,LastName,HeightMeasurement,WeightMeasurement FROM tblUser";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    userDo=new UserDo();
                    //   arrAbout = new ArrayList<AboutDo>();
                    //  userDo=new UserDo();
                    do {
                        //  UserDo userDo = new UserDo();
                        userDo.name = cursor.getString(0);
                        userDo.mobileNumber = cursor.getString(1);
                        userDo.email = cursor.getString(2);
                        userDo.gender = cursor.getString(3);
                        userDo.dob = cursor.getString(4);
                        userDo.height = cursor.getString(5);
                        userDo.weight = cursor.getString(6);
                        userDo.lastName = cursor.getString(7);
                        userDo.strHeight = cursor.getString(8);
                        userDo.strWeight = cursor.getString(9);
                        /*arrAbout.add(aboutDo);*/
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - getAbout() - ended");
            }
            return userDo;
        }
    }

    /***********************insert tblWeight**************/

    public boolean insertWeight(WeightDO weightDO) {

        LogUtils.debug(LogUtils.LOG_TAG, " insertWeight() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblWeight(id,weight,date,bmi) values(?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblWeight set weight=?,date=?,bmi=? where id =?");

            updateSqLiteStatement.bindString(1, weightDO.id);
            updateSqLiteStatement.bindString(2, weightDO.weight);
            updateSqLiteStatement.bindString(3, weightDO.date);
            updateSqLiteStatement.bindString(4, weightDO.bmi);

            if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                insertSqLiteStatement.bindString(1, weightDO.id);
                insertSqLiteStatement.bindString(2, weightDO.weight);
                insertSqLiteStatement.bindString(3, weightDO.date);
                insertSqLiteStatement.bindString(4, weightDO.bmi);
                insertSqLiteStatement.executeInsert();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-insertWeight - ended");
        }
        return false;
    }

    public ArrayList<WeightDO> getWeightsForGivenDate(String date) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "USerDataDA - getWeights() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<WeightDO> arrWeightDO=null;
            try {
                // columns retriveing
                String query = "SELECT id,weight,date,bmi FROM tblWeight where date='"+date+"'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrWeightDO = new ArrayList<WeightDO>();
                    do{
                        WeightDO weightDO = new WeightDO();
                        weightDO.id = cursor.getString(0);
                        weightDO.weight = cursor.getString(1);
                        weightDO.date = cursor.getString(2);
                        weightDO.bmi = cursor.getString(3);
                        arrWeightDO.add(weightDO);
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
                LogUtils.debug(LogUtils.LOG_TAG, "USerDataDA - getWeights() - ended");
            }
            return arrWeightDO;
        }
    }

    public ArrayList<WeightDO> getWeightsBetweenDates(String startDay,String endDay,int dataSelection) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "USerDataDA - getWeightsBetweenDates() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<WeightDO> arrWeightDO=null;
            String query=null;
            try {
                if (dataSelection==0){
                    query = "SELECT id,weight,date,bmi FROM tblWeight where date between '"+startDay+"' and '"+endDay+"' ";
                } else if (dataSelection==1) {
                    query = " SELECT id,avg(weight),date,avg(bmi) FROM tblWeight where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime( '%Y-%m-%d',date)";//Day wise for week
                }else if (dataSelection==2){
                    query = " SELECT id,avg(weight),date,avg(bmi) FROM tblWeight where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime( '%Y-%W',date)";//week wise for month
                }else if (dataSelection==3){
                    query = " SELECT id,avg(weight),date,avg(bmi) FROM tblWeight where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime('%Y-%m',date)";//Month wise for year
                }
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrWeightDO = new ArrayList<WeightDO>();
                    do{
                        WeightDO weightDO = new WeightDO();
                        weightDO.id = cursor.getString(0);
                        weightDO.weight = cursor.getString(1);
                        weightDO.date = cursor.getString(2);
                        weightDO.bmi = cursor.getString(3);
                        arrWeightDO.add(weightDO);
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
                LogUtils.debug(LogUtils.LOG_TAG, "USerDataDA - getWeightsBetweenDates() - ended");
            }
            return arrWeightDO;
        }
    }

    ///////////////////////////tblWater()/////////////////////////////////////////

    public boolean insertWater(WaterDO waterDO) {

        LogUtils.debug(LogUtils.LOG_TAG, " insertWater() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblWater(id,water,image,date,time) values(?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblWater set water=?,image=?,date=?,time=? where id =?");

            updateSqLiteStatement.bindString(1, waterDO.id);
            updateSqLiteStatement.bindString(2, waterDO.strWater);
            updateSqLiteStatement.bindString(3, waterDO.imgBottle);
            updateSqLiteStatement.bindString(4, waterDO.date);
            updateSqLiteStatement.bindString(5, waterDO.time);

            if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                insertSqLiteStatement.bindString(1, waterDO.id);
                insertSqLiteStatement.bindString(2, waterDO.strWater);
                insertSqLiteStatement.bindString(3, waterDO.imgBottle);
                insertSqLiteStatement.bindString(4, waterDO.date);
                insertSqLiteStatement.bindString(5, waterDO.time);
                insertSqLiteStatement.executeInsert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-insertWater() - ended");
        }
        return false;
    }
    public ArrayList<WaterDO> getWaterData(String strDate) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - getWaterData() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<WaterDO> arrWater = null;
            WaterDO  waterDO=null;
            try {
//                String query = "SELECT id,water,image,date,time FROM tblWater";
                String query = "SELECT id,water,image,date,time FROM tblWater where date like '%"+strDate+"%'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    arrWater = new ArrayList<WaterDO>();
                    do {
                        waterDO=new WaterDO();
                        waterDO.id = cursor.getString(0);
                        waterDO.strWater = cursor.getString(1);
                        waterDO.imgBottle = cursor.getString(2);
                        waterDO.date = cursor.getString(3);
                        waterDO.time = cursor.getString(4);
                        arrWater.add(waterDO);
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - getWaterData() - ended");
            }
            return arrWater;
        }
    }
    public ArrayList<WaterDO> getWaterBetweenDatesNew(String startDay,String endDay,int dataSelection) {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - getWaterData() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<WaterDO> arrWater = new ArrayList<WaterDO>();
            WaterDO  waterDO=null;
            String query=null;
            try {
                if (dataSelection==0) {
//                String query = "SELECT id,water,image,date,time FROM tblWater where date between '"+startDay+"' and '"+endDay+"'  group by date";
                    query = " SELECT id,sum(water),image,date,time FROM tblWater where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime( '%Y-%m-%d',date)";//Day wise for week
                }else if (dataSelection==1){
                    query = " SELECT id,sum(water),image,date,time FROM tblWater where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime( '%Y-%W',date)";//week wise for month
                }else if (dataSelection==2){
                    query = " SELECT id,sum(water),image,date,time FROM tblWater where  date between  '" + startDay + "' and '" + endDay + "'  group by  strftime('%Y-%m',date)";//Month wise for year
                }
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    arrWater = new ArrayList<WaterDO>();
                    do {
                        waterDO=new WaterDO();
                        waterDO.id = cursor.getString(0);
                        waterDO.strWater = cursor.getString(1);
                        waterDO.imgBottle = cursor.getString(2);
                        waterDO.date = cursor.getString(3);
                        waterDO.time = cursor.getString(4);
                        arrWater.add(waterDO);
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - getWaterData() - ended");
            }
            return arrWater;
        }
    }

    public boolean insertSettings(SettingDO settingDO) {

        LogUtils.debug(LogUtils.LOG_TAG, " tblSetting() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblSetting(date,entityName,value1,value2,value3) values(?,?,?,?,?)");

//            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblSetting set value1='"+settingDO.value1+"', value2='"+settingDO.value2+"', value3='"+settingDO.value1+"' where entityName='"+settingDO.entityName+"' AND   strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d',"+settingDO.date+")");
//            String updatequrrey  = sqLiteDatabase.compileStatement("update tblSetting set value1='"+settingDO.value1+"', value2='"+settingDO.value2+"', value3='"+settingDO.value1+"' where entityName='"+settingDO.entityName+"' AND   strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d','"+settingDO.date+"')");
            //sqLiteDatabase.execSQL(updatequrrey);
            updateSqLiteStatement= sqLiteDatabase.compileStatement("update tblSetting set value1=?, value2=?, value3=? where entityName=? AND   strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d', ?)");
            updateSqLiteStatement.bindString(1, settingDO.value1);
            updateSqLiteStatement.bindString(2, settingDO.value2);
            updateSqLiteStatement.bindString(3, settingDO.value3);
            updateSqLiteStatement.bindString(4, settingDO.entityName);
            updateSqLiteStatement.bindString(5, settingDO.date);

            if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                insertSqLiteStatement.bindString(1, settingDO.date);
                insertSqLiteStatement.bindString(2, settingDO.entityName);
                insertSqLiteStatement.bindString(3, settingDO.value1);
                insertSqLiteStatement.bindString(4, settingDO.value2);
                insertSqLiteStatement.bindString(5, settingDO.value3);
                insertSqLiteStatement.executeInsert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-tblSetting() - ended");
        }
        return false;
    }
    public SettingDO getSettings(String strDate,String entityName) {

        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - tblSetting() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
//            ArrayList<SettingDO> arr = null;
            SettingDO  settingDo =new SettingDO();;
            try {
//                String query = "SELECT id,water,image,date,time FROM tblWater";
                String query = "SELECT  date, entityName, value1,value2,value3 from tblSetting where entityName = '"+entityName+"'   AND   strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d','"+strDate+"')";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
//                    arr= new ArrayList<SettingDO>();
                    do {

                        settingDo.date= cursor.getString(0);
                        settingDo.entityName = cursor.getString(1);
                        settingDo.value1 = cursor.getString(2);
                        settingDo.value2 = cursor.getString(3);
                        settingDo.value3 = cursor.getString(4);
//                        arr.add(settingDo);
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - tblSetting() - ended");
            }
            return settingDo;
        }
    }
    public int getTargetWater(String strDate,String entityName) {

        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - tblSetting() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            SettingDO  settingDo =new SettingDO();;
            int sum=0;
            try {
                String query = "SELECT  date, entityName, sum(value1),value2,value3 from tblSetting where entityName = '"+entityName+"'   AND   strftime('%Y-%m',date) = strftime( '%Y-%m','"+strDate+"')";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    do {

                        sum =Integer.parseInt(cursor.getString(2));
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - tblSetting() - ended");
            }
            return sum;
        }
    }
    public int getConsumedWater(String strDate,String endDay) {

        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - tblSetting() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            SettingDO  settingDo =new SettingDO();;
            int sum=0;
            try {
//                String query = "SELECT id,water,image,date,time FROM tblWater";
//                String  query = " SELECT id,sum(water),image,date,time FROM tblWater where  date between  '" + strDate + "' and '" + endDay + "'  group by  strftime('%Y-%m',date)";
                String  query = "  SELECT id,sum(water),image,date,time FROM tblWater where   strftime('%Y-%m',date) = strftime( '%Y-%m','"+strDate+"')";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    do {
                        sum =Integer.parseInt(cursor.getString(1));
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - tblSetting() - ended");
            }
            return sum;
        }
    }
    public ArrayList<SetReminderDo> getRemainders(String strDate ) {

        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - getRemainders() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<SetReminderDo> arr = null;
            SetReminderDo  reminderDo=null;
            try {
//                String query = "SELECT id,water,image,date,time FROM tblWater";
                String query = "SELECT  date, time, value1 from tblSetRemainder where    strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d','"+strDate+"')";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst() &&!(cursor.getCount()==0)) {
                    arr= new ArrayList<SetReminderDo>();
                    do {
                        reminderDo=new SetReminderDo();
                        reminderDo.date= cursor.getString(0);
                        reminderDo.time = cursor.getString(1);
                        reminderDo.value1 = cursor.getString(2);
                        arr.add(reminderDo);
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
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - getWaterData() - ended");
            }
            return arr;
        }
    }
    public void deleteReminders() {

        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "aboutDa - deleteReminders() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();

            SQLiteStatement deleteSqliteStatement = null;

            try {

                deleteSqliteStatement= sqLiteDatabase.compileStatement("delete from  tblSetRemainder");
                deleteSqliteStatement.executeUpdateDelete();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.close();
                }
                LogUtils.debug(LogUtils.LOG_TAG,  "AboutDA - deleteReminders() - ended");
            }
        }
    }

    public boolean insertWater(ArrayList<SetReminderDo> arrSetRemainder) {
        LogUtils.debug(LogUtils.LOG_TAG, " tblSetRemainder() - started");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
//        SQLiteStatement updateSqLiteStatement = null;
        SQLiteStatement deleteSqliteStatement = null;

        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblSetRemainder(date,time,value1) values(?,?,?)");
            deleteSqliteStatement= sqLiteDatabase.compileStatement("delete from  tblSetRemainder where strftime('%Y-%m-%d',date) = strftime( '%Y-%m-%d', ?)");
//            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblSetRemainder set time=?,value1=? where date=?");
            deleteSqliteStatement.bindString(1,arrSetRemainder.get(0).date);

            if(deleteSqliteStatement.executeUpdateDelete()>=0)
                for (SetReminderDo setReminderDo : arrSetRemainder)
                {
                    insertSqLiteStatement.bindString(1, setReminderDo.date);
                    insertSqLiteStatement.bindString(2, setReminderDo.time);
                    insertSqLiteStatement.bindString(3, setReminderDo.value1);
                    insertSqLiteStatement.executeInsert();
                }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (deleteSqliteStatement != null)
                deleteSqliteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "-tblSetRemainder - ended");
        }
        return false;
    }
}
