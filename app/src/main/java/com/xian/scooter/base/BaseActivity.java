package com.xian.scooter.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xian.scooter.R;
import com.xian.scooter.manager.ActivityManager;
import com.xian.scooter.utils.bar.StatusBarUtil;
import com.blankj.utilcode.util.LogUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        ActivityManager.addActivity(this);
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
        mActivity = this;

        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);

        //沉浸式代码配置
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }

        //这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
        //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
        setStatusBar();

        init();
    }

    /**
     * 处理传递过来的Intent
     *
     * @param intent intent
     */
    protected void handleIntent(Intent intent) {
    }

    /**
     * 布局layout id
     */
    protected abstract int getLayoutResourceId();

    /**
     * 初始化操作
     */
    protected abstract void init();

    /**
     * 设置竖屏
     */
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//锁定竖屏
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setStatusBarColor(this, R.color.white);//状态栏背景颜色，默认白色
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        ActivityManager.removeActivityTmp(this);
        ActivityManager.removeActivityTmp2(this);
        LogUtils.e("onDestroy:", this.getClass().getName());
    }

}
