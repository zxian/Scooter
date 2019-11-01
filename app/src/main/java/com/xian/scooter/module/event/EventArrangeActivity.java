package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.view.View;
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
        // 设置二级item点击的监听器，同时在Adapter中设置isChildSelectable返回值true，同时二级列表布局中控件不能设置点击效果
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2, int arg3, long arg4) {
                EventPlanBean eventPlanBean = list.get(arg2).getList().get(arg3);

                Intent intent = new Intent(mActivity, EventArrangeRulesActivity.class);
                intent.putExtra("competitionId",competitionId);
                intent.putExtra("type",2);//1 新增 2 修改
                intent.putExtra("eventPlanBean",eventPlanBean);
                startActivity(intent);
                return false;
            }
        });

        onMyRefresh();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMyRefresh();
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        list.clear();
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
                            for (int i=0;i<eventTypeList.size();i++) {
                                EventTypeBean eventTypeBean = eventTypeList.get(i);
                                getCompetitionPlanList(eventTypeBean);
                            }
                        }

                    }
                }
            });
        }
    }

    /**
     * 赛事场次列表
     *
     * @param eventTypeBean 赛事类型
     */
    private void getCompetitionPlanList(EventTypeBean eventTypeBean) {
        String setId = eventTypeBean.getId();
        if (!TextUtils.isEmpty(setId)) {
            ApiRequest.getInstance().post(HttpURL.COMPETITION_PLAN_LIST.replace("{setId}",
                    setId), new DefineCallback<List<EventPlanBean>>() {
                @Override
                public void onMyResponse(SimpleResponse<List<EventPlanBean>, HttpEntity> response) {
                    if (response.isSucceed()) {
                        if (response.succeed() != null && response.succeed().size() > 0) {
                            List<EventPlanBean> eventPlanList = response.succeed();
                            EventArrangeBean eventArrangeBean=new EventArrangeBean();
                            eventArrangeBean.setId(eventTypeBean.getId());
                            eventArrangeBean.setApply_competition_name(eventTypeBean.getApply_competition_name());
                            eventArrangeBean.setCompetition_id(eventTypeBean.getCompetition_id());
                            eventArrangeBean.setList(eventPlanList);
                            list.add(eventArrangeBean);
                            eAdapter.updataData(list);
                            //遍历所有group,将所有项设置成默认展开
                            for (int i = 0; i < list.size(); i++) {
                                eListView.expandGroup(i);
                            }

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
        intent.putExtra("type",2);//1 新增 2 修改
        startActivity(intent);
    }
}
