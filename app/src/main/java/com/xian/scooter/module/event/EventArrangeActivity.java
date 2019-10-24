package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ExpandableListView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventArrangeBean;
import com.xian.scooter.bean.EventPlanBean;
import com.xian.scooter.bean.EventTypeBean;
import com.xian.scooter.module.adapter.EventArrangeEAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventArrangeActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.e_list_view)
    ExpandableListView eListView;
    private EventArrangeEAdapter eAdapter;
    private List<EventArrangeBean> list = new ArrayList<>();
    private String competitionId;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("competitionId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赛事安排");
        titleBarView.setLeftOnClickListener(view1 -> finish());
        eAdapter = new EventArrangeEAdapter(mActivity, list);
        eListView.setAdapter(eAdapter);
        onMyRefresh();
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        getCompetitionSetList(competitionId);
    }

    /**
     * 赛事类型设置列表
     *
     * @param competitionId 赛事ID
     */
    private void getCompetitionSetList(String competitionId) {
        if (!TextUtils.isEmpty(competitionId)) {
            ApiRequest.getInstance().post(HttpURL.COMPETITION_SET_LIST.replace("{competitionId}",
                    competitionId), new DefineCallback<List<EventTypeBean>>() {
                @Override
                public void onMyResponse(SimpleResponse<List<EventTypeBean>, HttpEntity> response) {
                    if (response.isSucceed()) {
                        if (response.succeed() != null && response.succeed().size() > 0) {
                            List<EventTypeBean> eventTypeList = response.succeed();
                            eventTypeList.get(0).getId();
                            getCompetitionPlanList(eventTypeList.get(0).getId());
                        }

                    }
                }
            });
        }
    }

    /**
     * 赛事场次列表
     *
     * @param setId 赛事类型ID
     */
    private void getCompetitionPlanList(String setId) {
        if (!TextUtils.isEmpty(competitionId)) {
            ApiRequest.getInstance().post(HttpURL.COMPETITION_PLAN_LIST.replace("{setId}",
                    competitionId), new DefineCallback<List<EventPlanBean>>() {
                @Override
                public void onMyResponse(SimpleResponse<List<EventPlanBean>, HttpEntity> response) {
                    if (response.isSucceed()) {
                        if (response.succeed() != null && response.succeed().size() > 0) {
                            List<EventPlanBean> eventTypeList = response.succeed();

                        }

                    }
                }
            });
        }
    }

    @OnClick(R.id.tv_btn)
    public void onViewClicked() {
        Intent intent = new Intent(mActivity, EventArrangeRulesActivity.class);
        intent.putExtra("competitionId",competitionId);
        startActivity(intent);
    }
}
