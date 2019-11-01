package com.xian.scooter.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.adapter.rvadapter.MultiItemTypeAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.kzz.dialoglibraries.DialogSetDateInterface;
import com.kzz.dialoglibraries.DialogShowingInterface;
import com.kzz.dialoglibraries.popupWindow.PopupWindowBase;
import com.xian.scooter.R;
import com.xian.scooter.adapter.MultiSelectAdapter;
import com.xian.scooter.adapter.MultiSelectRvAdapter;
import com.xian.scooter.adapter.SelectAdapter;
import com.xian.scooter.bean.EventTypeBean;

import java.util.ArrayList;
import java.util.List;


public class BaseMultiPopupView extends PopupWindowBase {


    private Context mContext;
    private RecyclerView recyclerView;
    private TextView tvEnsure;
    private MultiSelectRvAdapter rightAdapter;
    private Handler handler;

    private List<EventTypeBean> groupList;
    private List<Integer> selectList = new ArrayList<>();


    public BaseMultiPopupView(Context mContext, View parentView, List<EventTypeBean> groupList, Handler handler) {
        super(mContext, parentView, R.layout.popu_multi_select);
        this.mContext = mContext;
        this.groupList = groupList;
        this.handler = handler;
    }

    @Override
    public DialogSetDateInterface getCallback() {
        return inflaterView -> {
            recyclerView = inflaterView.findViewById(R.id.recycler_view);

            tvEnsure = inflaterView.findViewById(R.id.tv_ensure);
            initPopupWindowData(recyclerView,tvEnsure, groupList);
        };
    }

    @Override
    public DialogShowingInterface getShowingInterface() {
        return isShowing -> {
            if (isShowing) {
                sendPopuShowingMsg();
            } else {
                sendPopuCloseMsg();
            }
        };
    }

    /**
     * 初始化弹出的数据
     *
     * @param recyclerView 数据
     * @param tvEnsure     确定
     */
    private void initPopupWindowData(RecyclerView recyclerView, TextView tvEnsure, List<EventTypeBean> groupList) {
        rightAdapter = new MultiSelectRvAdapter<EventTypeBean>(mContext,
                R.layout.dialog_multiselect_event_type_item, groupList, selectList) {
            @Override
            protected void converts(ViewHolderRv viewHolder, EventTypeBean item) {
                viewHolder.setText(R.id.tv_content, item.getApply_competition_name());
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (selectList.size() > 0) {
                    boolean isAddPosition = false;
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i) == position) {
                            selectList.remove(i);
                            isAddPosition = false;
                            break;
                        } else {
                            isAddPosition = true;
                        }
                    }
                    if (isAddPosition) {
                        selectList.add(position);
                    }
                } else {
                    selectList.add(position);
                }
                rightAdapter.updataItem(selectList);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        tvEnsure.setOnClickListener(v -> {
            sendMessage();
            dismiss();
        });
    }

    /**
     * 筛选结果已出
     * 发送消息更新列表
     */
    private void sendMessage() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            String teamId = groupList.get(selectList.get(i)).getId();
            list.add(teamId);
        }
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", list);
        if (list.size() == 1) {
            bundle.putString("name", groupList.get(selectList.get(0)).getApply_competition_name());
        }
        msg.what = 2;
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /**
     * 发送popupView正在显示消息
     */
    private void sendPopuShowingMsg() {
        handler.sendEmptyMessage(0);
    }

    /**
     * 送popupView关闭消息
     */
    private void sendPopuCloseMsg() {
        handler.sendEmptyMessage(1);
    }
}
