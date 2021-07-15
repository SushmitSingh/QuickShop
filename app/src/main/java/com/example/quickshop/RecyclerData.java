package com.example.quickshop;

public class RecyclerData  {

    RecyclerData(){

    }

    private String title;
    private String Rate;
    private String img;

    public RecyclerData(String title, String rate, String img) {
        this.title = title;
        Rate = rate;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}