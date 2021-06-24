package com.example.tabpageradaptertuto;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.i("생주 DCPAdapter", "DemoCollectionPagerAdapter");
    }

    @Override
    public int getCount() {
        Log.i("생주 DCPAdapter", "getCount");
        return 4;
    }

    @Override
    public Fragment getItem(int i) {
        Log.i("생주 DCPAdapter", "getItem");
        Fragment fragment = new DemoObjectFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("생주 DCPAdapter", "getPageTitle");
        return "OBJECT " + (position + 1);
    }
}
