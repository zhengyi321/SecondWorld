package com.et.secondworld.widget.anim;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/6 https://blog.csdn.net/dreams_deng/article/details/105357734
 **/

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class ResetAnimation extends Animation {

    private final ImageView mImage;
    private final int startHeight;
    private final int endHeight;

    public ResetAnimation(ImageView mImage, int startHeight, int endHeight) {
        this.mImage = mImage;
        this.startHeight = startHeight;
        this.endHeight = endHeight;

        setInterpolator(new OvershootInterpolator());
        //设置动画执行时长
        setDuration(500);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // interpolatedTime 0.0f -> 1.0f


        Integer newHeight = evaluate(interpolatedTime, startHeight, endHeight);

        mImage.getLayoutParams().height = newHeight;
        mImage.requestLayout();

        super.applyTransformation(interpolatedTime, t);
    }


    /**
     * 类型估值器
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }



}
