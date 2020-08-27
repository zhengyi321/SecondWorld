package com.et.secondworld.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/14
 **/
public class MyRecycleViewScrollView extends ScrollView {
    private float xDistance, yDistance, xLast, yLast;

    public MyRecycleViewScrollView(Context context) {
        super(context);
    }

    public MyRecycleViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleViewScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}