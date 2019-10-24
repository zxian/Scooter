package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetUpFeedbackActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_out)
    TextView tvOut;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_feedback;
    }

    @Override
    protected void init() {

        titleBarView.setTvTitleText("意见反馈");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

}
