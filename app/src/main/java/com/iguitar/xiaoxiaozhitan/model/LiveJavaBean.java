package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 直播平台JavaBean
 * Created by Jiang on 2017/4/16.
 */

public class LiveJavaBean implements Serializable{
    private String image;
    private String title;
    private String description;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
