package com.icare_clinics.app.parser;

import android.content.Context;

import com.icare_clinics.app.dataobject.RequestDo;
import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONObject;

/**
 * Created by sudheer.jampana on 1/12/2017.
 */

public class RequestAppointmentHandler extends BaseJsonHandler{
    private RequestDo requestDo = new RequestDo();
    private Context mContext;
    /*public  RequestAppointmentHandler (Context context){
        //this.mContext = context;
        // this.mSyncDa = new DoctorDA(mContext);

    }*/
    @Override
    public Object getData() {
        return status == 0 ? message : message;
    }

    @Override
    public void parse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getInt("MSG1");
            message = jsonObject.getString("MSG2");
            if(status!=0)
            {
               RequestDo requestDo=new RequestDo();
                requestDo.message=jsonObject.getString("MSG2");
                // ((AppointmentForm)mContext) showCustomDialog(hashCode(), getResources().getString(R.string.alert), getResources().getString(R.string.request_sent), getResources().getString(R.string.ok), "", "");
            }

        }catch (Exception e){
            e.printStackTrace();
            LogUtils.debug(LogUtils.LOG_TAG,e.getMessage());
        }

    }
}
