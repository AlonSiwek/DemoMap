package com.example.alonsiwek.demomap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dor on 1/11/2017.
 * his class is the Fragment calls of the MapActivity.
 */

public class MapActivityFrag extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_maps, null);

        return view;
    }
}
