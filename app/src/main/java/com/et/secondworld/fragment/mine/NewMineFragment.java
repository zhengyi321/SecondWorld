package com.et.secondworld.fragment.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/13
 **/
public class NewMineFragment extends BaseFragment {
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_mine,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
