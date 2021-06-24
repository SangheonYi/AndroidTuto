package com.example.sharedpreferencestuto

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var value: EditText
    var name: TextView? = null
    var imageName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name = findViewById(R.id.TextView01)
        value = findViewById(R.id.EditText01)
        val settings = getSharedPreferences(PREFS_NAME, 0)
        imageName = settings.getString("imageName", "")
        value.setText(imageName)
    }

    override fun onStop() {
        super.onStop()
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        imageName = value!!.text.toString()
        editor.putString("imageName", imageName)
        editor.commit()
    }

    companion object {
        const val PREFS_NAME = "MyPrefs"
    }
}