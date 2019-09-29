package com.xian.scooter.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xian.scooter.bean.NotificationExtraBean;
import com.xian.scooter.jpush.utils.JpushSkipUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

                if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                    String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                    Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                    //send the Registration Id to your server...

                } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                    Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    dealMessage(context, bundle);
                    String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    if (!TextUtils.isEmpty(json)) {

                    }

                } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                    int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    new Handler().postDelayed(() -> {
//                        EventBus.getDefault().post(new NewsEvent(true));//通知主页的消息列表更新
                    }, 1000);
                    dealMessage(context, bundle);
                    Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                    Log.e(TAG, "[MyReceiver] 用户点击打开了通知");

                    String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    Log.e(TAG, "[MyReceiver] 用户点击打开了通知"+json);
                    NotificationExtraBean bean = JSON.parseObject(json, NotificationExtraBean.class);
                    if (bean != null) {
                        JpushSkipUtils.getInstance().skipActivity(context, "1",
                                bean.getBussiness(), false, null);
                    }else {
                        JpushSkipUtils.getInstance().skipActivity(context, "",0, true, bundle);
                    }


                } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

                    Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//                    String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                    NotificationExtraBean bean = JSON.parseObject(json, NotificationExtraBean.class);

                } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                    boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                    Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
                } else {
                    Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "[MyReceiver] Unhandled intent - " + e.getMessage());
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            switch (key) {
                case JPushInterface.EXTRA_NOTIFICATION_ID:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
                    break;
                case JPushInterface.EXTRA_CONNECTION_CHANGE:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
                    break;
                case JPushInterface.EXTRA_EXTRA:
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Log.e(TAG, "This message has no Extra data");
                        continue;
                    }

                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next();
                            sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }

                    break;
                default:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 处理通知跳转
     *
     * @param context 上下文
     * @param bundle  极光内容
     */
    private void dealMessage(Context context, Bundle bundle) {
        String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (!TextUtils.isEmpty(json)) {
            NotificationExtraBean bean = JSON.parseObject(json, NotificationExtraBean.class);
            if (bean != null) {
                JpushSkipUtils.getInstance().skipActivity(context, "1",
                        bean.getBussiness(), true, bundle);
            }else {
                JpushSkipUtils.getInstance().skipActivity(context, "",0, true, bundle);
            }
        }
    }

}
