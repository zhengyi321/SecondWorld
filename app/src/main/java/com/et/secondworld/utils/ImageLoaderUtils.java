package com.et.secondworld.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import com.et.secondworld.R;


/**
 * Created by az on 2017/5/6.
 */

public class ImageLoaderUtils {
    public static DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的�?
    public static DisplayImageOptions options_circle_imge;
    public static DisplayImageOptions options1;

    static {
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.allshopbg) // 设置图片下载期间显示的图�?
//                .showImageForEmptyUri(R.mipmap.allshopbg) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.allshopbg) // 设置图片加载或解码过程中发生错误显示的图�?
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存�?
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options1 = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.allshopbg) // 设置图片下载期间显示的图�?
//                .showImageForEmptyUri(R.mipmap.allshopbg) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.allshopbg) // 设置图片加载或解码过程中发生错误显示的图�?
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存�?
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        options_circle_imge = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.allshopbg) // 设置图片下载期间显示的图�?
                .showImageForEmptyUri(R.mipmap.allshopbg) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.allshopbg) // 设置图片加载或解码过程中发生错误显示的图�?
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存�?
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8)) // 设置成圆角图�?
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }

}
