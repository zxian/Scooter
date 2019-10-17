package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.CoursePackageBean;
import com.xian.scooter.bean.EventAddSetupBean;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventPar;
import com.xian.scooter.module.adapter.CoursePackageAdapter;
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

public class CoursePackageActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private CoursePackageAdapter adapter;
    private List<CoursePackageBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_course_package;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("课程包列表");
        titleBarView.setLeftOnClickListener(view -> finish());
        titleBarView.setRightText("购买记录");
        titleBarView.setRightOnClickListener(view -> {
            startActivity(new Intent(mActivity,CoursePurchaseRecordsActivity.class));
        });
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
                    getPackagePage(PAGE_INDEX,PAGE_SIZE);

                }else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CoursePackageAdapter(mActivity,R.layout.item_course_package,list);
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

        getPackagePage(PAGE_INDEX,PAGE_SIZE);
    }


    /**
     *
     * @param size  每页显示数量
     * @param current  当前页
     */
    private void getPackagePage(int size , int current) {

        ApiRequest.getInstance().post(HttpURL.PACKAGE_PAGE.replace("{size}", size  + "")
                .replace("{current}", current  + ""), new DefineCallback<PageBean<CoursePackageBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<CoursePackageBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
                        TOTAL_COUNTER = response.succeed().getTotal();
                        List<CoursePackageBean> list = response.succeed().getRecords();
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
    private void addItems(List<CoursePackageBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            adapter.updataItem(list);
        }
    }


    @OnClick({R.id.tv_add, R.id.tv_give})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                startActivity(new Intent(mActivity,NewCourseActivity.class));
                break;
            case R.id.tv_give:
                startActivity(new Intent(mActivity,CourseGivingActivity.class));
                break;
        }
    }


}
