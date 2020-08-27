package com.et.secondworld.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetCollectBean;
import com.et.secondworld.mine.adapter.MineCollectRVAdapter;
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
public class MineCollectFragment extends BaseFragment {

    @BindView(R.id.rv_mine_collect)
    RecyclerView rvMineCollect;
    private MineCollectRVAdapter rvAdapter;
    @BindView(R.id.rfl_mine_collect)
    SmartRefreshLayout rflMineCollect;
    @BindView(R.id.pb_mine_collect)
    ProgressBar pbMineCollect;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.tv_mine_collect)
    TextView tvMineCollect;
    private int page = 1;
    private int limit = 6;
    private List<GetCollectBean.ListBean> dataList1 = new ArrayList<>();
    private String account  = "";
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    public MineCollectFragment(String articleaccount){
        account = articleaccount;
    }

    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_collect,container,false);
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


    private void initRecycleView(){
        rvAdapter = new MineCollectRVAdapter(dataList1);

        rvMineCollect.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMineCollect.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflMineCollect.setOnRefreshListener(new OnRefreshListener() {
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
                        forumPostNetWork.getCollectFromNet(map, new Observer<GetCollectBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetCollectBean getCollectBean) {
                                if(getCollectBean.getIssuccess().equals("1")){

                                    List<GetCollectBean.ListBean> datalist11 = new ArrayList<>();
                                    datalist11.addAll(getCollectBean.getList());
                                    rvAdapter.replaceAll(datalist11);
//                                    if(datalist11.size() != limit) {
////                        datalist11.add(new );
//                                        GetCollectBean.ListBean bean = new GetCollectBean.ListBean();
////                                        datalist11.add(bean);
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

        rflMineCollect.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        forumPostNetWork.getCollectFromNet(map, new Observer<GetCollectBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetCollectBean getCollectBean) {
                                if(getCollectBean.getIssuccess().equals("1")){
                                    List<GetCollectBean.ListBean> datalist11 = getCollectBean.getList();

                                    if(datalist11.size() != limit) {
//                                        datalist11.add(new GetCollectBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbMineCollect.setVisibility(View.GONE);
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                    }else {
//                                        rvAdapter.isNoMoreData(false);
                                        llySplitView.setVisibility(View.GONE);
                                        pbMineCollect.setVisibility(View.VISIBLE);
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
        map.put("account",account);
        page = 1;
        map.put("page",""+page);
        map.put("limit",""+limit);
        forumPostNetWork.getCollectFromNet(map, new Observer<GetCollectBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCollectBean getCollectBean) {
                if(getCollectBean.getIssuccess().equals("1")){

                    List<GetCollectBean.ListBean> datalist11 = new ArrayList<>();
                    datalist11.addAll(getCollectBean.getList());
                    if(datalist11.size() != 0){
                        tvMineCollect.setVisibility(View.GONE);
                    }else {
                        tvMineCollect.setVisibility(View.VISIBLE);
                    }
                    rvAdapter.replaceAll(datalist11);
//                    if(datalist11.size() != limit) {
////                        datalist11.add(new );
//                        GetCollectBean.ListBean bean = new GetCollectBean.ListBean();
////                        datalist11.add(bean);
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(datalist11);
//                    }else {
//                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(datalist11);
//                    }
//                    rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"this is onresume",Toast.LENGTH_LONG).show();
//        initData();
    }
}