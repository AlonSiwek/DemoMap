package com.example.alonsiwek.demomap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 *splash screen activity
 * create by : http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
 *
 */

public class splashScreen extends Activity {

    // Duration of wait for the screen
    private final int  SPLASH_SCREEN_DISPALAY_DURATION =2500;

    // Called when the activity is first created.
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Create an Intent that will start the Menu-Activity (?: Menu or Maps)
                Intent mainIntent = new Intent(splashScreen.this,MapsActivity.class);
                splashScreen.this.startActivity(mainIntent);
                splashScreen.this.finish();
            }
        }, SPLASH_SCREEN_DISPALAY_DURATION);
    }
}
