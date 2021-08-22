package com.deshion.kol_demo;

import android.graphics.drawable.Drawable;

public class KOLPostItem {

    private Drawable pic;
    private String title;
    private String describe;
    private String like_num;
    private String favorite_num;
    private String date;

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(String favorite_num) {
        this.favorite_num = favorite_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
