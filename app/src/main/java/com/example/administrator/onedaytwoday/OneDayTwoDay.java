package com.example.administrator.onedaytwoday;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class OneDayTwoDay extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/SeoulNamsanvert.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/SeoulNamsanB.ttf"));
    }


}
