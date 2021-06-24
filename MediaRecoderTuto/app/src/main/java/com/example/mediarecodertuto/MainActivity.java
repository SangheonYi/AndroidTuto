package com.example.mediarecodertuto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Button mybutton;
    MediaRecorder myrecorder;
    SurfaceHolder myholder;
    boolean is_recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("onCreate()", "checkSelfPermission : false, requestPermissions");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        } else {
            Log.i("onCreate()", "checkSelfPermission : true init");
            bindRecorderWithView();
        }
        Log.i("onCreate()", " : onCreate end");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("RequePermitResult()", "onRequestPermissionsResult");
        bindRecorderWithView();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("surfaceCreated()", "surface created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i("surfaceChanged()", "surface changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i("surfaceDestroyed()", "surface destroyed");
    }

    private void initMediaRecorder() {
        Log.i("initMediaRecorder()", "recorder init");
        myrecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        myrecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
        myrecorder.setProfile(camcorderProfile_HQ);
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath();
        String videoFile = folder + "/video.mp4";
        Log.i("initMediaRecorder()", "videoFile : " + videoFile);
        myrecorder.setOutputFile(videoFile);
        myrecorder.setMaxDuration(60000);
        myrecorder.setMaxFileSize(5000000);
        myrecorder.setOrientationHint(90);
    }

    private void prepareMediaRecorder() {
        Log.i("prepareMediaRecorder()", "recorder preparing");
        myrecorder.setPreviewDisplay(myholder.getSurface());
        try {
            Log.i("prepareMediaRecorder()", "prepare try");
            myrecorder.prepare();
            Log.i("prepareMediaRecorder()", "prepare done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void bindRecorderWithView(){
        is_recording = false;
        myrecorder = new MediaRecorder();
        SurfaceView mysurfaceview = findViewById(R.id.videoview);
        mysurfaceview.setRotation(90);
        myholder = mysurfaceview.getHolder();
        myholder.addCallback(this);

        mybutton = findViewById(R.id.mybutton);
        mybutton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (is_recording) {
                    Log.i("myButtonClick", "is_recording : " + is_recording);
                    myrecorder.stop();
                    is_recording = false;
                    mybutton.setText("녹화시작");
                } else {
                    initMediaRecorder();
                    prepareMediaRecorder();
                    Log.i("myButtonClick", "is_recording : " + is_recording);
                    myrecorder.start();
                    is_recording = true;
                    mybutton.setText("녹화중지");
                }
            }
        });
    }
}
