package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/23 上午10:48
 * Description :
 */
public class ShopDetailEntity implements Serializable {


    /**
     * collection : 2
     * info : {"id":2,"user_id":2,"title":"李四的饭店","type":2,"category":1,"address":"地址","business":"家常菜，黄焖鸡等。","cover":"","contact":"15810792280","created_time":1526466001,"hits":0,"score":"100.00"}
     * pic : [{"title":"外景照片","path":"/static/images/logo.png"},{"title":"室内照片","path":"/static/images/logo.png"}]
     * product : [{"id":1,"title":"承接婚宴","cover":"/static/images/nopic.jpg","details":"承接婚宴，礼宴，聚会等。"},{"id":2,"title":"年夜饭套餐","cover":"/static/images/nopic.jpg","details":"现在开始接受2019年夜饭，规格丰富，欢迎预订。"}]
     */

    private int collection;
    private InfoBean info;
    private List<PicBean> pic;
    private List<ProductBean> product;

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 2
         * user_id : 2
         * title : 李四的饭店
         * type : 2
         * category : 1
         * address : 地址
         * business : 家常菜，黄焖鸡等。
         * cover :
         * contact : 15810792280
         * created_time : 1526466001
         * hits : 0
         * score : 100.00
         */

        private int id;
        private int user_id;
        private String title;
        private int type;
        private int category;
        private String address;
        private String business;
        private String cover;
        private String contact;
        private int created_time;
        private int hits;
        private String score;

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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public int getCreated_time() {
            return created_time;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    public static class PicBean implements Serializable{
        /**
         * title : 外景照片
         * path : /static/images/logo.png
         */

        private String title;
        private String path;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class ProductBean implements Serializable{
        /**
         * id : 1
         * title : 承接婚宴
         * cover : /static/images/nopic.jpg
         * details : 承接婚宴，礼宴，聚会等。
         */

        private int id;
        private String title;
        private String cover;
        private String details;

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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
