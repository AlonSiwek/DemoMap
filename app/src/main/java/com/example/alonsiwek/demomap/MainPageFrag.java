package com.example.alonsiwek.demomap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the main screen
 */

public class MainPageFrag extends Fragment{
    
    Button btn_go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_screen_frag,null);

        // Create UI components here.

        return view;
    }
////////// part of toast ////////////////////////////////
//        view = getLayoutInflater().inflate(R.layout.go_massage_toast,
//                (ViewGroup) findViewById(R.id.go_massage_toast_container));
//        msg = (TextView)view.findViewById(R.id.go_massage_toast_TextView);
}
//////////////////////////// part of toast ////////////////////////
//
//    public void go_toast(View v){
//
//        msg.setText("Notifications has been sent to your friends, Lets go Walkii!");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.BOTTOM,0,100);
//        toast.setDuration(Toast.LENGTH_LONG); // 3.5 sec
//        toast.setView(view);
//        toast.show();
//
//    }
}
