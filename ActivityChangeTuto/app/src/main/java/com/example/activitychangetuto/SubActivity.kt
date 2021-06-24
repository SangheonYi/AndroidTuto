package com.example.activitychangetuto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout2)
        val b = findViewById<Button>(R.id.Button01)
        val recieved = intent
        var arr = recieved.getIntegerArrayListExtra("strArray")
        Log.i("sub activity", "  ${arr.size}")
        b.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("sub activity", "in subacti")
        if (resultCode == RESULT_OK) {
        }
    }
}