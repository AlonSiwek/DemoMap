package com.example.alonsiwek.demomap;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dor on 17-May-17.
 * Update the data base when user is walking
 */

public class PostIsRunning extends AsyncTask <Object , Void , String > {

    private Context context;
    private String user_id;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.user_id = this.context.getSharedPreferences("UserInfo",MODE_PRIVATE).getString("User_ID", "NO_ID");
    }


    @Override
    protected String doInBackground(Object... params) {

        String who_isRunning;
        try {
            who_isRunning = updateIsRunnig();
            Log.d("PostIsRunning" , "SUCCEED");
            return who_isRunning;

        } catch (IOException ex){
            Log.d("PostIsRunning", "fail to connect!");
            Log.d("PostIsRunning", "doInBackground: " + ex);
            return " FAIL ";
        } catch (JSONException ex){
            Log.d("PostIsRunning", "Problem with json");
            Log.d("PostIsRunning", "doInBackground" + ex );
            return " FAIL ";
        }
    }

    private String updateIsRunnig() throws IOException , JSONException{

        String urlPath = Constants.SERVER_URL + Constants.LOC_STATUS_PATH + this.user_id ;
                //String url_path = "https://walkiii.herokuapp.com/api/isRunning/return_isRunning/";


        // get the current location for the JSON
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (currentLocation != null) {
            Log.d("PostIsRunning", "the location is: " + currentLocation);
        } else {
            Log.d("PostIsRunning", "location is : NULL");
        }

        JSONObject user_is_running = new JSONObject();
        JSONObject inside_loc = new JSONObject();

        // Pass Long & Lat
        JSONArray coordinates = new JSONArray();
        coordinates.put(currentLocation.getLongitude());  //long
        coordinates.put(currentLocation.getLatitude());  //lat

        StringBuilder result_whoIsRunning = new StringBuilder();

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        // Posting
        try {
            //get the data of phone number, user name and user ID from SharedPreferences
            SharedPreferences getSharedPreff = context.getSharedPreferences("UserInfo",MODE_PRIVATE);
            String data_phone = getSharedPreff.getString("PhoneNumber" , " NUMBER IS NOT AVAILABLE");
            String data_name = getSharedPreff.getString("Name", "NAME IS NOT AVAILABLE");

            Log.d("PostIsRunning"," user phone : " + data_phone);
            Log.d("PostIsRunning", "user name : " + data_name);

            //Build JSON
            inside_loc.put("type","Point");
            inside_loc.put("coordinates",coordinates);
            user_is_running.put("loc",inside_loc);
            user_is_running.put("user_name", data_name);
            user_is_running.put("phone_number", data_phone);
            user_is_running.put("is_running", true);
            user_is_running.put("time", DateFormat.getDateTimeInstance().format(new Date()));

            // Open connection to the server
            // Open url for reading
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = null;

            // try to connect the url
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("PostIsRunning" , String.valueOf(urlConnection));
            } catch ( IOException e){
                Log.d("PostIsRunning" , String.valueOf(e));
            }

            urlConnection.setDoOutput(true);
            //set the time to read from url - in miliSec
            urlConnection.setReadTimeout(10000);
            //set time to be used when opening a communications link
            // to the resource referenced by this URLConnection connect to url
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            // enable output
            urlConnection.setDoOutput(true);
            //set header
            urlConnection.setRequestProperty("Content-Type","application/json");

            urlConnection.connect();
            Log.d("PostIsRunning","Connecting");

            //Post data to server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter((new OutputStreamWriter(outputStream)));
            bufferedWriter.write(user_is_running.toString());

            Log.d("PostIsRunning","written to server");

            bufferedWriter.flush();

            //Read the response from the server

            //////////////    FOR DEBUG - CATCH ERROR WHEN CONNECTING   ///////////////////////////////////////
            InputStream inputStream = null;

            try{
                inputStream = urlConnection.getInputStream();
                Log.d("PostIsRunning", " urlConnection.getInputStream() : " + String.valueOf(urlConnection.getInputStream()));
            } catch (IOException ioe){
                if ( urlConnection instanceof  HttpURLConnection){
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    int statusCode = httpURLConnection.getResponseCode();
                    Log.d("PostIsRunning", " error code number: " + String.valueOf(statusCode));
                    if (statusCode != 200){
                        inputStream = httpURLConnection.getErrorStream();
                        Log.d("PostIsRunning", String.valueOf(inputStream));
                    }
                }
            }
            //////////////////////////////////////////////////////////////////////////////
            Log.d("PostIsRunning","connection OK");

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                result_whoIsRunning.append(line).append("\n" );

                Log.d("PostIsRunning", String.valueOf(result_whoIsRunning));
            }
            // disconnect
            urlConnection.disconnect();
        } finally {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }

        }
        Log.d("PostIsRunning", String.valueOf(result_whoIsRunning));
        Log.d("PostIsRunning","return");

        return result_whoIsRunning.toString();
    }
}
