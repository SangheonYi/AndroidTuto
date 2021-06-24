package com.example.activitychangetuto

import android.content.ClipData

import android.content.ClipData.Item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activitychangetuto.R
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.activitychangetuto.SubActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout1)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun onClick(v: View?) {
        var intent: Intent? = Intent(this, SubActivity::class.java)
        /*switch (v.getId()){
            case R.id.web://웹페이지 호출
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;

            case R.id.call://전화 다이얼 호출
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)123456789"));
                break;

            case R.id.map://지도 호출
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.30,127.2?z=10"));
                break;

            case R.id.contact://연락처 호출
                intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                break;

            case R.id.subActivity://연락처 호출
                intent  = new Intent(MainActivity.this, SubActivity.class);
                break;
        }*/
        val strArray = arrayListOf<String>()
        strArray.clear()
        for (i in 1..3350)//3350/3400
            strArray.add("content://com.android.providers.media.documents/document/image%3A208759\n")
        Log.i("print", "${strArray.size}")
        intent!!.putExtra("strArray", strArray)
        startActivity(intent)
        }
    }
