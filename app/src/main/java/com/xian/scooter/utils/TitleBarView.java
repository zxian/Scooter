package com.xian.scooter.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.scooter.R;


/**
 * Created by kezhangzhao on 2018/1/10.
 */

public class TitleBarView extends RelativeLayout {

    private Context mContext;
    private ImageView mIvLeftImage;//左边的图标，默认是返回图标
    private TextView mTvLeftText;//左边的文字
    private TextView mTvTitleText;//中间的标题文字
    private TextView mTvRightText;//右边的文字
    private ImageView mIvRightImage;//右边图片
    private ImageView mIvRightTwoimage;//右边第二张图片
    private RelativeLayout mLayoutTitle;//标题整个布局
    /**
     * 右边显示view的id。主要是右边的view是不固定的，所以点击事件要用这个ID来判断。
     * mTvRightText的设置id=0
     * mIvRightImage的设置id=1
     * <p>
     * 默认是id是0，也就是右边是一个TextView。
     */
    private int mRightViewID = 0;

    /**
     * 左边显示view的id。主要是左边的view是不固定的，所以点击事件要用这个ID来判断。
     * mIvLeftText的设置id=0
     * mIvLeftImage的设置id=1
     * <p>
     * 默认是id是1，也就是右边是一个ImageView。
     */
    private int mLeftViewID = 1;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public TitleBarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public TextView getmTvRightText() {return mTvRightText;}

    /**
     * 初始化
     */
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_title_bar, this);
        mIvLeftImage = findViewById(R.id.iv_left_image);
        mTvLeftText = findViewById(R.id.tv_left_text);
        mTvRightText = findViewById(R.id.tv_right_text);
        mTvTitleText = findViewById(R.id.tv_title_text);
        mIvRightImage = findViewById(R.id.iv_right);
        mIvRightTwoimage = findViewById(R.id.iv_right_two);
        mLayoutTitle = findViewById(R.id.layout_title);
    }

    /**
     * 设置标题背景颜色
     * @param resID BackgroundResource
     */
    public void setLayoutTitleBg(int resID){
        mLayoutTitle.setBackgroundResource(resID);
    }

    /**
     * 中间模块设置**********************************************************************
     */

    /**
     * 设置中间标题的文字
     *
     * @param titleText 字符串
     */
    public void setTvTitleText(String titleText) {
        mTvTitleText.setText(titleText);
    }

    /**
     * 设置中间标题文字图标
     *
     * @param resID Resources图片id
     * @param orientation 文字图片位置 0 left, 1 top, 2 right, 3 bottom
     * @param pad 文字和图片的间距
     */
    public void setTvTitleTextDrawables(int resID,int orientation,int pad) {

        Drawable drawable=mContext.getResources().getDrawable(resID);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mTvTitleText.setCompoundDrawablePadding(UISizeUtils.dp2px(pad));
        switch (orientation){
            case 0:
                mTvTitleText.setCompoundDrawables(drawable,null,null,null);
                break;
            case 1:
                mTvTitleText.setCompoundDrawables(null,drawable,null,null);
                break;
            case 2:
                mTvTitleText.setCompoundDrawables(null,null,drawable,null);
                break;
            case 3:
                mTvTitleText.setCompoundDrawables(null,null,null,drawable);
                break;
        }
    }

    /**
     * 设置中间标题文字颜色
     * @param resID Resources
     */
    public void setTvTitleTextColor(int resID) {
        mTvTitleText.setTextColor(mContext.getResources().getColor(resID));
    }

    /**
     * 设置中间标题文字大小
     * @param size 大小
     */
    public void setTvTitleTextSize(int size){
        mTvTitleText.setTextSize(size);
    }

    /**
     * 设置中间标题文字是否粗体
     * @param isBold  是否粗体
     */
    public void setTvTitleTextBold(Boolean isBold) {
        if (isBold){
            mTvTitleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            //设置不为加粗
            mTvTitleText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    };

    /**
     * 左边模块设置**********************************************************************
     */

    /**
     * 设置左边图标是否可见
     * 默认可见
     *
     * @param visi View.VISIBLE / View.GONE
     */
    public void setVisiLeftImage(int visi) {
        mIvLeftImage.setVisibility(visi);
    }

    /**
     * 设置左边图片
     *
     * @param resID 图片resID
     */
    public void setLeftImage(int resID) {
        mIvLeftImage.setImageResource(resID);
        mIvLeftImage.setVisibility(VISIBLE);
        mTvLeftText.setVisibility(GONE);//隐藏掉左边文字
        mRightViewID = 1;
    }

    /**
     * 设置左边文字颜色
     * @param resID Resources
     */
    public void setLeftTextColor(int resID) {
        mTvLeftText.setTextColor(mContext.getResources().getColor(resID));
    }

    /**
     * 设置左边文字大小
     * @param size 文字大小
     */
    public void setLeftTextSize(int size){
        mTvLeftText.setTextSize(size);
    }

    /**
     * 设置左边文字
     *
     * @param titleText 字符串
     */
    public void setLeftText(String titleText) {
        mTvLeftText.setText(titleText);
        mTvLeftText.setVisibility(VISIBLE);
        mIvLeftImage.setVisibility(GONE);//隐藏掉左边图片
        mLeftViewID = 0;
    }

    /**
     * 左边图片点击监听事件
     *  mLeftViewID
     * 左边是文字Textview 的时候mRightViewID=0;
     * 左边是图片Imageview 的时候mRightViewID=1;
     * @param leftOnClickListener leftOnClickListener
     */
    public void setLeftOnClickListener(OnClickListener leftOnClickListener) {
        switch (mLeftViewID) {
            case 0:
                mTvLeftText.setOnClickListener(leftOnClickListener);
                break;
            case 1:
                mIvLeftImage.setOnClickListener(leftOnClickListener);
                break;
            default:
                break;
        }
    }

    public void setTitleOnClickListener(OnClickListener onClickListener){

        if (onClickListener != null){
            mTvTitleText.setOnClickListener(onClickListener);
        }

    }


    /**
     * 左边模块设置**********************************************************************
     */


    /**
     * 设置右边文字是否可见
     * 默认可见
     *
     * @param visi View.VISIBLE / View.GONE
     */
    public void setVisiRightText(int visi) {
        mTvRightText.setVisibility(visi);
    }

    /**
     * 设置右边文字颜色
     * @param resID Resources
     */
    public void setRightTextColor(int resID) {
        mTvRightText.setTextColor(mContext.getResources().getColor(resID));
    }

    /**
     * 设置右边文字是否能够点击
     * @param enabled 是否能够点击
     */
    public void setRightTextEnabled(boolean enabled) {
        mTvRightText.setEnabled(enabled);
    }

    /**
     * 设置右边字体大小
     * @param size 字体大小
     */
    public void setRightTextSize(int size){
        mTvRightText.setTextSize(size);
    }
    /**
     * 设置右边是否粗体
     * @param isBold isBold
     */
    public void setRightTextBold(Boolean isBold) {
        if (isBold){
            mTvRightText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            //设置不为加粗
            mTvRightText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    };
    /**
     * 设置右边文字
     *
     * @param titleText 字符串
     */
    public void setRightText(String titleText) {
        mTvRightText.setText(titleText);
        mTvRightText.setVisibility(VISIBLE);
        mRightViewID = 0;
    }

    /**
     * 设置右边图片
     *
     * @param resID 图片id
     */
    public void setIvRightImage(int resID) {
        mIvRightImage.setImageResource(resID);
        mIvRightImage.setVisibility(VISIBLE);
        mRightViewID = 1;
    }
    /**
     * 设置右边第二张图片
     *
     * @param resID 图片id
     */
    public void setIvRightTwoImage(int resID){
        mIvRightTwoimage.setImageResource(resID);
        mIvRightTwoimage.setVisibility(VISIBLE);
    }

    /**
     * 设置右边图片是否可见
     * 默认是不可见的
     * 但是只要调用了setIvRightImage（int resID）设置图片的方法就会可见
     *
     * @param visi View.VISIBLE / View.GONE
     */
    public void setVisiRightImage(int visi) {
        mIvRightImage.setVisibility(visi);
    }

    /**
     * 右边点击监听事件
     * mRightViewID
     * 右边是文字Textview 的时候mRightViewID=0;
     * 右边是图片Imageview 的时候mRightViewID=1;
     *
     * @param rightOnClickListener rightOnClickListener
     */
    public void setRightOnClickListener(OnClickListener rightOnClickListener) {
        switch (mRightViewID) {
            case 0:
                mTvRightText.setOnClickListener(rightOnClickListener);
                break;
            case 1:
                mIvRightImage.setOnClickListener(rightOnClickListener);
                break;
            default:
                break;
        }
    }

    /**
     * 右边第二张点击监听事件
     *
     * @param rightOnClickListener rightOnClickListener
     */
    public void setRightTwoOnClickListener(OnClickListener rightOnClickListener) {
        mIvRightTwoimage.setOnClickListener(rightOnClickListener);
    }
}
