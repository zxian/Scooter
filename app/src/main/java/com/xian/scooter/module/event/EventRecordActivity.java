package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventRecordBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventRecordPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.adapter.EventRecordAdapter;
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

public class EventRecordActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_clear_search)
    ImageView btnClearSearch;
    @BindView(R.id.iv_screening)
    ImageView ivScreening;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventRecordAdapter adapter;
    private List<EventRecordBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String competitionId;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("id");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_record;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("报名记录");
        titleBarView.setLeftOnClickListener(view -> finish());
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = etSearch.getText().toString().trim();

            }
        });
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
                    getCompetitionJoin(PAGE_INDEX, PAGE_SIZE);
                } else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new EventRecordAdapter(mActivity, R.layout.item_event_record, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, EventRecordDetailsActivity.class);
                String msg = adapter.getDatas().get(position).getId();
                intent.putExtra("data", msg);
                startActivity(intent);
            }
        });

    }

    private void onMyRefresh() {
        adapter.cleanData();
        mCurrentCounter = 0;
        PAGE_INDEX = 1;
        getCompetitionJoin(PAGE_INDEX, PAGE_SIZE);
    }

    /**
     * 获取用户报名记录分页数据
     *
     * @param size    每页显示数量
     * @param current 当前页
     */
    private void getCompetitionJoin(int current, int size) {
        EventRecordPar par = new EventRecordPar();
        par.setCompetitionId(competitionId);
        par.setStoreId(UserManager.getInstance().getStoreId());
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_JOIN.replace("{size}",
                size + "").replace("{current}", current + ""), par, new DefineCallback<PageBean<EventRecordBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<EventRecordBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
                        List<EventRecordBean> list = response.succeed().getRecords();
                        addItems(list);
                    }

                }
            }

            @Override
            public void onEnd() {
                recyclerView.refreshComplete(mCurrentCounter);
            }
        });

    }

    private void addItems(List<EventRecordBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            adapter.updataItem(list);
        }
    }


    @OnClick({R.id.tv_results, R.id.tv_arrange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_results:
                break;
            case R.id.tv_arrange:
                break;
        }
    }
}
