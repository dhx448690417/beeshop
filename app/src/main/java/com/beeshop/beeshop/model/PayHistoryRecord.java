package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/27 上午9:02
 * Description : 消费历史记录
 */
public class PayHistoryRecord implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 8
         * member_id : 1
         * user_id : 5
         * money : 24.66
         * describe : 用户充值
         * created_time : 1527138775
         * status : 1
         * user_confirm_time : 0
         */

        private int id;
        private int member_id;
        private int user_id;
        private String money;
        private String describe;
        private int created_time;
        private int status;
        private int user_confirm_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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

        public int getUser_confirm_time() {
            return user_confirm_time;
        }

        public void setUser_confirm_time(int user_confirm_time) {
            this.user_confirm_time = user_confirm_time;
        }
    }
}
