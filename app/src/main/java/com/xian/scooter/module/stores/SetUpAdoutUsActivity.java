package com.xian.scooter.module.stores;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class SetUpAdoutUsActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_adout_us;
    }

    @Override
    protected void init() {

        titleBarView.setTvTitleText("关于我们");
        titleBarView.setLeftOnClickListener(view -> finish());
        tvVersion.setText("当前版本 "+getAppVersionName(mActivity));
    }

    @OnClick({R.id.rl_use, R.id.rl_service, R.id.rl_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_use:
                break;
            case R.id.rl_service:
                break;
            case R.id.rl_update:
                ToastUtils.showToast("已经是最新版本");
                break;
        }
    }

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return appVersionName;
    }
}
