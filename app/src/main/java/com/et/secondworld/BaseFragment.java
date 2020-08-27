package com.et.secondworld;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * Created by az on 2017/5/25.
 */

public abstract class BaseFragment extends Fragment {

    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = setView(inflater,container,savedInstanceState);

        initView();


        return view;
    }

    public abstract View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public  abstract void initView() ;

}
