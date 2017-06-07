//package com.example.alonsiwek.demomap;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.Marker;
//
///**
// * Created by alonsiwek on 31/05/2017.
// */
//
//public class CDrawHotspotsAsync extends AsyncTask<Void, Marker, Boolean>
//{
//    private final Context mCntxt;
//    private final GoogleMap mGMap;
//
//// ---------------------------------------------------------
//
//    public CDrawHotspotsAsync(Context cntxt, GoogleMap gmap)
//    {
//        this.mCntxt = cntxt;
//        this.mGMap = gmap;
//    }
//
//// ---------------------------------------------------------
//
//    private void drawMarker(GoogleMap gmap, Ma hotspot)
//    {
//        // Set marker options
//        MarkerOptions mo = new MarkerOptions();
//        mo.position(hotspot.getPosition());
//        mo.title(hotspot.getName());
//        String descr = hotspot.getAddress() + ", " + hotspot.getPostal() + ", " + hotspot.getCity();
//        mo.snippet(descr);
//        mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotspot_marker));
//
//        // Add marker to map
//        gmap.addMarker(mo);
//    }
//
//// ---------------------------------------------------------
//
//    @Override
//    protected Boolean doInBackground(Void... params)
//    {
//        // Get hotspots from database
//        CHotspotList hotspots = new CHotspotList(this.mCntxt);
//        ArrayList<CHotspot> arrHotspots = hotspots.getHotspotList();
//
//        for (CHotspot hotspot : arrHotspots)
//        {
//            // Publish progress
//            publishProgress(hotspot);
//        }
//
//        // Always return true
//        return true;
//    }
//
//// ---------------------------------------------------------
//
//    @Override
//    protected void onProgressUpdate(CHotspot... hotspot)
//    {
//        // Draw the marker
//        drawMarker(this.mGMap, hotspot[0]);
//    }
//}
