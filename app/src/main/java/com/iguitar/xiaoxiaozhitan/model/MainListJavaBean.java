package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 主播放列表类
 * Created by Jiang on 2017/5/3.
 */

public class MainListJavaBean implements Serializable {
    private String coverName;

    private ArrayList<PlayListMainJavaBean> mainJavaBeanArrayList ;

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public ArrayList<PlayListMainJavaBean> getMainJavaBeanArrayList() {
        return mainJavaBeanArrayList;
    }

    public void setMainJavaBeanArrayList(ArrayList<PlayListMainJavaBean> mainJavaBeanArrayList) {
        this.mainJavaBeanArrayList = mainJavaBeanArrayList;
    }
}
