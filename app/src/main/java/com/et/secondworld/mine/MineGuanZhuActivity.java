package com.et.secondworld.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.et.secondworld.bean.GetGuanZhuBean;
import com.et.secondworld.mine.adapter.MineGuanZhuRVAdapter;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.et.secondworld.application.MyApplication.instance;

public class MineGuanZhuActivity extends AppCompatActivity {

    @BindView(R.id.sp_mine_guanzhu)
    Spinner spMineGuanZhu;
    @BindView(R.id.rfl_mine_guanzhu)
    SmartRefreshLayout rflMineGuanZhu;
    @BindView(R.id.tv_mine_guanzhu_title)
    TextView tvMineGuanZhuTitle;
    @BindView(R.id.rv_mine_guanzhu)
    RecyclerView rvMineGuanZhu;
    @BindView(R.id.tly_mine_guanzhu)
    TabLayout tlyMineGuanZHu;
    @BindView(R.id.tv_mine_guanzhu)
    TextView tvMineGuanZhu;
    @BindView(R.id.rly_mine_guanzhu_back)
    RelativeLayout rlyMineGuanZHuBack;
    @OnClick(R.id.rly_mine_guanzhu_back)
    public void rlyMineGuanZhuBackOnclick(){
        this.finish();
    }
    private MineGuanZhuRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 10;
    int type = 0;
    private List<GetGuanZhuBean.ListBean> dataList = new ArrayList<>();
    private String account = "";
    private String nick = "";
    private String guanzhutype = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_guanzhu);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ButterKnife.bind(this);
        Intent intent = getIntent();

        account = intent.getStringExtra("account");
        nick = intent.getStringExtra("nick");
        guanzhutype = intent.getStringExtra("type");
        if(nick != null) {
            tvMineGuanZhuTitle.setText(nick + "的关注");
        }
//        initTabLayout();
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
//        tlyMineGuanZHu.addTab(tlyMineGuanZHu.newTab().setText("关注 1000").setTag(0));
    }
    private void initSpinner(){
//        ArrayList<String> spinnerList = new ArrayList<>();
//
//        spinnerList.add("全部关注");
//        spinnerList.add("互相关注");
        if(guanzhutype != null) {
            if (guanzhutype.equals("others")) {
                final String[] spinnerItems = {"关注的人  ", "关注的店铺  "};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spMineGuanZhu.setAdapter(spinnerAdapter);
                spMineGuanZhu.setSelection(0, true);
//        final GuanZhuSpinnerAdapter adapter = new GuanZhuSpinnerAdapter(instance,spinnerList);
//        spGuanZhu.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMineGuanZhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                        if (pos == 0) {
                            type = 0;
                            initData(0);
                        }
                  /*  if(pos == 1){
                        type = 1;
                        initData(1);
                    }*/
                        if (pos == 1) {
                            type = 2;

                            initData(2);
                        }
                        rvAdapter.setType(type);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else {
                final String[] spinnerItems = {"关注的人  ", "好友  ", "关注的店铺  "};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spMineGuanZhu.setAdapter(spinnerAdapter);
                spMineGuanZhu.setSelection(0, true);
//        final GuanZhuSpinnerAdapter adapter = new GuanZhuSpinnerAdapter(instance,spinnerList);
//        spGuanZhu.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMineGuanZhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                        if (pos == 0) {
                            type = 0;
                            initData(0);
                        }
                        if (pos == 1) {
                            type = 1;
                            initData(1);
                        }
                        if (pos == 2) {
                            type = 2;

                            initData(2);
                        }
                        rvAdapter.setType(type);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }else {
            final String[] spinnerItems = {"关注的人  ", "好友  ", "关注的店铺  "};
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                    R.layout.simple_spinner_item, spinnerItems);
            //下拉的样式res
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spMineGuanZhu.setAdapter(spinnerAdapter);
            spMineGuanZhu.setSelection(0, true);
//        final GuanZhuSpinnerAdapter adapter = new GuanZhuSpinnerAdapter(instance,spinnerList);
//        spGuanZhu.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMineGuanZhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                    if (pos == 0) {
                        type = 0;
                        initData(0);
                    }
                    if (pos == 1) {
                        type = 1;
                        initData(1);
                    }
                    if (pos == 2) {
                        type = 2;

                        initData(2);
                    }
                    rvAdapter.setType(type);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }
    }

    private void initRecycleView(){

        rvAdapter = new MineGuanZhuRVAdapter(dataList,guanzhutype);
        rvMineGuanZhu.setLayoutManager(new LinearLayoutManager(this));
        rvMineGuanZhu.setAdapter(rvAdapter);
        //设置下拉刷新和上拉加载监听
        rflMineGuanZhu.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

                        page = 1;
                        initData(type);
                        refreshLayout.finishRefresh();
                    }
                },0);
            }
        });

        rflMineGuanZhu.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,String> map = new HashMap<>();

                        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();

                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        page++;
                        map.put("account",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        if(type == 2) {
                            guanZhuFansNetWork.getGuanZhuShopFromNet(map, new Observer<GetGuanZhuBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(GetGuanZhuBean getGuanZhuBean) {

                                    if (getGuanZhuBean.getIssuccess().equals("1")) {
                                        String guanZhuNum = getGuanZhuBean.getList().size() + "";
//                        Log.d("fansssssssssss", guanZhuNum);
                                        tlyMineGuanZHu.removeAllTabs();
                                        tlyMineGuanZHu.addTab(tlyMineGuanZHu.newTab().setText("关注的店铺 " + guanZhuNum).setTag(0));
                                        rvAdapter.addData(rvAdapter.getItemCount(), getGuanZhuBean.getList());
                        /*
                        if (type == 0) {
                            rvAdapter.replaceAll(getGuanZhuBean.getList());
                        } else {
                            List<GetGuanZhuBean.ListBean> datalist1 = getGuanZhuBean.getList();
                            for (int i = 0; i < datalist1.size(); ) {
                                int isFriends = datalist1.get(i).getIsfriends();
                                if (isFriends == 0) {
                                    datalist1.remove(i);
                                    continue;
                                }
                                i++;
                            }
                            rvAdapter.replaceAll(datalist1);
                        }
*/
                                    }
                                }
                            });
                        }else {
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
                                        tlyMineGuanZHu.removeAllTabs();
                                        int guanZhuNum = getGuanZhuBean.getList().size() + rvAdapter.getItemCount();
                                        tlyMineGuanZHu.addTab(tlyMineGuanZHu.newTab().setText("关注的店铺 " + guanZhuNum).setTag(0));
                                        rvAdapter.addData(rvAdapter.getItemCount(), getGuanZhuBean.getList());


                                    }
                                }
                            });
                        }
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
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        if(type == 2) {
            guanZhuFansNetWork.getGuanZhuShopFromNet(map, new Observer<GetGuanZhuBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(GetGuanZhuBean getGuanZhuBean) {

                    if (getGuanZhuBean.getIssuccess().equals("1")) {
                        String guanZhuNum = getGuanZhuBean.getList().size() + "";
//                        Log.d("fansssssssssss", guanZhuNum);
                        tlyMineGuanZHu.removeAllTabs();
                        tlyMineGuanZHu.addTab(tlyMineGuanZHu.newTab().setText("关注的店铺 " + guanZhuNum).setTag(0));
                        if(getGuanZhuBean.getList().size() != 0){
                            tvMineGuanZhu.setVisibility(View.GONE);
                        }else {
                            tvMineGuanZhu.setVisibility(View.VISIBLE);
                        }
                        rvAdapter.replaceAll(getGuanZhuBean.getList());
                        /*
                        if (type == 0) {
                            rvAdapter.replaceAll(getGuanZhuBean.getList());
                        } else {
                            List<GetGuanZhuBean.ListBean> datalist1 = getGuanZhuBean.getList();
                            for (int i = 0; i < datalist1.size(); ) {
                                int isFriends = datalist1.get(i).getIsfriends();
                                if (isFriends == 0) {
                                    datalist1.remove(i);
                                    continue;
                                }
                                i++;
                            }
                            rvAdapter.replaceAll(datalist1);
                        }
*/
                    }
                }
            });
        }else {
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
                        String guanZhuNum = getGuanZhuBean.getList().size() + "";
                        if(getGuanZhuBean.getList().size() != 0){
                            tvMineGuanZhu.setVisibility(View.GONE);
                        }else {
                            tvMineGuanZhu.setVisibility(View.VISIBLE);
                        }
//                        Log.d("fansssssssssss", guanZhuNum);
                        tlyMineGuanZHu.removeAllTabs();
                        tlyMineGuanZHu.addTab(tlyMineGuanZHu.newTab().setText("关注的人 " + guanZhuNum).setTag(0));
                        if (type == 0) {
                            rvAdapter.replaceAll(getGuanZhuBean.getList());
                        } else {
                            List<GetGuanZhuBean.ListBean> datalist1 = getGuanZhuBean.getList();
                            for (int i = 0; i < datalist1.size(); ) {
                                int isFriends = datalist1.get(i).getIsfriends();
                                if (isFriends == 0) {
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
}
