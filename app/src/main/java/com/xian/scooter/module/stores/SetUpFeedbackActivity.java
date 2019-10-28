package com.xian.scooter.module.stores;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.FeedbackAddPar;
import com.xian.scooter.contant.Config;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class SetUpFeedbackActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_content_length)
    TextView tvContentLength;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_feedback;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("意见反馈");
        titleBarView.setLeftOnClickListener(view -> finish());
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvContentLength.setText(editable == null ? "0/" + Config.MAX_LINES_200 :
                        etContent.getText().toString().length() + "/" + Config.MAX_LINES_200);
            }
        });
    }


    /**
     * 添加反馈
     *
     * @param content 反馈内容
     */
    private void getCustAdd(String content) {
        FeedbackAddPar par = new FeedbackAddPar();
        par.setContent(content);
        par.setFeedback_user_id(UserManager.getInstance().getUserId());
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.FEEDBACK_ADD, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提交成功！");
                    finish();
                } else {
                    if (response.failed() != null) {
                        ToastUtils.showToast(response.failed().getMessage());
                    }
                }
            }

        });

    }


    @OnClick(R.id.tv_out)
    public void onViewClicked() {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("内容不能为空！");
            return;
        }
        getCustAdd(content);
    }
}
