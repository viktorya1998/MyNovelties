package com.example.mynovelties;

import com.google.gson.annotations.SerializedName;


public class NoveltiesRespons {
    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String text;

    @SerializedName("url")
    public String url;

    @SerializedName("urlToImage")
    public String urlToImage;

    @SerializedName("publishedAt")
    public String date;
}
