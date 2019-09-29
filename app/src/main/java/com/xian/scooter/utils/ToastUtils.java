package com.xian.scooter.utils;

import android.widget.Toast;

import com.xian.scooter.manager.ActivityManager;

/**
 * Created by kezhangzhao on 2018/8/28.
 */
public class ToastUtils {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(ActivityManager.getTopActivityOrApp(), null, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}
