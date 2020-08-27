package com.et.secondworld.first.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetFirstGuanZhuModuleBean;
import com.et.secondworld.first.fragment.adapter.FirstGuanZhu2RVAdapter;
import com.et.secondworld.network.ForumPostNetWork;

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
 * @Date 2020/4/9
 **/
public class FirstGuanZhuFragment extends BaseFragment {


    @BindView(R.id.rv_first_guanzhu)
    RecyclerView rvFirstGuanZhu;
    @BindView(R.id.rfl_first_guanzhu)
    SmartRefreshLayout rflFirstGuanZhu;
    @BindView(R.id.pb_first_guanzhu_loading)
    ProgressBar pbFirstGuanZhuLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.iv_first_guanzhu_loading)
    ImageView ivFirstGuanZhuLoading;
    @BindView(R.id.rly_first_guanzhu_loading)
    RelativeLayout rlyFirstGuanZhuLoading;
    @BindView(R.id.tv_first_guanzhu_weiguanzhu)
    TextView tvFirstGuanZhuWeiGuanZhu;
    private int page = 1;
    private int limit = 5;
    private List<GetFirstGuanZhuModuleBean.ListBean> dataList1 = new ArrayList<>();
    View view;
    private FirstGuanZhu2RVAdapter rvAdapter;
    private String accountid = "";
    private String shopid = "";
//    private boolean isFinishFlag = false;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_guanzhu,container,false);
        return view;
    }

    @Override
    public void initView() {
        if(view != null){
            ButterKnife.bind(this,view);
        }
        rlyFirstGuanZhuLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstGuanZhuLoading);
        initRecycleView();
        initData();
    }

    private void initRecycleView(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        rvAdapter = new FirstGuanZhu2RVAdapter(dataList1);
       /* ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }*/
        rvFirstGuanZhu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFirstGuanZhu.setAdapter(rvAdapter);

        rvFirstGuanZhu.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflFirstGuanZhu.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page = 1;
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("accountid",""+accountid);
                        map.put("shopid",""+shopid);
//                        Log.d("accountid111",accountid);
//                        Log.d("accountid111",shopid);
                        forumPostNetWork.getGuanZhuFromNet(map, new Observer<GetFirstGuanZhuModuleBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetFirstGuanZhuModuleBean getGuanZhuBean) {
                                if (getGuanZhuBean.getIssuccess().equals("1")) {
//                                    rvAdapter.replaceAll(getGuanZhuBean.getList());
                                    List<GetFirstGuanZhuModuleBean.ListBean> dataList = getGuanZhuBean.getList();
                                    if(dataList.size() != 0){
                                        tvFirstGuanZhuWeiGuanZhu.setVisibility(View.GONE);
                                    }else {
                                        tvFirstGuanZhuWeiGuanZhu.setVisibility(View.VISIBLE);
                                    }
                                    rvAdapter.replaceAll(dataList);

//                                    if(dataList.size() != limit) {
////                        datalist11.add(new );
////                                        dataList.add(new GetFirstGuanZhuModuleBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(dataList);
//                                        llySplitView.setVisibility(View.VISIBLE);
//                                        pbFirstGuanZhuLoading.setVisibility(View.GONE);
//                                    }else {
//                                        llySplitView.setVisibility(View.GONE);
//                                        pbFirstGuanZhuLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(dataList);
//                                    }

                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },0000);
            }
        });
        rflFirstGuanZhu.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("accountid",""+accountid);
                        map.put("shopid",""+shopid);
                        forumPostNetWork.getGuanZhuFromNet(map, new Observer<GetFirstGuanZhuModuleBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetFirstGuanZhuModuleBean getGuanZhuBean) {
                                if (getGuanZhuBean.getIssuccess().equals("1")) {
                                    List<GetFirstGuanZhuModuleBean.ListBean> dataList = getGuanZhuBean.getList();
                                    if(dataList.size() != limit) {
//                                        if(isFinishFlag){
//                                            refreshLayout.finishLoadMoreWithNoMoreData();
//                                        }else {
//                                            dataList.add(new GetFirstGuanZhuModuleBean.ListBean());
//                                            rvAdapter.isNoMoreData(true);
                                            rvAdapter.addData(rvAdapter.getItemCount(), dataList);
                                            refreshLayout.finishLoadMoreWithNoMoreData();
//                                            isFinishFlag = true;
//                                        }
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbFirstGuanZhuLoading.setVisibility(View.GONE);
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbFirstGuanZhuLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMore();
                                    }
//                                    rvAdapter.addData(rvAdapter.getItemCount(),getGuanZhuBean.getList());
//                                    if(getGuanZhuBean.getList().size() > 0){
//                                        refreshLayout.finishLoadMore();
//                                    }else {
//                                        refreshLayout.finishLoadMore(0,true,false);
//                                        refreshLayout.finishLoadMore(0,true,true);
//                                    }
                                }
                            }
                        });

                    }
                },0000);
            }
        });

    }

    private void initData() {
        rlyFirstGuanZhuLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstGuanZhuLoading);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String, String> map = new HashMap<>();
        page = 1;
        map.put("page", "" + page);
        map.put("limit", "" + limit);
        map.put("accountid",""+accountid);
        map.put("shopid",""+shopid);
        forumPostNetWork.getGuanZhuFromNet(map, new Observer<GetFirstGuanZhuModuleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetFirstGuanZhuModuleBean getGuanZhuBean) {
                if (getGuanZhuBean.getIssuccess().equals("1")) {
//                    rvAdapter.replaceAll(getGuanZhuBean.getList());
                    List<GetFirstGuanZhuModuleBean.ListBean> dataList = getGuanZhuBean.getList();
                    if(dataList.size() != 0){
                        tvFirstGuanZhuWeiGuanZhu.setVisibility(View.GONE);
                    }else {
                        tvFirstGuanZhuWeiGuanZhu.setVisibility(View.VISIBLE);
                    }
                    rvAdapter.replaceAll(dataList);
                    rlyFirstGuanZhuLoading.setVisibility(View.GONE);
//                        if(dataList.size() != limit) {
////                        datalist11.add(new );
//                            dataList.add(new GetFirstGuanZhuModuleBean.ListBean() );
//                            rvAdapter.isNoMoreData(true);
//                            rvAdapter.replaceAll(dataList);
//                            llySplitView.setVisibility(View.VISIBLE);
//                            pbFirstGuanZhuLoading.setVisibility(View.GONE);
//                        }else {
//                            llySplitView.setVisibility(View.GONE);
//                            pbFirstGuanZhuLoading.setVisibility(View.VISIBLE);
//                            rvAdapter.isNoMoreData(false);
//                            rvAdapter.replaceAll(dataList);
//                        }
//                    rvAdapter.replaceAll(dataList);

                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
//        initData();
    }
}
