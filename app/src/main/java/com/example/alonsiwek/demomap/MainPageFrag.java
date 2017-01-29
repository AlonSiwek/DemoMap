package com.example.alonsiwek.demomap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the main screen
 */

public class MainPageFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_screen_frag, null);

        // get the widgets reference from Fragment XML layout
        Button btn_go = (Button) view.findViewById(R.id.go_walking_btn);


///////////////   part of toast //////////////////////////////

        // Set a click listener for Fragment button
        btn_go.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Create a Toast notification/message
                Toast toast = Toast.makeText(
                        getActivity().getApplicationContext(), "Notifications has been sent to your friends"
                                , Toast.LENGTH_LONG);
                // Set the Toast display position layout center
                toast.setGravity(Gravity.CENTER,0,0);
                // Finally, show the toast
                toast.show();
            }
        });
///////////////  END part of toast //////////////////////////////
        return view;
    }
}



