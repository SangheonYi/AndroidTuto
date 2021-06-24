package com.example.progressbarthreadtuto;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PROGRESS=0x1;//뭔지 모르겠다.
    private ProgressBar mProgress;//Progress Bar
    private int mProgressStatus = 0;//Progress Bar 진행도
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = findViewById(R.id.progressBar);
        final Thread t = new Thread(new Runnable() {//스레드에 Runable 구현
                    @Override
                    public void run() {//run 메소드 오버라이딩
                        while (mProgressStatus<100){//진행도 100될 때 까지 반복
                            try {
                                Thread.sleep(1000);//1초 동안 스레드 슬립
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mProgressStatus = i++;//깨면 진행도 1 상승
                            mProgress.post(new Runnable() {//UI 스레드에 Post할 Runnable 구현
                                @Override
                                public void run() {
                                    mProgress.setProgress(mProgressStatus);//진행도 set
                                }
                            });
                        }
                    }
                });
                t.start();//thread실행
    }
}
