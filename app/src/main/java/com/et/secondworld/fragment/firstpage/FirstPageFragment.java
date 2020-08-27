package com.et.secondworld.fragment.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.shanhuxinxi.ShanHuXinXiActivity;
import com.et.secondworld.widget.dialog.MainDialog;


import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstPageFragment extends BaseFragment   {



    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
//    public class SDKReceiver extends BroadcastReceiver {
//
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (TextUtils.isEmpty(action)) {
//                return;
//            }
//            TextView text = (TextView) view.findViewById(R.id.tv_textinfo);
//            text.setVisibility(View.VISIBLE);
//            text.setTextColor(Color.RED);
//            if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
//                text.setText("key 验证出错! 错误码 :" + intent.getIntExtra
//                        (SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)
//                        + " ; 请在 AndroidManifest.xml 文件中检查 key 设置");
//            } else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
//                text.setText("key 验证成功! 功能可以正常使用");
//                text.setTextColor(Color.GREEN);
//            } else if (action.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
//                text.setText("网络出错");
//            }
//        }
//    }

    @BindView(R.id.mv_first_page)
    MapView mvFirstPage;
    View view;
    BaiduMap mBaiduMap;

//    private SDKReceiver mReceiver;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData,locData1;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    MainDialog mainDialog;
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_first_page, container, false);

        return view;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this, view);
        // 注册 SDK 广播监听者
//        IntentFilter iFilter = new IntentFilter();
//        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
//        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//        mReceiver = new SDKReceiver();
//        view.getContext().registerReceiver(mReceiver, iFilter);
        initBaiDuMap();
        indexDialog();
    }
    private void indexDialog() {
         mainDialog = new MainDialog(view.getContext()).Build.build(view.getContext(),getActivity());
        showDialog();
    }
    public void showDialog() {
        if (mainDialog != null && !mainDialog.isShowing())
            mainDialog.show();
    }

    public void dissmissDialog() {
        if (mainDialog != null && mainDialog.isShowing())
            mainDialog.dismiss();
    }


    private void initBaiDuMap() {
        mBaiduMap = mvFirstPage.getMap();
//        隐藏百度LOGO
        View child = mvFirstPage.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
//        隐藏百度LOGO
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化
        mLocClient = new LocationClient(view.getContext());
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
        mLocClient.start();



    }



    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mvFirstPage == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                addOvly(location.getLatitude()+0.0001,location.getLongitude());

            }



        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }

    private void addOvly(double lat,double lon){

        //定义Maker坐标点
        LatLng point = new LatLng(lat, lon);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_st);
        if(bitmap == null){
            return;
        }
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .title("黄石");


        //文字覆盖物位置坐标
        LatLng llText = new LatLng(lat, lon);

//构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("某某某村") //文字内容
                .bgColor(0xAAFFFF00) //背景色
                .fontSize(24) //字号
                .fontColor(0xFFFF00FF) //文字颜色
                .position(llText);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        mBaiduMap.addOverlay(mTextOptions);
        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
            /**
             * 地图 Marker 覆盖物点击事件监听函数
             * @param marker 被点击的 marker
             */
            @Override
            public boolean onMarkerClick(Marker marker) {
                String pos=  marker.getTitle();
//                Toast.makeText(getContext(),pos+"",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ShanHuXinXiActivity.class);
                view.getContext().startActivity(intent);
                return false;//是否捕获点击事件
            }
        };

// 设置地图 Marker 覆盖物点击事件监听者,自3.4.0版本起可设置多个监听对象，停止监听时调用removeMarkerClickListener移除监听对象
        mBaiduMap.setOnMarkerClickListener(listener);
    }


    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mvFirstPage.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mvFirstPage.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mvFirstPage.onDestroy();

    }
}
