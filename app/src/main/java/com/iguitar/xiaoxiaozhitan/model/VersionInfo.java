package com.iguitar.xiaoxiaozhitan.model;

import java.io.Serializable;

/**
 * Created by Jiang on 2017-09-19.
 */

public class VersionInfo implements Serializable {
    String version;
    String content;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
