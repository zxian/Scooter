package com.xian.scooter.module.event;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventPlanBean;
import com.xian.scooter.bean.EventTypeBean;
import com.xian.scooter.module.adapter.EventArrangePlanAdapter;
import com.xian.scooter.module.adapter.EventArrangeTypeAdapter;
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

public class EventArrangePlanActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventArrangePlanAdapter adapter;
    private List<EventPlanBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String setId;
    private String name;
    private String planName;
    private String planId;

    @Override
    protected void handleIntent(Intent intent) {
        setId = intent.getStringExtra("setId");
        name = intent.getStringExtra("name");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange_plan;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("进阶场次");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        tvTitle.setText(name);
        initRecyclerView();
        onMyRefresh();
    }

    private void initRecyclerView() {
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                onMyRefresh();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new EventArrangePlanAdapter(mActivity, R.layout.item_event_arrange_plan, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                typeId = adapter.getDatas().get(position).getId();
//                name = adapter.getDatas().get(position).getApply_competition_name();
                adapter.updataItem(position);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        adapter.cleanData();
        getCompetitionPlanList(setId);

    }

    /**
     * 赛事场次列表
     *
     * @param setId 赛事类型ID
     */
    private void getCompetitionPlanList(String setId) {
        if (!TextUtils.isEmpty(setId)) {
            ApiRequest.getInstance().post(HttpURL.COMPETITION_PLAN_LIST.replace("{setId}",
                    setId), new DefineCallback<List<EventPlanBean>>() {
                @Override
                public void onMyResponse(SimpleResponse<List<EventPlanBean>, HttpEntity> response) {
                    if (response.isSucceed()) {
                        if (response.succeed() != null) {
                            List<EventPlanBean> list = response.succeed();
                            addItems(list);
                        }

                    }
                }
                @Override
                public void onEnd() {
                    super.onEnd();
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            });
        }
    }


    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<EventPlanBean> list) {
        if (list.size() > 0 && adapter != null) {
            adapter.updataItem(list);
        }
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("planId",planId);
        intent.putExtra("planName",planName);
        setResult(RESULT_OK,intent);
        finish();
    }
}
