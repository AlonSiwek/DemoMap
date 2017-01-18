//package com.example.alonsiwek.demomap;
//
//import android.app.ActionBar;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
///**
// * Created by dor on 1/16/2017.
// *  took from google:
// *  https://developer.android.com/training/implementing-navigation/lateral.html#horizontal-paging
// *  notice errores (public / static , etc'   (16/1/17)
//*/
//
//public class CollectionActivity extends FragmentActivity {
//
//    // When requested, this adapter returns a ObjectFragment,
//    // representing an object in the collection.
//    CollectionPagerAdapter mCollectionPagerAdapter;
//    ViewPager mViewPager;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_screen);
//
//
//        // ViewPager and its adapters use support library
//        // fragments, so use getSupportFragmentManager.
//        mCollectionPagerAdapter =
//                new CollectionPagerAdapter(
//                        getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.page1_main);
//        mViewPager.setAdapter(mCollectionPagerAdapter);
//
//    }
//}
//
//// Since this is an object collection, use a FragmentStatePagerAdapter,
//// and NOT a FragmentPagerAdapter.
//public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
//
//    public CollectionPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = new ObjectFragment();
//        Bundle args = new Bundle();
//
//        // Our object is just an integer :-P
//        args.putInt(ObjectFragment.ARG_OBJECT, position + 1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Override
//    public int getCount() {
//        return 4;
//    }
//
//    public CharSequence getgetPageTitle(int position) {
//        return "OBJECT " + (position + 1);
//    }
//}
//
/////////////////////////////////////////////////////////////
///*              chack what this class does               */
////////////////////////////////////////////////////////////
//
//
//// Instances of this class are fragments representing a single
//// object in our collection.
//public static class ObjectFragment extends Fragment {
//    public static final String ARG_OBJECT = "object";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        // The last two arguments ensure LayoutParams are inflated
//        // properly.
//        View rootView = inflater.inflate(
//                R.layout.main_screen, container, false);
//        Bundle args = getArguments();
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
//    }
//}
//
//
//
//
