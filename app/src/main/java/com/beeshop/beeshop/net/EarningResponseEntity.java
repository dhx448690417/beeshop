package com.beeshop.beeshop.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ah on 2017/5/2.
 */

public class EarningResponseEntity implements Serializable {

    private String basicRate;
    private String couponRate;
    private String historyEarning;
    private String todayEarning;
    private String todayExpectEarning;
    private String secondEarning;
    private int loseSecond;
    private String availableAmount;
    private String totalAmount;
    private String mineTotalAmount;
    private List<String> loseTimes;

    public String getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public String getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(String couponRate) {
        this.couponRate = couponRate;
    }

    public String getHistoryEarning() {
        return historyEarning;
    }

    public void setHistoryEarning(String historyEarning) {
        this.historyEarning = historyEarning;
    }

    public String getTodayEarning() {
        return todayEarning;
    }

    public void setTodayEarning(String todayEarning) {
        this.todayEarning = todayEarning;
    }

    public String getTodayExpectEarning() {
        return todayExpectEarning;
    }

    public void setTodayExpectEarning(String todayExpectEarning) {
        this.todayExpectEarning = todayExpectEarning;
    }

    public String getSecondEarning() {
        return secondEarning;
    }

    public void setSecondEarning(String secondEarning) {
        this.secondEarning = secondEarning;
    }

    public int getLoseSecond() {
        return loseSecond;
    }

    public void setLoseSecond(int loseSecond) {
        this.loseSecond = loseSecond;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMineTotalAmount() {
        return mineTotalAmount;
    }

    public void setMineTotalAmount(String mineTotalAmount) {
        this.mineTotalAmount = mineTotalAmount;
    }

    public List<String> getLoseTimes() {
        return loseTimes;
    }

    public void setLoseTimes(List<String> loseTimes) {
        this.loseTimes = loseTimes;
    }

    @Override
    public String toString() {
        return "EarningResponseEntity{" +
                "basicRate='" + basicRate + '\'' +
                ", couponRate='" + couponRate + '\'' +
                ", historyEarning='" + historyEarning + '\'' +
                ", todayEarning='" + todayEarning + '\'' +
                ", todayExpectEarning='" + todayExpectEarning + '\'' +
                ", secondEarning='" + secondEarning + '\'' +
                ", loseSecond=" + loseSecond +
                ", availableAmount='" + availableAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", mineTotalAmount='" + mineTotalAmount + '\'' +
                ", loseTimes=" + loseTimes +
                '}';
    }
}
