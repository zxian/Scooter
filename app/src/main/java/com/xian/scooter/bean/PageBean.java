package com.xian.scooter.bean;

import java.util.List;

/**
 * author : jiacan.zhou
 * time   : 2019/07/25
 * desc   :
 */

public class PageBean<S> {
        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private List<S> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }


        public List<S> getList() {
            return list;
        }

        public void setList(List<S> list) {
            this.list = list;
        }


}
