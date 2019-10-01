package com.xian.scooter.module.activity;

import android.os.Bundle;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisteredAgreementActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_registered_agreement;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("注册协议");
        titleBarView.setLeftOnClickListener(view -> finish());
    }


}
