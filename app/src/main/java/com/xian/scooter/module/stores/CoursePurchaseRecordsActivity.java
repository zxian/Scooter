package com.xian.scooter.module.stores;


import android.support.v7.widget.LinearLayoutManager;
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

import com.xian.scooter.bean.CoursePurchaseRecordsBean;
import com.xian.scooter.bean.PageBean;

import com.xian.scooter.module.adapter.CoursePurchaseRecordsAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class CoursePurchaseRecordsActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_clear_search)
    ImageView btnClearSearch;
    @BindView(R.id.tv_right_text)
    ImageView tvRightText;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private CoursePurchaseRecordsAdapter adapter;
    private List<CoursePurchaseRecordsBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_purchase_records;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("购买记录");
        titleBarView.setLeftOnClickListener(view -> finish());
        initRecyclerView();
        onMyRefresh();
    }

    private void initRecyclerView(){
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                onMyRefresh();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (mCurrentCounter < TOTAL_COUNTER){
                    PAGE_INDEX++;
                }else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CoursePurchaseRecordsAdapter(mActivity,R.layout.item_record,list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    /**
     * 下拉刷新
     */

    private void onMyRefresh(){
        adapter.cleanData();
        mCurrentCounter = 0;
        PAGE_INDEX = 1;
    }

    /**
     *
     * @param size  每页显示数量
     * @param current  当前页
     */
    private void getPackagePage(int size , int current) {

        ApiRequest.getInstance().post(HttpURL.PACKAGE_PAGE.replace("{size}", size  + "")
                .replace("{current}", current  + ""), new DefineCallback<PageBean<CoursePurchaseRecordsBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<CoursePurchaseRecordsBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
                        List<CoursePurchaseRecordsBean> list = response.succeed().getRecords();
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
     *
     */

    private void addItems(List<CoursePurchaseRecordsBean> list){
        if (list.size() > 0 && adapter!= null){
            mCurrentCounter += list.size();
            adapter.updataItem(list);
        }
    }

}
