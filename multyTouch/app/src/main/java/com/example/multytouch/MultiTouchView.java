package com.example.multytouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.jar.Attributes;

public class MultiTouchView extends View {

    private static final int SIZE = 60;//그려질 원 크기

    final int MAX_POINTS = 10;//최대 점 갯 수
    float[] x = new float[MAX_POINTS];//터치 x좌표 배열
    float[] y = new float[MAX_POINTS];//터치 y좌표 배열
    boolean[] touching = new boolean[MAX_POINTS];//터치 여부 배열

    private Paint mPaint;//그려줄 paint 객체

    public MultiTouchView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        initView();
    }

    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//paint의 antialias효과를 주겠다
        mPaint.setColor(Color.BLUE);//파란색으로 칠함
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//외곽선 그리고 채우기
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){//터치 시
        int index = event.getActionIndex();//이벤트 색인 값 가져오기
        int id = event.getPointerId(index);//이벤트의 id(구분자? 특정 지을 무언가)를 가져옴
        int action = event.getActionMasked();//이벤트 액션의 제스쳐 정보를 가져옴

        switch (action){//각 제스쳐 발생 시
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                x[id] = (int) event.getX(index);//이벤트 발생 x좌표
                y[id] = (int) event.getY(index);//이벤트 발생 y좌표
                touching[id] = true;//터치 됨
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touching[id] = false;//터치 끝
                break;
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas){//이벤트로 입력된 데이터에 따라 그림을 그림
        super.onDraw(canvas);

        for (int i = 0; i < MAX_POINTS; i++){
            if (touching[i]){
                canvas.drawCircle(x[i], y[i], SIZE, mPaint);//이벤트로 입력된 데이터에 따라 그림을 그림
            }
        }
    }
}
