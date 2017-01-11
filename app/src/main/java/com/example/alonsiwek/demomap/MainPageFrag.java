package com.example.alonsiwek.demomap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the main screen
 */

public class MainPageFrag extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.main_screen, container ,false);
        )

        return rootView;

    }
}
