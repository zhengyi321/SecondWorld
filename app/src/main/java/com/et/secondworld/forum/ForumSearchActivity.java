package com.et.secondworld.forum;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.bean.VillageListBean;
import com.et.secondworld.forum.adapter.ForumSearchRVAdapter;
import com.et.secondworld.fragment.forum.ForumRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.SearchNetWork;
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
 * @Date 2020/7/12
 **/
public class ForumSearchActivity extends AppCompatActivity {


    @BindView(R.id.sp_forum_search_accountid_title)
    Spinner spForumSearchAccountidTitle;

    @BindView(R.id.et_forum_search_input)
    EditText etForumSearchInput;
    @OnClick(R.id.rly_forum_search_cancel)
    public void rlyForumSearchCancelOnclick(){
        this.finish();
    }
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.rfl_forum_search)
    SmartRefreshLayout rflForumSearch;
    @BindView(R.id.pb_forum_search_loading)
    ProgressBar pbForumSearchLoading;
    @BindView(R.id.rv_forum_search)
    RecyclerView rvForumSearch;
    ForumSearchRVAdapter rvAdapter;
    private List<GetForumBean.ListBean> dataList1 = new ArrayList<>();
    int page = 1;
    int limit = 20;
    String type = "0";
    String managetype = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_search);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        managetype = intent.getStringExtra("managetype");
        initSpinner();
        initRecycleView();
        initEditListener();
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

    private void initEditListener(){
        etForumSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getBaseContext(),s+"",Toast.LENGTH_SHORT).show();
                page = 1;
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
                String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
                String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
                String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
                ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                Map<String,String> map = new HashMap<>();
                String search = etForumSearchInput.getText().toString();
                if(search.isEmpty()){
//                    refreshLayout.finishRefresh();
                    return;
                }
//                if(search.isEmpty())
                page=1;
                map.put("page",""+page);
                map.put("limit",""+limit);
                map.put("search",search);
//                        map.put("distr",distr+",");
//                        map.put("city",city+",");
                map.put("type",type);
                map.put("accountid",accountid);
                forumPostNetWork.getForumSearchFromNet(map, new Observer<GetForumBean>() {
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
                            rvAdapter.setType(type);
                            rvAdapter.replaceAll(datalist11);
//                                    ivForumLoading.setVisibility(View.GONE);
//                    if(datalist11.size() == 0) {
//                        datalist11.add(new );
//                        datalist11.add(new GetForumBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(datalist11);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pbForumLoading.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pbForumLoading.setVisibility(View.VISIBLE);
////                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(datalist11);
//                    }
//                    rvAdapter.replaceAll(getForumBean.getList());
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initSpinner(){
        final String[] spinnerItems = {"用户","文章"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spForumSearchAccountidTitle.setAdapter(spinnerAdapter);
        spForumSearchAccountidTitle.setSelection(0, true);
        spForumSearchAccountidTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
                if(pos == 0){
                   type = "0";
                    etForumSearchInput.setHint("请输入搜索 用户名/账号ID");

                }
                if(pos == 1){
                    type = "1";
                    etForumSearchInput.setHint("请输入文章标题");
                }
               /* page = 1;
                rvAdapter.setType(type);
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
                String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
                String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
                String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
                ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                Map<String,String> map = new HashMap<>();
                String search = etForumSearchInput.getText().toString();
                page=1;
                map.put("page",""+page);
                map.put("limit",""+limit);
                map.put("search",search);
//                        map.put("distr",distr+",");
//                        map.put("city",city+",");
                map.put("type",type);
                map.put("accountid",accountid);
                forumPostNetWork.getForumSearchFromNet(map, new Observer<GetForumBean>() {
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
//                                    ivForumLoading.setVisibility(View.GONE);
//                    if(datalist11.size() == 0) {
//                        datalist11.add(new );
//                        datalist11.add(new GetForumBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(datalist11);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pbForumLoading.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pbForumLoading.setVisibility(View.VISIBLE);
////                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(datalist11);
//                    }
//                    rvAdapter.replaceAll(getForumBean.getList());
                        }
                    }
                });*/

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initRecycleView(){
        rvAdapter = new ForumSearchRVAdapter(dataList1,managetype);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
        rvForumSearch.setLayoutManager(new LinearLayoutManager(this));
        rvForumSearch.setAdapter(rvAdapter);
        rvForumSearch.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflForumSearch.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String search = etForumSearchInput.getText().toString();
                        if(search.trim().isEmpty()){
                            refreshLayout.finishRefresh();
                            return;
                        }
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
                        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
                        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
                        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();

                        page=1;
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("search",search);
//                        map.put("distr",distr+",");
//                        map.put("city",city+",");
                        map.put("type",type);
                        map.put("accountid",accountid);
                        forumPostNetWork.getForumSearchFromNet(map, new Observer<GetForumBean>() {
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
                                    rvAdapter.setType(type);
                                    rvAdapter.replaceAll(datalist11);
//                                    ivForumLoading.setVisibility(View.GONE);
//                    if(datalist11.size() == 0) {
//                        datalist11.add(new );
//                        datalist11.add(new GetForumBean.ListBean() );
//                        rvAdapter.isNoMoreData(true);
//                        rvAdapter.replaceAll(datalist11);
//                        llySplitView.setVisibility(View.VISIBLE);
//                        pbForumLoading.setVisibility(View.GONE);
//                    }else {
//                        llySplitView.setVisibility(View.GONE);
//                        pbForumLoading.setVisibility(View.VISIBLE);
////                        rvAdapter.isNoMoreData(false);
//                        rvAdapter.replaceAll(datalist11);
//                    }
//                    rvAdapter.replaceAll(getForumBean.getList());
                                }
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

        rflForumSearch.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        String search = etForumSearchInput.getText().toString();
                        if(search.isEmpty()){
                            refreshLayout.finishRefresh();
                            return;
                        }
                        Map<String,String> map = new HashMap<>();
                        page++;
                        map.put("page",""+page);
                        map.put("limit",""+limit);

                        map.put("search",search);
//                        map.put("distr",distr+",");
//                        map.put("city",city+",");
                        map.put("type",type);
                        rvAdapter.setType(type);
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
                                    List<GetForumBean.ListBean> datalist11 = getForumBean.getList();

                                    if(datalist11.size() != limit ) {
//                                        datalist11.add(new GetForumBean.ListBean() );
//                                        rvAdapter.isNoMoreData(true);
                                        rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                        refreshLayout.finishLoadMoreWithNoMoreData();
//                                        if(datalist11.size() == 0) {
                                        llySplitView.setVisibility(View.VISIBLE);
                                        pbForumSearchLoading.setVisibility(View.GONE);
//                                        }
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbForumSearchLoading.setVisibility(View.VISIBLE);
//                                        rvAdapter.isNoMoreData(false);
                                        rvAdapter.addData(rvAdapter.getItemCount(),datalist11);
                                        refreshLayout.finishLoadMore();
                                    }
//                                    rvAdapter.addData(rvAdapter.getItemCount(),getForumBean.getList());
//                                    if(getForumBean.getList().size() > 0){
//                                        refreshLayout.finishLoadMore();
//                                    }else {
//                                        refreshLayout.finishLoadMore(0,true,false);
//                                        refreshLayout.finishLoadMore(0,true,true);
//                                    }
                                }
//                                refreshLayout.finishLoadMore();
                            }
                        });


//                        refreshLayout.

                    }
                },0);
            }
        });

    }

}
