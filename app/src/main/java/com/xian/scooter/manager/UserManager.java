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
    public void saveUserId(int userId) {
        PreferenceUtils.get().putInt(ConfigPF.USER_ID_KEY, userId);
    }

    /**
     * 获取userId
     */
    public int getUserId() {
        return PreferenceUtils.get().getInt(ConfigPF.USER_ID_KEY, 0);
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
    public void putUserInfo(UserInfoBean userInfo) {
        if (userInfo != null) {
            PreferenceUtils.get().putObject(ConfigPF.USER_INFO_KEY, userInfo);
        }
    }

    public interface UserInfoInterface {
        void requestUserInfoBean(UserInfoBean bean);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Context context, String content,UserInfoInterface userInterface) {
        UserInfoBean userInfoBean = (UserInfoBean) PreferenceUtils.get().getObject(ConfigPF.USER_INFO_KEY);
        if (userInfoBean!=null&& content!=null){
            userInterface.requestUserInfoBean(userInfoBean);
        }else {
//            requestUserInfo(context,userInterface);
        }
    }

    public UserInfoBean getUserInfo() {
        return (UserInfoBean) PreferenceUtils.get().getObject(ConfigPF.USER_INFO_KEY);
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

    /**
     * 获取个人信息
     */
//    private void requestUserInfo(Context context,UserInfoInterface userInterface) {
//        //缓存获取数据
//        Map<String, String> map = new HashMap<>();
//        NetworkDataCacheBean networkDataCacheBean = NetworkDataCacheBean.getCacheFromDB(ConfigCache.getInstance().getUserInfoKey(), map);
//
//        ApiRequest.getInstance().getUserInfo(new DefineCallback<UserInfoEntity>(context) {
//            @Override
//            public void onMyResponse(SimpleResponse<UserInfoEntity, HttpEntity> response) {
//                if (response.isSucceed() && response.succeed() != null) {
//                    UserInfoEntity userInfoEntity = response.succeed();
//                    saveUserInfo(userInfoEntity, userInfo);
//                    userInfo = getUserInfo();
//                    userInterface.requestUserInfoBean(userInfo);
//                    String resultJson = new Gson().toJson(response.succeed());
//                    if (resultJson != null) {
//                        new NetworkDataCacheBean(ConfigCache.getInstance().getUserInfoKey(), map, resultJson,
//                                ConfigCacheTime.refresh_seconds_5, ConfigCacheTime.failure_day).save();
//                    }
//                } else {
//                    if (networkDataCacheBean != null) {
//                        try {
//                            UserInfoEntity userInfoEntity = new Gson().fromJson(networkDataCacheBean.getJsonData(), UserInfoEntity.class);
//                            if (userInfoEntity != null) {
//                                saveUserInfo(userInfoEntity, userInfo);
//                                userInfo = getUserInfo();
//                                userInterface.requestUserInfoBean(userInfo);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//    }
}
