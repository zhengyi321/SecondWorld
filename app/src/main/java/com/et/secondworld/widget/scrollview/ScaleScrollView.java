package com.et.secondworld.widget.scrollview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * @author kakrot
 * 顶部下拉图片放大回弹效果
 */
public class ScaleScrollView extends NestedScrollView {
    /**
     * 需要放大的View
     */
    private View mTargetView;
    private View secondTargetView;

    /**图片的高度*/
    private int drawableHeight;
    /**ImageView的原始高度*/
    private int originalHeight;

//    public void setParallaxImage(View iv_header) {
////        this.iv_header = iv_header;
//
//
//    }
    /**
     * 放大View的宽
     */
    private int mTargetViewWidth;
    private int mSecondTargetViewWidth;
    /**
     * 放大View的高
     */
    private int mTargetViewHeight;
    private int mSecondTargetViewHeight;
    /**
     * 上一次按下的位置
     */
    private float mLastPosition;
    /**
     * 是否正在滑动
     */
    private boolean isScrolling;
    /**
     * 滑到顶部是否需要回弹效果
     */
    private boolean isCanOverScroll;
    /**
     * 放大系数
     */
    private float mScaleRatio = 0.5f;
    /**
     * 恢复原样速度
     */
    private float mCallbackSpeed = 0.2f;

    public ScaleScrollView(@NonNull Context context) {
        super(context);
    }

    public ScaleScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTargetView(View targetView,View secondTargetView1) {
        this.mTargetView = targetView;
        this.secondTargetView = secondTargetView1;
//        drawableHeight = 1000;
//        originalHeight = mTargetView.getMeasuredHeight();
    }

    public void setScaleRatio(float ratio) {
        this.mScaleRatio = ratio;
    }

    public void setCallbackSpeed(float speed) {
        this.mCallbackSpeed = speed;
    }
    private boolean isNeedScroll = false;
    private float xDistance, yDistance, xLast, yLast;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOverScrollMode(OVER_SCROLL_ALWAYS);
    }

    @Override
    public void fling(int y) {
        this.isCanOverScroll = y <= -6000;
        super.fling(y);
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
                Log.e("SiberiaDante", "xDistance ：" + xDistance + "---yDistance:" + yDistance);
                if (isNeedScroll) {
                    if (-5<(xDistance - yDistance)&&(xDistance - yDistance)<5) {//点击不拦截
                        return false;
                    }
                    if (xDistance>yDistance){//左右滑动不拦截
                        return false;
                    }
                    if (xDistance<yDistance){//上下滑动拦截
                        return true;
                    }
                }else{
                    return false;
                }
//                return isNeedScroll;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null != mTargetView && null != secondTargetView) {
            if (mTargetViewWidth <= 0 || mTargetViewHeight <= 0) {
                mTargetViewWidth = mTargetView.getMeasuredWidth();
                mTargetViewHeight = mTargetView.getMeasuredHeight();
            }
            if (mSecondTargetViewWidth <= 0 || mSecondTargetViewHeight <= 0) {
                mSecondTargetViewWidth = secondTargetView.getMeasuredWidth();
                mSecondTargetViewHeight = secondTargetView.getMeasuredHeight()+50;
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                    //手指移开，放大的View恢复原样
                    isScrolling = false;
                    callbackView();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isScrolling) {
                        if (getScrollY() == 0) {
                            mLastPosition = ev.getY();
                        } else {
                            break;
                        }
                    }
                    float value = (ev.getY() - mLastPosition) * mScaleRatio;
                    if (value < 0) {
                        break;
                    }
//                    int newHeight = (int) (mTargetView.getHeight() + Math.abs(ev.getY()) / 2.0f);/*下拉的瞬时偏移量加给Header*/
//                    if(newHeight <= drawableHeight){/*限制最大范围*/
//                        mTargetView.getLayoutParams().height = newHeight;/*让新的高度生效*/
//                        mTargetView.requestLayout();
//                    }
                    isScrolling = true;
                    updateTargetViewValue(value);
                    updateSecondTargetViewValue(value);
                    return true;
                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * View恢复到最初状态动画
     */
    private void callbackView() {
        float value = mTargetView.getMeasuredWidth() - mTargetViewWidth;
        float valueSecond = secondTargetView.getMeasuredWidth() - mTargetViewWidth;
        ValueAnimator animator = ValueAnimator.ofFloat(value, 0f);
        ValueAnimator animatorSecond = ValueAnimator.ofFloat(valueSecond, 0f);
        animator.setDuration((long) (value * mCallbackSpeed));
        animatorSecond.setDuration((long) (value * mCallbackSpeed));
        animator.setInterpolator(new DecelerateInterpolator());
        animatorSecond.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        animatorSecond.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateSecondTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        animator.start();
        animatorSecond.start();
    }

    /**
     * 更新View的宽高属性值
     */
    private void updateTargetViewValue(float value) {
        if (null == mTargetView) {
            return;
        }
        if (mTargetViewWidth <= 0 || mTargetViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
        if (null != lp) {
//            lp.width = (int) (mTargetViewWidth + value);
            lp.width = (int) (mTargetViewWidth );
            lp.height = (int) (mTargetViewHeight * ((mTargetViewWidth + value) / mTargetViewWidth)+(value*0.2));
            if (lp instanceof MarginLayoutParams) {
                ((MarginLayoutParams) lp).setMargins(-(lp.width - mTargetViewWidth) / 2, 0, 0, 0);
            }
            mTargetView.setLayoutParams(lp);
        }
    }
    /**
     * 更新View的宽高属性值
     */
    private void updateSecondTargetViewValue(float value) {
        if (null == secondTargetView) {
            return;
        }
        if (mSecondTargetViewWidth <= 0 || mSecondTargetViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = secondTargetView.getLayoutParams();
        if (null != lp) {
            lp.width = (int) (mSecondTargetViewWidth );
//            lp.width = (int) (mSecondTargetViewWidth + value);
            lp.height = (int) (mSecondTargetViewHeight * ((mSecondTargetViewWidth + value) / mSecondTargetViewWidth)+(value*0.2))-50;
            if (lp instanceof MarginLayoutParams) {
                ((MarginLayoutParams) lp).setMargins(-(lp.width - mSecondTargetViewWidth) / 2, 0, 0, 0);
            }
            secondTargetView.setLayoutParams(lp);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //滑动到顶部，速度很快，开启动画
        if (t == 0 && isCanOverScroll) {
            zoomAnimator();
            zoomSecondAnimator();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 先放大后恢复动画
     */
    private void zoomAnimator() {
        float value = mTargetViewWidth * mScaleRatio;
        ValueAnimator enlarge = ValueAnimator.ofFloat(0f, value);
        enlarge.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        ValueAnimator narrow = ValueAnimator.ofFloat(value, 0f);
        narrow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration((long) (value * mCallbackSpeed));
        animationSet.playSequentially(enlarge, narrow);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.start();
    }/**
     * 先放大后恢复动画
     */
    private void zoomSecondAnimator() {
        float value = mSecondTargetViewWidth * mScaleRatio;
        ValueAnimator enlarge = ValueAnimator.ofFloat(0f, value);
        enlarge.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateSecondTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        ValueAnimator narrow = ValueAnimator.ofFloat(value, 0f);
        narrow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateSecondTargetViewValue((float) animation.getAnimatedValue());
            }
        });
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration((long) (value * mCallbackSpeed));
        animationSet.playSequentially(enlarge, narrow);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.start();
    }

}
