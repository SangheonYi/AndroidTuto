package com.example.asynccountetuto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgress;//Progress Bar
    private int mProgressStatus = 0;//Progress Bar 진행도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = findViewById(R.id.progressBar);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CounterTask().execute(0);
            }
        });
    }

    class CounterTask extends AsyncTask<Integer, Integer, Integer> {
        //AsyncTask 구현
        @Override
        protected Integer doInBackground(Integer... integers) {//background에서 실행될 메소드
            while (mProgressStatus<100){//진행도 100될 때 까지 반복
                try {
                    Thread.sleep(1000);//1초 동안 스레드 슬립
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mProgressStatus++;//깨면 진행도 1 상승
                publishProgress(mProgressStatus);//ProgressBar 상태를 update함 onProgressUpdate 호출
            }
            return mProgressStatus;
        }

        @Override
        protected  void onProgressUpdate(Integer... integers){//publishProgress로 call back 되는 method
            mProgress.setProgress(mProgressStatus);
        }

        @Override
        protected  void onPostExecute(Integer result){//doInBackground 반환 값 받아서 call back
            mProgress.setProgress(mProgressStatus);
        }
    }
}
