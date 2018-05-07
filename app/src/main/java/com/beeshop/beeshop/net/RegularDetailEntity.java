package com.beeshop.beeshop.net;

import java.io.Serializable;

/**
 * Author : Cooper
 * Time : 2017/9/7  11:12
 * Description :
 */

public class RegularDetailEntity implements Serializable {
    /**
     * interest : 0.96
     * principal : 100.00
     */

    private String interest;
    private String principal;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "RegularDetailEntity{" +
                "interest='" + interest + '\'' +
                ", principal='" + principal + '\'' +
                '}';
    }
}
