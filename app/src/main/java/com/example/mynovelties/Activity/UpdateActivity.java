package com.example.mynovelties.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mynovelties.R;

import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {

    private Context con = this;
    private SwipeRefreshLayout mSwipeRefresh;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_save:
                    menu_save();
                    return true;
                case R.id.navigation_novelties:
                    menu_novelties();
                    return true;
                case R.id.navigation_setting:
                    menu_setting();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_signal);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_novelties).setChecked(true);

        mSwipeRefresh =  findViewById(R.id.swipe);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()){
                    mSwipeRefresh.setRefreshing(false);
                    Intent intent = new Intent(UpdateActivity.this, NoveltiesActivity.class);
                    startActivity(intent);
                }else {
                    mSwipeRefresh.setRefreshing(false);
                    Toast toast = Toast.makeText(getApplicationContext(), "Нет сигнала", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    protected boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    protected void menu_setting() {
        Intent intent = new Intent(con, SettingActivity.class);
        startActivity(intent);
    }
    protected void menu_novelties() {
        Intent intent = new Intent(con, NoveltiesActivity.class);
        startActivity(intent);
    }
    protected void menu_save() {
        Intent intent = new Intent(con, SaveActivity.class);
        startActivity(intent);
    }
}
