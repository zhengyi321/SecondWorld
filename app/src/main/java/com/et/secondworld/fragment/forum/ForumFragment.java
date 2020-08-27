package com.et.secondworld.fragment.forum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.MainActivity;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetCollectVillageListBean;
import com.et.secondworld.forum.ForumSearchActivity;
import com.et.secondworld.fragment.firstpage.FirstFragment;
import com.et.secondworld.network.ApplyNetWork;
import com.et.secondworld.network.CollectVillageNetWork;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.forum.ForumPostNormalActivity;
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
 * @Date 2020/4/9
 **/
public class ForumFragment extends BaseFragment {




    public static ForumFragment newInstance() {


        ForumFragment firstFragment = new ForumFragment();

        // Get arguments passed in, if any
        Bundle args = firstFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putInt("aa", 0);
        firstFragment.setArguments(args);

        return firstFragment;
    }
    public ForumFragment(){
        super();
    }
    private int page = 1;
    private int limit = 10;
    @BindView(R.id.rv_forum)
    RecyclerView rvForum;
    @BindView(R.id.rfl_forum)
    SmartRefreshLayout rflForum;
    @BindView(R.id.tv_forum)
    TextView tvForum;
    @BindView(R.id.rly_forum_add)
    RelativeLayout rlyForumAdd;
    @OnClick({R.id.rly_forum_collect})
    public  void rlyForumCollectOnclick(){
        checkLogin();
        if(llyForumMark.getVisibility() == View.GONE){
            llyForumMark.setVisibility(View.VISIBLE);
        }else {
            llyForumMark.setVisibility(View.GONE);
        }
    }
    @OnClick({R.id.rly_forum,R.id.rv_forum,R.id.rfl_forum,R.id.tv_forum})
    public void rlyForumOnclick(){
        checkLogin();
//        if(llyForumMark.getVisibility() == View.GONE){
//            llyForumMark.setVisibility(View.VISIBLE);
//        }else {
            llyForumMark.setVisibility(View.GONE);
//        }
    }
    @BindView(R.id.pb_forum_loading)
    ProgressBar pbForumLoading;
    @BindView(R.id.lly_splitview)
    LinearLayout llySplitView;
    @BindView(R.id.tv_forum_title)
    TextView tvForumTitle;

    @BindView(R.id.lly_forum_mark_markerror)
    LinearLayout llyForumMarkMarkerror;
    @OnClick(R.id.lly_forum_mark_markerror)
    public void llyForumMarkMarkerrorOnclick(){
        checkLogin();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
        String lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
        if(village != null && villageallname != null && lat != null && lon != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("accountid", accountid);
            map.put("village", village);
            map.put("allname", villageallname);
            map.put("lat", lat);
            map.put("lon", lon);
            map.put("platform", "android");
            CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
            collectVillageNetWork.addErrorVillageToNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(getContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                    ivForumMarkMarkerror.setBackgroundResource(R.mipmap.wrongiconselect);
                    tvForumMarkMarkerror.setText("已报错");
                    tvForumMarkMarkerror.setTextColor(Color.parseColor("#ff5ac6de"));
                }
            });
        }
    }

    @BindView(R.id.iv_forum_mark_markerror)
    ImageView ivForumMarkMarkerror;
    @BindView(R.id.tv_forum_mark_markerror)
    TextView tvForumMarkMarkerror;
    @BindView(R.id.lly_forum_mark_mark_apply_manage)
    LinearLayout llyForumMarkMarkApplyManage;
    @OnClick(R.id.lly_forum_mark_mark_apply_manage)
    public void llyForumMarkMarkApplyManageOnclick(){
        checkLogin();
//        String manage = tvForumMarkMarkApplyManage.getText().toString();
        if(!managetype.equals("manage")&&(!managetype.equals("havemanage"))) {
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
            String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
            String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
            String lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
            String lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
            if (village != null && villageallname != null && lat != null && lon != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("accountid", accountid);
                map.put("village", village);
                map.put("allname", villageallname);
                map.put("lat", lat);
                map.put("lon", lon);
                ApplyNetWork applyNetWork = new ApplyNetWork();
                applyNetWork.applyManageToNet(map, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Toast.makeText(getContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                        ivForumMarkMarkApplyManage.setBackgroundResource(R.mipmap.applyiconselect);
                        tvForumMarkMarkApplyManage.setText("已申请");
                        tvForumMarkMarkApplyManage.setTextColor(Color.parseColor("#ff5ac6de"));
                    }
                });
            }
            if(managetype.equals("havemanage")){
                Toast.makeText(getContext(),"已有管理员",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @BindView(R.id.iv_forum_mark_mark_apply_manage)
    ImageView ivForumMarkMarkApplyManage;
    @BindView(R.id.tv_forum_mark_mark_apply_manage)
    TextView tvForumMarkMarkApplyManage;

    @BindView(R.id.lly_forum_mark)
    LinearLayout llyForumMark;
    @BindView(R.id.iv_forum_mark_markloc)
    ImageView ivForumMarkMarkLoc;
    @BindView(R.id.lly_forum_mark_markloc)
    LinearLayout llyForumMarkMarkLoc;
    @OnClick(R.id.lly_forum_mark_markloc)
    public void llyForumMarkMarkLocOnclick(){
        checkLogin();
        if(tvForumMarkMarkLoc.getText().toString().equals("已标记")){
            QueryCancelDialog queryCancelDialog = new QueryCancelDialog(view.getContext()).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                @Override
                public void isQuery(boolean isQuery) {
                    if(isQuery) {
                        markLoc();
                    }
                }
            }).build(view.getContext(),"确定取消标记吗?");
            queryCancelDialog.show();

        }else {
            markLoc();
        }
    }
    public LinearLayout getMarkGone(){
        return llyForumMark;
//        llyForumMark.setVisibility(View.GONE);
    }

    private void markLoc(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
        String lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
        if(village != null && villageallname != null && lat != null && lon != null){
            Map<String,Object> map = new HashMap<>();
            map.put("accountid",accountid);
            map.put("village",village);
            map.put("allname",villageallname);
            map.put("lat",lat);
            map.put("lon",lon);
            CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
            collectVillageNetWork.addCollectVillageToNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(view.getContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                    if(baseBean.getIssuccess().equals("2")){
                        ivForumMarkMarkLoc.setBackgroundResource(R.mipmap.markicon);
                        tvForumMarkMarkLoc.setText("标记地");
                        tvForumMarkMarkLoc.setTextColor(Color.parseColor("#ffffffff"));
                    }else {
                        ivForumMarkMarkLoc.setBackgroundResource(R.mipmap.markiconselect);
                        tvForumMarkMarkLoc.setText("已标记");
                        tvForumMarkMarkLoc.setTextColor(Color.parseColor("#ff5ac6de"));
                    }
                }
            });
        }
    }

    @OnClick(R.id.rly_forum_add)
    public void rlyForumAddOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            if(llyForumMark.getVisibility() == View.VISIBLE){
                llyForumMark.setVisibility(View.GONE);
            }else {
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(role != null && role.equals("01")) {
                  Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
                  return;

                }
                Intent intent = new Intent(view.getContext(), ForumPostNormalActivity.class);
                view.getContext().startActivity(intent);
            }
        }
    }
    private boolean flag = true;
    private long clickTime = 0;
    @BindView(R.id.iv_forum_loading)
    ImageView ivForumLoading;
    @BindView(R.id.rly_forum_loading)
    RelativeLayout rlyForumLoading;
    @OnClick(R.id.rly_forum_search)
    public void rlyForumSearchOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            if(llyForumMark.getVisibility() == View.VISIBLE){
                llyForumMark.setVisibility(View.GONE);
            }else {


                Intent intent = new Intent(view.getContext(), ForumSearchActivity.class);
                intent.putExtra("managetype",managetype);
                startActivity(intent);
            }
        }
    }
    private void checkLogin(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
        String uuuid = UniversalID.getUniversalID(getContext());
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("tel",tel);
        map.put("uuuid",uuuid.trim());
        registerLoginNetWork.checkLoginFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    MessageQueryDialog queryCancelDialog = new MessageQueryDialog(getContext()).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
                        @Override
                        public void isQuery(boolean isQuery) {
                            xcCacheManager.writeCache(xcCacheSaveName.account,"");
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
//                            MainActivity.this.finish();
                        }
                    }).build(getContext(),"您的账号("+baseBean.getMsg()+")在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
                    queryCancelDialog.show();


                }
            }
        });
    }
    @BindView(R.id.tv_forum_mark_markloc)
    TextView tvForumMarkMarkLoc;
    @OnClick(R.id.rly_forum_map)
    public void rlyForumMapOnclick(){

        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            if(llyForumMark.getVisibility() == View.VISIBLE){
                llyForumMark.setVisibility(View.GONE);
            }else {
                Intent intent = new Intent(view.getContext(), TecentMapViewActivity.class);
                startActivity(intent);
            }
        }

    }
    View view;
    private ForumRVAdapter rvAdapter;
    private List<GetForumBean.ListBean> dataList1 = new ArrayList<>();
    private String managetype = "";
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum,container,false);
        return view;
    }

    @Override
    public void initView() {
        if(view != null){
            ButterKnife.bind(this,view);
        }
        rlyForumLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivForumLoading);
        initRecycleView();
//        initTitle();
        initData();
        initCollectMark();
        initErrorVillage();
//        initApplyForManage();
        initManage();
    }

    private void initManage(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        Map<String,Object>map = new HashMap<>();
        map.put("accountid",accountid);
        map.put("allname",villageallname);
        ApplyNetWork applyNetWork = new ApplyNetWork();
        applyNetWork.getQueryManageFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    managetype = "manage";
                    rvAdapter.setManageType(managetype);
                    ivForumMarkMarkApplyManage.setBackgroundResource(R.mipmap.applyiconselect);
                    tvForumMarkMarkApplyManage.setText("管理员");
                    tvForumMarkMarkApplyManage.setTextColor(Color.parseColor("#ff5ac6de"));
                }
                if(baseBean.getIssuccess().equals("2")){
                    managetype = "havemanage";
                    rvAdapter.setManageType(managetype);

                    tvForumMarkMarkApplyManage.setText("已申请");
                    ivForumMarkMarkApplyManage.setBackgroundResource(R.mipmap.applyiconselect);
                    tvForumMarkMarkApplyManage.setTextColor(Color.parseColor("#ff5ac6de"));
                }
            }
        });
    }

    private void initErrorVillage(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        Map<String,Object>map = new HashMap<>();
        map.put("accountid",accountid);
        map.put("allname",villageallname);
        CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
        collectVillageNetWork.getErrorVillageFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    ivForumMarkMarkerror.setBackgroundResource(R.mipmap.wrongiconselect);
                    tvForumMarkMarkerror.setText("已报错");
                    tvForumMarkMarkerror.setTextColor(Color.parseColor("#ff5ac6de"));
                }
                if(baseBean.getIssuccess().equals("0")){
                    ivForumMarkMarkerror.setBackgroundResource(R.mipmap.wrongicon);
                    tvForumMarkMarkerror.setText("经纬报错");
                    tvForumMarkMarkerror.setTextColor(Color.parseColor("#ffffffff"));
                }
            }
        });
    }
    private void initApplyForManage(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        Map<String,Object>map = new HashMap<>();
        map.put("accountid",accountid);
        map.put("allname",villageallname);
        ApplyNetWork applyNetWork = new ApplyNetWork();
        applyNetWork.getApplyForManageFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    ivForumMarkMarkApplyManage.setBackgroundResource(R.mipmap.applyiconselect);
                    tvForumMarkMarkApplyManage.setText("已申请");
                    tvForumMarkMarkApplyManage.setTextColor(Color.parseColor("#ff5ac6de"));
                }
                if(baseBean.getIssuccess().equals("0")){
                    ivForumMarkMarkApplyManage.setBackgroundResource(R.mipmap.applyicon);
                    tvForumMarkMarkApplyManage.setText("申请");
                    tvForumMarkMarkApplyManage.setTextColor(Color.parseColor("#ffffffff"));
                }
            }
        });
    }

    private void initCollectMark(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        String villageallname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        Map<String,Object>map = new HashMap<>();
        map.put("accountid",accountid);
        CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
        collectVillageNetWork.getCollectVillageFromNet(map, new Observer<GetCollectVillageListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCollectVillageListBean getCollectVillageListBean) {
                List<GetCollectVillageListBean.ListBean> listBeans = getCollectVillageListBean.getList();
                ivForumMarkMarkLoc.setBackgroundResource(R.mipmap.markicon);
                tvForumMarkMarkLoc.setText("标记地");
                tvForumMarkMarkLoc.setTextColor(Color.WHITE);
                for(GetCollectVillageListBean.ListBean item: listBeans){
                    Log.d("zzz12211",item.getAllname());
                    if(item.getAllname().equals(villageallname)){
                        ivForumMarkMarkLoc.setBackgroundResource(R.mipmap.markiconselect);
                        tvForumMarkMarkLoc.setText("已标记");
                        tvForumMarkMarkLoc.setTextColor(Color.parseColor("#ff5ac6de"));
                    }
                }
            }
        });
    }
    private void initTitle(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        if(village == null || village.isEmpty()) {
            Intent intent = new Intent(view.getContext(), TecentMapViewActivity.class);
            startActivity(intent);

        }else {
            tvForumTitle.setText(village);
        }
    }

    private void initRecycleView(){
        rvAdapter = new ForumRVAdapter(dataList1,llyForumMark);

        rvForum.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvForum.setAdapter(rvAdapter);
        rvForum.setItemViewCacheSize(100);
        //设置下拉刷新和上拉加载监听
        rflForum.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
                        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
                        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
                        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
                        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
                        Map<String,String> map = new HashMap<>();
                        page=1;
                        if(city.indexOf("市")>=0){
                            city = city.substring(0,city.indexOf("市"));
                        }
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
                                    rlyForumLoading.setVisibility(View.GONE);
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

        rflForum.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
                        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
                        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
                        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
                        if(city.indexOf("市")>=0){
                            city = city.substring(0,city.indexOf("市"));
                        }
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        map.put("town",town);
                        map.put("village",village);
                        map.put("distr",distr+",");
                        map.put("city",city+",");
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
                                        pbForumLoading.setVisibility(View.GONE);
//                                        }
                                    }else {
                                        llySplitView.setVisibility(View.GONE);
                                        pbForumLoading.setVisibility(View.VISIBLE);
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

    private void initData(){
        rlyForumLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivForumLoading);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String distr = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        if(city == null){
            city = "";
        }
        page=1;
        if(city != null && city.indexOf("市")>=0){
            city = city.substring(0,city.indexOf("市"));
        }
        map.put("page",""+page);
        map.put("limit",""+limit);
        map.put("town",town);
        map.put("distr",distr+",");
        map.put("city",city+",");
        map.put("village",village);
        map.put("accountid",accountid);
//        Log.d("dddd11",town + " ,"+distr+" ,"+city+" ,"+village + accountid);
        forumPostNetWork.getForumFromNet(map, new Observer<GetForumBean>() {
            @Override
            public void onCompleted() {
                Log.d("forum11","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("forum11",""+e);
            }

            @Override
            public void onNext(GetForumBean getForumBean) {
                if(getForumBean.getIssuccess().equals("1")){
                    List<GetForumBean.ListBean> datalist11 = new ArrayList<>();
                    datalist11.addAll(getForumBean.getList());
                    if(datalist11.size() == 0){
                        tvForum.setVisibility(View.VISIBLE);
                    }else {
                        tvForum.setVisibility(View.GONE);
                    }
                    rvAdapter.replaceAll(datalist11);
                    rlyForumLoading.setVisibility(View.GONE);
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
    public void onResume(){
        super.onResume();
        initTitle();
        initData();
        initCollectMark();
        initErrorVillage();
        initManage();
    }

    public void onDestroyView() {
        super.onDestroyView();
        rvForum = null;
        view = null;
    }
}
