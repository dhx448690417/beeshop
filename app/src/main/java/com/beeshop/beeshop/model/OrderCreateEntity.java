package com.beeshop.beeshop.model;

import java.io.Serializable;

/**
 * Author : cooper
 * Time :  2018/6/26 下午2:34
 * Description :
 */
public class OrderCreateEntity implements Serializable {


    /**
     * order_id : 17
     * order_no : 11201806191824562e82113895408407
     * title : 染发套餐
     * real_payment : 288
     * num : 1
     * price : 288.00
     * cover : /static/images/nopic.jpg
     */

    private String order_id;
    private String order_no;
    private String title;
    private String real_payment;
    private int num;
    private String price;
    private String cover;
    private String unit;
    private String details;
    private String remark;
    private String isvip;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReal_payment() {
        return real_payment;
    }

    public void setReal_payment(String real_payment) {
        this.real_payment = real_payment;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
