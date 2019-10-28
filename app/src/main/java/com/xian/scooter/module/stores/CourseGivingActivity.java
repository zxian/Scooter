package com.xian.scooter.module.stores;

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
    private DialogFragmentBottom dialogBottom;
    private String type;

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
     * @param num 课程包次数
     * @param price 课程包价格
     * @param state 课程包是否启用：0、未启用，1、已启用
     * @param valid_day 剩余有效天数
     */
    private void getPackageSaveOrUpdate( String acount,String num, String price, String state, String valid_day) {

        CourseSendPar pas = new CourseSendPar();
        pas.setAccount(acount);
        pas.setNum(num);
        pas.setPackage_id(price);
        pas.setPackage_name(state);
        pas.setStore_id(UserManager.getInstance().getStoreId());
        pas.setValid_day(valid_day);
        pas.setSign();

        ApiRequest.getInstance().post(HttpURL.PACKAGE_SEND, pas, new DefineCallback<String>() {
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

    @OnClick({R.id.tv_type, R.id.tv_giving})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                showStateDialog();
                break;
            case R.id.tv_giving:

                String phone = etPhone.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String day = evDay.getText().toString().trim();
                String number = etNumber.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast("请输入课程包名！");
                    return;
                } else if (TextUtils.isEmpty(day)) {
                    ToastUtils.showToast("请选择有效期！");
                    return;
                } else if (TextUtils.isEmpty(number)) {
                    ToastUtils.showToast("请输入课程次数！");
                    return;
                }

//                getPackageSaveOrUpdate(number,name, price, type, day);

                break;
        }
    }

    /**
     * 选择弹窗
     */
    private void showStateDialog() {
        DialogFragmentBottom.Builder builder = new DialogFragmentBottom.Builder(mActivity);
        dialogBottom = builder.setAddViewId(R.layout.dialog_select)
                .setIsVisitCancel(false)
                .setDialogSetDateInterface(inflaterView -> {
                    List<String> listStr = new ArrayList<>();
                    listStr.add("启用");
                    listStr.add("禁用");
                    ListView listView = inflaterView.findViewById(R.id.lv_dialog_select_listview);
                    listView.setAdapter(new CommonLvAdapter<String>(mActivity, R.layout.dialog_select_listview_item, listStr) {
                        @Override
                        protected void convert(ViewHolderLv viewHolder, String item, int position) {
                            viewHolder.setText(R.id.tv_dialog_select_item, listStr.get(position));
                        }
                    });
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_dialog_select_cancel);
                    tvCancel.setOnClickListener(v -> dialogBottom.dismiss());
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        type = String.valueOf(position + 1);
                        tvType.setText(listStr.get(position));
                        dialogBottom.dismiss();
                    });

                }).build();

        dialogBottom.show(getSupportFragmentManager(), "FeedBackActivity");
    }
}
