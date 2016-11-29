package com.example.administrator.onedaytwoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Owner on 2016-11-30.
 */

public class resultSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] items = {"SM3", "SM5", "SM7"};
    private AutoCompleteTextView edit;
    private ImageView backIcon;

    public void initView(){
        backIcon = (ImageView) findViewById(R.id.resultbackIcon);
        edit = (AutoCompleteTextView) findViewById(R.id.autoResultSearchText);
        backIcon.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.resultbackIcon:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.autoResultSearchText:
                startActivity(new Intent(this, resultSearchActivity.class));
                break;
            default:
                break;
        }
    }
}
