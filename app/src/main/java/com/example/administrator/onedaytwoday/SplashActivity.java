package com.example.administrator.onedaytwoday;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private Handler h;
    private int delayTime = 1700;
    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        h = new Handler();
        h.postDelayed(intro, delayTime);
    }

    Runnable exit = new Runnable() {
        public void run() {
            System.exit(0);
        }
    };

    Runnable intro = new Runnable() {
        public void run() {
            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h.removeCallbacks(intro);
    }
}
