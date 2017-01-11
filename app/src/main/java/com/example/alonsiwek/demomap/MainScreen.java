package com.example.alonsiwek.demomap;

/**
 * Created by dor on 1/10/2017.
 * This class is for the main screen:
 * 1) control the fregment
 * 2) swipes
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;


public class MainScreen extends AppCompatActivity {

    // Adapter for the view pager
    public static class PageAdapter extends FragmentPagerAdapter {
        private static int NUM_PAGES = 4;

        public PageAdapter(FragmentManager fragmentManager){
            super(fragmentManager);

            frag1 = new Fragment().

        }


        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return
            }
            return null;
        }

        // Return the total amount of pages
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }




    /*
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setContentView(R.layout.main_screen);

    }
    */
}
