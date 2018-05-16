package com.icare_clinics.app.dataobject;

import java.util.ArrayList;

/**
 * Created by sudheer.jampana on 8/29/2016.
 */
public class DataSyncDO extends BaseDO {
    public int status      = 0;
    public String message   = "";
    public String serverTime = "";
    public int objStatus = 0;


    //*************I care***************
    public ArrayList<DoctorDo> arrDoctor=new ArrayList<>();
    public ArrayList<ClinicDO> arrClinic=new ArrayList<>();
    public ArrayList<AboutDo> arrAbout=new ArrayList<>();
    public ArrayList<BannerDo> arrBanner=new ArrayList<>();
    public ArrayList<BannerDo> arrGallery=new ArrayList<>();
    public ArrayList<BannerDo> arrVideos=new ArrayList<>();
    public ArrayList<AboutDo> arrTestimonial=new ArrayList<>();
    public ArrayList<InsuranceDo> arrInsurance=new ArrayList<>();
    public ArrayList<SpecializationDO> arrSpecialization=new ArrayList<>();
    public ArrayList<WellnessPackageDo> arrWellnessPackage=new ArrayList<>();
    public ArrayList<VaccinationDo> arrVaccination=new ArrayList<>();
    public  ArrayList<VaccinationDo> arrAntenatalPackage=new ArrayList<>();
    public  ArrayList<NewsDo> arrNews=new ArrayList<>();
   // public  ArrayList<UserDo> arrUser=new ArrayList<>();

}
