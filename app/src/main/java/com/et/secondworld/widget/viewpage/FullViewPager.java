package com.et.secondworld.widget.viewpage;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.et.secondworld.utils.WindowUtils;

/**
 * 解决Scrollview嵌套ViewPager显示不全
 */
public class FullViewPager extends ViewPager {
    private Context mcontext;
    public FullViewPager(@NonNull Context context) {
        super(context);
        mcontext = context;
    }

    public FullViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowUtils windowUtils= new WindowUtils((Activity)mcontext);
        int height = (int)windowUtils.getWindowHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (null != child) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int measuredHeight = child.getMeasuredHeight();
//                if (measuredHeight > height) {
//                    height = measuredHeight;
//                }
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
