package com.example.servicetest

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import java.util.*

class MyCounterService : Service() {
    private var isStop = false
    private var count = 0
    private var tts: TextToSpeech? = null

    /**
     * Service, Activity 통신을 위한 Binder 객체
     * Activity에게 getCount() 메소드를 제공해 Service의 count 값을 전달한다.
     */
    var binder: IMyCounterService.Stub = object : IMyCounterService.Stub() {
        var i = 0
        @Throws(RemoteException::class)
        override fun getCount(): Int = i++
    }

    override fun onCreate() {
        super.onCreate()
        tts = TextToSpeech(this) { tts!!.language = Locale.KOREAN }

        // Thread를 이용해 Counter 실행시키기
        val counter: Thread = Thread(Counter())
        counter.start()
    }

    /**
     * StopService가 실행될 때 호출된다.
     */
    override fun onDestroy() {
        super.onDestroy()
        isStop = true
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        isStop = true
        return super.onUnbind(intent)
    }

    private inner class Counter : Runnable {
        private val handler = Handler()
        override fun run() {
            /**
             * 10초로 설정
             */
            count = 0
            while (count < 10) {


                // STOP 버튼을 눌렀다면 종료한다.
                if (isStop) {
                    break
                }
                /**
                 * Thread 안에서는 UI와 관련된 Toast를 쓸 수 없습니다.
                 * 따라서, Handler를 통해 이용할 수 있도록 만들어줍니다.
                 */
                handler.post { // Toast로 Count 띄우기
                    Toast.makeText(
                        applicationContext, count.toString() + "", Toast.LENGTH_SHORT
                    ).show()
                    // Log로 Count 찍어보기
                    Log.d("COUNT", count.toString() + "")
                }
                // Sleep을 통해 1초씩 쉬도록 한다.
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                count++
            }
            handler.post {
                Toast.makeText(applicationContext, "서비스가 종료되었습니다.", Toast.LENGTH_SHORT).show()
                tts!!.speak("라면이 다 익었습니다.", TextToSpeech.QUEUE_ADD, null)
            }
        }
    }
}