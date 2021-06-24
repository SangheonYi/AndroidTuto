package com.example.imagedownloadtuto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = findViewById(R.id.btn_download);
        editText = findViewById(R.id.et_url);
        imageView = findViewById(R.id.iv_image);

        btnDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new ImageDownloadTask().execute(0);//AsyncTask 실행
            }
        });
    }
    class ImageDownloadTask extends AsyncTask<Integer, Integer, Integer>{
        //AsyncTask 구현
        @Override
        protected Integer doInBackground(Integer... integers) {//background에서 실행될 메소드
            try {
                @SuppressLint("WrongThread") URL url = new URL(editText.getText().toString());//입력된 url값을 가져온다.
                Log.i("두 인 백", "47");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//입력된 url로 http 연결 생성
                Log.i("두 인 백", "49");
                InputStream iStream = urlConnection.getInputStream();//위에서 생성한 http연결로 입력 스트림 생성
                Log.i("두 인 백", "51");
                final Bitmap bitmap = BitmapFactory.decodeStream(iStream);//입력 스트림을 bitmap으로 decode
                imageView.post(new Runnable() {//직접 UI에 접근이 안되므로 view.post를 이용
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);//decoding된 bitmap으로 imageview를 set
                    }
                });
            } catch (Exception e) {//예외 발생시 토스트 메시지 출력
                Log.i("두 인 백", "예외");
                e.printStackTrace();
            }
            return null;
        }
    }
}
