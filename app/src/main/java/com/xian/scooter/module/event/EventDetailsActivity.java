package com.xian.scooter.module.event;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.EventDetailsBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.yanzhenjie.kalle.simple.SimpleResponse;


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

    @Override
    protected void handleIntent(Intent intent) {
        id = intent.getStringExtra("id");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void init() {
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
                    EventDetailsBean eventDetailsBean = response.succeed();
                    if (eventDetailsBean!=null){
                        Glide.with(mActivity)
                                .load(eventDetailsBean.getPosters_url())
                                .into(ivPicture);
                        tvTitle.setText(eventDetailsBean.getCompetition_name());
                        tvTime.setText("比赛时间："+eventDetailsBean.getOfficial_time());
                        tvAddress.setText(eventDetailsBean.getAddress());
                        tvContent.setText(eventDetailsBean.getRemark());
                        String personnel_number = eventDetailsBean.getPersonnel_number();
                        String is_display = eventDetailsBean.getIs_display();//显示人员数量：0，不显示，1、显示
                        if ("1".equals(is_display)) {
                            tvNumber.setText("已报名：" +personnel_number+"人");
                        }

                        String competition_state = eventDetailsBean.getCompetition_state();
                        //赛事状态：1、待审核，2、审核未通过，3、已通过，4、取消中，5、取消失败，6、已取消
                        switch (competition_state){
                            case "1":
                                break;
                            case "2":
                                tvBtn1.setText("失败原因");
                                break;
                            case "3":

                                break;
                            case "4":
                                break;
                            case "5":
                                tvBtn1.setText("失败原因");
                                break;
                            case "6":
                                break;
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
                break;
            case R.id.tv_btn_2:
                break;
            case R.id.tv_btn_3:
                break;
        }
    }
}
