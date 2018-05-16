package com.icare_clinics.app.parser;

import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by WINIT on 06-Jan-17.
 */

public class ClinicsListHandler extends BaseJsonHandler {
    private ArrayList<ClinicDO> arrClinic;

    @Override
    public void parse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getInt("MSG1");
            message = jsonObject.getString("MSG2");
            if (status != 0 && jsonObject.has("DATA")) {
                JSONArray jArrData = jsonObject.optJSONArray("DATA");
                arrClinic = new ArrayList<>();
                for (int i = 0; i < jArrData.length(); i++) {
                    JSONObject jClinic = jArrData.optJSONObject(i);
                    ClinicDO clinicDo = new ClinicDO();
                    clinicDo.id = jClinic.getString("id");
                    clinicDo.code = jClinic.getString("code");
                    clinicDo.description = jClinic.getString("description");
                    clinicDo.contact = jClinic.getString("contact");
                    clinicDo.email = jClinic.getString("email");
                    clinicDo.timing = jClinic.getString("timing");
                    clinicDo.add1 = jClinic.getString("add1");
                    clinicDo.add2 = jClinic.getString("add2");
                    clinicDo.add3 = jClinic.getString("add3");
                    clinicDo.latitude = jClinic.getString("latitude");
                    clinicDo.longitude = jClinic.getString("longitude");
                    clinicDo.seo_url = jClinic.getString("seo_url");
                    clinicDo.seo_changed_url = jClinic.getString("seo_changed_url");
                    clinicDo.seo_title = jClinic.getString("seo_title");
                    clinicDo.seo_description = jClinic.getString("seo_description");
                    clinicDo.seo_keywords = jClinic.getString("seo_keywords");
                    clinicDo.seo_h1 = jClinic.getString("seo_h1");
                    clinicDo.seo_alt_tag = jClinic.getString("seo_alt_tag");
                    clinicDo.seo_content = jClinic.getString("seo_content");
                    clinicDo.seo_page_name = jClinic.getString("seo_page_name");
                    clinicDo.date = jClinic.getString("date");
                    clinicDo.deleted = jClinic.getString("deleted");
                    clinicDo.sort = jClinic.getString("sort");
                    arrClinic.add(clinicDo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug(LogUtils.LOG_TAG, e.getMessage());
        }

    }

    @Override
    public Object getData() {
        return status == 0 ? message : arrClinic;
    }

}
