package com.example.alonsiwek.demomap;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dor on 27-May-17.
 */

public class DisplayRunnersOnMap extends AsyncTask <Void,Void,String> {

    Context mContext;
    View mView;
    int viewID;

    DisplayRunnersOnMap() {}

    DisplayRunnersOnMap(Context context,View view, int viewID){
        this.mContext = context;
        this.mView = view;
        this.viewID = viewID;
    }


    @Override
    protected String doInBackground(Void... params) {

        String isNull = getAllRunners();
        if (isNull == null){
            Log.e(this.getClass().toString(),  "Error fetching users");
            return null;
        }

        return isNull;
    }

    String getAllRunners (){

        String IS_RUNNING = "?is_running=true";

        String get_all_users_url = Constants.SERVER_URL + Constants.LOC_STATUS_PATH + IS_RUNNING;


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
            Log.e("DisplayRunnersOnMap", e.toString());
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            Log.e("DisplayRunnersOnMap", e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("DisplayRunnersOnMap", e.toString());
            e.printStackTrace();
            return null;
        }

        return String.valueOf(result);
    }

    protected void onPostExecute(String result) {
        if (result == null) {
            return;
        }

        Parser.parseUsers(result);

        RecyclerView mRecyleView = (RecyclerView) this.mView.findViewById(this.viewID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mContext);
        mRecyleView.setLayoutManager(layoutManager);

        Log.d("DisplayRunnersOnMap","in parser and the josn string: " + "\n" + result.toString() );
        try {
            ArrayList<UserData> data = Parser.parseUsers(result);
            // Setup and Handover data to recyclerview
            AdapterUsers mAdapter = new AdapterUsers(mContext, data);
            mRecyleView.setAdapter(mAdapter);
            mRecyleView.setLayoutManager
                    (new LinearLayoutManager(this.mContext , LinearLayoutManager.HORIZONTAL , false));
            mAdapter.notifyDataSetChanged();
        }

        catch (Exception e){
            Log.e("UserAtApp","Exception at parser:" + e.toString());
            return;
        }

    }




}
