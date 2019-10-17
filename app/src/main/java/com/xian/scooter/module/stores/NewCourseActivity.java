package com.xian.scooter.module.stores;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.NewCoursePas;
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

public class NewCourseActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.ev_name)
    EditText evName;
    @BindView(R.id.ev_period)
    EditText evPeriod;
    @BindView(R.id.ev_time)
    EditText evTime;
    @BindView(R.id.ev_yuan)
    EditText evYuan;
    @BindView(R.id.tv_new)
    TextView tvNew;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_course;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("新增课程包");
        titleBarView.setLeftOnClickListener(view -> finish());
    }


    private void getPackageSaveOrUpdate(String name, String state, String num, String price) {

        NewCoursePas pas = new NewCoursePas();
        pas.setName(name);
        pas.setState(state);
        pas.setNum(num);
        pas.setPrice(price);
        pas.setSign();

        ApiRequest.getInstance().post(HttpURL.PACKAGE_SAVEORUPDATE, pas, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                        ToastUtils.showToast("增新成功");
                        finish();
                    } else {
                        ToastUtils.showToast("增新失败");
                        return;
                    }

            }
        });
    }


    @OnClick(R.id.tv_new)
    public void onViewClicked() {

        String evname = evName.getText().toString().trim();
        String evperiod = evPeriod.getText().toString().trim();
        String evtime = evTime.getText().toString().trim();
        String evyuan = evYuan.getText().toString().trim();


        if (TextUtils.isEmpty(evname)) {
            ToastUtils.showToast("请输入课程包名！");
            return;
        } else if (TextUtils.isEmpty(evperiod)) {
            ToastUtils.showToast("请选择有效期！");
            return;
        } else if (TextUtils.isEmpty(evtime)) {
            ToastUtils.showToast("请输入课程次数！");
            return;
        } else if (TextUtils.isEmpty(evyuan)) {
            ToastUtils.showToast("请输入课程包价格！");
            return;
        }
        getPackageSaveOrUpdate(evname,evperiod,evtime,evyuan);
    }

}
