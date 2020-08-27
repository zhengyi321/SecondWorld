package com.et.secondworld.forum;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetGuanZhuBean;
import com.et.secondworld.forum.adapter.ForumAtRVAdapter;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.edittext.RObject;

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
 * @Date 2020/4/27
 **/
public class ForumAtActivity  extends AppCompatActivity {

    public static   final String EXTRA_RESULT = "EXTRA_RESULT";
    @BindView(R.id.rly_forum_at_back)
    RelativeLayout rlyForumAtBack;
    @OnClick(R.id.rly_forum_at_back)
    public void rlyForumAtBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_forum_at_finish)
    RelativeLayout rlyForumAtFinish;
    @OnClick(R.id.rly_forum_at_finish)
        public void rlyForumAtFinishOnclick(){
        Intent intent = new Intent();
        ArrayList<RObject> objectArrayList = new ArrayList<>();
        objectArrayList.addAll(rvAdapter.getrObjectList());
        intent.putParcelableArrayListExtra(EXTRA_RESULT, (ArrayList<? extends Parcelable>) objectArrayList);
        setResult(RESULT_OK, intent);
        finish();
//        Toast.makeText(this,""+rvAdapter.getrObjectList().size(),Toast.LENGTH_SHORT).show();
    }
    @BindView(R.id.rv_forum_at)
    RecyclerView rvForumAt;
    @BindView(R.id.rfl_forum_at)
    SmartRefreshLayout rflForumAt;
    private ForumAtRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 10;

    private List<GetGuanZhuBean.ListBean> dataList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_at);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initRecycleView();
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
    private void initRecycleView(){

        rvAdapter = new ForumAtRVAdapter(dataList);
        rvForumAt.setLayoutManager(new LinearLayoutManager(this));
        rvForumAt.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflForumAt.setOnRefreshListener(new OnRefreshListener() {
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
                },2000);
            }
        });

        rflForumAt.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String, String> map = new HashMap<>();

                        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();

                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        page++;
                        map.put("account", account);
                        map.put("page", "" + page);
                        map.put("limit", "" + limit);
                        guanZhuFansNetWork.getGuanZhuFromNet(map, new Observer<GetGuanZhuBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetGuanZhuBean getGuanZhuBean) {


                                if (getGuanZhuBean.getIssuccess().equals("1")) {
                                    rvAdapter.addData(rvAdapter.getItemCount(), getGuanZhuBean.getList());
                                }
                            }
                        });

                        refreshLayout.finishLoadMore();
                }
            },2000);
        }
    });

}
    private void initData() {
        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();
        Map<String, String> map = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account", account);
        map.put("page", "" + page);
        map.put("limit", "" + limit);
        guanZhuFansNetWork.getGuanZhuFromNet(map, new Observer<GetGuanZhuBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetGuanZhuBean getGuanZhuBean) {

                if (getGuanZhuBean.getIssuccess().equals("1")) {
                        rvAdapter.replaceAll(getGuanZhuBean.getList());

                }
            }
        });
    }
}
