package com.et.secondworld;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.ImmersionBar.SystemBarTintManager;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.MinePraiseDialog;
import com.et.secondworld.widget.dialog.MineShopTimesPayDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.et.secondworld.fragment.find.FindFragment;
import com.et.secondworld.fragment.firstpage.FirstFragment;
import com.et.secondworld.fragment.forum.ForumFragment;
import com.et.secondworld.fragment.message.MessageFragment;
import com.et.secondworld.fragment.mine.MineFragment;
//import com.zhyan.secondworld.huanxin.ui.DemoHelper;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
//import com.tencent.tencentmap.mapsdk.maps.MapView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MainActivity extends AppCompatActivity implements LocationSource, TencentLocationListener {

    FragmentManager manager;
//    private FirstPageFragment firstPageFragment;
    private  FirstFragment firstFragment  ;
    private  MineFragment mineFragment =null;
//    private MessageFragment messageFragment;
    private   ForumFragment forumFragment ;
//    private ForumOneFragment forumOneFragment;
//    private ForumTwoFragment forumTwoFragment;
    private  FindFragment findFragment ;
    private   MessageFragment messageFragment;

//    @BindView(R.id.rly_main_topbar)
//    RelativeLayout rlyMainTopBar;
//    @BindView(R.id.mv_main)
//    MapView mvMain;
  /*  @BindView(R.id.mv_main)
    MapView mvMain;*/
    @BindView(R.id.rb_main_bottom_firstpage)
    RadioButton rbMainBottomFirstPage;
    @OnClick(R.id.rb_main_bottom_firstpage)
    public void rbMainBottomFirstPageOnclick(){
//        rlyMainTopBar.setVisibility(View.VISIBLE);
        if(forumFragment != null && forumFragment.getMarkGone().getVisibility() ==View.VISIBLE){
            forumFragment.getMarkGone().setVisibility(View.GONE);
            rbMainBottomForum.setChecked(true);
        }else {
            getFragment("first");
        }
    }
    @BindView(R.id.rb_main_bottom_forum)
    RadioButton rbMainBottomForum;
    @OnClick(R.id.rb_main_bottom_forum)
    public void rbMainBottomForumOnclick(){
//        rlyMainTopBar.setVisibility(View.VISIBLE);
        if(forumFragment  != null &&forumFragment.getMarkGone().getVisibility() ==View.VISIBLE){
            forumFragment.getMarkGone().setVisibility(View.GONE);
            rbMainBottomForum.setChecked(true);
        }else {
            getFragment("forum");
        }
    }
    @BindView(R.id.rb_main_bottom_find)
    RadioButton rbMainBottomFind;
    Map<Object,Object> provinceMap = new HashMap<>();
    @OnClick(R.id.rb_main_bottom_find)
    public void rbMainBottomFindOnclick(){
//        rlyMainTopBar.setVisibility(View.VISIBLE);
        if(forumFragment  != null &&forumFragment.getMarkGone().getVisibility() ==View.VISIBLE){
            forumFragment.getMarkGone().setVisibility(View.GONE);
            rbMainBottomForum.setChecked(true);
        }else {
            getFragment("find");
        }
        /*SelectCityDialog selectCityPopup = new SelectCityDialog(this).Build.setCallBackListener(new SelectCityDialog.OnFinishClickListener() {
            @Override
            public void getMaps(Map<Object, Object> map) {
//                maps = map;
            }
        }).setDataCallBackListener(new SelectCityDialog.OnDataListClickListener() {
            @Override
            public void getData(ArrayList<JsonBean> dataList) {
//                dataList1.addAll(dataList);
            }
        }).build(this,provinceMap);
        selectCityPopup.show();*/

//        SelectAreaDialog selectAreaDialog = new SelectAreaDialog(this).Build.setCallBackListener(new SelectAreaDialog.OnFinishClickListener() {
//            @Override
//            public void getMaps(Map<Object, Object> map) {
////                maps = map;
//            }
//        }).build(this,provinceMap);
//        selectAreaDialog.show();
//        ShareDialog shareDialog = new ShareDialog(this).Build.build(this);
//        shareDialog.show();
    }
    @BindView(R.id.rb_main_bottom_message)
    RadioButton rbMainBottomMessage;
    @OnClick(R.id.rb_main_bottom_message)
    public void rbMainBottomMessageOnclick(){
//        rlyMainTopBar.setVisibility(View.VISIBLE);
        if(forumFragment  != null &&forumFragment.getMarkGone().getVisibility() ==View.VISIBLE){
            forumFragment.getMarkGone().setVisibility(View.GONE);
            rbMainBottomForum.setChecked(true);
        }else {
            getFragment("message");
        }

    }
    @BindView(R.id.rb_main_bottom_mine)
    RadioButton rbMainBottomMine;
    @OnClick(R.id.rb_main_bottom_mine)
    public void setRbMainBottomMineOnclick(){
//        Toast.makeText(this,"mine",Toast.LENGTH_SHORT).show();
//        rlyMainTopBar.setVisibility(View.GONE);
        if(forumFragment  != null &&forumFragment.getMarkGone().getVisibility() ==View.VISIBLE){
            forumFragment.getMarkGone().setVisibility(View.GONE);
            rbMainBottomForum.setChecked(true);
        }else {
            getFragment("mine");
        }

    }
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    @BindView(R.id.p_main_content)
    FrameLayout pMainContent;

    private void checkLogin(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
        String uuuid = UniversalID.getUniversalID(this);
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("tel",tel);
        map.put("uuuid",uuuid.trim());
//        Log.d("zzzaaa11",uuuid);
//        Log.d("zzzaaa11",tel);
        registerLoginNetWork.checkLoginFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
//                Log.d("zzzaaa11",baseBean.getMsg());
//                Log.d("zzzaaa11",baseBean.getIssuccess());
                if(baseBean.getIssuccess().equals("1")){

                    MessageQueryDialog queryCancelDialog = new MessageQueryDialog(MainActivity.this).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
                        @Override
                        public void isQuery(boolean isQuery) {
                            xcCacheManager.writeCache(xcCacheSaveName.account,"");
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    }).build(MainActivity.this,"您的账号("+baseBean.getMsg()+")在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
                    queryCancelDialog.show();


                }
            }
        });
    }

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private TencentLocationManager locationManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
//        MineShopTimesPayDialog praiseDialog = new MineShopTimesPayDialog(this).Build.build(this);
//        praiseDialog.show();

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String role = xcCacheManager.readCache(xcCacheSaveName.role);

        if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }

        initFragmentManager();
//        initMapView();
        initRadionButtonDrawable();
        huanLogin();
        initStatusBar("0");
       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            //同意就拨打

        }*/
        tencentLoc();
        localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
//        tencentLoc();
    }

    private void tencentLoc(){
//        locationManager = TencentLocationManager.getInstance(this);
//        locationRequest = TencentLocationRequest.create()
//                .setInterval(1000*3) // 定位周期 (毫秒)
//                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI) ;// 定位要求水准
//                ; // 是否使用缓存
        locationManager= TencentLocationManager.getInstance(this);
        int error = TencentLocationManager.getInstance(this)
                .requestLocationUpdates(
                        TencentLocationRequest
                                .create().setInterval(5000)
                                .setRequestLevel(
                                        TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA), this);

        if (error == 0) {

            Log.e("监听状态:", "监听成功!");

        } else if (error == 1) {

            Log.e("监听状态:", "设备缺少使用腾讯定位SDK需要的基本条件");

        } else if (error == 2) {

            Log.e("监听状态:", "配置的 key 不正确");

        }
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        String province = tencentLocation.getProvince();
        String city = tencentLocation.getCity();
        String addr = tencentLocation.getAddress();
        locationManager.removeUpdates(this);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        Log.d("city11",city);
        xcCacheManager.writeCache(xcCacheSaveName.currentProvince,province);
        xcCacheManager.writeCache(xcCacheSaveName.currentCity,city);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    public void onPause() {
        super.onPause();
        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mineFragment.onActivityResult(requestCode, resultCode, data);
    }


    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            getFragment("first");
//            getFragment("forum");
//            manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            hideFragment(transaction,manager);
            if(forumFragment !=null){
                transaction.show(forumFragment);
            }else {

                forumFragment = new ForumFragment();
                transaction.add(R.id.p_main_content, forumFragment, "forum");
            }
//            forumFragment = new ForumFragment();
//            transaction.replace(R.id.p_main_content,forumFragment);
            rbMainBottomForum.setChecked(true);
            transaction.commitAllowingStateLoss();
//            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFragmentManager(){

//        getFragment("first");

        FragmentTransaction transaction = manager.beginTransaction();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null || account.isEmpty() || account == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }


      /*  if(isFirst) {
            initFragment(transaction);
            isFirst = false;
        }*/

        getFragment("forum");
        rbMainBottomForum.setChecked(true);
//        forumFragment = new ForumFragment();
//
//        rbMainBottomForum.setChecked(true);
////        transaction.add(R.id.p_main_content, firstFragment, "first");
////        transaction.add(R.id.p_main_content, forumFragment, "forum");
////        transaction.add(R.id.p_main_content, findFragment, "find");
////        transaction.add(R.id.p_main_content, messageFragment, "message");
////        transaction.add(R.id.p_main_content, mineFragment, "mine");
//        transaction.add(R.id.p_main_content, forumFragment, "forum");
//        transaction.commit();
    }
    private void isLogin(){

    }

    /*沉浸式状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBar(String type){

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
    /**
     * 以下的几个方法用来，让fragment能够监听touch事件
     */
   /* private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }*/
    @Override
    protected void onDestroy() {

        super.onDestroy();
        EMClient.getInstance().logout(true);
        if(localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }
    private void hideFragment(FragmentTransaction transaction,FragmentManager manager){
//        FirstFragment firstFragment =(FirstFragment) manager.findFragmentByTag("first");
//        ForumFragment forumFragment =(ForumFragment) manager.findFragmentByTag("forum");
//        FindFragment findFragment =(FindFragment) manager.findFragmentByTag("find");
//        MessageFragment messageFragment =(MessageFragment) manager.findFragmentByTag("message");
//        MineFragment mineFragment =(MineFragment) manager.findFragmentByTag("mine");
        if(firstFragment != null){
            transaction.hide(firstFragment);
        }
//        transaction.hide(firstFragment);
        if(forumFragment != null){
            transaction.hide(forumFragment);
        }
//        transaction.hide(forumFragment);
        if(findFragment != null){
            transaction.hide(findFragment);
        }
//        transaction.hide(findFragment);
        if(messageFragment != null){
            transaction.hide(messageFragment);
        }
//        transaction.hide(messageFragment);
        if(mineFragment != null){
            transaction.hide(mineFragment);
        }
//        transaction.hide(mineFragment);
//        transaction.commit();
    }
    private void getFragment(String type){
//        manager = getSupportFragmentManager();
        checkLogin();
        FragmentTransaction transaction = manager.beginTransaction();
//        hideFragment(transaction,manager);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String role = xcCacheManager.readCache(xcCacheSaveName.role);

        if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }


      /*  if(isFirst) {
            initFragment(transaction);
            isFirst = false;
        }*/
        hideFragment(transaction,manager);
        // 动态增加Fragment
        switch (type){
            case "first":
//                FirstFragment firstFragment = (FirstFragment) manager.findFragmentByTag("first");
                if(firstFragment !=null){
                    transaction.show(firstFragment);
                }else {

                    firstFragment =  FirstFragment.newInstance();
                    transaction.add(R.id.p_main_content, firstFragment, "first");
                }
               /* firstFragment = new FirstFragment();
                transaction.replace(R.id.p_main_content,firstFragment);*/
                break;
            case "forum":
//                ForumFragment forumFragment =(ForumFragment) manager.findFragmentByTag("forum");
                if(forumFragment !=null){
                    transaction.show(forumFragment);
                }else {

                    forumFragment =  ForumFragment.newInstance();
//                transaction.add(R.id.p_main_content, firstFragment, "first");
                    transaction.add(R.id.p_main_content, forumFragment, "forum");
                }
//                forumFragment = new ForumFragment();
//                transaction.replace(R.id.p_main_content,forumFragment);
                break;
            case "find":
//                FindFragment findFragment =(FindFragment) manager.findFragmentByTag("find");
                if(findFragment !=null){
                    transaction.show(findFragment);
                }else {

                    findFragment =  FindFragment.newInstance();

//                transaction.add(R.id.p_main_content, firstFragment, "first");
//                transaction.add(R.id.p_main_content, forumFragment, "forum");
                    transaction.add(R.id.p_main_content, findFragment, "find");
                }
//                findFragment = new FindFragment();
//                transaction.replace(R.id.p_main_content,findFragment);
                break;
            case "message":
//                 messageFragment =(MessageFragment) manager.findFragmentByTag("message");
                if(messageFragment != null){

                    transaction.show(messageFragment);
                }else {
                    messageFragment =  MessageFragment.newInstance();

//                transaction.add(R.id.p_main_content, firstFragment, "first");
//                transaction.add(R.id.p_main_content, forumFragment, "forum");
//                transaction.add(R.id.p_main_content, findFragment, "find");
                    transaction.add(R.id.p_main_content, messageFragment, "message");
                }
//                messageFragment = new MessageFragment();
//                transaction.replace(R.id.p_main_content,messageFragment);
                break;


            case "mine":
//                MineFragment mineFragment =(MineFragment) manager.findFragmentByTag("mine");
                if(mineFragment != null){
//                    mineFragment =(MineFragment) manager.findFragmentByTag("mine");
                    transaction.show(mineFragment);
                }else {
                    mineFragment =  MineFragment.newInstance();

//                transaction.add(R.id.p_main_content, firstFragment, "first");
//                transaction.add(R.id.p_main_content, forumFragment, "forum");
//                transaction.add(R.id.p_main_content, findFragment, "find");
//                transaction.add(R.id.p_main_content, messageFragment, "message");
                    transaction.add(R.id.p_main_content, mineFragment, "mine");
                }
//                mineFragment = new MineFragment();
//                transaction.replace(R.id.p_main_content,mineFragment);
                break;

        }
        transaction.commit();

    }


    private void initMapView(){
        //        隐藏百度LOGO
//        View child = mvMain.getChildAt(1);
//        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
//            child.setVisibility(View.INVISIBLE);
//        }
////        隐藏百度LOGO
//        //地图上比例尺
//        mvMain.showScaleControl(false);
//// 隐藏缩放控件
//        mvMain.showZoomControls(false);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        option.setNeedNewVersionRgc(true);
        mLocClient.setLocOption(option);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            //同意就拨打
            mLocClient.start();
        }

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null ) {
                return;
            }
            String province = location.getProvince();
            String city = location.getCity();
            String country = location.getCountry();
            String district = location.getDistrict();
            String locationDescribe = location.getLocationDescribe();
            Log.d("vvvv11",country+"  ,"+district+"  ,"+locationDescribe);
//            Toast.makeText(getBaseContext(),area,Toast.LENGTH_LONG).show();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            xcCacheManager.writeCache(xcCacheSaveName.currentProvince,province);
            xcCacheManager.writeCache(xcCacheSaveName.currentCity,city);
//            Log.d("province11",province);
            mLocClient.stop();
        }
    }
    private void initRadionButtonDrawable(){
        RadioButton[] rbs = new RadioButton[5];
        rbs[0] =rbMainBottomFirstPage;
        rbs[1] = rbMainBottomForum;
        rbs[2] = rbMainBottomFind;
        rbs[3] = rbMainBottomMessage;
        rbs[4] = rbMainBottomMine;
        for (RadioButton rb : rbs) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth()*6/7, drawables[1].getMinimumHeight()*6/7);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给指定的radiobutton设置drawable边界
//            if (rb.getId() == R.id.rb_more) {
//                r = new Rect(0, 0, drawables[1].getMinimumWidth(), drawables[1].getMinimumHeight());
//                drawables[1].setBounds(r);
//            }
            //添加限制给控件
            rb.setCompoundDrawables(null,drawables[1],null,null);
        }
    }
    private void huanLogin(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null || account.isEmpty()){
            return;
        }
        EMClient.getInstance().login(account,"11",new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
//                       boolean isSuccess = DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName("zz");
//                        Log.d("main111", "登录聊天服务器成功！"+isSuccess);
//                        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar("http://192.168.0.8/article/articleimg02020052512462559CCE34E-9F07-4E73-8FB7-8E835EBE80B3.jpg");


                    }
                }).start();
//                DemoHelper.getInstance().setCurrentUserName(com.ylean.redshellapp.utils.Constant.userInfo.getUserId() + ""); // 环信Id

//                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//                Toast.makeText(getApplicationContext(),"登录聊天服务器成功this is list:"+conversations.size(),Toast.LENGTH_LONG).show();
                Log.d("main111", "登录聊天服务器成功！");
//                Toast.makeText(getContext(),"登录成功",Toast.LENGTH_LONG).show();
                /*try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.d("zzzzzzzz", "好友数！"+usernames.size()+usernames.get(0));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

/*
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
            // notify new message
            for (EMMessage message: messages) {
                DemoHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
//            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
//            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
//            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
//            Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
        }
    };*/

    /*private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
//                updateUnreadLabel();
//                if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (messageFragment.chatMessageFragment != null) {
                        messageFragment.chatMessageFragment.refresh();
                    }
//                }
            }
        });
    }*/

    @Override
    protected void onResume(){
        super.onResume();
        checkLogin();
//        MessageQueryDialog queryCancelDialog = new MessageQueryDialog(MainActivity.this).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
//            @Override
//            public void isQuery(boolean isQuery) {
//
//            }
//        }).build(MainActivity.this,"您的账号在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
//        queryCancelDialog.show();
//        Toast.makeText(getBaseContext(),"this is new message",Toast.LENGTH_SHORT).show();
//        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }



    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
