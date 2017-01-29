package com.example.alonsiwek.demomap;

/**
 * Created by dor on 1/10/2017.
 * This class is for the main screen:
 * 1) control the fragment :
 *      All we do is define each screen as a Fragment.
 *      Rather than creating one activity per screen and defining the transition
 *      animations, the FragmentActivity class will handle all of the work for us.
 * 2) swipes
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends AppCompatActivity {

//    Button btn_go;
//    View view;
//    TextView msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);

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

    // Adapter for the view pager
    public static class PageAdapter extends FragmentActivity {

        /*
        * Identifier for the first fragment
        * Main screen
        */
        public static final int FRAGMENT_ONE_MAINSCREEN = 0;

        /*
        * Identifier for the second fragment.
        * Map
        */
        public static final int FRAGMENT_TWO_MAP = 1;

        /*
        * Identifier for the third fragment.
        * contact
        */
        public static final int FRAGMENT_THREE_CONTACATS = 2;

        /*
        * Identifier for the second fragment.
        * SPAIR
        */
        public static final int FRAGMENT_FOUR = 3;

        /******************************************************
         * add fragment to total for each screen that created *
        ******************************************************/

        // The number of Total Pages in the app
        private static int NUM_PAGES_FRAGMENTS = 2;

        /*
         * The pager view (widget) - which handles animation and allows swiping horizontally
         * to access to access previous and next steps.
         *
         * The ViewPager that hosts the section contents
          */
        private ViewPager viewPager;

        /**
         * The pager adapter, which provides the pages to the view pager view (widget).
         */
        private PageAdapter mPagerAdapter;

        /**
         * The adapter definition of the fragments.
         */
        private FragmentPagerAdapter _fragmentPagerAdapter;

        /**
         * List of fragments.
         */
        private List<Fragment> listFragments = new ArrayList<Fragment>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.main_screen);

            // Create fragments.

            listFragments.add(FRAGMENT_ONE_MAINSCREEN, new MainPageFrag());
            listFragments.add(FRAGMENT_TWO_MAP, new MapActivityFrag());



            /*
             * Setup the fragments, defining the number of fragments, the screens and title
             */
            _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                @Override
                public Fragment getItem(final int position) {

                    return listFragments.get(position);
                }

                @Override
                public int getCount() {
                    return NUM_PAGES_FRAGMENTS;
                }

                @Override
                public CharSequence getPageTitle(final int position){
                    switch (position) {
                        case FRAGMENT_ONE_MAINSCREEN:
                            return "Title One - impliment pic";
                        case FRAGMENT_TWO_MAP:
                            return "Title Two - impiimnet pic";
                        default:
                            return null;
                    }
                }
            };
            viewPager = (ViewPager) findViewById(R.id.page1_main);
            viewPager.setAdapter(_fragmentPagerAdapter);
        }
    }
}



