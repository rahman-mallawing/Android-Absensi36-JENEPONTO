package com.si.uinam.absensi36restfull.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApiHelper {

    private static final String BASE_URL = "https://rest.api3.annahdahcloudserver.com";
    private static final String IMG_BASE_URL = "https://rest.api3.annahdahcloudserver.com/identitas_files";
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

    private static int USER_ID = -1;
    private static String API_KEY = null;

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

    public static void setApiIdentity(Context context, int user_id, String api_key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("USER_ID",USER_ID);
        editor.putString("API_KEY",API_KEY);
        editor.apply();
        USER_ID = user_id;
        API_KEY = api_key;
    }

    private static void loadApiIdentity(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int USER_ID = preferences.getInt("USER_ID", -1);
        String API_KEY = preferences.getString("API_KEY", null);
    }

}
