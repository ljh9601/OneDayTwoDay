package com.example.administrator.onedaytwoday;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class TabFragment2 extends Fragment {
    private ArrayList<String> mList;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        mList = new ArrayList<String>();
        mListView= (ListView) view.findViewById(R.id.listview1);
        mAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mList.add("List1");
        mList.add("List2");
        mList.add("List3");
        mAdapter.notifyDataSetChanged();
        return view;
    }

}
