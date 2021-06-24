package com.example.servicetest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var tvCounter: TextView? = null
    private var btnStart: Button? = null
    private var btnStop: Button? = null
    private var binder: IMyCounterService? = null

    /**
     * Activity가 Service를 호출합니다.
     * 이 때, 꼭 binder가 필요합니다.
     */
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            /**
             * Service가 가지고있는 binder를 전달받는다.
             * 즉, Service에서 구체화한 getCount() 메소드를 받았습니다.
             */
            binder = IMyCounterService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }
    private var intent: Intent? = null
    private var running = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvCounter = findViewById<View>(R.id.tvCounter) as TextView
        btnStart = findViewById<View>(R.id.btnStart) as Button
        btnStart!!.setOnClickListener {
            intent = Intent(this@MainActivity, MyCounterService::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)
            running = true
            Thread(GetCountThread()).start()
        }
        btnStop = findViewById<View>(R.id.btnStop) as Button
        btnStop!!.setOnClickListener {
            unbindService(connection)
            running = false
        }
    }

    /**
     *
     */
    private inner class GetCountThread : Runnable {
        // binder에서 count가져와서 set시키려면 handler 필요
        private val handler = Handler()
        override fun run() {
            while (running) {
                if (binder == null) {
                    continue
                }
                handler.post {
                    try {
                        tvCounter!!.text = binder!!.count.toString() + ""
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }

                // 0.5초 텀을 준다.
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}