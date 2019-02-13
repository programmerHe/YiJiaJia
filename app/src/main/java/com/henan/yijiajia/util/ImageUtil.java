package com.henan.yijiajia.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.henan.yijiajia.R;

/**
 * gradle工具类
 */
public class ImageUtil {
    /**
     * 处理为圆形 的网络、本地图片展示
     */
    public static void showCircleImage(Context context, ImageView view, int imageId) {
        RequestOptions options = getCommentRequestOptions();
        options.circleCrop();
        Glide.with(context).load(imageId).apply(options).into(view);
    }

    private static void showCircleImage(Context context, ImageView view, String name) {
        RequestOptions options = getCommentRequestOptions();
        options.circleCrop();
        Glide.with(context).load(name).apply(options).into(view);
    }

    /**
     * 不处理的网络、本地图片展示
     */
    public static void showOriginalImage(Activity activity, ImageView view, int imageId) {
        RequestOptions options = getCommentRequestOptions();
        Glide.with(activity).load(imageId).apply(options).into(view);
    }

    private static void showOriginalImage(Context context, ImageView view, String imagePath) {
        RequestOptions options = getCommentRequestOptions();
        Glide.with(context).load(imagePath).apply(options).into(view);
    }

    /**
     * 初始化RequestOptions，设置相应的占位图
     */
    private static RequestOptions getCommentRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.placeholder();//未加载时显示
        //requestOptions.error();//加载错误时显示
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);//禁用所有缓存
        requestOptions.centerCrop();//会裁剪图片将imageView填满，其他：fitCenter()会绽放图像, 将图像全显示出来 crossFade()渐变动画
        return requestOptions;
    }

    /**
     * 将图片放大或缩小到指定尺寸
     */
    public static Bitmap resizeImage(Bitmap source, int w, int h) {
        int width = source.getWidth();
        int height = source.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true);
    }

//    public static void show(Activity activity, ImageView view, String name) {
//        if (TextUtils.isEmpty(name)) {
////            view.setImageResource(R.drawable.cd_bg);
//        } else {
////            RequestOptions option s = getCommentRequestOptions();
////            Glide.with(activity).load(getImageURI(name)).apply(options).into(view);
//        }
//    }
//    /**
//     * 聊天界面用
//     * @param context
//     * @param view
//     * @param imagePath
//     */
//    public static void showImage(Context context, ImageView view, String imagePath) {
//        RequestOptions options = new RequestOptions();
//        //options.placeholder(R.drawable.cd_bg);
//        //options.error(R.drawable.cd_bg);
//
//        Glide.with(context).load(imagePath).apply(options).into(view);
//    }
//public static String getImageURI(String name) {
//    return String.format(Consts.RESOURCE_PREFIX, name);
//}


//    public static void showCircleUri(Activity activity, ImageView view, String name) {
//        showCircle(activity, view, getImageURI(name));
//    }

//    //public static void showImageBlur(Activity activity, ImageView view, String name) {
//    //    RequestOptions requestOptions = bitmapTransform(new BlurTransformation(50, 5));
//    //    requestOptions.placeholder(R.drawable.default_album);
//    //    requestOptions.error(R.drawable.default_album);
//    //    Glide.with(activity).load(getImageURI(name)).apply(requestOptions).into(view);
//    //}
//    //
//    //public static void showImageBlur(Activity activity, ImageView view, int imageId) {
//    //    RequestOptions requestOptions = bitmapTransform(new BlurTransformation(50, 5));
//    //    Glide.with(activity).load(imageId).apply(requestOptions).into(view);
//    //}
//
//
//    public static Bitmap scaleBitmap(Resources resource, int resourceId, int width, int height) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds=true;
//
//        BitmapFactory.decodeResource(resource,resourceId,options);
//
//        options.inSampleSize=computerSampleSize(options,width,height);
//
//        options.inJustDecodeBounds=false;
//        Bitmap bitmap = BitmapFactory.decodeResource(resource, resourceId, options);
//
//        //使用inSampleSize可以一次不能缩放到指定大小，所以可以还需要再次缩放
//        int currentWidth = bitmap.getWidth();
//        if (currentWidth > width) {
//            //继续缩放
//            Bitmap newBitmap = ImageUtil.resizeImage(bitmap, width, height);
//
//            bitmap.recycle();
//            return newBitmap;
//        }
//
//        return bitmap;
//    }
//
//    private static int computerSampleSize(BitmapFactory.Options options, int width, int height) {
//        int outWidth = options.outWidth;
//        int outHeight = options.outHeight;
//
//        int inSampleSize=1;
//
//        if (outWidth>width||outHeight>height) {
//            int currentWidth = width / 2;
//            int currentHeight = height / 2;
//
//            while ((currentWidth/inSampleSize)>width && (currentHeight/inSampleSize)>height) {
//                inSampleSize*=2;
//            }
//        }
//
//        return inSampleSize;
//    }
//


}
