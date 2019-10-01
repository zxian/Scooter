package com.xian.scooter.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.bit.adapter.lvadapter.ViewHolderLv;
import com.kzz.dialoglibraries.DialogSetDateInterface;
import com.kzz.dialoglibraries.DialogShowingInterface;
import com.kzz.dialoglibraries.popupWindow.PopupWindowBase;
import com.xian.scooter.R;
import com.xian.scooter.adapter.SelectAdapter;

import java.util.List;

/**
 * author : jiacan.zhou
 * time   : 2019/05/07
 * desc   : 普通PopupView
 */

public class BasePopupView extends PopupWindowBase {


    private Context mContext;
    private ListView listView;
    private SelectAdapter rightAdapter;
    private Handler handler;
    private List<String> groupList;
    private int selectPosition;



    public BasePopupView(Context mContext, View parentView, List<String> groupList, int selectPosition, Handler handler) {
        super(mContext, parentView, R.layout.popu_task_select);
        this.mContext = mContext;
        this.groupList = groupList;
        this.selectPosition = selectPosition;
        this.handler = handler;
    }

    @Override
    public DialogSetDateInterface getCallback() {
        return inflaterView -> {
            listView = inflaterView.findViewById(R.id.list_view);
            initPopupWindowData(listView,groupList);
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
     * @param listView 数据
     */
    private void initPopupWindowData(ListView listView, List<String> groupList) {
        rightAdapter = new SelectAdapter<String>(mContext,groupList, selectPosition) {
            @Override
            protected void converts(ViewHolderLv viewHolder, String item) {
                viewHolder.setText(R.id.tv_dialog_select_item, item);
            }

        };

        listView.setAdapter(rightAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectPosition = position;
            sendMessage();
            dismiss();
        });

    }

    /**
     * 筛选结果已出
     * 发送消息更新列表
     */
    private void sendMessage() {
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < selectList.size(); i++) {
//            String teamId = groupList.get(selectList.get(i)).getTeamId();
//            list.add(teamId);
//        }
        Message msg = new Message();
        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("teamList", list);
        bundle.putInt("selectPosition", selectPosition);
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
