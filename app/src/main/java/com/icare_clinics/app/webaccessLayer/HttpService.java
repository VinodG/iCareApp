package com.icare_clinics.app.webaccessLayer;

import android.content.Context;

import com.icare_clinics.app.dataobject.ResponseDO;
import com.icare_clinics.app.parser.BaseJsonHandler;
import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import java.io.InputStream;


public class HttpService extends Thread
{
    private ServiceMethods method;
    private HttpListener httpListener;
    private String jsonRequest;
    private Context mContext;

    public HttpService(ServiceMethods method,HttpListener httpListener,String jsonRequest,Context mContext){
        this.method = method;
        this.httpListener = httpListener;
        this.jsonRequest = jsonRequest;
        this.mContext = mContext;
    }


    @Override
    public void run() {
        String respondsContent          = "";
        ResponseDO response             = new ResponseDO(method, true, "Unable to connect server, please try again.");
        InputStream inputStream         = null;
        RestCilent restCilent           = new RestCilent();
        try {

            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************");
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "WebService : " + String.valueOf(method));
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************");
            LogUtils.infoLog("Request Format : ", "" + jsonRequest);
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************");

            inputStream = restCilent.processRequest(method,jsonRequest);

            if (inputStream != null){
                BaseJsonHandler baseHandler = null;


                if(method == ServiceMethods.WS_SYNCDATA)
                    baseHandler = BaseJsonHandler.SyncDataParser(mContext);
                else
                    baseHandler = BaseJsonHandler.getParser(method, respondsContent);

               /* if((method == ServiceMethods.WS_DOWNLOAD) || (method == ServiceMethods.WS_DOWNLOAD_MASTER_TABLE)) {
                    response.method = method;
                    response.isError = false;
                    response.data = inputStream;
                }*/
                 if (baseHandler != null) {
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, String.valueOf(method) + " Parsing started");
                    String responseStr = StringUtils.convertStreamToString(inputStream);
                    baseHandler.parse(responseStr);
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, String.valueOf(method) + " Parsing completed");
//                    if (baseHandler.isError()){
                    response.method = method;
                    response.isError = baseHandler.isError();
                    response.data = baseHandler.getData();
                    /*}else{
                        response.isError = true;
                    }*/
                }else
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "JsonBaseParser  null");
            }else
                LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG , " InputStream is NULL ");
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.errorLog(LogUtils.SERVICE_LOG_TAG, e.getMessage());
        }finally {
            httpListener.onResponseReceived(response);
        }
    }
}