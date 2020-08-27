package com.et.secondworld.fragment.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.forum.ForumModulSelectTwoActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/9
 **/
public class ForumOneFragment extends BaseFragment {


    @BindView(R.id.rv_forum_one)
    RecyclerView rvForumOne;
    @BindView(R.id.rly_forum_one_add)
    RelativeLayout rlyForumOneAdd;
    @OnClick(R.id.rly_forum_one_add)
    public void rlyForumOneAddOnclick(){
        Intent intent = new Intent(view.getContext(), ForumModulSelectTwoActivity.class);
        view.getContext().startActivity(intent);
    }
    View view;
    private ForumOneRVAdapter rvAdapter;
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
        rvAdapter = new ForumOneRVAdapter();
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }
        rvForumOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvForumOne.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }
}
