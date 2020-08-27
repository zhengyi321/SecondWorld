package com.et.secondworld.utils;

import android.graphics.Bitmap;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/17
 **/
public class ImageUtils {
    //图片压缩
    private  Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.compressImage(bitmap);
        bitmap = bitmapUtils.comp(bitmap);

        return bitmap;
    }
}
