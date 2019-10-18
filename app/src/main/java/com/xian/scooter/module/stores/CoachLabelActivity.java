package com.xian.scooter.module.stores;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.ACacheUtils;
import com.xian.scooter.utils.TitleBarView;
import com.xian.scooter.utils.ToastUtils;
import com.xian.scooter.view.flowlayout.FlowLayout;
import com.xian.scooter.view.flowlayout.TagAdapter;
import com.xian.scooter.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class CoachLabelActivity extends BaseActivity {


    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.id_flow_layout)
    TagFlowLayout idFlowLayout;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.et_label)
    EditText etLabel;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;

    private TagAdapter<String> tagAdapter;
    private ArrayList<String> labelList = new ArrayList<>();
    private ACacheUtils mCache;
    private String labelType = "label_type";
    private boolean isAddLayout = false;//是否显示添加布局

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_coach_label;
    }

    @Override
    protected void init() {
        titleBarView.setTvTitleText("教练标签");
        titleBarView.setLeftOnClickListener(view -> mActivity.finish());
        titleBarView.setRightText("完成");
        titleBarView.setRightOnClickListener(view -> {
            StringBuilder stringBuilder = new StringBuilder();
            //获得所有选中的pos集合
            Set<Integer> selectedList = idFlowLayout.getSelectedList();
            if (selectedList != null && selectedList.size() > 0) {
            }else {
                ToastUtils.showToast("请设置教练标签");
                return;
            }
            if (selectedList.size() < 6) {
                ToastUtils.showToast("请设置6个教练标签");
                return;
            }
            Iterator<Integer> it = selectedList.iterator();
            while (it.hasNext()) {
                String s = labelList.get(it.next());
                stringBuilder.append(s);
                stringBuilder.append("、");
            }
            String label = stringBuilder.substring(0, stringBuilder.length() - 1);


            Intent intent = new Intent();
            intent.putExtra("label", label);
            setResult(RESULT_OK, intent);
            finish();
        });
        mCache = ACacheUtils.get(this);
        if (null != mCache.getAsObject(labelType)) {
            labelList = (ArrayList<String>) mCache.getAsObject(labelType);
        } else {
            labelList = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.label_type)));
            mCache.put(labelType, labelList);
        }

        tagAdapter = new TagAdapter<String>(labelList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = new TextView(CoachLabelActivity.this);
                tv.setText(s);
                tv.setTextColor(getResources().getColor(R.color.gray_66));
                tv.setBackgroundResource(R.drawable.horn_white_line_gray_66_20dp);
                tv.setPadding(20, 10, 20, 10);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.horn_theme_20dp);
            }

            @Override
            public void unSelected(int position, View view) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.gray_66));
                tv.setBackgroundResource(R.drawable.horn_white_line_gray_66_20dp);
            }
        };
        idFlowLayout.setAdapter(tagAdapter);
        idFlowLayout.setOnTagClickListener((view, position, parent) -> {
            String key = labelList.get(position);

            return true;
        });
    }


    @OnClick({R.id.tv_add, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                if (!isAddLayout) {
                    isAddLayout = true;
                    layoutBottom.setVisibility(View.VISIBLE);
                } else {
                    isAddLayout = false;
                    layoutBottom.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_ensure:
                String label = etLabel.getText().toString().trim();
                if (TextUtils.isEmpty(label)) {
                    ToastUtils.showToast("请输入你想添加的教练标签");
                    return;
                }
                if (label.length() > 6) {
                    ToastUtils.showToast("你输入的教练标签大于6个字");
                    return;
                }
                //保存标签
                boolean isCache = false;
                for (String item : labelList) {
                    if (item.equals(label)) {
                        isCache = true;
                    }
                }
                if (!isCache) {//判断是否有标签
                    if (!TextUtils.isEmpty(label)) {
                        labelList.add(label);
                        mCache.put(labelType, labelList);
                        tagAdapter.notifyDataChanged();
                    }
                }

                isAddLayout = false;
                layoutBottom.setVisibility(View.GONE);
                break;
        }
    }
}
