package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("系统设置");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

    @OnClick({R.id.switch_set, R.id.iv_feedback, R.id.iv_problem, R.id.iv_about, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_set:
                break;
            case R.id.iv_feedback:
                startActivity(new Intent(mActivity,SetUpFeedbackActivity.class));
                break;
            case R.id.iv_problem:
                startActivity(new Intent(mActivity,SetUpCommonActivity.class));
                break;
            case R.id.iv_about:
                startActivity(new Intent(mActivity,SetUpAdoutUsActivity.class));
                break;
            case R.id.tv_out:
                break;
        }
    }
}
