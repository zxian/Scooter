package com.xian.scooter.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.xian.scooter.beanpar.StoreAddPar;
import com.xian.scooter.contant.Config;
import com.xian.scooter.module.map.LocationActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.BasePopupView;
import com.xian.scooter.utils.PicSelectUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.xian.scooter.utils.UploadPicturesUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
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

    private List<String> pathList = new ArrayList<>();
    private MyHandler handler;
    private DialogFragmentBottom dialogBottom;
    private int selectPosition = 0;

    private List<LocalMedia> selectList = new ArrayList<>();
    private int isUpLoad = 0;//1 上传log，2 身份证人像面， 3 身份证国徽面 4 营业执照
    private String logoPath;
    private String portraitPath;
    private String emblemPath;
    private String picturePath;
    private int type;
    private String userId;
    private String area;

    private static class MyHandler extends Handler {
        private WeakReference<RegisterStoresActivity> mWeakReference;

        public MyHandler(RegisterStoresActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RegisterStoresActivity activity = mWeakReference.get();
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

            switch (msg.what) {
                case 1:
                    activity.pathList = (List<String>) msg.obj;
                    if (activity.pathList != null && activity.pathList.size() > 0) {
                        switch (activity.isUpLoad) {
                            case 1:
                                activity.logoPath = activity.pathList.get(0);
                                Glide.with(activity)
                                        .load(activity.pathList.get(0))
                                        .apply(options)
                                        .into(activity.ivLogo);
                                break;
                            case 2:
                                activity.portraitPath = activity.pathList.get(0);
                                Glide.with(activity)
                                        .load(activity.pathList.get(0))
                                        .apply(options)
                                        .into(activity.ivPortrait);
                                break;
                            case 3:
                                activity.emblemPath = activity.pathList.get(0);
                                Glide.with(activity)
                                        .load(activity.pathList.get(0))
                                        .apply(options)
                                        .into(activity.ivEmblem);
                                break;
                            case 4:
                                activity.picturePath = activity.pathList.get(0);
                                Glide.with(activity)
                                        .load(activity.pathList.get(0))
                                        .apply(options)
                                        .into(activity.ivPicture);
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void handleIntent(Intent intent) {
        userId = intent.getStringExtra("userId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register_stores;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("成为门店");
        titleBarView.setLeftOnClickListener(view -> finish());
        handler = new MyHandler(RegisterStoresActivity.this);
    }

    /**
     * 文件上传
     *
     * @param file 文件
     */
    private void fileUpload(File file) {
        UploadPicturesUtils.getInstance().updateImage(file, handler);
    }

    /**
     * 注册门店
     *
     * @param address          地址
     * @param area             位置
     * @param backdrop_url     门店背景图
     * @param business_license 营业执照
     * @param card_national    身份证国徽面
     * @param card_portrait    身份证人像面
     * @param code             社会信用代码
     * @param create_user_id   门店创建人ID
     *                         //     * @param id               门店id
     * @param id_card          负责人身份证号码
     * @param level            等级
     * @param logo             logo
     * @param manage_name      负责人姓名
     * @param name             名称
     * @param phone            手机
     * @param remark           介绍
     * @param type             类型，：0：个人，1：企业
     */
    private void storeAdd(String address, String area, String backdrop_url, String business_license,
                          String card_national, String card_portrait, String code,
                          String create_user_id, String id_card, String level, String logo,
                          String manage_name, String name, String phone, String remark, String type) {
        StoreAddPar par = new StoreAddPar();
        par.setAddress(address);
        par.setArea(area);
        par.setBackdrop_url(backdrop_url);
        par.setBusiness_license(business_license);
        par.setCard_national(card_national);
        par.setCard_portrait(card_portrait);
        par.setCode(code);
        par.setCreate_user_id(create_user_id);
        par.setId_card(id_card);
        par.setLevel(level);
        par.setLogo(logo);
        par.setManage_name(manage_name);
        par.setName(name);
        par.setPhone(phone);
        par.setRemark(remark);
        par.setType(type);

        par.setSign();
        ApiRequest.getInstance().post(HttpURL.STORE_ADD, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    startActivity(new Intent(mActivity, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showToast(response.failed().getMessage());
                }
            }
        });
    }

    @OnClick({R.id.iv_logo, R.id.rl_location, R.id.rl_type, R.id.iv_portrait, R.id.iv_emblem, R.id.iv_picture, R.id.tv_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                isUpLoad = 1;
                showCameraDialog();
                break;
            case R.id.rl_location:
                startActivityForResult(new Intent(mActivity, LocationActivity.class), 1000);
                break;
            case R.id.rl_type:
                List<String> typeList = Arrays.asList(getResources().getStringArray(R.array.stores_type));
                showAllType(typeList);
                break;
            case R.id.iv_portrait:
                isUpLoad = 2;
                showCameraDialog();
                break;
            case R.id.iv_emblem:
                isUpLoad = 3;
                showCameraDialog();
                break;
            case R.id.iv_picture:
                isUpLoad = 4;
                showCameraDialog();
                break;
            case R.id.tv_apply:
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String introduce = etIntroduce.getText().toString().trim();
                String headName = etHeadName.getText().toString().trim();
                String headNumber = etHeadNumber.getText().toString().trim();
                String code = etCode.getText().toString().trim();

                storeAdd(address, area, "", picturePath,
                        emblemPath, portraitPath, code,
                        userId, headNumber, "", logoPath,
                        headName, name, "", introduce, type + "");
                break;
        }
    }

    /**
     * 弹窗筛选框
     */
    private void showAllType(List<String> typeList) {
        BasePopupView popupWindow = new BasePopupView(mActivity, tvType, typeList, selectPosition,
                new Handler(msg -> {
                    switch (msg.what) {
                        case 2:
                            Bundle bundle = msg.getData();
                            selectPosition = bundle.getInt("selectPosition");
                            type = selectPosition;
                            tvType.setText(typeList.get(selectPosition));
                            break;
                    }
                    return false;
                }));
        popupWindow.create();
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
                case 1000:
                    if (resultCode == 2000) {
                        PoiItem poiItem = data.getParcelableExtra("PoiItem");
                        if (poiItem != null) {
                            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                            area = latLonPoint.getLatitude()+","+latLonPoint.getLongitude();
                        }
                    }
                    break;
            }


        }
    }

}
