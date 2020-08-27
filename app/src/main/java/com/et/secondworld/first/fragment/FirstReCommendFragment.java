package com.et.secondworld.first.fragment;

import android.content.Intent;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.CircleImgBean;
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.first.fragment.adapter.FirstReCommend2RVAdapter;
import com.et.secondworld.forum.ForumPostNormalActivity;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.imageview.RoundImageView;
import com.et.secondworld.widget.viewpage.AdCycleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/11
 **/
public class FirstReCommendFragment extends BaseFragment {

    View view;
    @BindView(R.id.adcv_first_recommend)
    AdCycleView adcMainFirstRecommend;
    @BindView(R.id.rv_first_recommend)
    RecyclerView rvMainFirstReCommend;
    @BindView(R.id.tv_first_recommend_next)
    TextView tvFirstRecommendNext;
    @BindView(R.id.pb_first_recommend_loading)
    ProgressBar pbFirstRecommendLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    private long clickTime = 0;
    @OnClick(R.id.lly_first_recommend_next)
    public void tvFirstRecommendNextOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(view.getContext(), FirstReCommendMoreActivity.class);
            view.getContext().startActivity(intent);
        }
        /*ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        page++;
        map.put("page",""+page);
        map.put("limit",""+limit);
        pbFirstRecommendLoading.setVisibility(View.VISIBLE);

        forumPostNetWork.getRecommendFromNet(map, new Observer<GetRecommendBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetRecommendBean getRecommendBean) {
                if(getRecommendBean.getIssuccess().equals("1")){
                    rvAdapter.replaceAll(getRecommendBean.getList());
                    pbFirstRecommendLoading.setVisibility(View.GONE);
                }
            }
        });*/
    }
    @BindView(R.id.rfl_first_recommend)
    SmartRefreshLayout rflFirstRecommend;
//    @BindView(R.id.pb_first_recommend_loading)
//    ProgressBar pbFirstRecommendLoading;
    @BindView(R.id.rly_first_recommend_add)
    RelativeLayout rlyFirstRecommendAdd;
    @OnClick(R.id.rly_first_recommend_add)
    public void rlyFirstRecommendAddOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(view.getContext(), ForumPostNormalActivity.class);
            view.getContext().startActivity(intent);
        }
    }
    @BindView(R.id.iv_first_recommend_loading)
    ImageView ivFirstRecommendLoading;
    @BindView(R.id.rly_first_recommend_loading)
    RelativeLayout rlyFirstRecommendLoading;

    @BindView(R.id.tv_first_recommend)
    TextView tvFirstRecommend;
    private int page = 1;
    private int limit = 20;
    private List<GetRecommendBean.ListBean> dataList1 = new ArrayList<>();

    private FirstReCommend2RVAdapter rvAdapter;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_first_recommend,container,false);
        return view;
    }

    @Override
    public void initView() {
        if (view != null){
            ButterKnife.bind(this,view);
        }
        rlyFirstRecommendLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstRecommendLoading);
        initLunBo();
        initRecycleView();
        initData();
//        defaultUltraViewPager();
    }
    //    轮播初始化
    private void initLunBo(){
        ArrayList<CircleImgBean.ListBean> imgURL = new ArrayList<>();
        ArrayList<Integer> imgLocal = new ArrayList<>();
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        forumPostNetWork.getCircleImgFromNet(map,new Observer<CircleImgBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CircleImgBean circleImgBean) {
                adcMainFirstRecommend.setImageResources(circleImgBean.getList(),null,imgLocal,imageCycleViewListener);
            }
        });
//        imgLocal.add(R.mipmap.forumdetailone1);
//        imgLocal.add(R.mipmap.forumdetailone2);
//        imgLocal.add(R.mipmap.forumdetailone3);


    }
    private AdCycleView.ImageCycleViewListener imageCycleViewListener=new AdCycleView.ImageCycleViewListener() {
        @Override
        public void displayImageURL(String imageURL, ImageView imageView) {
            Log.d("displayurl",imageURL);
//            imageView.setImageResource(R.mipmap.forumdetailone2);
            ImageLoader.getInstance().displayImage(imageURL,imageView, ImageLoaderUtils.options);
        }

        @Override
        public void displayImageLocal(int mipmap, ImageView imageView) {
//            imageView.setImageResource(mipmap);
        }

        @Override
        public void displayImageLocalRound(int mipmap, RoundImageView imageView) {
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setImageResource(mipmap);


        }

        @Override
        public void onImageClick(ArrayList<String> urlList, ArrayList<Integer> localList, int postion, View imageView) {

        }


    };
    private void initRecycleView(){
        rvAdapter = new FirstReCommend2RVAdapter(dataList1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
//        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
//        Map<String,String> map = new HashMap<>();
//        if(distr == null){
//            distr = "";
//        }
        rvMainFirstReCommend.setLayoutManager(linearLayoutManager);
//        rvMainFirstReCommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMainFirstReCommend.setAdapter(rvAdapter);
        rvMainFirstReCommend.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflFirstRecommend.setOnRefreshListener(new OnRefreshListener() {
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
//                        map.put("town","龙湾");
                        map.put("distr",distr);
//                        Log.d("distric11",distr);
//                        Log.d("distric11",accountid);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendFromNet(map, new Observer<GetRecommendBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetRecommendBean getRecommendBean) {
                                if(getRecommendBean.getIssuccess().equals("1")){
                                    List<GetRecommendBean.ListBean> dataList = getRecommendBean.getList();
                                    rvAdapter.replaceAll(dataList);

//                                    if(dataList.size() == 0) {
////                        datalist11.add(new );
//                                        dataList.add(new GetRecommendBean.ListBean() );
////                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(dataList);
//                                        llySplitView.setVisibility(View.VISIBLE);
//                                        pbFirstRecommendLoading.setVisibility(View.GONE);
//                                    }else {
//                                        llySplitView.setVisibility(View.GONE);
//                                        pbFirstRecommendLoading.setVisibility(View.VISIBLE);
////                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(dataList);
//                                    }
//                                    rvAdapter.replaceAll(getRecommendBean.getList());

                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },0000);
            }
        });
       /* rvMainFirstReCommend.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
                                                     @Override
                                                     public void onLoadMore(int currentPage) {
                                                         ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                                                         Map<String,String> map = new HashMap<>();
                                                         page++;
                                                         map.put("page",""+page);
                                                         map.put("limit",""+limit);
                                                         forumPostNetWork.getRecommendFromNet(map, new Observer<GetRecommendBean>() {
                                                             @Override
                                                             public void onCompleted() {

                                                             }

                                                             @Override
                                                             public void onError(Throwable e) {

                                                             }

                                                             @Override
                                                             public void onNext(GetRecommendBean getRecommendBean) {
                                                                 if(getRecommendBean.getIssuccess().equals("1")){
                                                                     rvAdapter.addData(rvAdapter.getItemCount(),getRecommendBean.getList());
//                                                                     refreshLayout.finishLoadMore();
                                                                 }
                                                             }
                                                         });
                                                     }
                                                 });*/
        rflFirstRecommend.setOnLoadMoreListener(new OnLoadMoreListener() {
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
//                        map.put("town","龙湾");
                        map.put("distr",distr);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendFromNet(map, new Observer<GetRecommendBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetRecommendBean getRecommendBean) {
                                if(getRecommendBean.getIssuccess().equals("1")){
                                    List<GetRecommendBean.ListBean> dataList = getRecommendBean.getList();
                                    if(dataList.size() == 0) {
//                                        dataList.add(new GetRecommendBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbFirstRecommendLoading.setVisibility(View.GONE);
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbFirstRecommendLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),dataList);
                                        refreshLayout.finishLoadMore();
                                    }
//                                    rvAdapter.addData(rvAdapter.getItemCount(),getRecommendBean.getList());
//                                    refreshLayout.finishLoadMore();
                                }
                            }
                        });


                    }
                },0000);
            }
        });
    }

    private void initData(){
        rlyFirstRecommendLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstRecommendLoading);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
//        if(distr == null){
//            distr = "";
//        }
        page = 1;
//        Log.d("fff1",distr);
        map.put("page",""+page);
        map.put("limit",""+limit);
        map.put("distr",distr);
        map.put("accountid",accountid);
        forumPostNetWork.getRecommendFromNet(map, new Observer<GetRecommendBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("fff1",e+"");
            }

            @Override
            public void onNext(GetRecommendBean getRecommendBean) {
                if(getRecommendBean.getIssuccess().equals("1")){
                    List<GetRecommendBean.ListBean> dataList = getRecommendBean.getList();
                    if(dataList.size() == 0){
                        tvFirstRecommend.setVisibility(View.VISIBLE);
                    }else {
                        tvFirstRecommend.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(dataList);
                    rlyFirstRecommendLoading.setVisibility(View.GONE);
//                    if(dataList.size() == 0) {
////                        datalist11.add(new );
//                        dataList.add(new GetRecommendBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(dataList);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pbFirstRecommendLoading.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pbFirstRecommendLoading.setVisibility(View.VISIBLE);
//                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(dataList);
//                    }
//                    rvAdapter.replaceAll(getRecommendBean.getList());
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String district = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        tvFirstRecommendNext.setText(district);
        initLunBo();
//        initData();
    }
}
