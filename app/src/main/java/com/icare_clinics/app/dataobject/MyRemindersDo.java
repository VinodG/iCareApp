package com.icare_clinics.app.dataobject;

/**
 * Created by Mallikarjuna.K on 05-01-2017.
 */
public class MyRemindersDo {

    public String MedicineName, Dosage, Days,Timings;
    public String Duration;

    public MyRemindersDo(String tvMedicineName, String tvDosage, String tvDays, String tvTimings,String tvDuration )
    {
        MedicineName=tvMedicineName;
        Dosage=tvDosage;
        Days=tvDays;
        Timings=tvTimings;
        Duration=tvDuration;
    }
  }
