package com.xian.scooter.module.interactive;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class InteractiveFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    public static InteractiveFragment newInstance() {
        return new InteractiveFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_interactive;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("互动");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

    }
}
