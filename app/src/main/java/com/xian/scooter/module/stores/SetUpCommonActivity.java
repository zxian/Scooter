package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUpCommonActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_restore)
    TextView tvRestore;
    @BindView(R.id.iv_restore)
    ImageView ivRestore;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.iv_reset)
    ImageView ivReset;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_common;
    }

    @Override
    protected void init() {

        titleBarView.setTvTitleText("常见问题");
        titleBarView.setLeftOnClickListener(view -> finish());
    }



    @OnClick({R.id.iv_delete, R.id.iv_restore, R.id.iv_reset, R.id.iv_notice, R.id.iv_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                break;
            case R.id.iv_restore:
                break;
            case R.id.iv_reset:
                break;
            case R.id.iv_notice:
                break;
            case R.id.iv_switch:
                break;
        }
    }
}
