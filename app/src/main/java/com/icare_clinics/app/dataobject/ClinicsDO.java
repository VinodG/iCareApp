package com.icare_clinics.app.dataobject;

/**
 * Created by Mallikarjuna.K on 27-Dec-16.
 */

public class ClinicsDO {
    public int ivHospital;
    public String tvplace,tvAddress,tvTimings,tvWeekDay;
    public int ivDirection;

    public ClinicsDO(){

    }
    public ClinicsDO(int ivHospital,String tvplace,String tvAddress,String tvTimings,String tvFriday){

        this.ivHospital=ivHospital;
        this.tvplace=tvplace;
        this.tvWeekDay=tvFriday;
        this.tvAddress=tvAddress;
        this.tvTimings=tvTimings;
    }
    public String getTvplace(){
        return tvplace;
    }
    public String getTvAddress(){
        return tvAddress;
    }
    public String getTvTimings(){
        return tvTimings;
    }
    public String getTvWeekDay(){
        return tvWeekDay;
    }
    public int getIvHospital(){
        return ivHospital;
    }
    public int getIvDirection(){
        return ivDirection;
    }
}
