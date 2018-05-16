package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.ReminderDo;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;

/**
 * Created by namashivaya.gangishe on 6/6/2017.
 */

public class ReminderDA {
    private Context context;

    public ReminderDA(Context context) {
        this.context = context;
    }

    public boolean insertReminder(ReminderDo reminderDo) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertReminderDetail() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        Cursor c=null;
        String ID="";
        int ID2=0;
        try {
            String query="SELECT count(*) FROM tblReminder";
            c=sqLiteDatabase.rawQuery(query,null);
            if (c.moveToFirst()) {
                ID=c.getString(0);
                ID2= StringUtils.getInt(ID) ;
                ID2++;
            }
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblReminder(MedicineName,NumberOfMedicine,NamesOfDays,MedicineTime,MedicineType,Durations,TimeType,MedicinID) values(?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblReminder set MedicineName=?,NumberOfMedicine=?,NamesOfDays=?,MedicineTime=?,MedicineType=?,Durations=?,TimeType=? where MedicinID=?");

            updateSqLiteStatement.bindString(1, reminderDo.medicineName);
            updateSqLiteStatement.bindString(2, reminderDo.numberOfCapsules);
            updateSqLiteStatement.bindString(3, reminderDo.days);
            updateSqLiteStatement.bindString(4, reminderDo.timing);
            updateSqLiteStatement.bindString(5, reminderDo.medicineType);
            updateSqLiteStatement.bindString(6, reminderDo.duration);
            updateSqLiteStatement.bindString(7, reminderDo.timeType);
            updateSqLiteStatement.bindString(8, reminderDo.MedicinID);

            if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                insertSqLiteStatement.bindString(1, reminderDo.medicineName);
                insertSqLiteStatement.bindString(2, reminderDo.numberOfCapsules);
                insertSqLiteStatement.bindString(3, reminderDo.days);
                insertSqLiteStatement.bindString(4, reminderDo.timing);
                insertSqLiteStatement.bindString(5, reminderDo.medicineType);
                insertSqLiteStatement.bindString(6, reminderDo.duration);
                insertSqLiteStatement.bindString(7, reminderDo.timeType);
                insertSqLiteStatement.bindString(8, ID2+"");
                reminderDo.MedicinID=ID2+"";
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
    public boolean updateReminder(ReminderDo reminderDo) {
        LogUtils.debug(LogUtils.LOG_TAG, "updateReminderData() - started");
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblReminder set MedicineName=?,NumberOfMedicine=?,NamesOfDays=?,MedicineTime=?,MedicineType=?,Durations=?,TimeType=? where MedicinID=?");
            updateSqLiteStatement.bindString(1, reminderDo.medicineName);
            updateSqLiteStatement.bindString(2, reminderDo.numberOfCapsules);
            updateSqLiteStatement.bindString(3, reminderDo.days);
            updateSqLiteStatement.bindString(4, reminderDo.timing);
            updateSqLiteStatement.bindString(5, reminderDo.medicineType);
            updateSqLiteStatement.bindString(6, reminderDo.duration);
            updateSqLiteStatement.bindString(7, reminderDo.timeType);
            updateSqLiteStatement.bindString(8, reminderDo.MedicinID);
            updateSqLiteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (insertSqLiteStatement != null)
                insertSqLiteStatement.close();
            if (updateSqLiteStatement != null)
                updateSqLiteStatement.close();
            LogUtils.debug(LogUtils.LOG_TAG, "updateReminderData() - ended");
        }
        return false;
    }
    public ArrayList<ReminderDo> getReminderData() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "reminderDA - getReminderData() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<ReminderDo> arrReminder = null;
            ReminderDo  reminderDo=null;
            try {
                //21 columns retriveing
                String query = "SELECT MedicineName,NumberOfMedicine,NamesOfDays,MedicineTime,MedicineType,Durations,TimeType,MedicinID FROM tblReminder";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {

                    arrReminder = new ArrayList<ReminderDo>();
                    // userDo=new UserDo();
                    do {
                        //  UserDo userDo = new UserDo();
                        reminderDo=new ReminderDo();
                        reminderDo.medicineName = cursor.getString(0);
                        reminderDo.numberOfCapsules = cursor.getString(1);
                        reminderDo.days = cursor.getString(2);
                        reminderDo.timing = cursor.getString(3);
                        reminderDo.medicineType = cursor.getString(4);
                        reminderDo.duration = cursor.getString(5);
                        reminderDo.timeType=cursor.getString(6);
                        reminderDo.MedicinID=cursor.getString(7);

                        arrReminder.add(reminderDo);
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
            return arrReminder;
        }
    }
    public void deleteReminder(String medId){
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.openDataBase();
        sqLiteDatabase.execSQL("delete from tblReminder where MedicinID='"+medId+"'");
        sqLiteDatabase.close();
    }

}
