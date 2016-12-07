package com.example.administrator.onedaytwoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Owner on 2016-11-30.
 */

public class searchActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<String> items;
    private AutoCompleteTextView edit;
    private ImageView backIcon;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public void initView(){
        backIcon = (ImageView) findViewById(R.id.back_Icon);
        edit = (AutoCompleteTextView) findViewById(R.id.autoSearchText);
        backIcon.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        edit.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items));
        pref = getSharedPreferences("ONEDAYTWODAY", MODE_PRIVATE);
        editor = pref.edit();
        items = new ArrayList<String>();
        getItems();
    }

    public void getItems(){
        String Url = UrlContainer.MAIN_URL + UrlContainer.REST_KEYWORD_OF_USER + "?Uid=" + pref.getString("Uid","#");
        ServerHandler serverHandler = new ServerHandler(Url);
        serverHandler.GET(new Handler(){
            @Override
            public void handleMessage(Message msg){
                String json = msg.getData().getString("json");
                try {
                    JSONArray jsonArr = new JSONArray(json);
                    for(int i = 0; i < jsonArr.length(); i++){
                        JSONObject json_list = jsonArr.getJSONObject(i);
                        if(json_list.getString("Uid").equals(pref.getString("Uid", "#"))){
                            items.add(json_list.getString("Keyword"));
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "최근 검색어를 불러오는 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }


        });
    }

    public void insertKeyword(){
        String Url = UrlContainer.MAIN_URL + UrlContainer.REST_NEW_KEYWORD;

        HashMap<String, String> data = new HashMap<String, String>();

        data.put("Uid", pref.getString("Uid", "#"));
        data.put("Keyword", edit.getText().toString());
        ServerHandler serverHandler = new ServerHandler(data, Url);
        serverHandler.POST(new Handler(){
            @Override
            public void handleMessage(Message msg){
                String json = msg.getData().getString("json");
                try{
                    JSONArray jsonArray = new JSONArray(json);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    Intent call = new Intent(searchActivity.this, resultSearchActivity.class);

                    startActivity(call);
                    finish();
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(),"검색에 실패하였습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back_Icon:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.search_icon:
                insertKeyword();
                startActivity(new Intent(this, resultSearchActivity.class));
                break;
            default:
                break;
        }
    }
}