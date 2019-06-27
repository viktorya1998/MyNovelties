package com.example.mynovelties.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.mynovelties.DBHelper;
import com.example.mynovelties.NoveltiesAdapter;
import com.example.mynovelties.NoveltiesList;
import com.example.mynovelties.R;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public Context con = this;
    private ArrayList<NoveltiesList> listNew;
    DBHelper dbhelper;
    SQLiteDatabase database;
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_save).setChecked(true);

        mSwipeRefresh =  findViewById(R.id.swipe);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetDateBase();

                Drawable dr2 = getResources().getDrawable(android.R.drawable.btn_star_big_on);
                Drawable dr1 = getResources().getDrawable(android.R.drawable.btn_star_big_off);

                NoveltiesAdapter noveltiesAdapter = new NoveltiesAdapter(listNew, con, dr1, dr2);
                recyclerView.setAdapter(noveltiesAdapter);
                mSwipeRefresh.setRefreshing(false);
            }
        });


        GetDateBase();

        Drawable dr2 = getResources().getDrawable(android.R.drawable.btn_star_big_on);
        Drawable dr1 = getResources().getDrawable(android.R.drawable.btn_star_big_off);

        NoveltiesAdapter noveltiesAdapter = new NoveltiesAdapter(listNew, con, dr1, dr2);
        recyclerView.setAdapter(noveltiesAdapter);

    }

    protected void menu_setting() {

        Intent intent = new Intent(SaveActivity.this, SettingActivity.class);
        startActivity(intent);
    }
    protected void menu_novelties() {

        Intent intent = new Intent(SaveActivity.this, NoveltiesActivity.class);
        startActivity(intent);
    }
    protected void menu_save() {

    }
    protected void GetDateBase() {

        listNew = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CONSTANT, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int i1 = cursor.getColumnIndex(DBHelper.KEY_TITLE);
                int i2 = cursor.getColumnIndex(DBHelper.KEY_URLTOIMAGE);
                int i3 = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
                int i4 = cursor.getColumnIndex(DBHelper.KEY_URL);
                int i5 = cursor.getColumnIndex(DBHelper.KEY_DATE);


                listNew.add(new NoveltiesList(
                        cursor.getString(i1),
                        cursor.getString(i2),
                        cursor.getString(i3),
                        cursor.getString(i4),
                        cursor.getString(i5)));

            } while (cursor.moveToNext());
        }

    }
}
