package com.et.secondworld;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.fragment.mine.MineFragmentPageAdapter;
import com.et.secondworld.mine.fragment.MineCollectFragment;
import com.et.secondworld.mine.fragment.MineDongTaiFragment;
import com.et.secondworld.widget.dialog.OthersShopMoreDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.viewpage.AutofitViewPager;
import com.google.android.material.tabs.TabLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.bean.AddVisitorBean;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.GetDongTaiBean;
import com.et.secondworld.bean.GetOtherBean;
import com.et.secondworld.mine.MineFansActivity;
import com.et.secondworld.mine.MineGuanZhuActivity;
import com.et.secondworld.mine.adapter.MineDongTaiRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.network.VisitorNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imageview.CircleImageView;
import com.et.secondworld.widget.scrollview.ScaleScrollView;

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
 * @Date 2020/4/25
 **/
public class VisitOthersActivity extends AppCompatActivity implements ScaleScrollView.OnScrollChangeListener{



    @BindView(R.id.ssv_visit_others)
    ScaleScrollView ssvVisitOthers;
    @BindView(R.id.v_visit_others)
    View vVisitOthers;
    @BindView(R.id.statusView)
    View statusView;
    private int colorPrimary;
    private ArgbEvaluator evaluator;

    @BindView(R.id.tly_visit_others2)
    TabLayout tlyVisitOthers2;
    @BindView(R.id.rly_visit_others_tly2)
    RelativeLayout rlyVisitOthersTly2;
    @BindView(R.id.rly_visit_others_title)
    RelativeLayout rlyVisitOthersTitle;


    private String articleaccount = "";
    private boolean isResume = false;
    @BindView(R.id.riv_visit_others_bg)
    ImageView rivVisitOthersBg;
    @BindView(R.id.civ_visit_others_head)
    CircleImageView civVisitOthersHead;
    @BindView(R.id.tv_visit_others_nick)
    TextView tvVisitOthersNick;
    @BindView(R.id.tv_visit_others_personnalnote)
    TextView tvVisitOthersPersonnalNote;
    @BindView(R.id.rly_visit_others_guanzhu)
    RelativeLayout rlyVisitOthersGuanZhu;
    @OnClick(R.id.rly_visit_others_guanzhu)
    public void rlyVisitOthersGuanZhuOnclick(){
        if(tvVisitOthersGuanZhu.getText().toString().equals("已关注")||tvVisitOthersGuanZhu.getText().toString().equals("互相关注")){
            QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                @Override
                public void isQuery(boolean isQuery) {
                    if(isQuery) {
                        guanZhuSubmit();
                    }
                }
            }).build(this,"确定取消关注吗");
            queryCancelDialog.show();
        }else {
            guanZhuSubmit();
        }
    }
    @BindView(R.id.rly_visit_others_back)
    RelativeLayout rlyVisitOthersBack;
    @OnClick(R.id.rly_visit_others_back)
    public void rlyVisitOthersBackOnclick(){
        finish();
    }
    @BindView(R.id.tv_visit_others_guanzhu)
    TextView tvVisitOthersGuanZhu;

    @BindView(R.id.tv_visit_others_good)
    TextView tvVisitOthersGood;
    @BindView(R.id.tv_visit_others_guanzhu_num)
    TextView tvVisitOthersGuanZhuNum;
    private long clickTime = 0;
    @OnClick(R.id.lly_visit_others_guanzhu)
    public void tvVisitOthersGuanZhuNumOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineGuanZhuActivity.class);
            intent.putExtra("account", articleaccount);
            intent.putExtra("nick", tvVisitOthersNick.getText().toString());
            intent.putExtra("type", "others");
            startActivity(intent);
            isResume = true;
        }
    }
    @OnClick(R.id.rly_visit_others_more)
    public void rlyVisitOthersMoreOnclick(){
        OthersShopMoreDialog othersShopMoreDialog = new OthersShopMoreDialog(this,articleaccount,null).Build.build(this);
        othersShopMoreDialog.show();
    }
    @BindView(R.id.tv_visit_others_fans_num)
    TextView tvVisitOthersFansNum;
    @OnClick(R.id.lly_visit_others_fans)
    public void tvVisitOthersFansNumOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineFansActivity.class);
            intent.putExtra("account", articleaccount);
            intent.putExtra("nick", tvVisitOthersNick.getText().toString());
            intent.putExtra("type", "shop");
            startActivity(intent);
            isResume = true;
        }
    }
    @BindView(R.id.tly_visit_others)
    TabLayout tlyVisitOthers;
    @BindView(R.id.vp_visit_others)
    AutofitViewPager vpVisitOthers;
    @BindView(R.id.iv_visit_others_loading)
    ImageView ivVisitOthersLoading;
    @BindView(R.id.rly_visit_others_loading)
    RelativeLayout rlyVisitOthersLoading;
    @BindView(R.id.tv_visit_others_account)
    TextView tvVisitOthersAccount;
   /* @BindView(R.id.rv_visit_others_dongtai)
    RecyclerView rvVisitOthersDongTai;
    @BindView(R.id.rfl_visit_others_dongtai)
    SmartRefreshLayout rflVisitOthersDongTai;*/
    private MineDongTaiRVAdapter rvAdapter;
    private int page = 1;
    private int limit = 5;
    private List<GetDongTaiBean.ListBean> dataList1 = new ArrayList<>();
    private String isFans = "";
    private MineCollectFragment mineCollectFragment;
    private MineDongTaiFragment mineDongTaiFragment;

    private MineFragmentPageAdapter mineFragmentPageAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_visit_others);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        rlyVisitOthersLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivVisitOthersLoading);
        initZoomScrollView();
        initGetIntent();
        initData();

//        initDongTaiRecycleView();
//        initDongTaiData();
        addVisitor();
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
    private void initTab(String dongtainum,String collectnum){
//        vpFansFollowGoods = view.findViewById(R.id.vp_fans_follow_goods);
        ArrayList<Fragment> list_fragment = new ArrayList<>();
        ArrayList<String> list_title = new ArrayList<>();
        mineDongTaiFragment = new MineDongTaiFragment(articleaccount);
        mineCollectFragment = new MineCollectFragment(articleaccount);
        list_fragment.add(mineDongTaiFragment);
        list_fragment.add(mineCollectFragment);
//        list_fragment.add(new FirstPageFragment());
//        list_fragment.add(allOrderFragment);


        list_title.add("动态 "+dongtainum+"");
        list_title.add("收藏 "+collectnum);

//        list_title.add("待收货");
        mineFragmentPageAdapter = new MineFragmentPageAdapter(getSupportFragmentManager(), list_fragment, list_title);
        if (vpVisitOthers == null) {
            Toast.makeText(this, "this is null", Toast.LENGTH_SHORT).show();
            return;
        }
        vpVisitOthers.setAdapter(mineFragmentPageAdapter);
        tlyVisitOthers.setupWithViewPager(vpVisitOthers);
        tlyVisitOthers.setTabMode(tlyVisitOthers.MODE_FIXED);
        tlyVisitOthers2.setupWithViewPager(vpVisitOthers);
        tlyVisitOthers2.setTabMode(tlyVisitOthers2.MODE_FIXED);
    }
    private void initZoomScrollView(){
        colorPrimary = ContextCompat.getColor(this, R.color.white);
//        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, getStatusBarHeight());
//        statusView.setLayoutParams(lp);
//        statusView.setBackgroundColor(Color.TRANSPARENT);
//        statusView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ssvVisitOthers.setTargetView(rivVisitOthersBg,vVisitOthers);
        ssvVisitOthers.setOnScrollChangeListener(this);
        ssvVisitOthers.setNeedScroll(true);
    }
    private void initGetIntent(){
        Intent intent = getIntent();
        articleaccount = intent.getStringExtra("articleaccount");
    }
    private void initData(){
        rlyVisitOthersLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivVisitOthersLoading);
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,Object> map = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null){
            account = "";
            rlyVisitOthersGuanZhu.setVisibility(View.GONE);
        }
        if(account.equals(articleaccount)){
            rlyVisitOthersGuanZhu.setVisibility(View.GONE);
        }
        map.put("otheraccount",articleaccount);
        map.put("account",account);
        mineNetWork.getOtherFromNet(map, new Observer<GetOtherBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetOtherBean getOtherBean) {
                if(getOtherBean.getIssuccess().equals("2")){
                    RequestOptions optionshead = new RequestOptions().fallback(R.mipmap.head);
                    RequestOptions optionsbg = new RequestOptions().fallback(R.mipmap.wodebg);
//                    Glide.with(getBaseContext()).load(getOtherBean.getBg()).apply(optionsbg).into(rivVisitOthersBg);
//                    Glide.with(getBaseContext()).load(getOtherBean.getHead()).apply(optionshead).into(civVisitOthersHead);
                    if(getOtherBean.getBg() != null && !getOtherBean.getBg().isEmpty()) {
                        ImageLoader.getInstance().displayImage(getOtherBean.getBg(), rivVisitOthersBg, ImageLoaderUtils.options);
                    }
                    if(getOtherBean.getHead() != null && !getOtherBean.getHead().isEmpty()) {
                        ImageLoader.getInstance().displayImage(getOtherBean.getHead(), civVisitOthersHead, ImageLoaderUtils.options);
                    }
                    isFans = getOtherBean.getIsguanzhu();
                    if (getOtherBean.getIsguanzhu().equals("0")) {
                        if (getOtherBean.getIsfriend().equals("0")) {
                            rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvVisitOthersGuanZhu.setText("+关注");
                            tvVisitOthersGuanZhu.setTextColor(Color.WHITE);
                        }else{
                            rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvVisitOthersGuanZhu.setText("已关注");
                            tvVisitOthersGuanZhu.setTextColor(Color.GRAY);
                        }
                    } else {
                        if (getOtherBean.getIsfriend().equals("0")) {
                            rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvVisitOthersGuanZhu.setText("+关注");
                            tvVisitOthersGuanZhu.setTextColor(Color.WHITE);

                        } else {
                            rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvVisitOthersGuanZhu.setText("互相关注");
                            tvVisitOthersGuanZhu.setTextColor(Color.GRAY);
                        }
                    }
                    tvVisitOthersAccount.setText(articleaccount);
                    tvVisitOthersNick.setText(getOtherBean.getNick());
                    tvVisitOthersPersonnalNote.setText(getOtherBean.getPersonnalnote());
                    tvVisitOthersGood.setText(""+getOtherBean.getPraise());
                    tvVisitOthersGuanZhuNum.setText(""+getOtherBean.getGuanzhunum());
                    tvVisitOthersFansNum.setText(""+getOtherBean.getFansnum());
                    initTab(getOtherBean.getDongtai(),getOtherBean.getCollect());
                    rlyVisitOthersLoading.setVisibility(View.GONE);
//                    tlyVisitOthers.addTab(tlyVisitOthers.newTab().setText("动态 "+getOtherBean.getDongtai()).setTag(0));
//                    tlyVisitOthers2.addTab(tlyVisitOthers2.newTab().setText("动态 "+getOtherBean.getDongtai()).setTag(0));
                }
            }
        });
    }

    private void initDongTaiRecycleView(){
//        rvAdapter = new MineDongTaiRVAdapter(dataList1);

       /* rvVisitOthersDongTai.setLayoutManager(new LinearLayoutManager(this));
        rvVisitOthersDongTai.setAdapter(rvAdapter);
        rvVisitOthersDongTai.setItemViewCacheSize(100);

        //设置下拉刷新和上拉加载监听
        rflVisitOthersDongTai.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page = 1;
                        map.put("account",articleaccount);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetDongTaiBean getDongTaiBean) {
                                if(getDongTaiBean.getIssuccess().equals("1")){
                                    rvAdapter.replaceAll(getDongTaiBean.getList());
                                    refreshLayout.finishRefresh();
                                }
                            }
                        });

                    }
                },0000);
            }
        });

        rflVisitOthersDongTai.setOnLoadMoreListener(new OnLoadMoreListener() {
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

                        map.put("account",articleaccount);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetDongTaiBean getDongTaiBean) {
                                if(getDongTaiBean.getIssuccess().equals("1")){
                                    rvAdapter.addData(rvAdapter.getItemCount(),getDongTaiBean.getList());
                                    refreshLayout.finishLoadMore();
                                }
                            }
                        });



                    }
                },0000);
            }
        });*/
    }

    private void initDongTaiData(){
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
      /*  XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);*/
        map.put("account",articleaccount);
//        Log.d("visitother1",articleaccount);
        page = 1;
        map.put("page",""+page);
        map.put("limit",""+limit);
        forumPostNetWork.getDongTaiFromNet(map, new Observer<GetDongTaiBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetDongTaiBean getDongTaiBean) {
                if(getDongTaiBean.getIssuccess().equals("1")){
                    rvAdapter.replaceAll(getDongTaiBean.getList());
                }
            }
        });
    }

    private void guanZhuSubmit(){
        Map<String,String> map = new HashMap<>();
        GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account",articleaccount);
        map.put("followid",account);
           /* if(isFans == 0){
                map.put("type","1");
            }
            if(isFans == 1){
                map.put("type","0");
            }*/
        guanZhuFansNetWork.cancelGuanZhuToNet(map, new Observer<CancelGuanZhuBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CancelGuanZhuBean cancelGuanZhuBean) {
                if(cancelGuanZhuBean.getIssuccess().equals("1")) {
                    if(isFans.equals("1")) {
//                            if (isFriends == 0) {
                        rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvVisitOthersGuanZhu.setText("互相关注");
                        tvVisitOthersGuanZhu.setTextColor(Color.GRAY);

//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//                                rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
//                                tvMineGuanZhuRVItemGuanZhu.setText("关注");
//                                tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
//
//                                dataList.get(pos).setIsfans(0);
//                                dataList.get(pos).setIsfriends(0);
//                            }
                    }
                    if(isFans.equals("0")) {
//                            if (isFriends == 0) {
                        rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvVisitOthersGuanZhu.setText("已关注");
                        tvVisitOthersGuanZhu.setTextColor(Color.GRAY);

//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//
//
//                            }
                    }
//                        notifyDataSetChanged();
                }else {
                    rlyVisitOthersGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                    tvVisitOthersGuanZhu.setText("+关注");
                    tvVisitOthersGuanZhu.setTextColor(Color.WHITE);

//                            dataList.get(pos).setIsfans(0);
//                        dataList.get(pos).setIsfriends(0);
                }
                Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addVisitor(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(articleaccount.equals(account)){
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("account",articleaccount);
        map.put("visitor",account);
        VisitorNetWork visitorNetWork = new VisitorNetWork();
        visitorNetWork.addVisitorToNet(map, new Observer<AddVisitorBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                Log.d("visitother1",""+e);
            }

            @Override
            public void onNext(AddVisitorBean addVisitorBean) {
//                Log.d("visitother1",addVisitorBean.getMsg());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isResume){
            initData();
            isResume = false;
        }
//        Toast.makeText(getContext(),"this is onresume",Toast.LENGTH_LONG).show();
//        initDongTaiData();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (null != tlyVisitOthers && null != tlyVisitOthers2 && null != rlyVisitOthersTitle && null != statusView) {
//            int distance = 2*tlyVisitOthers.getTop() - statusView.getHeight() ;
            int distance = 200 ;
            float ratio = v.getScaleY() * 1f / distance;
            if (distance <= v.getScrollY()) {
                ratio = 1;
                if (rlyVisitOthersTly2.getVisibility() != View.VISIBLE) {
                    rlyVisitOthersTly2.setVisibility(View.VISIBLE);
                    rlyVisitOthersTitle.setVisibility(View.GONE);
                    statusView.setBackgroundColor(colorPrimary);
                }
                ssvVisitOthers.setNeedScroll(false);
            } else {
                if (rlyVisitOthersTly2.getVisibility() == View.VISIBLE) {
                    rlyVisitOthersTly2.setVisibility(View.INVISIBLE);
                    statusView.setBackgroundColor(Color.TRANSPARENT);
                    rlyVisitOthersTitle.setVisibility(View.VISIBLE);
                }
                ssvVisitOthers.setNeedScroll(true);
            }
            if (null == evaluator) {
                evaluator = new ArgbEvaluator();
            }

//            rlyVisitOthersTitle.setBackgroundColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, colorPrimary));
        }
    }
}
