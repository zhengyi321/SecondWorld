package com.et.secondworld.message.fragment;

import android.view.View;


import butterknife.ButterKnife;

/**
 * Created by az on 2017/5/25.
 */

public class ChatMessageFragmentController extends BaseController {

    public ChatMessageFragmentController(View view1){
        view = view1;
        init();
    }

    @Override
    protected void init() {
        ButterKnife.bind(this,view);

    }
}
