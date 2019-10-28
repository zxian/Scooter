package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class CourseAddPar {
    private String num;
    private String package_name;
    private String package_price;
    private String package_state;
    private String store_id;
    private String valid_day;


    public void setSign() {
        String sign = "num=" + num +"&package_name=" + package_name +"&package_price=" + package_price
                +"&package_state=" + package_state  +"&store_id=" + store_id  +"&valid_day=" + valid_day + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPackage_state() {
        return package_state;
    }

    public void setPackage_state(String package_state) {
        this.package_state = package_state;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getValid_day() {
        return valid_day;
    }

    public void setValid_day(String valid_day) {
        this.valid_day = valid_day;
    }
}
