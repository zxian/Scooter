package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kzz.dialoglibraries.dialog.DialogFragmentBottom;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.NavBean;
import com.xian.scooter.bean.StoreListBean;
import com.xian.scooter.beanpar.storeEditPar;
import com.xian.scooter.contant.Config;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.activity.ForgotPasswordActivity;
import com.xian.scooter.module.map.LocationActivity;
import com.xian.scooter.module.map.MapLocationPositionActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.PicSelectUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.xian.scooter.utils.UploadPicturesUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.et_introduce)
    EditText etIntroduce;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;

    private String storeId;
    private DialogFragmentBottom dialogBottom;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> pathList = new ArrayList<>();
    private String logoPath;

    private MyHandler handler;
    private NavBean navInfo;

    private static class MyHandler extends Handler {
        private WeakReference<StoresInfoActivity> mWeakReference;

        public MyHandler(StoresInfoActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StoresInfoActivity activity = mWeakReference.get();

            switch (msg.what) {
                case 1:
                    activity.pathList = (List<String>) msg.obj;
                    if (activity.pathList != null && activity.pathList.size() > 0) {
                        activity.logoPath = activity.pathList.get(0);
                        Glide.with(activity).load(activity.pathList.get(0)).into(activity.ivBg);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void handleIntent(Intent intent) {
        storeId = intent.getStringExtra("storeId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_stores_info;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("门店介绍");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        titleBarView.setRightText("保存");
        titleBarView.setRightOnClickListener(view -> {
            String introduce = etIntroduce.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(introduce)) {
                ToastUtils.showToast("请输入门店介绍");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showToast("请输入热线电话");
                return;
            }
            if (TextUtils.isEmpty(logoPath)) {
                ToastUtils.showToast("请上传门店背景");
                return;
            }
            getStoreEdit(logoPath, phone, introduce);

        });
        handler = new MyHandler(StoresInfoActivity.this);
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
                    if (storeIfo != null) {
                        String backdrop_url = storeIfo.getBackdrop_url();
                        logoPath = storeIfo.getLogo();
                        Glide.with(mActivity)
                                .load(backdrop_url)
                                .into(ivBg);

                        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                        options.bitmapTransform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));
                        Glide.with(mActivity)
                                .load(logoPath)
                                .apply(options)
                                .into(ivLogo);

                        tvName.setText(storeIfo.getName());
                        tvLocation.setText(storeIfo.getArea());
                        tvAddress.setText(storeIfo.getAddress());
                        etIntroduce.setText(storeIfo.getRemark());
                        etPhone.setText(storeIfo.getPhone());


                        navInfo = new NavBean();

                        String latitude = storeIfo.getLatitude();
                        if (!TextUtils.isEmpty(latitude)){
                            navInfo.setLatitude(Double.parseDouble(latitude));
                        }
                        String longitude = storeIfo.getLongitude();
                        if (!TextUtils.isEmpty(longitude)){
                            navInfo.setLongitude(Double.parseDouble(longitude));
                        }
                        navInfo.setAddress(storeIfo.getAddress());
                    }
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });

    }

    /**
     * 编辑
     */
    private void getStoreEdit(String url, String phone, String remark) {
        storeEditPar par = new storeEditPar();
        par.setBackdrop_url(url);
        par.setCreate_user_id(UserManager.getInstance().getUserId());
        par.setId(storeId);
        par.setPhone(phone);
        par.setRemark(remark);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.STORE_EDIT, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("保存成功！");
                    finish();
                } else {
                    if (response.failed() != null) {
                        ToastUtils.showToast(response.failed().getMessage());
                    } else {
                        ToastUtils.showToast("保存失败！");
                    }
                }
            }
        });

    }

    /**
     * 文件上传
     *
     * @param file 文件
     */
    private void fileUpload(File file) {
        UploadPicturesUtils.getInstance().updateImage(file, handler);
    }

    @OnClick({R.id.rl_location, R.id.iv_bg, R.id.rl_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_location:
                Intent intent = new Intent(mActivity, MapLocationPositionActivity.class);
                if (navInfo != null) {
                    intent.putExtra("nav_info", navInfo);
                }
                startActivity(intent);
                break;
            case R.id.iv_bg:
                showCameraDialog();
                break;
            case R.id.rl_pwd:
                intent = new Intent(mActivity, ForgotPasswordActivity.class);
                intent.putExtra("type",2);//1 忘记密码，2 修改密码
                startActivity(intent);
                break;
        }
    }

    /**
     * 拍照弹窗
     */
    private void showCameraDialog() {
        DialogFragmentBottom.Builder builder = new DialogFragmentBottom.Builder(mActivity);
        dialogBottom = builder.setAddViewId(R.layout.dialog_select)
                .setIsVisitCancel(false)
                .setDialogSetDateInterface(inflaterView -> {
                    List<String> listStr = new ArrayList<>();
                    listStr.add("拍照");
                    listStr.add("从相册上传");
                    ListView listView = inflaterView.findViewById(R.id.lv_dialog_select_listview);
                    listView.setAdapter(new CommonLvAdapter<String>(mActivity, R.layout.dialog_select_listview_item, listStr) {
                        @Override
                        protected void convert(ViewHolderLv viewHolder, String item, int position) {
                            viewHolder.setText(R.id.tv_dialog_select_item, listStr.get(position));
                        }
                    });
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_dialog_select_cancel);
                    tvCancel.setOnClickListener(v -> dialogBottom.dismiss());
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        if (position == 0) {//拍照
                            PicSelectUtils.startCamera(mActivity, true, selectList);
                        } else if (position == 1) {//从相册上传
                            PicSelectUtils.startPhoto(mActivity, Config.maxSel, PictureConfig.MULTIPLE, true, false, selectList);
                        }
                        dialogBottom.dismiss();
                    });

                }).build();

        dialogBottom.show(getSupportFragmentManager(), "FeedBackActivity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    List<String> pathList = new ArrayList<>();
                    if (selectList != null && selectList.size() > 0) {
                        //上传图片
                        pathList.clear();
                        for (int i = 0; i < selectList.size(); i++) {
                            // 1.media.getPath(); 为原图path
                            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                            if (selectList.get(i).isCompressed()) {
                                pathList.add(selectList.get(i).getCompressPath());
                            } else {
                                pathList.add(selectList.get(i).getPath());
                            }
                        }

                    }
                    String path = pathList.get(0);
                    fileUpload(new File(path));
                    break;
            }
        }
    }
}
