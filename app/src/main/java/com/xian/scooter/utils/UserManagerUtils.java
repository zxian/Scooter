package com.xian.scooter.utils;


/**
 * 用户信息管理
 */
public class UserManagerUtils {

    private static UserManagerUtils instance;

    private UserManagerUtils() {
    }

    public static UserManagerUtils getInstance() {
        if (instance == null) {
            synchronized (UserManagerUtils.class) {
                if (instance == null) {
                    instance = new UserManagerUtils();
                }
            }
        }
        return instance;
    }


}
