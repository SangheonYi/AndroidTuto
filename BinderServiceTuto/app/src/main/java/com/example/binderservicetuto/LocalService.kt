package com.example.binderservicetuto

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.binderservicetuto.LocalService
import java.util.*

class LocalService : Service() {
    //클라이언트에게 반환되는 바인더
    private val mBinder: IBinder = LocalBinder()

    //난수 발생기
    private val mGenerator = Random()
    val TAG = "service"

    //클라이언트 바인더를 위한 클래스
    inner class LocalBinder : Binder() {
        val service: LocalService
            get() = this@LocalService
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind")
        return mBinder
    }//난수 생성해서 반환
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }//난수 생성해서 반환
    //클라이언트를 위한 메소드
    val randomNumber: Int
        get() = mGenerator.nextInt(100) //난수 생성해서 반환
}