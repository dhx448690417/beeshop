package com.beeshop.beeshop.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ah on 2017/4/30.
 */

public class RequestEntity implements Serializable {

    private String version ;
    private String data;
    private String rsa ;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRsa() {
        return rsa;
    }

    public void setRsa(String rsa) {
        this.rsa = rsa;
    }
}
