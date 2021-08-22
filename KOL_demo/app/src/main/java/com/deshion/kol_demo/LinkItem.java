package com.deshion.kol_demo;

import android.graphics.drawable.Drawable;

public class LinkItem {
    private Drawable pic;
    private String title, description, price;

    public LinkItem(Drawable pic, String title, String description, String price) {
        this.pic = pic;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Drawable getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

}
