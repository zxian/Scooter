package com.xian.scooter.bean;

public class CoursePurchaseRecordsBean {
    /**
     * id : c58b13c9-c252-4ab5-810f-51c8843d6a89
     * valid_day : 60
     * package_name : 自由7+1课程4
     * num : 60
     * package_price : 0.01
     * store_id : 4427dded-6d2e-43e1-b572-8e21c6f923c8
     * package_state : 1
     * create_time : 2019-10-02 12:26:03
     * create_user_id : 4427dded-6d2e-43e1-b572-8e21c6f923c8
     * status : 1
     */

    private String id;
    private int valid_day;
    private String package_name;
    private int num;
    private double package_price;
    private String store_id;
    private int package_state;
    private String create_time;
    private String create_user_id;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValid_day() {
        return valid_day;
    }

    public void setValid_day(int valid_day) {
        this.valid_day = valid_day;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPackage_price() {
        return package_price;
    }

    public void setPackage_price(double package_price) {
        this.package_price = package_price;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public int getPackage_state() {
        return package_state;
    }

    public void setPackage_state(int package_state) {
        this.package_state = package_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
