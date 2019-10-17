package com.reccakun.clientappiptv.Models;

public class Category {
    private String  Title ;
    private int id ;
    private  String img ;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private  boolean isSelected;
    public Category(String title, int id, String img) {
        Title = title;
        this.id = id;
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


 }
