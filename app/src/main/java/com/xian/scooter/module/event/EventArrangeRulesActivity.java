package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bit.commonView.CustomRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.beanpar.EventPlanPagePar;
import com.xian.scooter.beanpar.EventPlanSavePar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.BasePopupView;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DateTimePicker;

public class EventArrangeRulesActivity extends BaseActivity {
    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_places)
    TextView tvPlaces;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_1)
    TextView tvTime1;
    @BindView(R.id.tv_promotion)
    TextView tvPromotion;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_equipment)
    TextView tvEquipment;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    CustomRecyclerView recyclerView;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    private static final int TYPE_REQUEST_CODE = 100;
    private static final int PLAN_TYPE_REQUEST_CODE = 200;
    private static final int PERSON_TYPE_REQUEST_CODE = 300;
    private String officialTime;
    private String checkTime;
    private String competitionId;
    private String typeId;
    private int typePlaces = -1;
    private int typePromotion = -1;
    private int typeEquipment = -1;
    private String planId;
    private String planName;
    private String typeName;
    private List<String> addJoinsList = new ArrayList<>();
    private List<String> cancelJoinsList = new ArrayList<>();
    private String id;

    @Override
    protected void handleIntent(Intent intent) {
        competitionId = intent.getStringExtra("competitionId");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_arrange_rules;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("规则安排");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
    }

    /**
     * 新增OR编辑赛事场次信息
     *
     * @param addJoinsList    新增场次参与人员ID列表,无参请传[]
     * @param cancelJoinsList 删除场次参与人员ID列表,无参请传[]
     * @param checkTime       检录时间
     * @param setId           报名赛事类型ID
     * @param equipment       物联设备：0、无，1、有
     * @param id              新增时不传
     * @param name            赛事场次名称
     * @param officialTime    比赛时间
     * @param upgrade         是否进阶：0、否，1、是
     * @param upgradeId       进阶场次ID，（是否进阶为1时，进阶场次必填）
     * @param upgradeName     进阶场次名称
     * @param winNumber       优胜名额(比赛1-x名有奖励)
     */
    private void getCompetitionPlanPage(List<String> addJoinsList, List<String> cancelJoinsList,
                                        String checkTime, String setId, String equipment, String id,
                                        String name, String officialTime, String upgrade,
                                        String upgradeId, String upgradeName, String winNumber) {
        EventPlanSavePar par = new EventPlanSavePar();
        par.setAddJoins(addJoinsList);
        par.setCancelJoins(cancelJoinsList);
        par.setCheck_time(checkTime);
        par.setCompetition_id(competitionId);
        par.setCompetition_set_id(setId);
        par.setEquipment(equipment);
        if (!TextUtils.isEmpty(id)) {
            par.setId(id);
        }
        par.setName(name);
        par.setOfficial_time(officialTime);
        par.setStore_id(UserManager.getInstance().getStoreId());
        par.setUpgrade(upgrade);
        if ("1".equals(upgrade)) {
            par.setUpgrade_id(upgradeId);
            par.setUpgrade_name(upgradeName);
        }
        par.setWin_number(winNumber);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_PLAN_SAVE, par, new DefineCallback<String>() {
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

    @OnClick({R.id.tv_type, R.id.tv_places, R.id.tv_time, R.id.tv_time_1, R.id.tv_promotion, R.id.tv_number, R.id.tv_equipment, R.id.tv_add, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                Intent intent = new Intent(mActivity, EventArrangeTypeActivity.class);
                intent.putExtra("competitionId", competitionId);
                startActivityForResult(intent, TYPE_REQUEST_CODE);
                break;
            case R.id.tv_places:
                List<String> typeList = Arrays.asList(getResources().getStringArray(R.array.number_type));
                showAllType(typeList, typePlaces - 1, 1, tvPlaces);
                break;
            case R.id.tv_time:
                onMonthDayTimePicker(1);
                break;
            case R.id.tv_time_1:
                onMonthDayTimePicker(2);
                break;
            case R.id.tv_promotion:
                typeList = Arrays.asList(getResources().getStringArray(R.array.promotion_type));
                showAllType(typeList, typePromotion, 2, tvPromotion);
                break;
            case R.id.tv_number:
                intent = new Intent(mActivity, EventArrangePlanActivity.class);
                intent.putExtra("setId", typeId);
                intent.putExtra("name", typeName);
                startActivityForResult(intent, PLAN_TYPE_REQUEST_CODE);
                break;
            case R.id.tv_equipment:
                typeList = Arrays.asList(getResources().getStringArray(R.array.equipment_type));
                showAllType(typeList, typeEquipment, 3, tvEquipment);
                break;
            case R.id.tv_add:
                intent = new Intent(mActivity, EventArrangePersonActivity.class);
                intent.putExtra("competitionId", competitionId);
                startActivityForResult(intent, PERSON_TYPE_REQUEST_CODE);
                break;
            case R.id.tv_btn:
                String name = etName.getText().toString().trim();

                if (TextUtils.isEmpty(typeId)) {
                    ToastUtils.showToast("请选择报名类型");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast("请输入场次名称");
                    return;
                }
                if (typePlaces < 0) {
                    ToastUtils.showToast("请选择优胜名额");
                    return;
                }

                if (TextUtils.isEmpty(officialTime)) {
                    ToastUtils.showToast("请选择比赛时间");
                    return;
                }
                if (TextUtils.isEmpty(checkTime)) {
                    ToastUtils.showToast("请选择检录时间");
                    return;
                }
                if (typePromotion < 0) {
                    ToastUtils.showToast("请选择是否进阶");
                    return;
                }
                if (typePromotion == 1) {
                    if (TextUtils.isEmpty(planId)) {
                        ToastUtils.showToast("请选择进阶场次");
                        return;
                    }
                }
                if (typeEquipment < 0) {
                    ToastUtils.showToast("请选择物联设备");
                    return;
                }
                getCompetitionPlanPage(addJoinsList, cancelJoinsList,
                        checkTime, typeId, typeEquipment + "", id,
                        name, officialTime, typePromotion + "",
                        planId, planName, typePlaces + "");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case TYPE_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        typeId = data.getStringExtra("typeId");
                        typeName = data.getStringExtra("name");
                        tvType.setText(typeName);
                    }
                    break;
                case PLAN_TYPE_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        planId = data.getStringExtra("planId");
                        planName = data.getStringExtra("planName");
                        tvNumber.setText(planName);
                    }
                    break;
            }
        }
    }

    /**
     * 时间选择
     *
     * @param timeType
     */
    public void onMonthDayTimePicker(int timeType) {
        Calendar toDay = Calendar.getInstance();
        final DateTimePicker picker = new DateTimePicker(this, DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2000, 1, 1);
        picker.setDateRangeEnd(toDay.get(Calendar.YEAR) + 10, 1, 1);
        picker.setCanLinkage(true);
//        picker.setTitleTextColor(getResources().getColor(R.color.white));
        picker.setTitleText("");
        picker.setCanLoop(false);
        picker.setLabel("-", "-", "", "：", "");//设置年月日的单位
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
            String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
            switch (timeType) {
                case 1://比赛时间
                    officialTime = date;
                    tvTime.setText(date);
                    break;
                case 2://检录时间
                    checkTime = date;
                    tvTime1.setText(date);
                    break;
            }

        });

        picker.show();
    }


    /**
     * 弹窗筛选框
     *
     * @param typeList       item数据
     * @param selectPosition 当前选择position
     * @param type           弹窗类型
     */
    private void showAllType(List<String> typeList, final int selectPosition, int type, TextView view) {
        BasePopupView popupWindow = new BasePopupView(mActivity, view, typeList, selectPosition,
                new Handler(msg -> {
                    switch (msg.what) {
                        case 2:
                            Bundle bundle = msg.getData();
                            int position = bundle.getInt("selectPosition");
                            switch (type) {
                                case 1://优胜名额
                                    typePlaces = position + 1;
                                    tvPlaces.setText(typeList.get(position));
                                    break;
                                case 2://是否进阶
                                    typePromotion = position;
                                    tvPromotion.setText(typeList.get(position));
                                    break;
                                case 3://物联设备
                                    typeEquipment = position;
                                    tvEquipment.setText(typeList.get(position));
                                    break;
                            }
                            break;
                    }
                    return false;
                }));
        popupWindow.create();
    }
}
