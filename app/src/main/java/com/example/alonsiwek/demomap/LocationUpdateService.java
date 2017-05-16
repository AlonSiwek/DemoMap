package com.example.alonsiwek.demomap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.LocationServices;

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
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;


/**
 * POST the location all the time that the app is running
 */
public class LocationUpdateService extends Service {

    //dummy constructor
    public LocationUpdateService(){}

    private Context ctx;

    //Only use for the get the context of the app for SharedPreferences
    public LocationUpdateService(Context context){
        super();
        this.ctx = context;
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("LocationUpdateService", "service is started");
        try {
            getDeviceLocationForLocationService();
        } catch (IOException e) {
            Log.d("LocationUpdateService", "IOException: " + e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("LocationUpdateService", "JSONException: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Gets the current location of the device
     */
    private void getDeviceLocationForLocationService() throws IOException, JSONException {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (currentLocation != null) {
            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: " + currentLocation);
        } else {
            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: null");
        }

        // to get LATER On the specific user location we need user id
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserInfo",MODE_PRIVATE);
        String userID = sharedPreferences.getString("User_ID" ," NO ID");
        String userName = sharedPreferences.getString("Name"," NO-NAME");
        String userPhone = sharedPreferences.getString("PhoneNumber","NO-PHONE-NUMBER");

        // validation check
        Log.d("LocationUpdateService", "user id: " + userID);
        Log.d("LocationUpdateService", "user name: " + userName);
        Log.d("LocationUpdateService", "user phone: " + userPhone);


        // TODO: change HTTP / HTTPS

        final JSONObject LocationUpdateServiceOnTheRun = new JSONObject();
        JSONObject inside_loc = new JSONObject();

        // Pass Long & Lat
        JSONArray coordinates = new JSONArray();
        coordinates.put(currentLocation.getLongitude());  //long
        coordinates.put(currentLocation.getLatitude());  //lat

        LocationUpdateServiceOnTheRun.put("user_name",userName);
        LocationUpdateServiceOnTheRun.put("user_phone",userPhone);
        LocationUpdateServiceOnTheRun.put("user_id",userID);
        LocationUpdateServiceOnTheRun.put("time", DateFormat.getDateTimeInstance().format(new Date()));

        Log.d("LocationUpdateService", "time in JSON:" +
                DateFormat.getDateTimeInstance().format(new Date()));

        inside_loc.put("type","Point");
        inside_loc.put("coordinates",coordinates);
        LocationUpdateServiceOnTheRun.put("loc",inside_loc);

        // This Thread is for the connection - we cannot conect is service without Thread
        Thread t = new Thread(new Runnable() {
            JSONObject obj = LocationUpdateServiceOnTheRun;

            public void run() {
                try {
                    postDataToServer(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    /**
     * Posting the data of the JSON to the server
     * @param obj - the JSON object
     * @throws IOException
     */
    void postDataToServer(JSONObject obj) throws IOException {

        //TODO: change to final url
        //TODO: note - change ip with ipcongig
        // String urlPath = "https://walkiii.herokuapp.com/api/locationStatus/" ;

        String urlPath = "http://192.168.56.1:8080/api/locationStatus/";

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringBuilder result = new StringBuilder();

        try {

            // Open connection to the server
            // Open url for reading
            URL url = new URL(urlPath);

            //TODO: at final app - chang HTTP or HTTPS !!!!!!!!!!!!!!!!!!!!!
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("LocationUpdateService" , String.valueOf(urlConnection));
            } catch ( IOException e){
                Log.d("LocationUpdateService" , String.valueOf(e));
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

            try {
                urlConnection.connect();
            }
            catch(Exception e) {
                Log.d("LocationUpdateService","Connect exception: " + e.toString());
            }
            Log.d("LocationUpdateService","Connecting");

            //Post data to server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter((new OutputStreamWriter(outputStream)));
            bufferedWriter.write(obj.toString());

            Log.d("LocationUpdateService","written to server");

            bufferedWriter.flush();

            //Read the response from the server
            //////////////    FOR DEBUG - CATCH ERROR WHEN CONNECTING     ///////////////////////////////////////
            InputStream inputStream = null;

            try{
                inputStream = urlConnection.getInputStream();
                Log.d("LocationUpdateService", " urlConnection.getInputStream() : " + String.valueOf(urlConnection.getInputStream()));
            } catch (IOException ioe){
                if ( urlConnection instanceof  HttpURLConnection){
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    int statusCode = httpURLConnection.getResponseCode();
                    Log.d("LocationUpdateService", " error code number: " + String.valueOf(statusCode));
                    if (statusCode != 200){
                        inputStream = httpURLConnection.getErrorStream();
                        Log.d("LocationUpdateService", String.valueOf(inputStream));
                    }
                }
            }
            //////////////////////////////////////////////////////////////////////////////
            Log.d("LocationUpdateService","connection OK");

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                result.append(line).append("\n" );

                Log.d("LocationUpdateService", String.valueOf(result));

            }
            // disconnect
            urlConnection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
        }

    }
}


