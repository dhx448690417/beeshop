package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/7/3 下午3:11
 * Description :
 */
public class JoinedVipRecordEntity implements Serializable {


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
         * member_id : 1
         * user_id : 5
         * money : 24.66
         * describe : 用户消费
         * created_time : 1529379703
         * status : 1
         * user_confirm_time : 1529379703
         */

        private String id;
        private String member_id;
        private String user_id;
        private String money;
        private String describe;
        private long created_time;
        private String status;
        private String user_confirm_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
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

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_confirm_time() {
            return user_confirm_time;
        }

        public void setUser_confirm_time(String user_confirm_time) {
            this.user_confirm_time = user_confirm_time;
        }
    }
}
