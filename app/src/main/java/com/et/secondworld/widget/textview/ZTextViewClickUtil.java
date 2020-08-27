package com.et.secondworld.widget.textview;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/22
 **/
public class ZTextViewClickUtil {
    private static long lastClickTime;

    public ZTextViewClickUtil() {
    }

    public static synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500L) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
