package com.xian.scooter.module.stores;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.kzz.dialoglibraries.dialog.DialogFragmentBottom;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.CourseAddPar;
import com.xian.scooter.beanpar.CourseSendPar;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.event.EventArrangeTypeActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CourseGivingActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ev_day)
    EditText evDay;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_giving)
    TextView tvGiving;

    private static final int TYPE_REQUEST_CODE = 100;
    private DialogFragmentBottom dialogBottom;
    private String typeId;
    private String typeName;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_course_giving;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("赠送课程包");
        titleBarView.setLeftOnClickListener(view -> finish());
    }
    /**
     *
     * 赠送课程包接口
     *
     * @param acount 用户名
     * @param num 课程包次数
     * @param packageId 课程包id
     * @param packageName 课程包名
     * @param valid_day 剩余有效天数
     */
    private void getPackageSaveOrUpdate( String acount,String num, String packageId, String packageName, String valid_day) {

        CourseSendPar pas = new CourseSendPar();
        pas.setAccount(acount);
        pas.setNum(num);
        pas.setPackage_id(packageId);
        pas.setPackage_name(packageName);
        pas.setStore_id(UserManager.getInstance().getStoreId());
        pas.setValid_day(valid_day);
        pas.setSign();

        ApiRequest.getInstance().post(HttpURL.PACKAGE_SEND, pas, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("增赠送成功");
                    finish();
                } else {
                    if (response.failed()!=null){
                        ToastUtils.showToast(response.failed().getMessage());
                    }else {
                        ToastUtils.showToast("赠送失败");
                    }
                }

            }
        });
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
            }
        }
    }


    @OnClick({R.id.tv_type, R.id.tv_giving})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                Intent intent = new Intent(mActivity, CourseTypeActivity.class);
                startActivityForResult(intent, TYPE_REQUEST_CODE);
                break;
            case R.id.tv_giving:

                String phone = etPhone.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String day = evDay.getText().toString().trim();
                String number = etNumber.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("请输入用户手机号！");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast("请输入课程包名！");
                    return;
                }
                if (TextUtils.isEmpty(typeId)) {
                    ToastUtils.showToast("请选择适用类型！");
                    return;
                }
                if (TextUtils.isEmpty(day)) {
                    ToastUtils.showToast("请输入有效期！");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    ToastUtils.showToast("请输入课程次数！");
                    return;
                }

                getPackageSaveOrUpdate(phone,number,typeId, name, day);

                break;
        }
    }
}
