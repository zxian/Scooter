package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class EventRecordPar {
    private String competitionId;
    private String storeId;

    public void setSign(){
        String sign = "competitionId=" + competitionId + "&storeId=" + storeId + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
