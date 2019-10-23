package com.xian.scooter.module.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bit.commonView.CustomRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.utils.TitleBarView;

import java.util.Calendar;
import java.util.Date;

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
    private String time;
    private String collectTime;
    private String competitionId;
    private String[] typeId;

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

    @OnClick({R.id.tv_type, R.id.tv_places, R.id.tv_time, R.id.tv_time_1, R.id.tv_promotion, R.id.tv_number, R.id.tv_equipment, R.id.tv_add, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                Intent intent = new Intent(mActivity, EventArrangeTypeActivity.class);
                intent.putExtra("competitionId",competitionId);
                startActivityForResult(intent,TYPE_REQUEST_CODE);
                break;
            case R.id.tv_places:
                break;
            case R.id.tv_time:
                onMonthDayTimePicker(1);
                break;
            case R.id.tv_time_1:
                onMonthDayTimePicker(2);
                break;
            case R.id.tv_promotion:
                break;
            case R.id.tv_number:
                break;
            case R.id.tv_equipment:
                break;
            case R.id.tv_add:
                break;
            case R.id.tv_btn:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case TYPE_REQUEST_CODE:
                    if (resultCode==RESULT_OK){
                        typeId =data.getStringArrayExtra("typeId");
                        tvType.setText("已设置");
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
            String date = year + "-" + month + "-" + day + " " + hour + ":" + minute+":00";
            switch (timeType){
                case 1://比赛时间
                    time = date;
                    tvTime.setText(date);
                    break;
                case 2://检录时间
                    collectTime = date;
                    tvTime1.setText(date);
                    break;
            }

        });

        picker.show();
    }
}
