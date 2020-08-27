package com.et.secondworld.first.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.first.fragment.adapter.RecommendMoreEliteRVAdapter;
import com.et.secondworld.mine.adapter.MineShopArticleHistoryRVAdapter;
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
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/29
 **/
public class FirstReCommendMoreEliteActivity extends AppCompatActivity {


    @BindView(R.id.rly_first_recommend_more_elite_title)
    RelativeLayout rlyFirstRecommendMoreEliteTitle;
    @BindView(R.id.rly_first_recommend_more_elite_back)
    RelativeLayout rlyFirstRecommendMoreEliteBack;
    @OnClick(R.id.rly_first_recommend_more_elite_back)
    public void rlyFirstRecommendMoreEliteBackOnclick(){
        finish();
    }
    @BindView(R.id.rfl_first_recommend_more_elite)
    SmartRefreshLayout rflFirstRecommendMoreElite;
    @BindView(R.id.rv_first_recommend_more_elite)
    RecyclerView rvFirstRecommendMoreElite;
    @BindView(R.id.pb_first_recommend_more_elite_loading)
    ProgressBar pbFirstRecommendMoreEliteLoading;
    @BindView(R.id.tv_first_recommend_more_elite)
    TextView tvFirstRecommendMoreElite;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    RecommendMoreEliteRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 20;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_first_recommend_more_elite);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initRV();
        initData();
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
    private void initData(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        map.put("page",""+page);
        map.put("limit",""+limit);
//        map.put("town","龙湾");
        map.put("distr",distr);
        map.put("accountid",accountid);
        forumPostNetWork.getRecommendEliteFromNet(map, new Observer<GetRecommendBean>() {
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
                        tvFirstRecommendMoreElite.setVisibility(View.VISIBLE);
                    }else {
                        tvFirstRecommendMoreElite.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(dataList);

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
    private void initRV() {
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
//            dataList.add("");
//        }
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        rvAdapter = new RecommendMoreEliteRVAdapter();
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//        //设置布局管理器， 参数gridLayoutManager对象
//        rvSearch.setLayoutManager(gridLayoutManager);
        rvFirstRecommendMoreElite.setLayoutManager(new LinearLayoutManager(this));
        rvFirstRecommendMoreElite.setAdapter(rvAdapter);
//        rvAdapter.replaceAll(dataList);


        //设置下拉刷新和上拉加载监听
        rflFirstRecommendMoreElite.setOnRefreshListener(new OnRefreshListener() {
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
                        map.put("distr",distr);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendEliteFromNet(map, new Observer<GetRecommendBean>() {
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
                },0);
            }
        });
        rflFirstRecommendMoreElite.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        map.put("distr",distr);
                        map.put("accountid",accountid);
                        forumPostNetWork.getRecommendEliteFromNet(map, new Observer<GetRecommendBean>() {
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
                                        pbFirstRecommendMoreEliteLoading.setVisibility(View.GONE);
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbFirstRecommendMoreEliteLoading.setVisibility(View.VISIBLE);
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
                },0);
            }
        });
    }

}
