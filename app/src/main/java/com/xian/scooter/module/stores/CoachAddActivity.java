package com.xian.scooter.module.stores;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bumptech.glide.Glide;
import com.kzz.dialoglibraries.dialog.DialogFragmentBottom;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.beanpar.CustAddPar;
import com.xian.scooter.beanpar.EventAddPar;
import com.xian.scooter.contant.Config;
import com.xian.scooter.module.event.AddInfoActivity;
import com.xian.scooter.module.event.EventAddActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.FileUtils;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoachAddActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private DialogFragmentBottom dialogBottom;
    private MyHandler handler;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> pathList = new ArrayList<>();
    private String logoPath;
    private static final int INFO_REQUEST_CODE = 100;
    private static final int LABEL_REQUEST_CODE = 200;
    private String content;
    private String label;
    private String state;
    private String storeId;
    private List<CustAddPar.VideosBean> videosList=new ArrayList<>();
    private CustAddPar.VideosBean videosBean = new CustAddPar.VideosBean();
    private int uploadType; //上传类型 1 照片 2 视频
    private String videoPath;
    private boolean isVideo=true;//是否是上传视频， false：缩略图


    private static class MyHandler extends Handler {
        private WeakReference<CoachAddActivity> mWeakReference;

        public MyHandler(CoachAddActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CoachAddActivity activity = mWeakReference.get();

            switch (msg.what) {
                case 1:
                    if (activity.uploadType==1) {
                        activity.pathList = (List<String>) msg.obj;
                        if (activity.pathList != null && activity.pathList.size() > 0) {
                            activity.logoPath = activity.pathList.get(0);
                            Glide.with(activity).load(activity.pathList.get(0)).into(activity.ivLogo);
                        }
                    }else if (activity.uploadType==2){
                        List<String> pathList = (List<String>) msg.obj;
                        if (activity.isVideo) {
                            if (pathList != null && pathList.size() > 0) {
                                activity.videosBean.setVideo_url(pathList.get(0));
                            }
                            activity.fileUpload(new File(activity.videoPath));
                            activity.isVideo=false;
                        }else {
                            if (pathList != null && pathList.size() > 0) {
                                activity.videosBean.setVideo_image_url(pathList.get(0));
                                Glide.with(activity).load(pathList.get(0)).into(activity.ivVideo);
                            }
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
        storeId =intent.getStringExtra("storeId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_coach_add;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("新增教练");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        handler = new MyHandler(CoachAddActivity.this);
    }

    /**
     * 新增User /教练
     * @param account 用户账号&手机号码
     * @param head_mage_url 用户头像url
     * @param name 名称
     * @param password 密码
     * @param remark 用户介绍
     * @param role_state 用户身份标识，0：普通用户，1、门店用户，2、教练用户
     * @param store_id 所属门店ID
     * @param store_state 教练门店状态
     * @param user_tag     用户标签
     * @param videosList 教练视频
     */
    private void getCustAdd(String account, String head_mage_url, String name, String password,
                                    String remark, String role_state, String store_id,
                                    String store_state, String user_tag, List<CustAddPar.VideosBean> videosList) {
        CustAddPar par = new CustAddPar();
        par.setAccount(account);
        par.setHead_mage_url(head_mage_url);
        par.setName(name);
        par.setPassword(password);
        par.setRemark(remark);
        par.setRole_state(role_state);
        par.setStore_id(store_id);
        par.setStore_state(store_state);
        par.setUser_tag(user_tag);
        par.setVideos(videosList);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.CUST_ADD, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提交成功！");
                    finish();
                }else {
                    if (response.failed()!=null){
                        ToastUtils.showToast(response.failed().getMessage());
                    }
                }
            }

        });

    }

    @OnClick({R.id.iv_logo, R.id.tv_info, R.id.tv_label, R.id.iv_video, R.id.tv_state, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                uploadType =1;
                showCameraDialog();
                break;
            case R.id.tv_info:
                Intent intent = new Intent(mActivity, AddInfoActivity.class);
                intent.putExtra("name","教练简介");
                startActivityForResult(intent,INFO_REQUEST_CODE);
                break;
            case R.id.tv_label:
                startActivityForResult(new Intent(mActivity,CoachLabelActivity.class),LABEL_REQUEST_CODE);
                break;
            case R.id.iv_video:
                uploadType =2;
                showVidoDialog();
                break;
            case R.id.tv_state:
                showStateDialog();
                break;
            case R.id.tv_submit:
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String account = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showToast("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    ToastUtils.showToast("密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(account)){
                    ToastUtils.showToast("手机号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(logoPath)){
                    ToastUtils.showToast("头像不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content)){
                    ToastUtils.showToast("教练简介不能为空");
                    return;
                }
                if (TextUtils.isEmpty(state)){
                    ToastUtils.showToast("请选择状态");
                    return;
                }
                if (TextUtils.isEmpty(label)){
                    ToastUtils.showToast("请选择标签");
                    return;
                }
                videosList.add(videosBean);
                getCustAdd(account, logoPath,name,pwd, content, "2", storeId, state,label, videosList);
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

    /**
     * 视频弹窗
     */
    private void showVidoDialog() {
        DialogFragmentBottom.Builder builder = new DialogFragmentBottom.Builder(mActivity);
        dialogBottom = builder.setAddViewId(R.layout.dialog_select)
                .setIsVisitCancel(false)
                .setDialogSetDateInterface(inflaterView -> {
                    List<String> listStr = new ArrayList<>();
                    listStr.add("从文件中上传");
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
                        PicSelectUtils.startVideo(mActivity, Config.maxSel, PictureConfig.MULTIPLE);
                        dialogBottom.dismiss();
                    });

                }).build();

        dialogBottom.show(getSupportFragmentManager(), "FeedBackActivity");
    }

    /**
     * 选择弹窗
     */
    private void showStateDialog() {
        DialogFragmentBottom.Builder builder = new DialogFragmentBottom.Builder(mActivity);
        dialogBottom = builder.setAddViewId(R.layout.dialog_select)
                .setIsVisitCancel(false)
                .setDialogSetDateInterface(inflaterView -> {
                    List<String> listStr = new ArrayList<>();
                    listStr.add("启用");
                    listStr.add("禁用");
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
                        state = String.valueOf(position+1);
                        tvState.setText(listStr.get(position));
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
                    if (uploadType==1) {
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
                    }else if (uploadType==2){
                        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                        List<String> pathList = new ArrayList<>();
                        if (localMedia != null && localMedia.size() > 0) {
                            //上传图片
                            pathList.clear();
                            for (int i = 0; i < localMedia.size(); i++) {
                                // 1.media.getPath(); 为原图path
                                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                                if (localMedia.get(i).isCompressed()) {
                                    pathList.add(localMedia.get(i).getCompressPath());
                                } else {
                                    pathList.add(localMedia.get(i).getPath());
                                }
                            }

                        }
                        String path = pathList.get(0);

                        //获取本地视频缩略图
                        MediaMetadataRetriever media = new MediaMetadataRetriever();
                        media.setDataSource(path);
                        Bitmap bitmap = media.getFrameAtTime();
                        videoPath = FileUtils.saveToLocal(bitmap);
                        fileUpload(new File(path));

                    }
                    break;
                case INFO_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        content = data.getStringExtra("content");
                        tvInfo.setText(content);
                    }
                    break;
                case LABEL_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        label = data.getStringExtra("label");
                        tvLabel.setText(label);
                    }
                    break;
            }
        }
    }
    /**
     * 文件上传
     *
     * @param file 文件
     */
    private void fileUpload(File file) {
        UploadPicturesUtils.getInstance().updateImage(file, handler);
    }
}
