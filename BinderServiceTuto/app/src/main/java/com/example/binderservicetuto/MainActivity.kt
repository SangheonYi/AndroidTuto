package com.example.binderservicetuto

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.binderservicetuto.LocalService.LocalBinder

class MainActivity : AppCompatActivity() {
    var mService: LocalService? = null
    var mBound = false
    val TAG = "activity"
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as LocalBinder
            mService = binder.service
            mBound = true
            Log.i(TAG, "service connect")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBound = false
            Log.i(TAG, "service disConnect")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
//        val intent = Intent(this, LocalService::class.java)
//        bindService(intent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
        Intent(this, LocalService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }

        Log.i(TAG, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
        Log.i(TAG, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()

        Log.i(TAG, "onDestroy")
    }
    //버튼 클릭 이벤트
    fun onButtonClick(v: View?) {
        if (mBound) {
            val num = mService!!.randomNumber
            Toast.makeText(this, "number: $num", Toast.LENGTH_LONG).show()
        }
    }
}