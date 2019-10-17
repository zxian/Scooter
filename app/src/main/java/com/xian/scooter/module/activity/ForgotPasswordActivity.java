package com.xian.scooter.module.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.ForgotPasswordPar;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.CountDownTimerUtils;
import com.xian.scooter.utils.SignUtils;
import com.xian.scooter.utils.TimerTaskUtil;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repeat_password)
    EditText etRepeatPassword;

    private CountDownTimerUtils countDownTimerUtils;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forgot_passwordctivity;
    }

    @Override
    protected void init() {

        int resetPhoneTime = TimerTaskUtil.getInstance().getmResetPhoneTime();
        if (resetPhoneTime != 0){
            countDownTimerUtils = new CountDownTimerUtils(tvGetCode,1000,1000);
            countDownTimerUtils.start();
        }

        titleBarView.setTvTitleText("忘记密码");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

    /**
     * 发送短信验证码
     *
     * @param phoneNumber 手机号码
     * @param source      来源，1：注册，2:重置密码
     */
    private void getSms(String phoneNumber, String source) {

        String sign = "phoneNumber=" + phoneNumber + "&source=" + source + "&key= 0129acdeefgiknrtz";
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));

        ApiRequest.getInstance().get(HttpURL.SMS.replace("{phoneNumber}", phoneNumber)
                .replace("{source}", source), new DefineCallback<String>() {

            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("验证码已发送，请耐心等待！");
                } else {
                    ToastUtils.showToast("获取验证码失败，请稍后试试");
                    TimerTaskUtil.getInstance().stopResetPhoneTimer();
                    countDownTimerUtils.onFinish();
                }
            }
        });
    }

    /**
     *
     *
     * @param code 验证码
     * @param newPassword  新密码
     * @param hone  手机号码
     */
    private void getCust(String code,String newPassword,String hone) {

        ForgotPasswordPar par = new ForgotPasswordPar();
        par.setPhone(hone);
        par.setCode(code);
        par.setNewPassword(newPassword);
        par.setSign();

        ApiRequest.getInstance().post(HttpURL.CUST,par,new DefineCallback<String>() {

            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("修改成功");
                    finish();
                } else {
                    if (response.failed()!=null) {
                        ToastUtils.showToast(response.failed().getMessage());
                    }else {
                        ToastUtils.showToast("修改密码失败");
                    }
                }
            }
        });
    }


    @OnClick({R.id.tv_get_code, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showToast("手机号码不能为空！");
                    return;
                }

                getSms(phone,"2");

                countDownTimerUtils = new CountDownTimerUtils(tvGetCode,60000,1000);
                countDownTimerUtils.start();
                TimerTaskUtil.getInstance().startResetPhoneTimer(60);
                break;
            case R.id.tv_register:
                String phone1 = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String repeatpassword = etRepeatPassword.getText().toString().trim();


                if (TextUtils.isEmpty(phone1)){
                    ToastUtils.showToast("手机号码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showToast("验证码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    ToastUtils.showToast("密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(repeatpassword)){
                    ToastUtils.showToast("再次输入密码不能为空！");
                    return;
                }
                if (!repeatpassword.equals(password)){
                    ToastUtils.showToast("密码与再次输入密码不一致！");
                    return;
                }
                getCust(code,password,phone1);
                break;
        }


    }

}
