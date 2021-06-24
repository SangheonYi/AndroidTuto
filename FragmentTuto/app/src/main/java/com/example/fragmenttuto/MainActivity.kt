package com.example.fragmenttuto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fragmenttuto.R
import com.example.fragmenttuto.FragmentA
import com.example.fragmenttuto.FragmentB

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //fragment의 container가 안 비었을 시
        if (findViewById<View?>(R.id.fragment_container) != null) {
            //savedInstanceState?가 안 비었을 시
            if (savedInstanceState != null) return  //아무것도 안함
            val firstFragment = FragmentA() //첫 화면에서 쓸 Fragment 변수에 FragmentA를 할당
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, firstFragment)
                .commit()
            /* getSupportFragmentManager의 경우 androidx의 FragmentManager를 반환
               반환된 FragmentManager의 beginTransaction로 수정 시작을 알림
               firstFragment를 fragment_container에 추가함
               위의 사항을 commit으로 적용
            */
        }
    }

    fun selectFragment(view: View) {
        var fr: Fragment? = null
        when (view.id) {
            R.id.button1 -> {
                fr = FragmentA()
                Log.i("버튼", "A : $fr")
            }
            R.id.button2 -> {
                fr = FragmentB()
                Log.i("버튼", "B : $fr")
            }
        }

        /* getSupportFragmentManager의 경우 androidx의 FragmentManager를 반환
               반환된 FragmentManager의 beginTransaction로 수정 시작을 알림
               fragment_container의 Fragment를 fr로 교체
               addToBackStack은 일련의 작업을 stack에 push했다가
               기기의 back버튼을 누를 시 pop함으로써 작업 전의 Fragment로 복구시킬 수 있게 함
               위의 사항을 commit으로 적용
        */
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fr!!)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}