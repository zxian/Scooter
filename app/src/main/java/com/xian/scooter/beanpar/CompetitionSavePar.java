package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

import java.io.Serializable;

public class CompetitionSavePar implements Serializable {
    private String apply_competition_name;
    private String big_age;
    private String competition_id;
    private String limit;
    private String money;
    private String small_age;

    public void setSign(){
        String sign = "apply_competition_name=" + apply_competition_name + "&big_age=" + big_age
                +"&competition_id=" + competition_id +"&limit=" + limit +"&money=" + money +"&small_age=" + small_age + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
    public String getApply_competition_name() {
        return apply_competition_name;
    }

    public void setApply_competition_name(String apply_competition_name) {
        this.apply_competition_name = apply_competition_name;
    }

    public String getBig_age() {
        return big_age;
    }

    public void setBig_age(String big_age) {
        this.big_age = big_age;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSmall_age() {
        return small_age;
    }

    public void setSmall_age(String small_age) {
        this.small_age = small_age;
    }
}
