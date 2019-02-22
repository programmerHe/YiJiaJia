package com.henan.yijiajia.p_base.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

import com.henan.yijiajia.R;

/**
 * Created by 叶满林 on 2019/2/22.
 */
@SuppressLint("AppCompatCustomView")
public class MenuButton extends Button{

    private int mArrow;
    private Bitmap mArrowBitmap;

    public MenuButton(Context context) {
        super(context,null);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mArrow = R.drawable.arrow;
        mArrowBitmap = BitmapFactory.decodeResource(getResources(), mArrow);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //靠右
        int x = 0;
        int y = 0;
        canvas.drawBitmap(mArrowBitmap, x, y, null);
        // 坐标需要转换，因为默认情况下Button中的文字居中显示
        // 这里需要让文字在底部显示
        canvas.translate(0,(this.getMeasuredHeight()/2));
        super.onDraw(canvas);
    }

}
