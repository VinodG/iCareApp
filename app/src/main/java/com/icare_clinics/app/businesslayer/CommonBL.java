package com.icare_clinics.app.businesslayer;

import android.content.Context;

import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.webaccessLayer.BaseWA;
import com.icare_clinics.app.webaccessLayer.BuildJsonRequest;
import com.icare_clinics.app.webaccessLayer.ServiceMethods;

public class CommonBL extends BaseBL {
    private Preference preference;

    public CommonBL(Context mContext, DataListener listener) {
        super(mContext, listener);
        preference = new Preference(mContext);
    }
//    public void downloadMasterTable(String url) {
//        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_DOWNLOAD_MASTER_TABLE, url);
//    }
//    public void dataSync(String CustomerId, String lastSync) {
//        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_DATASYNC, BuildJsonRequest.dataSyncQueryParams(CustomerId, lastSync));
//    }
    //************************icare******************************************
    public void syncData(String Action, String lastSyncDate) {
        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_SYNCDATA, BuildJsonRequest.dataSyncQueryParams(Action, lastSyncDate));
    }

    public void getClinicsList(String Action, String JsoneString) {
        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_CLINIC_LIST, BuildJsonRequest.jsonRequest(Action, JsoneString));
    }

    public void getSpecialistList(String Action, String JsoneString) {
        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_SPECIALIST_LIST, BuildJsonRequest.jsonRequest(Action, JsoneString));
    }

    public void getDoctorsList(String Action, String JsoneString) {
        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_DOCTOR_LIST, BuildJsonRequest.jsonRequest(Action, JsoneString));
    }
    public void requestAppointment(String Action,String gender, String strLocation,String doctorId,String email_id,String contact,String last_name,
                                   String auth_token,String appointmentDate,String specialityId,String appointmentTime,String fst_name,
                                   String country) {
        new BaseWA(mContext, this).startDataDownload(ServiceMethods.WS_REQUEST_APPOINTMENT, BuildJsonRequest.jsonRequestAppointment(
                Action,gender,strLocation,doctorId,email_id,contact,
                last_name,auth_token,appointmentDate,specialityId,appointmentTime,
                fst_name,country));
    }
}
