package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";

    Button btn_Update;
    Button btn_Insert;
    Button btn_Select;
    EditText edit_Title;
    EditText edit_Page;
    TextView text_Title;
    TextView text_Page;
    CheckBox check_Title;
    CheckBox check_Page;

    long nowIndex;
    String Title;
    long Page;

    String sort = "title";

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private MyDatabaseOpenHelper mMyDatabaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Insert = (Button) findViewById(R.id.btn_insert);
        btn_Insert.setOnClickListener(this);
        btn_Update = (Button) findViewById(R.id.btn_update);
        btn_Update.setOnClickListener(this);
        btn_Select = (Button) findViewById(R.id.btn_select);
        btn_Select.setOnClickListener(this);

        edit_Title = (EditText) findViewById(R.id.edit_title);
        edit_Page = (EditText) findViewById(R.id.edit_page);

        text_Title = (TextView) findViewById(R.id.text_title);
        text_Page = (TextView) findViewById(R.id.text_page);

        check_Title = (CheckBox) findViewById(R.id.check_title);
        check_Title.setOnClickListener(this);
        check_Page = (CheckBox) findViewById(R.id.check_page);
        check_Page.setOnClickListener(this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);

        mMyDatabaseOpenHelper = new MyDatabaseOpenHelper(this);
        mMyDatabaseOpenHelper.open();
        mMyDatabaseOpenHelper.create();

        check_Title.setChecked(true);
        showDatabase(sort);

        btn_Insert.setEnabled(true);
        btn_Update.setEnabled(false);
    }

    public void setInsertMode(){
        edit_Title.setText("");
        edit_Page.setText("");

        btn_Insert.setEnabled(true);
        btn_Update.setEnabled(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            edit_Title.setText(tempData[0].trim());
            edit_Page.setText(tempData[1].trim());

            btn_Insert.setEnabled(false);
            btn_Update.setEnabled(true);
        }
    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3];
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mMyDatabaseOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                            setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    public void showDatabase(String sort){
        Cursor iCursor = mMyDatabaseOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempTitle = iCursor.getString(iCursor.getColumnIndex("title"));
            tempTitle = setTextLength(tempTitle,10);
            String tempPage = iCursor.getString(iCursor.getColumnIndex("last_page"));
            tempPage = setTextLength(tempPage,10);

            String Result = tempTitle + tempPage;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                Title = edit_Title.getText().toString();
                try {
                    Page = Long.parseLong(edit_Page.getText().toString());
                }
                catch (Exception e){
                    Log.e("On Click", "insert 단디 느으라");
                }
                mMyDatabaseOpenHelper.open();
                mMyDatabaseOpenHelper.insertColumn( Title, Page);
                showDatabase(sort);
                setInsertMode();
                edit_Title.requestFocus();
                edit_Title.setCursorVisible(true);
                break;

            case R.id.btn_update:
                Title = edit_Title.getText().toString();
                try {
                    Page = Long.parseLong(edit_Page.getText().toString());
                }
                catch (Exception e){
                    Log.e("On Click", "update 단디 느으라");
                }
                mMyDatabaseOpenHelper.updateColumn(nowIndex, Title, Page);
                showDatabase(sort);
                setInsertMode();
                edit_Title.requestFocus();
                edit_Title.setCursorVisible(true);
                break;

            case R.id.btn_select:
                showDatabase(sort);
                break;

            case R.id.check_title:
                check_Page.setChecked(false);
                sort = "title";
                break;

            case R.id.check_page:
                check_Title.setChecked(false);
                sort = "last_page";
                break;
        }
    }
}
