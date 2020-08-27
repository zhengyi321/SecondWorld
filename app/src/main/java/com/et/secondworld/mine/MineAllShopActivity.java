package com.et.secondworld.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.bean.GetStreetShopListBean;
import com.et.secondworld.bean.GetTradeBean;
import com.et.secondworld.mine.adapter.MineAllShopRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.network.TradeNetWork;
import com.et.secondworld.widget.StaggeredDividerItemDecoration;
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

import static com.et.secondworld.application.MyApplication.instance;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/14
 **/
public class MineAllShopActivity extends AppCompatActivity {


    @OnClick(R.id.rly_mine_all_shop_back)
    public void rlyMineAllShopBackOnclick(){
        this.finish();
    }
    @BindView(R.id.rv_mine_all_shop)
    RecyclerView rvMineAllShop;
    @BindView(R.id.tv_mine_all_shop_title)
    TextView tvMineAllShopTitle;
    private MineAllShopRVAdapter rvAdapter;
    private String address;
    @BindView(R.id.rfl_mine_all_shop)
    SmartRefreshLayout rflMineAllShop;
    @BindView(R.id.pb_mine_all_shop_loading)
    ProgressBar pbMineAllShopLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.et_mine_all_shop)
    EditText etMineAllShop;
    @BindView(R.id.sp_mine_all_shop_trade)
    Spinner spMineAllShopTrade;
    @BindView(R.id.sp_mine_all_shop_letter)
    Spinner spMineAllShopLetter;
    int page = 1;
    int limit = 6;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_all_shop);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){

        ButterKnife.bind(this);
        getData();
        initRecycleView();

        initData();
        initStatusBar();
        initEditListener();
        initTradeSpinner();
    }

    private void initTradeSpinner(){

        TradeNetWork tradeNetWork = new TradeNetWork();
        Map<String,Object> map = new HashMap<>();
        tradeNetWork.getSortLetterFromNet(map, new Observer<GetTradeBean>() {
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
                    spinnerItems[i] = listBeanList.get(i).getTrade()+"  ";
                }
//                final String[] spinnerItems = {"全部", "关注的店铺"};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件

                spMineAllShopLetter.setAdapter(spinnerAdapter);
                spMineAllShopLetter.setSelection(0, true);
            }
        });


//        TradeNetWork tradeNetWork = new TradeNetWork();
//        Map<String,Object> map = new HashMap<>();
        tradeNetWork.getSortTradeFromNet(map, new Observer<GetTradeBean>() {
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
                    spinnerItems[i] = listBeanList.get(i).getTrade()+"  ";
                }
//                final String[] spinnerItems = {"全部", "关注的店铺"};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件

                spMineAllShopTrade.setAdapter(spinnerAdapter);
                spMineAllShopTrade.setSelection(0, true);
            }
        });
        /*final String[] spinnerItems = {"按行业分类","全部分类"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spMineAllShopTrade.setAdapter(spinnerAdapter);
        spMineAllShopTrade.setSelection(0, true);
        spMineAllShopTrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                if(pos == 0){

                }
                if(pos == 1){

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
    }

    private void initEditListener(){
        etMineAllShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getBaseContext(),s+"",Toast.LENGTH_SHORT).show();
                String name = etMineAllShop.getText().toString();
                Map<String,Object> map = new HashMap<>();
//                page = 1;
                map.put("name",name);
//                map.put("page",page+"");
//                map.put("limit",limit+"");
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                map.put("accountid",account);
                ShopNetWork shopNetWork = new ShopNetWork();
                shopNetWork.searchShopNameFromNet(map, new Observer<GetStreetShopListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetStreetShopListBean getStreetShopListBean) {
                        rvAdapter.replaceAll(getStreetShopListBean.getList());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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


    private void getData(){
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        Log.d("address11",address);
        tvMineAllShopTitle.setText(address);
    }

    @SuppressLint("WrongConstant")
    private void initRecycleView(){
        rvAdapter = new MineAllShopRVAdapter();
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvMineAllShop.setLayoutManager(gridLayoutManager);
//        rvMineAllShop.addItemDecoration(new StaggeredDividerItemDecoration(this,10,2));
        rvMineAllShop.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflMineAllShop.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,Object> map = new HashMap<>();
                        page = 1;
                        map.put("street",address);
                        map.put("page",page+"");
                        map.put("limit",limit+"");
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        map.put("accountid",account);
                        ShopNetWork shopNetWork = new ShopNetWork();
                        shopNetWork.getStreetShopFromNet(map, new Observer<GetStreetShopListBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetStreetShopListBean getStreetShopListBean) {
                                rvAdapter.replaceAll(getStreetShopListBean.getList());
                            }
                        });
//                        findAdapter.replaceAll(dataList);
                        /*ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page = 1;
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("town",town);
                        map.put("distr",distr+",");
                        map.put("city",city+",");
                        map.put("village",village);
                        map.put("accountid",accountid);
                        forumPostNetWork.getForumFromNet(map, new Observer<GetForumBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetForumBean getForumBean) {
                                if(getForumBean.getIssuccess().equals("1")){
                                    List<GetForumBean.ListBean> datalist11 = new ArrayList<>();
                                    datalist11.addAll(getForumBean.getList());
                                    rvAdapter.replaceAll(datalist11);
//                                    if(datalist11.size() == 0) {
////                        datalist11.add(new );
//                                        datalist11.add(new GetForumBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
//                                        rvAdapter.replaceAll(datalist11);
//                                        llySplitView.setVisibility(View.VISIBLE);
//                                        pbForumLoading.setVisibility(View.GONE);
//                                    }else {
//                                        llySplitView.setVisibility(View.GONE);
//                                        pbForumLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
//                                        rvAdapter.replaceAll(datalist11);
//                                    }
                                }
                            }
                        });*/
                        refreshLayout.finishRefresh();
                    }
                },00);
            }
        });

        rflMineAllShop.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();

                        page++;
                        Map<String,Object> map = new HashMap<>();
                        map.put("street",address);
                        map.put("page",page+"");
                        map.put("limit",limit+"");
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        map.put("accountid",account);
                        ShopNetWork shopNetWork = new ShopNetWork();
                        shopNetWork.getStreetShopFromNet(map, new Observer<GetStreetShopListBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetStreetShopListBean getStreetShopListBean) {
                                List<GetStreetShopListBean.ListBean> datalist11 = getStreetShopListBean.getList();

                                if(datalist11.size() != limit ) {
//                                        datalist11.add(new GetForumBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                    rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                    refreshLayout.finishLoadMoreWithNoMoreData();
//                                        if(datalist11.size() == 0) {
                                    llySplitView.setVisibility(View.VISIBLE);
                                    pbMineAllShopLoading.setVisibility(View.GONE);
//                                        }
                                }else {
                                    llySplitView.setVisibility(View.GONE);
                                    pbMineAllShopLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                    rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                    refreshLayout.finishLoadMore();
                                }
//                                rvAdapter.replaceAll(getStreetShopListBean.getList());
                            }
                        });



//                        refreshLayout.

                    }
                },0);
            }
        });
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 10;i++){
//            dataList.add("");
//        }



//        rvAdapter.replaceAll(dataList);
    }


    private void initData(){
        Map<String,Object> map = new HashMap<>();
        page = 1;
//        Log.d("street1",address);
        map.put("street",address);
        map.put("page",page+"");
        map.put("limit",limit+"");
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("accountid",account);
//        Log.d("street1",account);
//        Log.d("street1",page+"");
//        Log.d("street1",limit+"");
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.getStreetShopFromNet(map, new Observer<GetStreetShopListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetStreetShopListBean getStreetShopListBean) {
                rvAdapter.replaceAll(getStreetShopListBean.getList());
            }
        });
    }
}
