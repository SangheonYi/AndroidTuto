package com.example.grimpan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class SingleTouchView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();

    public SingleTouchView(Context context, AttributeSet attributes){
        super(context, attributes);
        paint.setAntiAlias(true);//paint객체에 AntiAlias속성을 설정
        paint.setStrokeWidth(10f);//선 굵기를 10f로
        paint.setColor(Color.BLUE);//선 색을 파랑으로
        paint.setStyle(Paint.Style.STROKE);//외곽선이 나오도록
        paint.setStrokeJoin(Paint.Join.ROUND);//꼭지점이 둥글게
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPath(path, paint);//지정한 경로대로 paint를 그리기
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){//터치 이벤트 발생 시
        super.onTouchEvent(event);
        float eventX = event.getX();//이벤트 발생 지점의 x좌표
        float eventY = event.getY();//이벤트 발생 지점의 y좌표

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://터치 한 순간
                path.moveTo(eventX, eventY);//path 초기 위치 지정
                return true;
            case MotionEvent.ACTION_MOVE://터치 된 채 움직일 때
                path.lineTo(eventX, eventY);//움직이는 경로를 path에
                break;
            case MotionEvent.ACTION_UP:// 손가락 떼면
                break;
            default:
                return false;

        }
        invalidate();//화면 갱신
        return true;
    }
}
