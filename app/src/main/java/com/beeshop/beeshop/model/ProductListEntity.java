package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/23 上午11:20
 * Description :
 */
public class ProductListEntity implements Serializable{


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
         * title : 承接婚宴
         * price : 2.03
         * unit : 0
         * cover : /static/images/nopic.jpg
         * list_order : 0
         * created_time : 0
         * status : 2
         */

        private int id;
        private String title;
        private String price;
        private String unit;
        private String cover;
        private int list_order;
        private int created_time;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getList_order() {
            return list_order;
        }

        public void setList_order(int list_order) {
            this.list_order = list_order;
        }

        public int getCreated_time() {
            return created_time;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
