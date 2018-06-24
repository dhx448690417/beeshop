package com.beeshop.beeshop.model;

import java.io.Serializable;

/**
 * Author : cooper
 * Time :  2018/6/23 下午1:32
 * Description :
 */
public class ProductInfo implements Serializable {
    private String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
