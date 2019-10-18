package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

import java.util.List;

public class CustAddPar {

    private String account;
    private String head_mage_url;
    private String name;
    private String password;
    private String remark;
    private String role_state;
    private String store_id;
    private String store_state;
    private String user_tag;
    private List<VideosBean> videos;

    public void setSign(){
        String sign = "account=" + account + "&head_mage_url=" + head_mage_url
                +"&name=" + name +"&password=" + password +"&remark=" + remark +"&role_state=" +
                role_state+"&store_id=" + store_id+"&store_state=" + store_state+"&user_tag=" + user_tag + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHead_mage_url() {
        return head_mage_url;
    }

    public void setHead_mage_url(String head_mage_url) {
        this.head_mage_url = head_mage_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRole_state() {
        return role_state;
    }

    public void setRole_state(String role_state) {
        this.role_state = role_state;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_state() {
        return store_state;
    }

    public void setStore_state(String store_state) {
        this.store_state = store_state;
    }

    public String getUser_tag() {
        return user_tag;
    }

    public void setUser_tag(String user_tag) {
        this.user_tag = user_tag;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public static class VideosBean {
        private String video_image_url;
        private String video_url;

        public String getVideo_image_url() {
            return video_image_url;
        }

        public void setVideo_image_url(String video_image_url) {
            this.video_image_url = video_image_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }
}
