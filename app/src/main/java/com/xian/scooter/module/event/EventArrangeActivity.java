package com.xian.scooter.module.event;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventArrangeBean;
import com.xian.scooter.module.adapter.EventArrangeEAdapter;
import com.xian.scooter.utils.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EventArrangeActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.e_list_view)
    ExpandableListView eListView;
    private EventArrangeEAdapter eAdapter;
    private List<EventArrangeBean> list=new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赛事安排");
        titleBarView.setLeftOnClickListener(view1 -> finish());
        eAdapter = new EventArrangeEAdapter(mActivity, list);
        eListView.setAdapter(eAdapter);
    }
}
