package com.xian.scooter.bean;

import java.util.List;

public class EventArrangeBean {
    private String id;
    private String competition_id;
    private String apply_competition_name;
    private List<EventPlanBean> list;

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

    public List<EventPlanBean> getList() {
        return list;
    }

    public void setList(List<EventPlanBean> list) {
        this.list = list;
    }
}
