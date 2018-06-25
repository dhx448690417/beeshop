package com.beeshop.beeshop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/25 上午11:00
 * Description :
 */
public class AddressEntity implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 1
         * tag : 公司
         * detailed : 某某乡某村某号
         * phone : 1581133132
         * addressee : 张三
         * default : 1
         * created_time : 1526978125
         */

        private int id;
        private String tag;
        private String detailed;
        private String phone;
        private String addressee;
        @SerializedName("default")
        private int defaultX;
        private int created_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getDetailed() {
            return detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddressee() {
            return addressee;
        }

        public void setAddressee(String addressee) {
            this.addressee = addressee;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public int getCreated_time() {
            return created_time;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }
    }
}
