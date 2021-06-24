package com.example.listtuto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list01);

        String[] values = {"Apple", "Apricot", "Avocado", "Banana",
        "Blackberry", "Blueberry", "Cherry", "Coconut", "Cranberry",
        "Grape Raisin", "Honeydew", "Jackfruit", "Lemon", "Lime",
        "Mango", "Watermelon"};//list 아이템의 label
        MyAdapter adapter = new MyAdapter(this, values);//커스텀 list 생성

        list.setAdapter(adapter);//커스텀 list 어플의 view로 set
    }
}
