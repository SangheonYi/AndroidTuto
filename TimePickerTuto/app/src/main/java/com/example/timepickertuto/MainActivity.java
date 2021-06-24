package com.example.timepickertuto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    //필요한 변수 선언
    private TimePicker timePicker;
    private TextView time;
    private String format = "";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 각각의 View를 변수에 할당
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        time = (TextView) findViewById(R.id.time);
        // TimePicker의 시간과 분을 가져와서 변수에 할당
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();

        // TimePicker View의 시간을 새로 맞출 시에 불리는 리스너 할당
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                showTime(timePicker.getHour(), timePicker.getMinute());//새로운 시간과 분을 text뷰에 띄워줌
            }
        });
        showTime(hour, min);//어플 시작 시 시간과 분을 text뷰에 띄워줌
    }

    public void showTime(int hour, int min){
        //시간에 따라 TextView에 표시할 값을 정함
        if (hour == 0){
            hour += 12;
            format = "AM";
        } else if (hour == 12){
            format = "PM";
        } else if (hour > 12){
            hour -= 12;
            format = "PM";
        } else  {
            format = "AM";
        }
        //정한 값을 새롭게 TextView에 표시
        time.setText(new StringBuilder().append(hour).append(":").append(min).append(" ").append(format));

    }
}
