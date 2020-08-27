package com.et.secondworld.widget.timeselect.lib;

import android.view.MotionEvent;

final class ProCityAreaLoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final ProCityAreaWheelView loopView;

    ProCityAreaLoopViewGestureListener(ProCityAreaWheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
