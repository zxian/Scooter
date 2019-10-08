package com.xian.scooter.module.stores;

import android.os.Bundle;
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
import com.xian.scooter.bean.MemberBean;
import com.xian.scooter.module.adapter.EventAdapter;
import com.xian.scooter.module.adapter.MemberAdapter;
import com.xian.scooter.utils.TitleBarView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private List<MemberBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_members;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("会员管理");
        titleBarView.setLeftOnClickListener(view -> finish());
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {onRestart();

            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter< TOTAL_COUNTER){
                    PAGE_INDEX++;
                }else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        list.add(new MemberBean());
        list.add(new MemberBean());
        list.add(new MemberBean());
        list.add(new MemberBean());
        list.add(new MemberBean());
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


}
