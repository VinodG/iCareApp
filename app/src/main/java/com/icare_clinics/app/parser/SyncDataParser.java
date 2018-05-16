package com.icare_clinics.app.parser;

import android.content.Context;

import com.icare_clinics.app.dataaccesslayer.SyncDA;
import com.icare_clinics.app.dataobject.AboutDo;
import com.icare_clinics.app.dataobject.BannerDo;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DataSyncDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.dataobject.InsuranceDo;
import com.icare_clinics.app.dataobject.NewsDo;
import com.icare_clinics.app.dataobject.SpecializationDO;
import com.icare_clinics.app.dataobject.VaccinationDo;
import com.icare_clinics.app.dataobject.WellnessPackageDo;
import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by WINIT on 25-Jan-17.
 */

public class SyncDataParser extends BaseJsonHandler {
    private DataSyncDO dataSyncDO = new DataSyncDO();
    private Context mContext;
    private SyncDA mSyncDa;
   // private DoctorDA mSyncDa;
    public  SyncDataParser (Context context){
        this.mContext = context;
       // this.mSyncDa = new DoctorDA(mContext);
       this.mSyncDa = new SyncDA(mContext);
    }
    @Override
    public Object getData() {
        return status == 0 ? message : dataSyncDO;

    }

    @Override
    public void parse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            status=jsonObject.getInt("MSG1");
            dataSyncDO.status = status;
            dataSyncDO.message = jsonObject.getString("MSG2");
            if (status != 0 && jsonObject.has("DATA")) {
                JSONObject jObj=jsonObject.getJSONObject("DATA");
                JSONObject jObjData=jObj.getJSONObject("MasterDetails");
                dataSyncDO.serverTime=jObjData.getString("server_time");

                //*****************************doctorsList***********************
                JSONArray jsonArrDoctor = jObjData.optJSONArray("doctors");
                for (int i = 0; i < jsonArrDoctor.length(); i++) {
                    JSONObject jsonObj = jsonArrDoctor.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        DoctorDo doctorDo = new DoctorDo();
                        doctorDo.id = jsonObj.getString("id");
                        doctorDo.name = jsonObj.getString("name");
                        doctorDo.qualification = jsonObj.getString("qualification");
                        doctorDo.location = jsonObj.getString("location");
                        doctorDo.details = jsonObj.getString("details");
                        doctorDo.photo = jsonObj.getString("photo");
                        doctorDo.Degree = jsonObj.getString("Degree");
                        doctorDo.AreasOfExpertise = jsonObj.getString("AreasOfExpertise");
                        doctorDo.Languages = jsonObj.getString("Languages");
                        doctorDo.service = jsonObj.getString("service");
                        doctorDo.seo_url = jsonObj.getString("seo_url");
                        doctorDo.seo_changed_url = jsonObj.getString("seo_changed_url");
                        doctorDo.seo_title = jsonObj.getString("seo_title");
                        doctorDo.seo_description = jsonObj.getString("seo_description");
                        doctorDo.seo_keywords = jsonObj.getString("seo_keywords");
                        doctorDo.seo_h1 = jsonObj.getString("seo_h1");
                        doctorDo.seo_alt_tag = jsonObj.getString("seo_alt_tag");
                        doctorDo.seo_content = jsonObj.getString("seo_content");
                        doctorDo.seo_page_name = jsonObj.getString("seo_page_name");
                        doctorDo.date = jsonObj.getString("date");
                        doctorDo.deleted = jsonObj.getString("deleted");
                        doctorDo.sort = jsonObj.getString("sort");
                        doctorDo.FeartureArticles = jsonObj.getString("FeartureArticles");
                        doctorDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrDoctor.add(doctorDo);
                    }
                }

                //*************************ClinicsList***************************
                JSONArray jsonArrClinics = jObjData.optJSONArray("clinics");
                for(int i=0;i<jsonArrClinics.length();i++) {
                    JSONObject jsonObj = jsonArrClinics.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        ClinicDO clinicDO = new ClinicDO();
                        clinicDO.id = jsonObj.getString("id");
                        clinicDO.code = jsonObj.getString("code");
                        clinicDO.description = jsonObj.getString("description");
                        clinicDO.contact = jsonObj.getString("contact");
                        clinicDO.email = jsonObj.getString("email");
                        clinicDO.timing = jsonObj.getString("timing");
                        clinicDO.add1 = jsonObj.getString("add1");
                        clinicDO.add2 = jsonObj.getString("add2");
                        clinicDO.add3 = jsonObj.getString("add3");
                        clinicDO.latitude = jsonObj.getString("latitude");
                        clinicDO.longitude = jsonObj.getString("longitude");
                        clinicDO.seo_url = jsonObj.getString("seo_url");
                        clinicDO.seo_changed_url = jsonObj.getString("seo_changed_url");
                        clinicDO.seo_title = jsonObj.getString("seo_title");
                        clinicDO.seo_description = jsonObj.getString("seo_description");
                        clinicDO.seo_keywords = jsonObj.getString("seo_keywords");
                        clinicDO.seo_h1 = jsonObj.getString("seo_h1");
                        clinicDO.seo_alt_tag = jsonObj.getString("seo_alt_tag");
                        clinicDO.seo_content = jsonObj.getString("seo_content");
                        clinicDO.seo_page_name = jsonObj.getString("seo_page_name");
                        clinicDO.date = jsonObj.getString("date");
                        clinicDO.deleted = jsonObj.getString("deleted");
                        clinicDO.sort = jsonObj.getString("sort");
                        clinicDO.Banners750x374 = jsonObj.getString("Banners750x374");
                        clinicDO.Banners1242x620 = jsonObj.getString("Banners1242x620");
                        clinicDO.Action = jsonObj.getString("Action");
                        dataSyncDO.arrClinic.add(clinicDO);
                    }
                }
                //****************************about*************************************
                JSONArray jsonArrAbout = jObjData.optJSONArray("about");
                for(int i=0;i<jsonArrAbout.length();i++) {
                    JSONObject jsonObj = jsonArrAbout.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        AboutDo aboutDo = new AboutDo();
                        aboutDo.id = jsonObj.getString("id");
                        aboutDo.title = jsonObj.getString("title");
                        aboutDo.description = jsonObj.getString("description");
                        aboutDo.vision = jsonObj.getString("vision");
                        aboutDo.bh_photo = jsonObj.getString("bh_photo");
                        aboutDo.bh_msg = jsonObj.getString("bh_msg");
                        aboutDo.ip = jsonObj.getString("ip");
                        aboutDo.date = jsonObj.getString("date");
                        aboutDo.dateymd = jsonObj.getString("dateymd");
                        aboutDo.deleted = jsonObj.getString("deleted");
                        aboutDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrAbout.add(aboutDo);
                    }
                }
            //*************************Banner****************************************
                JSONArray jsonArrBanner = jObjData.optJSONArray("banner");
                for(int i=0;i<jsonArrBanner.length();i++){
                    JSONObject jsonObj = jsonArrBanner.optJSONObject(i);
                    if(Integer.parseInt(jsonObj.getString("deleted"))==0) {
                        BannerDo bannerDo = new BannerDo();
                        bannerDo.id = jsonObj.getString("id");
                        bannerDo.image = jsonObj.getString("image");
                        bannerDo.seo_alt_tag = jsonObj.getString("seo_alt_tag");
                        bannerDo.date = jsonObj.getString("date");
                        bannerDo.deleted = jsonObj.getString("deleted");
                        bannerDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrBanner.add(bannerDo);
                    }
                }
                //*************************testimonial****************************************
                JSONArray jsonArrTestimonial = jObjData.optJSONArray("testimonial");
                for(int i=0;i<jsonArrTestimonial.length();i++) {
                    JSONObject jsonObj = jsonArrTestimonial.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        AboutDo aboutDo = new AboutDo();
                        aboutDo.id = jsonObj.getString("id");
                        aboutDo.image = jsonObj.getString("image");
                        aboutDo.description = jsonObj.getString("description");
                        aboutDo.title = jsonObj.getString("title");
                        aboutDo.date = jsonObj.getString("date");
                        aboutDo.ip = jsonObj.getString("ip");
                        aboutDo.dateymd = jsonObj.getString("dateymd");
                        aboutDo.deleted = jsonObj.getString("deleted");
                        aboutDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrTestimonial.add(aboutDo);
                    }
                }
                //*************************insurance****************************************
                JSONArray jsonArrInsurance = jObjData.optJSONArray("insurance");
                for(int i=0;i<jsonArrInsurance.length();i++){
                    JSONObject jsonObj = jsonArrInsurance.optJSONObject(i);

                    if(Integer.parseInt(jsonObj.getString("deleted"))==0) {
                        InsuranceDo insuranceDo = new InsuranceDo();
                        insuranceDo.id = jsonObj.getString("id");
                        insuranceDo.category = jsonObj.getString("category");
                        insuranceDo.image = jsonObj.getString("image");
                        insuranceDo.date = jsonObj.getString("date");
                        insuranceDo.deleted = jsonObj.getString("deleted");
                        insuranceDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrInsurance.add(insuranceDo);
                    }
                }
                //*************************specialization****************************************
                JSONArray jsonArrSpecialization = jObjData.optJSONArray("specialization");
                for(int i=0;i<jsonArrSpecialization.length();i++) {
                    JSONObject jsonObj = jsonArrSpecialization.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        SpecializationDO specializationDO = new SpecializationDO();
                        specializationDO.id = jsonObj.getString("id");
                        specializationDO.name = jsonObj.getString("name");
                        specializationDO.seo_name = jsonObj.getString("seo_name");
                        specializationDO.SpecialtyBanner750x482 = jsonObj.getString("SpecialtyBanner750x482");
                        specializationDO.SpecialtyIcons241x262 = jsonObj.getString("SpecialtyIcons241x262");
                        specializationDO.SpecialtyIcons397x432 = jsonObj.getString("SpecialtyIcons397x432");
                        specializationDO.SpedcialtyBanner1242x798 = jsonObj.getString("SpedcialtyBanner1242x798");
                        specializationDO.in_menu = jsonObj.getString("in_menu");
                        specializationDO.date = jsonObj.getString("date");
                        specializationDO.path = jsonObj.getString("path");
                        specializationDO.sort = jsonObj.getString("sort");
                        specializationDO.deleted = jsonObj.getString("deleted");
                        specializationDO.Action = jsonObj.getString("Action");
                        dataSyncDO.arrSpecialization.add(specializationDO);
                    }
                }

                //*************************gallery****************************************
                JSONArray jsonArrGallery = jObjData.optJSONArray("gallery");
                for(int i=0;i<jsonArrGallery.length();i++) {
                    JSONObject jsonObj = jsonArrGallery.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        BannerDo bannerDo = new BannerDo();
                        bannerDo.id = jsonObj.getString("id");
                        bannerDo.image = jsonObj.getString("image");
                        bannerDo.seo_alt_tag = jsonObj.getString("seo_alt_tag");
                        bannerDo.date = jsonObj.getString("date");
                        bannerDo.deleted = jsonObj.getString("deleted");
                        bannerDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrGallery.add(bannerDo);
                    }
                }

                //****************************wellness_package*******************************
                JSONArray jsonArrWellnessPackage=jObjData.optJSONArray("wellness_package");
                for(int i=0;i<jsonArrWellnessPackage.length();i++)
                {
                    JSONObject jsonObj = jsonArrWellnessPackage.optJSONObject(i);
                    if(Integer.parseInt(jsonObj.getString("deleted"))==0) {
                        WellnessPackageDo wellnessPackageDo = new WellnessPackageDo();
                        wellnessPackageDo.id = jsonObj.getString("id");
                        wellnessPackageDo.name = jsonObj.getString("name");
                        wellnessPackageDo.ip = jsonObj.getString("ip");
                        wellnessPackageDo.date = jsonObj.getString("date");
                        wellnessPackageDo.dateymd = jsonObj.getString("dateymd");
                        wellnessPackageDo.deleted = jsonObj.getString("deleted");
                        wellnessPackageDo.plan = jsonObj.getString("plan");
                        wellnessPackageDo.plan_type = jsonObj.getString("plan_type");
                        wellnessPackageDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrWellnessPackage.add(wellnessPackageDo);

                    }
                }

              //**************************Vaccination_Package************************
                JSONArray jsonArrVaccinationPackage=jObjData.optJSONArray("vaccination_package");
                  for(int i=0;i<jsonArrVaccinationPackage.length();i++) {
                      JSONObject jsonObj = jsonArrVaccinationPackage.optJSONObject(i);
                      if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                          VaccinationDo vaccinationDo = new VaccinationDo();
                          vaccinationDo.id = jsonObj.getString("id");
                          vaccinationDo.packageVac = jsonObj.getString("package");
                          vaccinationDo.ip = jsonObj.getString("ip");
                          vaccinationDo.date = jsonObj.getString("date");
                          vaccinationDo.dateymd = jsonObj.getString("dateymd");
                          vaccinationDo.deleted = jsonObj.getString("deleted");
                          vaccinationDo.package_id = jsonObj.getString("package_id");
                          vaccinationDo.Action = jsonObj.getString("Action");
                          dataSyncDO.arrVaccination.add(vaccinationDo);
                      }
                  }
                //****************************Antenatal_package*******************************
                JSONArray jsonArrAntenatalPackage=jObjData.optJSONArray("antenatal_package");
                for(int i=0;i<jsonArrAntenatalPackage.length();i++) {
                    JSONObject jsonObj = jsonArrAntenatalPackage.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        VaccinationDo vaccinationDo = new VaccinationDo();
                        vaccinationDo.id = jsonObj.getString("id");
                        vaccinationDo.packageVac = jsonObj.getString("package");
                        vaccinationDo.ip = jsonObj.getString("ip");
                        vaccinationDo.date = jsonObj.getString("date");
                        vaccinationDo.dateymd = jsonObj.getString("dateymd");
                        vaccinationDo.deleted = jsonObj.getString("deleted");
                        vaccinationDo.package_id = jsonObj.getString("package_id");
                        vaccinationDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrAntenatalPackage.add(vaccinationDo);
                    }
                }
                //****************************videos*******************************

                JSONArray jsonArrVideos=jObjData.optJSONArray("videos");
                for(int i=0;i<jsonArrVideos.length();i++) {
                    JSONObject jsonObj = jsonArrVideos.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        BannerDo bannerDo = new BannerDo();
                        bannerDo.id = jsonObj.getString("id");
                        bannerDo.link = jsonObj.getString("link");
                        bannerDo.seo_alt_tag = jsonObj.getString("seo_alt_tag");
                        bannerDo.date = jsonObj.getString("date");
                        bannerDo.deleted = jsonObj.getString("deleted");
                        bannerDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrVideos.add(bannerDo);
                    }
                }

              //***************************NEWS***************************************************
                JSONArray jsonArrNews=jObjData.optJSONArray("news");
                for(int i=0;i<jsonArrNews.length();i++) {
                    JSONObject jsonObj = jsonArrNews.optJSONObject(i);
                    if (Integer.parseInt(jsonObj.getString("deleted")) == 0) {
                        NewsDo newsDo = new NewsDo();
                        newsDo.id = jsonObj.getString("id");
                        newsDo.title = jsonObj.getString("title");
                        newsDo.description = jsonObj.getString("description");
                        newsDo.image = jsonObj.getString("image");
                        newsDo.ip = jsonObj.getString("ip");
                        newsDo.date = jsonObj.getString("date");
                        newsDo.dateymd = jsonObj.getString("dateymd");
                        newsDo.deleted = jsonObj.getString("deleted");
                        newsDo.Action = jsonObj.getString("Action");
                        dataSyncDO.arrNews.add(newsDo);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug(LogUtils.LOG_TAG, e.getMessage());
        }finally {
            mSyncDa.insertDoctor(dataSyncDO.arrDoctor);
            mSyncDa.insertClinic(dataSyncDO.arrClinic);
            mSyncDa.insertAbout(dataSyncDO.arrAbout);
            mSyncDa.inserTestimonial(dataSyncDO.arrTestimonial);
            mSyncDa.inserInsurance(dataSyncDO.arrInsurance);
            mSyncDa.inserBanner(dataSyncDO.arrBanner);
            mSyncDa.inserGallery(dataSyncDO.arrGallery);
            mSyncDa.inserSpecialization(dataSyncDO.arrSpecialization);
            mSyncDa.insertAntenatalPackage(dataSyncDO.arrAntenatalPackage);
            mSyncDa.insertVaccinationPackage(dataSyncDO.arrVaccination);
            mSyncDa.insertWellnessPackage(dataSyncDO.arrWellnessPackage);
            mSyncDa.insertNews(dataSyncDO.arrNews);
            mSyncDa.insertVideos(dataSyncDO.arrVideos);

        }

    }
}
