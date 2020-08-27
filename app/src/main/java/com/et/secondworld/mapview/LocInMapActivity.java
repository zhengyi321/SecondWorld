package com.et.secondworld.mapview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.SearchVillageShopActivity;
import com.et.secondworld.bean.GetMapArticleListBean;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;

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
 * @Date 2020/6/22
 **/
public class LocInMapActivity extends MapActivity implements TencentLocationListener, LocationSource, View.OnTouchListener {

    @BindView(R.id.mv_tencent_mapview)
    MapView mvTencentMapView;
    @BindView(R.id.tv_tencent_mapview_baitang)
    TextView tvTencentMapViewBaiTang;
    @BindView(R.id.rly_tencent_mapview_baitang)
    RelativeLayout rlyTencentMapViewBaiTang;

    private final int MAP_POST_ARTICLE = 0x002;
    @OnClick(R.id.rly_tencent_mapview_post_event)
    public void rlyTencentMapViewPostEventOnclick(){
        if(latLng1 != null){
            Intent intent = new Intent(this, MapPostNormalActivity.class);
            intent.putExtra("lat",latLng1.latitude+"");
            intent.putExtra("lon",latLng1.longitude+"");
            startActivityForResult(intent,MAP_POST_ARTICLE);
        }
    }
    @OnClick(R.id.rly_tencent_mapview_baitang_event)
    public void rlyTencentMapViewBaiTangEventOnclick(){
//        BaiTangDialog praiseDialog = new BaiTangDialog(TecentMapViewActivity.this).Build.build(TecentMapViewActivity.this);
//        praiseDialog.show();
        if(latLng1 != null) {
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latLng1, //中心点坐标，地图目标经纬度
                            16,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.moveCamera(cameraSigma);
        }
        rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
//        rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
    }
    private LocationSource.OnLocationChangedListener locationChangedListener;
    TencentMap tencentMap;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private MyLocationStyle locationStyle;
    private final int SEARCH_FOR_RESULT = 0x01;
    private Marker myLocation;
    private Circle accuracy;
    private String province = "";
    private String city = "";
    private String district ="";
    private String town ="";
    private float x =0,y = 0;
    /*@OnClick(R.id.mv_tencent_mapview)
    public void rlyTencentMapViewOnclick(){
        Log.d("xxxx11",x+" "+y);
    }*/
    @BindView(R.id.iv_tencent_mapview_search)
    ImageView ivTencentMapViewSearch;
    LatLng latLng1 = null;
    boolean flag = true;
    Marker marker = null;
    @OnClick(R.id.iv_tencent_mapview_baitang_close)
    public void ivTencentMapViewBaiTangCloseOnclick(){
        rlyTencentMapViewBaiTang.setVisibility(View.GONE);
    }
    /* @BindView(R.id.lly_tencent_mapview_search)
     LinearLayout llyTenCentMapViewSearch;*/
    @OnClick(R.id.iv_tencent_mapview_search)
    public void ivTencentMapViewSearchOnclick(){
        Intent intent = new Intent(this, SearchVillageShopActivity.class);
        intent.putExtra("province",province);
        intent.putExtra("city",city);
        intent.putExtra("district",district);
        intent.putExtra("town",town);
        startActivityForResult(intent,SEARCH_FOR_RESULT);
    }
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_tecent_mapview);
        ButterKnife.bind(this);
        /*TencentMap tencentMap = mvTencentMapView.getMap();
        tencentMap.getUiSettings().setLogoPosition(
                //logo 放到左下角
                TencentMapOptions.LOGO_POSITION_BOTTOM_LEFT,
                //logo 距离 mapView 底边 50 像素，左边 100 像素
                new int[]{50, 100});*/
//        MapView mapView = new MapView(this);
//        this.parentView.addView(mapView);
//        mvTencentMapView.onCreate(bundle);
        //定位需要申请的权限

        tencentMap = mvTencentMapView.getMap();
        //建立定位
        getMapArticle();
        initLocation();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.BLUETOOTH},1);
//        }else {
//            //同意就拨打
//            bindListener();
//        }
        bindListener();
    }


    private void getMapArticle(){
        Map<String,Object> map = new HashMap<>();
        ArticleNetWork articleNetWork = new ArticleNetWork();

        articleNetWork.getMapArticleFromNet(map, new Observer<GetMapArticleListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetMapArticleListBean getMapArticleListBean) {
                List<GetMapArticleListBean.ListBean> list = getMapArticleListBean.getList();
                Log.d("result11",getMapArticleListBean.getMsg());
                tencentMap.clearAllOverlays();
                for(GetMapArticleListBean.ListBean item :list){
                    String lat = item.getLat();
                    String lon = item.getLon();
                    String title = item.getTitle();
                    String articleid = item.getArticleid();
                    String accountid = item.getAccountid();
//                Log.d("result11",lat);
//                Log.d("result11",lon);
                    LatLng latLng = null;
                    if(lat != null && lon != null){
                        latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    }
                    Marker  marker = tencentMap.addMarker(new MarkerOptions().
                                    position(latLng).
                                    title(title)
//                            snippet()
                                    .tag(accountid+","+articleid)
                    );
//创建图标
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.forumad)));//标注是有默认图标，这句代码是修改图标
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SEARCH_FOR_RESULT) {
                String lat = data.getExtras().getString("lat");
                String lon = data.getExtras().getString("lon");
//                Log.d("result11",lat);
//                Log.d("result11",lon);
                LatLng latLng = null;
                if(lat != null && lon != null){
                    latLng = new LatLng(Double.parseDouble(lon), Double.parseDouble(lat));
                }
                Location location = new Location("");

//            myLocation = tencentMap.addMarker(new MarkerOptions().
//                    position(latLng).draggable(true)
//                    .icon(com.tencent.mapsdk.raster.model.BitmapDescriptorFactory.fromResource(R.mipmap.navigation)).
//                    anchor(0.5f, 0.5f))
//                    ;
//            tencentMap.animateTo(latLng/*, 4000, callback*/);
//            tencentMap.setZoom(16);
//            accuracy = tencentMap.addCircle(new CircleOptions().
//                    center(latLng).
//                    radius((double)arg0.getAccuracy()).
//                    fillColor(0x440000ff).
//                    strokeWidth(0f));
//            myLocation.setPosition(latLng);
//            myLocation.setRotation(arg0.getBearing()); //仅当定位来源于gps有效，或者使用方向传感器
//            accuracy.setCenter(latLng);
//            accuracy.setRadius(arg0.getAccuracy());
                location.setLatitude(Double.parseDouble(lon));
                location.setLongitude(Double.parseDouble(lat));
//                location.setAccuracy(arg0.getAccuracy());
                locationChangedListener.onLocationChanged(location);

//                myLocation = tencentMap.addMarker(new MarkerOptions().
//                        position(latLng).draggable(true)
//                        .icon(com.tencent.mapsdk.raster.model.BitmapDescriptorFactory.fromResource(R.mipmap.navigation)).
//                                anchor(0.5f, 0.5f))
//                ;
//                tencentMap.animateTo(latLng/*, 4000, callback*/);
//                mTvShow.setText(data.getExtras().getString("second") + "requestCode:" + requestCode + "resultCode:" + resultCode);
            }

            if(requestCode == MAP_POST_ARTICLE){
                getMapArticle();
//                String lat = data.getExtras().getString("lat");
//                String lon = data.getExtras().getString("lon");
//                String title = data.getExtras().getString("title");
////                Log.d("result11",lat);
////                Log.d("result11",lon);
//                LatLng latLng = null;
//                if(lat != null && lon != null){
//                    latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
//                }
//              Marker  marker = tencentMap.addMarker(new MarkerOptions().
//                        position(latLng).
//                        title(title).
//                        snippet(title)
//                        .tag(1)
//                );
////创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.forumad)));//标注是有默认图标，这句代码是修改图标
            }
        }
    }
    /**
     * 定位的一些初始化设置
     */
    private void initLocation() {
        //用于访问腾讯定位服务的类, 周期性向客户端提供位置更新
        locationManager = TencentLocationManager.getInstance(this);
        //设置坐标系
        locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        //创建定位请求
        locationRequest = TencentLocationRequest.create();
        //设置定位周期（位置监听器回调周期）为3s
        locationRequest.setInterval(3000);
        bindListener();
        //地图上设置定位数据源
        tencentMap.setLocationSource(this);
        //设置当前位置可见
        tencentMap.setMyLocationEnabled(true);
        //设置定位图标样式
        setLocMarkerStyle();
        tencentMap.setMyLocationStyle(locationStyle);
        tencentMap.setOnMapLongClickListener(new TencentMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("xxxx11",x+" "+y);
                latLng1 = latLng;
//                LatLng latLng1 = new LatLng(30.588822,113.951175);
                if(marker != null){
                    marker.remove();
                }
                marker = tencentMap.addMarker(new MarkerOptions().
                        position(latLng).
                        title("").
                        snippet("")
                        .tag("0")
                );
//创建图标
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.drawable.location_icon)));//标注是有默认图标，这句代码是修改图标
//                marker.setIcon(BitmapDescriptorFactory.fromView(llyTenCentMapViewSearch));//标注是有默认图标，这句代码是修改图标

            }
        });
        tencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                Marker marker = tencentMap.
//                tencentMap.clearAllOverlays();
            }
        });
        //下面是标注支持的事件   监听标注点击事件
        TencentMap.OnMarkerClickListener listener = new TencentMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0+"被点击");
                String tag = arg0.getTag().toString();
//                String articleid = arg0.getSnippet();

                latLng1 = arg0.getPosition();
//                arg0.getPosition();
//                arg0.getTag();
                if(flag) {
                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    arg0.getPosition(), //中心点坐标，地图目标经纬度
                                    16,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
                    flag = false;
                }
                if(!tag.equals("0")){
                    String[] tagString = tag.split(",");
                    String articleAccount = tagString[0];
                    String articleid = tagString[1];
                    Intent intent = new Intent(getBaseContext(), ForumDetailTwoActivity.class);
                    intent.putExtra("articleAccount",articleAccount);
                    intent.putExtra("articleid",articleid);

//            intent.putExtra("title",title);

                    startActivity(intent);
                }
//                rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
//                BaiTangDialog praiseDialog = new BaiTangDialog(TecentMapViewActivity.this).Build.build(TecentMapViewActivity.this);
//                praiseDialog.show();
                return false;
            }

        };
        tencentMap.setOnMarkerClickListener(listener);
        tencentMap.setOnCameraChangeListener(new TencentMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
                float zoom = cameraPosition.zoom;
                if(zoom != 16){
                    flag = true;
                }
                Log.d("zoomsize11",zoom+"");
            }
        });
    }
    protected void bindListener() {
//        int error = locationManager.requestLocationUpdates(
//                locationRequest,TecentMapViewActivity.this);
        locationManager= TencentLocationManager.getInstance(this);
        int error = TencentLocationManager.getInstance(this)
                .requestLocationUpdates(
                        TencentLocationRequest
                                .create().setInterval(5000)
                                .setRequestLevel(
                                        TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA), this);

        switch (error) {
            case 0:
                Log.e("location", "成功注册监听器");
                break;
            case 1:
                Log.e("location", "设备缺少使用腾讯定位服务需要的基本条件");
                break;
            case 2:
                Log.e("location", "manifest 中配置的 key 不正确");
                break;
            case 3:
                Log.e("location", "自动加载libtencentloc.so失败");
                break;

            default:
                break;
        }
    }
    /**
     * 设置定位图标样式
     */
    private void setLocMarkerStyle() {
        locationStyle = new MyLocationStyle();
        //创建图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getBitMap(R.drawable.location_icon));
        locationStyle.icon(bitmapDescriptor);
        //设置定位圆形区域的边框宽度
//        locationStyle.strokeWidth(3);
        //设置圆区域的颜色
//        locationStyle.fillColor(R.color.yellow);
    }

    private Bitmap getBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 55;
        int newHeight = 55;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }
    /**
     * 腾讯定位SDK位置变化回调
     */
    @Override
    public void onLocationChanged(TencentLocation arg0, int i, String s) {
        if (i == TencentLocation.ERROR_OK ) {
            Location location = new Location(arg0.getProvider());
            LatLng latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
//            if (myLocation == null) {
//
//            }
//            if (accuracy == null) {
//
//            }
//            myLocation = tencentMap.addMarker(new MarkerOptions().
//                    position(latLng).draggable(true)
//                    .icon(com.tencent.mapsdk.raster.model.BitmapDescriptorFactory.fromResource(R.mipmap.navigation)).
//                    anchor(0.5f, 0.5f))
//                    ;
//            tencentMap.animateTo(latLng/*, 4000, callback*/);
//            tencentMap.setZoom(16);
//            accuracy = tencentMap.addCircle(new CircleOptions().
//                    center(latLng).
//                    radius((double)arg0.getAccuracy()).
//                    fillColor(0x440000ff).
//                    strokeWidth(0f));
//            myLocation.setPosition(latLng);
//            myLocation.setRotation(arg0.getBearing()); //仅当定位来源于gps有效，或者使用方向传感器
//            accuracy.setCenter(latLng);
//            accuracy.setRadius(arg0.getAccuracy());
            location.setLatitude(arg0.getLatitude());
            location.setLongitude(arg0.getLongitude());
//            location.setAccuracy(arg0.getAccuracy());
            locationChangedListener.onLocationChanged(location);
//            locationChangedListener.onLocationChanged(location);
            province = arg0.getProvince();
            city = arg0.getCity();
            district = arg0.getDistrict();
            town = arg0.getTown();

//            Log.e("maplocation", "location: " +arg0.getProvince()+ arg0.getCity() +arg0.getDistrict()+ " " + arg0.getProvider());
//            Location location = new Location(tencentLocation.getProvider());
//            //设置经纬度以及精度
//            location.setLatitude(tencentLocation.getLatitude());
//            location.setLongitude(tencentLocation.getLongitude());
//            location.setAccuracy(tencentLocation.getAccuracy());
//            locationChangedListener.onLocationChanged(location);
//            Log.e("当前的点坐标****", "aaaaaaaaaaaa: " + tencentLocation.getLatitude() + " " + tencentLocation.getLongitude());
//
//            //当前点
//            LatLng latLng = new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude());
//
//
//            Location location = new Location(tencentLocation.getProvider());
//            location.setLatitude(tencentLocation.getLatitude());
//            location.setLongitude(tencentLocation.getLongitude());
//            location.setAccuracy(tencentLocation.getAccuracy());
//
//
//
//            //定位到当前位置并且设置缩放级别
            tencentMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(),arg0.getLongitude())));
//
//
//
//            locationChangedListener.onLocationChanged(location);
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mvTencentMapView.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mvTencentMapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mvTencentMapView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mvTencentMapView.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mvTencentMapView.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mvTencentMapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;

        int err = locationManager.requestLocationUpdates(locationRequest, this, Looper.myLooper());
        switch (err) {
            case 1:
                Toast.makeText(this,"设备缺少使用腾讯定位服务需要的基本条件",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"manifest 中配置的 key 不正确",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this,"自动加载libtencentloc.so失败",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void deactivate() {
        locationManager.removeUpdates(this);
        locationManager = null;
        locationRequest = null;
        locationChangedListener=null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
//                tvTouchShowStart.setText("起始位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
//                tvTouchShow.setText("实时位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
//                tvTouchShow.setText("结束位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            default:
                break;
        }
        /**
         *  注意返回值
         *  true：view继续响应Touch操作；
         *  false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
         */
        return true;
    }
/*
    @Override
    protected void onDestroy() {
        mvTencentMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mvTencentMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mvTencentMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mvTencentMapView.onStop();
        super.onStop();
    }*/
}
