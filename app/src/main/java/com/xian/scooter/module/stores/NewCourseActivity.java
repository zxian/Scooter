package com.xian.scooter.module.stores;

import android.os.Bundle;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCourseActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_course;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("新增课程包");
        titleBarView.setLeftOnClickListener(view -> finish());
    }
}
