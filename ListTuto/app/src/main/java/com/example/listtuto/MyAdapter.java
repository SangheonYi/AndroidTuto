package com.example.listtuto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listtuto.R;

public class MyAdapter extends BaseAdapter {
    private final Context context;
    private final String[] values;

    public MyAdapter(Context context, String[] values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;//item 갯수
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //inflater 선언 및 할당
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //반환할 커스텀 뷰
        View itemView = inflater.inflate(R.layout.list_item, viewGroup, false);
        TextView textView = (TextView) itemView.findViewById(R.id.label);//item의 텍스트 뷰
        ImageView imageView = (ImageView) itemView.findViewById(R.id.icon);//item의 이미지 뷰
        textView.setText(values[i]);//item의 텍스트 뷰의 텍스트 속성값을 생성할 때 입력한 배열의 값으로 줌
        String s = values[i];//item에 입력할 텍스트 값을 가져옴

        if (s.startsWith("App") || s.startsWith("Cher") || s.startsWith("Ban")){
            //입력될 텍스트 값에 따라 이미지를 다르게 할당
            imageView.setImageResource(R.drawable.good);
        } else {
            imageView.setImageResource(R.drawable.fig);
        }
        return itemView;//완성된 커스텀 list를 반환
    }
}
