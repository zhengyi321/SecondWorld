package com.et.secondworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.adapter.SearchHistoryRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/14
 **/
public class SearchHistoryActivity extends AppCompatActivity {


    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    private SearchHistoryRVAdapter rvAdapter;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_history);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initRV();

    }
    private void initRV(){
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 8;i++){
            dataList.add("");
        }
        rvAdapter = new SearchHistoryRVAdapter();

        rvSearchHistory.setLayoutManager(new LinearLayoutManager(this));
        rvSearchHistory.setAdapter(rvAdapter);

        rvAdapter.replaceAll(dataList);
    }
}
