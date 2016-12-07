package com.example.administrator.onedaytwoday;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String selectedCity;
    public static int id = 0;
    private static String [] city = {"서울","부산","인천",
            "대전","대구","광주","울산", "수원", "분당", "경기", "청주", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도", "제주도", "강원도", "기타지역"};
    class NavItem{
        String mTitle;
        String mSubTitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon){
            mTitle = title;
            mSubTitle = subtitle;
            mIcon = icon;

        }
    }
    private static String TAG = MainActivity.class.getSimpleName();
    private ViewPager viewPager;
    private GoogleApiClient client;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_btn);
        fab.setOnClickListener(this);
        ImageView sidebar = (ImageView) findViewById(R.id.sideBar);
        ImageView searchImage = (ImageView) findViewById(R.id.search_icon);
        sidebar.setOnClickListener(this);
        searchImage.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Initializing the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("대타 알바"));
        tabLayout.addTab(tabLayout.newTab().setText("단기 알바"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Three"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Spinner s1 = (Spinner) findViewById(R.id.spinner1);

        // 1 city 에 대한 Spinner
        ArrayAdapter<String> adapter; // 데이터
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), // 현재화면의 제어권자
//                android.R.layout.simple_spinner_item, // 레이아웃
                R.layout.spin,
                city);
        adapter.setDropDownViewResource(
                //android.R.layout.simple_spinner_dropdown_item);
                R.layout.spin_dropdown);

        s1.setAdapter(adapter);

        final TextView tvAdd = (TextView)findViewById(R.id.textView3);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tvAdd.setText("주소 : "+city[position]);
                selectedCity = s1.getSelectedItem().toString();
//                Log.d("test",s1.getSelectedItem().toString()); // 실제 내용
//                Log.d("test",s1.getSelectedItemId() +""); // positon
//                Log.d("test",s1.getSelectedItemPosition() +""); // positon
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.addDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }


        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_icon:
                startActivity(new Intent(this, searchActivity.class));
                break;
            case R.id.floating_btn:
                Toast.makeText(getApplicationContext(), "FAB", Toast.LENGTH_LONG).show();
                break;
            case R.id.sideBar:
                Toast.makeText(getApplicationContext(), "Sidebar", Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
    }
}