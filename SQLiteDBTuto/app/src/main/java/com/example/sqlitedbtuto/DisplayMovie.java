package com.example.sqlitedbtuto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayMovie extends AppCompatActivity implements View.OnClickListener {
    DBHelper helper;
    SQLiteDatabase db;
    EditText edit_name, edit_year, edit_director, edit_rating, edit_nation;
    int idValue;
    Bundle extras;
    Cursor movieCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        helper = new DBHelper(this);
        Button saveBtn = findViewById(R.id.save_btn);
        Button updateBtn = findViewById(R.id.update_btn);
        Button deleteBtn = findViewById(R.id.delete_btn);

        edit_name = findViewById(R.id.movie_name_editText);
        edit_year = findViewById(R.id.movie_year_editText);
        edit_director = findViewById(R.id.movie_director_editText);
        edit_rating = findViewById(R.id.movie_rating_editText);
        edit_nation = findViewById(R.id.movie_nation_editText);

        saveBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        //받아온 intent에서 id가 값이 있다면 추출
        extras = getIntent().getExtras();
        if (extras != null) {
            idValue = extras.getInt("id");
        }
        if (idValue>0){
            movieCursor = helper.getData(idValue);
            movieCursor.moveToFirst();
            edit_name.setText(movieCursor.getString(1));
            edit_director.setText(movieCursor.getString(2));
            edit_year.setText(movieCursor.getString(3));
            edit_nation.setText(movieCursor.getString(4));
            edit_rating.setText(movieCursor.getString(5));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                if (helper.insertMovie(
                        edit_name.getText().toString(),
                        edit_director.getText().toString(),
                        edit_year.getText().toString(),
                        edit_nation.getText().toString(),
                        edit_rating.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Insert Error", Toast.LENGTH_LONG).show();
                break;
            case R.id.update_btn:
                if (extras != null) {
                    if(idValue>0){
                        if (helper.updateMovie(
                                idValue,
                                edit_name.getText().toString(),
                                edit_director.getText().toString(),
                                edit_year.getText().toString(),
                                edit_nation.getText().toString(),
                                edit_rating.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Update Error!", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "처음 화면에서 리스트 목록을 선택해주세요.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn:
                if (extras != null) {
                    if(idValue>0){
                        if (helper.deleteMovie(idValue) > 0){
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Delete Error!", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "처음 화면에서 리스트 목록을 선택해주세요.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        Log.i("DisplayMovie", "버튼 finish 전");
        finish();
    }
}
