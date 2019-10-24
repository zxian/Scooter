package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class EventPlanPagePar {
    private String competition_id;

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }
    public void setSign(){
        String sign = "competition_id=" + competition_id +"&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
}
