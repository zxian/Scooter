package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.adapter.EventAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class EventFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventAdapter adapter;
    private List<EventBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private long time;
    private static int REQUEST_CODE_ADD = 100;

    public static EventFragment newInstance() {
        return new EventFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_event;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("赛事");
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
                    getCompetitionList(PAGE_INDEX, PAGE_SIZE);
                } else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new EventAdapter(mActivity, R.layout.item_event, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, EventDetailsActivity.class);
                intent.putExtra("id", adapter.getDatas().get(position).getId());
                intent.putExtra("time", time);
                startActivity(intent);
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
        getCompetitionList(PAGE_INDEX, PAGE_SIZE);
    }


    /**
     * 赛事列表
     *
     * @param pageNum  页码
     * @param pageSize 查询数量
     */
    private void getCompetitionList(int pageNum, int pageSize) {
        EventPar par = new EventPar();
        par.setIs_app("0");//是否用户端查询：0、否，1、是
        par.setStore_id(UserManager.getInstance().getStoreId());
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_LIST.replace("{size}", pageSize + "")
                .replace("{current}", pageNum + ""), par, new DefineCallback<PageBean<EventBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<EventBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
                        List<EventBean> list = response.succeed().getRecords();
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

    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<EventBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            Date netTime = TimeUtils.getNetTime();
            time = netTime.getTime();
            adapter.updataItem(list, time);

        }
    }

    @OnClick(R.id.fab_add)
    public void onViewClicked() {
        startActivityForResult(new Intent(mActivity, EventAddActivity.class), REQUEST_CODE_ADD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                if (resultCode == RESULT_OK) {
                  onMyRefresh();
                }
            }

        }
    }
}
