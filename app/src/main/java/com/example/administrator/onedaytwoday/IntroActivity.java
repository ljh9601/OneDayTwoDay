package com.example.administrator.onedaytwoday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    private CallbackManager callbackManager;
    private Button _signup, _signin;
    private LoginButton _fbsignin;

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fbsignin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default: break;
        }
    }

    public void initView(){
        _fbsignin = (LoginButton)findViewById((R.id.fbsignin));
        _fbsignin.setReadPermissions(Arrays.asList("public_profile", "email"));
        _fbsignin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result", object.toString());
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // nothing to do
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Login Error", error.toString());
            }
        });
        _signin = (Button)findViewById(R.id.signin);
        _signin.setOnClickListener(this);
        _signup = (Button)findViewById(R.id.signup);
        _signup.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_intro);
        callbackManager = CallbackManager.Factory.create();
        initView();
    }
}
