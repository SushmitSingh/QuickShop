package com.example.quickshop;

public class CartModelData {
    String img,title,rate;
    Long number;
    CartModelData(){

    }


    public CartModelData(String img, String title, String rate, Long number) {
        this.img = img;
        this.title = title;
        this.rate = rate;
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long numbers) {
        this.number = numbers;
    }
}
