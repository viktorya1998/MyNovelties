package com.example.mynovelties.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.util.Consumer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.mynovelties.AI;
import com.example.mynovelties.NoveltiesAdapter;
import com.example.mynovelties.NoveltiesList;
import com.example.mynovelties.NoveltiesRespons;
import com.example.mynovelties.R;

import java.io.IOException;
import java.util.ArrayList;

public class NoveltiesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public Context con = this;
    public ArrayList<NoveltiesList> listNew;
    private SwipeRefreshLayout mSwipeRefresh;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return menu(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novelties_main);

        con = this;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_novelties).setChecked(true);

        listNew = new ArrayList<>();

        mSwipeRefresh = findViewById(R.id.swipe);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAI();
                mSwipeRefresh.setRefreshing(false);
            }
        });

        if (isOnline()) {
            getAI();
        } else {
            Intent intent = new Intent(con, UpdateActivity.class);
            startActivity(intent);
        }
    }

    protected void menu_setting() {
        Intent intent = new Intent(con, SettingActivity.class);
        startActivity(intent);
    }

    protected void menu_novelties() {
    }

    protected void menu_save() {
        Intent intent = new Intent(con, SaveActivity.class);
        startActivity(intent);
    }

    protected boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected void getAI() {
        AI.getAnswer(new Consumer<ArrayList<NoveltiesRespons>>() {
            @Override
            public void accept(ArrayList<NoveltiesRespons> noveltiesRespons) {
                for (int i = 0; i < noveltiesRespons.size(); i++) {

                    listNew.add(new NoveltiesList(noveltiesRespons.get(i).title
                            , noveltiesRespons.get(i).urlToImage
                            , noveltiesRespons.get(i).text
                            , noveltiesRespons.get(i).url
                            , noveltiesRespons.get(i).date));
                }

                Drawable dr2 = getResources().getDrawable(android.R.drawable.btn_star_big_on);
                Drawable dr1 = getResources().getDrawable(android.R.drawable.btn_star_big_off);

                NoveltiesAdapter noveltiesAdapter = new NoveltiesAdapter(listNew, con, dr1, dr2);
                recyclerView.setAdapter(noveltiesAdapter);
            }
        });
    }

    protected boolean menu(MenuItem item) {
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
}
