package com.xian.scooter.module.stores;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.bean.PageBean;

import com.xian.scooter.beanpar.WithdrawalExtractPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_amount)
    EditText etAmount;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("提现");
        titleBarView.setLeftOnClickListener(view -> finish());
    }

    /**
     *
     * 门店新增提现
     *
     * @param amount 提现金额
     * @param withdrawal_account 收款账号
     * @param withdrawal_channel 提现通道
     */
    private void getWithdrawalExtract(String amount,String withdrawal_account,String withdrawal_channel) {
        WithdrawalExtractPar par = new WithdrawalExtractPar();
        par.setAmount(amount);
        par.setStore_id(UserManager.getInstance().getStoreId());//门店ID
        par.setUser_id(UserManager.getInstance().getUserId());//提现人
        par.setWithdrawal_account(withdrawal_account);
        par.setWithdrawal_channel(withdrawal_channel);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.WITHDRAWAL_EXTRACT, par, new DefineCallback<PageBean<EventBean>>() {
            @Override
            public void onMyResponse(SimpleResponse<PageBean<EventBean>, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提现成功！");
                }else {
                    if (response.failed()!=null){
                        ToastUtils.showToast(response.failed().getMessage());
                    }else {
                        ToastUtils.showToast("提现失败！");
                    }
                }
            }

        });
    }

    @OnClick({R.id.tv_all, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                break;
            case R.id.tv_btn:
                String account = etAccount.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();
                if (TextUtils.isEmpty(account)){
                    ToastUtils.showToast("请输入提现账号");
                    return;
                }
                if (TextUtils.isEmpty(amount)){
                    ToastUtils.showToast("请输入提现金额");
                    return;
                }
                getWithdrawalExtract(amount,account,"支付宝");
                break;
        }
    }
}
