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

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetFourBean;
import com.et.secondworld.first.fragment.adapter.FirstModuleFour2RVAdapter;
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
 * @Date 2020/4/15
 **/
public class FirstModuleFourFragment extends BaseFragment {


    @BindView(R.id.rv_first_module_four)
    RecyclerView rvFirstModuleFour;
    @BindView(R.id.rfl_first_module_four)
    SmartRefreshLayout rflFirstModuleFour;
    @BindView(R.id.pb_first_module_four_loading)
    ProgressBar pbFirstModuleFourLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.iv_first_module_four_loading)
    ImageView ivFirstModuleFourLoading;
    @BindView(R.id.rly_first_module_four_loading)
    RelativeLayout rlyFirstModuleFourLoading;
    @BindView(R.id.tv_first_module_four)
    TextView tvFirstModuleFour;
    /*@BindView(R.id.rvh_first_module_four)
    RecyclerViewHeader rvhFirstModuleFour;*/
    private FirstModuleFour2RVAdapter rvAdapter;
    private List<GetFourBean.ListBean> dataList1 = new ArrayList<>();
    private int page = 1;
    private int limit = 20;
    private String city ="";
    View view;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_module_four,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        rlyFirstModuleFourLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstModuleFourLoading);
        initRecycleView();
        initData();
    }

    private void initRecycleView(){
        rvAdapter = new FirstModuleFour2RVAdapter(dataList1);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        String accountid = xcCacheManager.readCache(xcCacheSaveName.shopId);
         city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
        if(city != null && city.indexOf("市") > 0) {
            city = city.substring(0, city.indexOf("市"));
        }
        rvFirstModuleFour.setLayoutManager(new LinearLayoutManager(getActivity()));
//        RecyclerViewHeader header = RecyclerViewHeader.inflate(view.getContext(), R.layout.activity_first_module_four_rv_header);
//        rvhFirstModuleFour.attachTo(rvFirstModuleFour);
        rvFirstModuleFour.setAdapter(rvAdapter);
        rvFirstModuleFour.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflFirstModuleFour.setOnRefreshListener(new OnRefreshListener() {
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
                        map.put("town",city+",");
                        Log.d("aaa111",city+",");
                        map.put("accountid",accountid);
                        forumPostNetWork.getFourFromNet(map, new Observer<GetFourBean>() {
                            @Override
                            public void onCompleted() {
//                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
//                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(GetFourBean getFourBean) {
//                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();
//                                List<GetFourBean.ListBean> dataList = getFourBean.getList();

                                if(getFourBean.getIssuccess().equals("1")){
//                                    rvAdapter.replaceAll(dataList);
                                    List<GetFourBean.ListBean> dataList = new ArrayList<>();
                                    dataList.add(new GetFourBean.ListBean());
                                    dataList.addAll(getFourBean.getList());
                                    rvAdapter.isSixOne = 0;
                                    rvAdapter.replaceAll(dataList);

//                                    if(dataList.size() == 0) {
////                        datalist11.add(new );
//                                        dataList.add(new GetFourBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(dataList);
//                                        llySplitView.setVisibility(View.VISIBLE);
//                                        pbFirstModuleFourLoading.setVisibility(View.GONE);
//                                    }else {
//                                        llySplitView.setVisibility(View.GONE);
//                                        pbFirstModuleFourLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(dataList);
//                                    }

                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },000);
            }
        });

        rflFirstModuleFour.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        map.put("town",city+",");
                        map.put("accountid",accountid);
                        forumPostNetWork.getFourFromNet(map, new Observer<GetFourBean>() {
                            @Override
                            public void onCompleted() {
//                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
//                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(GetFourBean getFourBean) {
//                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();
//                                List<GetFourBean.ListBean> dataList = getFourBean.getList();

                                if(getFourBean.getIssuccess().equals("1")){


                                    List<GetFourBean.ListBean> dataList = getFourBean.getList();
                                    if(dataList.size() == 0) {
//                                        dataList.add(new GetFourBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbFirstModuleFourLoading.setVisibility(View.GONE);
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbFirstModuleFourLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMore();
                                    }

                                    /*rvAdapter.addData(rvAdapter.getItemCount(),dataList);
//                                    refreshLayout.finishLoadMore();
                                    if(dataList.size() > 0){
                                        refreshLayout.finishLoadMore();
                                    }else {
//                                        refreshLayout.finishLoadMore(0,true,false);
                                        refreshLayout.finishLoadMore(0,false,true);
//                                        refreshLayout.finishLoadMore();
                                    }*/
                                }
                            }
                        });
//

                    }
                },000);
            }
        });
//        rflFirstModuleFour.autoRefresh();
    }

    private void initData(){
        rlyFirstModuleFourLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstModuleFourLoading);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        String accountid = xcCacheManager.readCache(xcCacheSaveName.shopId);
         city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
         if(city != null &&city.indexOf("市") > 0) {
             city = city.substring(0, city.indexOf("市"));
         }
//         Log.d("city11",city);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        page = 1;
        Map<String,String> map = new HashMap<>();
        map.put("page",""+page);
        map.put("limit",""+limit);
        map.put("town",city+",");
        map.put("accountid",accountid);
        forumPostNetWork.getFourFromNet(map, new Observer<GetFourBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(GetFourBean getFourBean) {
//                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();


                if(getFourBean.getIssuccess().equals("1")){
                    List<GetFourBean.ListBean> dataList = new ArrayList<>();
                    dataList.add(new GetFourBean.ListBean());
                    dataList.addAll(getFourBean.getList());
                    if(dataList.size() == 0){
                        tvFirstModuleFour.setVisibility(View.VISIBLE);
                    }else {
                        tvFirstModuleFour.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(dataList);
                    rlyFirstModuleFourLoading.setVisibility(View.GONE);
//                    if(dataList.size() == 0) {
////                        datalist11.add(new );
//                        dataList.add(new GetFourBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(dataList);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pbFirstModuleFourLoading.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pbFirstModuleFourLoading.setVisibility(View.VISIBLE);
//                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(dataList);
//                    }
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
//        initData();
//        Log.d("modulefour11",city);
    }
}
