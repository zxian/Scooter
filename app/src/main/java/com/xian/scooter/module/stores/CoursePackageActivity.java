package com.xian.scooter.module.stores;

import android.os.Bundle;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursePackageActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_course_package;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("课程包列表");
        titleBarView.setLeftOnClickListener(view -> finish());
        titleBarView.setRightText("购买记录");
    }


}
