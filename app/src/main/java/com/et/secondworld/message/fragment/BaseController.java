package com.et.secondworld.message.fragment;

import android.app.Activity;
import android.view.View;

/**
 * Created by az on 2017/4/25.
 */

public abstract class BaseController {

    protected Activity activity;

    protected View view;

    protected abstract void init();

}
