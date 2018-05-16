package com.icare_clinics.app.webaccessLayer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;


public class BuildJsonRequest {

    private final static String SOAP_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<soap:Body>";

    private final static String SOAP_FOOTER = "</soap:Body>" +
            "</soap:Envelope>";

    public static String loginRequest(String emailId, String mobile, String password, String loginType, String deviceId, String language) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email", emailId);
            jsonObject.put("Password", password);
            jsonObject.put("MobileNumber", mobile);
            jsonObject.put("LoginType", loginType);
            jsonObject.put("Hash1", "");
            jsonObject.put("Hash2", "");
            jsonObject.put("DeviceId", deviceId);
            jsonObject.put("PreferedLanguage", language);
            jsonObject.put("DeviceType", "Android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String otpVerify(String mobile, String otp, String loginType) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OTP", otp);
            jsonObject.put("MobileNumber", mobile);
            jsonObject.put("LoginType", loginType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();

    }

    public static String otpSendMail(String email, String otpstring) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?Email=").append(email).append("&Name=").append(otpstring);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    public static String masterTableQueryParams(String customerCode, String lastSyncDateTime) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?CustomerId=").append(customerCode).append("&LastSyncDateTime=").append(lastSyncDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    public static String resendOTPQueryParams(String mailId, String mobile, String type) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?Email=").append(mailId).append("&MobileNumber=").append(mobile).append("&Type=").append(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    public static String getOrderQueryParams(String UserId, String AuthToken, String OrderType, String Timeline, String SearchText,
                                             String OrderStatus, String LastSyncDateTime) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?UserId=").append(UserId)
                    .append("&AuthToken=").append(AuthToken)
                    .append("&OrderType=").append(OrderType)
                    .append("&Timeline=").append(Timeline)
                    .append("&SearchText=").append(SearchText)
                    .append("&OrderStatus=").append(OrderStatus)
                    .append("&LastSyncDateTime=").append(URLEncoder.encode(LastSyncDateTime, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    public static String verifyOTPRequest(String email, String otpstring) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MobileNumber", email);
            jsonObject.put("VerificationCode", otpstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String cartListRequest(String sessionId) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?SessionId=").append(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String removeFromCart(String sessionId, int shoppingCartId, int userId) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("ShoppingCartIds", shoppingCartId + "");
            jsonObject.put("SessionId", sessionId);
            jsonObject.put("UserId", userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Request", jsonObject.toString());
        return jsonObject.toString();
    }



    public static String getForgetPasswordRequest(String emailId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email", emailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }



    public static String repeatOrder(String trxCode, String userId) {
        JSONObject mainjsonObject = new JSONObject();
        try {
            mainjsonObject.put("TrxCode", trxCode);
            mainjsonObject.put("CustomerId", userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainjsonObject.toString();
    }

    public static String recurrOrder(String trxCode, String frequency) {
        JSONObject mainjsonObject = new JSONObject();
        try {
            mainjsonObject.put("TrxCode", trxCode);
            mainjsonObject.put("Frequency", frequency);
            mainjsonObject.put("NumberOfRepeatations", 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainjsonObject.toString();
    }

    public static String cancelRecurring(String trxCode, String date) {
        JSONObject mainjsonObject = new JSONObject();
        try {
            mainjsonObject.put("TrxCode", trxCode);
            mainjsonObject.put("RemovedFromRecurringOn", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainjsonObject.toString();
    }

    public static String feedbackRequest(int userId, String category, String rating, String comments, String email, String deviceId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category", category);
            jsonObject.put("rating", rating);
            jsonObject.put("comments", comments);
            jsonObject.put("email", email);
            jsonObject.put("DeviceType", "Android");
            jsonObject.put("DeviceId", deviceId);
            jsonObject.put("CustomerId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /*public static String dataSyncQueryParams(String CustomerId, String LastSyncDateTime) {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("?CustomerId=").append(CustomerId).append("&LastSyncDateTime=").append(URLEncoder.encode(LastSyncDateTime, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();

    }*/

    //************************************icare****************************

    public static String dataSyncQueryParams(String action, String LastSyncDateTime) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("lastSyncDate", LastSyncDateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonBuild(action, jsonObject.toString());
    }

    public static String jsonRequest(String action, String Auth) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("auth_token", Auth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonBuild(action, jsonObject.toString());
    }

    public static String jsonRequestAppointment(String action,String gender, String strLocation,String doctorId,String email_id,String contact,String last_name,
                                                String auth_token,String appointmentDate,String specialityId,String appointmentTime,String fst_name,
                                                String country) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("gender", gender);
            jsonObject.put("location", strLocation);
            jsonObject.put("doctor", doctorId);
            jsonObject.put("email_id", email_id);
            jsonObject.put("contact", contact);
            jsonObject.put("last_name", last_name);
            jsonObject.put("auth_token", auth_token);
            jsonObject.put("req_date", appointmentDate);
            jsonObject.put("speciality", specialityId);
            jsonObject.put("req_time", appointmentTime);
            jsonObject.put("first_name", fst_name);
            jsonObject.put("country", country);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonBuild(action, jsonObject.toString());
    }

    // this is common for all service build
    public static String commonBuild(String action, String JsoneString) {
        StringBuilder builder = new StringBuilder();
        builder.append("Action=" + action + "&JsoneString=" + JsoneString);
        return builder.toString();
    }

}