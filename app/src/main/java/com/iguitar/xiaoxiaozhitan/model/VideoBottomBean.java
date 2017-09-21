package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 视频页面底栏数据JavaBean
 * Created by Jiang on 2017-09-21.
 */

public class VideoBottomBean implements Serializable {
    //图片URL
    private int imageUrl;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
