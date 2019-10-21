package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.BasePopupView;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.bar.StatusBarUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DateTimePicker;

public class FinancialActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_withdrawal)
    TextView tvWithdrawal;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private int selectPosition = 0;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setStatusBarColor(this, R.color.theme);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_financial;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("财务管理");
        titleBarView.setTvTitleTextColor(R.color.white);
        titleBarView.setLayoutTitleBg(R.color.theme);
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
    }

    @OnClick({R.id.tv_withdrawal, R.id.tv_date, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_withdrawal:
                break;
            case R.id.tv_date:
                onMonthDayTimePicker();
                break;
            case R.id.tv_type:
                List<String> typeList = Arrays.asList(getResources().getStringArray(R.array.financial_type));
                showAllType(typeList);
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
//                            switch (selectPosition) {
//                                case 0: //全部
//                                    unitType = 0;
//                                    break;
//                                case 1://企业类型
//                                    unitType = 1;
//                                    break;
//                                case 2://其他单位
//                                    unitType = 2;
//                                    break;
//                            }
                            tvType.setText(typeList.get(selectPosition));
//                            onMyRefresh();
                            break;
                    }
                    return false;
                }));
        popupWindow.create();
    }


    /**
     * 时间选择
     */
    public void onMonthDayTimePicker() {
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
            tvDate.setText(date);
        });
        picker.show();
    }


}
