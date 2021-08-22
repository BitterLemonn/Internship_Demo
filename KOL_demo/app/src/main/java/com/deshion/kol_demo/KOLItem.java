package com.deshion.kol_demo;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class KOLItem {
    private Drawable icon;

    private String id;
    private String introduce;
    private String fans;
    private String blog;
    private boolean isNullTag;

    private ArrayList<TextView> tag_list;

    public KOLItem(Drawable icon, String id, String introduce, String fans, String blog, @Nullable ArrayList<TextView> tag_list, boolean isNullTag) {
        this.icon = icon;
        this.id = id;
        this.introduce = introduce;
        this.fans = fans;
        this.blog = blog;
        this.tag_list = tag_list;
        this.isNullTag = isNullTag;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getFans() {
        return fans;
    }

    public String getBlog() {
        return blog;
    }

    public ArrayList<TextView> getTag_list() {
        return tag_list;
    }

    public boolean isNullTag() {
        return isNullTag;
    }
}
