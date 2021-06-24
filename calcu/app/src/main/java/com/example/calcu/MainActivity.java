package com.example.calcu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {
    int left;
    int right;
    String op;
    EditText eT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eT = (EditText) findViewById(R.id.number);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.no0 :
                eT.append("0");
                break;
            case R.id.no1 :
                eT.append("1");
                break;
            case R.id.no2 :
                eT.append("2");
                break;
            case R.id.no3 :
                eT.append("3");
                break;
            case R.id.no4 :
                eT.append("4");
                break;
            case R.id.no5 :
                eT.append("5");
                break;
            case R.id.no6 :
                eT.append("6");
                break;
            case R.id.no7 :
                eT.append("7");
                break;
            case R.id.no8 :
                eT.append("8");
                break;
            case R.id.no9 :
                eT.append("9");
                break;
            case R.id.plus :
                opClicked();
                op = "+";
                break;
            case R.id.minus :
                opClicked();
                op = "-";
                break;
            case R.id.multiply :
                opClicked();
                op = "*";
                break;
            case R.id.divide :
                opClicked();
                op = "/";
                break;
            case R.id.clear :
                setClear();
                break;
            case R.id.OK :
                int cal = 0;
                right = Integer.parseInt(eT.getText().toString());
                switch (op) {
                    case "+":
                        cal = left + right;
                        eT.setText(" = " + cal);
                        break;
                    case "-":
                        cal = left - right;
                        eT.setText(" = " + cal);
                        break;
                    case "*":
                        cal = left * right;
                        eT.setText(" = " + cal);
                        break;
                    case "/":
                        cal = left / right;
                        eT.setText(" = " + cal);
                        break;
                    default:
                        setClear();
                        eT.setText("retry");
                        break;
                }
                break;
        }
    }
    public void opClicked(){
        left = Integer.parseInt(eT.getText().toString());
        eT.setText("");
    }
    public void setClear(){
        left = 0;
        right = 0;
        op = "";
        eT.setText("");
    }
}
