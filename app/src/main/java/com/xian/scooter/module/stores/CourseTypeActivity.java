package com.xian.scooter.module.stores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.xian.scooter.bean.EventTypeBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.module.adapter.CoursePackageAdapter;
import com.xian.scooter.module.adapter.CourseTypeAdapter;
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

public class CourseTypeActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private CourseTypeAdapter adapter;
    private List<CoursePackageBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private String typeId;
    private String name;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_course_type;
    }


    @Override
    protected void init() {
        titleBarView.setTvTitleText("适用类型");
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
        adapter = new CourseTypeAdapter(mActivity,R.layout.item_course_package,list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                typeId = adapter.getDatas().get(position).getId();
                name = adapter.getDatas().get(position).getPackage_name();
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
        getPackagePage(PAGE_INDEX,PAGE_SIZE);
    }


    /**
     *
     * @param size  每页显示数量
     * @param current  当前页
     */
    private void getPackagePage( int current,int size ) {

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

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("typeId",typeId);
        intent.putExtra("name",name);
        setResult(RESULT_OK,intent);
        finish();
    }
}

