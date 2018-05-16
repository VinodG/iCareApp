package com.icare_clinics.app.dataobject;

/**
 * Created by Sreekanth.P on 21-06-2017.
 */

public class WaterDO extends BaseDO{

    public String id="";
    public String strWater="";
    public String imgBottle="";
    public String date="";
    public String time="";

    public WaterDO() {

    }
    public WaterDO(String strMl,int ivBottleId){
        this.strWater=strMl;
        this.imgBottle=String.valueOf(ivBottleId);
    }

}
