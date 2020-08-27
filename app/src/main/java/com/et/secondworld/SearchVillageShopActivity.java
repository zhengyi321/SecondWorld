package com.et.secondworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.adapter.SearchVillageShopRVAdapter;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.bean.VillageListBean;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.SearchNetWork;
import com.et.secondworld.utils.GetJsonDataUtil;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.timeselect.ProvCityAreaOptionsPickerView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;

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
 * @Date 2020/6/17
 **/
public class SearchVillageShopActivity extends AppCompatActivity {

    @BindView(R.id.sp_village_shop_search_village_shop)
    Spinner spVillageShopSearchVillageShop;
    @BindView(R.id.sp_village_shop_search_area)
    Spinner spVillageShopSearchArea;
    @BindView(R.id.tv_village_shop_search_more)
    TextView tvVillageShopSearchMore;
    private long clickTime = 0;
    @OnClick(R.id.lly_village_shop_search_more)
    public void tvVillageShopSearchMoreOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, SearchVillageShopMoreActivity.class);
            intent.putExtra("shopname", etVillageShopSearchInput.getText() + "");
            startActivity(intent);
        }
    }
    @BindView(R.id.rv_village_shop)
    RecyclerView rvVillageShop;
    @BindView(R.id.tv_village_shop_search_province)
    TextView tvVillageShopSearchProvince;
    @BindView(R.id.rly_village_shop_search_cancel)
    RelativeLayout rlyVillageShopSearchCancel;
    @OnClick({R.id.rly_village_shop_search_cancel,R.id.rly_village_shop_search_cancel2})
    public void rlyVillageShopSearchCancelOnclick(){
        finish();
    }
    @BindView(R.id.et_village_shop_search_input)
    EditText etVillageShopSearchInput;
    @BindView(R.id.rfl_village_shop)
    SmartRefreshLayout rflVillageShop;
    @BindView(R.id.pb_village_shop_loading)
    ProgressBar pbVillageShopLoading;
    @OnClick({R.id.lly_village_shop_search,R.id.rfl_village_shop,R.id.rv_village_shop})
    public void llyVillageShopSearchOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.lly_village_shop_search_input)
    LinearLayout llyVillageShopSearchInput;
    @BindView(R.id.et_village_shop_search_input2)
    EditText etVillageShopSearchInput2;
    @BindView(R.id.rly_village_shop_search_cancel2)
    RelativeLayout rlyVillageShopSearchCancel2;
    @BindView(R.id.lly_village_shop_search_more)
    LinearLayout llyVillageShopSearchMore;
    private boolean isVillage = true;
    private  int type = 0;
    private SearchVillageShopRVAdapter rvAdapter;
    @OnClick(R.id.tv_village_shop_search_province)
    public void tvVillageShopSearchProvinceOnclick(){
        ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(this, new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
                String tx = "";
                String province = options1Items.get(options1).getPickerViewText();
//                String city = options2Items.get(options1).get(options2);
//                String area = options3Items.get(options1).get(options2).get(options3);
//                if(province.equals(city)){
//                    tx = province+area;
//                }else {
//                    tx = province+city+area;
//                }
             /*       page = 1;
                    getData2FromNet();*/
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                xcCacheManager.writeCache(xcCacheSaveName.selectProvince,province);
                tvVillageShopSearchProvince.setText(province);
                /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("选择省份")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();

        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        pvOptions.show();
    }
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private String province = "";
    private String city = "";
    private String district ="";
    private String town ="";
    private int page = 1;
    private int limit = 10;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_village_shop_search);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
        String province = xcCacheManager.readCache(xcCacheSaveName.selectProvince);
//        xcCacheManager.writeCache(xcCacheSaveName.selectProvince,province);
        if(province != null) {
            tvVillageShopSearchProvince.setText(province);
        }
        getData();
        initSpinnerProvinceCity();
        initSpinnerVillageShop();

        initJsonData();
        initRV();
        initInput();
        initRefreshLayout();
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
    private void initRefreshLayout(){
        //设置下拉刷新和上拉加载监听
        rflVillageShop.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);


                        if(isVillage) {
//                            if(etVillageShopSearchInput.getText().toString().isEmpty()){
//                                refreshLayout.finishRefresh();
//                                return;
//                            }
                            page = 1;
                            Map<String, Object> map = new HashMap<>();
                            map.put("page", page + "");
                            map.put("limit", limit + "");
                            map.put("province", tvVillageShopSearchProvince.getText() + "");
                            map.put("place", etVillageShopSearchInput.getText() + "");
                            SearchNetWork searchNetWork = new SearchNetWork();
                            searchNetWork.getVillageFromNet(map, new Observer<VillageListBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(VillageListBean villageListBean) {
                                    rvAdapter.replaceAll(villageListBean.getList());
                                }
                            });
                        }else {
//                            if(etVillageShopSearchInput2.getText().toString().isEmpty()){
//                                refreshLayout.finishRefresh();
//                                return;
//                            }
                            page = 1;
                            if(type == -1) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");

                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                SearchNetWork searchNetWork = new SearchNetWork();
                                searchNetWork.getShopFromNet(map, new Observer<VillageListBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(VillageListBean villageListBean) {
                                        Log.d("search111", "" + villageListBean.getMsg());
                                        rvAdapter.replaceAll(villageListBean.getList());
                                    }
                                });
                            }

                            if(type == 0){

                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", town);
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
                            if(type == 1){
                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", district);
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
                            if(type == 2){
                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", city);
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
                            if(type == 3){
                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", province);
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
                            if(type == 4){
                                Map<String, Object> map = new HashMap<>();
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", "");
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
//                            Log.d("search111",""+villageListBean.getMsg());
                                        rvAdapter.replaceAll(villageListBean.getList());
                                    }
                                });
                            }
                        }
                        refreshLayout.finishRefresh();
                    }
                },00);
            }
        });

        rflVillageShop.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(isVillage) {
//                            if(etVillageShopSearchInput.getText().toString().isEmpty()){
//                                refreshLayout.finishRefresh();
//                                return;
//                            }
                            Map<String, Object> map = new HashMap<>();
                            page++;
                            map.put("page", page + "");
                            map.put("limit", limit + "");
                            map.put("province", tvVillageShopSearchProvince.getText() + "");
                            map.put("place", etVillageShopSearchInput.getText() + "");
                            SearchNetWork searchNetWork = new SearchNetWork();
                            searchNetWork.getVillageFromNet(map, new Observer<VillageListBean>() {
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
                                        pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                    } else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                        refreshLayout.finishLoadMore();
                                    }
                                }
                            });
                        }else {
//                            if(etVillageShopSearchInput2.getText().toString().isEmpty()){
//                                refreshLayout.finishRefresh();
//                                return;
//                            }
                            if (type == -1) {
                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");

                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                SearchNetWork searchNetWork = new SearchNetWork();
                                searchNetWork.getShopFromNet(map, new Observer<VillageListBean>() {
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
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }

                            if(type == 0){

                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");

                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", town);
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
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }
                            if(type == 1){
                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", district);
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
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }
                            if(type == 2){
                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", city);
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
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }
                            if(type == 3){
                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", province);
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
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }
                            if(type == 4){
                                Map<String, Object> map = new HashMap<>();
                                page++;
                                map.put("page", page + "");
                                map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                                map.put("shopname", etVillageShopSearchInput2.getText() + "");
                                map.put("place", "");
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
//                            Log.d("search111",""+villageListBean.getMsg());
                                        if (villageListBean.getList().size() != limit) {
//                                        datalist11.add(new GetForumBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMoreWithNoMoreData();
//                                        if(datalist11.size() == 0) {
                                            llySplitView.setVisibility(View.VISIBLE);
                                            pbVillageShopLoading.setVisibility(View.GONE);
//                                        }
                                        } else {
                                            llySplitView.setVisibility(View.GONE);
                                            pbVillageShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                            rvAdapter.addData(rvAdapter.getItemCount(), villageListBean.getList());
                                            refreshLayout.finishLoadMore();
                                        }
                                    }
                                });
                            }




                        }











//                        refreshLayout.

                    }
                },0);
            }
        });
    }

    private void initInput(){
        etVillageShopSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getBaseContext(),s+"",Toast.LENGTH_SHORT).show();

                page = 1;
                type = -1;
                if(isVillage) {
//                    if(etVillageShopSearchInput.getText().toString().isEmpty()){
//                        return;
//                    }
                    Log.d("search111","this is search");
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
                    map.put("province", tvVillageShopSearchProvince.getText() + "");
                    map.put("place", etVillageShopSearchInput.getText() + "");
                    SearchNetWork searchNetWork = new SearchNetWork();
                    searchNetWork.getVillageFromNet(map, new Observer<VillageListBean>() {
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
                }else {
//                    if(etVillageShopSearchInput2.getText().toString().isEmpty()){
//                        return;
//                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput2.getText() + "");
                    SearchNetWork searchNetWork = new SearchNetWork();
                    searchNetWork.getShopFromNet(map, new Observer<VillageListBean>() {
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etVillageShopSearchInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getBaseContext(),s+"",Toast.LENGTH_SHORT).show();
                page = 1;
                type = -1;

                if(isVillage) {
//                    if(etVillageShopSearchInput.getText().toString().isEmpty()){
//                        return;
//                    }
                    Log.d("search111","this is search");
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
                    map.put("province", tvVillageShopSearchProvince.getText() + "");
                    map.put("place", etVillageShopSearchInput.getText() + "");
                    SearchNetWork searchNetWork = new SearchNetWork();
                    searchNetWork.getVillageFromNet(map, new Observer<VillageListBean>() {
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
                }else {
//                    if(etVillageShopSearchInput2.getText().toString().isEmpty()){
//                        return;
//                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput2.getText() + "");
                    SearchNetWork searchNetWork = new SearchNetWork();
                    searchNetWork.getShopFromNet(map, new Observer<VillageListBean>() {
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initRV(){
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
//            dataList.add("");
//        }
        rvAdapter = new SearchVillageShopRVAdapter(this);
//        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//        //设置布局管理器， 参数gridLayoutManager对象
//        rvSearch.setLayoutManager(gridLayoutManager);
        rvVillageShop.setLayoutManager(new LinearLayoutManager(this));
        rvVillageShop.setAdapter(rvAdapter);

//        rvAdapter.replaceAll(dataList);
    }
    private void getData(){
        Intent intent = getIntent();
        province = intent.getStringExtra("province");
        city = intent.getStringExtra("city");
        district = intent.getStringExtra("district");
        town = intent.getStringExtra("town");
    }
    private void initSpinnerVillageShop(){
        final String[] spinnerItems = {"地区","店铺"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spVillageShopSearchVillageShop.setAdapter(spinnerAdapter);
        spVillageShopSearchVillageShop.setSelection(0, true);
        spVillageShopSearchVillageShop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                if(pos == 0){
                    tvVillageShopSearchProvince.setVisibility(View.VISIBLE);
                    tvVillageShopSearchMore.setVisibility(View.GONE);
                    spVillageShopSearchArea.setVisibility(View.GONE);
                    llyVillageShopSearchMore.setVisibility(View.GONE);
                    llyVillageShopSearchInput.setVisibility(View.GONE);
                    etVillageShopSearchInput.setVisibility(View.VISIBLE);
                    rlyVillageShopSearchCancel.setVisibility(View.VISIBLE);
                    etVillageShopSearchInput.setHint("请搜索正确的地名");
                    List<VillageListBean.ListBean> dataList = new ArrayList<>();
                    rvAdapter.replaceAll(dataList);
                    isVillage = true;

                }
                if(pos == 1){
                    tvVillageShopSearchProvince.setVisibility(View.GONE);
//                    tvVillageShopSearchMore.setVisibility(View.VISIBLE);
                    llyVillageShopSearchMore.setVisibility(View.VISIBLE);
                    spVillageShopSearchArea.setVisibility(View.VISIBLE);
                    etVillageShopSearchInput2.setHint("请输入店铺名称");
                    llyVillageShopSearchInput.setVisibility(View.VISIBLE);
                    etVillageShopSearchInput.setVisibility(View.GONE);
                    rlyVillageShopSearchCancel.setVisibility(View.GONE);
                    List<VillageListBean.ListBean> dataList = new ArrayList<>();
                    rvAdapter.replaceAll(dataList);
                    isVillage = false;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void initSpinnerProvinceCity(){
        if(district == null){
            district = "";
        }
        if(city == null){
            city = "";
        }
        if(province == null){
            province = "";
        }
        final String[] spinnerItems = {"周边",district,city,province,"全国"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spVillageShopSearchArea.setAdapter(spinnerAdapter);
        spVillageShopSearchArea.setSelection(0, true);
        spVillageShopSearchArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                page = 1;
                type = pos;
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                if(pos == 0){

                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput.getText() + "");
                    map.put("place", town);
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
                if(pos == 1){
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput.getText() + "");
                    map.put("place", district);
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
                if(pos == 2){
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput.getText() + "");
                    map.put("place", city);
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
                if(pos == 3){
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput.getText() + "");
                    map.put("place", province);
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
                if(pos == 4){
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", page + "");
                    map.put("limit", limit + "");
//                    map.put("province", tvVillageShopSearchProvince.getText().toString() + "");
                    map.put("shopname", etVillageShopSearchInput.getText() + "");
                    map.put("place", "");
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
//                            Log.d("search111",""+villageListBean.getMsg());
                            rvAdapter.replaceAll(villageListBean.getList());
                        }
                    });
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        String quanIndex = AreaName.substring(0,1);
                        if(quanIndex != null && quanIndex.equals("全")){
                            continue;
                        }
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }


        }



    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                if(data.optJSONObject(i) != null) {
                    JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                    detail.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }
}
