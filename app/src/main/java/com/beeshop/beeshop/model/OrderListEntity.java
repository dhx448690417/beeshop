package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/23 下午6:24
 * Description : 订单列表实体类
 */
public class OrderListEntity implements Serializable {


    /**
     * count : 2
     * pagetotal : 1
     * page : 1
     * size : 10
     * cur_size : 2
     * list : [{"id":9,"order_no":"11201806141753312e68277513165794","product_id":1,"cover":"","title":"承接婚宴","phone":"15810792281","price":"2.03","num":1233,"money":"2502.99","real_payment":"0.00","payment":1,"create_time":1528970011,"status":3,"confirm_time":1528970435,"delivery_time":1528970445,"recipient_time":0,"remark":""},{"id":12,"order_no":"11201806141759332e18750521147089","product_id":4,"cover":"","title":"染发套餐","phone":"15810792281","price":"288.00","num":1233,"money":"355104.00","real_payment":"0.00","payment":1,"create_time":1528970373,"status":3,"confirm_time":1529405752,"delivery_time":1529405757,"recipient_time":0,"remark":""}]
     */

    private int count;
    private int pagetotal;
    private String page;
    private int size;
    private int cur_size;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(int pagetotal) {
        this.pagetotal = pagetotal;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCur_size() {
        return cur_size;
    }

    public void setCur_size(int cur_size) {
        this.cur_size = cur_size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 9
         * order_no : 11201806141753312e68277513165794
         * product_id : 1
         * cover :
         * title : 承接婚宴
         * phone : 15810792281
         * price : 2.03
         * num : 1233
         * money : 2502.99
         * real_payment : 0.00
         * payment : 1
         * create_time : 1528970011
         * status : 3
         * confirm_time : 1528970435
         * delivery_time : 1528970445
         * recipient_time : 0
         * remark :
         */

        private int id;
        private String order_no;
        private int product_id;
        private String cover;
        private String title;
        private String phone;
        private String price;
        private int num;
        private String money;
        private String real_payment;
        private int payment;
        private int create_time;
        private int status;
        private int confirm_time;
        private int delivery_time;
        private int recipient_time;
        private String remark;
        private String unit;
        private String isvip;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getIsvip() {
            return isvip;
        }

        public void setIsvip(String isvip) {
            this.isvip = isvip;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getReal_payment() {
            return real_payment;
        }

        public void setReal_payment(String real_payment) {
            this.real_payment = real_payment;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(int confirm_time) {
            this.confirm_time = confirm_time;
        }

        public int getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(int delivery_time) {
            this.delivery_time = delivery_time;
        }

        public int getRecipient_time() {
            return recipient_time;
        }

        public void setRecipient_time(int recipient_time) {
            this.recipient_time = recipient_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
