package com.et.secondworld.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.fragment.firstpage.FirstFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetDongTaiBean;
import com.et.secondworld.mine.adapter.MineDongTaiRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/8
 **/
public class MineDongTaiFragment extends BaseFragment {

    @BindView(R.id.rv_mine_dongtai)
    RecyclerView rvMineDongTai;
    @BindView(R.id.rfl_mine_dongtai)
    SmartRefreshLayout rflMineDongTai;
    @BindView(R.id.pb_mine_dongtai)
    ProgressBar pbMineDongTai;
    @BindView(R.id.tv_mine_dongtai)
    TextView tvMineDongTai;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    private MineDongTaiRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 6;
    private List<GetDongTaiBean.ListBean> dataList1 = new ArrayList<>();
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private String account  = "";
    public MineDongTaiFragment(String articleaccount){
        account = articleaccount;
    }
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_dongtai,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);

        localBroadcastManager = LocalBroadcastManager.getInstance(view.getContext()); // 获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        initRecycleView();
        initData();
    }


    private void initRecycleView(){
        rvAdapter = new MineDongTaiRVAdapter(dataList1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvMineDongTai.setLayoutManager(linearLayoutManager);
//        rvMineDongTai.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMineDongTai.setAdapter(rvAdapter);
//        rvMineDongTai.setItemViewCacheSize(100);

        //设置下拉刷新和上拉加载监听
        rflMineDongTai.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page = 1;
//                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
//                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetDongTaiBean getDongTaiBean) {
                                if(getDongTaiBean.getIssuccess().equals("1")){
                                    List<GetDongTaiBean.ListBean> datalist11 = new ArrayList<>();
                                    datalist11.addAll(getDongTaiBean.getList());
                                    rvAdapter.replaceAll(datalist11);
//                                    if(datalist11.size() != limit) {
////                        datalist11.add(new );
////                                        datalist11.add(new GetDongTaiBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(datalist11);
//                                    }else {
//                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(datalist11);
//                                    }
                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },0);
            }
        });
        /*rvMineDongTai.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                Map<String,String> map = new HashMap<>();
                page++;
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                map.put("account",account);
                map.put("page",""+page);
                map.put("limit",""+limit);
                forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetDongTaiBean getDongTaiBean) {
                        if(getDongTaiBean.getIssuccess().equals("1")){

                            rvAdapter.addData(rvAdapter.getItemCount(),getDongTaiBean.getList());
                                    *//*try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }*//*
//                            refreshLayout.finishLoadMore();
                        }

                    }
                });
            }
        });*/
        rflMineDongTai.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page++;
//                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
//                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetDongTaiBean getDongTaiBean) {
                                if(getDongTaiBean.getIssuccess().equals("1")){

//                                    rvAdapter.addData(rvAdapter.getItemCount(),getDongTaiBean.getList());
//                                    try {
//                                        Thread.sleep(500);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
                                    List<GetDongTaiBean.ListBean> datalist11 = getDongTaiBean.getList();

                                    if(datalist11.size() != limit) {
//                                        datalist11.add(new GetDongTaiBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbMineDongTai.setVisibility(View.GONE);
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbMineDongTai.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                        refreshLayout.finishLoadMore();
                                    }
                                }

                            }
                        });



                    }
                },0);
            }
        });
    }

    private void initData(){
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
//        Log.d("zzzz1111",account);
        map.put("account",account);
        page = 1;
        map.put("page",""+page);
        map.put("limit",""+limit);
        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetDongTaiBean getDongTaiBean) {
                if(getDongTaiBean.getIssuccess().equals("1")){
                    List<GetDongTaiBean.ListBean> datalist11 = new ArrayList<>();
                    datalist11.addAll(getDongTaiBean.getList());
                    if(datalist11.size() != 0){
                        tvMineDongTai.setVisibility(View.GONE);
                    }else {
                        tvMineDongTai.setVisibility(View.VISIBLE);
                    }
                    rvAdapter.replaceAll(datalist11);
//                    if(datalist11.size() != limit) {
////                        datalist11.add(new );
//                        datalist11.add(new GetDongTaiBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(datalist11);
//                    }else {
//                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(datalist11);
//                    }
//                    rvAdapter.replaceAll(getDongTaiBean.getList());
                }
            }
        });
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            getFragment("first");
//            manager = getSupportFragmentManager();
//            initRecycleView();
            account = intent.getStringExtra("account");




//            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"this is onresume", Toast.LENGTH_LONG).show();
//        initData();
    }
}