package com.icare_clinics.app.webaccessLayer;

import android.util.Log;
import android.webkit.MimeTypeMap;

import com.icare_clinics.app.utilities.LogUtils;
import com.icare_clinics.app.utilities.StringUtils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class HttpHelper {

    private final String LOG_TAG = "HttpHelper";

    private int TIMEOUT_CONNECT_MILLIS = (1*60*1000);
    private int TIMEOUT_READ_MILLIS = (30*1000);

    public InputStream sendRequest(String requestUrl,String method,Map<String,String> headers,String postData){

        LogUtils.debug(LOG_TAG, "sendRequest - Started");
        LogUtils.debug(LOG_TAG, "requestUrl - "+requestUrl);
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(TIMEOUT_CONNECT_MILLIS);
            connection.setReadTimeout(TIMEOUT_READ_MILLIS);
            connection.setDoInput(true);
            if(headers != null && headers.size() >0){
                Set<String> keySet = headers.keySet();
                for(String key : keySet)
                    connection.setRequestProperty(key,headers.get(key));
            }
            if(!StringUtils.isEmpty(postData)){
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();
            }
            int httpCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();
            LogUtils.debug(LOG_TAG,"Response Code - "+httpCode);
            LogUtils.debug(LOG_TAG,"Response Message - "+resMsg);
            return connection.getInputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            LogUtils.debug(LOG_TAG,"sendRequest - Ended");
        }
        return null;
    }


    public String uploadImage(String baseUrl,String customerId,String file){

        URL url;
        HttpURLConnection connection = null;

        try {

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            url = new URL(baseUrl+"Upload.aspx?CustomerId="+customerId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            File uploadFile = new File(file);

            FileInputStream fileInputStream = new FileInputStream(uploadFile);
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            MimeTypeMap map = MimeTypeMap.getSingleton();

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append(twoHyphens + boundary + lineEnd)
                    .append("Content-Disposition: form-data; name=\"photo\";filename=\"" + uploadFile.getName() + "\"")
                    .append(lineEnd)
                    .append("Content-Type: "+map.getMimeTypeFromExtension(StringUtils.getExtentionOfFile(file)))
                    .append(lineEnd + lineEnd);

            LogUtils.infoLog("upload", stringBuffer.toString());
            writer.writeBytes(stringBuffer.toString());
            byte[] buffer = new byte[1024];
            int length;

            while ((length = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
                writer.write(buffer, 0, length);
            }
            writer.writeBytes(lineEnd + lineEnd);
            writer.writeBytes(twoHyphens + boundary + twoHyphens);
            writer.flush();
            writer.close();

            int httpCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();
            LogUtils.debug(LOG_TAG,"Response Code - "+httpCode);
            LogUtils.debug(LOG_TAG,"Response Message - "+resMsg);
            if(httpCode == 200){
                String responseStr = StringUtils.convertStreamToString(connection.getInputStream());
                Log.e("responseStr",responseStr);

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource src = new InputSource();
                src.setCharacterStream(new StringReader(responseStr));

                Document doc = builder.parse(src);
                String message = doc.getElementsByTagName("Message").item(0).getTextContent();
                String Url = doc.getElementsByTagName("FileName").item(0).getTextContent();

                if(!StringUtils.isEmpty(message) && message.equalsIgnoreCase("successful"))
                {
                    return Url;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }


    public void close(InputStream inputStream){
        LogUtils.debug(LOG_TAG, "close - Started");
        if(inputStream != null){
            try {
                inputStream.close();
            }catch (Exception e){
                LogUtils.errorLog(LOG_TAG, e.toString());
                e.printStackTrace();
            }
        }
        LogUtils.debug(LOG_TAG, "close - Ended");
    }

}
