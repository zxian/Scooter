package com.xian.scooter.module.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EventCancelActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_actual_case_length)
    TextView tvActualCaseLength;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_cancel;
    }

    @Override
    protected void init() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvActualCaseLength.setText(editable == null ? "0/" + Config.MAX_LINES_200 : etContent.getText().toString().length() + "/" + Config.MAX_LINES_200);
            }
        });
    }

    @OnClick(R.id.tv_btn)
    public void onViewClicked() {
        String content = etContent.getText().toString().trim();
        if (content.length() <10) {
            ToastUtils.showToast("请填写10个字以上的问题描述！");
            return;
        }
    }
}
