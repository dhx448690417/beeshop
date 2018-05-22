package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/22 下午7:44
 * Description :
 */
public class Shop implements Serializable {


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
         * user_id : 0
         * title : 蜂店
         * type : 2
         * category : 3
         * address : 孙付集
         * business : 蜂店介绍内容
         * cover :
         */
        private int id;
        private int user_id;
        private String title;
        private int type;
        private int category;
        private String address;
        private String business;
        private String cover;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
