package com.xian.scooter.module.mall;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;

public class MallFragment extends BaseFragment {


    public static MallFragment newInstance() {
        return new MallFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init(View view) {

    }
}
