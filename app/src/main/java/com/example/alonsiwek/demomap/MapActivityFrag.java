package com.example.alonsiwek.demomap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

import static android.R.attr.fragment;
import static com.example.alonsiwek.demomap.MainPageFrag.updateRunningState;
import static com.example.alonsiwek.demomap.R.id.login_btn;
import static com.example.alonsiwek.demomap.R.id.map;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the MapActivity.
 */

public class MapActivityFrag extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    Boolean mIsRunning_atMAF;
    List<UserData> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.activity_maps, null, false);
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(MapActivityFrag.class.toString(),"error to display map: " + e.toString());
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // for display my current location at the map (without marker)
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

                if (location != null) {
                    double myLat = location.getLatitude();
                    double myLong = location.getLongitude();

                    Log.d("MapActivityFrag","lat: " + myLat);
                    Log.d("MapActivityFrag","long: " + myLong);

                    // For zooming automatically to my location
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLong), 10);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        });

        //////////////////////added for display friends to add
//        mainPageFrag.mRecyvleView = (RecyclerView)rootView.findViewById(R.id.addFriends);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//        mainPageFrag.mRecyvleView.setLayoutManager(layoutManager);


//        mainPageFrag.mAdapter = new AdapterUsers(getActivity(),mainPageFrag.data);
//        mainPageFrag.mRecyvleView.setAdapter(mainPageFrag.mAdapter);
//        mainPageFrag.mRecyvleView.setLayoutManager(new LinearLayoutManager(getActivity()));
/////////////////////////////


        ImageButton finish = (ImageButton)rootView.findViewById(R.id.finish_btn);

        ////////////// Part of UPDATE DB ////////////////////////////////

        /* update DB only when mIsRunning_atMAF == false.
         * update DB only when mIsRunning == true  will be with btn_go button.
         */

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

//
//                if(adapterUsers.dataOfUsersList.get(0).user_name != null)
//                System.out.println("BLLLLAAAAAAAAAAAAAA:    " + adapterUsers.dataOfUsersList.get(0).user_name);

                mIsRunning_atMAF = false;

                Log.d("MapActivityFrag","mIsRunning_atMAF :" + mIsRunning_atMAF.toString());

                if (mIsRunning_atMAF == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MainPageFrag.updateRunningState(mIsRunning_atMAF);
                            } catch (IOException e) {
                                Log.e(MapActivityFrag.class.toString(), e.toString());
                                e.printStackTrace();
                                return;
                            }
                        }
                    }).start();
                }
            }
        });

<<<<<<< HEAD
        Bundle bundle = this.getArguments();
        if (bundle != null){
            list = bundle.getParcelable("list");
            Log.d("MapFrag","list:" + list.get(0).user_name);
        }

=======
        new DisplayRunnersOnMap(getActivity(),rootView,R.id.runners_list).execute();
>>>>>>> 7b034d8e536e9c98156aed51f9d390c108795988

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
