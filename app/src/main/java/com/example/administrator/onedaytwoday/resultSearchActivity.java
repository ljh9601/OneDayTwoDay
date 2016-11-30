package com.example.administrator.onedaytwoday;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2016-11-30.
 */

public class resultSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] items = {"SM3", "SM5", "SM7"};
    private AutoCompleteTextView edit;
    private ImageView backIcon;
    private ListView resultList;
    private List mList;
    private ImageView autoResult_search;
    public void initView(){
        mList = new ArrayList<String>();

        backIcon = (ImageView) findViewById(R.id.resultbackIcon);
        edit = (AutoCompleteTextView) findViewById(R.id.autoResultSearchText);
        backIcon.setOnClickListener(this);
        autoResult_search = (ImageView)findViewById(R.id.autoResult_search_icon);
        autoResult_search.setOnClickListener(this);
        edit.setOnClickListener(this);
        resultList = (ListView) findViewById(R.id.resultSearchList);
        resultList.setOnItemClickListener(new ListViewItemClickListener());
        resultList.setOnItemLongClickListener(new ListViewItemLongClickListener());
        resultList.setSelector(R.drawable.list_selector);
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
            case R.id.autoResult_search_icon:
                startActivity(new Intent(this, resultSearchActivity.class));
                break;
            default:
                break;
        }
    }
    private class ListViewItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setPositiveButton( R.string.button_ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which ) {
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            alertDlg.setMessage((Integer) mList.get(position));
            alertDlg.show();
        }
    }
    int selectedPos = -1;

    /**
     * ListView의 item을 길게 클릭했을 경우.
     * 클릭된 item을 삭제한다.
     * @author stargatex
     *
     */
    private class ListViewItemLongClickListener implements AdapterView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectedPos = position;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setTitle(R.string.alert_title_question);

            // '예' 버튼이 클릭되면
            alertDlg.setPositiveButton( R.string.button_yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                    //mList.remove(selectedPos);
                    // 아래 method를 호출하지 않을 경우, 삭제된 item이 화면에 계속 보여진다.
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            // '아니오' 버튼이 클릭되면
            alertDlg.setNegativeButton( R.string.button_no, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });
            // alertDlg.setMessage( String.format( getString(R.string.alert_msg_delete),
            //       mList.get(position)) );
            alertDlg.show();
            return true;
        }
    }
}
