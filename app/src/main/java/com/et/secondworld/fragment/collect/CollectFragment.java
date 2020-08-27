package com.et.secondworld.fragment.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;

public class CollectFragment extends BaseFragment {
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_first_page,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
