package com.example.administrator.onedaytwoday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Owner on 2016-12-06.
 */

public class BoardActivity extends AppCompatActivity  {
    private String selectedCity = MainActivity.selectedCity;
    private TextView textview = (TextView)findViewById(R.id.selected_city_text);
    private ImageView _backicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textview.setText(selectedCity+"에 대한 검색 결과");
        setContentView(R.layout.board_activity);
        initView();

    }

    public void initView() {
        _backicon = (ImageView)findViewById(R.id.backing);
    }
}
