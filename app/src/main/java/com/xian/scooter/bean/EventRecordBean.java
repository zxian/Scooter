package com.xian.scooter.bean;

import java.io.Serializable;

public class EventRecordBean implements Serializable {

    /**
     * id : 514e2683-058f-4bb5-bb76-5819c3459d0f
     * store_id : 34012e03-cccd-405d-8fd8-6c98e4872a89
     * store_name : 沙县门店11
     * competition_id : c1b7a979-3b22-40e9-a0a4-804800eb3e43
     * competition_name : 排舞
     * posters_url : http://zhouzhengxiang.oss-cn-shenzhen.aliyuncs.com/data/2019/10/12/4221570870283447.jpg?Expires=1886230283&OSSAccessKeyId=LTAIqrVaCBf5RxNd&Signature=FBM5Yd4gk2SBbrH4p81QJZ60XuI%3D
     * address : 黑寡妇
     * official_time : 2019-10-18 17:51:00
     * finish_time : 2019-10-12 16:52:00
     * start_time : 2019-11-01 16:52:00
     * end_time : 2019-10-31 16:52:00
     * competition_set_id : cc97020b-5b96-40b4-8d51-09e79e09850f
     * competition_set_name : 法国人
     * cust_user_id : 7e624bde-a974-45e0-8db2-49155becbdc6
     * cust_user_phone : 18998805990
     * child_id : 033d8391-39dd-433c-b404-9ee9c6729452
     * child_face_url : http://img4.imgtn.bdimg.com/it/u=262424088,4280707850&fm=26&gp=0.jpg
     * child_name : 111
     * child_sex : 1
     * child_age : 3
     * join_state : 0
     * pay_no : MD514E2N20191015105510690
     * pay_money : 0.01
     * pay_type : 支付宝
     * pay_json : alipay_sdk=alipay-sdk-java-3.4.49.ALL&app_id=2016080901722452&biz_content=%7B%22body%22%3A%22%E8%B5%9B%E4%BA%8B%E6%8A%A5%E5%90%8D%E8%AE%A2%E5%8D%95%EF%BC%9AMD514E2N20191015105510690%2C%E5%8F%82%E8%B5%9B%E4%BA%BA%EF%BC%9A111%22%2C%22out_trade_no%22%3A%22MD514E2N20191015105510690%22%2C%22product_code%22%3A%22SSDD%22%2C%22subject%22%3A%22%E8%B5%9B%E4%BA%8B%E6%8A%A5%E5%90%8D%E8%AE%A2%E5%8D%95%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwill.worthful.top%2Fshopping%2FcompetitionJoin%2FaliPay%2Fnotify%2F514e2683-058f-4bb5-bb76-5819c3459d0f&sign=VQAJnExcDJUA0SM%2Ft5V8j49o7sMdqWgdDHQN5ycIJpm%2FiKMyKgIEuOu%2BkePlJSRhoA8ejp8C5nL%2BKdgFLsco%2FDoOns3HpqeaPveVOIHlz82MeEpvWW0cksEQSwmppskps0aZbytZMy%2B6bTK4r59O%2BgjcDbd4YbQyEsIZ3tJ3e4uxqQ8lUm0thoi0TjPJpAwu5xHlDmom96B7Z2w9OcqltOoMbnnLPciG1akGZkZWqdIMvB6j%2BCHAaPxEoEWWGNj9%2FW1XaCLu%2FzZmTxhZE2NdcCPy573DKozJEUEmtKUcDWWPghaJXKTXdPkhJ28NCZJCzz3mVlKGESvf3qoLPGLSug%3D%3D&sign_type=RSA2&timestamp=2019-10-15+10%3A55%3A10&version=1.0
     * create_time : 2019-10-15 10:55:10
     * create_user_id : 7e624bde-a974-45e0-8db2-49155becbdc6
     * status : 1
     * remark : 好的好的好的好的
     * out_order_no : 2088022366160732
     * pay_success_time : 2019-10-12 12:22:14
     * pay_refund_time : 2019-10-13 10:19:56
     * update_time : 2019-10-13 10:19:56
     * competition_remark : 面向年满十周岁，会骑车，热爱骑车的青少年，欢迎来参赛
     */

    private String id;
    private String store_id;
    private String store_name;
    private String competition_id;
    private String competition_name;
    private String posters_url;
    private String address;
    private String official_time;
    private String finish_time;
    private String start_time;
    private String end_time;
    private String competition_set_id;
    private String competition_set_name;
    private String cust_user_id;
    private String cust_user_phone;
    private String child_id;
    private String child_face_url;
    private String child_name;
    private int child_sex;
    private int child_age;
    private int join_state;
    private String pay_no;
    private double pay_money;
    private String pay_type;
    private String pay_json;
    private String create_time;
    private String create_user_id;
    private int status;
    private String remark;
    private String out_order_no;
    private String pay_success_time;
    private String pay_refund_time;
    private String update_time;
    private String competition_remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getPosters_url() {
        return posters_url;
    }

    public void setPosters_url(String posters_url) {
        this.posters_url = posters_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficial_time() {
        return official_time;
    }

    public void setOfficial_time(String official_time) {
        this.official_time = official_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCompetition_set_id() {
        return competition_set_id;
    }

    public void setCompetition_set_id(String competition_set_id) {
        this.competition_set_id = competition_set_id;
    }

    public String getCompetition_set_name() {
        return competition_set_name;
    }

    public void setCompetition_set_name(String competition_set_name) {
        this.competition_set_name = competition_set_name;
    }

    public String getCust_user_id() {
        return cust_user_id;
    }

    public void setCust_user_id(String cust_user_id) {
        this.cust_user_id = cust_user_id;
    }

    public String getCust_user_phone() {
        return cust_user_phone;
    }

    public void setCust_user_phone(String cust_user_phone) {
        this.cust_user_phone = cust_user_phone;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getChild_face_url() {
        return child_face_url;
    }

    public void setChild_face_url(String child_face_url) {
        this.child_face_url = child_face_url;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public int getChild_sex() {
        return child_sex;
    }

    public void setChild_sex(int child_sex) {
        this.child_sex = child_sex;
    }

    public int getChild_age() {
        return child_age;
    }

    public void setChild_age(int child_age) {
        this.child_age = child_age;
    }

    public int getJoin_state() {
        return join_state;
    }

    public void setJoin_state(int join_state) {
        this.join_state = join_state;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_json() {
        return pay_json;
    }

    public void setPay_json(String pay_json) {
        this.pay_json = pay_json;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getPay_success_time() {
        return pay_success_time;
    }

    public void setPay_success_time(String pay_success_time) {
        this.pay_success_time = pay_success_time;
    }

    public String getPay_refund_time() {
        return pay_refund_time;
    }

    public void setPay_refund_time(String pay_refund_time) {
        this.pay_refund_time = pay_refund_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCompetition_remark() {
        return competition_remark;
    }

    public void setCompetition_remark(String competition_remark) {
        this.competition_remark = competition_remark;
    }
}