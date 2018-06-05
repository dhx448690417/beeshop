package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/23 下午2:40
 * Description :
 */
public class ClientChatEntity implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 3
         * headimg :
         * customer : 3
         * initiator : 0
         * created_time : 1526874999
         * last_content : 你好
         * last_time : 2018-05-21 15:47:07
         * unread : 1
         */

        private int id;
        private String headimg;
        private int customer;
        private int initiator;
        private int created_time;
        private String last_content;
        private String last_time;
        private int unread;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public int getCustomer() {
            return customer;
        }

        public void setCustomer(int customer) {
            this.customer = customer;
        }

        public int getInitiator() {
            return initiator;
        }

        public void setInitiator(int initiator) {
            this.initiator = initiator;
        }

        public int getCreated_time() {
            return created_time;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public String getLast_content() {
            return last_content;
        }

        public void setLast_content(String last_content) {
            this.last_content = last_content;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public int getUnread() {
            return unread;
        }

        public void setUnread(int unread) {
            this.unread = unread;
        }
    }
}
