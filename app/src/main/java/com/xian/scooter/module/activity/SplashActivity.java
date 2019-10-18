package com.xian.scooter.module.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;


import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.utils.Delivery;
import com.xian.scooter.utils.PreferenceUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {

        Delivery.getInstance().postDelayed(() -> {
//                // 判断是否是第一次开启应用
//                boolean isFirstOpen = PreferenceUtils.get().getBoolean(ConfigPF.USER_IS_FIRST_START_APP_KEY, false);
//                // 如果是第一次启动，则先进入功能引导页
//                if (!isFirstOpen) {
//                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }



            //   ElevatorApplication.getInstance().initKalle();
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
//        updateApk();
//        startActivity(new Intent(this, LoginActivity.class));
//        startActivity(new Intent(this, GuideActivity.class));
//        finish();
    }

//    private boolean isLogin() {
//        String token = UserManager.getInstance().getAccessToken();
//        String userId = UserManager.getInstance().getUserId();
//        return !(TextUtils.empty(token) || TextUtils.empty(userId));
//    }

}
