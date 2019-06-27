package com.example.mynovelties;

public class NoveltiesList {
    private String title;
    private String image;
    private String text;
    private String url;
    private String date;


    public NoveltiesList(String title, String image, String text, String url, String date) {
        this.title = title;
        this.image = image;
        this.text = text;
        this.url = url;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

}

