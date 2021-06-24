package com.example.fragmentstatepagertuto;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
    static final int NUM_ITEMS = 10;

    MyAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("생주 액티", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Watch for button clicks.
        Button button = findViewById(R.id.goto_first);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = findViewById(R.id.goto_last);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_ITEMS-1);
            }
        });
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
            Log.i("생주 마어댑", "MyAdapter(FragmentManager fm)");
        }

        @Override
        public int getCount() {
            Log.i("생주 마어댑", "getCount");
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("생주 마어댑", "getItem");
            return ArrayListFragment.newInstance(position);
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            /*num은 fragment 생성 시 해당 fragment의 위치 즉, 몇 번째 fragment 인지 나타내는 정수를 받음*/
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            //ArrayListFragment 'f'의 argument는 budle형태로 key가 'num'인 정수를 value(fragment의 위치)로 set.
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i("생주 프래그", "onCreate");
            /*위의 newInstance()에서 set한 argument값을 가져온다.*/
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.i("생주 프래그", "onCreateView");
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);// fragment의 번호를 띄워줄 textview
            ((TextView)tv).setText("Fragment #" + mNum);
            return v;//fragment_pager_list 그린 뷰를 반환
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.i("생주 프래그", "onActivityCreated");
            String[] Cheeses = {"ch1", "ch2", "ch3"};//리스트의 아이템
            /* getActivity() : Return the FragmentActivity this fragment is currently associated with.
            May return null if the fragment is associated with a Context instead.*/
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, Cheeses));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            //listitem 클릭 시
            Log.i("FragmentList", "Item clicked: " + id);
        }

/*        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.i("생주 프래그", "onDestroyView");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i("생주 프래그", "onDestroy");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.i("생주 프래그", "onDetach");
        }*/
    }
}