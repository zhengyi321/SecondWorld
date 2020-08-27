package com.et.secondworld;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.adapter.SearchVillageShopRVAdapter;
import com.et.secondworld.bean.VillageListBean;
import com.et.secondworld.network.SearchNetWork;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class SearchVillageShopMoreActivity extends AppCompatActivity {


    @BindView(R.id.et_village_shop_more_shopname)
    EditText etVillageShopMoreShopName;
    @BindView(R.id.rfl_village_shop_more)
    SmartRefreshLayout rflVillageShopMore;
    @BindView(R.id.pb_village_shop_more_loading)
    ProgressBar pbVillageShopMoreLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.et_village_shop_more_place)
    EditText etVillageShopMorePlace;
    @BindView(R.id.rv_village_shop_more)
    RecyclerView rvVillageShopMore;
    @BindView(R.id.lly_village_shop_more)
    LinearLayout llyVillageShopMore;

    private int page = 1;
    private int limit = 10;
    private SearchVillageShopRVAdapter rvAdapter;
    @OnClick(R.id.lly_village_shop_more)
    public void llyVillageShopMoreOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @BindView(R.id.bt_village_shop_more_search)
    Button btVillageShopMoreSearch;
    @OnClick(R.id.bt_village_shop_more_search_cancel)
    public void btVillageShopMoreSearchCancelOnclick(){
        this.finish();
    }

    @OnClick({R.id.bt_village_shop_more_search})
    public void btVillageShopMoreSearchOnclick(){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page + "");
        map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
        map.put("shopname", etVillageShopMoreShopName.getText().toString() + "");
        map.put("place", etVillageShopMorePlace.getText().toString());
        SearchNetWork searchNetWork = new SearchNetWork();
        searchNetWork.getShopByPlaceFromNet(map, new Observer<VillageListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VillageListBean villageListBean) {
                Log.d("search111",""+villageListBean.getMsg());
                rvAdapter.replaceAll(villageListBean.getList());
            }
        });
    }

    String shopname = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_village_shop_more);

        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        getData();
        initRV();
        initRefresh();
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
    private void initRefresh(){
        rflVillageShopMore.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);
                                page = 1;

                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopMoreShopName.getText().toString() + "");
                                map.put("place", etVillageShopMorePlace.getText().toString());
                                SearchNetWork searchNetWork = new SearchNetWork();
                                searchNetWork.getShopByPlaceFromNet(map, new Observer<VillageListBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(VillageListBean villageListBean) {
                                        Log.d("search111",""+villageListBean.getMsg());
                                        rvAdapter.replaceAll(villageListBean.getList());
                                    }
                                });




                        refreshLayout.finishRefresh();
                    }
                },00);
            }
        });

        rflVillageShopMore.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");

                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopMoreShopName.getText().toString() + "");
                                map.put("place", etVillageShopMorePlace.getText().toString());
                                SearchNetWork searchNetWork = new SearchNetWork();
                                searchNetWork.getShopByPlaceFromNet(map, new Observer<VillageListBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(VillageListBean villageListBean) {
                                        if (villageListBean.getList().size() != limit) {
//                                        datalist11.add(new GetForumBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMoreWithNoMoreData();
//                                        if(datalist11.size() == 0) {
                                            llySplitView.setVisibility(View.VISIBLE);
                                            pbVillageShopMoreLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopMoreLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });





















//                        refreshLayout.

                    }
                },0);
            }
        });
    }

    private void initRV(){
        rvAdapter = new SearchVillageShopRVAdapter(this);
//        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//        //设置布局管理器， 参数gridLayoutManager对象
//        rvSearch.setLayoutManager(gridLayoutManager);
        rvVillageShopMore.setLayoutManager(new LinearLayoutManager(this));
        rvVillageShopMore.setAdapter(rvAdapter);
    }

    private void getData(){
        Intent intent= getIntent();
        shopname = intent.getStringExtra("shopname");
        etVillageShopMoreShopName.setText(shopname);
    }

}
