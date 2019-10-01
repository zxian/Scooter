package com.xian.scooter.module.event;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.module.adapter.EventAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EventFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventAdapter adapter;
    private List<EventBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

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
//                    getTaskList(type, status, planId, PAGE_INDEX, PAGE_SIZE);
                }else {
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
//                Intent intent = new Intent(mActivity, TaskDetailActivity.class);
//                intent.putExtra("id", list.get(position).getId());
//                startActivity(intent);
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
//        getTaskList(type, status, planId, PAGE_INDEX, PAGE_SIZE);
    }


    /**
     * 根据计划分页查询任务
     *
     * @param type     类型 1：日常巡检，2：专项排查
     * @param status   (0：未开始，1：进行中，2：已完成)
     * @param planId   计划ID
     * @param pageNum  页码
     * @param pageSize 查询数量
     */
    private void getTaskList(int type, int status, long planId, int pageNum, int pageSize) {
//        TaskPar par = new TaskPar();
//
//        par.setPageNum(pageNum);
//        par.setPageSize(pageSize);
//        ApiRequest.getInstance().post(HttpURL.TASK_LIST, par, new DefineCallback<EventBean>() {
//            @Override
//            public void onMyResponse(SimpleResponse<EventBean, HttpEntity> response) {
//                if (response.isSucceed()) {
//                    if (response.succeed() != null) {
//                        TOTAL_COUNTER = response.succeed().getTotal();
//                        List<TaskBean> list = response.succeed().getList();
//                        addItems(list);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onEnd() {
//                recyclerView.refreshComplete(mCurrentCounter);
//            }
//        });
    }
    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<EventBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            adapter.updataItem(list);
        }
    }

}
