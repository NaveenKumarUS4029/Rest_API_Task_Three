package com.example.restapitaskthree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.restapitaskthree.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding amb;
    String user,fst_name,lst_name,avatar,mail;
    String url = "https://reqres.in/api/users?page=2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amb = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    public void getdata(View view) {
        AsyncClass asyncClass= new AsyncClass();
        asyncClass.execute();
    }

    private class AsyncClass extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HTTPHandler httpHandler = new HTTPHandler();
            String h = httpHandler.makeServiceCall(url);
            Log.i("abc",""+h);

            try {
                JSONObject jsonObject = new JSONObject(h);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                int num = Integer.valueOf(amb.enterNum.getText().toString());

                JSONObject jsonObject1 = jsonArray.getJSONObject(num);
                user = jsonObject1.getString("id");
                mail = jsonObject1.getString("email");
                fst_name = jsonObject1.getString("first_name");
                lst_name = jsonObject1.getString("last_name");
                avatar = jsonObject1.getString("avatar");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            amb.userid.setText(user);
            amb.email.setText(mail);
            amb.fstName.setText(fst_name);
            amb.lstName.setText(lst_name);
            Glide.with(MainActivity.this)
                    .load(avatar)
                    .circleCrop()
                    .into(amb.imageView);
        }
    }
}