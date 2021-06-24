package com.example.startservicetuto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var start: Button? = null
    var stop: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start = findViewById(R.id.start)
        stop = findViewById(R.id.stop)
        start!!.setOnClickListener(this)
        stop!!.setOnClickListener(this)
        Log.i(TAG, "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onClick(src: View) {
        when (src.id) {
            R.id.start -> {
                //서비스 시작
                Log.i(TAG, "start()")
                startService(Intent(this, MyService::class.java))
            }
            R.id.stop -> {
                Log.i(TAG, "stop()")
                //서비스 정지
                stopService(Intent(this, MyService::class.java))
            }
        }
    }

    companion object {
        private const val TAG = "activity"
    }
}