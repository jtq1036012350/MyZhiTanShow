package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 视频页面底栏数据JavaBean
 * Created by Jiang on 2017-09-21.
 */

public class VideoBottomBean implements Serializable {
    //图片URL
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
