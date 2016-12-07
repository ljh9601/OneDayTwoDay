package com.example.administrator.onedaytwoday;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Administrator on 2016-12-06.
 */

public class MyFireBaseMessagingService extends FirebaseMessagingService{

    private SharedPreferences pref, set;
    private SharedPreferences.Editor editor;

    @Override
    public void onMessageReceived(RemoteMessage message){
        String from = message.getFrom();
        Map<String, String> data = message.getData();
        String title = data.get("title");
        String msg = data.get("message");

        pref = getSharedPreferences("ONEDAYTWODAY", MODE_PRIVATE);
        editor = pref.edit();

        set = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("FCM Message", from + "/" + title + " : " + msg);
    }
}