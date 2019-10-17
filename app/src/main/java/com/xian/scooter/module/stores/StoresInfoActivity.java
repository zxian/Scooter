package com.xian.scooter.module.stores;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.StoreListBean;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class StoresInfoActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;

    private String storeId;

    @Override
    protected void handleIntent(Intent intent) {
        storeId=intent.getStringExtra("storeId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_stores_info;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("门店信息");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        getStoreByid(storeId);
    }

    /**
     * 按照门店ID获取门店详情
     */
    private void getStoreByid(String storeId) {

        ApiRequest.getInstance().get(HttpURL.STORE_BYID.replace("{storeId}", storeId), new DefineCallback<StoreListBean>() {
            @Override
            public void onMyResponse(SimpleResponse<StoreListBean, HttpEntity> response) {
                if (response.isSucceed()) {
                    StoreListBean storeIfo = response.succeed();
                    if (storeIfo!=null){
                        String backdrop_url = storeIfo.getBackdrop_url();
                        String logo = storeIfo.getLogo();
                        Glide.with(mActivity)
                                .load(backdrop_url)
                                .into(ivBg);

                        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                        options.bitmapTransform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));
                        Glide.with(mActivity)
                                .load(logo)
                                .apply(options)
                                .into(ivLogo);

                        tvName.setText(storeIfo.getName());
                        tvLocation.setText(storeIfo.getArea());
                        tvAddress.setText(storeIfo.getAddress());
                        tvIntroduce.setText(storeIfo.getRemark());
                        tvPhone.setText(storeIfo.getPhone());
                        tvPhone.setText(storeIfo.getPhone());

                    }
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });

    }

    @OnClick({R.id.rl_location, R.id.rl_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_location:
                break;
            case R.id.rl_pwd:
                break;
        }
    }
}
