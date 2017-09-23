package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * 语音机器人JavaBean
 * Created by Jiang on 2017-09-23.
 */

public class MyConversionBean implements Serializable {
    private String askString;
    private String responseString;

    public String getAskString() {
        return askString;
    }

    public void setAskString(String askString) {
        this.askString = askString;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }
}
