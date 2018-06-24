package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/16 下午4:21
 * Description :
 */
public class PicListEntity implements Serializable {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {

        private int id;
        private String title;
        private String path;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
