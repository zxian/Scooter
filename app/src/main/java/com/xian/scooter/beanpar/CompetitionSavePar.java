package com.xian.scooter.beanpar;

import android.os.Parcel;
import android.os.Parcelable;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

import java.io.Serializable;

public class CompetitionSavePar implements Parcelable {
    private String apply_competition_name;
    private String big_age;
    private String competition_id;
    private String limit;
    private String money;
    private String remark;
    private String small_age;

    public CompetitionSavePar(){

    }

    protected CompetitionSavePar(Parcel in) {
        apply_competition_name = in.readString();
        big_age = in.readString();
        competition_id = in.readString();
        limit = in.readString();
        money = in.readString();
        remark = in.readString();
        small_age = in.readString();
    }

    public static final Creator<CompetitionSavePar> CREATOR = new Creator<CompetitionSavePar>() {
        @Override
        public CompetitionSavePar createFromParcel(Parcel in) {
            return new CompetitionSavePar(in);
        }

        @Override
        public CompetitionSavePar[] newArray(int size) {
            return new CompetitionSavePar[size];
        }
    };

    public void setSign(){
        String sign = "apply_competition_name=" + apply_competition_name + "&big_age=" + big_age
                +"&competition_id=" + competition_id +"&limit=" + limit +"&money=" + money +"&remark=" + remark +"&small_age=" + small_age + "&key="+ Config.KEY;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSmall_age() {
        return small_age;
    }

    public void setSmall_age(String small_age) {
        this.small_age = small_age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(apply_competition_name);
        parcel.writeString(big_age);
        parcel.writeString(competition_id);
        parcel.writeString(limit);
        parcel.writeString(money);
        parcel.writeString(remark);
        parcel.writeString(small_age);
    }
}
