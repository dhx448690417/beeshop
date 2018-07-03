package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/7/3 下午2:19
 * Description : 我加入的会员entity
 */
public class JoinedVipEntity implements Serializable{

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
         * shop_id : 3
         * created_time : 1529316079
         * level : 4
         * status : 1
         * money : 30.42
         * frozen : 24.66
         * update_time : 2018-06-19 11:41:43
         * shop_title : 我的小店new
         * member_title : 三级会员
         */

        private String id;
        private String shop_id;
        private long created_time;
        private String level;
        private String status;
        private String money;
        private String frozen;
        private String update_time;
        private String shop_title;
        private String member_title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getFrozen() {
            return frozen;
        }

        public void setFrozen(String frozen) {
            this.frozen = frozen;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getMember_title() {
            return member_title;
        }

        public void setMember_title(String member_title) {
            this.member_title = member_title;
        }
    }
}
