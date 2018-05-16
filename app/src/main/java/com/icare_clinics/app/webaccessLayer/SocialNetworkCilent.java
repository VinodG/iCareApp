package com.icare_clinics.app.webaccessLayer;

import android.util.Base64;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.utilities.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class SocialNetworkCilent {

    private final String tag = "SocialNetworkCilent";

    public String prepareQueryParameters(TreeMap params){
        String parameterString = "";
        Set<String> keys = params.keySet();
        for (String key : keys){
            if(parameterString.equalsIgnoreCase("")){
                parameterString+= key +"="+params.get(key);
            }else
                parameterString+= "&"+key +"="+params.get(key);
        }
        return parameterString;
    }

    public String urlEncode(String value){
        String encoded = "";
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }

    private String encode(String value) {
        String encoded = "";
        encoded = urlEncode(value);
        String sb = "";
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                sb += "%2A";
            } else if (focus == '+') {
                sb += "%20";
            } else if (focus == '%' && i + 1 < encoded.length()
                    && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                sb += '~';
                i += 2;
            } else {
                sb += focus;
            }
        }
        return sb.toString();
    }

    private String generateSignature(String signatueBaseStr, String oAuthConsumerSecret, String oAuthTokenSecret) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec;
            if (null == oAuthTokenSecret) {
                String signingKey = encode(oAuthConsumerSecret) + '&';
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            } else {
                String signingKey = encode(oAuthConsumerSecret) + '&' + encode(oAuthTokenSecret);
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            }
            mac.init(spec);
            byteHMAC = mac.doFinal(signatueBaseStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteHMAC, 0).trim();
    }

    public String prepareKeyValue(TreeMap params){
        String parameterString = "";
        Set<String> keys = params.keySet();
        for (String key : keys){
            parameterString += key + "=\"" + params.get(key) + "\",";
        }
        return parameterString.substring(0, parameterString.length() - 1);
    }

    public String[] parseResponse(String response){
        String[] responseArr = response.split("&");
        if(responseArr != null){
            int length = responseArr.length;
            String[] tokenArr = new String[length];
            for (int i=0;i<length;i++) {
                tokenArr[i] = responseArr[i].split("=")[1];
            }
            return tokenArr;
        }else{
            return null;
        }
    }

    public String[] getGooglePlusAcessToken(String authCode){
        LogUtils.debug(tag,"getGooglePlusAcessToken - Started");
        InputStream is = null;
        try {

            TreeMap<String,String> params = new TreeMap<>();
            params.put("client_id", AppConstants.GOOGLEPLUS_CLIENT_ID);
            params.put("client_secret", AppConstants.GOOGLEPLUS_CLIENT_SECRET);
            params.put("grant_type", "authorization_code");
            params.put("redirect_uri", urlEncode(AppConstants.REDIRECT_URL));
            params.put("code", urlEncode(authCode));

            String baseUrl = AppConstants.AUTH_URL;
            TreeMap<String,String> headers = new TreeMap<>();
            headers.put("content-type", "application/x-www-form-urlencoded");
            is = new HttpHelper().sendRequest(baseUrl,AppConstants.POST,headers,prepareQueryParameters(params));
            if(is != null) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                if (jsonObject.has("access_token") && jsonObject.has("refresh_token")) {
                    String[] tokens = new String[2];
                    tokens[0] = jsonObject.getString("access_token");
                    tokens[1] = jsonObject.getString("refresh_token");
                    return tokens;
                } else if (jsonObject.has("error_description")) {
                    LogUtils.debug(tag, jsonObject.getString("error_description"));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is != null)
                    is.close();
            }catch (Exception e){
                LogUtils.debug(tag,"Exception in closing the inputstream");
            }
            LogUtils.debug(tag,"getGooglePlusAcessToken - Ended");
        }
        return null;
    }

    public String[] getRequestToken(){

        int nonce = (int) (Math.random() * 100000000);
        long timeStamp = (System.currentTimeMillis() / 1000);

        TreeMap<String,String> params = new TreeMap<>();
        params.put("oauth_callback",urlEncode(AppConstants.REDIRECT_URL));
        params.put("oauth_consumer_key",AppConstants.TWITTER_CLIENT_ID);
        params.put("oauth_nonce",nonce+"");
        params.put("oauth_signature_method","HMAC-SHA1");
        params.put("oauth_timestamp",timeStamp+"");
        params.put("oauth_version",1.0+"");

        String parameter_string = prepareQueryParameters(params);

        String baseURl = AppConstants.TWITTER_Request_Token_URL;
        String signatueBaseStr = "POST&"+urlEncode(baseURl)+"&"+encode(parameter_string);
        params.put("oauth_signature", encode(generateSignature(signatueBaseStr, AppConstants.TWITTER_CLIENT_SECRET, null)));

        TreeMap<String,String> headers = new TreeMap<>();
        headers.put("Authorization", "OAuth " + prepareKeyValue(params));
        try {
            InputStream is = new HttpHelper().sendRequest(baseURl, AppConstants.POST, headers, null);
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return parseResponse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
