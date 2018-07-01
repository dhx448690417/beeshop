package com.beeshop.beeshop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author : cooper
 * Time :  2018/6/27 下午5:31
 * Description : 微信支付返回entity
 */
public class WeiXinPayEntity implements Serializable {

    /**
     * appid : wx234f5277333171ba
     * noncestr : ytRtjmYBYJhT1ujWX3IA
     * package : Sign=WXPay
     * partnerid : 1507003781
     * prepayid : wx21175204065200805e8e0d0d1578730563
     * timestamp : 1529574724
     * sign : 6F719AC2D2E13BFE1799259514CB7B4F
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
