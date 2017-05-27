package com.example.alonsiwek.demomap;

import android.content.SharedPreferences;

/**
 * Created by Dor on 19-May-17.
 */

public final class Constants {
    final static String SERVER_URL = "http://192.168.56.1:8080"; //
    final static String LOC_STATUS_PATH = "/api/locationStatus/";
    static String user_id = null;
    static String user_name = null;
    static String user_phone = null;

    static void loadSharedPrefs(SharedPreferences preferences)
    {
        Constants.user_id = preferences.getString("uid", null);

        Constants.user_name = preferences.getString("PhoneNumber",null);
        Constants.user_phone = preferences.getString("Name",null);
    }
}
