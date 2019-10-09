package com.xian.scooter.module.event;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

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
import com.xian.scooter.contant.Config;
import com.xian.scooter.module.activity.RegisterStoresActivity;
import com.xian.scooter.utils.PicSelectUtils;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.UploadPicturesUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DateTimePicker;

public class EventAddActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.sw_select)
    Switch swSelect;
    @BindView(R.id.tv_setup)
    TextView tvSetup;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    private static final int INFO_REQUEST_CODE = 100;
    private static final int SETUP_REQUEST_CODE = 200;
    private long time;
    private long startTime;
    private long endTime;


    private MyHandler handler;
    private DialogFragmentBottom dialogBottom;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> pathList = new ArrayList<>();
    private String logoPath;
    private String content;

    private static class MyHandler extends Handler {
        private WeakReference<EventAddActivity> mWeakReference;

        public MyHandler(EventAddActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EventAddActivity activity = mWeakReference.get();

            switch (msg.what) {
                case 1:
                    activity.pathList = (List<String>) msg.obj;
                    if (activity.pathList != null && activity.pathList.size() > 0) {
                        activity.logoPath = activity.pathList.get(0);
                        Glide.with(activity).load(activity.pathList.get(0)).into(activity.ivLogo);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_add;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("申请赛事");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        handler = new MyHandler(EventAddActivity.this);
    }
    /**
     * 文件上传
     *
     * @param file 文件
     */
    private void fileUpload(File file) {
        UploadPicturesUtils.getInstance().updateImage(file, handler);
    }

    @OnClick({R.id.iv_logo, R.id.tv_time, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_info, R.id.tv_setup, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                showCameraDialog();
                break;
            case R.id.tv_time:
                onMonthDayTimePicker(1);
                break;
            case R.id.tv_start_time:
                onMonthDayTimePicker(2);
                break;
            case R.id.tv_end_time:
                onMonthDayTimePicker(3);
                break;
            case R.id.tv_info:
                startActivityForResult(new Intent(mActivity,EventAddInfoActivity.class),INFO_REQUEST_CODE);
                break;
            case R.id.tv_setup:
                startActivityForResult(new Intent(mActivity,EventAddSetupActivity.class),SETUP_REQUEST_CODE);
                break;
            case R.id.tv_btn:
                break;
        }
    }


    /**
     * 时间选择
     *
     * @param timeType
     */
    public void onMonthDayTimePicker(int timeType) {
        Calendar toDay = Calendar.getInstance();
        final DateTimePicker picker = new DateTimePicker(this, DateTimePicker.YEAR_MONTH_DAY,DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2000, 1, 1);
        picker.setDateRangeEnd(toDay.get(Calendar.YEAR) + 10, 1, 1);
        picker.setCanLinkage(true);
//        picker.setTitleTextColor(getResources().getColor(R.color.white));
        picker.setTitleText("");
        picker.setCanLoop(false);
        picker.setLabel("-", "-", "","：","");//设置年月日的单位
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setTopLineHeight(0);//顶部标题栏下划线高度
        picker.setCancelTextColor(getResources().getColor(R.color.gray_99));//顶部取消按钮文字颜色
        picker.setCancelTextSize(16);
        picker.setSubmitText("完成");//设置顶部标题栏确定按钮文字
        picker.setSubmitTextColor(getResources().getColor(R.color.theme));//顶部确定按钮文字颜色
        picker.setSubmitTextSize(16);

        //中间滚动项文字颜色
        picker.setSelectedTextColor(getResources().getColor(R.color.black_33));
        picker.setLineVisible(false);//设置分隔线是否可见
        Date toDate = new Date();
        int minutes = toDate.getMinutes();
        if (minutes == 0)
            minutes++;
        picker.setSelectedItem(toDay.get(Calendar.YEAR), toDay.get(Calendar.MONTH) + 1,
                toDay.get(Calendar.DAY_OF_MONTH), toDate.getHours(), minutes);
        picker.setWeightEnable(true);
        picker.setOnDateTimePickListener((DateTimePicker.OnYearMonthDayTimePickListener) (year, month, day, hour, minute) -> {
            String date = year + "-" + month + "-" + day + " " + hour + "：" + minute;
            switch (timeType){
                case 1://比赛时间
                    time = TimeUtils.getStringToDateHM(date);
                    tvTime.setText(date);
                    break;
                case 2://报名开始时间
                    startTime = TimeUtils.getStringToDateHM(date);
                    tvStartTime.setText(date);
                    break;
                case 3://报名结束时间
                    endTime = TimeUtils.getStringToDateHM(date);
                    tvEndTime.setText(date);
                    break;
            }

        });

        picker.show();
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
                case INFO_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        content = data.getStringExtra("content");
                        tvInfo.setText(content);
                    }
                    break;
            }
        }
    }

}
