package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

import java.util.List;

public class EventAddPar {
    private String address;
    private String competition_name;
    private String competition_type;
    private String end_time;
    private String finish_time;
    private String is_display;
    private String official_time;
    private String posters_url;
    private String remark;
    private String start_time;
    private String store_id;
    private String store_name;
    private List<CompetitionSavePar> subSets;

    public void setSign(){
        String sign = "address=" + address + "&competition_name=" + competition_name
                +"&competition_type=" + competition_type +"&end_time=" + end_time +"&finish_time=" + finish_time
                +"&is_display=" + is_display +"&official_time=" + official_time +"&posters_url=" + posters_url
                +"&remark=" + remark  +"&start_time=" + start_time +"&store_id=" + store_id  +"&store_name=" + store_name  + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getCompetition_type() {
        return competition_type;
    }

    public void setCompetition_type(String competition_type) {
        this.competition_type = competition_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getIs_display() {
        return is_display;
    }

    public void setIs_display(String is_display) {
        this.is_display = is_display;
    }

    public String getOfficial_time() {
        return official_time;
    }

    public void setOfficial_time(String official_time) {
        this.official_time = official_time;
    }

    public String getPosters_url() {
        return posters_url;
    }

    public void setPosters_url(String posters_url) {
        this.posters_url = posters_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<CompetitionSavePar> getSubSets() {
        return subSets;
    }

    public void setSubSets(List<CompetitionSavePar> subSets) {
        this.subSets = subSets;
    }
}
