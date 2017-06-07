package com.example.alonsiwek.demomap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Dor on 28-May-17.
 *
 * Activate location if closed
 * Taken from: " http://java-blog.kbsbng.com/2012/08/displaying-dialog-in-android-to-prompt.html "
 * signiture change from: final Activity to final Service
 */

public class Utils {

    public static boolean displayPromptForEnablingGPS(final Activity activity) {

        AlertDialog.Builder builder = null;
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Please enable either GPS location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";
        try {
            builder = new AlertDialog.Builder(activity);
            Log.d("Utils", "builder:" + builder.toString());
        }
        catch (Exception e){
            Log.e("Utils","AlertDialog.Builder: " + e.toString() );
        }
        try {
            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    activity.startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        } catch (Exception e){
            Log.e("Utils","builder.setMessage(message): " + e.toString() );
        }

        return true;
    }
}
