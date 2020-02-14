package com.si.uinam.absensi36restfull.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.si.uinam.absensi36restfull.models.UserModel;

public class ApiHelper {

    private static final String BASE_URL = "https://rest.api3.annahdahcloudserver.com";
    private static final String IMG_BASE_URL = "https://rest.api3.annahdahcloudserver.com/identitas_files/";
    private static final String IMG_POSTER_PLACEHOLDER = "https://via.placeholder.com/500x750.jpg";

    private static int USER_ID;
    private static String API_KEY;
    private static String API_NAME;
    private static String API_EMAIL;

    public static int getTokenId() {
        return USER_ID;
    }

    public static String getToken() {
        return API_KEY;
    }

    public static String getImgPosterPlaceholder() {
        return IMG_POSTER_PLACEHOLDER;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getImgBaseUrl() {
        return IMG_BASE_URL;
    }

    //private static int USER_ID = -1;
    //private static String API_KEY = null;

    public static int getUserId(Context context) {
        if(USER_ID > -1){
            return  USER_ID;
        }
        loadApiIdentity(context);
        return USER_ID;
    }

    public static String getApiKey(Context context) {
        if(API_KEY != null){
            return  API_KEY;
        }
        loadApiIdentity(context);
        return API_KEY;
    }

    public static void saveUser(Context context, UserModel user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("USER_ID",user.getId());
        editor.putString("API_KEY",user.getApiToken());
        editor.putString("API_NAME",user.getNamaOperator());
        editor.putString("API_EMAIL",user.getEmail());
        editor.apply();
        USER_ID = user.getId();
        API_KEY = user.getApiToken();
        API_NAME = user.getNamaOperator();
        API_EMAIL = user.getEmail();
    }

    public static void invalidate(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("USER_ID");
        editor.remove("API_KEY");
        editor.remove("API_NAME");
        editor.remove("API_EMAIL");
        USER_ID = 0;
        API_KEY = "";
        API_NAME = "";
        API_EMAIL = "";
        editor.apply();
    }

    public static void simpleInvalidate(){
        USER_ID = 0;
        API_KEY = "";
        API_NAME = "";
        API_EMAIL = "";
    }

    public static int getUserId() {
        return USER_ID;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getApiName() {
        return API_NAME;
    }

    public static String getApiEmail() {
        return API_EMAIL;
    }

    private static void loadApiIdentity(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int USER_ID = preferences.getInt("USER_ID", -1);
        String API_KEY = preferences.getString("API_KEY", null);
    }

}
