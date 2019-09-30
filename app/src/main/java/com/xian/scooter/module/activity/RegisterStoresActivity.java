package com.xian.scooter.module.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.TitleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterStoresActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_introduce)
    EditText etIntroduce;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_head_name)
    EditText etHeadName;
    @BindView(R.id.et_head_number)
    EditText etHeadNumber;
    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.iv_emblem)
    ImageView ivEmblem;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register_stores;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.iv_logo, R.id.rl_location, R.id.rl_type, R.id.iv_portrait, R.id.iv_emblem, R.id.iv_picture, R.id.tv_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                break;
            case R.id.rl_location:
                break;
            case R.id.rl_type:
                break;
            case R.id.iv_portrait:
                break;
            case R.id.iv_emblem:
                break;
            case R.id.iv_picture:
                break;
            case R.id.tv_apply:
                break;
        }
    }
}
