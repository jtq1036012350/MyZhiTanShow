package com.iguitar.xiaoxiaozhitan.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jiang on 2017-09-19.
 */

public interface ApiInerface {
    @GET("AppVersion.txt")
    Call<Object> getVersionReturn();
}
