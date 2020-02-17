package com.si.uinam.absensi36restfull.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.UserModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ApiHelper {

    private static final String BASE_URL = "https://rest.api3.annahdahcloudserver.com";
    private static final String IMG_BASE_URL = "https://rest.api3.annahdahcloudserver.com/identitas_files/";
    private static final String IMG_POSTER_PLACEHOLDER = "https://via.placeholder.com/500x750.jpg";

    public static String getImgPosterPlaceholder() {
        return IMG_POSTER_PLACEHOLDER;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getImgBaseUrl() {
        return IMG_BASE_URL;
    }

    public static void saveTanggal(Context context, int year, int month, int day){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        Date tgl = new GregorianCalendar(year, month - 1, day).getTime();
        String savedTgl = df.format(tgl);

        editor.putBoolean("DATE_SET", true);
        editor.putString("TANGGAL",savedTgl);
        editor.apply();
    }

    public static void resetTanggal(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("DATE_SET");
        editor.remove("TANGGAL");
        editor.apply();
    }

    public static String getTanggal(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String tgl = preferences.getString("TANGGAL", "");
        //Log.d("LOG-USER","CEK LOGIN:"+isLoggedActive);
        return tgl;
    }

    public static boolean isTanggal(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLogged = preferences.getBoolean("DATE_SET", false);
        //Log.d("LOG-USER","CEK LOGIN:"+isLoggedActive);
        return isLogged;
    }

    public static void saveUser(Context context, UserModel user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_LOGGED", true);
        editor.putInt("USER_ID",user.getId());
        editor.putString("API_KEY",user.getApiToken());
        editor.putString("API_NAME",user.getNamaOperator());
        editor.putString("API_EMAIL",user.getEmail());
        editor.apply();
        Log.d("LOG-USER",user.getName());
    }

    public static void invalidate(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("IS_LOGGED");
        editor.remove("USER_ID");
        editor.remove("API_KEY");
        editor.remove("API_NAME");
        editor.remove("API_EMAIL");
        editor.apply();
        Log.d("LOG-USER","INVALIDATE");
    }

    public static boolean isLogged(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLoggedActive = preferences.getBoolean("IS_LOGGED", false);
        Log.d("LOG-USER","CEK LOGIN:"+isLoggedActive);
        return isLoggedActive;
    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int USER_ID = preferences.getInt("USER_ID", -1);
        Log.d("LOG-USER","GetUserID:"+USER_ID);
        return USER_ID;
    }

    public static String getApiKey(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String API_KEY = preferences.getString("API_KEY", null);
        Log.d("LOG-USER","GET API KEY: "+API_KEY);
        return API_KEY;
    }

    public static String getApiName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String API_NAME = preferences.getString("API_NAME", null);
        return API_NAME;
    }

    public static String getApiEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String API_EMAIL = preferences.getString("API_EMAIL", null);
        return API_EMAIL;
    }


}
