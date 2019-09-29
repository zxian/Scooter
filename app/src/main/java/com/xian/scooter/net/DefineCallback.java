package com.xian.scooter.net;

import android.content.Intent;

import com.xian.scooter.app.AppInfo;
import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.ConfigNetwork;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.manager.ActivityManager;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.activity.LoginActivity;
import com.xian.scooter.utils.PreferenceUtils;
import com.yanzhenjie.kalle.exception.ConnectTimeoutError;
import com.yanzhenjie.kalle.exception.HostError;
import com.yanzhenjie.kalle.exception.NetworkError;
import com.yanzhenjie.kalle.exception.ParseError;
import com.yanzhenjie.kalle.exception.ReadTimeoutError;
import com.yanzhenjie.kalle.exception.URLError;
import com.yanzhenjie.kalle.exception.WriteException;
import com.yanzhenjie.kalle.simple.Callback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.jpush.android.api.JPushInterface;

public abstract class DefineCallback<S> extends Callback<S, HttpEntity> {

    public DefineCallback() {
    }

    @Override
    public void onStart() {
        // 请求开始
    }

    @Override
    public void onEnd() {
        // 请求结束
    }

    @Override
    public Type getSucceed() {
        // 通过反射获取业务成功的数据类型。
        Type superClass = getClass().getGenericSuperclass();
        if (superClass != null) {
            return ((ParameterizedType) superClass).getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    @Override
    public Type getFailed() {
        // 返回失败时的数据类型，String。
        return HttpEntity.class;
    }

    @Override
    public void onException(Exception e) {
        // 发生异常了，回调到onResonse()中。
        HttpEntity httpEntity = new HttpEntity();
        if (e instanceof NetworkError) {
            httpEntity.setMsg("网络未连接，请检查网络设置");
            httpEntity.setCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof URLError) {
            httpEntity.setMsg("Url格式错误");
        } else if (e instanceof HostError) {
            httpEntity.setMsg("没有找到Url指定服务器");
        } else if (e instanceof ConnectTimeoutError) {
            httpEntity.setMsg("连接服务器超时，请重试");
            httpEntity.setCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof WriteException) {
            httpEntity.setMsg("发送数据错误，请检查网络");
            httpEntity.setCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof ReadTimeoutError) {
            httpEntity.setMsg("读取服务器数据超时，请检查网络");
            httpEntity.setCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof ParseError) {
            httpEntity.setMsg("解析数据时发生异常");
        } else if (e instanceof ExecutionException) {
            httpEntity.setMsg("网络未连接，请检查网络设置");
            httpEntity.setCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else {
            httpEntity.setMsg("发生未知异常，请稍后重试");
        }
        SimpleResponse<S, HttpEntity> response = SimpleResponse.<S, HttpEntity>newBuilder()
                .failed(httpEntity)
                .build();
        onResponse(response);
    }

    @Override
    public void onCancel() {
        // 请求被取消了，如果开发者需要，请自行处理。
    }
    /**
     * 统一处理网络请求后的回调
     *
     * @param response response
     */
    public abstract void onMyResponse(SimpleResponse<S, HttpEntity> response);

    @Override
    public void onResponse(SimpleResponse<S, HttpEntity> response) {

        if (response != null) {
            if (!response.isSucceed() && response.failed() != null) {
                switch (response.failed().getCode()) {
//                    case ConfigNetwork.TOKEN_LOSE_EFFICACY://token失效
                    case ConfigNetwork.TOKEN_INVALID://无效token
//                    case ConfigNetwork.TOKEN_PAR_LOSE://token参数缺失
                        try {
                            List<BaseActivity> activityList = ActivityManager.getActivityList();
                            for (int i = 0; i < activityList.size(); i++) {
                                if (!activityList.get(i).getClass().getSimpleName()
                                        .equals("LoginActivity")) {//判断是否已经有同名LoginActivity
                                    AppInfo appInfo = UserManager.getInstance().getAppInfo();
                                    String mobile = "";
                                    if (appInfo != null) {
                                        mobile = appInfo.getMobile();
                                    }
                                    UserManager.getInstance().clearUserInfo();//清除用户信息
                                    JPushInterface.stopPush(BaseApplication.getInstance());//停止极光推送
                                    PreferenceUtils.get().putBoolean(ConfigPF.USER_IS_LOGIN_KEY, false);//清除登录状态
                                    ActivityManager.finishAll();//关闭所有activity
                                    //跳转登录页面finishAll
                                    Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
                                    intent.putExtra("mobile", mobile);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    BaseApplication.getInstance().startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        onMyResponse(response);
                        break;
                }
            } else {
                onMyResponse(response);
            }
        }

    }
}