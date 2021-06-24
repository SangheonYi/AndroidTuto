package com.example.startservicetuto

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MyService : Service() {
    var player: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate()")
        player = MediaPlayer.create(this, R.raw.old_pop)
        //old_pop파일 재생할 미디어 플레이어 생성해서 할당
        player!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()

        Log.i(TAG, "onStartCommand() startId: $startId")
        Log.i(TAG, "onStartCommand() flags: $flags")
        Log.i(TAG, "START_STICKY: $START_STICKY START_NOT_STICKY: $START_NOT_STICKY START_FLAG_REDELIVERY: $START_REDELIVER_INTENT")
        player!!.start() //플레이어 정지
//        return flags
//        return START_STICKY
//        return START_NOT_STICKY
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "onDestroy()")
        player!!.stop() //플레이어 정지
    }

    companion object {
        private const val TAG = "service"
    }
}