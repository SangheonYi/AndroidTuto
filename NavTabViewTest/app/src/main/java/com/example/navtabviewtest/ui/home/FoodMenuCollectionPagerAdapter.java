package com.example.navtabviewtest.ui.home;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FoodMenuCollectionPagerAdapter extends FragmentStatePagerAdapter {
    Bundle asapterSavedInstanceState;
    String RESTAURANT = "restaurant";
    ArrayList<String> pickedRestaurant;

    public FoodMenuCollectionPagerAdapter(FragmentManager fm, Bundle savedInstanceState) {
        super(fm);
        asapterSavedInstanceState = savedInstanceState;
        pickedRestaurant = asapterSavedInstanceState.getStringArrayList(RESTAURANT);
        Log.i("생주 FMCPA", "FoodMenuCollectionPagerAdapter");
    }

    @Override
    public int getCount() {
        Log.i("생주 FMCPA", "getCount");
        return pickedRestaurant.size();
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FoodMenuFragment();
        Bundle args = new Bundle();
        args.putInt(FoodMenuFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        Log.i("생주 FMCPA", "getItem");
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        Log.i("생주 FMCPA", "getPageTitle");
        return pickedRestaurant.get(position);
    }
}
