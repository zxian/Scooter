package com.xian.scooter.module.training;

import android.view.View;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;

public class TrainingFragment extends BaseFragment {


    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_training;
    }

    @Override
    protected void init(View view) {

    }
}
