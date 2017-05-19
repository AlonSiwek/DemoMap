package com.example.alonsiwek.demomap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

/**
 * Created by alonsiwek on 11/12/2016.
 */

public class Splash extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                Constants.loadSharedPrefs(settings);
                if(settings.contains("PhoneNumber"))
                {

                    //TODO: change the time of the service
                    // call to service
                    Intent intent = new Intent(Splash.this, LocationUpdateService.class);
                    PendingIntent pintent = PendingIntent.getService(Splash.this, 0, intent, 0);
                    AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5*1000, pintent);

                    //sent intent to main screen. use by:  new Intent(Splash.this,MapsActivity.class);
                    Intent mainIntent = new Intent(Splash.this,MainScreen.PageAdapter.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
               else {
                    //the user DID NOT LOGIN
                    // then we will run the service after the first login
                    Intent mainIntent = new Intent(Splash.this,Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}