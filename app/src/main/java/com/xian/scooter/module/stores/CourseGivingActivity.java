package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseGivingActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_effective_period)
    EditText etEffectivePeriod;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_giving)
    TextView tvGiving;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_course_giving;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赠送课程包");
        titleBarView.setLeftOnClickListener(view -> finish());
    }



    @OnClick(R.id.tv_giving)
    public void onViewClicked() {

    }
}
