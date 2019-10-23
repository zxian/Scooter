package com.xian.scooter.module.event;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.module.adapter.EventAddSetupAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import butterknife.BindView;
import butterknife.OnClick;

public class EventAddSetupActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private static final int ADD_REQUEST_CODE = 100;
    private static final int ALTER_REQUEST_CODE = 200;
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private EventAddSetupAdapter adapter;
    private List<CompetitionSavePar> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String competitionId;

    private ArrayList<CompetitionSavePar> competitionSaveList = new ArrayList<>();
    private int alterPosition=-1;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("competitionId");
        competitionSaveList =intent.getParcelableArrayListExtra("CompetitionSaveParList");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_add_setup;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赛事设置");
        titleBarView.setLeftOnClickListener(view1 -> {
            Intent intent=new Intent();
            intent.putParcelableArrayListExtra("CompetitionSaveParList",competitionSaveList);
            setResult(RESULT_OK,intent);
            finish();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new EventAddSetupAdapter(mActivity, R.layout.item_event_add_setup, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                alterPosition =position;
                Intent intent = new Intent(mActivity, EventAddSetupAddActivity.class);
                intent.putExtra("CompetitionSavePar",competitionSaveList.get(position));
                startActivityForResult(intent,ALTER_REQUEST_CODE);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        adapter.cleanData();
        addItems(competitionSaveList);
        recyclerView.refreshComplete(mCurrentCounter);
    }


    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<CompetitionSavePar> list) {
        if (list.size() > 0 && adapter != null) {
            adapter.updataItem(list);
        }
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivityForResult(new Intent(mActivity,EventAddSetupAddActivity.class),ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case ADD_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        CompetitionSavePar competitionSave =data.getParcelableExtra("CompetitionSavePar");
                        competitionSaveList.add(competitionSave);
                        onMyRefresh();
                    }
                case ALTER_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        CompetitionSavePar competitionSave = data.getParcelableExtra("CompetitionSavePar");
                        if (alterPosition!=-1) {
                            competitionSaveList.set(alterPosition,competitionSave);//把修改后item数据替换掉
                        }
                        onMyRefresh();
                    }
                    break;
            }
        }
    }
}
