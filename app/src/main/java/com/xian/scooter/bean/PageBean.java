package com.xian.scooter.bean;

import java.util.List;

/**
 * author : jiacan.zhou
 * time   : 2019/07/25
 * desc   :
 */

public class PageBean<S> {
        /**
         * total : 0
         * size : 10
         * pages : 0
         * current : 1
         * records : []
         */

        private int total;
        private int size;
        private int pages;
        private int current;
        private List<S> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<S> getRecords() {
            return records;
        }

        public void setRecords(List<S> records) {
            this.records = records;
        }
}
