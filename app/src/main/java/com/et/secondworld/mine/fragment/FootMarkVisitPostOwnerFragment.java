package com.et.secondworld.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.mine.adapter.MineFootMarkVisitPostOwnerRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class FootMarkVisitPostOwnerFragment extends BaseFragment {

    @BindView(R.id.rv_footmark_visit_post_owner)
    RecyclerView rvFootMarkVisitPostOwner;
    private MineFootMarkVisitPostOwnerRVAdapter rvAdapter;
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_footmark_visit_post_owner,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initRecycleView();
    }


    private void initRecycleView(){
        rvAdapter = new MineFootMarkVisitPostOwnerRVAdapter();
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }
        rvFootMarkVisitPostOwner.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFootMarkVisitPostOwner.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }
}
