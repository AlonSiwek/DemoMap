package com.example.alonsiwek.demomap;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by alonsiwek on 11/12/2016.
 *
 * display App splash screen
 * display App permission
 * handle next activity / fragemnt - depands if user sigh in at the past
 * activate LocationUpdateService
 *
 */

//TODO: decide if delete CONTACTS in the manifest

public class Splash extends AppCompatActivity{ //extends AbstractRuntimePermissions {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1300;

    /** The code used when requesting permissions */
    private static final int PERMISSIONS_REQUEST = 10;

    /**
     * The TextView which is used to inform the user whether the permissions are
     * granted.
     */
    private TextView textView = null;
    private static final int textViewID = View.generateViewId();

    /** The time when this Activity was created. */
    private long startTimeMillis = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         * On a post-Android 6.0 devices, check if the required permissions have
         * been granted.
         */
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            startNextActivity();
        }
    }

    /**
     * start the next activity - depend on checkPermissions method
     * handle inside if the user has sign in to the app
     */
    private void startNextActivity(){
     /* New Handler to start the Menu-Activity
     * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            /* Create an Intent that will start the Menu-Activity. */

                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                Constants.loadSharedPrefs(settings);
                if (settings.contains("PhoneNumber")) {

                    // call to service
                    Intent intent = new Intent(Splash.this, LocationUpdateService.class);
                    PendingIntent pintent = PendingIntent.getService(Splash.this, 0, intent, 0);
                    AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, pintent);


                    //sent intent to main screen. use by:  new Intent(Splash.this,MapsActivity.class);
                    Intent mainIntent = new Intent(Splash.this, MainScreen.PageAdapter.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                } else {
                    //the user DID NOT LOGIN
                    // then we will run the service after the first login
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


    /**
     * Get the list of required permissions by searching the manifest.
     * I
     * @return new String[]{Manifest.permission. XXXX }
     */
    public String[] getRequiredPermissions() {
        String[] permissions = null;
        try {
            permissions = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e("Splash", e.toString());
            e.printStackTrace();
        }
        catch (Exception e){
            Log.e("Splash" , e.toString());
            e.printStackTrace();
        }
        if (permissions == null) {
            return new String[0];
        } else {
            return permissions.clone();
        }
    }

    /**
     * Get the time (in milliseconds) that the splash screen will be on the
     * screen before
     * @return SPLASH_DISPLAY_LENGTH
     */
    public int getTimeoutMillis() {
        return SPLASH_DISPLAY_LENGTH;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            checkPermissions();
        }
    }

    /**
     * Check if the required permissions have been granted:
     * {@link #startNextActivity()} if they have.
     *
     * Otherwise:
     * {@link #requestPermissions(String[], int)}.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        String[] ungrantedPermissions = requiredPermissionsStillNeeded();
        if (ungrantedPermissions.length == 0) {
            startNextActivity();
        } else {
            requestPermissions(ungrantedPermissions, PERMISSIONS_REQUEST);
        }
    }

    /**
     * Convert the array of required permissions to a {@link Set} to remove
     * redundant elements.
     * Then remove already granted permissions.
     * @return an array of ungranted permissions.
     */
    @TargetApi(23)
    private String[] requiredPermissionsStillNeeded() {

        Set<String> permissions = new HashSet<String>();
        for (String permission : getRequiredPermissions()) {
            permissions.add(permission);
        }
        for (Iterator<String> i = permissions.iterator(); i.hasNext();) {
            String permission = i.next();
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d(Splash.class.getSimpleName(),
                        "Permission: " + permission + " already granted.");
                i.remove();
            } else {
                Log.d(Splash.class.getSimpleName(),
                        "Permission: " + permission + " not yet granted.");
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }
}