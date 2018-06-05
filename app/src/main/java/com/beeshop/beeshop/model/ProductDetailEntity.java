package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/23 下午2:04
 * Description :
 */
public class ProductDetailEntity implements Serializable {

    /**
     * id : 2
     * shop_id : 2
     * user_id : 0
     * title : 年夜饭套餐
     * price : 0.00
     * unit : 0
     * supply : 1
     * min_buy : 0
     * max_buy : 0
     * cover : /static/images/nopic.jpg
     * details : 现在开始接受2019年夜饭，规格丰富，欢迎预订。
     * hits : 0
     * num : 0
     * pic : [{"title":"外景照片","path":"/static/images/logo.png"},{"title":"室内照片","path":"/static/images/logo.png"}]
     */

    private int id;
    private int shop_id;
    private int user_id;
    private String title;
    private String price;
    private int unit;
    private int supply;
    private int min_buy;
    private int max_buy;
    private String cover;
    private String details;
    private int hits;
    private int num;
    private List<PicBean> pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public int getMin_buy() {
        return min_buy;
    }

    public void setMin_buy(int min_buy) {
        this.min_buy = min_buy;
    }

    public int getMax_buy() {
        return max_buy;
    }

    public void setMax_buy(int max_buy) {
        this.max_buy = max_buy;
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

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
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
}
