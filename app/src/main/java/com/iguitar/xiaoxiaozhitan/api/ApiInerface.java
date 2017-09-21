package com.iguitar.xiaoxiaozhitan.api;

import com.iguitar.xiaoxiaozhitan.model.UserReturnBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jiang on 2017-09-19.
 */

public interface ApiInerface {
    @GET("Update/AppVersion.txt")
    Call<Object> getVersionReturn();

    @GET("Server/User.txt")
    Call<UserReturnBean> getLoginInfo();
}
