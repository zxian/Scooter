package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kzz.dialoglibraries.dialog.DialogCreate;
import com.xian.scooter.R;
import com.xian.scooter.app.AppInfo;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.ConfigPF;
import com.xian.scooter.manager.ActivityManager;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.activity.LoginActivity;
import com.xian.scooter.utils.PreferenceUtils;
import com.xian.scooter.utils.TimerTaskUtil;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class SetUpActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.switch_set)
    Switch switchSet;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.iv_feedback)
    ImageView ivFeedback;
    @BindView(R.id.tv_problem)
    TextView tvProblem;
    @BindView(R.id.iv_problem)
    ImageView ivProblem;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.iv_about)
    ImageView ivAbout;
    @BindView(R.id.tv_out)
    TextView tvOut;

    private DialogCreate mDialogCreate;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("系统设置");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.switch_set, R.id.tv_feedback, R.id.iv_feedback, R.id.tv_problem, R.id.iv_problem, R.id.tv_about, R.id.iv_about, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_set:
                break;
            case R.id.tv_feedback:
                break;
            case R.id.iv_feedback:
                break;
            case R.id.tv_problem:
                break;
            case R.id.iv_problem:
                break;
            case R.id.tv_about:
                break;
            case R.id.iv_about:
                break;
            case R.id.tv_out:
                mDialogCreate = new DialogCreate.Builder(mActivity)
                        .setAddViewId(R.layout.dialog_ok_cancel)
                        .setIsHasCloseView(false)
                        .setDialogSetDateInterface(inflaterView -> {
                            TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                            TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                            TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                            TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                            tvTitle.setVisibility(View.GONE);
                            tvMsg.setText("确定退出登录？");
                            tvCancel.setOnClickListener(v12 -> mDialogCreate.dismiss());
                            tvConfirm.setOnClickListener(v12 -> {
                                singOut();
                                mDialogCreate.dismiss();
                            });
                        })
                        .build();
                mDialogCreate.showSingle();
                break;
        }
    }

    /**
     * 注销登录
     */
    private void singOut() {

        AppInfo appInfo = UserManager.getInstance().getAppInfo();
        String mobile = "";
        if (appInfo != null) {
            mobile = appInfo.getMobile();
        }
        ToastUtils.showToast("退出成功");
        TimerTaskUtil.getInstance().stopAllTime();//停止所有验证码倒计时器
        UserManager.getInstance().clearUserInfo();
        PreferenceUtils.get().putBoolean(ConfigPF.USER_IS_LOGIN_KEY, false);
        JPushInterface.stopPush(mActivity);
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtra("mobile", mobile);
        startActivity(intent);
        ActivityManager.finishAll();
    }

}
