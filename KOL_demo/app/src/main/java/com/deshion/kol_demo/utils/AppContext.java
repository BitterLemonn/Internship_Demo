package com.deshion.kol_demo.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class AppContext extends Application {
    private Context context;
    private ArrayList<String> tag_list; //kol标签列传递
    private Drawable icon_pic;   //图片传递
    private String id_string;    //id传递
    private String introduce_string; //介绍传递
    private String fans_string;  //粉丝数传递
    private String blog_string;  //帖子数传递
    private String kol_string;   //kol数传递
    private boolean isFollowed;  //kol关注状态
    private String search_key;   //搜索关键词

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }

    public Drawable getIcon_pic() {
        return icon_pic;
    }

    public void setIcon_pic(Drawable icon_pic) {
        this.icon_pic = icon_pic;
    }

    public String getId_string() {
        return id_string;
    }

    public void setId_string(String id_string) {
        this.id_string = id_string;
    }

    public String getIntroduce_string() {
        return introduce_string;
    }

    public void setIntroduce_string(String introduce_string) {
        this.introduce_string = introduce_string;
    }

    public String getFans_string() {
        return fans_string;
    }

    public void setFans_string(String fans_string) {
        this.fans_string = fans_string;
    }

    public String getBlog_string() {
        return blog_string;
    }

    public void setBlog_string(String blog_string) {
        this.blog_string = blog_string;
    }

    public String getKol_string() {
        return kol_string;
    }

    public void setKol_string(String kol_string) {
        this.kol_string = kol_string;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }
}
