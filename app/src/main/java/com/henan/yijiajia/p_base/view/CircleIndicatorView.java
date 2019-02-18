package com.henan.yijiajia.p_base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.util.DisplayUtils;

import java.util.ArrayList;


/**
 * Created by 叶满林 on 2019/2/17.
 */

public class CircleIndicatorView extends View {
    private float mRadius;//半径
    private float mDistance;//间距
    private int mDefaultColor = Color.WHITE;//未选中的颜色
    private int mDefaultSelectColor = Color.YELLOW;//选中时的颜色
    private int mCount;//个数
    private Paint mPaint;
    private int mPosition;
    private ArrayList<Circle> mIndicatorList;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    private void getAttr(Context context, AttributeSet attrs) {
        //用来加载自定义属性的布局文件，返回的是TypedArray类型的对象
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView);
        //getDimension：用来获取自定义的属性的大小尺寸，参数一为定义的属性名称，参数二为默认设置的尺寸大小(绝对尺寸)
        mRadius = attributes.getDimension(R.styleable.CircleIndicatorView_indicatorRadius, DisplayUtils.dpToPx(5));
        mDistance = attributes.getDimension(R.styleable.CircleIndicatorView_indicatorDistance, DisplayUtils.dpToPx(8));
        mDefaultColor = attributes.getColor(R.styleable.CircleIndicatorView_indicatorColor, mDefaultColor);
        mDefaultSelectColor = attributes.getColor(R.styleable.CircleIndicatorView_indicatorSelectColor, mDefaultSelectColor);
        attributes.recycle();  //进行变量缓存，每次加载从缓存数据里面读取
    }

    private void init() {
        //创建画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置绘制防抖
        mPaint.setDither(true);
        //设置绘制模式：内部填充:FILL：填充内部，FILL_AND_STROKE：填充内部和描边，STROKE：描边
        mPaint.setStyle(Paint.Style.FILL);
        //创建集合添加指示器
        mIndicatorList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mCount == 0) {
            mCount = 1;
        }
        //宽度=直径+间距,高度=直径
        int width = (int) (mRadius * 2 * mCount + mDistance * (mCount - 1));
        int height = (int) mRadius * 2;
        setMeasuredDimension(width, height);
        if (mIndicatorList!=null){
            mIndicatorList.clear();
        }else {
            mIndicatorList= new ArrayList<>();
        }

        float x = 0;
        for (int i = 0; i < mCount; i++) {
            //确定圆点的位置
            Circle circle = new Circle();
            if (i == 0) {
                x = mRadius;
            } else {
                x += mRadius * 2 + mDistance;
            }
            circle.x = x;
            circle.y = getMeasuredHeight() / 2;
            mIndicatorList.add(circle);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mIndicatorList.size(); i++) {
            Circle circle = mIndicatorList.get(i);
            float x = circle.x;
            float y = circle.y;

            if (mPosition == i) {
                mPaint.setColor(mDefaultSelectColor);
            } else {
                mPaint.setColor(mDefaultColor);
            }
            canvas.drawCircle(x, y, mRadius, mPaint);
        }
    }


    public static class Circle {
        public float x; // 圆心x坐标
        public float y; // 圆心y 坐标
    }

    /**
     * 设置小圆点数量
     */
    public void setCount(int count) {
        this.mCount = count;
    }

    /**
     * 设置选中指示器的颜色
     */
    public void setSelectColor(int selectColor) {
        this.mDefaultSelectColor = selectColor;
    }

    /**
     * 设置指示器默认颜色
     */
    public void setDefaultColor(int defaultColor) {
        this.mDefaultColor = defaultColor;
    }

    /**
     * 设置选中的位置
     */
    public void setSelectPosition(int selectPosition) {
        this.mPosition = selectPosition;
        invalidate();
    }

    /**
     * 设置Indicator 半径
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    /**
     * 设置小圆点之间的距离
     */
    public void setDistance(int distance) {
        this.mDistance = distance;
    }


}
