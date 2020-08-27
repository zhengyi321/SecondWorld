package com.et.secondworld.widget.smartrefresh.foot;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/21
 **/

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.et.secondworld.R;

/**
 * @author : zlf
 * date    : 2019-04-19
 * github  : https://github.com/mamumu
 * blog    : https://www.jianshu.com/u/281e9668a5a6
 */
public class MRefreshFooter extends LinearLayout implements RefreshFooter {

    private ImageView mImage;
    private TextView tvState;
    private ImageView ivSplitView;
    private LinearLayout llySplitView;
    private RefreshKernel mRefreshKernel;
//    private Animation mAnim;
    protected boolean mNoMoreData = false;
    public MRefreshFooter(Context context) {
        this(context, null, 0);
    }

    public MRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.m_refresh_footer, this);
        mImage = view.findViewById(R.id.iv_refresh_footer);
        ivSplitView = view.findViewById(R.id.iv_refresh_footer_splitview);
        llySplitView = view.findViewById(R.id.lly_splitview);
        tvState = view.findViewById(R.id.tv_refresh_footer);

//        mAnim = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
//        LinearInterpolator linearInterpolator = new LinearInterpolator();
//        mAnim.setInterpolator(linearInterpolator);

    }


    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        Log.d("loadstatenoMoreData122",noMoreData+"");
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
//            final View arrowView = mArrowView;
            if (noMoreData) {
                mImage.setVisibility(GONE);
                llySplitView.setVisibility(VISIBLE);
//                tvState.setText("已经到底啦");
//                arrowView.setVisibility(GONE);
            } else {
//                mTitleText.setText(mTextPulling);
//                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        //控制是否稍微上滑动就刷新
        kernel.getRefreshLayout().setEnableAutoLoadMore(false);
        mRefreshKernel = kernel;
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (mRefreshKernel != null) {
            mRefreshKernel.setState(RefreshState.None);
            //onReleased 的时候 调用 setState(RefreshState.None); 并不会立刻改变成 None
            //而是先执行一个回弹动画，LoadFinish 是介于 Refreshing 和 None 之间的状态
            //LoadFinish 用于在回弹动画结束时候能顺利改变为 None
            mRefreshKernel.setState(RefreshState.LoadFinish);
        }
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
       /* if(mAnim != null && mAnim.hasStarted() && !mAnim.hasEnded()){
            mAnim.cancel();
            mImage.clearAnimation();
        }*/
//        Log.d("loadstate122",success+"");
        mImage.clearAnimation();
        llySplitView.clearAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//        Log.d("loadstate1",newState+"");
        switch (newState) {
            case None:

                break;
            case PullUpToLoad:
              /*  if (mAnim != null) {
                    mImage.startAnimation(mAnim);
                }*/
//                tvState.setText(" ");
                break;
            case Loading:

            case LoadReleased:

                break;
            case ReleaseToLoad:
                break;
            case PullUpCanceled:

                break;
            case LoadFinish:
//                tvState.setText(" ");
                break;
            case PullDownToRefresh:
                mImage.setVisibility(VISIBLE);
                llySplitView.setVisibility(GONE);
                break;

        }
    }
}