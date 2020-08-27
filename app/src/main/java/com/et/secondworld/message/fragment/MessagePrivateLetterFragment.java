package com.et.secondworld.message.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.message.adapter.MessagePrivateLetterRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MessagePrivateLetterFragment extends BaseFragment {

    @BindView(R.id.rv_message_private_letter)
    RecyclerView rvMessagePrivateLetter;
    private MessagePrivateLetterRVAdapter rvAdapter;
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_private_letter,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initRecycleView();
    }


    private void initRecycleView(){
        rvAdapter = new MessagePrivateLetterRVAdapter();
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }
        rvMessagePrivateLetter.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessagePrivateLetter.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }
}
