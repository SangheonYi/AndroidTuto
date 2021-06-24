package com.example.activitytuto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    TextView text;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("LifeCycle", "onCreate() 호출");
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count++;
                text.setText("현재 개수=" + count);
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            count--;
            text.setText("현재 개수=" + count);
        }
        });

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count");
            text.setText("현재 개수=" + count);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("LifeCycle", "onStart() 호출");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("LifeCycle", "onResume() 호출");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("LifeCycle", "onPause() 호출");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i("LifeCycle", "onStop() 호출");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycle", "onDestroy() 호출");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
        Log.i("LifeCycle", "onSaveInstanceState() 호출");
    }
}
