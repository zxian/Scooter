package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class EventPar {
    private String is_app;
    private String store_id;


    public void setSign(){
        String sign = "is_app=" + is_app + "&store_id=" + store_id + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }

    public String getIs_app() {
        return is_app;
    }

    public void setIs_app(String is_app) {
        this.is_app = is_app;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
