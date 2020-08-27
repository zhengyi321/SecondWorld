package com.et.secondworld.message.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetCommentAndAt;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.message.adapter.MessageGetGoodRVAdapter;
import com.et.secondworld.network.CommentAndAtNetWork;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * @Date 2020/4/14
 **/
public class MessageGetGoodFragment extends BaseFragment {

    @BindView(R.id.rv_message_get_good)
    RecyclerView rvMessageGetGood;
    @BindView(R.id.rfl_message_get_good)
    SmartRefreshLayout rflMessageGetGood;
    MessageGetGoodRVAdapter rvAdapter;
    @BindView(R.id.tv_message_get_good_uncomment)
    TextView tvMessageGetGoodUnComment;
    View view;
    private int page = 1;
    private int limit = 10;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_get_good,container,false);
        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initRV();
    }

    private void initRV(){
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 4;i++){
//            dataList.add("");
//        }
        rvAdapter = new MessageGetGoodRVAdapter();
        rvMessageGetGood.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMessageGetGood.setAdapter(rvAdapter);
//        rvMessageGetGood.setItemViewCacheSize(100);
        rflMessageGetGood.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

                        initCommentAndAtData();
                        refreshLayout.finishRefresh();
                    }
                },00);
            }
        });

        rflMessageGetGood.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,Object> map = new HashMap<>();

                        CommentAndAtNetWork guanZhuFansNetWork = new CommentAndAtNetWork();

                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        page++;
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        guanZhuFansNetWork.getCommentAndAtFromNet(map, new Observer<GetCommentAndAt>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetCommentAndAt getFansBean) {


                                if(getFansBean.getIssuccess().equals("1")){
                                  /*  XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                                    String account = xcCacheManager.readCache(xcCacheSaveName.account);
                                    List<GetFansBean.ListBean> datalist1 = getFansBean.getList();
                                    for(int i=0;i<datalist1.size();){

                                        if(datalist1.get(i).getFollowid().equals(account)){
                                            datalist1.remove(i);
                                            continue;
                                        }
                                        i++;

                                    }*/
                                    rvAdapter.addData(rvAdapter.getItemCount(),getFansBean.getList());


                                }
                            }
                        });
                        refreshLayout.finishLoadMore();
                    }
                },0);
            }
        });
        initCommentAndAtData();
//        rvAdapter.replaceAll(dataList);
    }

    private void initCommentAndAtData(){
        CommentAndAtNetWork commentAndAtNetWork = new CommentAndAtNetWork();
        Map<String,Object> map = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
//        Log.d("good11",account);
        page = 1;
        map.put("account",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        commentAndAtNetWork.getCommentAndAtFromNet(map, new Observer<GetCommentAndAt>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCommentAndAt getCommentAndAt) {
                if(getCommentAndAt.getList().size() != 0){
                    tvMessageGetGoodUnComment.setVisibility(View.GONE);
                }else {
                    tvMessageGetGoodUnComment.setVisibility(View.VISIBLE);
                }
                rvAdapter.replaceAll(getCommentAndAt.getList());
            }
        });
    }
    @Override
    public void onResume() {

        super.onResume();
//        initCommentAndAtData();
//        CommentAndAtNetWork commentAndAtNetWork = new CommentAndAtNetWork();
//        Map<String,Object> map = new HashMap<>();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
////        Log.d("good11",account);
//        map.put("account",account);
//        commentAndAtNetWork.getCommentAndAtFromNet(map, new Observer<GetCommentAndAt>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(GetCommentAndAt getCommentAndAt) {
//                rvAdapter.replaceAll(getCommentAndAt.getList());
//            }
//        });
    }
}
