package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/22 下午8:08
 * Description : 搜索店铺结果
 */
public class SearchShopEntity implements Serializable{



    private int page;
    private int size;
    private int total;
    private int rows;
    private List<Shop.ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Shop.ListBean> getList() {
        return list;
    }

    public void setList(List<Shop.ListBean> list) {
        this.list = list;
    }


}
