package com.example.administrator.onedaytwoday;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TabFragment1 extends Fragment {
    private ArrayList<String> mList;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private ServerHandler serverHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        mList = new ArrayList<String>();
        mListView= (ListView) view.findViewById(R.id.listview1);
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mList);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ListViewItemClickListener());
        mListView.setOnItemLongClickListener(new ListViewItemLongClickListener());
        mListView.setSelector(R.drawable.list_selector);
        mAdapter.notifyDataSetChanged();
        serverHandler = new ServerHandler(UrlContainer.MAIN_URL + UrlContainer.REST_JOBBY_TYPE);

        return view;
    }

    public void daetaJob(){
        serverHandler.GET(new Handler(){
            @Override
            public void handleMessage(Message msg){
                String json = msg.getData().getString("json");
                try {
                    JSONArray jsonArr = new JSONArray(json);
                    for(int i = 0; i < jsonArr.length(); i++){
                        JSONObject json_list = jsonArr.getJSONObject(i);
                        if(json_list.getInt("Type") == -1) continue;
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "정보를 불러오는 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }finally {
                }
            }
        });

    }


    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
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

            alertDlg.setMessage( mList.get(position) );
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
                    mAdapter.notifyDataSetChanged();
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