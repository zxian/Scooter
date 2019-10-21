package com.xian.scooter.module.stores;

import android.content.Intent;
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
import com.xian.scooter.bean.MembersBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.MembersPar;
import com.xian.scooter.module.adapter.MemberAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MembersActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_clear_search)
    ImageView btnClearSearch;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;
    private static int PAGE_INDEX = 1;
    private static final int PAGE_SIZE = 10;
    private static int mCurrentCounter = 0;
    private MemberAdapter adapter;
    private List<MembersBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String storeId;


    @Override
    protected void handleIntent(Intent intent) {
        storeId = intent.getStringExtra("storeId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_members;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("会员管理");
        titleBarView.setLeftOnClickListener(view -> finish());
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
                if (mCurrentCounter< TOTAL_COUNTER){
                    PAGE_INDEX++;
                    getCustList(PAGE_INDEX, PAGE_SIZE);
                }else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new MemberAdapter(mActivity,R.layout.item_embers,list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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
        getCustList(PAGE_INDEX, PAGE_SIZE);
    }
    /**
     *
     * 获取用户分页数据
     *
     * @param pageNum  页码
     * @param pageSize 查询数量
     */
    private void getCustList(int pageNum, int pageSize) {
        MembersPar par=new MembersPar();
//        par.setRole("1");//用户身份标识，0：普通用户，1、门店用户，2、教练用户
        par.setStore_id(storeId);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.CUST_LIST.replace("{size}", pageSize + "")
                .replace("{current}", pageNum + ""), par,new DefineCallback<PageBean<MembersBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<MembersBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
                        List<MembersBean> list = response.succeed().getRecords();
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
    private void addItems(List<MembersBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            adapter.setData(list);
        }
    }

}
