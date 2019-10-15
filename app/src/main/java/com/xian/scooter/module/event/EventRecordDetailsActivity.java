package com.xian.scooter.module.event;

import android.os.Bundle;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventRecordDetailsActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_record_details;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("规则安排");
        titleBarView.setLeftOnClickListener(view -> finish());
    }


}
