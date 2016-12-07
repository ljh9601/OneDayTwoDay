package com.example.administrator.onedaytwoday;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private Button _register, _back, _pick;
    private DatePickerDialog datePicker;
    private EditText _name, _inputpassword, _checkpassword, _email, _inputid, _phone;
    private String targetUrl = UrlContainer.MAIN_URL + UrlContainer.REST_REGISTER;
    private ServerHandler serverHandler;

    private boolean register(){
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        _register.setEnabled(false);
        if(_name.getText().toString().equals("")) {
            _name.setError("이름을 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_inputid.getText().toString().equals("")) {
            _inputid.setError("아이디를 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_inputpassword.getText().toString().equals("")) {
            _inputpassword.setError("아이디를 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_checkpassword.getText().toString().equals("")) {
            _checkpassword.setError("아이디를 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_phone.getText().toString().equals("")){
            _phone.setError("아이디를 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_email.getText().toString().equals("")) {
            _email.setError("아이디를 입력하세요");
            progressDialog.dismiss();
            _register.setEnabled(true);
            return false;
        }
        if(_pick.getText().toString().equals("생년월일 선택")){
            Toast.makeText(getApplicationContext(),"생년월일을 선택해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!(_inputpassword.getText().toString().equals(_checkpassword.getText().toString()))
                && !(_inputpassword.getText().toString().equals("")) && !(_checkpassword.getText().toString().equals(""))){
            Toast.makeText(getApplicationContext(), "입력한 비밀번호가 일치하지 않습니다. 다시 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("Uid", _inputid.getText().toString());
        data.put("Pwd", _inputpassword.getText().toString());
        data.put("Name", _name.getText().toString());
        data.put("BDate", _pick.getText().toString());
        data.put("Phone", _phone.getText().toString());
        data.put("Email", _email.getText().toString());
        serverHandler = new ServerHandler(data, targetUrl);
        serverHandler.POST(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String json = msg.getData().getString("json");
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    Intent call = new Intent(RegisterActivity.this, IntroActivity.class);
                    progressDialog.dismiss();
                    _register.setEnabled(true);
                    startActivity(call);
                    finish();
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    _register.setEnabled(true);
                }
            }
        });
        return true;
    }

    public void initView(){
        _register = (Button)findViewById(R.id.register);
        _back = (Button)findViewById(R.id.back);
        _back.setOnClickListener(this);
        _register.setOnClickListener(this);
        _name = (EditText)findViewById(R.id.name);
        _inputid = (EditText)findViewById(R.id.input_id);
        _inputpassword = (EditText)findViewById(R.id.input_password);
        _checkpassword = (EditText)findViewById(R.id.check_password);
        _email = (EditText)findViewById(R.id.email);
        _phone = (EditText)findViewById(R.id.phone);
        _pick = (Button)findViewById(R.id.pick_birth);
        _pick.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _pick.setText(year + "-" + (monthOfYear +1) + "-" + dayOfMonth);
    }

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                if(!register())
                    break;
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                break;
            case R.id.back:
                startActivity(new Intent(this, IntroActivity.class));
                break;
            case R.id.pick_birth:
                datePicker.show();
                break;
            default: break;
        }
    }
}