package com.xian.scooter.module.stores;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.commonView.CustomGridView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.bean.StoreListBean;
import com.xian.scooter.bean.UserInfoBean;
import com.xian.scooter.beanpar.StoreListPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.activity.MainActivity;
import com.xian.scooter.module.activity.RegisterStoresActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.SignUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class StoresFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_star)
    TextView tvStar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.gv_item)
    CustomGridView gvItem;

    public static List<Integer> list = new ArrayList<>();
    private CommonLvAdapter<Integer> itemAdapter;
    private String storeId;

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
        titleBarView.setIvRightImage(R.mipmap.ic_set_up);
        titleBarView.setRightOnClickListener(view1 -> {
            startActivity(new Intent(mActivity,SetUpActivity.class));
        });

        TypedArray array = mActivity.getResources().obtainTypedArray(R.array.item_res_img);

        for (int i=0,len=8; i<len; i++){
            int resourceId = array.getResourceId(i,0);
            list.add(resourceId);
        }
        array.recycle();
        itemAdapter = new CommonLvAdapter<Integer>(mActivity, R.layout.item_store,list) {
            @Override
            protected void convert(ViewHolderLv viewHolder, Integer resId, int position) {
                viewHolder.setImageResource(R.id.iv_picture, resId);
            }
        };

        gvItem.setAdapter(itemAdapter);
        gvItem.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position){
                case 0://教练
                    Intent intent = new Intent(mActivity, CoachActivity.class);
                    intent.putExtra("storeId", storeId);
                    startActivity(intent);
                    break;
                case 1://会员
                    startActivity(new Intent(mActivity,MembersActivity.class));
                    break;
                case 2://课程包
                    startActivity(new Intent(mActivity,CoursePackageActivity.class));
                    break;
                case 3://财务
                    startActivity(new Intent(mActivity,FinancialActivity.class));
                    break;
                case 4://订单
                    break;
                case 5://我的互动
                    break;
                case 6://门店信息
                    intent = new Intent(mActivity, StoresInfoActivity.class);
                    intent.putExtra("storeId", storeId);
                    startActivity(intent);
                    break;
                case 7://消息中心
                    startActivity(new Intent(mActivity,MessageActivity.class));
                    break;
            }
        });
        String userId = UserManager.getInstance().getUserId();
        if (!TextUtils.isEmpty(userId)) {
            getStoreList(userId);
        }
    }
    /**
     * 获取门店分页数据
     */
    private void getStoreList(String userId) {
        StoreListPar par = new StoreListPar();
        par.setUserId(userId);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.STORE_LIST.replace("{size}", "10")
                .replace("{current}", "1"), par, new DefineCallback<PageBean<StoreListBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<StoreListBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    PageBean<StoreListBean> succeed = response.succeed();
                    if (succeed!=null){
                        if (succeed.getRecords()!=null&&succeed.getRecords().size()>0){
                            List<StoreListBean> storeList = succeed.getRecords();
                            storeId = storeList.get(0).getId();
                            getStoreByid(storeId);
                        }
                    }
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });
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
                                .into(ivPicture);

                        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                        options.bitmapTransform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));
                        Glide.with(mActivity)
                                .load(logo)
                                .apply(options)
                                .into(ivLogo);
                        tvName.setText(storeIfo.getName());
                        tvContent.setText(storeIfo.getRemark());
//                        tvStar.setText(storeIfo.get());

                    }
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });

    }

}
