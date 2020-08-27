package com.et.secondworld.widget.timeselect.lib;

final class ProvCityAreaOnItemSelectedRunnable implements Runnable {
    final ProCityAreaWheelView loopView;

    ProvCityAreaOnItemSelectedRunnable(ProCityAreaWheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}
