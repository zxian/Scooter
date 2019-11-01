package com.xian.scooter.bean;

import java.io.Serializable;

public class EventPlanBean implements Serializable {
    /**
     * id : 264c512a-8625-4082-8822-9462c7ce7123
     * competition_id : ae2516f0-c085-460a-ac9d-9250828be0db
     * competition_set_id : 30319bb0-da88-4eb9-8755-77db669ac750
     * competition_set_name : 超能陆战队
     * name : 初赛
     * win_number : 2
     * official_time : 2019-10-31 18:03:00
     * check_time : 2019-10-31 18:03:00
     * upgrade : 0
     * equipment : 1
     * store_id : 84db4709-2b5d-44d3-843a-e212d57589c8
     * create_time : 2019-10-31 18:04:23
     * status : 1
     */

    private String id;
    private String competition_id;
    private String competition_set_id;
    private String competition_set_name;
    private String name;
    private int win_number;
    private String official_time;
    private String check_time;
    private int upgrade;
    private String upgrade_id;
    private String upgrade_name;
    private String equipment;
    private String store_id;
    private String create_time;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCompetition_set_name() {
        return competition_set_name;
    }

    public void setCompetition_set_name(String competition_set_name) {
        this.competition_set_name = competition_set_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWin_number() {
        return win_number;
    }

    public void setWin_number(int win_number) {
        this.win_number = win_number;
    }

    public String getOfficial_time() {
        return official_time;
    }

    public void setOfficial_time(String official_time) {
        this.official_time = official_time;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
