package com.example.testthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    int mainValue = 0;
    int backValue = 0;
    TextView mainText;
    TextView backText;
    EditText editTest;
    private TextToSpeech mTts;
    private Button mOnButton;
    BackThread thread = new BackThread();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView) findViewById(R.id.mainvalue);
        backText = (TextView) findViewById(R.id.backvalue);
        editTest = (EditText) findViewById(R.id.edit_test);
        editTest.setSelection(0,9);

        mTts = new TextToSpeech(this, this);
        mOnButton = (Button) findViewById(R.id.increase);
        // 스레즈 생성하고 시작

        thread.setDaemon(true);
        thread.start();

            Log.println(5,"스레드","소리질러~");


    } //end onCreate()

    // 버튼을 누르면 mainValue 증가
    public void mOnClick(View v) {

        editTest.setSelection(4,7);
            mainValue++;
            mainText.setText("MainValue:" + mainValue);
//            notify();

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.KOREAN);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {
                mOnButton.setEnabled(true);
            }
        } else {
            Log.e("띠띠에스", "TextToSpeech 초기화 에러!");
        }
    }

    class BackThread extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    backValue++;
                    //                메인에서 생성된 Handler 객체의 sendEmpryMessage 를 통해 Message 전달
                    handler.sendEmptyMessage(0);

                    try {
                        Thread.sleep(1000);
//                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } // end while
        } // end run()
    } // end class BackThread


    // '메인스레드' 에서 Handler 객체를 생성한다.
    // Handler 객체를 생성한 스레드 만이 다른 스레드가 전송하는 Message나 Runnable 객체를
    // 수신할수 있다.
    // 아래 생성된 Handler 객체는 handlerMessage() 를 오버라이딩 하여
    // Message 를 수진합니다.
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {   // Message id 가 0 이면
                backText.setText("BackValue:" + backValue); // 메인스레드의 UI 내용 변경
                editTest.setSelection(2,8);
            }
        }
    };

} // end class
