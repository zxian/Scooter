package com.xian.scooter.module.stores;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.FinancialBean;
import com.xian.scooter.bean.PageBean;
import com.xian.scooter.beanpar.BillAllPar;
import com.xian.scooter.beanpar.EventPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.adapter.FinancialAdapter;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.BasePopupView;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.bar.StatusBarUtil;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;
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

    private static int TOTAL_COUNTER = 0;//服务器端一共多少条数据
    private static int PAGE_INDEX = 1;//当前第几页
    private static final int PAGE_SIZE = 10;//每一页展示多少条数据
    private static int mCurrentCounter = 0;//已经获取到多少条数据了
    private FinancialAdapter adapter;
    private List<FinancialBean> list = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private int selectPosition = 0;
    private String billType="0";
    private String queryDate="";

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
        queryDate=TimeUtils.stampToDate(TimeUtils.getCurrentTime(),"yyyy-MM");//获取当前时间并转换
        initRecyclerView();
        onMyRefresh();
    }

    private void initRecyclerView() {
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                onMyRefresh();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    PAGE_INDEX++;
                    //网络请求获取列表数据
                    getWithdrawalBillall(billType, queryDate);
                } else {
                    recyclerView.refreshComplete(mCurrentCounter);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new FinancialAdapter(mActivity, R.layout.item_event, list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(mActivity, EventDetailsActivity.class);
//                intent.putExtra("id", adapter.getDatas().get(position).getId());
//                intent.putExtra("time",time);
//                startActivity(intent);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void onMyRefresh() {
        adapter.cleanData();
        mCurrentCounter = 0;
        PAGE_INDEX = 1;
        getWithdrawalBillall(billType, queryDate);
    }

    /**
     * 查询门店/用户账单记录
     * @param bill_type 账单类型（0、全部，1、赛事报名2、课程预约3、课程包购买4、提现记录）
     * @param query_date 查询日期（格式：yyyy-MM）
     */
    private void getWithdrawalBillall(String bill_type,String query_date) {
        BillAllPar par = new BillAllPar();
        par.setBill_type(bill_type);
        par.setId(UserManager.getInstance().getStoreId());
        par.setQuery_date(query_date);
        par.setQuery_type("2");//查询类型（1、用户，2、门店）
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.WITHDRAWAL_BILLALL, par, new DefineCallback<PageBean<EventBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<EventBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    if (response.succeed() != null) {
//                        TOTAL_COUNTER = response.succeed().getTotal();
//                        List<EventBean> list = response.succeed().getRecords();
//                        addItems(list);
                    }

                }
            }

            @Override
            public void onEnd() {
                recyclerView.refreshComplete(mCurrentCounter);
            }
        });
    }

    /**
     * 添加数据到列表
     *
     * @param list 数据列表
     */
    private void addItems(List<FinancialBean> list) {
        if (list.size() > 0 && adapter != null) {
            mCurrentCounter += list.size();
            adapter.updataItem(list);

        }
    }

    @OnClick({R.id.tv_withdrawal, R.id.tv_date, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_withdrawal:
                startActivity(new Intent(mActivity,WithdrawalActivity.class));
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
                            billType=selectPosition+"";
                            tvType.setText(typeList.get(selectPosition));
                            onMyRefresh();
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
        final DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        picker.setDateRangeStart(2000, 1);
        picker.setDateRangeEnd(toDay.get(Calendar.YEAR) + 10, 1);
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
        picker.setSelectedItem(toDay.get(Calendar.YEAR), toDay.get(Calendar.MONTH) + 1);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener((DatePicker.OnYearMonthPickListener) (year, month) -> {
            queryDate = year + "-" + month;
            tvDate.setText(queryDate);
            onMyRefresh();
        });
        picker.show();
    }


}
