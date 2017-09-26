package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * Created by Jiang on 2017-09-26.
 */

public class MessageEvent implements Serializable {
    public String message;
    public int index;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
