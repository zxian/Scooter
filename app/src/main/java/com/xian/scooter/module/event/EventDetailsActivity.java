package com.xian.scooter.module.event;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzz.dialoglibraries.dialog.DialogCreate;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.EventDetailsBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;


import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class EventDetailsActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_btn_1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn_2)
    TextView tvBtn2;
    @BindView(R.id.tv_btn_3)
    TextView tvBtn3;


    private String id;
    private long time;
    private String competition_state;
    private DialogCreate mDialogCreate;
    private EventDetailsBean eventDetailsBean;

    @Override
    protected void handleIntent(Intent intent) {
        id = intent.getStringExtra("id");
        time = intent.getLongExtra("time",0);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赛事详情");
        titleBarView.setLeftOnClickListener(view1 -> finish());
        getCompetitionDetails(id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCompetitionDetails(id);
    }

    /**
     *
     * 按照ID获取详情
     *
     * @param id  赛事id
     */
    private void getCompetitionDetails( String id) {
        ApiRequest.getInstance().get(HttpURL.COMPETITION_DETAILS.replace("{id}", id),
                new DefineCallback<EventDetailsBean>() {
            @Override
            public void onMyResponse(SimpleResponse<EventDetailsBean, HttpEntity> response) {
                if (response.isSucceed()) {

                    if ( response.succeed()!=null){
                        eventDetailsBean = response.succeed();
                        Glide.with(mActivity)
                                .load(eventDetailsBean.getPosters_url())
                                .into(ivLogo);
                        tvTitle.setText(eventDetailsBean.getCompetition_name());
                        tvTime.setText("比赛时间："+ eventDetailsBean.getOfficial_time());
                        tvAddress.setText(eventDetailsBean.getAddress());
                        tvContent.setText(eventDetailsBean.getRemark());
                        int personnel_number = eventDetailsBean.getPersonnel_number();
                        String is_display = eventDetailsBean.getIs_display();//显示人员数量：0，不显示，1、显示
                        if ("1".equals(is_display)) {
                            tvNumber.setText("已报名：" +personnel_number+"人");
                        }
                        long start_time = TimeUtils.getStringToDate(eventDetailsBean.getStart_time(), "yyyy-MM-dd HH:mm:ss");//报名开始时间
                        long end_time = TimeUtils.getStringToDate(eventDetailsBean.getEnd_time(), "yyyy-MM-dd HH:mm:ss");//报名结束时间
                        competition_state = eventDetailsBean.getCompetition_state();
                        //赛事状态：1、待审核，2、审核未通过，3、已通过，4、取消中，5、取消失败，6、已取消
                        if (!TextUtils.isEmpty(competition_state)) {
                            switch (competition_state) {
                                case "2":
                                    tvBtn1.setVisibility(View.VISIBLE);
                                    tvBtn1.setText("审核失败原因");
                                    break;
                                case "5":
                                    tvBtn1.setVisibility(View.VISIBLE);
                                    tvBtn1.setText("取消失败原因");
                                    break;
                                    default:
                                        if (time!=0) {
                                            if (time >= start_time && time <= end_time) {
                                                tvBtn2.setVisibility(View.VISIBLE);
                                                tvBtn3.setVisibility(View.VISIBLE);
                                                tvBtn2.setText("报名记录");
                                                tvBtn3.setText("取消赛事");
                                            }
                                        }
                                        break;
                            }
                        }else {
                            if (time!=0) {
                            if (time >= start_time && time <= end_time) {
                                tvBtn2.setVisibility(View.VISIBLE);
                                tvBtn3.setVisibility(View.VISIBLE);
                                tvBtn2.setText("报名记录");
                                tvBtn3.setText("取消赛事");
                            }
                        }

                        }
                    }
                }
            }


        });
    }
    @OnClick({R.id.tv_btn_1, R.id.tv_btn_2, R.id.tv_btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_1:
                DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
                mDialogCreate = builder
                        .setAddViewId(R.layout.dialog_ok_cancel)
                        .setIsHasCloseView(false)
                        .setDialogSetDateInterface(inflaterView -> {
                            TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                            TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                            TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                            TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                            tvTitle.setText("失败原因");
                            String remark="";
                            if ("2".equals((competition_state))){//2、审核未通过，5、取消失败，
                                if (!TextUtils.isEmpty(eventDetailsBean.getAudit_failed_remark())){
                                    remark = eventDetailsBean.getAudit_failed_remark();
                                }else if (!TextUtils.isEmpty(eventDetailsBean.getCancel_failed_remark())){
                                    remark = eventDetailsBean.getCancel_failed_remark();
                                }
                            }else {
                                remark = eventDetailsBean.getCancel_remark();
                            }
                            tvMsg.setText(remark);

                            tvConfirm.setText("编辑");
                            tvConfirm.setOnClickListener(v -> {
                                mDialogCreate.dismiss();
//                                getCommunityDocDelete(position);
                            });
                            tvCancel.setOnClickListener(v -> mDialogCreate.dismiss());
                        })
                        .build();
                mDialogCreate.showSingle();
                break;
            case R.id.tv_btn_2:
                break;
            case R.id.tv_btn_3:
                startActivity(new Intent(mActivity,EventCancelActivity.class));
                break;
        }
    }

}
