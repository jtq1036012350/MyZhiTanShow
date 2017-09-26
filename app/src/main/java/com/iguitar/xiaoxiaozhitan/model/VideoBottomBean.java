package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 视频页面底栏数据JavaBean
 * Created by Jiang on 2017-09-21.
 */

public class VideoBottomBean implements Serializable {
    //图片URL
    private String imageUrl;
    //描述
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
