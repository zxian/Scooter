package com.xian.scooter.module.event;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.EventPlanBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventPar;
import com.xian.scooter.beanpar.EventPlanPagePar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.adapter.EventArrangePlanAdapter;
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

public class EventArrangePersonActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventArrangePlanAdapter adapter;
    private List<EventPlanBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String competitionId;
    private String planName;
    private String planId;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("competitionId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange_person;
    }
    @Override
    protected void init() {
        titleBarView.setTvTitleText("选择人员");
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
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    PAGE_INDEX++;
                    //网络请求获取列表数据
                    getCompetitionPlanPage(PAGE_INDEX, PAGE_SIZE);
                } else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
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
        mCurrentCounter = 0;
        PAGE_INDEX = 1;
        getCompetitionPlanPage(PAGE_INDEX, PAGE_SIZE);

    }

    /**
     * 赛事场次参与人员列表
     *
     * @param pageNum  页码
     * @param pageSize 查询数量
     */
    private void getCompetitionPlanPage(int pageNum, int pageSize) {
        EventPlanPagePar par = new EventPlanPagePar();
        par.setCompetition_id(competitionId);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_PLAN_PAGE.replace("{size}", pageSize + "")
                .replace("{current}", pageNum + ""), par, new DefineCallback<PageBean<EventBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<EventBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
//                        List<EventBean> list = response.succeed().getRecords();
//                        addItems(list);
                    }

                }
            }

            @Override
            public void onEnd() {
                recyclerView.refreshComplete(mCurrentCounter);
            }
        });
    }


    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<EventPlanBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
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