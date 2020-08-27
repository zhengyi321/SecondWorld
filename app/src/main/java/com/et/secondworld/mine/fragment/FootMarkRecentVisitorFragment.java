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
import com.et.secondworld.bean.GetVisitorBean;
import com.et.secondworld.mine.adapter.MineFootMarkRecentVisitorRVAdapter;
import com.et.secondworld.network.VisitorNetWork;
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
public class FootMarkRecentVisitorFragment extends BaseFragment {

    @BindView(R.id.rv_footmark_recent_visitor)
    RecyclerView rvFootMarkRecentVisitor;
    @BindView(R.id.rfl_footmark_recent_visitor)
    SmartRefreshLayout rflFootMarkRecentVisitor;
    @BindView(R.id.tv_footmark_recent_visitor)
    TextView tvFootMarkRecentVisitor;
    private MineFootMarkRecentVisitorRVAdapter rvAdapter;
    View view;
    private int page = 1;
    private int limit = 10;
    private String account = "";
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_footmark_recent_visitor,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initRecycleView();
        initData();
    }


    private void initRecycleView(){
        rvAdapter = new MineFootMarkRecentVisitorRVAdapter();
       /* ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }*/
        rvFootMarkRecentVisitor.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFootMarkRecentVisitor.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflFootMarkRecentVisitor.setOnRefreshListener(new OnRefreshListener() {
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

        rflFootMarkRecentVisitor.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,Object> map = new HashMap<>();

                        VisitorNetWork visitorNetWork = new VisitorNetWork();

                        /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
                        page++;
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        visitorNetWork.getVisitorToNet(map, new Observer<GetVisitorBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetVisitorBean getVisitorBean) {
                                if(getVisitorBean.getIssuccess().equals("1")){

                                    rvAdapter.addData(rvAdapter.getItemCount(),getVisitorBean.getList());


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
        VisitorNetWork visitorNetWork = new VisitorNetWork();
        Map<String,Object> map = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
         account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        visitorNetWork.getVisitorToNet(map, new Observer<GetVisitorBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetVisitorBean getVisitorBean) {
                if(getVisitorBean.getIssuccess().equals("1")){
                    if(getVisitorBean.getList().size() != 0){
                        tvFootMarkRecentVisitor.setVisibility(View.GONE);
                    }else {
                        tvFootMarkRecentVisitor.setVisibility(View.VISIBLE);
                    }
                    rvAdapter.replaceAll(getVisitorBean.getList());


                }
            }
        });
    }
}
