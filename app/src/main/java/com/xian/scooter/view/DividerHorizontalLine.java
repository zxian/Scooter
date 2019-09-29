package com.xian.scooter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xian.scooter.R;

/**
 * Created by DELL on 2019/3/3.
 */

public class DividerHorizontalLine extends View {

    private int dividerLineColor;
    private int dividerLineBgColor;
    private int dividerPaddingLeft;
    private int dividerPaddingRight;

    public DividerHorizontalLine(Context context) {
        super(context);
    }

    private static final int DEFAULT_BORDER_WIDTH = 0;


    public DividerHorizontalLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final int DEFAULT_COLOR_PRIMARY = 0xECECEC;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Divider);
        dividerLineColor = a.getColor(R.styleable.Divider_dividerLineColor, DEFAULT_COLOR_PRIMARY);
        dividerLineBgColor = a.getColor(R.styleable.Divider_dividerLineBgColor, DEFAULT_COLOR_PRIMARY);
        dividerPaddingLeft = a.getDimensionPixelSize(R.styleable.Divider_dividerPaddingLeft, DEFAULT_BORDER_WIDTH);
        dividerPaddingRight = a.getDimensionPixelSize(R.styleable.Divider_dividerPaddingRight, DEFAULT_BORDER_WIDTH);
        a.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        // canvas的宽度和高度
        int lineWidth = getWidth();
        int lineHeight = getHeight();
        // 设置线的粗细为canvas的高度
        paint.setStrokeWidth(lineHeight);
        // 画左端的白线，假设两端留白长度是30
        paint.setColor(dividerLineBgColor);
        canvas.drawLine(0, lineHeight / 2, dividerPaddingLeft, lineHeight / 2, paint);
        // 画中间的分割线
        paint.setColor(dividerLineColor);
        canvas.drawLine(dividerPaddingLeft, lineHeight / 2, lineWidth - dividerPaddingRight, lineHeight / 2, paint);
        // 画右端的白线，假设两端留白长度是30
        paint.setColor(dividerLineBgColor);
        canvas.drawLine(lineWidth - dividerPaddingRight, lineHeight / 2, lineWidth, lineHeight / 2, paint);
    }
}
