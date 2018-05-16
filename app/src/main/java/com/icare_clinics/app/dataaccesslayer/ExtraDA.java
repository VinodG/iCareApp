package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icare_clinics.app.MaiDubaiApplication;
import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.BannerDo;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.dataobject.TestimonialDo;
import com.icare_clinics.app.dataobject.VaccinationDo;
import com.icare_clinics.app.dataobject.WellnessPackageDo;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by WINIT on 27-Jan-17.
 */

public class ExtraDA {
    private Context context;

    public ExtraDA(Context context) {
        this.context = context;
    }

    public ArrayList<BannerDo> getBanner() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "getBanner - getBanner() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<BannerDo> arrBanner = null;
            try {
                String query = "SELECT id,image,seo_alt_tag,date,deleted,Action FROM tblBanner where deleted='0' ";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrBanner = new ArrayList<BannerDo>();
                    do {
                        BannerDo bannerDo = new BannerDo();
                        bannerDo.id = cursor.getString(0);
                        bannerDo.image = cursor.getString(1);
                        bannerDo.seo_alt_tag = cursor.getString(2);
                        bannerDo.date = cursor.getString(3);
                        bannerDo.deleted = cursor.getString(4);
                        bannerDo.Action = cursor.getString(5);
                        arrBanner.add(bannerDo);
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
                LogUtils.debug(LogUtils.LOG_TAG, "getBanner - getBanner() - ended");
            }
            return arrBanner;

        }
    }

    public ArrayList<TestimonialDo> getTestimonial() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "TestimonialDA - getTestimonial() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<TestimonialDo> arrTestimonial = null;
            try {
                //21 columns retriveing
                String query = "SELECT id,image,description,title,date,ip,dateymd,deleted,Action FROM tblTestimonial where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrTestimonial = new ArrayList<TestimonialDo>();
                    do {
                        TestimonialDo aboutDo = new TestimonialDo();
                        aboutDo.id = cursor.getString(0);
                        aboutDo.image = cursor.getString(1);
                        aboutDo.description = cursor.getString(2);
                        aboutDo.title = cursor.getString(3);
                        aboutDo.date = cursor.getString(4);
                        aboutDo.ip = cursor.getString(5);
                        aboutDo.dateymd = cursor.getString(6);
                        aboutDo.deleted = cursor.getString(7);
                        aboutDo.Action = cursor.getString(8);
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
                LogUtils.debug(LogUtils.LOG_TAG, "TestimonialDA - getTestimonial() - ended");
            }
            return arrTestimonial;
        }
    }

    public ArrayList<NewsDo> getNews() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "NewsDA - getNews() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<NewsDo> arrNews = null;
            try {
                //21 columns retriveing
                String query = "SELECT id,title,description,image,ip,date,dateymd,deleted,Action FROM tblNews where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrNews = new ArrayList<NewsDo>();
                    do {
                        NewsDo newsDo = new NewsDo();
                        newsDo.id = cursor.getString(0);
                        newsDo.title = cursor.getString(1);
                        newsDo.description = cursor.getString(2);
                        newsDo.image = cursor.getString(3);
                        newsDo.ip = cursor.getString(4);
                        newsDo.date = cursor.getString(5);
                        newsDo.dateymd = cursor.getString(6);
                        newsDo.deleted = cursor.getString(7);
                        newsDo.Action = cursor.getString(8);
                        arrNews.add(newsDo);
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
                LogUtils.debug(LogUtils.LOG_TAG, "NewsDA - getNews() - ended");
            }
            return arrNews;
        }
    }

    //*****************************insurance**********
    public HashMap<String, ArrayList<InsuranceDo>> getInsuranceDetails() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getMedia() - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            HashMap<String, ArrayList<InsuranceDo>> hmInsurance = null;
            ArrayList<InsuranceDo> arrInsuCovered = null;
            ArrayList<InsuranceDo> arrInsuAffiliates = null;
            String type = "List of Insurances covered";
            try {
                String query = "select id,category,image,date,deleted,Action from tblInsurance where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmInsurance = new HashMap<>();
                    arrInsuCovered = new ArrayList<>();
                    arrInsuAffiliates = new ArrayList<>();
                    do {
                        InsuranceDo insuranceDo = new InsuranceDo();
                        insuranceDo.id = cursor.getString(0);
                        insuranceDo.category = cursor.getString(1);
                        insuranceDo.image = cursor.getString(2);
                        insuranceDo.date = cursor.getString(3);
                        insuranceDo.deleted = cursor.getString(3);
                        insuranceDo.Action = cursor.getString(4);
                       if(insuranceDo.category.contains("Third Party Affiliates")){
                           arrInsuAffiliates.add(insuranceDo);
                       }else if(insuranceDo.category.contains("List of Insurances covered")){
                           arrInsuCovered.add(insuranceDo);
                       }

                    } while (cursor.moveToNext());
                    hmInsurance.put("coveredInsurance", arrInsuCovered);
                    hmInsurance.put("affiliatesInsurance", arrInsuAffiliates);
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
            return hmInsurance;
        }
    }

    //********************************************About Icare*********************************************
    public ArrayList<AboutDo> getAbout() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {

            LogUtils.debug(LogUtils.LOG_TAG, "AboutDA - getAbout() - strated");

            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            ArrayList<AboutDo> arrAbout = null;
            try {
                //21 columns retriveing
                String query = "SELECT id,title,description,vision,bh_photo,bh_msg,ip,date,dateymd,deleted,Action FROM tblAbout where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    arrAbout = new ArrayList<AboutDo>();
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
                        arrAbout.add(aboutDo);
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
            return arrAbout;
        }
    }



    //*****************************WellnessHealthPackages**********

    public LinkedHashMap<String, ArrayList<WellnessPackageDo>> getWellnessHealthPackages() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getWellnessHealthPackages - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            LinkedHashMap<String, ArrayList<WellnessPackageDo>> hmWP = null;
            ArrayList<WellnessPackageDo> wellnessPackageDos1 = new ArrayList<>();
            ArrayList<WellnessPackageDo> wellnessPackageDos2 = new ArrayList<>();
            ArrayList<WellnessPackageDo> wellnessPackageDos3 = new ArrayList<>();
            try {
                // insert into tblWellnessPackage(id,name,ip,date,dateymd,deleted,plan,plan_type,Action) values(?,?,?,?,?,?,?,?,?)");

                String query = "select id, name, ip, date, dateymd, deleted, plan, plan_type, Action from tblWellnessPackage where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmWP = new LinkedHashMap<>();
                    do {
                        WellnessPackageDo wellnessPackageDo = new WellnessPackageDo();
                        wellnessPackageDo.id = cursor.getString(0);
                        wellnessPackageDo.name = cursor.getString(1);
                        wellnessPackageDo.ip= cursor.getString(2);
                        wellnessPackageDo.date = cursor.getString(3);
                        wellnessPackageDo.dateymd = cursor.getString(4);
                        wellnessPackageDo.deleted = cursor.getString(5);
                        wellnessPackageDo.plan = cursor.getString(6);
                        wellnessPackageDo.plan_type = cursor.getString(7);
                        wellnessPackageDo.Action = cursor.getString(8);

                        if(wellnessPackageDo.plan.equals("1"))
                           wellnessPackageDos1.add(wellnessPackageDo);
                        else if(wellnessPackageDo.plan.equals("2"))
                            wellnessPackageDos2.add(wellnessPackageDo);
                        else if(wellnessPackageDo.plan.equals("3"))
                            wellnessPackageDos3.add(wellnessPackageDo);
                    } while (cursor.moveToNext());
                    hmWP.put("1", wellnessPackageDos1);
                    hmWP.put("2", wellnessPackageDos2);
                    hmWP.put("3", wellnessPackageDos3);
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
                LogUtils.debug(LogUtils.LOG_TAG, "getWellnessHealthPackages() - ended");
            }
            return hmWP;
        }
    }


    public LinkedHashMap<String, ArrayList<VaccinationDo>> getAnteNatalHealthPackages() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getAnteNatalHealthPackages - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            LinkedHashMap<String, ArrayList<VaccinationDo>> hmWP = null;
            ArrayList<VaccinationDo> VaccinationDos1 = new ArrayList<>();
            ArrayList<VaccinationDo> VaccinationDos2 = new ArrayList<>();
            ArrayList<VaccinationDo> VaccinationDos3 = new ArrayList<>();
            try {
//                insert into tblAntenatalPackage(id,package,ip,date,dateymd,deleted,package_id,Action) values(?,?,?,?,?,?,?,?)");
                String query = "select id,package,ip,date,dateymd,deleted,package_id,Action from tblAntenatalPackage where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmWP = new LinkedHashMap<>();
                    do {
                        VaccinationDo VaccinationDo = new VaccinationDo();
                        VaccinationDo.id = cursor.getString(0);
                        VaccinationDo.packageVac = cursor.getString(1);
                        VaccinationDo.ip= cursor.getString(2);
                        VaccinationDo.date = cursor.getString(3);
                        VaccinationDo.dateymd = cursor.getString(4);
                        VaccinationDo.deleted = cursor.getString(5);
                        VaccinationDo.package_id = cursor.getString(6);
                        VaccinationDo.Action = cursor.getString(7);
                        if(VaccinationDo.package_id .equals("9"))
                            VaccinationDos1.add(VaccinationDo);
                        else if(VaccinationDo.package_id .equals("10"))
                            VaccinationDos2.add(VaccinationDo);
                        else if(VaccinationDo.package_id .equals("11"))
                            VaccinationDos3.add(VaccinationDo);
                    } while (cursor.moveToNext());

                    hmWP.put("9", VaccinationDos1);
                    hmWP.put("10", VaccinationDos2);
                    hmWP.put("11", VaccinationDos3);
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
                LogUtils.debug(LogUtils.LOG_TAG, "getAnteNatalHealthPackages() - ended");
            }
            return hmWP;
        }
    }

    public LinkedHashMap<String, ArrayList<VaccinationDo>> getVaccinationHealthPackages() {
        synchronized (MaiDubaiApplication.APP_DB_LOCK) {
            LogUtils.debug(LogUtils.LOG_TAG, "getVaccinationHealthPackages - strated");
            SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
            Cursor cursor = null;
            LinkedHashMap<String, ArrayList<VaccinationDo>> hmWP = null;
            ArrayList<VaccinationDo> VaccinationDos1 = new ArrayList<>();
            ArrayList<VaccinationDo> VaccinationDos2 = new ArrayList<>();
            ArrayList<VaccinationDo> VaccinationDos3 = new ArrayList<>();
            try {
//              ("insert into tblVaccinationPackage(id,package,ip,date,dateymd,deleted,package_id,Action) values(?,?,?,?,?,?,?,?)");
                String query = "select id,package,ip,date,dateymd,deleted,package_id,Action from tblVaccinationPackage where deleted='0'";
                cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    hmWP = new LinkedHashMap<>();
                    do {
                        VaccinationDo VaccinationDo = new VaccinationDo();
                        VaccinationDo.id = cursor.getString(0);
                        VaccinationDo.packageVac = cursor.getString(1);
                        VaccinationDo.ip= cursor.getString(2);
                        VaccinationDo.date = cursor.getString(3);
                        VaccinationDo.dateymd = cursor.getString(4);
                        VaccinationDo.deleted = cursor.getString(5);
                        VaccinationDo.package_id = cursor.getString(6);
                        VaccinationDo.Action = cursor.getString(7);
                        if(VaccinationDo.package_id .equals("1"))
                            VaccinationDos1.add(VaccinationDo);
                        else if(VaccinationDo.package_id .equals("2"))
                            VaccinationDos2.add(VaccinationDo);
                        else if(VaccinationDo.package_id .equals("3"))
                            VaccinationDos3.add(VaccinationDo);
                    } while (cursor.moveToNext());

                    hmWP.put("1", VaccinationDos1);
                    hmWP.put("2", VaccinationDos2);
                    hmWP.put("3", VaccinationDos3);
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
                LogUtils.debug(LogUtils.LOG_TAG, "getVaccinationHealthPackages() - ended");
            }
            return hmWP;
        }
    }

}
