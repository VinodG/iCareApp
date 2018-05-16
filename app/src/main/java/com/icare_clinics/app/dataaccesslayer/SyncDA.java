package com.icare_clinics.app.dataaccesslayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.icare_clinics.app.databaseaccess.DatabaseHelper;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.BannerDo;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.dataobject.VaccinationDo;
import com.icare_clinics.app.dataobject.WellnessPackageDo;
import com.icare_clinics.app.utilities.LogUtils;

import java.util.ArrayList;

/**
 * Created by Girish Velivela on 23-08-2016.
 */
public class SyncDA {

    private Context context;

    public SyncDA(Context context) {
        this.context = context;
    }

    public boolean insertDoctor(ArrayList<DoctorDo> arrDoctorDo) {
        LogUtils.debug(LogUtils.LOG_TAG, "ProductDOs-insertCustomer() - started");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblDoctor(id,name,qualification,location,details,photo,Degree,AreasOfExpertise,Languages,service,deleted) values(?,?,?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblDoctor set name=?,qualification=?,location=?,details=?,photo=?," +
                    "Degree=?,AreasOfExpertise=?, Languages=?, service=?,deleted=? where id =?");

            for (DoctorDo doctorDo : arrDoctorDo) {
                updateSqLiteStatement.bindString(1, doctorDo.name);
                updateSqLiteStatement.bindString(2, doctorDo.qualification);
                updateSqLiteStatement.bindString(3, doctorDo.location);
                updateSqLiteStatement.bindString(4, doctorDo.details);
                updateSqLiteStatement.bindString(5, doctorDo.photo);
                updateSqLiteStatement.bindString(6, doctorDo.Degree);
                updateSqLiteStatement.bindString(7, doctorDo.AreasOfExpertise);
                updateSqLiteStatement.bindString(8, doctorDo.Languages);
                updateSqLiteStatement.bindString(9, doctorDo.service);
                updateSqLiteStatement.bindString(10, doctorDo.deleted);
                updateSqLiteStatement.bindString(11, doctorDo.id);


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
                    insertSqLiteStatement.bindString(10, doctorDo.service);
                    insertSqLiteStatement.bindString(11, doctorDo.deleted);

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

    public boolean insertClinic(ArrayList<ClinicDO> arrClinic) {
        LogUtils.debug(LogUtils.LOG_TAG, "insert clinics() - started");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblClinic(id,code,description,contact,email,timing,add1,add2,add3,latitude,longitude,seo_url,seo_changed_url,seo_title,seo_description, \n" +
                    "seo_keywords,seo_h1,seo_alt_tag,seo_content,seo_page_name,date,deleted,sort,Banners750x374,Banners1242x620,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblClinic set code=?,description=?,contact=?,email=?,timing=?,add1=?,add2=?,add3=?,latitude=?,longitude=?,seo_url=?,seo_changed_url=?,seo_title=?,seo_description=?, \n" +
                    "seo_keywords=?,seo_h1=?,seo_alt_tag=?,seo_content=?,seo_page_name=?,date=?,deleted=?,sort=?,Banners750x374=?,Banners1242x620=?,Action=?  where id =?");

            for (ClinicDO clinicDO : arrClinic) {
                updateSqLiteStatement.bindString(1, clinicDO.code);
                updateSqLiteStatement.bindString(2, clinicDO.description);
                updateSqLiteStatement.bindString(3, clinicDO.contact);
                updateSqLiteStatement.bindString(4, clinicDO.email);
                updateSqLiteStatement.bindString(5, clinicDO.timing);
                updateSqLiteStatement.bindString(6, clinicDO.add1);
                updateSqLiteStatement.bindString(7, clinicDO.add2);
                updateSqLiteStatement.bindString(8, clinicDO.add3);
                updateSqLiteStatement.bindString(9, clinicDO.latitude);
                updateSqLiteStatement.bindString(10, clinicDO.longitude);
                updateSqLiteStatement.bindString(11, clinicDO.seo_url);
                updateSqLiteStatement.bindString(12, clinicDO.seo_changed_url);
                updateSqLiteStatement.bindString(13, clinicDO.seo_title);
                updateSqLiteStatement.bindString(14, clinicDO.seo_description);
                updateSqLiteStatement.bindString(15, clinicDO.seo_keywords);
                updateSqLiteStatement.bindString(16, clinicDO.seo_h1);
                updateSqLiteStatement.bindString(17, clinicDO.seo_alt_tag);
                updateSqLiteStatement.bindString(18, clinicDO.seo_content);
                updateSqLiteStatement.bindString(19, clinicDO.seo_page_name);
                updateSqLiteStatement.bindString(20, clinicDO.date);
                updateSqLiteStatement.bindString(21, clinicDO.deleted);
                updateSqLiteStatement.bindString(22, clinicDO.sort);
                updateSqLiteStatement.bindString(23, clinicDO.Banners750x374);
                updateSqLiteStatement.bindString(24, clinicDO.Banners1242x620);
                updateSqLiteStatement.bindString(25, clinicDO.Action);
                updateSqLiteStatement.bindString(26, clinicDO.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, clinicDO.id);
                    insertSqLiteStatement.bindString(2, clinicDO.code);
                    insertSqLiteStatement.bindString(3, clinicDO.description);
                    insertSqLiteStatement.bindString(4, clinicDO.contact);
                    insertSqLiteStatement.bindString(5, clinicDO.email);
                    insertSqLiteStatement.bindString(6, clinicDO.timing);
                    insertSqLiteStatement.bindString(7, clinicDO.add1);
                    insertSqLiteStatement.bindString(8, clinicDO.add2);
                    insertSqLiteStatement.bindString(9, clinicDO.add3);
                    insertSqLiteStatement.bindString(10, clinicDO.latitude);
                    insertSqLiteStatement.bindString(11, clinicDO.longitude);
                    insertSqLiteStatement.bindString(12, clinicDO.seo_url);
                    insertSqLiteStatement.bindString(13, clinicDO.seo_changed_url);
                    insertSqLiteStatement.bindString(14, clinicDO.seo_title);
                    insertSqLiteStatement.bindString(15, clinicDO.seo_description);
                    insertSqLiteStatement.bindString(16, clinicDO.seo_keywords);
                    insertSqLiteStatement.bindString(17, clinicDO.seo_h1);
                    insertSqLiteStatement.bindString(18, clinicDO.seo_alt_tag);
                    insertSqLiteStatement.bindString(19, clinicDO.seo_content);
                    insertSqLiteStatement.bindString(20, clinicDO.seo_page_name);
                    insertSqLiteStatement.bindString(21, clinicDO.date);
                    insertSqLiteStatement.bindString(22, clinicDO.deleted);
                    insertSqLiteStatement.bindString(23, clinicDO.sort);
                    insertSqLiteStatement.bindString(24, clinicDO.Banners750x374);
                    insertSqLiteStatement.bindString(25, clinicDO.Banners1242x620);
                    insertSqLiteStatement.bindString(26, clinicDO.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insert clinics() - ended");
        }
        return false;
    }

    //for About***********************
    public boolean insertAbout(ArrayList<AboutDo> arrAbout) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertAbout() - started");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblAbout(id,title,description,vision,bh_photo,bh_msg,ip,date,dateymd,deleted,Action) values(?,?,?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblAbout set title=?,description=?,vision=?,bh_photo=?,bh_msg=?,ip=?,date=?,dateymd=?,deleted=?,Action=? where id =?");
            for (AboutDo aboutDo : arrAbout) {
                updateSqLiteStatement.bindString(1, aboutDo.title);
                updateSqLiteStatement.bindString(2, aboutDo.description);
                updateSqLiteStatement.bindString(3, aboutDo.vision);
                updateSqLiteStatement.bindString(4, aboutDo.bh_photo);
                updateSqLiteStatement.bindString(5, aboutDo.bh_msg);
                updateSqLiteStatement.bindString(6, aboutDo.ip);
                updateSqLiteStatement.bindString(7, aboutDo.date);
                updateSqLiteStatement.bindString(8, aboutDo.dateymd);
                updateSqLiteStatement.bindString(9, aboutDo.deleted);
                updateSqLiteStatement.bindString(10, aboutDo.Action);
                updateSqLiteStatement.bindString(11, aboutDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, aboutDo.id);
                    insertSqLiteStatement.bindString(2, aboutDo.title);
                    insertSqLiteStatement.bindString(3, aboutDo.description);
                    insertSqLiteStatement.bindString(4, aboutDo.vision);
                    insertSqLiteStatement.bindString(5, aboutDo.bh_photo);
                    insertSqLiteStatement.bindString(6, aboutDo.bh_msg);
                    insertSqLiteStatement.bindString(7, aboutDo.ip);
                    insertSqLiteStatement.bindString(8, aboutDo.date);
                    insertSqLiteStatement.bindString(9, aboutDo.dateymd);
                    insertSqLiteStatement.bindString(10, aboutDo.deleted);
                    insertSqLiteStatement.bindString(11, aboutDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insertAbout - ended");
        }
        return false;
    }

    //for  tblTestimonial
    public boolean inserTestimonial(ArrayList<AboutDo> arrTestimonial) {
        LogUtils.debug(LogUtils.LOG_TAG, " inserTestimonial() - started");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblTestimonial(id,image,description,title,date,ip,dateymd,deleted,Action) values(?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblTestimonial set image=?,description=?,title=?,date=?,ip=?,dateymd=?,deleted=?,Action=? where id =?");
            for (AboutDo aboutDo : arrTestimonial) {
                updateSqLiteStatement.bindString(1, aboutDo.image);
                updateSqLiteStatement.bindString(2, aboutDo.description);
                updateSqLiteStatement.bindString(3, aboutDo.title);
                updateSqLiteStatement.bindString(4, aboutDo.date);
                updateSqLiteStatement.bindString(5, aboutDo.ip);
                updateSqLiteStatement.bindString(6, aboutDo.dateymd);
                updateSqLiteStatement.bindString(7, aboutDo.deleted);
                updateSqLiteStatement.bindString(8, aboutDo.Action);
                updateSqLiteStatement.bindString(9, aboutDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, aboutDo.id);
                    insertSqLiteStatement.bindString(2, aboutDo.image);
                    insertSqLiteStatement.bindString(3, aboutDo.description);
                    insertSqLiteStatement.bindString(4, aboutDo.title);
                    insertSqLiteStatement.bindString(5, aboutDo.date);
                    insertSqLiteStatement.bindString(6, aboutDo.ip);
                    insertSqLiteStatement.bindString(7, aboutDo.dateymd);
                    insertSqLiteStatement.bindString(8, aboutDo.deleted);
                    insertSqLiteStatement.bindString(9, aboutDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-inserTestimonial - ended");
        }
        return false;
    }

    //for  tblInsurance
    public boolean inserInsurance(ArrayList<InsuranceDo> arrInsurance) {
        LogUtils.debug(LogUtils.LOG_TAG, " inserInsurance() - start Insurance");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblInsurance(id,category,image,date,deleted,Action) values(?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblInsurance set category=?,image=?,date=?,deleted=?,Action=? where id =?");
            for (InsuranceDo insuranceDo : arrInsurance) {
                updateSqLiteStatement.bindString(1, insuranceDo.category);
                updateSqLiteStatement.bindString(2, insuranceDo.image);
                updateSqLiteStatement.bindString(3, insuranceDo.date);
                updateSqLiteStatement.bindString(4, insuranceDo.deleted);
                updateSqLiteStatement.bindString(5, insuranceDo.Action);
                updateSqLiteStatement.bindString(6, insuranceDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, insuranceDo.id);
                    insertSqLiteStatement.bindString(2, insuranceDo.category);
                    insertSqLiteStatement.bindString(3, insuranceDo.image);
                    insertSqLiteStatement.bindString(4, insuranceDo.date);
                    insertSqLiteStatement.bindString(5, insuranceDo.deleted);
                    insertSqLiteStatement.bindString(6, insuranceDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-inserInsurance - ended");
        }
        return false;
    }
    //*****************for tblBanner*************
    public boolean inserBanner(ArrayList<BannerDo> arrBanner) {
        LogUtils.debug(LogUtils.LOG_TAG, " inserBanner() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblBanner(id,image,seo_alt_tag,date,deleted,Action) values(?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblBanner set image=?,date=?,seo_alt_tag=?,deleted=?,Action=? where id =?");
            for (BannerDo bannerDo : arrBanner) {
                updateSqLiteStatement.bindString(1, bannerDo.image);
                updateSqLiteStatement.bindString(2, bannerDo.seo_alt_tag);
                updateSqLiteStatement.bindString(3, bannerDo.date);
                updateSqLiteStatement.bindString(4, bannerDo.deleted);
                updateSqLiteStatement.bindString(5, bannerDo.Action);
                updateSqLiteStatement.bindString(6, bannerDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, bannerDo.id);
                    insertSqLiteStatement.bindString(2, bannerDo.image);
                    insertSqLiteStatement.bindString(3, bannerDo.seo_alt_tag);
                    insertSqLiteStatement.bindString(4, bannerDo.date);
                    insertSqLiteStatement.bindString(5, bannerDo.deleted);
                    insertSqLiteStatement.bindString(6, bannerDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-inserBanner - ended");
        }
        return false;
    }
    //*****************for tblGallery*************
    public boolean inserGallery(ArrayList<BannerDo> arrGallery) {
        LogUtils.debug(LogUtils.LOG_TAG, " inserBanner() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblGallery(id,image,seo_alt_tag,date,deleted,Action) values(?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblGallery set image=?,date=?,seo_alt_tag=?,deleted=?,Action=? where id =?");
            for (BannerDo bannerDo : arrGallery) {
                updateSqLiteStatement.bindString(1, bannerDo.image);
                updateSqLiteStatement.bindString(2, bannerDo.seo_alt_tag);
                updateSqLiteStatement.bindString(3, bannerDo.date);
                updateSqLiteStatement.bindString(4, bannerDo.deleted);
                updateSqLiteStatement.bindString(5, bannerDo.Action);
                updateSqLiteStatement.bindString(6, bannerDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, bannerDo.id);
                    insertSqLiteStatement.bindString(2, bannerDo.image);
                    insertSqLiteStatement.bindString(3, bannerDo.seo_alt_tag);
                    insertSqLiteStatement.bindString(4, bannerDo.date);
                    insertSqLiteStatement.bindString(5, bannerDo.deleted);
                    insertSqLiteStatement.bindString(6, bannerDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-inserBanner - ended");
        }
        return false;
    }
    //*****************for tblVideos*************
    public boolean insertVideos(ArrayList<BannerDo> arrGallery) {
        LogUtils.debug(LogUtils.LOG_TAG, " inserBanner() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblVideos(id,link,seo_alt_tag,date,deleted,Action) values(?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblVideos set link=?,date=?,seo_alt_tag=?,deleted=?,Action=? where id =?");
            for (BannerDo bannerDo : arrGallery) {
                updateSqLiteStatement.bindString(1, bannerDo.link);
                updateSqLiteStatement.bindString(2, bannerDo.seo_alt_tag);
                updateSqLiteStatement.bindString(3, bannerDo.date);
                updateSqLiteStatement.bindString(4, bannerDo.deleted);
                updateSqLiteStatement.bindString(5, bannerDo.Action);
                updateSqLiteStatement.bindString(6, bannerDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, bannerDo.id);
                    insertSqLiteStatement.bindString(2, bannerDo.link);
                    insertSqLiteStatement.bindString(3, bannerDo.seo_alt_tag);
                    insertSqLiteStatement.bindString(4, bannerDo.date);
                    insertSqLiteStatement.bindString(5, bannerDo.deleted);
                    insertSqLiteStatement.bindString(6, bannerDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-inserBanner - ended");
        }
        return false;
    }
    //*****************for tblSpecialization*************
    public boolean inserSpecialization(ArrayList<SpecializationDO> arrSpecialization) {
        LogUtils.debug(LogUtils.LOG_TAG, " tblSpecialization() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblSpecialization(id,name,seo_name,SpecialtyBanner750x482,SpecialtyIcons241x262,SpecialtyIcons397x432,SpedcialtyBanner1242x798,in_menu,date,path,deleted,sort,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblSpecialization set name=?,seo_name=?,SpecialtyBanner750x482=?,SpecialtyIcons241x262=?,SpecialtyIcons397x432=?,SpedcialtyBanner1242x798=?,in_menu=?,date=?,path=?,deleted=?,sort=?,Action=? where id =?");
            for (SpecializationDO specializationDO : arrSpecialization) {
                updateSqLiteStatement.bindString(1, specializationDO.name);
                updateSqLiteStatement.bindString(2, specializationDO.seo_name);
                updateSqLiteStatement.bindString(3, specializationDO.SpecialtyBanner750x482);
                updateSqLiteStatement.bindString(4, specializationDO.SpecialtyIcons241x262);
                updateSqLiteStatement.bindString(5, specializationDO.SpecialtyIcons397x432);
                updateSqLiteStatement.bindString(6, specializationDO.SpedcialtyBanner1242x798);
                updateSqLiteStatement.bindString(7, specializationDO.in_menu);
                updateSqLiteStatement.bindString(8, specializationDO.date);
                updateSqLiteStatement.bindString(9, specializationDO.path);
                updateSqLiteStatement.bindString(10, specializationDO.deleted);
                updateSqLiteStatement.bindString(11, specializationDO.sort);
                updateSqLiteStatement.bindString(12, specializationDO.Action);
                updateSqLiteStatement.bindString(13, specializationDO.id);


                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, specializationDO.id);
                    insertSqLiteStatement.bindString(2, specializationDO.name);
                    insertSqLiteStatement.bindString(3, specializationDO.seo_name);
                    insertSqLiteStatement.bindString(4, specializationDO.SpecialtyBanner750x482);
                    insertSqLiteStatement.bindString(5, specializationDO.SpecialtyIcons241x262);
                    insertSqLiteStatement.bindString(6, specializationDO.SpecialtyIcons397x432);
                    insertSqLiteStatement.bindString(7, specializationDO.SpedcialtyBanner1242x798);
                    insertSqLiteStatement.bindString(8, specializationDO.in_menu);
                    insertSqLiteStatement.bindString(9, specializationDO.date);
                    insertSqLiteStatement.bindString(10, specializationDO.path);
                    insertSqLiteStatement.bindString(11, specializationDO.deleted);
                    insertSqLiteStatement.bindString(12, specializationDO.sort);
                    insertSqLiteStatement.bindString(13, specializationDO.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-tblSpecialization - ended");
        }
        return false;
    }

    //******************************Wellness_Package***********************************************


    public boolean insertWellnessPackage(ArrayList<WellnessPackageDo> arrWellnessPackage) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertWellnessPackage() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblWellnessPackage(id,name,ip,date,dateymd,deleted,plan,plan_type,Action) values(?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblWellnessPackage set name=?,ip=?,date=?,dateymd=?,deleted=?,plan=?,plan_type=?,Action=? where id =?");
            for (WellnessPackageDo wellnessPackageDo : arrWellnessPackage) {
                updateSqLiteStatement.bindString(1, wellnessPackageDo.name);
                updateSqLiteStatement.bindString(2, wellnessPackageDo.ip);
                updateSqLiteStatement.bindString(3, wellnessPackageDo.date);
                updateSqLiteStatement.bindString(4, wellnessPackageDo.dateymd);
                updateSqLiteStatement.bindString(5, wellnessPackageDo.deleted);
                updateSqLiteStatement.bindString(6, wellnessPackageDo.plan);
                updateSqLiteStatement.bindString(7, wellnessPackageDo.plan_type);
                updateSqLiteStatement.bindString(8, wellnessPackageDo.Action);
                updateSqLiteStatement.bindString(9, wellnessPackageDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, wellnessPackageDo.id);
                    insertSqLiteStatement.bindString(2, wellnessPackageDo.name);
                    insertSqLiteStatement.bindString(3, wellnessPackageDo.ip);
                    insertSqLiteStatement.bindString(4, wellnessPackageDo.date);
                    insertSqLiteStatement.bindString(5, wellnessPackageDo.dateymd);
                    insertSqLiteStatement.bindString(6, wellnessPackageDo.deleted);
                    insertSqLiteStatement.bindString(7, wellnessPackageDo.plan);
                    insertSqLiteStatement.bindString(8, wellnessPackageDo.plan_type);
                    insertSqLiteStatement.bindString(9, wellnessPackageDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insertWellnessPackage - ended");
        }
        return false;
    }

    //*******************************VaccinationPackage*****************************************

    public boolean insertVaccinationPackage(ArrayList<VaccinationDo> arrVaccinationPackage) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertVaccinationPackage() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblVaccinationPackage(id,package,ip,date,dateymd,deleted,package_id,Action) values(?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblVaccinationPackage set package=?,ip=?,date=?,dateymd=?,deleted=?,package_id=?,Action=? where id =?");
            for (VaccinationDo vaccinationDo : arrVaccinationPackage) {
                updateSqLiteStatement.bindString(1, vaccinationDo.packageVac);
                updateSqLiteStatement.bindString(2, vaccinationDo.ip);
                updateSqLiteStatement.bindString(3, vaccinationDo.date);
                updateSqLiteStatement.bindString(4, vaccinationDo.dateymd);
                updateSqLiteStatement.bindString(5, vaccinationDo.deleted);
                updateSqLiteStatement.bindString(6, vaccinationDo.package_id);
                updateSqLiteStatement.bindString(7, vaccinationDo.Action);
                updateSqLiteStatement.bindString(8, vaccinationDo.id);

                   if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, vaccinationDo.id);
                    insertSqLiteStatement.bindString(2, vaccinationDo.packageVac);
                    insertSqLiteStatement.bindString(3, vaccinationDo.ip);
                    insertSqLiteStatement.bindString(4, vaccinationDo.date);
                    insertSqLiteStatement.bindString(5, vaccinationDo.dateymd);
                    insertSqLiteStatement.bindString(6, vaccinationDo.deleted);
                    insertSqLiteStatement.bindString(7, vaccinationDo.package_id);
                    insertSqLiteStatement.bindString(8, vaccinationDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insertVaccinationPackage - ended");
        }
        return false;
    }

    //**********************************AntenatalPackage***************************************

    public boolean insertAntenatalPackage(ArrayList<VaccinationDo> arrAntenatalPackage) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertAntenatalPackage() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblAntenatalPackage(id,package,ip,date,dateymd,deleted,package_id,Action) values(?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblAntenatalPackage set package=?,ip=?,date=?,dateymd=?,deleted=?,package_id=?,Action=? where id =?");
            for (VaccinationDo vaccinationDo : arrAntenatalPackage) {
                updateSqLiteStatement.bindString(1, vaccinationDo.packageVac);
                updateSqLiteStatement.bindString(2, vaccinationDo.ip);
                updateSqLiteStatement.bindString(3, vaccinationDo.date);
                updateSqLiteStatement.bindString(4, vaccinationDo.dateymd);
                updateSqLiteStatement.bindString(5, vaccinationDo.deleted);
                updateSqLiteStatement.bindString(6, vaccinationDo.package_id);
                updateSqLiteStatement.bindString(7, vaccinationDo.Action);
                updateSqLiteStatement.bindString(8, vaccinationDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, vaccinationDo.id);
                    insertSqLiteStatement.bindString(2, vaccinationDo.packageVac);
                    insertSqLiteStatement.bindString(3, vaccinationDo.ip);
                    insertSqLiteStatement.bindString(4, vaccinationDo.date);
                    insertSqLiteStatement.bindString(5, vaccinationDo.dateymd);
                    insertSqLiteStatement.bindString(6, vaccinationDo.deleted);
                    insertSqLiteStatement.bindString(7, vaccinationDo.package_id);
                    insertSqLiteStatement.bindString(8, vaccinationDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insertAntenatalPackage - ended");
        }
        return false;
    }
    //*************************for tblNews***************************************************
    public boolean insertNews(ArrayList<NewsDo> arrNews) {
        LogUtils.debug(LogUtils.LOG_TAG, " insertNews() - start ");
        SQLiteDatabase sqLiteDatabase = new DatabaseHelper(context).openDataBase();
        SQLiteStatement insertSqLiteStatement = null;
        SQLiteStatement updateSqLiteStatement = null;
        try {
            insertSqLiteStatement = sqLiteDatabase.compileStatement("insert into tblNews(id,title,description,image,ip,date,dateymd,deleted,Action) values(?,?,?,?,?,?,?,?,?)");

            updateSqLiteStatement = sqLiteDatabase.compileStatement("update tblNews set title=?,description=?,image=?,ip=?,date=?,dateymd=?,deleted=?,Action=? where id =?");
            for (NewsDo newsDo : arrNews) {
                updateSqLiteStatement.bindString(1, newsDo.title);
                updateSqLiteStatement.bindString(2, newsDo.description);
                updateSqLiteStatement.bindString(3, newsDo.image);
                updateSqLiteStatement.bindString(4, newsDo.ip);
                updateSqLiteStatement.bindString(5, newsDo.date);
                updateSqLiteStatement.bindString(6, newsDo.dateymd);
                updateSqLiteStatement.bindString(7, newsDo.deleted);
                updateSqLiteStatement.bindString(8, newsDo.Action);
                updateSqLiteStatement.bindString(9, newsDo.id);

                if (updateSqLiteStatement.executeUpdateDelete() <= 0) {
                    insertSqLiteStatement.bindString(1, newsDo.id);
                    insertSqLiteStatement.bindString(2, newsDo.title);
                    insertSqLiteStatement.bindString(3, newsDo.description);
                    insertSqLiteStatement.bindString(4, newsDo.image);
                    insertSqLiteStatement.bindString(5, newsDo.ip);
                    insertSqLiteStatement.bindString(6, newsDo.date);
                    insertSqLiteStatement.bindString(7, newsDo.dateymd);
                    insertSqLiteStatement.bindString(8, newsDo.deleted);
                    insertSqLiteStatement.bindString(9, newsDo.Action);
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
            LogUtils.debug(LogUtils.LOG_TAG, "-insertNews - ended");
        }
        return false;
    }

    //*******************************************above are related to iCare****************************************************************************
}
