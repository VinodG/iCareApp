package com.icare_clinics.app.parser;

import android.content.Context;

import com.icare_clinics.app.webaccessLayer.ServiceMethods;

import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseJsonHandler extends DefaultHandler{

    protected int status;
    protected String message = "Some problem occured. Please contact Admin.";
    public boolean isError(){
        return status==0?true:false;
    }
    public abstract Object getData();
    public abstract void parse(String response);

    public static BaseJsonHandler getParser(ServiceMethods method, String respondsContent) {

        switch (method){
//            case WS_LOGIN:
//            case WS_SIGNUP:
//
//            case WS_OTP_MAIL:
//            case WS_RESEND_OTP:

            //*********Icare
            case WS_CLINIC_LIST:
                return new ClinicsListHandler();
            case WS_SPECIALIST_LIST:
                return new SpecialistListHandler();
            case WS_DOCTOR_LIST:
                return new DoctorsListHandler();
            case WS_REQUEST_APPOINTMENT:
                return new RequestAppointmentHandler();
           /* case WS_SYNCDATA:
                return new SyncDataParser();*/

        }
        return null;
    }

    public static BaseJsonHandler SyncDataParser(Context con){
        return new SyncDataParser(con);
    }
   /* public static BaseJsonHandler getDataSync(Context con){
        return new DataSyncParser(con);
    }*/

}
