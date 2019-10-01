package com.xian.scooter.module.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xian.scooter.R;
import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.UserInfoBean;
import com.xian.scooter.beanpar.LoginPwdPar;
import com.xian.scooter.beanpar.RegisterPar;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.CountDownTimerUtils;
import com.xian.scooter.utils.SignUtils;
import com.xian.scooter.utils.TimerTaskUtil;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;

    private CountDownTimerUtils countDownTimerUtils;
    private boolean isChecked = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        int resetPhoneTime = TimerTaskUtil.getInstance().getmResetPhoneTime();
        if (resetPhoneTime != 0) {
            countDownTimerUtils = new CountDownTimerUtils(tvGetCode, resetPhoneTime * 1000, 1000);
            countDownTimerUtils.start();
        }
        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked=b;
            }
        });
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
     * 注册
     *
     * @param mobile 账号
     * @param pwd    密码
     * @param code   验证码
     * @param source 来源，1、门店端，2、用户端，3:、教练
     */
    private void register(String mobile, String pwd, String code, String source) {
        RegisterPar par = new RegisterPar();
        par.setAccount(mobile);
        par.setPassword(pwd);
        par.setCode(code);
        par.setSource(source);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.REGISTER, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    finish();
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });
    }

    @OnClick({R.id.ic_close, R.id.tv_get_code, R.id.tv_register, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_close:
                finish();
                break;
            case R.id.tv_get_code:
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("手机号码不能为空！");
                    return;
                }
                getSms(phone, "1");

                countDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                countDownTimerUtils.start();
                TimerTaskUtil.getInstance().startResetPhoneTimer(60);

                break;
            case R.id.tv_register:
                phone = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String pwdConfirm = etPwdConfirm.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("手机号码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast("验证码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showToast("密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(pwdConfirm)) {
                    ToastUtils.showToast("再次输入密码不能为空！");
                    return;
                }
                if (!pwd.equals(pwdConfirm)) {
                    ToastUtils.showToast("密码与再次输入密码不一致！");
                    return;
                }
                if (!isChecked){
                    ToastUtils.showToast("请阅读并勾选同意用户协议！");
                    return;
                }
                register(phone, pwd, code, "1");
                break;
            case R.id.tv_agreement:
                break;
        }
    }
}
