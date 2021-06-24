package com.example.simpleblackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, SensorEventListener {

    //Recorder
    Button mybutton;
    MediaRecorder myrecorder;
    SurfaceHolder myholder;
    boolean is_recording;

    //Compass
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;
    CompassView compass;

    float[] mGravity;
    float[] mGeomagnetic;

    //Location Status
    TextView status;
    double[] statusValue = new double[3];

    //DB, List
    Button listbutton;
    DBHelper mydb;
    String[] videoTupleCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("onCreate()", "checkSelfPermission : false, requestPermissions");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 10);
        } else {
            Log.i("onCreate()", "checkSelfPermission : true init");
            bindComponWithView();
        }
        //Compass
        compass = new CompassView(this);
        compass = findViewById(R.id.view_compass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //DB, List
        mydb = new DBHelper(this);
        Log.i("onCreate()", " : onCreate end");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("RequePermitResult()", "onRequestPermissionsResult");
        bindComponWithView();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    //Recorder
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

    private String initMediaRecorder(String date) {
        Log.i("initMediaRecorder()", "recorder init");
        myrecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        myrecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
        myrecorder.setProfile(camcorderProfile_HQ);
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath();
        String videoFile = folder + "/video_" + date + ".mp4";
        Log.i("initMediaRecorder()", "videoFile : " + videoFile);
        myrecorder.setOutputFile(videoFile);
        myrecorder.setMaxDuration(60000);
        myrecorder.setMaxFileSize(5000000);
        myrecorder.setOrientationHint(90);
        return videoFile;
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

    @SuppressLint("MissingPermission")
    private void bindComponWithView(){
        //Recorder
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
                    Cursor tempCursor = mydb.getLastData();
                    if (mydb.updateVideo(tempCursor.getInt(0), tempCursor.getString(1), tempCursor.getString(2),
                            tempCursor.getString(3), " end point: " + statusValue[0] + " " + statusValue[1]))
                        Log.i("db", "update success");
                    else
                        Log.i("db", "update failed");

                } else {
                    videoTupleCache = new String[3];
                    videoTupleCache[2] = getDate();//date
                    videoTupleCache[0] = initMediaRecorder(videoTupleCache[2]);//file name(path) & init the media recorder
                    videoTupleCache[1] = " start point: " +statusValue[0] + " " + statusValue[1];//위도 경도
                    prepareMediaRecorder();
                    Log.i("myButtonClick", "is_recording : " + is_recording);
                    myrecorder.start();
                    is_recording = true;
                    mybutton.setText("녹화중지");
                    if (mydb.insertVideo(videoTupleCache[0], videoTupleCache[1], videoTupleCache[2], "not ended"))
                        Log.i("db", "insert success");
                    else
                        Log.i("db", "insert failed");
                }
            }
        });
        listbutton = findViewById(R.id.button_list);
        listbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        //Location Status
        status = findViewById(R.id.view_status);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                statusValue[0] = location.getLatitude();
                statusValue[1] = location.getLongitude();
                statusValue[2] = location.getSpeed()/1000;

                status.setText("위도: " + statusValue[0] + "\n경도: " + statusValue[1]
                        + "\n속도: " + statusValue[2]+" km/h");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        Log.i("getDate()", "now: " + date);
        return date;
    }
    //Compass
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        Log.i("멘 액", "onSensorChanged : ");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                compass.setAzimuth((float) Math.toDegrees(orientation[0]));
                compass.invalidate();
            }
        }
    }
}
