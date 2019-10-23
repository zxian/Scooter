package com.xian.scooter.module.event;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class EventAddSetupAddActivity extends BaseActivity {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_age_max)
    EditText etAgeMax;
    @BindView(R.id.et_age_min)
    EditText etAgeMin;
    @BindView(R.id.tv_add)
    TextView tvAdd;


    private String competition_id;
    private CompetitionSavePar competitionSave;

    @Override
    protected void handleIntent(Intent intent) {
        competition_id=intent.getStringExtra("competition_id");
        competitionSave = intent.getParcelableExtra("CompetitionSavePar");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_event_add_setup_add;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("新增类型");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());
        if (competitionSave!=null){
            etName.setText(competitionSave.getApply_competition_name());
            etNumber.setText(competitionSave.getLimit());
            etPrice.setText(competitionSave.getMoney());
            etAgeMax.setText(competitionSave.getBig_age());
            etAgeMin.setText(competitionSave.getSmall_age());
            tvAdd.setText("修改");
        }
    }

    /**
     * 新增OR编辑赛事类型设置信息
     *
     * @param name           报名类型名称
     * @param bigAge         最大年龄
     * @param competition_id 赛事ID
     * @param limit          限制人数
     * @param money          报名费用
     * @param smallAge       最小年龄
     */
    private void getCompetitionSave(String name, String competition_id, String bigAge, String limit, String money, String smallAge) {
        CompetitionSavePar par = new CompetitionSavePar();
        par.setApply_competition_name(name);
        par.setBig_age(bigAge);
        if (!TextUtils.isEmpty(competition_id)) {
            par.setCompetition_id(competition_id);
        }
        par.setLimit(limit);
        par.setMoney(money);
        par.setSmall_age(smallAge);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_SET_SAVE, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提交成功！");
                    Intent intent=new Intent();
                    intent.putExtra("CompetitionSavePar",par);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

        });

    }


    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String ageMax = etAgeMax.getText().toString().trim();
        String ageMin = etAgeMin.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            ToastUtils.showToast("人数不能为空！");
            return;
        }
        if (TextUtils.isEmpty(price)) {
            ToastUtils.showToast("报名费用不能为空！");
            return;
        }
        if (TextUtils.isEmpty(ageMax)) {
            ToastUtils.showToast("最大年龄不能为空！");
            return;
        }
        if (TextUtils.isEmpty(ageMin)) {
            ToastUtils.showToast("最小年龄不能为空！");
            return;
        }
        getCompetitionSave(name, competition_id,ageMax,number,price,ageMin);
    }
}
