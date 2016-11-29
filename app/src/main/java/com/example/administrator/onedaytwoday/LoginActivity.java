package com.example.administrator.onedaytwoday;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button _login;
    private EditText _id, _pwd;
    private String targetUrl = UrlContainer.MAIN_URL + UrlContainer.REST_LOGIN;
    private String Uid;
    private String Pwd;
    private boolean isLoggedIn;

    public boolean tryLogin(){
        HashMap<String, String> data = new HashMap<>();
        if(_id.getText().toString().equals("") || _pwd.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            data.put("Uid", _id.getText().toString());
            data.put("Pwd", _pwd.getText().toString());
            try {
                URL url = new URL(targetUrl);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDefaultUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");
                http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                String body = "Uid=" + _id.getText().toString() + "&Pwd=" + _pwd.getText().toString();
                OutputStream os = http.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.flush();
                os.close();
                InputStreamReader reader = new InputStreamReader(http.getInputStream());
                BufferedReader br = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                String temp;
                while((temp = br.readLine()) != null){
                    builder.append(temp + "\n");
                }
                System.out.println(builder.toString());
            } catch (MalformedURLException e) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                //if(!(isLoggedIn = tryLogin()))
                  //  break;
                startActivity(new Intent(this, MainActivity.class ));
                break;
            default: break;
        }
    }

    public void initView(){
        _login = (Button)findViewById(R.id.login);
        _login.setOnClickListener(this);
        _id = (EditText)findViewById(R.id.login_account);
        _pwd = (EditText)findViewById(R.id.login_password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
}
