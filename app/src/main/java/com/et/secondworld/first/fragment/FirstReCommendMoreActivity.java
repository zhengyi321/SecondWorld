package com.et.secondworld.first.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.bean.CircleImgBean;
import com.et.secondworld.first.fragment.adapter.FirstReCommendMoreRVAdapter;
import com.et.secondworld.forum.ForumPostNormalActivity;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.viewpage.ImageCycleView;
import com.et.secondworld.widget.viewpage.ImageInCycleView;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.first.fragment.adapter.FirstReCommend2RVAdapter;
import com.et.secondworld.network.ForumPostNetWork;

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
 * @Date 2020/5/21
 **/
public class FirstReCommendMoreActivity extends AppCompatActivity {


    @BindView(R.id.rly_first_recommend_more_back)
    RelativeLayout rlyFirstRecommendMoreBack;
    @OnClick(R.id.rly_first_recommend_more_back)
    public void rlyFirstRecommendMoreBackOnclick(){
        finish();
    }
    @BindView(R.id.rfl_first_recommend_more)
    SmartRefreshLayout rflFirstRecommendMore;
    @BindView(R.id.rv_first_recommend_more)
    RecyclerView rvFirstRecommendMore;
    @BindView(R.id.pb_first_recommend_loading)
    ProgressBar pbFirstRecommendLoading;
    @BindView(R.id.tly_first_recommend_more)
    TabLayout tlyFirstRecommendMore;
    /*@BindView(R.id.rvh_first_recommend_more)
    RecyclerViewHeader rvhFirstRecommendMore;*/
    @BindView(R.id.icv_first_recommend_more)
    ImageInCycleView icvFirstRecommendMore;
    @BindView(R.id.rly_first_recommend_more_elite)
    RelativeLayout rlyFirstRecommendMoreElite;
    private long clickTime = 0;
    @OnClick(R.id.rly_first_recommend_more_elite)
    public void rlyFirstRecommendMoreEliteOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, FirstReCommendMoreEliteActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.tv_first_recommend_more_title)
    TextView tvFirstRecommendMoreTitle;
    @OnClick(R.id.rly_first_recommend_more_add)
    public void rlyFirstRecommendAddOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, ForumPostNormalActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.iv_first_recommend_more_loading)
    ImageView ivFirstRecommendMoreLoading;
    @BindView(R.id.rly_first_recommend_more_loading)
    RelativeLayout rlyFirstRecommendMoreLoading;
    @BindView(R.id.tv_first_recommend_more)
    TextView tvFirstRecommendMore;

    private int page = 1;
    private int limit = 6;
    private List<GetRecommendBean.ListBean> dataList1 = new ArrayList<>();

    private FirstReCommendMoreRVAdapter rvAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_first_recommend_more);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initTitle();
        rlyFirstRecommendMoreLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstRecommendMoreLoading);
        initRecycleView();
        initData();
        tlyFirstRecommendMore.addTab(tlyFirstRecommendMore.newTab().setText("故事").setTag(0));
//        List<Integer> localList = new ArrayList<>();
////        localList.add(R.mipmap.forumad1);
////        localList.add(R.mipmap.forumad1);
////        localList.add(R.mipmap.forumad1);
//        List<String> urlList = new ArrayList<>();
//        urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
//        urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
//        urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
//        icvFirstRecommendMore.setImageResources(urlList, null, localList, imageCycleViewListener);
        initLunBo();
        initStatusBar();
    }
    /*沉浸式状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.blue);
//        switch (type) {
//            case "index":
//                tintManager.setStatusBarTintResource(R.color.color_main_index_topbar_blue_bg);
//                break;
//            case "release":
//                tintManager.setStatusBarTintResource(R.color.color_main_release_topbar_blue_bg);
//                break;
//            case "advice":
//                tintManager.setStatusBarTintResource(R.color.color_main_advice_content_white_bg);
//                break;
//            case "message":
//                tintManager.setStatusBarTintResource(R.color.color_main_message_content_white_bg);
//                break;
//            case "mine":
//                tintManager.setStatusBarTintResource(R.mipmap.top_big_blue_bg);
//                break;
//        }
    }
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setTranslucentStatus(boolean on) {
       /* Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.white ));


    }
    private void initLunBo(){
        List<Integer> localList = new ArrayList<>();
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        Map<String,Object> map = new HashMap<>();
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        map.put("province",distr+",");
        Log.d("distr111",distr+"");
//               Map<String,String> map = new HashMap<>();
        forumPostNetWork.getFindCircleImgFromNet(map,new Observer<CircleImgBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CircleImgBean circleImgBean) {
                if (circleImgBean.getList().size() > 0){
                    icvFirstRecommendMore.setImageResources(circleImgBean.getList(), null, localList, imageCycleViewListener);
                }
            }

        });
    }
    private void initTitle(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String disc = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        tvFirstRecommendMoreTitle.setText(disc);
    }
    private ImageInCycleView.ImageCycleViewListener imageCycleViewListener=new ImageInCycleView.ImageCycleViewListener() {
        @Override
        public void displayImageURL(String imageURL, ImageView imageView) {
            Glide.with(getBaseContext()).load(imageURL)
                    .apply(new RequestOptions().fallback(R.color.white))
                    .into(imageView);
//            ImageLoader.getInstance().displayImage(imageURL,imageView,ImageLoaderUtils.options);
        }

        @Override
        public void displayImageLocal(int mipmap, ImageView imageView) {
//            Log.d("zzzz111",mipmap+"");
            imageView.setImageResource(R.color.white);
        }

        @Override
        public void onImageClick(ArrayList<String> urlList, ArrayList<Integer> localList, int postion, View imageView) {

        }


    };
    @SuppressLint("WrongConstant")
    private void initRecycleView(){
//        rvAdapter = new FirstReCommend2RVAdapter(dataList1);
        rvAdapter = new FirstReCommendMoreRVAdapter();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//        };
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvFirstRecommendMore.setLayoutManager(gridLayoutManager);

//        rvFirstRecommendMore.setLayoutManager(linearLayoutManager);
//        rvMainFirstReCommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFirstRecommendMore.setAdapter(rvAdapter);
//        rvhFirstRecommendMore.attachTo(rvFirstRecommendMore);
        //设置下拉刷新和上拉加载监听
        rflFirstRecommendMore.setOnRefreshListener(new OnRefreshListener() {
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
                        map.put("town",distr);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendMoreFromNet(map, new Observer<GetRecommendBean>() {
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

//                                    if(dataList.size() != limit) {
////                        datalist11.add(new );
//                                        dataList.add(new GetRecommendBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(dataList);
//                                    }else {
//                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(dataList);
//                                    }
//                                    rvAdapter.replaceAll(getRecommendBean.getList());
//                                    refreshLayout.finishRefresh();
                                }
                            }
                        });
                        refreshLayout.finishRefresh();
                    }
                },0000);
            }
        });
        rflFirstRecommendMore.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        map.put("town",distr);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendMoreFromNet(map, new Observer<GetRecommendBean>() {
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
                                    /*rvAdapter.addData(rvAdapter.getItemCount(),getRecommendBean.getList());
                                    if(getRecommendBean.getList().size()>0) {
                                        refreshLayout.finishLoadMore();
                                    }else {
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                    }*/
                                }
                            }
                        });


                    }
                },0000);
            }
        });
    }

    private void initData(){
        rlyFirstRecommendMoreLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivFirstRecommendMoreLoading);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        map.put("page",""+page);
        map.put("limit",""+limit);
        map.put("town",distr);
        map.put("accountid",accountid);
        forumPostNetWork.getRecommendMoreFromNet(map, new Observer<GetRecommendBean>() {
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
                    if(dataList.size() == 0){
                        tvFirstRecommendMore.setVisibility(View.VISIBLE);
                    }else {
                        tvFirstRecommendMore.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(dataList);
                    rlyFirstRecommendMoreLoading.setVisibility(View.GONE);
//                    if(dataList.size() != limit) {
////                        datalist11.add(new );
//                        dataList.add(new GetRecommendBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(dataList);
//                    }else {
//                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(dataList);
//                    }
//                    rvAdapter.replaceAll(getRecommendBean.getList());
                }
            }
        });
    }
}
