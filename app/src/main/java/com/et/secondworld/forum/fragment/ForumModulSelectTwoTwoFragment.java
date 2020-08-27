package com.et.secondworld.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/10
 **/
public class ForumModulSelectTwoTwoFragment extends BaseFragment {

    View view ;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum_select_modul_two_two,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
