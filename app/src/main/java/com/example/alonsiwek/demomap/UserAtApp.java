package com.example.alonsiwek.demomap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserAtApp extends Service {

    String mAllUsers_asString = null;
    RecyclerView showUserData;

    public UserAtApp() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(UserAtApp.class.toString(), "service is started");
            acitvateThread();

    }

    private void acitvateThread(){
        // This Thread is for the connection - we cannot conect is service without Thread
        Thread t = new Thread(new Runnable() {

            public void run() {
                mAllUsers_asString = getAllUser();
                Log.d("UsersAtApp"," ******* FINAL JSON ******" + "\n" +
                mAllUsers_asString + "\n" +" ************************* ");
             }
        });
        t.start();

        // Pass the data string to MainPageFrag
        if (mAllUsers_asString != null){
            Intent in = new Intent();
            in.putExtra("DATA_OF_USERS",mAllUsers_asString);
            in.setAction("NOW");
            //sendBroadcast(in);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
        }
    }

    /**
     * the GET method
     * return - a string with all the users
     */
    String getAllUser () {
        String get_all_users_url = Constants.SERVER_URL + Constants.LOC_STATUS_PATH;


        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();

        try {
            // Open connection to the server
            // Open url for reading
            URL url = new URL(get_all_users_url);

            //TODO: at final app - chang HTTP or HTTPS !!!!!!!!!!!!!!!!!!!!!
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.d(UserAtApp.class.toString(), String.valueOf(urlConnection));
            } catch (IOException e) {
                Log.e(UserAtApp.class.toString(), String.valueOf(e));
            }


            //set the time to read from url - in miliSec
            urlConnection.setReadTimeout(10000);
            //set time to be used when opening a communications link
            // to the resource referenced by this URLConnection connect to url
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");

            //set header
            urlConnection.setRequestProperty("Content-Type", "application/json");

            try {
                urlConnection.connect();
            } catch (Exception e) {
                Log.e(UserAtApp.class.toString(), "Connect exception: " + e.toString());
                e.printStackTrace();
                return null;
            }
            Log.d(UserAtApp.class.toString(), "Connecting");


            //Read the response from the server
            //////////////    FOR DEBUG - CATCH ERROR WHEN CONNECTING     ///////////////////////////////////////
            InputStream inputStream = null;

            try {
                inputStream = urlConnection.getInputStream();
                Log.d(UserAtApp.class.toString(), " urlConnection.getInputStream() : " + String.valueOf(urlConnection.getInputStream()));
            } catch (IOException ioe) {
                Log.e(this.getClass().toString(), "Error getting response from server");
                ioe.printStackTrace();
            }
            //////////////////////////////////////////////////////////////////////////////
            Log.d(UserAtApp.class.toString(), "connection OK");

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");

            }
            // disconnect
            urlConnection.disconnect();
        } catch (ProtocolException e) {
            Log.e("UsersAtApp", e.toString());
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            Log.e("UsersAtApp", e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("UsersAtApp", e.toString());
            e.printStackTrace();
            return null;
        }

        return String.valueOf(result);
    }
}
