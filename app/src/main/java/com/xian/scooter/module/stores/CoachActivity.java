package com.xian.scooter.module.stores;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.kzz.dialoglibraries.dialog.DialogCreate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.CoachBean;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.EventPar;
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

import static com.xian.scooter.utils.UISizeUtils.dp2px;

public class CoachActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.swipe_menu_listview)
    SwipeMenuListView swipeMenuListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private static int TOTAL_COUNTER = 0;
    private static int PAGE_INDEX = 1;
    private static final int PAGE_SIZE = 10;
    private static int mCurrentCounter = 0;

    private DialogCreate mDialogCreate;
    private CommonLvAdapter<CoachBean> mAdapter;
    private List<CoachBean> coachList = new ArrayList<>();
    private String storeId;

    @Override
    protected void handleIntent(Intent intent) {
        storeId=intent.getStringExtra("storeId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_coach;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("教练列表");
        titleBarView.setLeftOnClickListener(v -> finish());

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onMyRefresh();
            }

            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    PAGE_INDEX++;
                    //网络请求获取列表数据
                    getCoachList(storeId,PAGE_INDEX, PAGE_SIZE);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                }
            }
        });
        initListview();
        onMyRefresh();
    }

    private void initListview() {
        mAdapter = new CommonLvAdapter<CoachBean>(this, R.layout.item_coach, coachList) {
            @Override
            protected void convert(final ViewHolderLv holder, CoachBean bean, final int position) {
//                holder.setText(R.id.tv_name, bean.getName());
            }
        };

        swipeMenuListview.setAdapter(mAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        createMenu(menu);
                        break;
                }
            }

            /**
             * 特别说明：源码坑会导致字体颜色有色差
             *setBackground方法在源码里面有进行了转换颜色，使用的是mContext.getResources().getColor
             * 而setTitleColor方法在源码中是没有进行转换颜色的，所以要手动转换颜色。调用mContext.getResources().getColor
             * @param menu SwipeMenu
             */
            private void createMenu(SwipeMenu menu) {
                SwipeMenuItem item = new SwipeMenuItem(mActivity);
                item.setWidth(dp2px(60));
                item.setTitle("编辑");
                item.setBackground(R.color.red_alter);
                item.setTitleColor(mActivity.getResources().getColor(R.color.white));
                item.setTitleSize(15);
                menu.addMenuItem(item);

                SwipeMenuItem itemDelete = new SwipeMenuItem(mActivity);
                itemDelete.setWidth(dp2px(60));
                itemDelete.setTitle("删除");
                itemDelete.setBackground(R.color.red_delete);
                itemDelete.setTitleColor(mActivity.getResources().getColor(R.color.white));
                itemDelete.setTitleSize(15);
                menu.addMenuItem(itemDelete);
            }
        };
        swipeMenuListview.setMenuCreator(creator);
        swipeMenuListview.setOnMenuItemClickListener((position, menu, index) -> {

            //index 0 修改 ；1 删除
            if (index == 0) {

            } else {
                DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
                mDialogCreate = builder
                        .setAddViewId(R.layout.dialog_ok_cancel)
                        .setIsHasCloseView(false)
                        .setDialogSetDateInterface(inflaterView -> {
                            TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                            TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                            TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                            TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                            tvTitle.setVisibility(View.GONE);
                            tvMsg.setText("确定删除？");
                            tvConfirm.setOnClickListener(v -> {
                                mDialogCreate.dismiss();
//                                getCommunityDocDelete(position);
                            });
                            tvCancel.setOnClickListener(v -> mDialogCreate.dismiss());
                        })
                        .build();
                mDialogCreate.showSingle();
            }
            return false;
        });
        swipeMenuListview.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(mActivity, CommunityDocumentDetailsActivity.class);
//            intent.putExtra("docId", communityDocList.get(position).getId());
//            intent.putExtra("name", communityDocList.get(position).getName());
//            startActivity(intent);
        });
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        mCurrentCounter = 0;
        PAGE_INDEX = 1;
        coachList.clear();
        getCoachList(storeId,PAGE_INDEX, PAGE_SIZE);
    }

    /**
     * 门店教练列表
     *
     * @param storeId  门店id
     * @param pageNum  页码
     * @param pageSize 查询数量
     */
    private void getCoachList(String storeId,int pageNum, int pageSize) {
        ApiRequest.getInstance().get(HttpURL.COACH_LIST.replace("{storeId}", storeId)
                .replace("{size}", pageSize + "")
                .replace("{current}", pageNum + ""),new DefineCallback<PageBean<EventBean>>() {
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
                super.onEnd();
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    refreshLayout.setNoMoreData(false);//恢复上拉状态
                }
            }
        });
    }
    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<EventBean> list) {
        if (list.size() > 0 && mAdapter != null) {
//            mCurrentCounter += list.size();
//            coachList.addAll(list)
//            mAdapter.setmDatas(coachList);

        }
    }
    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivity(new Intent(mActivity,CoachAddActivity.class));
    }
}
