package com.deshion.kol_demo;

import android.graphics.drawable.Drawable;

public class TagItem {
    private Drawable icon;
    private String name;
    private String kol_num;
    private String blog_num;
    private String description;

    public TagItem(Drawable icon, String name, String kol_num, String blog_num, String description) {
        this.icon = icon;
        this.name = name;
        this.kol_num = kol_num;
        this.blog_num = blog_num;
        this.description = description;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getKol_num() {
        return kol_num;
    }

    public String getBlog_num() {
        return blog_num;
    }

    public String getDescription() {
        return description;
    }
}
