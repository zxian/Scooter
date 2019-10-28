package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.SetUpCommonBean;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetUpCommonDetailsActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private String id;

    @Override
    protected void handleIntent(Intent intent) {
        id = intent.getStringExtra("id");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_set_up_common_details;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("问题解答");
        titleBarView.setLeftOnClickListener(view -> finish());
        getIssueByid(id);
    }

    /**
     * 按照ID获取详情
     */
    private void getIssueByid(String id) {

        ApiRequest.getInstance().get(HttpURL.ISSUE_BYID.replace("{id}", id), new DefineCallback<SetUpCommonBean>() {
            @Override
            public void onMyResponse(SimpleResponse<SetUpCommonBean, HttpEntity> response) {
                if (response.isSucceed()) {
                    SetUpCommonBean setUpCommonBean = response.succeed();
                    if (setUpCommonBean!=null){
                        tvTitle.setText(setUpCommonBean.getTitle());
                        tvContent.setText(setUpCommonBean.getContent());
                    }
                }
            }
        });

    }

}
