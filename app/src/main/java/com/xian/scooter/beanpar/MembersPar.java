package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class MembersPar {
    private String role;
    private String store_id;

    public void setSign(){
        String sign = "role=" + role + "&store_id=" + store_id +"&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
