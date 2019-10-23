package com.xian.scooter.bean;

public class EventTypeBean {
    /**
     * id : 1d1634ae-8630-4cee-9505-64fb312a3ecc
     * competition_id : e70b0199-2e0c-438b-b36e-8765491461f3
     * apply_competition_name : 少年对2
     * limit : 12
     * big_age : 18
     * small_age : 12
     * money : 100.0
     * create_time : 2019-10-23 10:34:38
     * status : 1
     */

    private String id;
    private String competition_id;
    private String apply_competition_name;
    private int limit;
    private int big_age;
    private int small_age;
    private double money;
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

    public String getApply_competition_name() {
        return apply_competition_name;
    }

    public void setApply_competition_name(String apply_competition_name) {
        this.apply_competition_name = apply_competition_name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getBig_age() {
        return big_age;
    }

    public void setBig_age(int big_age) {
        this.big_age = big_age;
    }

    public int getSmall_age() {
        return small_age;
    }

    public void setSmall_age(int small_age) {
        this.small_age = small_age;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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
