package com.xian.scooter.module.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.bit.meantest.BottomBarView;
import com.bit.meantest.BottomBean;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.manager.UserManager;
import com.xian.scooter.module.event.EventFragment;
import com.xian.scooter.module.interactive.InteractiveFragment;
import com.xian.scooter.module.mall.MallFragment;
import com.xian.scooter.module.stores.StoresFragment;
import com.xian.scooter.module.training.TrainingFragment;
import com.xian.scooter.utils.PermissionsUtils;
import com.xian.scooter.utils.bar.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * author : jiacan,zhou
 * time   : 2019/07/16
 * desc   : MainActivity
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.bottombarview)
    BottomBarView bottombarview;


    private FragmentManager mFragmentManager;
    private static final int LOGIN_REQUEST_CODE = 1;
    private StoresFragment storesFragment;
    private EventFragment eventFragment;
    private TrainingFragment trainingFragment;
    private MallFragment mallFragment;
    private InteractiveFragment interactiveFragment;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String userToken = UserManager.getInstance().getUserToken();
//        if (TextUtils.isEmpty(userToken)){
//            finish();
//            startActivity(new Intent(mActivity,LoginActivity.class));
//        }
    }

    @Override
    protected void init() {

        mFragmentManager = getSupportFragmentManager();

        bottombarview.setData(createMenuData());
        bottombarview.setSelectPosition(0);
        bottombarview.setSelectTextColor(R.color.theme);
        bottombarview.setUnSelectTextColor(R.color.gray_99);
        setTabSelection(0);
        bottombarview.setOnMyItemClickListener((view, position) -> {
            switch (position) {
                case 0:
                    setTabSelection(0);
                    StatusBarUtil.setStatusBarDarkTheme(this, true);
                    break;
                case 1:
                    setTabSelection(1);
                    StatusBarUtil.setStatusBarDarkTheme(this, true);
                    break;
                case 2:
                    setTabSelection(2);
                    StatusBarUtil.setStatusBarDarkTheme(this, true);
                    break;
                case 3:
                    setTabSelection(3);
                    StatusBarUtil.setStatusBarDarkTheme(this, false);
                    break;
                case 4:
                    setTabSelection(4);
                    StatusBarUtil.setStatusBarDarkTheme(this, false);
                    break;
            }
        });

        //申请权限
        PermissionsUtils.getInstance().requestPermissions(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.REORDER_TASKS);

    }


    private ArrayList<BottomBean> createMenuData() {
        ArrayList<BottomBean> listBean = new ArrayList<>();
        BottomBean bottomBean1 = new BottomBean();
        bottomBean1.setIvSelectID(R.mipmap.ic_stores_select);
        bottomBean1.setIvUnSelectID(R.mipmap.ic_stores_default);
        bottomBean1.setBottomText("门店");
        listBean.add(bottomBean1);
        BottomBean bottomBean2 = new BottomBean();
        bottomBean2.setIvSelectID(R.mipmap.ic_event_select);
        bottomBean2.setIvUnSelectID(R.mipmap.ic_event_default);
        bottomBean2.setBottomText("赛事");
        listBean.add(bottomBean2);
        BottomBean bottomBean3 = new BottomBean();
        bottomBean3.setIvSelectID(R.mipmap.ic_training_select);
        bottomBean3.setIvUnSelectID(R.mipmap.ic_training_default);
        bottomBean3.setBottomText("培训");
        listBean.add(bottomBean3);
        BottomBean bottomBean4 = new BottomBean();
        bottomBean4.setIvSelectID(R.mipmap.ic_mall_select);
        bottomBean4.setIvUnSelectID(R.mipmap.ic_mall_default);
        bottomBean4.setBottomText("商城");
        listBean.add(bottomBean4);
        BottomBean bottomBean5 = new BottomBean();
        bottomBean5.setIvSelectID(R.mipmap.ic_interactive_select);
        bottomBean5.setIvUnSelectID(R.mipmap.ic_interactive_default);
        bottomBean5.setBottomText("互动");
        listBean.add(bottomBean5);
        return listBean;
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0:
                if (storesFragment == null) {
                    storesFragment = StoresFragment.newInstance();
                    transaction.add(R.id.content, storesFragment);
                } else {
                    transaction.show(storesFragment);
                }
                break;
            case 1:
                if (eventFragment == null) {
                    eventFragment = EventFragment.newInstance();
                    transaction.add(R.id.content, eventFragment);
                } else {
                    transaction.show(eventFragment);
                }
                break;
            case 2:
                if (trainingFragment == null) {
                    trainingFragment = TrainingFragment.newInstance();
                    transaction.add(R.id.content, trainingFragment);
                } else {
                    transaction.show(trainingFragment);
                }
                break;
            case 3:
                if (mallFragment == null) {
                    mallFragment = MallFragment.newInstance();
                    transaction.add(R.id.content, mallFragment);
                } else {
                    transaction.show(mallFragment);
                }
                break;
            case 4:
                if (interactiveFragment == null) {
                    interactiveFragment = InteractiveFragment.newInstance();
                    transaction.add(R.id.content, interactiveFragment);
                } else {
                    transaction.show(interactiveFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (storesFragment != null) {
            transaction.hide(storesFragment);
        }
        if (eventFragment != null) {
            transaction.hide(eventFragment);
        }
        if (trainingFragment != null) {
            transaction.hide(trainingFragment);
        }
        if (mallFragment != null) {
            transaction.hide(mallFragment);
        }
        if (interactiveFragment != null) {
            transaction.hide(interactiveFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                bottombarview.setSelectPosition(0);
                setTabSelection(0);
            }
        }
    }

}
