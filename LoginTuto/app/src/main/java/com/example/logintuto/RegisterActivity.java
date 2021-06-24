package com.example.logintuto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText register_idText = (EditText) findViewById(R.id.register_idText);
        EditText register_pwText = (EditText) findViewById(R.id.register_pwText);
        EditText register_nameText = (EditText) findViewById(R.id.register_nameText);
        EditText register_ageText = (EditText) findViewById(R.id.register_ageText);
        Button register_Button = (Button) findViewById(R.id.register_Button);

    }
}
