package com.henan.yijiajia.p_base.util;

import com.henan.yijiajia.main.YijiajiaApplication;

/**
 * Created by 叶满林 on 2019/2/17.
 */

public class DisplayUtils {

    /**
     * convert px to its equivalent dp
     * <p>
     * 将px转换为与之相等的dp
     */
    public static int pxToDp(float pxValue) {
        final float scale = YijiajiaApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     * <p>
     * 将dp转换为与之相等的px
     */
    public static int dpToPx(float dipValue) {
        final float scale = YijiajiaApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * convert px to its equivalent sp
     * <p>
     * 将px转换为sp
     */
    public static int pxToSp(float pxValue) {
        final float fontScale = YijiajiaApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int spToPx(float spValue) {
        final float fontScale = YijiajiaApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
