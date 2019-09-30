package com.xian.scooter.module.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.xian.scooter.R;
import com.xian.scooter.app.AppInfo;
import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.UserInfoBean;
import com.xian.scooter.beanpar.LoginPwdPar;
import com.xian.scooter.contant.Config;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.PreferenceUtils;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

//        AppInfo appInfo = UserManager.getInstance().getAppInfo();
//        if (appInfo != null) {
//            etUser.setText(appInfo.getMobile());
//            etPwd.setText(appInfo.getPwd());
//        }
    }


    /**
     * 登陆
     */
    private void login(final String mobile, final String pwd) {

        LoginPwdPar par = new LoginPwdPar();
        par.setUsername(mobile);
        par.setPassword(pwd);
        ApiRequest.getInstance().post(HttpURL.SIGN_IN, par, new DefineCallback<UserInfoBean>() {
            @Override
            public void onMyResponse(SimpleResponse<UserInfoBean, HttpEntity> response) {
                if (response.isSucceed()) {
                    UserInfoBean bean = response.succeed();
                    if (bean != null) {
                        BaseApplication.getInstance().getKalleConfig()
                                .setHeader("at", bean.getToken());

                        AppInfo appInfo = new AppInfo(pwd, mobile);
                        UserManager.getInstance().saveAppInfo(appInfo);
                        UserManager.getInstance().saveUserToken(bean.getToken());
                        UserManager.getInstance().saveUserId(bean.getId());
                        UserManager.getInstance().putUserInfo(bean);
                        PreferenceUtils.get().putBoolean(ConfigPF.USER_IS_LOGIN_KEY, true);
                        ToastUtils.showToast("登录成功");
                        startActivity(new Intent(mActivity, MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });
    }

    @OnClick({R.id.ic_close, R.id.tv_register, R.id.tv_login, R.id.tv_forget_pwd, R.id.iv_wechat, R.id.iv_qq, R.id.iv_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_close:
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(mActivity,RegisterActivity.class));
                break;
            case R.id.tv_login:
//                String phone = etPhone.getText().toString().trim();
//                String pwd = etPwd.getText().toString().trim();
//                login(phone, pwd);
                startActivity(new Intent(mActivity,MainActivity.class));
                break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.iv_wechat:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_alipay:
                break;
        }
    }

}
