package com.et.secondworld.mine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetBrowsHistoryBean;
import com.et.secondworld.mine.adapter.MineFootMarkHaveSeenInvitionRVAdapter;
import com.et.secondworld.network.FootMarkNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class FootMarkHaveSeenInvitationFragment extends BaseFragment {

    @BindView(R.id.rv_footmark_have_seen_invition)
    RecyclerView rvFootMarkHaveSeenInvition;
    @BindView(R.id.rfl_footmark_have_seen_invition)
    SmartRefreshLayout rflFootMarkHaveSeenInvition;
    @BindView(R.id.tv_footmark_have_seen_invition)
    TextView tvFootMarkHaveSeenInvition;
    private MineFootMarkHaveSeenInvitionRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 10;
    private String account = "";
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_footmark_have_seen_invition,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initRecycleView();
        initData();
    }


    private void initRecycleView(){
        rvAdapter = new MineFootMarkHaveSeenInvitionRVAdapter();
       /* ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }*/
        rvFootMarkHaveSeenInvition.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFootMarkHaveSeenInvition.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflFootMarkHaveSeenInvition.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

                        page = 1;
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },0);
            }
        });

        rflFootMarkHaveSeenInvition.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,Object> map = new HashMap<>();

                        FootMarkNetWork footMarkNetWork = new FootMarkNetWork();

                        /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
                        page++;
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        footMarkNetWork.getBrowsHistoryFromNet(map, new Observer<GetBrowsHistoryBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetBrowsHistoryBean getBrowsHistoryBean) {
                                if(getBrowsHistoryBean.getIssuccess().equals("1")){
                                    rvAdapter.addData(rvAdapter.getItemCount(),getBrowsHistoryBean.getList());
                                }
                            }
                        });


                        refreshLayout.finishLoadMore();
                    }
                },0);
            }
        });
//        rvAdapter.replaceAll(dataList);
    }
    private void initData(){
        FootMarkNetWork footMarkNetWork = new FootMarkNetWork();
        Map<String,Object> map = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        footMarkNetWork.getBrowsHistoryFromNet(map, new Observer<GetBrowsHistoryBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetBrowsHistoryBean getBrowsHistoryBean) {
                if(getBrowsHistoryBean.getIssuccess().equals("1")){
                    if(getBrowsHistoryBean.getList().size() != 0){
                        tvFootMarkHaveSeenInvition.setVisibility(View.GONE);
                    }else {
                        tvFootMarkHaveSeenInvition.setVisibility(View.VISIBLE);
                    }
                    rvAdapter.replaceAll(getBrowsHistoryBean.getList());
                }
            }
        });
    }
}
