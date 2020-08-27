package com.et.secondworld.fragment.forum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/9
 **/
public class ForumTwoFragment extends BaseFragment {


    @BindView(R.id.rv_forum_one)
    RecyclerView rvForumOne;
    View view;
    private ForumTwoRVAdapter rvAdapter;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum_one,container,false);
        return view;
    }

    @Override
    public void initView() {
        if(view != null){
            ButterKnife.bind(this,view);
        }
        initRecycleView();
    }

    private void initRecycleView(){
        rvAdapter = new ForumTwoRVAdapter();
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }
        rvForumOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvForumOne.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }
}
