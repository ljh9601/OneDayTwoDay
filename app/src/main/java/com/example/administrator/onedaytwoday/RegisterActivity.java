package com.example.administrator.onedaytwoday;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private Button _pick;
    private DatePickerDialog datePicker;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _pick.setText(year + "-" + (monthOfYear +1) + "-" + dayOfMonth);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pick_birth:
                datePicker.show();
                break;
            default: break;
        }
    }

    public void initView(){
        _pick = (Button)findViewById(R.id.pick_birth);
        _pick.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
}
