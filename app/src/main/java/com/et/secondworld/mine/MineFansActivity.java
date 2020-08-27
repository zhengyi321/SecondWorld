package com.et.secondworld.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetFansBean;
import com.et.secondworld.mine.adapter.MineFansRVAdapter;
import com.et.secondworld.network.GuanZhuFansNetWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.et.secondworld.application.MyApplication.instance;

public class MineFansActivity extends AppCompatActivity {

    @BindView(R.id.sp_mine_fans)
    Spinner spMineFans;
    @BindView(R.id.rfl_mine_fans)
    SmartRefreshLayout rflMineFans;
    @BindView(R.id.rv_mine_fans)
    RecyclerView rvMineFans;
    @BindView(R.id.tly_mine_fans)
    TabLayout tlyMineFans;
    @BindView(R.id.tv_mine_fans_title)
    TextView tvMineFansTitle;
    @BindView(R.id.tv_mine_fans)
    TextView tvMineFans;
    @BindView(R.id.rly_mine_fans_back)
    RelativeLayout rlyMineFansBack;
    @OnClick(R.id.rly_mine_fans_back)
    public void rlyMineFansBackOnclick(){
        this.finish();
    }
    private MineFansRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 10;
    private List<GetFansBean.ListBean> dataList = new ArrayList<>();
    private String account = "";
    private String nick = "";
    private String type = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_fans);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ButterKnife.bind(this);
//        initTabLayout();

        Intent intent = getIntent();

        account = intent.getStringExtra("account");
        nick = intent.getStringExtra("nick");
        type = intent.getStringExtra("type");
        if(nick != null) {
            tvMineFansTitle.setText(nick + "的粉丝");
        }
        initSpinner();
        initRecycleView();
        initData(0);
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
    private void initTabLayout(){
//        tlyGuanZHu.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tlyMineFans.addTab(tlyMineFans.newTab().setText("关注 1000").setTag(0));
    }
    private void initSpinner(){
//        ArrayList<String> spinnerList = new ArrayList<>();
//
//        spinnerList.add("全部粉丝");
//        spinnerList.add("互相关注");
        if(type != null && type.equals("shop")) {
            final String[] spinnerItems = {"全部粉丝"};
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                    R.layout.simple_spinner_item, spinnerItems);
            //下拉的样式res
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spMineFans.setAdapter(spinnerAdapter);
            spMineFans.setSelection(0, true);
//        final FansSpinnerAdapter adapter = new FansSpinnerAdapter(instance,spinnerList);
//        spFans.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMineFans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                    if (pos == 0) {
                        initData(0);
                    }
//                    if (pos == 1) {
//                        initData(1);
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }else {
            final String[] spinnerItems = {"全部粉丝", "互相关注"};
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                    R.layout.simple_spinner_item, spinnerItems);
            //下拉的样式res
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spMineFans.setAdapter(spinnerAdapter);
            spMineFans.setSelection(0, true);
//        final FansSpinnerAdapter adapter = new FansSpinnerAdapter(instance,spinnerList);
//        spFans.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMineFans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                    if (pos == 0) {
                        initData(0);
                    }
                    if (pos == 1) {
                        initData(1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void initRecycleView(){

        rvAdapter = new MineFansRVAdapter(dataList,type);
        rvMineFans.setLayoutManager(new LinearLayoutManager(this));
        rvMineFans.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflMineFans.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

                        page = 1;
                        initData(0);
                        refreshLayout.finishRefresh();
                    }
                },0);
            }
        });

        rflMineFans.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,String> map = new HashMap<>();

                        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();

                        /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
                        page++;
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        guanZhuFansNetWork.getFansFromNet(map, new Observer<GetFansBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetFansBean getFansBean) {


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

    }

    //type:0全部关注 1互相关注
    private void initData(int type){
        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();
        Map<String,String> map = new HashMap<>();
       /* XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
        map.put("account",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        guanZhuFansNetWork.getFansFromNet(map, new Observer<GetFansBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetFansBean getFansBean) {


                if(getFansBean.getIssuccess().equals("1")){
                    /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
                    List<GetFansBean.ListBean> datalist1 = getFansBean.getList();
                    if(datalist1.size() != 0 ){
                        tvMineFans.setVisibility(View.GONE);
                    }else {
                        tvMineFans.setVisibility(View.VISIBLE);
                    }
                   /*
                    for(int i=0;i<datalist1.size();){


                        if(datalist1.get(i).getFollowid().equals(account)){
                            datalist1.remove(i);
                            continue;
                        }
                        i++;

                    }*/

                    String fansnum = datalist1.size()+"";
//                    Log.d("fansssssssssss",fansnum);
                    tlyMineFans.removeAllTabs();
                    tlyMineFans.addTab(tlyMineFans.newTab().setText("粉丝 "+fansnum).setTag(0));
                    if(type == 0) {
                        rvAdapter.replaceAll(datalist1);
                    }else {

                        for(int i=0;i<datalist1.size();){
                            int isFriends = datalist1.get(i).getIsfriends();
                            if(isFriends == 0){
                                datalist1.remove(i);
                                continue;
                            }

                            i++;

                        }
                        rvAdapter.replaceAll(datalist1);
                    }

                }
            }
        });
    }

}
