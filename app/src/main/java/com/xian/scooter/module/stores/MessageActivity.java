package com.xian.scooter.module.stores;


import android.os.Bundle;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_message;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("消息中心");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

    }
}
