package com.xian.scooter.module.stores;

import android.view.View;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class StoresFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    public static StoresFragment newInstance() {
        return new StoresFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_stores;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("门店");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
    }
}
