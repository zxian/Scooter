package com.xian.scooter.module.stores;

import android.view.View;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;

public class StoresFragment extends BaseFragment {


    public static StoresFragment newInstance() {
        return new StoresFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_stores;
    }

    @Override
    protected void init(View view) {

    }
}
