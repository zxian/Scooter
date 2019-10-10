package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoachAddActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_coach_add;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("新增教练");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

    }

    @OnClick({R.id.iv_logo, R.id.tv_info, R.id.tv_label, R.id.iv_video, R.id.tv_state, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                break;
            case R.id.tv_info:
                break;
            case R.id.tv_label:
                break;
            case R.id.iv_video:
                break;
            case R.id.tv_state:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
