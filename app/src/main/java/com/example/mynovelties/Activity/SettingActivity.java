package com.example.mynovelties.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mynovelties.City;
import com.example.mynovelties.DBHelper;
import com.example.mynovelties.R;

public class SettingActivity extends AppCompatActivity {

    Button buttonClear;
    Context context = this;

    private RadioGroup radioGroup;

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
        setContentView(R.layout.setting);

        // Инициализируем объект RadioGroup и устанавливаем обработчик нажатий
        radioGroup = findViewById(R.id.rbGroup);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_setting).setChecked(true);

        buttonClear = findViewById(R.id.buttonClean);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int check) {
                city(group, check);
            }
        });


        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(DBHelper.TABLE_CONSTANT, null, null);

                Toast toast = Toast.makeText(getApplicationContext(), "Данные удалены", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        radio_button();

    }

    protected void menu_setting() {}
    protected void menu_novelties() {
        Intent intent = new Intent(SettingActivity.this, NoveltiesActivity.class);
        startActivity(intent);
    }
    protected void menu_save() {
        Intent intent = new Intent(SettingActivity.this, SaveActivity.class);
        startActivity(intent);
    }

    protected void radio_button(){
        String c = City.city;
        switch (c){
            case "ru": {
                RadioButton rb = findViewById(R.id.rb1);
                rb.setChecked(true);
                return;
            }
            case "ua": {
                RadioButton rb = findViewById(R.id.rb2);
                rb.setChecked(true);
                return;
            }
            case "de": {
                RadioButton rb = findViewById(R.id.rb3);
                rb.setChecked(true);
                return;
            }
            case "it": {
                RadioButton rb = findViewById(R.id.rb4);
                rb.setChecked(true);
                return;
            }
            case "jp": {
                RadioButton rb = findViewById(R.id.rb5);
                rb.setChecked(true);
                return;
            }
            case "cn": {
                RadioButton rb = findViewById(R.id.rb6);
                rb.setChecked(true);
                return;
            }
        }
    }
    protected void city(RadioGroup group, int check){
        RadioButton checkedRadioButton = group.findViewById(check);
        int checkedIndex = group.indexOfChild(checkedRadioButton);

        switch (checkedIndex){
            case 0: City.city = "ru"; return;
            case 1: City.city = "ua"; return;
            case 2: City.city = "de"; return;
            case 3: City.city = "it"; return;
            case 4: City.city = "jp"; return;
            case 5: City.city = "cn"; return;
        }
    }
}
