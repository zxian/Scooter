package com.xian.scooter.module.stores;

import android.os.Bundle;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GivingActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_giving;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赠送课程包");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

}
