package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * Created by Jiang on 2017-09-19.
 */

public class UserInfo implements Serializable {
    private String name;
    private String password;
    private String token;

    private boolean isAuto;//是否自动登陆
    private boolean isRember;//是否记住密码

    private String downloadAddress;

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
    }

    public String getName() {
        return name;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public boolean isRember() {
        return isRember;
    }

    public void setRember(boolean rember) {
        isRember = rember;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
