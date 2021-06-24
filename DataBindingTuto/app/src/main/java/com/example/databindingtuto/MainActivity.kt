package com.example.databindingtuto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.databindingtuto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var hello = "Hello World!"
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)// 잊지마라 activity에서 setContentview를 id로하면 layout이 이중으로 inflate 됨
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        hello = "create"
    }

    override fun onPause() {
        super.onPause()
        hello += "pause"
    }
    override fun onStart() {
        super.onStart()
        hello += "start"
    }
    fun tvClick(view: View) {
        Log.i("tvClick", "hello: $hello")
        hello += "click"
        if (hello.length >= 15)
            hello = "clear"
        binding.activity = this
    }
}