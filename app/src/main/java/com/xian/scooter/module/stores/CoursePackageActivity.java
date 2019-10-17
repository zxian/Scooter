package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @OnClick({R.id.tv_add, R.id.tv_give})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                startActivity(new Intent(mActivity,NewCourseActivity.class));
                break;
            case R.id.tv_give:
                startActivity(new Intent(mActivity,CourseGivingActivity.class));
                break;
        }
    }
}
