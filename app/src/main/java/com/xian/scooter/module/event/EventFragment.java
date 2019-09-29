package com.xian.scooter.module.event;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;

public class EventFragment extends BaseFragment {


    public static EventFragment newInstance() {
        return new EventFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_event;
    }

    @Override
    protected void init(View view) {

    }
}
