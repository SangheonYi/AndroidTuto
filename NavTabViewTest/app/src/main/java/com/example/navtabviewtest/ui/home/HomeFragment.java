package com.example.navtabviewtest.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.navtabviewtest.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FoodMenuCollectionPagerAdapter foodMenuCollectionPagerAdapter;
    ViewPager viewPager;
    String RESTAURANT = "restaurant";
    Bundle adapterBundle;
    ImageButton kakaoBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("생주 HF", "onCreateView");
        ArrayList<String> restaurant = new ArrayList<String>();
        adapterBundle = new Bundle();
        restaurant.add("학식");
        restaurant.add("어문관");
        restaurant.add("직원");
        restaurant.add("긱식");
        adapterBundle.putStringArrayList(RESTAURANT,restaurant);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        foodMenuCollectionPagerAdapter = new FoodMenuCollectionPagerAdapter(getChildFragmentManager(), adapterBundle);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(foodMenuCollectionPagerAdapter);

        Log.i("생주 HF", "onViewCreated");
    }
}