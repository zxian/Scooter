package com.xian.scooter.jpush.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.xian.scooter.R;
import com.xian.scooter.module.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * author : jiacan.zhou
 * time   : 2019/04/08
 * desc   : 极光推送跳转activity工具
 */

public class JpushSkipUtils {

    private static JpushSkipUtils instance;
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
    private int requestCode;//如果requestCode的值是固定的，那么每次点击通知后，获取到的参数永远是最后的那个。所以requestCode要保存不一样

    private JpushSkipUtils() {
    }

    public static JpushSkipUtils getInstance() {
        if (instance == null) {
            synchronized (JpushSkipUtils.class) {
                if (instance == null) {
                    instance = new JpushSkipUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 跳转activity
     *
     * @param mContext  上下文
     * @param type      类型：1.巡检任务详情
     * @param id        id
     * @param isJpush   是否极光推送通知
     * @param bundle    极光推送的bundle 没有就填null
     */
    public void skipActivity(Context mContext, String type,int id, boolean isJpush, Bundle bundle) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "1": //巡检任务详情
//                    intent.setClass(mContext, TaskDetailActivity.class);
//                    intent.putExtra("id", id);
//                    myStartActivity(mContext, intent, isJpush, bundle);
                    break;
            }
        } else {
            intent.setClass(mContext, MainActivity.class);
//            intent.putExtra(AppConstants.TAB_SELECTION_MAIN, 3);
            myStartActivity(mContext, intent, isJpush, bundle);
        }

    }

    private void myStartActivity(Context mContext, Intent intent, boolean isJpush, Bundle bundle) {
        if (!isJpush) {
            mContext.startActivity(intent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //8.0以上弹出通知状态栏
                showNotify80(mContext, intent, bundle);
            } else {
                showNotify(mContext, intent, bundle);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotify80(Context context, Intent intent, Bundle bundle) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PUSH_CHANNEL_ID);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE))//设置通知栏标题
                .setContentText(bundle.getString(JPushInterface.EXTRA_ALERT))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent) //设置通知栏点击意图
                .setChannelId(PUSH_CHANNEL_ID);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        if (notificationManager != null) {
            notificationManager.notify(bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID), notification);
        }
    }

    private void showNotify(Context context, Intent intent, Bundle bundle) {
        //弹出通知栏 8.0以下系统弹出方式
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //创建notification
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE))
                .setContentText(bundle.getString(JPushInterface.EXTRA_ALERT));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        if (notificationManager != null)
            notificationManager.notify(bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID), builder.build());
    }
}
