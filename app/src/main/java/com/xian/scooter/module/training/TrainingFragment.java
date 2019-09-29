package com.xian.scooter.module.training;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;

public class TrainingFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_training;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("培训");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

    }
}
