package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 主的播放列表子类
 * Created by Jiang on 2017/5/3.
 */

public class PlayListMainJavaBean implements Serializable {
    //播放封面
    private String videoCover;
    //专辑名称
    private String coverName;
    //视频集合
    private ArrayList<VideoListJavaBean> videoListJavaBeen;

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public ArrayList<VideoListJavaBean> getVideoListJavaBeen() {
        return videoListJavaBeen;
    }

    public void setVideoListJavaBeen(ArrayList<VideoListJavaBean> videoListJavaBeen) {
        this.videoListJavaBeen = videoListJavaBeen;
    }
}
