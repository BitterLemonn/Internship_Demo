package com.example.deshion.Utils;

import android.app.Application;

import java.util.ArrayList;

public class AppContent extends Application {
    private String sex;
    private String province;
    private String city;
    private String invite_key;
    private ArrayList<String> tag_list;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInvite_key() {
        return invite_key;
    }

    public void setInvite_key(String invite_key) {
        this.invite_key = invite_key;
    }

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }
}
