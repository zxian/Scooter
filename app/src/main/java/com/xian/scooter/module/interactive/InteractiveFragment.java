package com.xian.scooter.module.interactive;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;

public class InteractiveFragment extends BaseFragment {


    public static InteractiveFragment newInstance() {
        return new InteractiveFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_interactive;
    }

    @Override
    protected void init(View view) {

    }
}
