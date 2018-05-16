package com.icare_clinics.app.dataobject;

/**
 * Created by WINIT on 28-04-2017.
 */
public class ItemDo {
    public String name;
    public  boolean status=false;

    public ItemDo(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean getAStatus() {
        return status;
    }

}
