package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class StoreAddPar {
    private String address;
    private String area;
    private String backdrop_url;
    private String business_license;
    private String card_national;
    private String card_portrait;
    private String code;
    private String create_user_id;
    private String id;
    private String id_card;
    private String level;
    private String logo;
    private String manage_name;
    private String name;
    private String remark;
    private String phone;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public void setBackdrop_url(String backdrop_url) {
        this.backdrop_url = backdrop_url;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getCard_national() {
        return card_national;
    }

    public void setCard_national(String card_national) {
        this.card_national = card_national;
    }

    public String getCard_portrait() {
        return card_portrait;
    }

    public void setCard_portrait(String card_portrait) {
        this.card_portrait = card_portrait;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getManage_name() {
        return manage_name;
    }

    public void setManage_name(String manage_name) {
        this.manage_name = manage_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setSign(){
        String sign = "address=" + address + "&area=" + area +"&backdrop_url=" + backdrop_url +
                "&business_license=" + business_license + "&card_national=" + card_national +
                "&card_portrait=" + card_portrait + "&code=" + code + "&create_user_id=" + create_user_id +
                "&id=" + id + "&id_card=" + id_card + "&level=" + level + "&logo=" + logo +
                "&manage_name=" + manage_name + "&name=" + name + "&phone=" + phone + "&remark=" + remark + "&type=" + type + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));
    }
}
