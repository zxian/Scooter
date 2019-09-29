package com.xian.scooter.module.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.contant.Config;
import com.xian.scooter.utils.ACacheUtils;
import com.xian.scooter.view.flowlayout.FlowLayout;
import com.xian.scooter.view.flowlayout.TagAdapter;
import com.xian.scooter.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : jiacan.zhou
 * time   : 2018/12/21
 * desc   : 搜索页面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.btn_clear_search)
    ImageView btn_clear_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_right_text)
    TextView tv_right_text;
    @BindView(R.id.iv_clear_out)
    ImageView iv_clear_out;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout id_flowlayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    TextView emptyView;

    private ACacheUtils mCache;
    private ArrayList<String> mHistory = new ArrayList<>();
    private TagAdapter<String> tagAdapter;
    private String searchType;
    private String editString;
    private Handler handler = new Handler();

//    private EnterpriseAdapter enterpriseAdapter;
//    private ArrayList<DictBean> dictList=new ArrayList<>();
//    private ArrayList<DictBean> dictCategoryList=new ArrayList<>();


    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
//            switch (searchType) {
//                case Config.SEARCH_ENTERPRISE:
//                    enterpriseAdapter.cleanData();
//                    SearchNetWorkUtils.getInstance().getEnterpriseList(enterpriseAdapter, editString, emptyView,dictList,dictCategoryList);
//                    break;
//                default:
//
//                    break;
//            }
        }
    };


    @Override
    protected void handleIntent(Intent intent) {
        searchType = intent.getStringExtra(Config.SEARCH_TYPE);
//        if (Config.SEARCH_ENTERPRISE.endsWith(searchType)){//企业信息
//            dictList=intent.getParcelableArrayListExtra("dictList");
//            dictCategoryList=intent.getParcelableArrayListExtra("dictCategoryList");
//        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {

        mCache = ACacheUtils.get(this);
        if (null != mCache.getAsObject(searchType)) {
            mHistory = (ArrayList<String>) mCache.getAsObject(searchType);
        } else {
            mCache.put(searchType, mHistory);
        }
        initRecyclerView();

        tagAdapter = new TagAdapter<String>(mHistory) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = new TextView(SearchActivity.this);
                tv.setText(s);
                tv.setBackgroundResource(R.drawable.horn_gray_ef_3dp);
                tv.setPadding(20, 10, 20, 10);
                return tv;
            }
        };
        id_flowlayout.setAdapter(tagAdapter);
        id_flowlayout.setOnTagClickListener((view, position, parent) -> {
            String key = mHistory.get(position);
            et_search.setText("");
            et_search.setText(key);
            if (!TextUtils.isEmpty(key))
                et_search.setSelection(key.length());
            return true;
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    btn_clear_search.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    llHistory.setVisibility(View.VISIBLE);
                    if (delayRun != null) {
                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        handler.removeCallbacks(delayRun);
                    }
                } else {
                    btn_clear_search.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    llHistory.setVisibility(View.GONE);

                    if (delayRun != null) {
                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        handler.removeCallbacks(delayRun);
                    }
                    editString = s.toString().trim();
                    //延迟800ms，如果不再输入字符，则执行该线程的run方法
                    handler.postDelayed(delayRun, 800);
                }

            }
        });

        //软键盘搜索监听
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(et_search.getText())) {
                    btn_clear_search.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    llHistory.setVisibility(View.VISIBLE);
                } else {
                    btn_clear_search.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    llHistory.setVisibility(View.GONE);

                    editString = et_search.getText().toString().trim();
                    //保存搜索历史
                    boolean isCache = false;
                    for (String item : mHistory) {
                        if (item.equals(editString)) {
                            isCache = true;
                        }
                    }
                    if (!isCache) {//判断是否有历史记录
                        if (!TextUtils.isEmpty(editString)) {
                            mHistory.add(editString);
                            mCache.put(searchType, mHistory);
                            tagAdapter.notifyDataChanged();
                        }
                    }

                    if (delayRun != null) {
                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        handler.removeCallbacks(delayRun);
                    }

                    //延迟800ms，如果不再输入字符，则执行该线程的run方法
                    handler.postDelayed(delayRun, 800);
                }
                return true;
            }
            return false;
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);
        //根据搜索类型不同初始化界面
//        switch (searchType) {
//            case Config.SEARCH_ENTERPRISE://企业信息
//                et_search.setHint("搜索企业信息");
//                List<EnterpriseBean> enterpriseList = new ArrayList<>();
//                enterpriseAdapter = new EnterpriseAdapter(mActivity, R.layout.item_enterprise, enterpriseList);
//                recyclerView.setAdapter(enterpriseAdapter);
//                break;
//        }
    }

    @OnClick({R.id.btn_clear_search, R.id.tv_right_text, R.id.iv_clear_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_search:
                et_search.setText("");
                break;
            case R.id.tv_right_text:
                finish();
                break;
            case R.id.iv_clear_out:
//                DialogUtil.showConfirmDialog(this, "温馨提示", "你确定要清空历史记录吗？", true, v -> {
                mHistory.clear();
                mCache.put(searchType, mHistory);
                tagAdapter.notifyDataChanged();
//                    DialogUtil.dissmiss();
//                });
                break;
        }
    }
}