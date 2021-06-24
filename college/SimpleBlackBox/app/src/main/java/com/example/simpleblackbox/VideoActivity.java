package com.example.simpleblackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    DBHelper mydb;
    private ListView myListView;
    ArrayAdapter mAdapter;
    Button recorder_back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //DB, adapter 생성
        mydb = new DBHelper(this);
        ArrayList arrayList = mydb.getAllVideos();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        //List 생성 및 리스너 등록
        myListView = findViewById(R.id.listview_video);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getApplicationContext(), MediaPlay.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                Log.i("List item", "onItemClicked");
            }
        });

        //녹화 화면으로 돌아갈 버튼 등록
        recorder_back_button = findViewById(R.id.button_recorder);
        recorder_back_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.i("onCreate()", " : onCreate end");
    }
    protected void onResume() {
        super.onResume();
        //새로 녹화 시 리스트 갱신
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllVideos());
        mAdapter.notifyDataSetChanged();
    }
}
