package com.xian.scooter.utils;

public class SearchNetWorkUtils {

    private static SearchNetWorkUtils searchNetWorkUtils;

    public static SearchNetWorkUtils getInstance() {
        if (null == searchNetWorkUtils) {
            synchronized (SearchNetWorkUtils.class) {
                if (searchNetWorkUtils == null) {
                    searchNetWorkUtils = new SearchNetWorkUtils();
                }
            }
        }
        return searchNetWorkUtils;
    }

    /**
     * 获取企业列表
     *
     * @param mAdapter  mAdapter
     * @param key       关键字
     * @param emptyView
     */
//    public void getEnterpriseList(EnterpriseAdapter mAdapter, String key, TextView emptyView, ArrayList<DictBean> dictList,ArrayList<DictBean> dictCategoryList) {
//        EnterprisePar par = new EnterprisePar();
//        par.setName(key);
//        par.setPageNum(1);
//        par.setPageSize(1000);
//        ApiRequest.getInstance().post(HttpURL.UNIT_ALL, par, new DefineCallback<PageBean<EnterpriseBean>>() {
//            @Override
//            public void onMyResponse(SimpleResponse<PageBean<EnterpriseBean>, HttpEntity> response) {
//                if (response.isSucceed()) {
//                    if (response.succeed() != null) {
//                        List<EnterpriseBean> enterpriseList = response.succeed().getList();
//                        if ((enterpriseList != null && enterpriseList.size() > 0)) {
//                            mAdapter.updataItem(enterpriseList, dictList, dictCategoryList);
//                            emptyView.setVisibility(View.GONE);
//                        } else {
//                            emptyView.setText("没有找到“" + key + "”的搜索结果");
//                            emptyView.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        emptyView.setText("没有找到“" + key + "”的搜索结果");
//                        emptyView.setVisibility(View.VISIBLE);
//                    }
//
//                } else {
//                    if (response.failed() != null)
//                        ToastUtils.showToast(response.failed().getMsg());
//                }
//            }
//
//        });
//    }

}
