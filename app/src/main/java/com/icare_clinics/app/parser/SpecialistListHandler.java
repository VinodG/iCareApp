package com.icare_clinics.app.parser;

import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by WINIT on 07-Jan-17.
 */

public class SpecialistListHandler extends BaseJsonHandler {
    private ArrayList<String> arrSpecilist;

    @Override
    public Object getData() {
         return status == 0 ? message : arrSpecilist;
    }

    @Override
    public void parse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getInt("MSG1");
            message = jsonObject.getString("MSG2");
            if (status != 0 && jsonObject.has("DATA")) {
                JSONArray jArrData = jsonObject.optJSONArray("DATA");
                arrSpecilist = new ArrayList<>();
                for (int i = 0; i < jArrData.length(); i++) {
                    JSONObject jsonObj = jArrData.optJSONObject(i);
                    String qualification = jsonObj.getString("qualification");
                    arrSpecilist.add(qualification);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug(LogUtils.LOG_TAG, e.getMessage());
        }

    }
}
