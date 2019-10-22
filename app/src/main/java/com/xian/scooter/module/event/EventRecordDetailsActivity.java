package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventRecordDetailsBean;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventRecordDetailsActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_yellow)
    TextView tvYellow;
    @BindView(R.id.tv_black)
    TextView tvBlack;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_record_details;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("规则安排");
        titleBarView.setLeftOnClickListener(view -> finish());
        Intent intent = this.getIntent();
        String id = intent.getStringExtra("data");
        getCompetitionJoinBid(id);
    }


    /**
     * @param id
     */
    private void getCompetitionJoinBid(String id) {
        ApiRequest.getInstance().get(HttpURL.COMPETITION_JOIN_BYID.replace("{id}",
                id + ""), new DefineCallback<EventRecordDetailsBean>() {
            @Override
            public void onMyResponse(SimpleResponse<EventRecordDetailsBean, HttpEntity> response) {
                if (response.isSucceed()) {
                    EventRecordDetailsBean eventRecordDetailsBean = response.succeed();
                    if (eventRecordDetailsBean != null) {
                        String name = eventRecordDetailsBean.getCompetition_name();
                        tvGroup.setText(name);

                        String set_name = eventRecordDetailsBean.getCompetition_set_name();
                        tvNickname.setText(set_name);

                        String child_name = eventRecordDetailsBean.getChild_name();
                        tvName.setText(child_name);

                        int sex = eventRecordDetailsBean.getChild_sex();
                        if (sex == 1) {
                            tvGender.setText("男");
                        } else if (sex == 2) {
                            tvGender.setText("女");
                        }

                        int age = eventRecordDetailsBean.getChild_age();
                        String s = age + "岁";
                        tvAge.setText(s);

                        String user_phone = eventRecordDetailsBean.getCust_user_phone();
                        tvPhone.setText(user_phone);

                        String remark = eventRecordDetailsBean.getRemark();
                        tvNote.setText(remark);


                    }
                }
            }


            @Override
            public void onEnd() {
//                recyclerView.refreshComplete(mCurrentCounter);
            }
        });

    }

    @OnClick({R.id.tv_game, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_game:
                break;
            case R.id.tv_sign:
                break;
        }
    }
}
