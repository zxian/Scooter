package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseRecordsActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_clear_search)
    ImageView btnClearSearch;
    @BindView(R.id.tv_right_text)
    ImageView tvRightText;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_purchase_records;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("购买记录");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

}
