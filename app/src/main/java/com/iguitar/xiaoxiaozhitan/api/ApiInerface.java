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
    @GET("Server/VoiceData.txt")
    Call<List<MyConversionBean>> getVoiceReturn();

    @GET("Update/AppVersion.txt")
    Call<Object> getVersionReturn();

    @GET("Server/User.txt")
    Call<UserReturnBean> getLoginInfo();

    @GET("Server/Study.txt")
    Call<List<StudyJavaBean>> getStudyInfo();

    @GET("Server/VideoTop.txt")
    Call<List<MainListJavaBean>> getVideoTopInfo();

    @GET("Server/VideoBottom.txt")
    Call<List<VideoBottomBean>> getVideoBottomInfo();
}
