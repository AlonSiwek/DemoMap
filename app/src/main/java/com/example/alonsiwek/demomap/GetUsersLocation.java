//package com.example.alonsiwek.demomap;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//
///**
// * Created by Dor on 28-Apr-17.
// */
//
//
//
//public class GetUsersLocation extends AsyncTask<String,Void, String> {
//
//    // A dialog showing a progress indicator and an optional text message or view.
//    // Only a text message or a view can be used at the same time.
//    ProgressDialog progressDialog;
//    ScrollView mResult;
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        //TODO: Handel progrees bar - to show in fragment ( MainPageFrag)
//
//        progressDialog =  new ProgressDialog();             // where to display the msg - have to be Context
//        progressDialog.setMessage("Looking for your friend...");
//        progressDialog.show();
//    }
//
//
//    @Override
//    protected String doInBackground(String... params) {
//        try {
//            return getData(params[0]);
//        } catch (IOException exeption) {
//            exeption.printStackTrace();
//            return "Network error!";
//        }
//
//    }
//
//    /**
//     * Set the data reseponse to TextView
//     * @param result
//     */
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//
//        //TODO: the result suoul be enter in the scroll view - only to the people that go invited
//        //mResult.setText(result);
//
//        // Cancel progress dialog
//        if (progressDialog != null){
//           progressDialog.dismiss();
//        }
//    }
//
//    /**
//     * Initialize and config request and conerct to server
//     * Read the data response
//     * @param urlPath
//     * @return string of the data
//     */
//    private String getData(String urlPath) throws  IOException{
//
//        StringBuilder resultData = new StringBuilder();
//        BufferedReader bufferedReader = null;
//
//        try {
//            // Open url for reading
//            URL url = new URL(urlPath);
//            HttpURLConnection  urlConnection = (HttpURLConnection) url.openConnection();
//            //set the time to read from url - in miliSec
//            urlConnection.setReadTimeout(10000);
//            //set time to be used when opening a communications link
//            // to the resource referenced by this URLConnection connect to url
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setRequestMethod("GET");
//            //set header
//            urlConnection.setRequestProperty("Content_Type","application/json");
//            urlConnection.connect();
//
//            // Read the data response
//            InputStream dateResponse = urlConnection.getInputStream();
//            bufferedReader = new BufferedReader(new InputStreamReader(dateResponse));
//
//            String line;
//            while ((line = bufferedReader.readLine()) != null){
//                resultData.append("\n");
//            }
//
//        } finally {
//            if (bufferedReader != null){
//                bufferedReader.close();
//            }
//        }
//
//        return resultData.toString();
//
//    }
//
//}
