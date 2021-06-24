package com.example.broadcastrecievertuto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSION_SMS = 10;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();//받아온 intent의 이벤트를 String으로 할당
            if (action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){//sms 수신 이벤트인지 판별
                String smsBody = "";
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)){
                    //for문의 조건에서 smsMessage에 sms문자 받은거 할당
                    smsBody += smsMessage.getMessageBody();//할당된 sms메시지에서 내용을 smsBody에 할당
                }
                Toast.makeText(getApplicationContext(), smsBody, Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sms권한 요청
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_SMS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();//intent filter 생성
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");//filter에 sms 수신 action 추가
        registerReceiver(receiver, filter);//receiver와 filter 등록
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);//액티비티 pause시에 receiver 등록 취소
    }
}
