package com.xian.scooter.module.event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventTypeBean;
import com.xian.scooter.module.adapter.EventAddSetupAdapter;
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
import butterknife.OnClick;

public class EventArrangeTypeActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventArrangeTypeAdapter adapter;
    private List<EventTypeBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String competitionId;
    private String typeId;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("competitionId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange_type;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("报名类型");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
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
        adapter = new EventArrangeTypeAdapter(mActivity, R.layout.item_event_add_setup, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                typeId = adapter.getDatas().get(position).getId();
                adapter.updataItem(position);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        adapter.cleanData();
        getCompetitionList(competitionId);

    }


    /**
     * 赛事类型设置列表
     *
     * @param competitionId  赛事ID
     */
    private void getCompetitionList(String competitionId) {
        if (!TextUtils.isEmpty(competitionId)) {
            ApiRequest.getInstance().post(HttpURL.COMPETITION_SET_LIST.replace("{competitionId}", competitionId), new DefineCallback<List<EventTypeBean>>() {
                @Override
                public void onMyResponse(SimpleResponse<List<EventTypeBean>, HttpEntity> response) {
                    if (response.isSucceed()) {
                        if (response.succeed() != null) {
                            List<EventTypeBean> list = response.succeed();
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
    private void addItems(List<EventTypeBean> list) {
        if (list.size() > 0 && adapter != null) {
            adapter.updataItem(list);
        }
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("typeId",typeId);
        setResult(RESULT_OK,intent);
        finish();
    }
}
