package com.xian.scooter.module.mall;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class MallFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    public static MallFragment newInstance() {
        return new MallFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("商城");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

    }
}
