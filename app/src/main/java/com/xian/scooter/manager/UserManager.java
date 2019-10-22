package com.xian.scooter.manager;
import android.content.Context;

import com.xian.scooter.app.AppInfo;
import com.xian.scooter.bean.UserInfoBean;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.utils.PreferenceUtils;

/**
 * 用户信息管理
 */
public class UserManager {

    private static UserManager instance;
    private UserInfoBean userInfo;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    /**
     * 保存token
     */
    public void saveUserToken(String userToken) {
        PreferenceUtils.get().putString(ConfigPF.USER_TOKEN_KEY, userToken);
    }

    /**
     * 获取token
     */
    public String getUserToken() {
        return PreferenceUtils.get().getString(ConfigPF.USER_TOKEN_KEY, "");
    }

    /**
     * 获取用户信息
     */
    public AppInfo getAppInfo() {
        return (AppInfo) PreferenceUtils.get().getObject(ConfigPF.APP_INFO_KEY);
    }

    public void saveAppInfo(AppInfo appInfo) {
        PreferenceUtils.get().putObject( ConfigPF.APP_INFO_KEY, appInfo);
    }

    /**
     * 保存userId
     */
    public void saveUserId(String userId) {
        PreferenceUtils.get().putString(ConfigPF.USER_ID_KEY, userId);
    }

    /**
     * 获取userId
     */
    public String getUserId() {
        return PreferenceUtils.get().getString(ConfigPF.USER_ID_KEY);
    }
    /**
     * 保存storeId
     */
    public void saveStoreId(String storeId) {
        PreferenceUtils.get().putString(ConfigPF.STORE_ID_KEY, storeId);
    }

    /**
     * 获取storeId
     */
    public String getStoreId() {
        return PreferenceUtils.get().getString(ConfigPF.STORE_ID_KEY);
    }

    /**
     * 退出清空用户信息
     */
    public void clearUserInfo() {
        PreferenceUtils.get().remove(ConfigPF.APP_INFO_KEY);
        PreferenceUtils.get().remove(ConfigPF.USER_ID_KEY);
        PreferenceUtils.get().remove(ConfigPF.USER_TOKEN_KEY);
        PreferenceUtils.get().remove(ConfigPF.USER_INFO_KEY);
        removeUserInfo();
    }

    /**
     * 保存用户信息
     */
    public void putUserInfo(UserInfoBean.UserBean  userInfo) {
        if (userInfo != null) {
            PreferenceUtils.get().putObject(ConfigPF.USER_INFO_KEY, userInfo);
        }
    }

    public interface UserInfoInterface {
        void requestUserInfoBean(UserInfoBean.UserBean bean);
    }



    public UserInfoBean.UserBean getUserInfo() {
        return (UserInfoBean.UserBean) PreferenceUtils.get().getObject(ConfigPF.USER_INFO_KEY);
    }

    /**
     * 移除用户信息
     */
    private void removeUserInfo() {
        PreferenceUtils.get().remove(ConfigPF.USER_INFO_KEY);
    }



//    public void putUserEnablingKey(EnablingKeyBean enablingKey) {
//        if (enablingKey != null) {
//            PreferenceUtils.get().putObject(ConfigPF.USER_ENABLING_KEY, enablingKey);
//        }
//    }

//    public interface EnablingKeyInterface {
//        void requestEnablingKeyBean(EnablingKeyBean bean);
//    }


    /**
     * 获取当前用户在APP端的菜单集合
     */
//    private void requestEnablingKey(Context context, EnablingKeyInterface enablingKeyInterface) {
//        ApiRequest.getInstance().enablingKey(new DefineCallback<EnablingKeyBean>(context) {
//            @Override
//            public void onMyResponse(SimpleResponse<EnablingKeyBean, HttpEntity> response) {
//                if (response.isSucceed()) {
//                    EnablingKeyBean enablingKeyBean = response.succeed();
//                    if (enablingKeyBean != null) {
//                        enablingKeyInterface.requestEnablingKeyBean(enablingKeyBean);
//                    }
//                }
//            }
//        });
//    }

}
