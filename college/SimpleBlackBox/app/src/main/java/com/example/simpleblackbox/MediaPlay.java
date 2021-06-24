package com.example.simpleblackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import javax.crypto.EncryptedPrivateKeyInfo;

public class MediaPlay extends AppCompatActivity {

    DBHelper helper;
    Bundle extras;
    TextView view_info;
    int idValue;
    String path;
    VideoView videoView;
    Cursor videoCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);

        view_info = findViewById(R.id.view_media_info);
        helper = new DBHelper(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            idValue = extras.getInt("id");
        }
        if (idValue>0) {
            videoCursor = helper.getData(idValue);
            videoCursor.moveToFirst();
            view_info.setText(videoCursor.getString(2) + ", " + videoCursor.getString(4));
            path = videoCursor.getString(1);
        }
        videoView = this.findViewById(R.id.view_video);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoPath(path);
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
