package com.iguitar.xiaoxiaozhitan.api;

import com.iguitar.xiaoxiaozhitan.model.MainListJavaBean;
import com.iguitar.xiaoxiaozhitan.model.MyConversionBean;
import com.iguitar.xiaoxiaozhitan.model.StudyJavaBean;
import com.iguitar.xiaoxiaozhitan.model.UserReturnBean;
import com.iguitar.xiaoxiaozhitan.model.VideoBottomBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 网络访问url类
 * Created by Jiang on 2017-09-19.
 */

public interface ApiInerface {
    //语音模块
    @GET("Server/VoiceData.txt")
    Call<List<MyConversionBean>> getVoiceReturn();

    //版本信息and测试网络
    @GET("Update/AppVersion.txt")
    Call<Object> getVersionReturn();

    //用户信息
    @GET("Server/User.txt")
    Call<UserReturnBean> getLoginInfo();

    //学习中心
    @GET("Server/Study.txt")
    Call<List<StudyJavaBean>> getStudyInfo();

    //视频页顶部
    @GET("Server/VideoTop.txt")
    Call<List<MainListJavaBean>> getVideoTopInfo();

    //视频页底部
    @GET("Server/VideoBottom.txt")
    Call<List<VideoBottomBean>> getVideoBottomInfo();
}
