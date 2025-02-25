package com.xian.scooter.view.indicator.buildings.commonnavigator.abs;



import com.xian.scooter.view.indicator.buildings.commonnavigator.model.PositionData;

import java.util.List;

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public interface IPagerIndicator {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void onPositionDataProvide(List<PositionData> dataList);
}
