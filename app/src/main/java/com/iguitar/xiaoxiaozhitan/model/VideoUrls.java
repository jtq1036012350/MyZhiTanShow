package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 视频子集
 * Created by Jiang on 2017-09-26.
 */

public class VideoUrls implements Serializable {
    //描述
    private String description;
    //视频地址
    private String url;
    //封面地址
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
