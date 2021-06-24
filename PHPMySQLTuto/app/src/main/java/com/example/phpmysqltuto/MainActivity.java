package com.example.phpmysqltuto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "userID";
    private static final String TAG_PW = "userPassword";
    private static final String TAG_NAME = "userName";
    private static final String TAG_AGE = "userAge";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personlist;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        personlist = new ArrayList<>();
        getData("http://192.168.43.86:80/PHP_connection.php");
    }

    protected void showList() throws JSONException{
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            peoples = jsonObject.getJSONArray(TAG_RESULTS);
            for(int i = 0; i<peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String pw = c.getString(TAG_PW);
                String name = c.getString(TAG_NAME);
                String age = c.getString(TAG_AGE);

                HashMap<String, String> persons = new HashMap<>();

                persons.put(TAG_ID, id);
                persons.put(TAG_PW, pw);
                persons.put(TAG_NAME, name);
                persons.put(TAG_AGE, age);

                personlist.add(persons);
            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personlist, R.layout.list_item,
                    new String[]{TAG_ID, TAG_PW, TAG_NAME, TAG_AGE},
                    new int[]{R.id.id, R.id.pw, R.id.name, R.id.age}
            );
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url){

        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    class GetDataJSON extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String uri = params[0];
            BufferedReader bufferedReader;
            try {
                Log.i("doInBackground","try");
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                con.connect();
                Log.i("doInBackground","con is " + con);
                if (con == null)
                    Log.i("doInBackground","con is null");
                InputStream in = new BufferedInputStream(con.getInputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(in));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json).append("\n");
                }
                Log.i("doInBackground","백그 완성");
                return sb.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            myJSON = result;
            if (result!=null){
                try {
                    showList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
