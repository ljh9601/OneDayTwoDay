package com.example.administrator.onedaytwoday;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button _login;
    private EditText _id, _pwd;
    private String targetUrl = UrlContainer.MAIN_URL + UrlContainer.REST_LOGIN;
    private String Uid;
    private String Pwd;
    private boolean isLoggedIn;
    private ServerHandler serverHandler;

    public boolean tryLogin(){
        HashMap<String, String> data = new HashMap<>();
        if(_id.getText().toString().equals("") || _pwd.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            data.put("Uid", _id.getText().toString());
            data.put("Pwd", _pwd.getText().toString());
            serverHandler = new ServerHandler(data, targetUrl);
            serverHandler.POST(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String json = msg.getData().getString("json");
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        JSONObject obj = jsonArray.getJSONObject(0);
                        Intent call = new Intent(LoginActivity.this, IntroActivity.class);
                        startActivity(call);
                        finish();
                    } catch (JSONException e) {
                    }
                }
            });
        }
        return true;
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
