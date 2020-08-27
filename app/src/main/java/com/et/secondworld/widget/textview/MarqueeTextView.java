package com.et.secondworld.widget.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/22
 **/
public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {//必须重写，且返回值是true，表示始终获取焦点
        return true;
    }
}
