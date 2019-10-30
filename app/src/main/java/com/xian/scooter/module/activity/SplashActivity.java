package com.xian.scooter.module.activity;


import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;


import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.utils.Delivery;
import com.xian.scooter.utils.PermissionsUtils;
import com.xian.scooter.utils.PreferenceUtils;
import com.xian.scooter.utils.bar.StatusBarUtil;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        Delivery.getInstance().postDelayed(() -> {
            if (PreferenceUtils.get().getBoolean(ConfigPF.USER_IS_LOGIN_KEY, false)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtra("mobile", "");
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

}
