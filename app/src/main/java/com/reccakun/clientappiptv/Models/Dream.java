package com.reccakun.clientappiptv.Models;

public class Dream {



    private String  Title ;
    private String Description ;
    private  String Content ;
    private int dream_ID ;
    private int cat_ID;

    public Dream(String title, String description, String content, int dream_ID, int cat_ID) {
        Title = title;
        Description = description;
        Content = content;
        this.dream_ID = dream_ID;
        this.cat_ID = cat_ID;
    }

    public int getDream_ID() {
        return dream_ID;
    }

    public void setDream_ID(int dream_ID) {
        this.dream_ID = dream_ID;
    }

    public int getCat_ID() {
        return cat_ID;
    }

    public void setCat_ID(int cat_ID) {
        this.cat_ID = cat_ID;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }



}
