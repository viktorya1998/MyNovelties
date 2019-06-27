package com.example.mynovelties;

import android.support.v4.util.Consumer;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NoveltiesAI {

    public static class ApiResult{
        @SerializedName("articles")
        public  ArrayList<NoveltiesRespons> articles;
    }

    public  interface Novelties_Service{
        @GET("v2/top-headlines?apiKey=c31e0919922d46008666b4068aed266a")
        Call<ApiResult> getNovelties(@Query("country") String city);
    }

    public static void getNovelties( final Consumer<ArrayList<NoveltiesRespons>> callback){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<ApiResult> call = retrofit
                .create(Novelties_Service.class)
                .getNovelties(City.city);

        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();
                callback.accept(result.articles);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.w("Ошибка - NoveltiesAI", t.getMessage());
            }
        });

    }

}