package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 学习平台JavaBean
 * Created by Jiang on 2017/4/16.
 */

public class StudyJavaBean implements Serializable{
    private String title;
    private String description;

    private int image;
    private String url;

    //0:代表可以跳转 1：代表不可以跳转
    private int isMore;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsMore() {
        return isMore;
    }

    public void setIsMore(int isMore) {
        this.isMore = isMore;
    }
}
