package com.example.edittexttuto

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var timer : CountDownTimer
    lateinit var tv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lyric =
            "[00:16:200]we wish you a merry christmas\n[00:18:300]we wish you a merry christmas\n[00:21:100]we wish you a merry christmas\n[00:23:600]and a happy new year\n"
        val list = lyric.split('[')
        var tmp = ""
        var i = 0
        var lengthStack = 0

        tv = findViewById(R.id.lyricView)
        tv.setText(lyric, TextView.BufferType.SPANNABLE)
        timer = object : CountDownTimer(10000, 500) {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTick(millisUntilFinished: Long) {
                tv.text = setHigh(lengthStack, list[i].length)
                Log.i("tier", "$lengthStack ${lengthStack + list[i].length}")

                lengthStack += list[i].length
                if (i < list.lastIndex)
                    i++
                else {
                    i = 0
                    lengthStack = 0
                }

            }

            override fun onFinish() {
            }

        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setHigh(start:Int, target: Int): Spannable {
        val spanLyric = tv.text as Spannable

        spanLyric.setSpan(
            BackgroundColorSpan(getColor(R.color.purple_500)), start, start + target,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spanLyric

    }
}