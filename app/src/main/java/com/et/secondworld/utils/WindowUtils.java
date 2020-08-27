package com.et.secondworld.utils;

import android.app.Activity;

/**
 * Created by az on 2017/5/18.
 */

public class WindowUtils {

    private Activity activity;
    public WindowUtils(Activity activity1){
        activity = activity1;
    }

    public float getWindowWidth(){

        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public float getWindowHeight(){
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }
}
