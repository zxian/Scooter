package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

class SetUpAdoutUsActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_adout_us;
    }

    @Override
    protected void init() {

        titleBarView.setTvTitleText("关于我们");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

}
