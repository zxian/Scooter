package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

import java.util.List;

public class EventPlanSavePar {
    private List<String> addJoins;
    private List<String> cancelJoins;
    private String check_time;
    private String competition_id;
    private String competition_set_id;
    private String equipment;
    private String id;
    private String name;
    private String official_time;
    private String store_id;
    private String upgrade;
    private String upgrade_id;
    private String upgrade_name;
    private String win_number;

    public void setSign(){
        String sign = "addJoins=" + addJoins + "&cancelJoins=" + cancelJoins + "&check_time=" + check_time
                + "&competition_id=" + competition_id + "&competition_set_id=" + competition_set_id
                + "&equipment=" + equipment + "&id=" + id + "&name=" + name + "&official_time=" + official_time
                + "&store_id=" + store_id + "&upgrade=" + upgrade + "&upgrade_id=" + upgrade_id +
                "&upgrade_name=" + upgrade_name + "&win_number=" + win_number+ "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }

    public List<String> getAddJoins() {
        return addJoins;
    }

    public void setAddJoins(List<String> addJoins) {
        this.addJoins = addJoins;
    }

    public List<String> getCancelJoins() {
        return cancelJoins;
    }

    public void setCancelJoins(List<String> cancelJoins) {
        this.cancelJoins = cancelJoins;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getCompetition_set_id() {
        return competition_set_id;
    }

    public void setCompetition_set_id(String competition_set_id) {
        this.competition_set_id = competition_set_id;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficial_time() {
        return official_time;
    }

    public void setOfficial_time(String official_time) {
        this.official_time = official_time;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getUpgrade_id() {
        return upgrade_id;
    }

    public void setUpgrade_id(String upgrade_id) {
        this.upgrade_id = upgrade_id;
    }

    public String getUpgrade_name() {
        return upgrade_name;
    }

    public void setUpgrade_name(String upgrade_name) {
        this.upgrade_name = upgrade_name;
    }

    public String getWin_number() {
        return win_number;
    }

    public void setWin_number(String win_number) {
        this.win_number = win_number;
    }
}
