package com.example.administrator.onedaytwoday;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;
    private Button _signup, _signin;
    private LoginButton _fbsignin;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fbsignin:
                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
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
                        String id;
                        String name;
                        String email;
                        String birthday;

                        Log.v("result", object.toString());
                        try {
                            id = object.getString("id");
                            name = object.getString("name");
                            email = object.getString("email");
                            birthday = object.getString("birthday");

                            editor.putString("Uid", id);
                            editor.putString("Name", name);
                            editor.putString("Email", email);
                            editor.commit();

                        }catch(JSONException e){
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday");
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

    public String FBbirthDateFormat(String fbBirth){
        String birth = fbBirth;
        String dateFormat = "19";
        String[] birthday = new String[3];
        birthday = dateFormat.split("\\/");
        for(int i = 0; i < 2; i++){
            dateFormat += birthday[i] + "-";
        }
        dateFormat += birthday[2];
        return dateFormat;
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
        pref = getSharedPreferences("ONEDAYTWODAY", MODE_PRIVATE);
        initView();
    }
}