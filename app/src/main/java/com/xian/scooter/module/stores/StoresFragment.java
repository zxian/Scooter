package com.xian.scooter.module.stores;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.adapter.lvadapter.CommonLvAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.commonView.CustomGridView;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseFragment;
import com.xian.scooter.utils.SignUtils;
import com.xian.scooter.utils.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoresFragment extends BaseFragment {

    @BindView(R.id.title_bar_view)
    TitleBarView titleBarView;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_star)
    TextView tvStar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.gv_item)
    CustomGridView gvItem;

    public static List<Integer> list = new ArrayList<>();
    private CommonLvAdapter<Integer> itemAdapter;

    public static StoresFragment newInstance() {
        return new StoresFragment();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_stores;
    }

    @Override
    protected void init(View view) {
        titleBarView.setTvTitleText("门店");
        titleBarView.setLeftOnClickListener(view1 -> mActivity.finish());

        TypedArray array = mActivity.getResources().obtainTypedArray(R.array.item_res_img);

        for (int i=0,len=8; i<len; i++){
            int resourceId = array.getResourceId(i,0);
            list.add(resourceId);
        }
        array.recycle();
        itemAdapter = new CommonLvAdapter<Integer>(mActivity, R.layout.item_store,list) {
            @Override
            protected void convert(ViewHolderLv viewHolder, Integer resId, int position) {
                viewHolder.setImageResource(R.id.iv_picture, resId);
            }
        };

        gvItem.setAdapter(itemAdapter);
        gvItem.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position){
                case 0://教练
                    startActivity(new Intent(mActivity,CoachActivity.class));
                    break;
                case 1://会员
                    break;
                case 2://课程包
                    break;
                case 3://财务
                    break;
                case 4://订单
                    break;
                case 5://我的互动
                    break;
                case 6://门店信息
                    break;
                case 7://消息中心
                    break;
            }
        });
    }

}
