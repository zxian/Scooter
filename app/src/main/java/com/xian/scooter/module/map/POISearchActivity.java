package com.xian.scooter.module.map;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class POISearchActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {


    private AddressAdapter adapter;
    private ArrayList<PoiItem> poiItemList = new ArrayList<>();

    @BindView(R.id.et_Str)
    EditText etStr;
    @BindView(R.id.lv_address_list)
    ListView lvAddressList;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_poisearch;
    }

    @Override
    protected void init() {
        adapter = new AddressAdapter(mActivity, null, false);
        lvAddressList.setAdapter(adapter);
        lvAddressList.setOnItemClickListener((parent, view, position, id) -> {
            if (poiItemList != null && poiItemList.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("PoiItem", poiItemList.get(position));
                setResult(2000, intent);
                finish();
            } else {
                ToastUtils.showToast("请稍后重试");
            }
        });
        etStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                searchAddress(s);
                if (s != null) {
                    searchAddress(s.toString().trim());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchAddress(String str) {
//        KeyBoardUtils.closeKeybord(poiSearchInMaps, BaseApplication.mContext);
        PoiSearch.Query query = new PoiSearch.Query(str, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(mActivity, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    private void searchAddress(CharSequence s) {
        String content = s.toString().trim();//获取自动提示输入框的内容
        LogUtils.e(content);
        InputtipsQuery inputtipsQuery = new InputtipsQuery(content, "");//初始化一个输入提示搜索对象，并传入参数
        Inputtips inputtips = new Inputtips(mActivity, inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
        inputtips.setInputtipsListener((list, errcode) -> {
            LogUtils.e(list.toString() + errcode);
            if (errcode == 1000) {
                poiItemList = new ArrayList<>();
//                for (int i = 0; i < list.size(); i++) {
//                    Tip tip = list.get(i);
//                        PoiItem poiItem = new PoiItem(tip.getName(),tip.getPoint(),tip.getAddress(),tip.getDistrict());
//                        Tip tip = list.get(i);
//                        locationBean.latitude = tip.getPoint().getLatitude();
//                        locationBean.longitude = tip.getPoint().getLongitude();
//                        locationBean.snippet = tip.getName();
//                        locationBean.title = tip.getDistrict();
//                        datas.add(locationBean);
//                }
//                    searchCarAdapter.setNewData(datas);
            }
        });//设置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
        inputtips.requestInputtipsAsyn();//输入查询提示的异步接口实现
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int errcode) {
        LogUtils.e(poiResult.getPois().toString() + "" + errcode);
        if (errcode == 1000) {
            if (poiResult.getPois().size() > 0) {
                poiItemList = poiResult.getPois();
                adapter.setData(poiItemList);
            } else {
                ToastUtils.showToast("没有搜索到相关地址");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
