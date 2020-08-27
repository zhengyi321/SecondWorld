package com.et.secondworld.fragment.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.bean.GetTradeBean;
import com.et.secondworld.network.TradeNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetFindBean;
import com.et.secondworld.network.ForumPostNetWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.et.secondworld.application.MyApplication.instance;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/9
 **/
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance() {


        FindFragment firstFragment = new FindFragment();

        // Get arguments passed in, if any
        Bundle args = firstFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putInt("aa", 0);
        firstFragment.setArguments(args);

        return firstFragment;
    }
    public FindFragment(){
        super();
    }
    @BindView(R.id.rv_find)
    RecyclerView rvFind;
    private long clickTime = 0;
    @OnClick(R.id.iv_find_map)
    public void ivFindMapOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
//            checkLogin();
            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(view.getContext(), TecentMapViewActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.rfl_find)
    SmartRefreshLayout rflFind;
    @BindView(R.id.pb2_find)
    ProgressBar pb2Find;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.iv_find_loading)
    ImageView ivFindLoading;
    @BindView(R.id.rly_find_loading)
    RelativeLayout rlyFindLoading;
    @BindView(R.id.sp_find)
    Spinner spFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    View view;
    private List<GetFindBean.ListBean> dataList1 = new ArrayList<>();
    private FindRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 20;
    private int flag = 0;
    private String town = "";
    private String distr = "";
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find,container,false);
        return view;
    }

    @Override
    public void initView() {
        if(view != null){
            ButterKnife.bind(this,view);
        }
        rlyFindLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFindLoading);
        initRecycleView();
        initSpinner();
        initData();
    }

    private void initSpinner(){
        TradeNetWork tradeNetWork = new TradeNetWork();
        Map<String,Object> map = new HashMap<>();
        tradeNetWork.getTradeFromNet(map, new Observer<GetTradeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetTradeBean getTradeBean) {
                List<GetTradeBean.ListBean> listBeanList = getTradeBean.getList();
                String[] spinnerItems = new String[listBeanList.size()];
                for(int i=0;i<listBeanList.size();i++){
                    spinnerItems[i] = listBeanList.get(i).getTrade();
                }
//                final String[] spinnerItems = {"全部", "关注的店铺"};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spFind.setAdapter(spinnerAdapter);
                spFind.setSelection(0, true);
            }
        });


    }

    private void initRecycleView(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String shopId = xcCacheManager.readCache(xcCacheSaveName.shopId);
         town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
         distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        rvAdapter = new FindRVAdapter(dataList1);
        rvFind.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFind.setAdapter(rvAdapter);
        rvFind.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflFind.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page = 1;
                        flag = 0;
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("town",town);
//                        Log.d("find111",town);
                        map.put("distr",distr+",");
                        map.put("accountid",shopId);
                        Log.d("town111",town+" "+distr+","+shopId);
                        forumPostNetWork.getFindFromNet(map, new Observer<GetFindBean>() {
                            @Override
                            public void onCompleted() {
//                                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
//                                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(GetFindBean getFindBean) {
//                                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();
                                rlyFindLoading.setVisibility(View.GONE);
                                if(getFindBean.getIssuccess().equals("1")){
                                    List<GetFindBean.ListBean> dataList = getFindBean.getList();
                                    rvAdapter.replaceAll(dataList);

//                                        if(dataList.size() != limit) {
////                        datalist11.add(new );
//                                            dataList.add(new GetFindBean.ListBean() );
////                                            rvAdapter.isNoMoreData(true);
//                                            rvAdapter.replaceAll(dataList);
//                                            llySplitView.setVisibility(View.VISIBLE);
//                                            pb2Find.setVisibility(View.GONE);
//                                        }else {
//                                            llySplitView.setVisibility(View.GONE);
//                                            pb2Find.setVisibility(View.VISIBLE);
////                                            rvAdapter.isNoMoreData(false);
//                                            rvAdapter.replaceAll(dataList);
//                                        }

                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },000);

            }
        });

        rflFind.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        map.put("town",town);
                        map.put("distr",distr+",");
                        map.put("accountid",shopId);
                        Log.d("town111",town+" "+distr+",");
                        forumPostNetWork.getFindFromNet(map, new Observer<GetFindBean>() {
                            @Override
                            public void onCompleted() {
//                                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
//                                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(GetFindBean getFindBean) {
//                                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();
                                rlyFindLoading.setVisibility(View.GONE);
                                if(getFindBean.getIssuccess().equals("1")){
                                    /*rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                    if(dataList.size() > 0){
                                        refreshLayout.finishLoadMore();
                                    }else {
                                        refreshLayout.finishLoadMore(0,true,false);
                                        refreshLayout.finishLoadMore(0,true,true);
                                    }*/
                                    List<GetFindBean.ListBean> dataList = getFindBean.getList();
                                    if(dataList.size() != limit) {
//                                        dataList.add(new GetFindBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
//
//                                        refreshLayout.finishLoadMore();
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pb2Find.setVisibility(View.GONE);
                                        if(flag == 0){
                                            refreshLayout.finishLoadMore();
                                            refreshLayout.finishLoadMoreWithNoMoreData();

                                            flag = 1;
                                        }else {
                                            refreshLayout.finishLoadMoreWithNoMoreData();
                                        }
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pb2Find.setVisibility(View.VISIBLE);
                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMore();
                                    }

                                }
                            }
                        });

                    }
                },000);
            }
        });

    }

    private void initData(){
        ivFindLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFindLoading);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String shopId = xcCacheManager.readCache(xcCacheSaveName.shopId);
        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        page = 1;
        Map<String,String> map = new HashMap<>();
        map.put("page",""+page);
        map.put("limit",""+limit);
        map.put("town",town);
        map.put("distr",distr+",");
        map.put("accountid",shopId);
        forumPostNetWork.getFindFromNet(map, new Observer<GetFindBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(getContext(),"onError"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(GetFindBean getFindBean) {
//                Toast.makeText(getContext(),"zz"+getFindBean.getMsg(),Toast.LENGTH_LONG).show();
                List<GetFindBean.ListBean> dataList = getFindBean.getList();
                if(dataList.size() == 0){
                    tvFind.setVisibility(View.VISIBLE);
                }else {
                    tvFind.setVisibility(View.GONE);
                }
                rvAdapter.replaceAll(dataList);
                rlyFindLoading.setVisibility(View.GONE);
//                if(getFindBean.getIssuccess().equals("1")){
//                    if(dataList.size() != limit) {
////                        datalist11.add(new );
//                        dataList.add(new GetFindBean.ListBean() );
////                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(dataList);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pb2Find.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pb2Find.setVisibility(View.VISIBLE);
////                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(dataList);
//                    }
////                    rvAdapter.replaceAll(dataList);
//                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String shopId = xcCacheManager.readCache(xcCacheSaveName.shopId);
        town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
        distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        Log.d("find111",town+","+distr);
        initData();
    }
}
