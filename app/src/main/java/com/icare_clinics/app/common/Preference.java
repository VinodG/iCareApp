package com.icare_clinics.app.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.icare_clinics.app.dataobject.SetReminderDo;
import com.icare_clinics.app.dataobject.WaterDO;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.attr.id;


@SuppressLint("CommitPrefEdits") public class Preference {

	private SharedPreferences preferences;
	private SharedPreferences.Editor edit;
	public static String LANGUAGE = "LANGUAGE";
	public static String CUSTOMER_ID = "CUSTOMERID";
	public static String CUSTOMER_CODE = "CUSTOMER_CODE";
	public static String LAST_ORDERS_SYNC = "LastSyncDateTime";
	public static String SESSION_ID = "SESSION_ID";
	public static String CUSTOMER_NAME = "NAME";
	public static String CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
	public static String DEVICE_DISPLAY_WIDTH = "DEVICE_DISPLAY_WIDTH";
	public static String DEVICE_DISPLAY_HEIGHT = "DEVICE_DISPLAY_HEIGHT";
	public static String FIRST_TIME_DISPLAY = "IS_FIRST_TIME";
	public static String IS_LOGGED_IN = "Is Logged in";
	public static String NOTIFICATION_FLAG = "notificationFlag";
	public static String CityName = "CityName";
	public static String TEMPERATURE = "TEMPERATURE";
	public static String TEMPERATURE_ICON = "TEMP ICON ID";
	public static String Latitude = "Latitude";
	public static String Longitude = "Longitude";
	public static String USER_IMAGE = "USER IMAGE";
	public static String OTP = "OTP";
	public static String ISSINGUP = "ISSINGUP";
	public static String SHOWDELIVERY = "DeliveryPopup";
	public static String DELIVERY_DAYS = "DELIVERYDAYS";
	public static String IS_DISCLAIMER = "DISCLAIMER";
	public static String GCM_TOKEN = "GCM_TOKEN";
	public static String IS_SYNCING = "IS_SYNCING";
	public static String LOCALE_UPDATED = "LocaleUpdated";
	public static String HYDRATION_TIME_PERIOD = "HYDRATION_TIME_PERIOD";

	public static String EMAIL_ID = "EMAILID";
	public static String MOBILE_NUM = "MOBILE_NUM";
	public static String NAME = "NAME";
	public static String LASTNAME = "LASTNAME";
	public static String GENDER = "GENDER";
	public static String DOB = "DOB";
	public static String HEIGHT = "HEIGHT";
	public static String WEIGHT = "WEIGHT";
	public static String MM = "MM";
	public static String DD = "DD";
	public static String YY = "YY";
	public static String REQ_TIME = "REQ_TIME";
	public static String REQ_YY = "REQ_YY";
	public static String HEIGHT_MEASUREMENT = "HEIGHT_MEASUREMENT";
	public static String WEIGHT_MEASUREMENT = "WEIGHT_MEASUREMENT";
	public static String DAILY_TARGET = "DAILY_TARGET";
	public static String WATER_COMSUMED = "WATER_COMSUMED";
	public static String PERCENTAGE = "PERCENTAGE";
	public static String SELECTED_DATE = "SELECTED_DATE";
	public static String SELECTEDDATE = "SELECTEDDATE";
	public static String BMI = "BMI";
	public static String RL_HEIGHT = "RL_HEIGHT";
	public static String LIST = "LIST";
	public static String MANUAL_LIST = "MANUAL_LIST";
	public static String TEMP_LIST = "TEMP_LIST";
	public static String IS_AUTOMATIC_REMAINDER = "IS_AUTOMATIC_REMAINDER";
	public static String IS_MANUAL_REMAINDER = "IS_MANUAL_REMAINDER";
	public static String STEPS_COUNT = "STEPS_COUNT";
	public static String TARGET = "TARGET";
	public static String BMI_WIDTH = "BMI_WIDTH";
	public static String START_TIME = "START_TIME";
	public static String END_TIME = "END_TIME";
	public static String DATE_WATER_IN_TAKE_GRAPH = "DATE_WATER_IN_TAKE_GRAPH";
	public static String MONTH = "MONTH";
	public static String IS_REMAINDER_SET = "IS_REMAINDER_SET";
	public static String NEW_DAY_TARGET = "NEW_DAY_TARGET";
	public static String IS_FIRST_TIME = "IS_FIRST_TIME";


	public Preference(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		edit = preferences.edit();
	}

	public void saveStringInPreference(String strKey, String strValue) {
		edit.putString(strKey, strValue);
		commitPreference();
	}

	public void saveIntInPreference(String strKey, int value) {
		edit.putInt(strKey, value);
		commitPreference();
	}

	public void saveBooleanInPreference(String strKey, boolean value) {
		edit.putBoolean(strKey, value);
		commitPreference();
	}

	public void saveLongInPreference(String strKey, Long value) {
		edit.putLong(strKey, value);
		commitPreference();
	}

	public void saveDoubleInPreference(String strKey, String value) {
		edit.putString(strKey, value);
		commitPreference();
	}

	/*public void saveListInPreference(String strKey, ArrayList<WaterDO> arrList) {
		*//*for(int i=0;i<arrList.size();i++)
		{
			edit.putString("strKey"+i, String.valueOf(arrList.get(i)));
		}
		edit.putInt(strKey+"size",arrList.size());
		edit.commit();*//*
		Gson gson = new Gson();
		String json = gson.toJson(arrList);
		edit.putString("strKey", json);
		edit.commit();
	}*/
	public void saveListInPreference(String strKey, Object obj, int typeDO) {//ArrayList<SetReminderDo> arrList) {
		/*for(int i=0;i<arrList.size();i++)
		{
			edit.putString("strKey"+i, String.valueOf(arrList.get(i)));
		}
		edit.putInt(strKey+"size",arrList.size());
		edit.commit();*/
		Gson gson = new Gson();
		String json;
		switch(typeDO)
		{
			case  0:

				ArrayList<WaterDO> arrList = (ArrayList<WaterDO>)obj;
				json = gson.toJson(arrList);

				break;
			default:
//			case  1:
				ArrayList<SetReminderDo> arrlist = (ArrayList<SetReminderDo>)obj;
				json = gson.toJson(arrlist);break;
		}
		edit.putString(strKey, json);
		edit.commit();
	}

	public void removeFromPreference(String strKey) {
		edit.remove(strKey);
		commitPreference();
	}

	private void commitPreference() {
		edit.commit();
	}

	public String getStringFromPreference(String strKey, String defaultValue) {
		return preferences.getString(strKey, defaultValue);
	}

	public boolean getbooleanFromPreference(String strKey, boolean defaultValue) {
		return preferences.getBoolean(strKey, defaultValue);
	}

	public int getIntFromPreference(String strKey, int defaultValue) {
		return preferences.getInt(strKey, defaultValue);
	}

	public double getDoubleFromPreference(String strKey, double defaultValue) {
		return Double.parseDouble(preferences.getString(strKey, "" + defaultValue));
	}

	public long getLongInPreference(String strKey) {
		return preferences.getLong(strKey, 0);
	}

	/*public ArrayList<WaterDO> getListFromPreference(String strKey) {

		Gson gson = new Gson();
		String json = preferences.getString("strKey", "");
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
		ArrayList<WaterDO> list = new ArrayList<WaterDO>();
		Gson gsonObj = new Gson();

		for (int i = 0; i < jsonArray.size(); i++) {
			WaterDO waterDO=new WaterDO();
			waterDO = gsonObj.fromJson(jsonArray.get(i).toString(), WaterDO.class);
			list.add((WaterDO) waterDO);
		}
		return list;
	}*/
	public Object getListFromPreference(String strKey,int typeDO) {

		Gson gson = new Gson();
		String json = preferences.getString(strKey, "");
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(json);

		Gson gsonObj = new Gson();

		switch(typeDO)
		{
			case  0:  // 0 for storage of WaterDO
				ArrayList<WaterDO> list = new ArrayList<WaterDO>();
				for (int i = 0; i < jsonArray.size(); i++) {
					WaterDO waterDO = new WaterDO();
					waterDO = gsonObj.fromJson(jsonArray.get(i).toString(), WaterDO.class);
					list.add((WaterDO) waterDO);
				}
				return list;
			default: // 1 for storage of SetReminderDo
				 ArrayList<SetReminderDo> arrlist = new ArrayList<SetReminderDo>();
				for (int i = 0; i < jsonArray.size(); i++) {
//			case  1:
					SetReminderDo setReminderDo = new SetReminderDo();
					setReminderDo = gsonObj.fromJson(jsonArray.get(i).toString(), SetReminderDo.class);
					arrlist.add((SetReminderDo) setReminderDo);
				}
				return arrlist;
		}
	}

	public void clearPreferences() {
		edit.clear();
		edit.commit();
	}
}
