package com.xian.scooter.module.event;

import android.content.Intent;
import android.widget.EditText;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class AddInfoActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_content)
    EditText etContent;

    private String content;
    private String name;

    @Override
    protected void handleIntent(Intent intent) {
        name = intent.getStringExtra("name");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_add_info;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText(name);
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        titleBarView.setRightText("完成");
        titleBarView.setRightOnClickListener(view -> {
            content = etContent.getText().toString().trim();
            Intent intent= new Intent();
            intent.putExtra("content",content);
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}
