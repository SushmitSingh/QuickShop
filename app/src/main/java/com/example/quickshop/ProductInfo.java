package com.example.quickshop;

public class ProductInfo {
    String title;
    String Rate;
    String  img;
    Long number;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public ProductInfo() {

    }

    public ProductInfo(String title, String rate, String img, Long number) {
        this.title = title;
        Rate = rate;
        this.img = img;
        this.number = number;
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
