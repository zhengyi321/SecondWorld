package com.et.secondworld.widget.scrollview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class ZoomInScrollView extends ScrollView {

    private View mHeaderView;
    private int mHeaderWidth;
    private int mHeaderHeight;
    private View inner;// 孩子

    private float y;// 坐标

    private Rect normal = new Rect();// 矩形空白

    // 是否正在下拉
    private boolean mIsPulling;
    // 记录首次按下位置
    private int mLastY;

    // 最大的放大倍数
    private float mScaleTimes = 2.0f;
    // 滑动放大系数：系数越大，滑动时放大程度越大
    private float mScaleRatio = 0.4f;
    // 回弹时间系数：系数越小，回弹越快
    private float mReplyRatio = 0.5f;

    // 当前坐标值
    private float currentX = 0;
    private float currentY = 0;
    // 移动坐标值
    private float distanceX = 0;
    private float distanceY = 0;
    // 最后坐标值
    private float lastX = 0;
    private float lastY = 0;
    // 上下滑动标记
    private boolean upDownSlide = false;

    private OnScrollListener onScrollListener;

    public ZoomInScrollView(Context context) {
        this(context, null);
    }

    public ZoomInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 设置不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
        View child = getChildAt(0);
        inner = getChildAt(0);// 获取其孩子
        if (child != null && child instanceof ViewGroup) {
            // 获取默认第一个子View
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                ViewGroup vg1 =(ViewGroup)  vg.getChildAt(0);
                if (vg1.getChildAt(0) != null) {
                    mHeaderView = vg1.getChildAt(0);
//                    ViewGroup vg2 =(ViewGroup)  vg1.getChildAt(0);
//                    if (vg2.getChildAt(0) != null) {
//                        mHeaderView = vg2.getChildAt(0);
//                    }
                }
//                mHeaderView = vg.getChildAt(0);
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
//        mHeaderHeight = ((ImageView) mHeaderView).getDrawable().getIntrinsicHeight();;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        currentX = ev.getX();
        currentY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                if (Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > 12) {
                    upDownSlide = true;
                }
                break;
        }

        lastX = currentX;
        lastY = currentY;

        if (upDownSlide && mHeaderView != null) {
            commOnTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @Description 触摸事件
     */
    private void commOnTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                // 手指离开后头部恢复图片
                mIsPulling = false;
                replyView();
                clear();
                if (isNeedAnimation()) {
                    animation();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();// 获取点击y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                int deltaY = (int) (preY - nowY);// 获取滑动距离

                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        // 填充矩形，目的：就是告诉this:我现在已经有了，你松开的时候记得要执行回归动画.
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }
                    // 移动布局
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
                            inner.getRight(), inner.getBottom() - deltaY / 2);
                }
                if (!mIsPulling) {
                    // 第一次下拉
                    if (getScrollY() == 0) {
                        // 滚动到顶部时记录位置，否则正常返回
                        mLastY = (int) ev.getY();
                    } else {
                        break;
                    }
                }

                int distance = (int) ((ev.getY() - mLastY) * mScaleRatio);
                // 当前位置比记录位置要小时正常返回
                if (distance < 0) {
                    break;
                }
                mIsPulling = true;
                setZoom(distance);

                break;
        }

    }

    /**
     * @Description 头部缩放
     */
    private void setZoom(float s) {
        float scaleTimes = (float) ((mHeaderWidth + s) / (mHeaderWidth * 1.0));
        // 如超过最大放大倍数则直接返回
        if (scaleTimes > mScaleTimes) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.width = (int) (mHeaderWidth + s);
        layoutParams.height = (int) (mHeaderHeight * ((mHeaderWidth + s) / mHeaderWidth));
        // 设置控件水平居中
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mHeaderWidth) / 2, 0, 0, 0);
        mHeaderView.setLayoutParams(layoutParams);
    }

    /**
     * @Description 回弹动画
     */
    private void replyView() {
        final float distance = (mHeaderView.getMeasuredWidth() - mHeaderWidth);

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    /**
     * @Description 清除属性值
     */
    private void clear() {
        lastX = 0;
        lastY = 0;
        distanceX = 0;
        distanceY = 0;
        upDownSlide = false;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();

    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * @Description 滑动监听
     */
    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }











    /***
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }



    /***
     * 开启动画移动
     */
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(300);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();// 清空矩形
    }

    /***
     * 是否需要开启动画
     * <p>
     * 如果矩形不为空，返回true，否则返回false.
     *
     * @return
     */
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /***
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的高度
     * getHeight()：获取的是当前控件在屏幕中显示的高度
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
}