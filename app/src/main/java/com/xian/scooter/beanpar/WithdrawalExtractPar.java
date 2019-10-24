package com.xian.scooter.beanpar;

import com.xian.scooter.app.BaseApplication;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.SignUtils;

public class WithdrawalExtractPar {
    private String amount;
    private String store_id;
    private String user_id;
    private String withdrawal_account;
    private String withdrawal_channel;

    public void setSign() {
        String sign = "amount=" + amount +"&store_id=" + store_id +"&user_id=" + user_id
                +"&withdrawal_account=" + withdrawal_account  +"&withdrawal_channel=" + withdrawal_channel + "&key="+ Config.KEY;
        BaseApplication.getInstance().getKalleConfig()
                .setHeader("sign", SignUtils.getInstance().getMd5Value(sign));
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWithdrawal_account() {
        return withdrawal_account;
    }

    public void setWithdrawal_account(String withdrawal_account) {
        this.withdrawal_account = withdrawal_account;
    }

    public String getWithdrawal_channel() {
        return withdrawal_channel;
    }

    public void setWithdrawal_channel(String withdrawal_channel) {
        this.withdrawal_channel = withdrawal_channel;
    }
}
