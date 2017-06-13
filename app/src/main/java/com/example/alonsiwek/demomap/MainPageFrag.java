package com.example.alonsiwek.demomap;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the main screen
 */

public class MainPageFrag extends Fragment {

    Boolean mIsRunning = false;

    private static final int UPDATE_RECYCLE_VIEW_DURATION = 5000;
    int isVisible = 0;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.main_screen_frag, null);

        TimerTask task = new UserAtAppTimer(getActivity(), view, R.id.users_list);
        new Timer().scheduleAtFixedRate(task,0,UPDATE_RECYCLE_VIEW_DURATION);

        ImageButton btn_go = (ImageButton) view.findViewById(R.id.go_walking_btn);
        final RecyclerView mRecyleView = (RecyclerView) view.findViewById(R.id.users_list);
        final TextView tv = (TextView) view.findViewById(R.id.your_friends_tv);
        // get the widgets reference from Fragment XML layout

        final ImageButton btn_bell = (ImageButton) view.findViewById(R.id.bell);



        final TextView numOfNotification = (TextView) view.findViewById(R.id.red_cycle);
        numOfNotification.setText(String.valueOf("  5"));
        /*
        TimerTask displaySize = new TimerTask() {
            @Override
            public void run() {
                int size;
                if(null != mRecyleView.getAdapter()){
                    size = mRecyleView.getAdapter().getItemCount();
                    numOfNotification.setText(String.valueOf(size));
                }
            }
        };
        new Timer().scheduleAtFixedRate(displaySize,1,2000);

        */
//        TimerTask task2 = new UserAtAppTimer(getActivity(), view, R.id.red_cycle);
//        new Timer().scheduleAtFixedRate(task2,0,100);

        btn_bell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                btn_bell.setVisibility(View.GONE);
                numOfNotification.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                mRecyleView.setVisibility(View.VISIBLE);



            }
        });

        // Toast of the Main button
        // Set a click listener for Fragment button
        btn_go.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                // Get the application context
                Toast toast = new Toast(getContext());
                // Set the Toast display position layout center
                toast.setGravity(Gravity.CENTER, 0, 0);

                LayoutInflater inflater = getLayoutInflater(savedInstanceState);
                View layout = inflater.inflate(R.layout.go_massage_toast, null);

                // Set the Toast duration
                toast.setDuration(Toast.LENGTH_LONG);

                // Set the Toast custom layout
                toast.setView(layout);
                toast.show();


                //  UPDATE DB
                mIsRunning = true;

                /* update DB only when mIsRunning == true.
                 * update DB only when mIsRunning == false will be with FINISH button.
                 */
                Log.d(MainPageFrag.class.toString(),"mIsRunning:" +  String.valueOf(mIsRunning));
                if (mIsRunning) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                updateRunningState(mIsRunning);
                            } catch (IOException e) {
                                Log.e(MainPageFrag.class.toString(), e.toString());
                                e.printStackTrace();
                                return;
                            }
                        }
                    }).start();
                }
            }
        });

        return view;
    }

    /**
     * Update the DB with the boolean state field of "is_running"
     * @param state - true or flase
     * @throws IOException
     */
    static void updateRunningState(Boolean state) throws IOException {

        JSONObject json = new JSONObject();
        URL url;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            json.put("is_running", state);
        } catch (JSONException e) {
            Log.e(MainPageFrag.class.toString(), "json error: " + e.toString());
            e.printStackTrace();
            return;
        }

        try {
            url = new URL(Constants.SERVER_URL + Constants.LOC_STATUS_PATH + Constants.user_id);
        } catch (MalformedURLException e) {
            Log.e(MainPageFrag.class.toString(), "error at url: "  + e.toString());
            e.printStackTrace();
            return;
        }

        HttpURLConnection urlConnection = null;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        //set the time to read from url - in miliSec
        urlConnection.setReadTimeout(10000);
        //set time to be used when opening a communications link
        // to the resource referenced by this URLConnection connect to url
        urlConnection.setConnectTimeout(10000);
        urlConnection.setRequestMethod("PUT");

        // enable output
        urlConnection.setDoOutput(true);
        //set header
        urlConnection.setRequestProperty("Content-Type","application/json");

        urlConnection.connect();

        Log.d(MainPageFrag.class.toString(),"Connecting");

        //Post data to server
        OutputStream outputStream = null;

        outputStream = urlConnection.getOutputStream();
        bufferedWriter = new BufferedWriter((new OutputStreamWriter(outputStream)));
        bufferedWriter.write(json.toString());

        Log.d(MainPageFrag.class.toString(),"written to server");
        bufferedWriter.flush();

        if ( urlConnection.getResponseCode() != 200) {
            Log.e(MainPageFrag.class.toString()," response code error:" + urlConnection.getResponseCode());
            return;
        }

        // disconnect
        urlConnection.disconnect();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}