package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class EventStatePar {
    private String id;
    private String ramark;
    private String state;
    private String user_id;

    public void setSign(){
        String sign = "id=" + id + "&ramark=" + ramark
                +"&state=" + state +"&user_id=" + user_id + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRamark() {
        return ramark;
    }

    public void setRamark(String ramark) {
        this.ramark = ramark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
