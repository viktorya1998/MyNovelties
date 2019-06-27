package com.example.mynovelties.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.mynovelties.DBHelper;
import com.example.mynovelties.NoveltiesList;
import com.example.mynovelties.OpenNoveltiesAdapter;
import com.example.mynovelties.R;
import com.example.mynovelties.TransportNovelties;

import java.util.ArrayList;

public class Open extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context con = this;
    private ArrayList<NoveltiesList> listNew;
    private DBHelper dbhelper;
    private SQLiteDatabase database;
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
        setContentView(R.layout.novelties_main);

        dbhelper = new DBHelper(con);
        recyclerView = findViewById(R.id.recycler_view);
        database = dbhelper.getWritableDatabase();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_novelties).setChecked(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefresh = findViewById(R.id.swipe);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
                OpenNoveltiesAdapter openNoveltiesAdapter = new OpenNoveltiesAdapter(listNew, con);
                recyclerView.setAdapter(openNoveltiesAdapter);
                mSwipeRefresh.setRefreshing(false);
            }
        });


        getList();
        OpenNoveltiesAdapter openNoveltiesAdapter = new OpenNoveltiesAdapter(listNew, con);
        recyclerView.setAdapter(openNoveltiesAdapter);
    }

    private void getList(){
        NoveltiesList N = TransportNovelties.NL;
        listNew = new ArrayList<>();
        listNew.add(new NoveltiesList(N.getTitle(), N.getImage(), N.getText(), N.getUrl(), N.getDate()));
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
