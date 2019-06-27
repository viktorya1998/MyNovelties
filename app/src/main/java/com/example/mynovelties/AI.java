package com.example.mynovelties;

import android.support.v4.util.Consumer;

import java.util.ArrayList;

public class AI {
    public static void getAnswer(final Consumer<ArrayList<NoveltiesRespons>> callback){

        NoveltiesAI.getNovelties(new Consumer<ArrayList<NoveltiesRespons>>() {
            @Override
            public void accept(ArrayList<NoveltiesRespons>  s) {
                ArrayList<NoveltiesRespons>  res = s;
                callback.accept(res);
            }
        });
    }
}
