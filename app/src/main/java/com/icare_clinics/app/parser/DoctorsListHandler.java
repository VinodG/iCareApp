package com.icare_clinics.app.parser;


import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.dataobject.DoctorDo;
import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by WINIT on 07-Jan-17.
 */

public class DoctorsListHandler extends BaseJsonHandler {
    private ArrayList<DoctorDo> arrDoctor;

    @Override
    public Object getData() {
        return status == 0 ? message : arrDoctor;
    }

    @Override
    public void parse(String response) {
        try {

            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getInt("MSG1");
            message = jsonObject.getString("MSG2");
            if (status != 0 && jsonObject.has("DATA")) {
                JSONArray jArrData = jsonObject.optJSONArray("DATA");
                arrDoctor = new ArrayList<>();
                for (int i = 0; i < jArrData.length(); i++) {
                    JSONObject jsonObj = jArrData.optJSONObject(i);
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
                    arrDoctor.add(doctorDo);
                }
                //loadDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug(LogUtils.LOG_TAG, e.getMessage());
        }

    }


}
