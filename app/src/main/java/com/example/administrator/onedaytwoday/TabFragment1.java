package com.example.administrator.onedaytwoday;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TabFragment1 extends Fragment {
    private ArrayList<String> mList;
    private ListView mListView;
    private ArrayAdapter mAdapter;

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


        mList.add("List1");
        mList.add("List2");
        mList.add("List3");
        mList.add("List4");
        mList.add("List5");
        mList.add("List6");
        mList.add("List7");
        mList.add("List8");
        mList.add("List9");
        mList.add("List10");
        mList.add("List11");
        mList.add("List12");
        mList.add("List13");
        mList.add("List14");
        mList.add("List15");
        mList.add("List16");
        mList.add("List17");
        mList.add("List18");
        mList.add("List19");
        mList.add("List20");
        mList.add("List21");
        mList.add("List22");
        mList.add("List23");
        mAdapter.notifyDataSetChanged();

        return view;
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
            alertDlg.setMessage( String.format( getString(R.string.alert_msg_delete),
                    mList.get(position)) );
            alertDlg.show();
            return true;
        }
    }
}
