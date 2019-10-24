package com.xian.scooter.module.event;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventStatePar;
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

public class EventCancelActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_actual_case_length)
    TextView tvActualCaseLength;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    private String id;

    @Override
    protected void handleIntent(Intent intent) {
        id = intent.getStringExtra("id");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_cancel;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("取消赛事");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

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

    /**
     * 更新赛事状态
     *
     * @param id  赛事id
     * @param ramark 备注
     * @param state 赛事状态：1、待审核，2、审核未通过，3、已通过，4、取消中，5、取消失败，6、已取消
     * @param user_id 操作用户ID
     */
    private void getCompetitionUpdateState(String id, String ramark, String state, String user_id) {
        EventStatePar par = new EventStatePar();
        par.setId(id);
        par.setRamark(ramark);
        par.setState(state);
        par.setUser_id(user_id);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_UPDATE_STATE, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提交成功！");
                    finish();
                }else {
                    if (response.failed()!=null){
                        ToastUtils.showToast(response.failed().getMessage());
                    }
                }
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
        String userId = UserManager.getInstance().getUserId();
        getCompetitionUpdateState(id,content,"4",userId);
    }
}
