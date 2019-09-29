package com.xian.scooter.module.event;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class EventFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    public static EventFragment newInstance() {
        return new EventFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_event;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("赛事");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
    }
}
