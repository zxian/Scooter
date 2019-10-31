package com.xian.scooter.module.event;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bit.commonView.CustomRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.kzz.dialoglibraries.dialog.DialogCreate;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.CoachBean;
import com.xian.scooter.bean.EventAddSetupAddBean;
import com.xian.scooter.beanpar.CompetitionSavePar;
import com.xian.scooter.module.adapter.EventAdapter;
import com.xian.scooter.module.stores.CoachActivity;
import com.xian.scooter.module.stores.CoachAddActivity;
import com.xian.scooter.net.ApiRequest;
import com.xian.scooter.net.DefineCallback;
import com.xian.scooter.net.HttpEntity;
import com.xian.scooter.net.HttpURL;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.xian.scooter.view.flowlayout.FlowLayout;
import com.xian.scooter.view.flowlayout.TagAdapter;
import com.xian.scooter.view.flowlayout.TagFlowLayout;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.CarNumberPicker;
import cn.addapp.pickers.picker.DateTimePicker;
import cn.addapp.pickers.picker.NumberPicker;
import cn.addapp.pickers.picker.SinglePicker;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.xian.scooter.utils.UISizeUtils.dp2px;

public class EventAddSetupAddActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.recycler_view)
    CustomRecyclerView recyclerView;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    private String competition_id;
    private CompetitionSavePar competitionSave;
    private int ageMax;
    private int ageMin;
    private DialogCreate mDialogCreate;
    private CommonRvAdapter<EventAddSetupAddBean> adapter;
    private List<EventAddSetupAddBean> list = new ArrayList<>();
    private List<EventAddSetupAddBean> addList = new ArrayList<>();

    @Override
    protected void handleIntent(Intent intent) {
        competition_id = intent.getStringExtra("competition_id");
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
        if (competitionSave != null) {
            etName.setText(competitionSave.getApply_competition_name());
            etNumber.setText(competitionSave.getLimit());
            etPrice.setText(competitionSave.getMoney());
//            etAgeMax.setText(competitionSave.getBig_age());
//            etAgeMin.setText(competitionSave.getSmall_age());
            tvBtn.setText("修改");
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CommonRvAdapter<EventAddSetupAddBean>(this, R.layout.item_event_add_setup_add, list) {
            @Override
            public void convert(final ViewHolderRv viewHolder, EventAddSetupAddBean record, int position) {
                viewHolder.setText(R.id.tv_title, record.getName());
                TextView tvContent = viewHolder.getView(R.id.tv_content);
                tvContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                tvContent.setText(record.getInput());
            }
        };
        recyclerView.setAdapter(adapter);
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
        if (addList!=null&&addList.size()>0) {
            String remarkJson = new Gson().toJson(addList);
            par.setRemark(remarkJson);
        }
        par.setSmall_age(smallAge);
        par.setSign();
        ApiRequest.getInstance().post(HttpURL.COMPETITION_SET_SAVE, par, new DefineCallback<String>() {
            @Override
            public void onMyResponse(SimpleResponse<String, HttpEntity> response) {
                if (response.isSucceed()) {
                    ToastUtils.showToast("提交成功！");
                    Intent intent = new Intent();
                    intent.putExtra("CompetitionSavePar", par);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });

    }

    @OnClick({R.id.tv_age, R.id.tv_add, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_age:
                showOpenDialog();
                break;
            case R.id.tv_add:
                showTypeDialog();
                break;
            case R.id.tv_btn:
                String name = etName.getText().toString().trim();
                String number = etNumber.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
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
                if (ageMax == 0 && ageMin == 0) {
                    ToastUtils.showToast("请选择年龄区间！");
                    return;
                }
                getCompetitionSave(name, competition_id, ageMax + "", number, price, ageMin + "");

                break;
        }
    }


    /**
     * 类型弹窗提示
     */
    private void showTypeDialog() {
        DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
        mDialogCreate = builder
                .setAddViewId(R.layout.dialog_select_type)
                .setIsHasCloseView(false)
                .setDialogSetDateInterface(inflaterView -> {
                    TextView tv_single_text = inflaterView.findViewById(R.id.tv_single_text);
                    TextView tv_multiple_text = inflaterView.findViewById(R.id.tv_multiple_text);
                    TextView tv_single_select = inflaterView.findViewById(R.id.tv_single_select);
                    TextView tv_multiple_select = inflaterView.findViewById(R.id.tv_multiple_select);

                    tv_single_text.setOnClickListener(v -> {
                        mDialogCreate.dismiss();
                        showSingleTextDialog();
                    });
                    tv_multiple_text.setOnClickListener(v -> {
                        mDialogCreate.dismiss();
                        showMultipleTextDialog();
                    });
                    tv_single_select.setOnClickListener(v -> {
                        mDialogCreate.dismiss();
                        showSingleSelectDialog(1);
                    });
                    tv_multiple_select.setOnClickListener(v -> {
                        mDialogCreate.dismiss();
                        showSingleSelectDialog(2);
                    });

                })
                .build();
        mDialogCreate.showSingle();
    }

    /**
     * 单行文本弹窗提示
     */
    private void showSingleTextDialog() {
        DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
        mDialogCreate = builder
                .setAddViewId(R.layout.dialog_editor)
                .setIsHasCloseView(false)
                .setDialogSetDateInterface(inflaterView -> {
                    TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                    TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                    TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                    tvTitle.setText("单行文本");
                    tvConfirm.setOnClickListener(v -> {
                        String content = tvMsg.getText().toString().trim();
                        EventAddSetupAddBean eventAddSetupAddBean = new EventAddSetupAddBean();
                        eventAddSetupAddBean.setType("1");
                        eventAddSetupAddBean.setName(content);
                        eventAddSetupAddBean.setInput("单行文本");
                        addList.add(eventAddSetupAddBean);
                        adapter.setData(addList);
                        mDialogCreate.dismiss();
                    });
                    tvCancel.setOnClickListener(v -> mDialogCreate.dismiss());
                })
                .build();
        mDialogCreate.showSingle();
    }

    /**
     * 多行文本弹窗提示
     */
    private void showMultipleTextDialog() {
        DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
        mDialogCreate = builder
                .setAddViewId(R.layout.dialog_editor)
                .setIsHasCloseView(false)
                .setDialogSetDateInterface(inflaterView -> {
                    TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                    TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                    TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                    tvTitle.setText("多行文本");
                    tvMsg.setMinLines(3);
                    tvConfirm.setOnClickListener(v -> {
                        String content = tvMsg.getText().toString().trim();
                        EventAddSetupAddBean eventAddSetupAddBean = new EventAddSetupAddBean();
                        eventAddSetupAddBean.setType("2");
                        eventAddSetupAddBean.setName(content);
                        eventAddSetupAddBean.setInput("多行文本");
                        addList.add(eventAddSetupAddBean);
                        adapter.setData(addList);
                        mDialogCreate.dismiss();
                    });
                    tvCancel.setOnClickListener(v -> mDialogCreate.dismiss());
                })
                .build();
        mDialogCreate.showSingle();
    }
    /**
     *  选择问题弹窗提示
     * @param type 1 单选 ；2 多选
     */
    private void showSingleSelectDialog(int type) {
        List<EventAddSetupAddBean.SelectBean> list = new ArrayList<>();
        List<EventAddSetupAddBean.SelectBean> selectList = new ArrayList<>();

        DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
        mDialogCreate = builder
                .setAddViewId(R.layout.dialog_type_select)
                .setIsHasCloseView(false)
                .setDialogSetDateInterface(inflaterView -> {
                    TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                    TextView tvMsg = inflaterView.findViewById(R.id.tv_dialog_msg);
                    CustomRecyclerView recyclerView = inflaterView.findViewById(R.id.recycler_view);
                    TextView tvAdd = inflaterView.findViewById(R.id.tv_dialog_add);
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                    TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                    if (type==1) {
                        tvTitle.setText("单选问题");
                    }else {
                        tvTitle.setText("多选问题");
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                    CommonRvAdapter<EventAddSetupAddBean.SelectBean> selectAdapter =
                            new CommonRvAdapter<EventAddSetupAddBean.SelectBean>(mActivity, R.layout.item_dialog_type_select, list) {
                                @Override
                                public void convert(ViewHolderRv holder, EventAddSetupAddBean.SelectBean selectBean, int position) {
                                    EditText etContent = holder.getView(R.id.et_content);
                                    ImageView ivDelete = holder.getView(R.id.iv_delete);
                                    etContent.setText(selectBean.getName());

                                    ivDelete.setOnClickListener(view -> {
                                        selectList.remove(position);
                                        setData(selectList);
                                    });
                                }

                                @Override
                                public void onBindViewHolder(ViewHolderRv holder, int position) {
                                    super.onBindViewHolder(holder, position);
                                    EditText etContent = holder.getView(R.id.et_content);
                                    etContent.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                            int adapterPosition = holder.getAdapterPosition();
                                            if (adapterPosition < selectList.size()) {
                                                String content = etContent.getText().toString().trim();
                                                selectList.get(adapterPosition).setName(content);
                                            }
                                        }
                                    });
                                }

                            };
                    recyclerView.setAdapter(selectAdapter);

                    tvAdd.setOnClickListener(view -> {
                        EventAddSetupAddBean.SelectBean selectBean = new EventAddSetupAddBean.SelectBean();
                        selectList.add(selectBean);
                        selectAdapter.setData(selectList);
                    });
                    tvConfirm.setOnClickListener(v -> {
                        String content = tvMsg.getText().toString().trim();
                        EventAddSetupAddBean eventAddSetupAddBean = new EventAddSetupAddBean();
                        eventAddSetupAddBean.setName(content);
                        if (type==1) {
                            eventAddSetupAddBean.setType("3");
                            eventAddSetupAddBean.setInput("单选问题");
                        }else {
                            eventAddSetupAddBean.setType("4");
                            eventAddSetupAddBean.setInput("多选问题");
                        }

                        eventAddSetupAddBean.setSelect(selectList);
                        addList.add(eventAddSetupAddBean);
                        adapter.setData(addList);
                        mDialogCreate.dismiss();
                    });
                    tvCancel.setOnClickListener(v -> mDialogCreate.dismiss());
                })
                .build();
        mDialogCreate.showSingle();
    }

    /**
     * 年龄弹窗提示
     */
    private void showOpenDialog() {
        DialogCreate.Builder builder = new DialogCreate.Builder(mActivity);
        mDialogCreate = builder
                .setAddViewId(R.layout.dialog_age_content)
                .setIsHasCloseView(false)
                .setDialogSetDateInterface(inflaterView -> {
                    TextView tvTitle = inflaterView.findViewById(R.id.tv_dialog_title);
                    LinearLayout rlMin = inflaterView.findViewById(R.id.rl_min);
                    LinearLayout rlMax = inflaterView.findViewById(R.id.rl_max);
                    TextView tvConfirm = inflaterView.findViewById(R.id.tv_confirm);
                    TextView tvCancel = inflaterView.findViewById(R.id.tv_cancel);
                    tvCancel.setOnClickListener(view -> mDialogCreate.dismiss());
                    tvTitle.setText("年龄区间选择");
                    NumberPicker minPicker = onAgePicker(ageMin);
                    NumberPicker maxPicker = onAgePicker(ageMax);
                    rlMin.addView(minPicker.getContentView());
                    rlMax.addView(maxPicker.getContentView());

                    minPicker.setOnSingleWheelListener(new OnSingleWheelListener() {
                        @Override
                        public void onWheeled(int i, String s) {
                            ageMin = Integer.parseInt(s);
                        }
                    });
                    maxPicker.setOnSingleWheelListener(new OnSingleWheelListener() {
                        @Override
                        public void onWheeled(int i, String s) {
                            ageMax = Integer.parseInt(s);
                        }
                    });
                    tvConfirm.setOnClickListener(v -> {
                        if (ageMin >= ageMax) {
                            ToastUtils.showToast("最小年龄不能超过最大年龄");
                            return;
                        }
                        tvAge.setText(ageMin + "-" + ageMax);
                        mDialogCreate.dismiss();
                    });

                })
                .build();
        mDialogCreate.showSingle();
    }

    /**
     * 时间选择
     */
    public NumberPicker onAgePicker(int age) {
        NumberPicker picker = new NumberPicker(this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setOffset(1);//显示的条目数

        picker.setRange(1, 100);
        if (age != 0) {
            picker.setSelectedItem(age);
        }
        //中间滚动项文字颜色
        picker.setSelectedTextColor(getResources().getColor(R.color.black_33));
        picker.setLineVisible(false);//设置分隔线是否可见
        picker.setWeightEnable(true);

        return picker;
    }

}
