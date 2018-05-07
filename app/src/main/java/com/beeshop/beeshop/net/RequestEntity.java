package com.beeshop.beeshop.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ah on 2017/4/30.
 */

public class RequestEntity implements Serializable {
    /**
     * id : -75931528
     * jsonrpc : 2.0
     * method : loginStatus
     * params : []
     */

    private int id;
    private String jsonrpc;
    private String method;
    private List<HashMap<String, Object>> params;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HashMap<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<HashMap<String, Object>> params) {
        this.params = params;
    }
}
