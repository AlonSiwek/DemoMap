package com.example.alonsiwek.demomap;

import android.content.SharedPreferences;

/**
 * Created by Dor on 19-May-17.
 * Constants of the app:
 * 1) URL
 * 2) USER DATA OF: name , id , phone
 */

public final class Constants {
<<<<<<< HEAD
    final static String SERVER_URL = "https://walkiii.herokuapp.com"; //
=======
    final static String SERVER_URL = "https://walkiii.herokuapp.com"; // http://192.168.56.1:8080
>>>>>>> 3d37c9333d0cc75f99b60b5f63cc150e251b585d
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
