package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 视频播放的最小集合
 * Created by Jiang on 2017/5/3.
 */

public class VideoListJavaBean implements Serializable {
    //视频缩略图
    private int videoCover;
    //片段名称
    private String coverName;
    //地址
    private String url;

    public int getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(int videoCover) {
        this.videoCover = videoCover;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
