package com.et.secondworld.fragment.mine;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.behavior.NoScrollViewPager;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imageview.HeadBgImagePreActivity;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;
import com.et.secondworld.widget.viewpage.FullViewPager;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.MineBean;
import com.et.secondworld.mine.MineEditDataActivity;
import com.et.secondworld.mine.MineFansActivity;
import com.et.secondworld.mine.MineFootMarkActivity;
import com.et.secondworld.mine.MineGuanZhuActivity;
import com.et.secondworld.mine.MineMyShopActivity;
import com.et.secondworld.mine.MineOpenShopActivity;
import com.et.secondworld.mine.MineSettingActivity;
import com.et.secondworld.mine.fragment.MineCollectFragment;
import com.et.secondworld.mine.fragment.MineDongTaiFragment;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MineGetGoodDialog;
import com.et.secondworld.widget.dialog.MinePraiseDialog;
import com.et.secondworld.widget.scrollview.ScaleScrollView;
import com.et.secondworld.widget.viewpage.AutofitViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MineFragment extends BaseFragment  implements ScaleScrollView.OnScrollChangeListener{
    public static MineFragment newInstance() {


        MineFragment firstFragment = new MineFragment();

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
    public MineFragment(){
        super();
    }
    private MineGetGoodDialog mineGetGoodDialog;
    @BindView(R.id.statusView)
    View statusView;
    private int colorPrimary;
    private ArgbEvaluator evaluator;
    @BindView(R.id.rly_mine_title)
    RelativeLayout rlyMineTitle;
    @BindView(R.id.v_mine_secondView)
    View vMineSecondView;
    @BindView(R.id.vp_mine)
    AutofitViewPager vpFansFollowGoods;
    @BindView(R.id.tly_mine)
    TabLayout tabLyFavGoodsCollect;
    @OnClick(R.id.tly_mine)
    public void tlyMineOnclick(){
        ssvMine.setNeedScroll(true);
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
    @BindView(R.id.tly_mine2)
    TabLayout tabLyFavGoodsCollect2;
    @BindView(R.id.rly_mine_shop_open)
    RelativeLayout rlyMineShopOpen;
    private long clickTime = 0;
    @OnClick(R.id.rly_mine_shop_open)
    public void rlyMineShopOpenOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
            isResume = true;
            Intent intent = new Intent(getActivity(), MineOpenShopActivity.class);
//            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_OPENSHOP);
        }
    }
/*
    @BindView(R.id.rfl_mine)
    SmartRefreshLayout rflMine;*/
    @BindView(R.id.tv_mine_editdata)
    TextView tvMineEditData;
    @OnClick(R.id.tv_mine_editdata)
    public void tvMineEditDataOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
            Intent intent = new Intent(getActivity(), MineEditDataActivity.class);
//            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_EDIT);
            isResume = true;
        }
    }
    @BindView(R.id.lly_mine_guanzhu)
    LinearLayout llyMineGuanZhu;
    @OnClick(R.id.lly_mine_guanzhu)
    public void llyMineGuanZhuOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            Intent intent = new Intent(getActivity(), MineGuanZhuActivity.class);
            intent.putExtra("account", account);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_GUANZHU);
            isResume = true;
        }
    }
    @BindView(R.id.lly_mine_fans)
    LinearLayout llyMineFans;
    @OnClick(R.id.lly_mine_fans)
    public void llyMineFansOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            Intent intent = new Intent(getActivity(), MineFansActivity.class);
            intent.putExtra("account", account);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_FANS);
            isResume = true;
        }
    }
    @BindView(R.id.lly_mine_footmark)
    LinearLayout llyMineFootMark;
    @OnClick(R.id.lly_mine_footmark)
    public void llyMineFootMarkOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
            Intent intent = new Intent(getActivity(), MineFootMarkActivity.class);
//            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_FOOTMARK);
        }
    }
    @BindView(R.id.lly_mine_get_good)
    LinearLayout llyMineGetGood;
    @OnClick(R.id.lly_mine_get_good)
    public void llyMineGetGoodOnclick(){
//        mineGetGoodDialog = new MineGetGoodDialog(view.getContext()).Build.build(getActivity());
//        showDialog();
        checkLogin();
        MinePraiseDialog praiseDialog = new MinePraiseDialog(view.getContext()).Build.build(view.getContext(),tvMineGetGoodNum.getText().toString(),tvMineNick.getText().toString());
        praiseDialog.show();
    }
    public void showDialog() {
        if (mineGetGoodDialog != null && !mineGetGoodDialog.isShowing())
            mineGetGoodDialog.show();

    }

    @BindView(R.id.tv_mine_open_or_mine_shop)
    TextView tvMineOpenOrMineShop;
    @BindView(R.id.rly_mine_my_shop)
    RelativeLayout rlyMineMyShop;
    @OnClick(R.id.rly_mine_my_shop)
    public void rlyMineMyShopOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
            Intent intent = new Intent(getActivity(), MineMyShopActivity.class);
            ((Activity)view.getContext()).startActivityForResult(intent, SELECT_FOR_SHOP);
        }
    }
    private final int SELECT_FOR_SHOP = 0x002;
    private final int SELECT_FOR_FOOTMARK = 0x003;
    private final int SELECT_FOR_GUANZHU = 0x004;
    private final int SELECT_FOR_FANS = 0x005;
    private final int SELECT_FOR_EDIT = 0x006;
    private final int SELECT_FOR_OPENSHOP = 0x006;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        init(account);
    }
    @BindView(R.id.tv_mine_nick)
    TextView tvMineNick;
    @BindView(R.id.tv_mine_account)
    TextView tvMineAccount;
    @BindView(R.id.tv_mine_personnalnote)
    TextView tvMinePersonnalNote;
    @BindView(R.id.tv_mine_get_good_num)
    TextView tvMineGetGoodNum;
    @BindView(R.id.tv_mine_guanzhu_num)
    TextView tvMineGuanZhuNum;
    @BindView(R.id.tv_mine_fans_num)
    TextView tvMineFansNum;
    @BindView(R.id.tv_mine_footmark_num)
    TextView tvMineFootMarkNum;
    @BindView(R.id.siv_mine_head)
    ImageView sivMineHead;
    ArrayList<String> imgUrlList = new ArrayList<>();
    @OnClick(R.id.siv_mine_head)
    public void sivMineHeadOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            isResume = true;
            imgUrlList.clear();
            imgUrlList.add(xcCacheManager.readCache(xcCacheSaveName.userHeadImgUrl));
            Intent intent = new Intent(view.getContext(), HeadBgImagePreActivity.class);
            intent.putStringArrayListExtra("imgurlList", (ArrayList<String>) imgUrlList);
            intent.putExtra("imagePosition", 0);
            intent.putExtra("type", "head");
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, 100, 100, 400, 400);
            startActivity(intent, compat.toBundle());
        }
    }
    @BindView(R.id.riv_mine_bg)
    ImageView ivMineBg;
    @OnClick(R.id.riv_mine_bg)
    public void ivMineBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            isResume = true;
            imgUrlList.clear();
            imgUrlList.add(xcCacheManager.readCache(xcCacheSaveName.userBgUrl));
            Intent intent = new Intent(view.getContext(), HeadBgImagePreActivity.class);
            intent.putStringArrayListExtra("imgurlList", (ArrayList<String>) imgUrlList);
            intent.putExtra("imagePosition", 0);
            intent.putExtra("type", "bg");
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, 100, 100, 400, 400);
            startActivity(intent, compat.toBundle());
        }
    }
    @BindView(R.id.ib_mine_setting)
    ImageButton ibMineSetting;
    @OnClick(R.id.ib_mine_setting)
    public void ibMineSettingOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);

                return;
            }
            Intent intent = new Intent(view.getContext(), MineSettingActivity.class);
            view.getContext().startActivity(intent);
        }
    }
    @BindView(R.id.ssv_mine)
    ScaleScrollView ssvMine;
    private boolean first = true;
    private int mInitWidth;
    private int mInitHeight;
//    private String[] titles = new String[]{"作品","收藏","喜欢"};
    private MineCollectFragment mineCollectFragment;
    private MineDongTaiFragment mineDongTaiFragment;

    private MineFragmentPageAdapter mineFragmentPageAdapter;
    View view ;


    @BindView(R.id.iv_mine_loading)
    ImageView ivMineLoading;
    @BindView(R.id.rly_mine_loading)
    RelativeLayout rlyMineLoading;
    private Rect normal = new Rect();// 矩形空白

    private boolean isResume = false;

    // 上下滑动标记
//    MainActivity.MyOnTouchListener myOnTouchListener;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_mine,container,false);

    /*    myOnTouchListener = new MainActivity.MyOnTouchListener() {

            @Override
            public boolean onTouch(MotionEvent ev) {
//                mOriginalHeight = ivMineBg.getHeight(); // 160,当前显示图片高度
//                drawableHeight = ivMineBg.getDrawable().getIntrinsicHeight(); // 488,图片原始高度
                mHeaderWidth = ivMineBg.getMeasuredWidth();
                mHeaderHeight = ivMineBg.getMeasuredHeight();
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 手指离开后头部恢复图片
                        mIsPulling = false;
                        replyView();
//                        clear();
                        if (isNeedAnimation()) {
                            animation();
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        y = ev.getY();// 获取点击y坐标
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final float preY = y;
                        float nowY = ev.getY();
                        int deltaY = (int) (preY - nowY);// 获取滑动距离

                        y = nowY;
                        // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                        *//*if (isNeedMove()) {
                            if (normal.isEmpty()) {
                                // 填充矩形，目的：就是告诉this:我现在已经有了，你松开的时候记得要执行回归动画.
                                normal.set(inner.getLeft(), inner.getTop(),
                                        inner.getRight(), inner.getBottom());
                            }
                            // 移动布局
                            inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
                                    inner.getRight(), inner.getBottom() - deltaY / 2);
                        }
                        if (!mIsPulling) {
                            // 第一次下拉
                            if (getScrollY() == 0) {
                                // 滚动到顶部时记录位置，否则正常返回
                                mLastY = (int) ev.getY();
                            } else {
                                break;
                            }
                        }*//*

                        int distance = (int) ((ev.getY() - mLastY) * mScaleRatio);
                        // 当前位置比记录位置要小时正常返回
                        if (distance < 0) {
                            break;
                        }
                        mIsPulling = true;
                        setZoom(distance);



                        break;
                }

                return false;
            }
        };
        ((MainActivity) getActivity())
                .registerMyOnTouchListener(myOnTouchListener);*/
        return view;
    }
    /***
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的高度
     * getHeight()：获取的是当前控件在屏幕中显示的高度
     *
     * @return
     */
//    public boolean isNeedMove() {
//        int offset = inner.getMeasuredHeight() - getHeight();
//        int scrollY = getScrollY();
//        // 0是顶部，后面那个是底部
//        if (scrollY == 0 || scrollY == offset) {
//            return true;
//        }
//        return false;
//    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            getFragment("first");
//            manager = getSupportFragmentManager();
//            initRecycleView();
            String account1 = intent.getStringExtra("account");
//            Toast.makeText(getContext(),"this is onresume"+account1, Toast.LENGTH_LONG).show();
            init(account1);
        }
    }
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        rlyMineLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivMineLoading);
        localBroadcastManager = LocalBroadcastManager.getInstance(view.getContext()); // 获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
//        initMyScrollView();
//        initTab();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        init(account);
        initZoomScrollView();
//        Glide.with(getContext())
////                            .load(mineBean.getHead())
//                .load(new File("http://et-stars.cn/18767775244/asd.png"))
//                .apply(new RequestOptions()
//                        .fallback(R.mipmap.imghead)
//                        .circleCrop()
//                )
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//                        Log.d("zzzddd11",e+"");
//                        return false;
//
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                // 重点在这行
//                .into(sivMineHead);
//        ImageLoader.getInstance().displayImage("http://et-stars.cn/18767775244/asd.png",sivMineHead,ImageLoaderUtils.options);
        ssvMine.setNeedScroll(true);
//        initRefresh();
    }

    private int getStatusBarHeight() {
        int height = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    private void initZoomScrollView() {
        colorPrimary = ContextCompat.getColor(getContext(), R.color.white);
//        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, getStatusBarHeight());
//        statusView.setLayoutParams(lp);
//        statusView.setBackgroundColor(Color.TRANSPARENT);
//        statusView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ssvMine.setTargetView(ivMineBg, vMineSecondView);
        ssvMine.setOnScrollChangeListener(this);
       /* ssvMine.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
//                magicIndicator.getLocationOnScreen(location);
                int xPosition = location[0];
                int yPosition = location[1];
                Log.d("yPosti1",""+scrollY);
//                if (yPosition < toolBarPositionY) {
//                    tablayout.setVisibility(View.VISIBLE);
//                    scrollView.setNeedScroll(false);
//                } else {
//                    tablayout.setVisibility(View.GONE);
//                    scrollView.setNeedScroll(true);
//                }

            }
        });*/
    }

    /*private void initRefresh(){
        //设置下拉刷新和上拉加载监听
        rflMine.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

                        init();
                        refreshLayout.finishRefresh();
                        Log.d("this is mine2222","mine");
                    }
                },2000);
            }
        });
    }*/

    private void initTab(String dongtainum,String collectnum,String account){
//        vpFansFollowGoods = view.findViewById(R.id.vp_fans_follow_goods);
         ArrayList<Fragment> list_fragment = new ArrayList<>();
         ArrayList<String> list_title = new ArrayList<>();
//        Log.d("zzzz1111account",account);
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        mineDongTaiFragment = new MineDongTaiFragment(account);
        mineCollectFragment = new MineCollectFragment(account);
        list_fragment.add(mineDongTaiFragment);
        list_fragment.add(mineCollectFragment);
//        list_fragment.add(new FirstPageFragment());
//        list_fragment.add(allOrderFragment);


        list_title.add("动态 "+dongtainum+"");
        list_title.add("收藏 "+collectnum);

//        list_title.add("待收货");
        mineFragmentPageAdapter = new MineFragmentPageAdapter(getChildFragmentManager(), list_fragment, list_title);
        if (vpFansFollowGoods == null) {
            Toast.makeText(view.getContext(), "this is null", Toast.LENGTH_SHORT).show();
            return;
        }
        vpFansFollowGoods.setAdapter(mineFragmentPageAdapter);
        tabLyFavGoodsCollect.setupWithViewPager(vpFansFollowGoods);
        tabLyFavGoodsCollect.setTabMode(tabLyFavGoodsCollect.MODE_FIXED);
        tabLyFavGoodsCollect2.setupWithViewPager(vpFansFollowGoods);
        tabLyFavGoodsCollect2.setTabMode(tabLyFavGoodsCollect.MODE_FIXED);
    }

    private void init(String account){
//        rlyMineLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineLoading);
        MineNetWork mineNetWork = new MineNetWork();
        if(view == null){
            return;
        }
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        Map<String,String> param = new HashMap<>();
        param.put("account",account);
//        Glide.with(getContext()).asBitmap().load("http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/b2e7-iryninw4454695.jpg")
//                .into(sivMineHead);

//        ImageLoader.getInstance().displayImage("http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/66a8-iryninw4454635.jpg",sivMineHead, ImageLoaderUtils.options);
        mineNetWork.getMineFromNet(param, new Observer<MineBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getContext(),"onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(getContext(),"22"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(MineBean mineBean) {
//                Toast.makeText(getContext(),"onCompleted"+mineBean.getMsg(),Toast.LENGTH_SHORT).show();
                if(mineBean.getIssuccess().equals("0")||mineBean.getIssuccess().equals("1")){
                    xcCacheManager.writeCache(xcCacheSaveName.account,"");
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                if(mineBean.getNick() != null){
                    tvMineNick.setText(mineBean.getNick());
                    xcCacheManager.writeCache(xcCacheSaveName.userName,mineBean.getNick());
                }else {
                    xcCacheManager.writeCache(xcCacheSaveName.userName,"");
                }
                    tvMineAccount.setText(xcCacheManager.readCache(xcCacheSaveName.guid));
                xcCacheManager.writeCache(xcCacheSaveName.userTel, mineBean.getTel());
//                Log.d("isauth11",mineBean.getIsauth()+"");
                xcCacheManager.writeCache(xcCacheSaveName.userAuth, mineBean.getIsauth()+"");
                String role = mineBean.getRole();


                xcCacheManager.writeCache(xcCacheSaveName.role, mineBean.getRole()+"");
                xcCacheManager.writeCache(xcCacheSaveName.realName, mineBean.getName()+"");
                xcCacheManager.writeCache(xcCacheSaveName.identifyPass, mineBean.getIdentifypass()+"");
                if(mineBean.getBg() != null) {
                    xcCacheManager.writeCache(xcCacheSaveName.userBgUrl, mineBean.getBg());

                }else {
                    xcCacheManager.writeCache(xcCacheSaveName.userBgUrl, "");
                }
//                Log.d("minehead111",mineBean.getHead()+"");
                if(mineBean.getHead() != null && !mineBean.getHead().isEmpty()){
                    Glide.with(getContext())
                            .load(mineBean.getHead())
//                            .load("http://47.114.155.83/18767775244/head/headbg20200724094831725072250.jpg")
                            .apply(new RequestOptions()
                                    .fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead)
                                    .circleCrop()
                            )
                            // 重点在这行
                            .into(sivMineHead);
//                    ImageLoader.getInstance().displayImage(mineBean.getHead(), sivMineHead, ImageLoaderUtils.options);
//                    ViewGroup.LayoutParams layoutParams = sivMineHead.getLayoutParams();
//                    layoutParams.height = 680;
//                    layoutParams.width = 600;
////                    Toast.makeText(getContext(),"layoutParams.height:"+layoutParams.height,Toast.LENGTH_LONG).show();
//                    sivMineHead.setLayoutParams(layoutParams);
                    xcCacheManager.writeCache(xcCacheSaveName.userHeadImgUrl,mineBean.getHead());
                }else {
                    xcCacheManager.writeCache(xcCacheSaveName.userHeadImgUrl,"");
//                    xcCacheManager.writeCache(xcCacheSaveName.userHeadImgUrl,"");
                }
                if(mineBean.getPersonnalnote() != null){
                    tvMinePersonnalNote.setText(mineBean.getPersonnalnote());
                    xcCacheManager.writeCache(xcCacheSaveName.personnalNote,mineBean.getPersonnalnote());
                }else {
                    xcCacheManager.writeCache(xcCacheSaveName.personnalNote,"");
                }
                tvMineGetGoodNum.setText(mineBean.getPraise());
                tvMineGuanZhuNum.setText(mineBean.getGuanzhunum());
                tvMineFansNum.setText(mineBean.getFansnum());
                tvMineFootMarkNum.setText(mineBean.getFootmark());

                ImageLoader.getInstance().displayImage(mineBean.getBg(),ivMineBg,ImageLoaderUtils.options);
//                rlyMineShopOpen.setVisibility(View.VISIBLE);
//                rlyMineMyShop.setVisibility(View.GONE);
                if(mineBean.getShopstatus() == 0){
                    rlyMineShopOpen.setVisibility(View.VISIBLE);
                    rlyMineMyShop.setVisibility(View.GONE);
                }else {
                    rlyMineMyShop.setVisibility(View.VISIBLE);
                    rlyMineShopOpen.setVisibility(View.GONE);
                }
                initTab(mineBean.getDongtai(),mineBean.getCollect(),account);
                initData();
            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();
        if(isResume) {
//            initData();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            init(account);
            isResume = false;
        }


    }
    private void initData(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
        String nick = xcCacheManager.readCache(xcCacheSaveName.userName);
        String head = xcCacheManager.readCache(xcCacheSaveName.userHeadImgUrl);
        String bg = xcCacheManager.readCache(xcCacheSaveName.userBgUrl);
        String personnalnote = xcCacheManager.readCache(xcCacheSaveName.personnalNote);

        ImageLoader.getInstance().displayImage(bg,ivMineBg,ImageLoaderUtils.options);
        if(head != null && !head.isEmpty()) {
            Glide.with(getContext())
                    .load(head)
                    .apply(new RequestOptions()
                            .fallback(R.mipmap.imghead)
                            .error(R.mipmap.imghead)
                            .circleCrop()
                    )
                    // 重点在这行
                    .into(sivMineHead);
        }
//        ImageLoader.getInstance().displayImage(head,sivMineHead,ImageLoaderUtils.options);
        tvMineNick.setText(nick);
        tvMinePersonnalNote.setText(personnalnote);
        rlyMineLoading.setVisibility(View.GONE);
    }


    public void onDestroyView() {
        super.onDestroyView();
        vpFansFollowGoods = null;
        tabLyFavGoodsCollect = null;
        view = null;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (null != tabLyFavGoodsCollect && null != tabLyFavGoodsCollect2 && null != rlyMineTitle && null != statusView) {
            int distance = 2*tabLyFavGoodsCollect.getTop()  - statusView.getHeight();
            float ratio = v.getScaleY() * 1f / distance;
            if (distance <= v.getScrollY()) {
                ratio = 1;
                if (tabLyFavGoodsCollect2.getVisibility() != View.VISIBLE) {
                    tabLyFavGoodsCollect2.setVisibility(View.VISIBLE);
                    rlyMineTitle.setVisibility(View.GONE);

                    statusView.setBackgroundColor(colorPrimary);
                }
                ssvMine.setNeedScroll(false);
            } else {
                if (tabLyFavGoodsCollect2.getVisibility() == View.VISIBLE) {
                    tabLyFavGoodsCollect2.setVisibility(View.INVISIBLE);
                    statusView.setBackgroundColor(Color.TRANSPARENT);

                    rlyMineTitle.setVisibility(View.VISIBLE);
                }
                ssvMine.setNeedScroll(true);
            }
            if (null == evaluator) {
                evaluator = new ArgbEvaluator();
            }

//            rlyMineTitle.setBackgroundColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, colorPrimary));
//            rlyMineTitle.setTitleColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, Color.WHITE));
        }
    }
}
