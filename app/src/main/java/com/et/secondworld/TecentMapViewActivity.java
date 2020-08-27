package com.et.secondworld;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.et.secondworld.adapter.CollectVillageRVAdapter;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetApplyVillageListBean;
import com.et.secondworld.bean.GetBoothListBean;
import com.et.secondworld.bean.GetCollectVillageListBean;
import com.et.secondworld.bean.GetMapArticleListBean;
import com.et.secondworld.bean.GetMapVerticalTitleListBean;
import com.et.secondworld.bean.GetStreetBean;
import com.et.secondworld.bean.MapViewTagBean;
import com.et.secondworld.bean.TecentMessageListBean;
import com.et.secondworld.bean.VillageListBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.mapview.ApplyForVillageActivity;
import com.et.secondworld.mapview.MapPostNormalActivity;
import com.et.secondworld.mine.MineAllShopActivity;
import com.et.secondworld.network.ApplyNetWork;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.network.BoothNetWork;
import com.et.secondworld.network.CollectVillageNetWork;
import com.et.secondworld.network.SearchNetWork;
import com.et.secondworld.network.SpreadNetWork;
import com.et.secondworld.network.StreetNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.BaiTangDialog;
import com.et.secondworld.widget.dialog.MapViewMessageListDialog;
import com.et.secondworld.widget.dialog.MapViewMessageListRVAdapter;
import com.et.secondworld.widget.dialog.MinePraiseDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.textview.AutoVerticalScrollTextView;
import com.et.secondworld.widget.textview.AutoVerticalScrollTextViewUtil;
import com.et.secondworld.widget.textview.RotateTextView;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.httpresponse.Poi;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapOptions;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.Animation;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;
import com.tencent.tencentmap.mapsdk.maps.model.ScaleAnimation;
import com.tencent.tencentmap.mapsdk.maps.model.TencentMapGestureListener;
import com.tencent.tencentmap.mapsdk.maps.model.TranslateAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Observer;

import static com.et.secondworld.R2.id.image;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/16
 **/
public class TecentMapViewActivity extends MapActivity implements TencentLocationListener,LocationSource ,  AutoVerticalScrollTextViewUtil.OnMyClickListener {

    @BindView(R.id.mv_tencent_mapview)
    MapView mvTencentMapView;
   /* @BindView(R.id.tv_tencent_mapview_baitang)
    TextView tvTencentMapViewBaiTang;*/
    private boolean isMessageHold = true;
    @OnClick(R.id.rly_tencent_mapview_message)
    public void ivTencentMapViewMessageOnclick(){
//        MapViewMessageListDialog praiseDialog = new MapViewMessageListDialog(this).Build.build(this);
//        praiseDialog.show();
        if(isMessageHold) {
            rvTencentMapViewMessage.setVisibility(View.VISIBLE);
            rlyTencentMapViewCollect.setVisibility(View.GONE);
            isMessageHold = false;
        }else {
            rvTencentMapViewMessage.setVisibility(View.GONE);
            isMessageHold = true;
        }
        handler.postDelayed(runnable, 0);
    }
    private long clickTime = 0;
    @OnClick(R.id.rly_tencent_mapview_applyfor)
    public void rlyTencentMapViewApplyForOnclick(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }else {
//            //同意就拨打
//
//        }
//        locOne = true;
        rvTencentMapViewMessage.setVisibility(View.GONE);
        rlyTencentMapViewCollect.setVisibility(View.GONE);
        if(latLng1 != null) {
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);

            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            for(Marker item :markerListApplyVillage) {
                MapViewTagBean tag = (MapViewTagBean) item.getTag();
                String markeraccount = tag.getArticleidaccount();
//                Log.d("markeraccount11", markeraccount);
                String lat = tag.getLat();
                String lon = tag.getLon();
//            String title = tag.getTitle();
//            String content = tag.getContent();
//            String img = tag.getImg();
                if (lat == null) {
                    continue;
                }
                LatLng latLng = null;
//               LatLng latLng  = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                try {
                    latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                }catch (Exception e){
//                    Log.d("result11lon",e+"");
                    continue;
                }
                if (markeraccount != null && markeraccount.equals(account)) {
                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    latLng, //中心点坐标，地图目标经纬度
                                    (float) 14.73,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
                    item.setInfoWindowEnable(true);
                    return;
                }
            }


            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng1, //中心点坐标，地图目标经纬度
                                (float) 14.73,  //目标缩放级别
                                0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                0)); //目标旋转角 0~360° (正北方为0)
                tencentMap.animateCamera(cameraSigma, new TencentMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(getBaseContext(), ApplyForVillageActivity.class);
                        intent.putExtra("lat", latLng1.latitude + "");
                        intent.putExtra("lon", latLng1.longitude + "");
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

            }
        }else {
            Toast.makeText(this,"请选择一个位置",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isCollecthold = true;
    @BindView(R.id.rly_tencent_mapview_baitang)
    RelativeLayout rlyTencentMapViewBaiTang;
    @OnClick(R.id.iv_tencent_mapview_collect)
    public void ivTencentMapViewCollectOnclick(){
//        initCollectVillageRV();
        rvTencentMapViewMessage.setVisibility(View.GONE);
        rlyTencentMapViewCollect.setVisibility(View.GONE);
        if(isCollecthold) {
            rlyTencentMapViewCollect.setVisibility(View.VISIBLE);
            rvTencentMapViewMessage.setVisibility(View.GONE);
            isCollecthold = false;
        }else {
            rlyTencentMapViewCollect.setVisibility(View.GONE);
            isCollecthold = true;
        }
    }
    List<GetMapVerticalTitleListBean.ListBean> listBeans;
    private final int MAP_POST_ARTICLE = 0x002;
    private final int SELECT_IMAGE_REQUEST = 0x004;
    private Map<String,String> provinceMap = new HashMap<>();
    @OnClick(R.id.rly_tencent_mapview_post_event)
    public void rlyTencentMapViewPostEventOnclick(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }else {
//            //同意就拨打
//
//        }
//        locOne = true;
        rvTencentMapViewMessage.setVisibility(View.GONE);
        rlyTencentMapViewCollect.setVisibility(View.GONE);
        if(latLng1 != null){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
                String auth = xcCacheManager.readCache(xcCacheSaveName.userAuth);
                if(auth != null && !auth.equals("1")){
                    Toast.makeText(this,"请先认证",Toast.LENGTH_LONG).show();
                    return;
                }
//                XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(role != null && role.equals("01")) {
                    Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
                    return;

                }
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                Log.d("zzzz111",markerListArticle.size()+"");
                for (Marker item : markerListArticle) {
                    MapViewTagBean tag = (MapViewTagBean) item.getTag();
                    String markeraccount = tag.getArticleidaccount();
//                    Log.d("markeraccount11", markeraccount);
                    String lat = tag.getLat();
                    String lon = tag.getLon();
//            String title = tag.getTitle();
//            String content = tag.getContent();
//            String img = tag.getImg();
                    if (lat == null) {
                        continue;
                    }
//                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    LatLng latLng = null;
                    try {
                        latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                    }catch (Exception e){
                        Log.d("result11lon",e+"");
                        continue;
                    }
                    if (markeraccount != null && markeraccount.equals(account)) {
                        CameraUpdate cameraSigma =
                                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                        latLng, //中心点坐标，地图目标经纬度
                                        (float) 14.73,  //目标缩放级别
                                        0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                        0)); //目标旋转角 0~360° (正北方为0)
//                    tencentMap.animateCamera(cameraSigma);
                        tencentMap.animateCamera(cameraSigma, new TencentMap.CancelableCallback() {
                            @Override
                            public void onFinish() {
//                            Intent intent = new Intent(getBaseContext(), ForumDetailTwoActivity.class);
//                            intent.putExtra("articleAccount",tag.getArticleidaccount());
//                            intent.putExtra("articleid",tag.getArticleid());
//
////            intent.putExtra("title",title);
//
//                            startActivity(intent);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });


                        return;
                    }
                }
//            latLng1 = orginalLng;
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng1, //中心点坐标，地图目标经纬度
                                (float) 14.73,  //目标缩放级别
                                0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                0)); //目标旋转角 0~360° (正北方为0)
                tencentMap.animateCamera(cameraSigma, new TencentMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        TencentSearch tencentSearch = new TencentSearch(getBaseContext());
                        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
                        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
                        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(latLng1).getPoi(true)
                                .setPoiOptions(new Geo2AddressParam.PoiOptions()
                                        .setRadius(1000).setCategorys("面包")
                                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_O2O));
                        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

                            @Override
                            public void onSuccess(int arg0, BaseObject arg1) {
                                if (arg1 == null) {
                                    return;
                                }
                                Geo2AddressResultObject obj = (Geo2AddressResultObject) arg1;
                                StringBuilder sb = new StringBuilder();
                                String address = obj.result.address;
//                    sb.append("逆地址解析");
//                    sb.append("\n地址：" + obj.result.address);

                                Intent intent = new Intent(getBaseContext(), MapPostNormalActivity.class);
                                intent.putExtra("lat", latLng1.latitude + "");
                                intent.putExtra("lon", latLng1.longitude + "");
                                intent.putExtra("address", address + "");
                                startActivityForResult(intent, MAP_POST_ARTICLE);
//                    sb.append("\npois:");
//                for (Poi poi : obj.result.pois) {
//                    sb.append("\n\t" + poi.title);
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(poi.latLng)  //标注的位置
//                            .title(poi.title)     //标注的InfoWindow的标题
//                            .snippet(poi.address) //标注的InfoWindow的内容
//                    );
//                }
                                Log.e("test", sb.toString());
                            }

                            @Override
                            public void onFailure(int arg0, String arg1, Throwable arg2) {
                                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                            }
                        });
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }

        }else {
//                else {
            Toast.makeText(this,"请打开定位",Toast.LENGTH_SHORT).show();
//                }
        }
    }
    @OnClick(R.id.rly_tencent_mapview_baitang_event)
    public void rlyTencentMapViewBaiTangEventOnclick(){
//        BaiTangDialog praiseDialog = new BaiTangDialog(TecentMapViewActivity.this).Build.build(TecentMapViewActivity.this);
//        praiseDialog.show();
        /*if(orginalLng != null) {
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            orginalLng, //中心点坐标，地图目标经纬度
                            16,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
        }*/
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }else {
//            //同意就拨打
//
//        }
//        locOne = true;
        rvTencentMapViewMessage.setVisibility(View.GONE);
        rlyTencentMapViewCollect.setVisibility(View.GONE);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String role = xcCacheManager.readCache(xcCacheSaveName.role);

        if(role != null && role.equals("01")) {
            Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
            return;

        }
        if(orginalLng != null) {
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            orginalLng, //中心点坐标，地图目标经纬度
                            16,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
        }else {

            Toast.makeText(this,"请打开定位",Toast.LENGTH_SHORT).show();
            return;

        }
        rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
//        latLng1 = orginalLng;
        boothEdit();
//        rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
    }
    private static LocationSource.OnLocationChangedListener locationChangedListener;
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
    @BindView(R.id.lly_tencent_mapview_vertical)
    LinearLayout llyTencentMapViewVertical;
    private boolean ishold = true;
    @OnClick(R.id.iv_tencent_mapview_close)
    public  void ivTencentMapViewVerticalCloseOnclick(){
        if(ishold) {
            avtvTencentMapView.setVisibility(View.INVISIBLE);
            llyTencentMapViewVertical.setBackgroundResource(R.color.transparent);
            ishold = false;
        }else {
            llyTencentMapViewVertical.setBackgroundResource(R.drawable.black_half_round_shape);
            avtvTencentMapView.setVisibility(View.VISIBLE);
            ishold = true;
        }
    }
    LatLng latLng1 = null;
    boolean flag = true;
     Marker markers = null;
     private int type = 0;//0自动定位 1为主动定位
    private boolean fistloc = true;
    @BindView(R.id.rly_tencent_mapview_baitang_close)
    RelativeLayout rlyTencentMapViewBaiTangClose;
    @OnClick(R.id.rly_tencent_mapview_baitang_content)
    public void rlyTencentMapViewBaiTangContentOnclick(){
        etTencentMapViewBaiTangContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) ((Activity) this).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(etTencentMapViewBaiTangContent, 0);
//            if(imm.isActive(editText)){
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private String boothid = "";
    @OnClick(R.id.rly_tencent_mapview_baitang_close)
    public void ivTencentMapViewBaiTangCloseOnclick(){
        if(isImgEdit){
            rlyTencentMapViewBaiTang.setVisibility(View.GONE);
        }else {
            QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                @Override
                public void isQuery(boolean isQuery) {
                    if (isQuery) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("boothid", boothid);
                        BoothNetWork boothNetWork = new BoothNetWork();
                        boothNetWork.deleteBoothToNet(map, new Observer<BaseBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                Toast.makeText(getBaseContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                rlyTencentMapViewBaiTang.setVisibility(View.GONE);
                                for(Marker item:markerListBooth){
                                    MapViewTagBean tag = (MapViewTagBean)item.getTag();
                                    String id = tag.getArticleid();
                                    if(id.equals(boothid)){
                                        item.remove();
                                    }
                                }
                                getMapArticle(0);
                            }
                        });
                    }
                }
            }).build(this, "是否收摊?");
            queryCancelDialog.show();
        }
    }
   /* @BindView(R.id.lly_tencent_mapview_search)
    LinearLayout llyTenCentMapViewSearch;*/
    @OnClick(R.id.rly_tencent_mapview_search)
    public void ivTencentMapViewSearchOnclick(){
        rvTencentMapViewMessage.setVisibility(View.GONE);
        rlyTencentMapViewCollect.setVisibility(View.GONE);
        if(System.currentTimeMillis() - clickTime > 2000) {
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            String lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
//            String lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
            String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
            String villagedis = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
            String villageCity = xcCacheManager.readCache(xcCacheSaveName.forumCity);
            String villageProv = xcCacheManager.readCache(xcCacheSaveName.forumProv);
            String allname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
            String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
            clickTime = System.currentTimeMillis();
            if(province == null || province.isEmpty()){
                province = villageProv;
            }
            if(city == null || city.isEmpty()){
                city = villageCity;
            }
            if(district == null || district.isEmpty()){
                district = villagedis;
            }
            if(this.town == null || this.town.isEmpty()){
                this.town = town;
            }
            Intent intent = new Intent(this, SearchVillageShopActivity.class);
            intent.putExtra("province", province);
            intent.putExtra("city", city);
            intent.putExtra("district", district);
            intent.putExtra("town", this.town);
            startActivityForResult(intent, SEARCH_FOR_RESULT);
        }
//        marker.isClickable();
    }
    @BindView(R.id.avtv_tencent_mapview)
    AutoVerticalScrollTextView avtvTencentMapView;
    @BindView(R.id.iv_tencent_mapview_loc)
    ImageView ivTencentMapViewLoc;
    @OnClick(R.id.rly_tencent_mapview_back)
    public void rlyTencentMapViewBackOnclick(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String forumVillage = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        if(forumVillage == null || forumVillage.isEmpty()){
            return;
        }
        this.finish();
    }
    @OnClick(R.id.iv_tencent_mapview_loc)
    public void ivTencentMapViewLocOnclick(){
        if(type == 1){
            finish();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            //同意就拨打

        }
        if(orginalLng != null) {
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            orginalLng, //中心点坐标，地图目标经纬度
                            14,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
        }else {
//            isFirstloc = true;
//            locOne = true;
//            initLocation();
//            bindListener();

//            tencentMap = mvTencentMapView.getMap();
//            tencentMap.setMyLocationEnabled(true);
        }
        isFirstloc = true;
        locOne = true;
        initLocation();
        bindListener();
    }
    private LatLng orginalLng;
    private ArrayList<CharSequence> list;
    private AutoVerticalScrollTextViewUtil aUtil;
    private String addr = "";
    private String lat = "";
    private List<Marker> markerListBooth = new ArrayList<>();
    private List<Marker> markerListVillage = new ArrayList<>();
    private List<Marker> markerListArticle = new ArrayList<>();
    private List<Marker> markerListStreet = new ArrayList<>();
    private List<Marker> markerListApplyVillage = new ArrayList<>();
    @BindView(R.id.iv_tencent_mapview_baitang)
    ImageView ivTencentMapViewBaiTang;
    @BindView(R.id.tv_tencent_mapview_apply)
    RotateTextView rtvTencentMapViewApply;
    @BindView(R.id.tv_tencent_mapview_back)
    RotateTextView rtvTencentMapViewBack;
    @BindView(R.id.tv_tencent_mapview_baitang)
    RotateTextView rtvTencentMapViewBaiTang;
    @BindView(R.id.tv_tencent_mapview_shijian)
    RotateTextView rtvTencentMapViewShiJian;
    @OnClick(R.id.iv_tencent_mapview_baitang)
    public void ivTencentMapViewBaiTangOnclick(){
        if(isImgEdit) {
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                takePhoto();
            }
        }
    }
    @BindView(R.id.tv_tencent_mapview_baitang_title)
    TextView tvTencentMapViewBaiTangTitle;
    @BindView(R.id.et_tencent_mapview_baitang_title)
    EditText etTencentMapViewBaiTangTitle;
    @BindView(R.id.tv_tencent_mapview_baitang_content)
    TextView tvTencentMapViewBaiTangContent;
    @BindView(R.id.et_tencent_mapview_baitang_content)
    EditText etTencentMapViewBaiTangContent;
    @BindView(R.id.tv_tencent_mapview_baitang_name)
    TextView tvTencentMapViewBaiTangName;
    @BindView(R.id.cb_tencent_mapview_baitang_issend)
    CheckBox cbTencentMapViewBaiTangIsSend;
    @BindView(R.id.rly_tencent_mapview_baitang_send)
    RelativeLayout rlyTencentMapViewBaiTangSend;
    @OnClick(R.id.rly_tencent_mapview_baitang_send)
    public void rlyTencentMapViewBaiTangSendOnclick(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }else {
//            //同意就拨打
//
//        }
        if(latLng1!=null) {
            sendBooth();
//            Log.d("zzz1111","this is send");
//            PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
//                @Override
//                public void isSuccessful(boolean isSuccessful) {
//                    if(isSuccessful){
//
//                    }else {
//                        sendBooth();
//                    }
//                }
//            }).build(this,this,"5","摆摊","摆设地摊",false);
////            }).build(this,this,"0.01","摆摊","摆设地摊",false);
//            selectTownDialog.show();


        }
    }
    @BindView(R.id.lly_tencent_mapview_baitang_send)
    LinearLayout llyTencentMapViewBaiTangSend;
    @BindView(R.id.rv_tencent_mapview_message)
    RecyclerView rvTencentMapViewMessage;
    private boolean isImgEdit = false;
    private void boothEdit(){
        isImgEdit = true;
        tvTencentMapViewBaiTangTitle.setVisibility(View.GONE);
        tvTencentMapViewBaiTangContent.setVisibility(View.GONE);
        etTencentMapViewBaiTangContent.setVisibility(View.VISIBLE);
        etTencentMapViewBaiTangTitle.setVisibility(View.VISIBLE);
        llyTencentMapViewBaiTangSend.setVisibility(View.VISIBLE);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String nick = xcCacheManager.readCache(xcCacheSaveName.userName);
        ivTencentMapViewBaiTang.setImageResource(R.drawable.add);
        etTencentMapViewBaiTangTitle.setText("");
        etTencentMapViewBaiTangContent.setText("");
        tvTencentMapViewBaiTangName.setText(nick);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
//        Log.d("markeraccount11",markerListBooth.size()+"");
        for(Marker item :markerListBooth){
            MapViewTagBean tag = (MapViewTagBean)item.getTag();
            String markeraccount = tag.getArticleidaccount();
            Log.d("markeraccount11",markeraccount);
            Log.d("markeraccount11",account);
            String lat = tag.getLat();
            String lon = tag.getLon();
//            String title = tag.getTitle();
//            String content = tag.getContent();
//            String img = tag.getImg();
            if(lat == null){
                continue;
            }
            LatLng latLng = null;
//            LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));

            try {
                latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
            }catch (Exception e){
//                Log.d("result11lon",e+"");
                continue;
            }
            if(markeraccount != null && markeraccount.equals(account)){
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng, //中心点坐标，地图目标经纬度
                                (float) 16,  //目标缩放级别
                                0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                0)); //目标旋转角 0~360° (正北方为0)
                tencentMap.animateCamera(cameraSigma);

                if(scaleMarker != null) {
                    if(scaleMarker.getTag() != tag) {
                        Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                        scaleAnimation2.setDuration(100);
                        item.setAnimation(scaleAnimation2);
                        item.startAnimation();
                        scaleMarker = item;
                    }
                }else {
                    Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                    scaleAnimation2.setDuration(100);
                    item.setAnimation(scaleAnimation2);
                    item.startAnimation();
                    scaleMarker = item;
                }

                isImgEdit = false;
                boothDisplay(tag);
//                tvTencentMapViewBaiTangTitle.setVisibility(View.VISIBLE);
//                tvTencentMapViewBaiTangContent.setVisibility(View.VISIBLE);
//                etTencentMapViewBaiTangContent.setVisibility(View.GONE);
//                etTencentMapViewBaiTangTitle.setVisibility(View.GONE);
//                llyTencentMapViewBaiTangSend.setVisibility(View.GONE);
//                rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
//                tvTencentMapViewBaiTangTitle.setText(title);
//                tvTencentMapViewBaiTangContent.setText(content);
//                Glide.with(this).load(img).into(ivTencentMapViewBaiTang);
                return;

            }
        }


    }
    private void boothDisplay(MapViewTagBean tagBean){
        isImgEdit = false;
        etTencentMapViewBaiTangContent.setVisibility(View.GONE);
        etTencentMapViewBaiTangTitle.setVisibility(View.GONE);
        tvTencentMapViewBaiTangTitle.setVisibility(View.VISIBLE);
        tvTencentMapViewBaiTangContent.setVisibility(View.VISIBLE);
        llyTencentMapViewBaiTangSend.setVisibility(View.GONE);
        String boothaccount = tagBean.getArticleidaccount();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account.equals(boothaccount)){
            rlyTencentMapViewBaiTangClose.setVisibility(View.VISIBLE);
        }else {
            rlyTencentMapViewBaiTangClose.setVisibility(View.INVISIBLE);
        }
        boothid = tagBean.getArticleid();
        tvTencentMapViewBaiTangTitle.setText(tagBean.getTitle());
        tvTencentMapViewBaiTangContent.setText(tagBean.getContent());
        tvTencentMapViewBaiTangName.setText(tagBean.getName());
        Glide.with(this).load(tagBean.getImg()).into(ivTencentMapViewBaiTang);
        rlyTencentMapViewBaiTang.setVisibility(View.VISIBLE);
    }
    private ArrayList<String> mSelectImages = new ArrayList<>();
    private void takePhoto(){

        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }


    private boolean isSend = false;

    private void sendBooth(){



        TencentSearch tencentSearch = new TencentSearch(this);
        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(latLng1).getPoi(true)
                .setPoiOptions(new Geo2AddressParam.PoiOptions()
//                        .setRadius(1000).setCategorys("面包")
                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_O2O));
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                Map<String,Object> map = new HashMap<>();
                BoothNetWork boothNetWork = new BoothNetWork();
                BitmapUtils bitmapUtils = new BitmapUtils();
                if(mSelectImages != null && mSelectImages.size() > 0) {
                    Bitmap bitmapHead = bitmapUtils.compressImageFromFile(mSelectImages.get(0));
                    //将图片显示到ImageView中
                    String base64_00Head = bitmapUtils.bitmapConvertBase64(bitmapHead);
                    map.put("img", base64_00Head);
                }
                String title = etTencentMapViewBaiTangTitle.getText().toString();
                String content = etTencentMapViewBaiTangContent.getText().toString();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                if((title == null || title.isEmpty() )&&(content == null || content.isEmpty())){
                    Toast.makeText(getBaseContext(),"发送内容为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                map.put("title",title);
                map.put("content",content);
                map.put("account",account);
                map.put("addr",obj.result.address);

                String lat = latLng1.getLatitude()+"";
                String lon = latLng1.getLongitude()+"";
                map.put("lat",lat);
                map.put("lon",lon);
                map.put("platform","android");
                if(cbTencentMapViewBaiTangIsSend.isChecked()){
                    map.put("issend","1");
                }else {
                    map.put("issend","0");
                }
                boothNetWork.addBoothToNet(map, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Toast.makeText(getBaseContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                        ivTencentMapViewBaiTang.setImageResource(R.drawable.add);
                        etTencentMapViewBaiTangTitle.setText("");
                        etTencentMapViewBaiTangContent.setText("");
                        rlyTencentMapViewBaiTang.setVisibility(View.GONE);
                        if(baseBean.getIssuccess().equals("1")) {
                            isSend = true;
                            getMapArticle(0);

//                    Marker  marker = tencentMap.addMarker(new MarkerOptions().
//                                    position(latLng1).
//                                    title(title)
////                            snippet()
//                                    .tag("")
//                    );
////创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.forumad)));
                        }
                    }
                });
//                StringBuilder sb = new StringBuilder();
//                sb.append("逆地址解析");
//                sb.append("\n地址：" + obj.result.address);
//                sb.append("\npois:"+obj.result.address_component.province+obj.result.address_component.city+obj.result.address_component.district);
//                for (Poi poi : obj.result.pois) {
//                    sb.append("\n\t" + poi.title);
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(poi.latLng)  //标注的位置
//                            .title(poi.title)     //标注的InfoWindow的标题
//                            .snippet(poi.address) //标注的InfoWindow的内容
//                    );
//                }
//                Log.e("test", sb.toString());
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
//                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });

    }
    @BindView(R.id.rv_tencent_mapview_collect)
    RecyclerView rvTencentMapViewCollect;
    @BindView(R.id.rly_tencent_mapview_collect)
    RelativeLayout rlyTencentMapViewCollect;
    MapViewMessageListRVAdapter rvAdapter;
    CollectVillageRVAdapter collectVillageRVAdapter;
    Handler handler=new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String currentlat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
        String currentlon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
        String fistloc = xcCacheManager.readCache(xcCacheSaveName.firstloc);
        if(fistloc == null || fistloc.isEmpty()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //同意就拨打

            }
            xcCacheManager.writeCache(xcCacheSaveName.firstloc,"notfirst");
        }


        /*String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);

        if(village != null && !village.isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(village == null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        */
        tencentMap = mvTencentMapView.getMap();
        tencentMap.setMaxZoomLevel(16);

//        View child = mvTencentMapView.getChildAt(0);
//        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
//            child.setVisibility(View.INVISIBLE);
//        }
        initLocation();
        bindListener();
        if(currentlat != null && !currentlat.isEmpty()) {
            LatLng latLng = null;
            Log.d("ccc11","this is currentlat"+currentlat);
            try {

                latLng = new LatLng(Double.parseDouble(currentlat), Double.parseDouble(currentlon));
            }catch (Exception e){
                Log.d("result11lon",e+"");
//                continue;
            }
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latLng, //中心点坐标，地图目标经纬度
//                            (float) 14.78,  //目标缩放级别
                            14,
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
//            tencentMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(currentlat), Double.parseDouble(currentlon))));
        }else {
            villageLoc("39.95268117","116.3321618","中关村南大街40号社区居委会","北京市海淀区北下关街道办事处中关村南大街40号社区居委会","110108007010");
        }


//        //建立定位
        getMapArticle(0);


//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED){
//            //如果不同意，就去请求权限   参数1：上下文，2：权限，3：请求码
//            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.BLUETOOTH},1);
//        }else {
//            //同意就拨打
//            bindListener();
//        }
        rtvTencentMapViewShiJian.setText(Html.fromHtml("事件<small>(寻人)</small>"));
        rtvTencentMapViewApply.setDegrees(45);
        rtvTencentMapViewBack.setDegrees(135);
        rtvTencentMapViewBaiTang.setDegrees(225);
        rtvTencentMapViewShiJian.setDegrees(-45);

        geocoder();
        autoRunVertical();
        getLocMap();

////
        initRV();
        initCollectVillageRV();
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
    private void initRV(){
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
//            dataList.add("");
//        }
        rvAdapter = new MapViewMessageListRVAdapter(tencentMap);
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//            gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//            //设置布局管理器， 参数gridLayoutManager对象
//            rvSearch.setLayoutManager(gridLayoutManager);
        rvTencentMapViewMessage.setLayoutManager(new LinearLayoutManager(this));
        rvTencentMapViewMessage.setAdapter(rvAdapter);
        rvAdapter.setCallBackListener(new MapViewMessageListRVAdapter.OnFinishClickListener() {
            @Override
            public void isQuery(String lat, String lon) {
                if(lat != null && !lat.isEmpty()){
                    for(Marker item :markerListBooth){
                        LatLng latLng = item.getPosition();
                        String lat1 = latLng.latitude+"";
                        String lon1 = latLng.longitude+"";
                        if(lat1.equals(lat)&& lon1.equals(lon)){
                            MapViewTagBean tag = (MapViewTagBean)item.getTag();
                            boothDisplay(tag);
                            if(scaleMarker != null) {
                                if(scaleMarker.getTag() != tag) {
                                    Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                                    scaleAnimation2.setDuration(100);
                                    item.setAnimation(scaleAnimation2);
                                    item.startAnimation();
                                    scaleMarker = item;
                                }
                            }else {
                                Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                                scaleAnimation2.setDuration(100);
                                item.setAnimation(scaleAnimation2);
                                item.startAnimation();
                                scaleMarker = item;
                            }
                        }
                    }
                }
            }
        });



    }
    private void initCollectVillageRV(){
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
//            dataList.add("");
//        }
        collectVillageRVAdapter = new CollectVillageRVAdapter(this,tencentMap);
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//            gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//            //设置布局管理器， 参数gridLayoutManager对象
//            rvSearch.setLayoutManager(gridLayoutManager);
        rvTencentMapViewCollect.setLayoutManager(new LinearLayoutManager(this));
        rvTencentMapViewCollect.setAdapter(collectVillageRVAdapter);
//        collectVillageRVAdapter.replaceAll(dataList);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
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
                List<GetCollectVillageListBean.ListBean> listBeans = new ArrayList<>();
//                Log.d("zzz111",listBeans.size()+"");

                GetCollectVillageListBean.ListBean bean = new GetCollectVillageListBean.ListBean();
                bean.setVillage("地图");
                bean.setAllname("");
                bean.setGuid("");
                bean.setLat("");
                bean.setLon("");
                listBeans.add(bean);
                listBeans.addAll(getCollectVillageListBean.getList());
                for(GetCollectVillageListBean.ListBean item : listBeans){
                    String village = item.getVillage();
                    String allname = item.getAllname();
                    String lat = item.getLat();
                    String lon = item.getLon();
//                    Log.d("vvv11",allname);
                    TencentSearch tencentSearch = new TencentSearch(getBaseContext());
//        String address = etGeocoder.getText().toString();
                    Address2GeoParam address2GeoParam =
                            new Address2GeoParam(allname);
                    tencentSearch.address2geo(address2GeoParam, new HttpResponseListener<BaseObject>() {

                        @Override
                        public void onSuccess(int arg0, BaseObject arg1) {
                            if (arg1 == null) {
                                return;
                            }
                            Address2GeoResultObject obj = (Address2GeoResultObject) arg1;
                            StringBuilder sb = new StringBuilder();
                            sb.append("地址解析");
                            if (obj.result.latLng != null) {
                                sb.append("\n坐标：" + obj.result.latLng.toString());
                            } else {
                                sb.append("\n无坐标");
                            }
                            String villagedis = obj.result.address_components.district;
                            String villageCity = obj.result.address_components.city;
                            String villageProv = obj.result.address_components.province;
                            String town = allname.substring(0,allname.indexOf(village));
                            MapViewTagBean mapViewTagBean = new MapViewTagBean();
                            mapViewTagBean.setTown(town);
                            mapViewTagBean.setType("village");
                            mapViewTagBean.setVillage(village);
                            mapViewTagBean.setDistrict(villagedis);
                            mapViewTagBean.setCity(villageCity);
                            mapViewTagBean.setProvince(villageProv);
                            mapViewTagBean.setLat(lat);
                            mapViewTagBean.setLon(lon);
                            mapViewTagBean.setLocate(allname);
//                mapViewTagBean.setAreacode(areacode);
                            LatLng latLng = null;
                            if(lat != null && !lat.isEmpty()){
                                latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                                try {
                                    latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                                }catch (Exception e){
//                                    Log.d("result11lon",e+"");
//                                    continue;
                                }
                            }

                            Marker marker = tencentMap.addMarker(new MarkerOptions().
                                            position(latLng).
                                            title(village)
                                            .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)))
//                            snippet()
                                            .tag(mapViewTagBean)
//                            .tag(accountid+","+articleid)
                            );
                            markerListVillage.add(marker);

//                        markerList.add(marker);
//创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.village)));
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.village));
//                            if(marker != null) {
//                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)));
//                            }

                        }

                        @Override
                        public void onFailure(int arg0, String arg1, Throwable arg2) {
                            Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                        }
                    });

                }
                collectVillageRVAdapter.replaceAll(listBeans);

            }
        });


    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            getMessage();
//            Log.d("zzzz111","11");
            handler.postDelayed(this, 5000);
        }
    };
    private void getMessage(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        BoothNetWork boothNetWork = new BoothNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("accountid",account);
        boothNetWork.getBoothMessageFromNet(map, new Observer<TecentMessageListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                Log.d("zzzz11",e+"");
            }

            @Override
            public void onNext(TecentMessageListBean tecentMessageListBean) {
//                Log.d("zzzz11",tecentMessageListBean.getMsg()+"");
                rvAdapter.replaceAll(tecentMessageListBean.getList());
            }
        });
    }
    protected void geocoder() {
        Intent intent = getIntent();

         addr = intent.getStringExtra("addr");
//        Log.d("qqq111","this is addrout");
        if(addr != null && !addr.isEmpty()) {
//            Log.d("qqq111","this is addrin");
            type = 1;
            TencentSearch tencentSearch = new TencentSearch(this);
//        String address = etGeocoder.getText().toString();
            Address2GeoParam address2GeoParam =
                    new Address2GeoParam(addr);
            tencentSearch.address2geo(address2GeoParam, new HttpResponseListener<BaseObject>() {

                @Override
                public void onSuccess(int arg0, BaseObject arg1) {
                    if (arg1 == null) {
                        return;
                    }
                    Address2GeoResultObject obj = (Address2GeoResultObject) arg1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("地址解析");
                    if (obj.result.latLng != null) {
                        sb.append("\n坐标：" + obj.result.latLng.toString());
                    } else {
                        sb.append("\n无坐标");
                    }
//                    Log.e("test", sb.toString());
                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    obj.result.latLng, //中心点坐标，地图目标经纬度
                                    (float) 14.78,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
//                tencentMap.moveCamera(CameraUpdateFactory
//                        .newCameraPosition(new CameraPosition(obj.result.latLng,15f, 0, 0)));
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(obj.result.latLng));
                    MapViewTagBean mapViewTagBean = new MapViewTagBean();
                    mapViewTagBean.setType("orignal");
                    markers = tencentMap.addMarker(new MarkerOptions().
                            position(obj.result.latLng)
                            .title("").
                            snippet("")
                            .tag(mapViewTagBean)
                    );
                }

                @Override
                public void onFailure(int arg0, String arg1, Throwable arg2) {
                    Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                }
            });
        }
    }

    private void getLocMap(){
        Intent intent = getIntent();
         lat = intent.getStringExtra("lat");
        if(lat != null && !lat.isEmpty()) {
            type = 1;
            String lon = intent.getStringExtra("lon");
            String accountid = intent.getStringExtra("accountid");
            String articleid = intent.getStringExtra("articleid");
            String title = intent.getStringExtra("title");
            LatLng latLng = null;
            if (lat != null && lon != null) {
//                latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                try {
                    latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));

                }catch (Exception e){
//                    Log.d("result11lon",e+"");
//                    continue;
                }
            }
            MapViewTagBean mapViewTagBean = new MapViewTagBean();
            mapViewTagBean.setType("article");
            mapViewTagBean.setArticleidaccount(accountid);
            mapViewTagBean.setArticleid(articleid);
            mapViewTagBean.setTitle(title);


            markers = tencentMap.addMarker(new MarkerOptions().
                            position(latLng).
                            title(title)
//                            snippet()
                            .tag(mapViewTagBean)
//                            .tag(accountid + "," + articleid)
            );
//创建图标
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.forumad,55,55)));//标注是有默认图标，这句代码是修改图标
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latLng, //中心点坐标，地图目标经纬度
                            14,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
        }
    }

    private void getMapArticle(int type){
        Map<String,Object> map = new HashMap<>();
        ArticleNetWork articleNetWork = new ArticleNetWork();
        getBooths(type);
        getApplyVillage(type);
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
//                tencentMap.clearAllOverlays();
                markerListArticle.clear();
                Log.d("result11",list.size()+"");
                for(GetMapArticleListBean.ListBean item :list){
                    Log.d("result11","this is item");
                    String lat = item.getLat();
                    String lon = item.getLon();
                    String title = item.getTitle();
                    String articleid = item.getArticleid();
                    String accountid = item.getAccountid();
//                Log.d("result11",lat);
//                Log.d("result11",lon);
//                    Log.d("result11","this is item2");
                    LatLng latLng = null;
                    if(lat != null && lon != null){
//                        Log.d("result11lat",lat+"");
//                        Log.d("result11lon",lon+"");
                        try {
                            latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                        }catch (Exception e){
//                            Log.d("result11lon",e+"");
                            continue;
                        }
//                        Log.d("result11","this is item3");
                    }

//                    Log.d("result11",lat+"");
                    MapViewTagBean mapViewTagBean = new MapViewTagBean();
                    mapViewTagBean.setType("article");
                    mapViewTagBean.setArticleidaccount(accountid);
                    mapViewTagBean.setArticleid(articleid);
                    mapViewTagBean.setLat(lat);
                    mapViewTagBean.setLon(lon);
                    mapViewTagBean.setTitle(title);
                    Marker  marker = tencentMap.addMarker(new MarkerOptions().
                            position(latLng).
                            title(title)
                            .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.redflag, 50, 50)))
//                            snippet()
                            .tag(mapViewTagBean)
//                            .tag(accountid+","+articleid)
                    );
//                    Log.d("result11",title+"");
                    if(marker != null) {
                    if(type == 0) {
                        marker.setVisible(false);
                    }else {
                        marker.setVisible(true);
                    }
                    markerListArticle.add(marker);
//                        Log.d("result11",markerListArticle.size()+"");
//创建图标
//                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.shijian2));//标注是有默认图标，这句代码是修改图标
//                    if(marker != null) {
//                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.redflag, 50, 50)));//标注是有默认图标，这句代码是修改图标
                    }
                }

            }
        });
    }
    private void getApplyVillage(int type){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();

        map.put("accountid",accountid);
        ApplyNetWork applyNetWork = new ApplyNetWork();
//        Log.d("accountid11",accountid);
        applyNetWork.getApplyVillageFromNet(map, new Observer<GetApplyVillageListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetApplyVillageListBean getApplyVillageListBean) {
                List<GetApplyVillageListBean.ListBean> list = getApplyVillageListBean.getList();
//                Log.d("accountid11",list.size()+"");
                for(GetApplyVillageListBean.ListBean item :list){
                    String lat = item.getLat();
                    String lon = item.getLon();
                    String title = item.getAllname();
//                    Log.d("accountid11",title+"");
//                    String articleid = item.ge();
                    String accountid = item.getAccountid();
//                Log.d("result11",lat);
//                Log.d("result11",lon);
                    LatLng latLng = null;
                    if(lat != null && lon != null){
//                        latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        try {
                            latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                        }catch (Exception e){
//                            Log.d("result11lon",e+"");
                            continue;
                        }
                    }

                    MapViewTagBean mapViewTagBean = new MapViewTagBean();
                    mapViewTagBean.setType("applyvillage");
                    mapViewTagBean.setArticleidaccount(accountid);
                    mapViewTagBean.setLat(lat);
                    mapViewTagBean.setLon(lon);
//                    mapViewTagBean.setArticleid(articleid);
                    mapViewTagBean.setTitle(title);
                    Marker  marker = tencentMap.addMarker(new MarkerOptions().
                                    position(latLng).
                                    title(title)
                                    .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.shijian2, 63, 63)))
//                            snippet()
                                    .tag(mapViewTagBean)
//                            .tag(accountid+","+articleid)
                    );
//                    if(type == 0){
//
//                    }else {
//                        marker.setVisible(true);
//                    }
                    if(marker != null) {
//                    marker.setVisible(false);
                    marker.setVisible(true);
                    markerListApplyVillage.add(marker);
//创建图标
//                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.forumad));//标注是有默认图标，这句代码是修改图标
//                    if(marker != null) {
//                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.shijian2, 63, 63)));//标注是有默认图标，这句代码是修改图标
                    }
                }
            }
        });

    }

    LatLng templatLng = null;
    private void getVillage(String province,String city,String district,String town){


//        page = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("page",   "1");
        map.put("limit", "1");
        map.put("province", province);
        map.put("place", district+town);
        SearchNetWork searchNetWork = new SearchNetWork();
        searchNetWork.getVillageFromNet(map, new Observer<VillageListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VillageListBean villageListBean) {
//                Log.d("tencentMap111",villageListBean.getMsg());

                List<VillageListBean.ListBean> listBeans = villageListBean.getList();
//                markerListVillage.clear();
                String templat = "";
                String templon = "";
//                Log.d("tencentMap111",listBeans.size()+"");
                int count = 0;
                if(listBeans != null) {
                    for(VillageListBean.ListBean item:listBeans) {

                        String lat = item.getLat();

                        String lon = item.getLon();
                        if(templat.equals(lat) && templon.equals(lon)){
                            continue;
                        }

                        if(count > 1){
                            break;
                        }
                        count++;
                        templat = lat;
                        templon = lon;
//                        Log.d("tencentMap111",Double.parseDouble(lat)+"");
//                        Log.d("tencentMap111",Double.parseDouble(lon)+"");

                        if(lat != null && lon != null){
//                            latLng = new LatLng(120.8301704, 28.00137883);
//                            templatLng = new LatLng(Double.parseDouble(lon), Double.parseDouble(lat));
                            try {
                                templatLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                            }catch (Exception e){
//                                Log.d("result11lon",e+"");
                                continue;
                            }
                        }
//                        Log.d("tencentMap111",Double.parseDouble(lat)+"");
//                        Log.d("tencentMap111",Double.parseDouble(lon)+"");
                        villagedis = "";
                        villageCity = "";
                        TencentSearch tencentSearch = new TencentSearch(getBaseContext());
                        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
                        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
                        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(templatLng).getPoi(true)
                                .setPoiOptions(new Geo2AddressParam.PoiOptions()
//                                        .setRadius(1000).setCategorys("面包")
                                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_O2O));
                        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

                            @Override
                            public void onSuccess(int arg0, BaseObject arg1) {
                                if (arg1 == null) {
                                    return;
                                }
                                Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                                StringBuilder sb = new StringBuilder();
                                villagedis = obj.result.address_component.district;
                                villageCity = obj.result.address_component.city;
                                villageProv = obj.result.address_component.province;
                                String village = item.getVillage();
                                String allname = item.getLocate();
                                String town = allname.substring(0,allname.indexOf(village));

                                MapViewTagBean mapViewTagBean = new MapViewTagBean();
                                mapViewTagBean.setTown(town);
                                mapViewTagBean.setType("village");
                                mapViewTagBean.setVillage(item.getVillage());
                                mapViewTagBean.setDistrict(villagedis);
                                mapViewTagBean.setProvince(villageProv);
                                mapViewTagBean.setCity(villageCity);
                                mapViewTagBean.setLat(lat);
                                mapViewTagBean.setLon(lon);
                                mapViewTagBean.setLocate(item.getLocate());
                                mapViewTagBean.setAreacode(item.getAreacode());
                                Marker marker = tencentMap.addMarker(new MarkerOptions().
                                                position(templatLng).
                                                title(item.getVillage())
//                            snippet()
                                                .tag(mapViewTagBean)
                                                .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)))
//                            .tag(accountid+","+articleid)
                                );
                                Log.d("vvv22",allname);
                                if(marker == null){
                                    return;
                                }
                                marker.setVisible(false);
                                markerListVillage.add(marker);
//                        markerList.add(marker);
//创建图标
//                                if(marker != null) {
//                                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)));
//                                }
//                                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.headimg));//标注是有默认图标，这句代码是修改图标
//                                String address = obj.result.address;
//                    sb.append("逆地址解析");
//                    sb.append("\n地址：" + obj.result.address);

//                    sb.append("\npois:");
//                for (Poi poi : obj.result.pois) {
//                    sb.append("\n\t" + poi.title);
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(poi.latLng)  //标注的位置
//                            .title(poi.title)     //标注的InfoWindow的标题
//                            .snippet(poi.address) //标注的InfoWindow的内容
//                    );
//                }
//                                Log.e("test", sb.toString());
                            }

                            @Override
                            public void onFailure(int arg0, String arg1, Throwable arg2) {
//                                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                            }
                        });



//                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.forumad1)));//标注是有默认图标，这句代码是修改图标
                    }
                }
            }
        });
    }
    LatLng latLngBooth = null;
    private void getBooths(int type){
        BoothNetWork boothNetWork = new BoothNetWork();
        Map<String,Object> map = new HashMap<>();
        boothNetWork.getBoothListFromNet(map, new Observer<GetBoothListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetBoothListBean getBoothListBean) {
                List<GetBoothListBean.ListBean> listBeans = getBoothListBean.getList();
                markerListBooth.clear();
                if(listBeans != null && listBeans.size() > 0){
                    Log.d("booth111",listBeans.size()+"");
                    for(GetBoothListBean.ListBean item : listBeans){
                        String lat = item.getLat();
                        String lon = item.getLon();
                        String title = item.getTitle();
                        String content = item.getContent();
                        String img = item.getImg();
                        String nick = item.getNick();
                        String accountid = item.getAccountid();
                        String boothid = item.getBoothid();
//                Log.d("result11",lat);
//                Log.d("result11",lon);

                        if(lat != null && lon != null){
//                            latLngBooth = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                            try {
                                latLngBooth = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                            }catch (Exception e){
//                                Log.d("result11lon",e+"");
                                continue;
                            }
                        }
                        MapViewTagBean mapViewTagBean = new MapViewTagBean();
                        mapViewTagBean.setType("booth");
                        mapViewTagBean.setContent(content);
                        mapViewTagBean.setName(nick);
                        mapViewTagBean.setImg(img);
                        mapViewTagBean.setLat(lat);
                        mapViewTagBean.setLon(lon);
                        mapViewTagBean.setArticleidaccount(accountid);
                        mapViewTagBean.setTitle(title);
                        mapViewTagBean.setArticleid(boothid);

//                        Log.d("img111",img);
//                        Glide.with(getBaseContext()).load(img).apply(new RequestOptions().circleCrop().fallback(R.mipmap.booth)).into(new SimpleTarget<Drawable>() {
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
////                                ivTencentMapViewBaiTang.setImageDrawable(resource);
//                                BitmapDrawable bd = (BitmapDrawable) resource;
//                                 Bitmap     bitmap = bd.getBitmap();
//                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
//                            }
//                        });
                            Glide.with(getBaseContext())
                                    .asBitmap().apply(new RequestOptions().circleCrop().fallback(R.mipmap.booth))
                                    .listener(new RequestListener<Bitmap>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                            //图片加载错误，没研究怎么判断异常类型，没有服务器配合研究
                                            Marker  marker = tencentMap.addMarker(new MarkerOptions().
                                                            position(latLngBooth).
                                                            title(title)
//                            snippet()
                                                            .tag(mapViewTagBean)
//                            .tag(accountid+","+articleid)
                                            );
                                            if(marker != null) {
                                                if (isSend) {
                                                    marker.setVisible(true);
                                                } else {
                                                    marker.setVisible(false);
                                                }
                                            }
//                        if(type == 0){
//                            marker.setVisible(false);
//                        }else {
//                            marker.setVisible(true);
//                        }
                                            markerListBooth.add(marker);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                            //resource就是得到的bitmap
                                            Marker  marker = tencentMap.addMarker(new MarkerOptions().
                                                            position(latLngBooth).
                                                            title(title)
//                            snippet()
                                                            .tag(mapViewTagBean)
                                                            .icon(BitmapDescriptorFactory.fromBitmap(resource))
//                            .tag(accountid+","+articleid)
                                            );
                                            if(marker != null) {
                                                if (isSend) {
                                                    marker.setVisible(true);
                                                } else {
                                                    marker.setVisible(false);
                                                }
                                            }
//                        if(type == 0){
//                            marker.setVisible(false);
//                        }else {
//                            marker.setVisible(true);
//                        }
                                                markerListBooth.add(marker);
//                                            if (marker != null) {
//                                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
//                                            }
                                            return false;
                                        }

                                    })
                                    .load(img)
                                    .preload((int) 46.6, (int) 46.6);//设置长宽，原图就去掉参数
                        Log.d("booth111",markerListBooth.size()+"");
//创建图标
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.booth));//标注是有默认图标，这句代码是修改图标
//                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.booth,55,55)));//标注是有默认图标，这句代码是修改图标
                    }
                }
            }
        });
    }
    public  final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private String villagedis = "";
    private String villageCity = "";
    private String villageProv = "";

    private boolean isVillageFirst = true;
    private void villageLoc(String lat,String lon,String village,String allname,String areacode){
        templatLng = null;
        if (lat != null && lon != null) {
//                        templatLng = new LatLng(Double.parseDouble(lon), Double.parseDouble(lat));
            try {
                templatLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
            }catch (Exception e){
//                Log.d("result11lon",e+"");
//                            continue;
            }
        }
        Log.d("v121",""+lat);
        villagedis = "";
        villageCity = "";
        villageProv = "";
        Log.d("ccc11","this is loc"+allname);
//                    getMapArticle(0);
        TencentSearch tencentSearch = new TencentSearch(this);
//        String address = etGeocoder.getText().toString();
        Address2GeoParam address2GeoParam =
                new Address2GeoParam(allname);
        tencentSearch.address2geo(address2GeoParam, new HttpResponseListener<BaseObject>() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {

                if (arg1 == null) {

                    return;
                }
                Address2GeoResultObject obj = (Address2GeoResultObject) arg1;
                StringBuilder sb = new StringBuilder();
                sb.append("地址解析");
                if (obj.result.latLng != null) {
                    sb.append("\n坐标：" + obj.result.latLng.toString());
                } else {
                    sb.append("\n无坐标");
                }

                villagedis = obj.result.address_components.district;
                villageCity = obj.result.address_components.city;
                villageProv = obj.result.address_components.province;
                String town = allname.substring(0,allname.indexOf(village));

                MapViewTagBean mapViewTagBean = new MapViewTagBean();
                mapViewTagBean.setTown(town);
                mapViewTagBean.setType("village");
                mapViewTagBean.setVillage(village);
                mapViewTagBean.setDistrict(villagedis);
                mapViewTagBean.setCity(villageCity);
                mapViewTagBean.setProvince(villageProv);
                mapViewTagBean.setLat(lat);
                mapViewTagBean.setLon(lon);
                mapViewTagBean.setLocate(allname);
                mapViewTagBean.setAreacode(areacode);
                Marker marker = tencentMap.addMarker(new MarkerOptions().
                                position(templatLng).
                                title(village)
//                            snippet()
                                .tag(mapViewTagBean)
                                .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)))
//                            .tag(accountid+","+articleid)
                );
                markerListVillage.add(marker);
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                String firstzhongguancun = xcCacheManager.readCache(xcCacheSaveName.firstzhongguancun);

                if(firstzhongguancun == null || firstzhongguancun.isEmpty()) {
                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    templatLng, //中心点坐标，地图目标经纬度
                                    (float) 10.82,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
                    xcCacheManager.writeCache(xcCacheSaveName.firstzhongguancun,"zz");
                }else {
                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    templatLng, //中心点坐标，地图目标经纬度
                                    14,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
                }

//                        markerList.add(marker);
//创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.village)));
//                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.village));
//                            if(marker != null) {
//                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)));
//                            }
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test111", "error code:" + arg0 + ", msg:" + arg1);
                Log.d("v121","this is null22");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SEARCH_FOR_RESULT: {
                    String lat = data.getExtras().getString("lat");
                    String lon = data.getExtras().getString("lon");
                    String village = data.getExtras().getString("village");
                    String allname = data.getExtras().getString("allname");
                    String areacode = data.getExtras().getString("areacode");
//                Log.d("result11",lat);
                Log.d("result11",lat+","+lon+","+village+","+allname+","+areacode);
                    villageLoc(lat,lon,village,allname,areacode);










                    /*
                    TencentSearch tencentSearch = new TencentSearch(getBaseContext());
                    //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
                    //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
                    Geo2AddressParam geo2AddressParam = new Geo2AddressParam(templatLng).getPoi(true)
                            .setPoiOptions(new Geo2AddressParam.PoiOptions()
                                    .setRadius(1000).setCategorys("面包")
                                    .setPolicy(Geo2AddressParam.PoiOptions.POLICY_O2O));
                    tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

                        @Override
                        public void onSuccess(int arg0, BaseObject arg1) {
                            if (arg1 == null) {
                                return;
                            }
                            Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                            StringBuilder sb = new StringBuilder();
                            villagedis = obj.result.address_component.district;
                            villageCity = obj.result.address_component.city;
                            villageProv = obj.result.address_component.province;
                            String town = allname.substring(0,allname.indexOf(village));

                            MapViewTagBean mapViewTagBean = new MapViewTagBean();
                            mapViewTagBean.setTown(town);
                            mapViewTagBean.setType("village");
                            mapViewTagBean.setVillage(village);
                            mapViewTagBean.setDistrict(villagedis);
                            mapViewTagBean.setCity(villageCity);
                            mapViewTagBean.setProvince(villageProv);
                            mapViewTagBean.setLat(lat);
                            mapViewTagBean.setLon(lon);
                            mapViewTagBean.setLocate(allname);
                            mapViewTagBean.setAreacode(areacode);
                            Marker marker = tencentMap.addMarker(new MarkerOptions().
                                            position(templatLng).
                                            title(village)
//                            snippet()
                                            .tag(mapViewTagBean)
//                            .tag(accountid+","+articleid)
                            );
                            markerListVillage.add(marker);
                            CameraUpdate cameraSigma =
                                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                            templatLng, //中心点坐标，地图目标经纬度
                                            14,  //目标缩放级别
                                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                            0)); //目标旋转角 0~360° (正北方为0)
                            tencentMap.animateCamera(cameraSigma);
//                        markerList.add(marker);
//创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.village)));
//                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.village));
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg,(int)55,(int)55)));
//                                String address = obj.result.address;
//                    sb.append("逆地址解析");
//                    sb.append("\n地址：" + obj.result.address);

//                    sb.append("\npois:");
//                for (Poi poi : obj.result.pois) {
//                    sb.append("\n\t" + poi.title);
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(poi.latLng)  //标注的位置
//                            .title(poi.title)     //标注的InfoWindow的标题
//                            .snippet(poi.address) //标注的InfoWindow的内容
//                    );
//                }
                            Log.e("test", sb.toString());
                        }

                        @Override
                        public void onFailure(int arg0, String arg1, Throwable arg2) {
                            Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                        }
                    });*/


//                    Location location = new Location("");

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
//                    location.setLatitude(Double.parseDouble(lon));
//                    location.setLongitude(Double.parseDouble(lat));
//                location.setAccuracy(arg0.getAccuracy());
//                    locationChangedListener.onLocationChanged(location);

//                myLocation = tencentMap.addMarker(new MarkerOptions().
//                        position(latLng).draggable(true)
//                        .icon(com.tencent.mapsdk.raster.model.BitmapDescriptorFactory.fromResource(R.mipmap.navigation)).
//                                anchor(0.5f, 0.5f))
//                ;
//                tencentMap.animateTo(latLng/*, 4000, callback*/);
//                mTvShow.setText(data.getExtras().getString("second") + "requestCode:" + requestCode + "resultCode:" + resultCode);
                }

                case  MAP_POST_ARTICLE: {
                    getMapArticle(1);
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
                case SELECT_IMAGE_REQUEST:{
                    List<String> imagePaths;
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImages.clear();
                    mSelectImages.addAll(imagePaths);
                    Glide.with(this)
                            .load(mSelectImages.get(0))
                            .into(ivTencentMapViewBaiTang);
                }
            }

        }
    }
    /**
     * 逆地理编码
     */
    String province1 ;
    protected void streetLoc(LatLng latLng) {
//        String str = etRegeocoder.getText().toString().trim();
//        LatLng latLng = str2Coordinate(this, str);
        if (latLng == null) {
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(latLng).getPoi(true)
                .setPoiOptions(new Geo2AddressParam.PoiOptions()
//                        .setRadius(1000).setCategorys("面包")
                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_O2O));
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                StringBuilder sb = new StringBuilder();
                sb.append("逆地址解析");
                sb.append("\n地址：" + obj.result.address);
                sb.append("\npois:"+obj.result.address_component.province+obj.result.address_component.city+obj.result.address_component.district);
//                provinceMap.put()
                 province1 = provinceMap.get(obj.result.address_component.province+obj.result.address_component.city+obj.result.address_component.district);
                if(province1 == null) {
                    provinceMap.put(obj.result.address_component.province+obj.result.address_component.city+obj.result.address_component.district,obj.result.address_component.province+obj.result.address_component.city+obj.result.address_component.district);
                    getStreet(obj.result.address_component.province, obj.result.address_component.city, obj.result.address_component.district);
                }

//                for (Poi poi : obj.result.pois) {
//                    sb.append("\n\t" + poi.title);
//                    tencentMap.addMarker(new MarkerOptions()
//                            .position(poi.latLng)  //标注的位置
//                            .title(poi.title)     //标注的InfoWindow的标题
//                            .snippet(poi.address) //标注的InfoWindow的内容
//                    );
//                }
//                Log.e("test", sb.toString());
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }
    private void getStreet(String province,String city,String district){
        Map<String,Object> map = new HashMap<>();
        StreetNetWork streetNetWork = new StreetNetWork();
        map.put("place",province+city+district);
        streetNetWork.getStreetFromNet(map, new Observer<GetStreetBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetStreetBean getStreetBean) {

                List<GetStreetBean.ListBean> listBeans = getStreetBean.getList();

                if(listBeans != null){
//                    markerListStreet.clear();
                    for(GetStreetBean.ListBean item:listBeans){
                        TencentSearch tencentSearch = new TencentSearch(getBaseContext());
//        String address = etGeocoder.getText().toString();
//                        Log.d("street11",item.getAllname());
                        Address2GeoParam address2GeoParam =
                                new Address2GeoParam(item.getAllname());
                        tencentSearch.address2geo(address2GeoParam, new HttpResponseListener<BaseObject>() {

                            @Override
                            public void onSuccess(int arg0, BaseObject arg1) {
                                if (arg1 == null) {
                                    return;
                                }
                                Address2GeoResultObject obj = (Address2GeoResultObject) arg1;
                                MapViewTagBean mapViewTagBean = new MapViewTagBean();
                                mapViewTagBean.setType("street");
                                mapViewTagBean.setLocate(item.getAllname());
                                Marker marker = tencentMap.addMarker(new MarkerOptions().
                                        position(obj.result.latLng).
                                        title(item.getAllname())
                                        .tag(mapViewTagBean)
                                        .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.street, (int) 56.5, (int)56.5)))
                                );
                                if(marker != null) {
//                                marker.setVisible(false);
                                markerListStreet.add(marker);
//                tencentMap.moveCamera(CameraUpdateFactory
//                        .newCameraPosition(new CameraPosition(obj.result.latLng,15f, 0, 0)));
//                                tencentMap.addMarker(new MarkerOptions()
//                                        .position(obj.result.latLng));
//                                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.street));
//                                if(marker != null) {
//                                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.street, 63, 63)));
                                }
                            }

                            @Override
                            public void onFailure(int arg0, String arg1, Throwable arg2) {
                                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                            }
                        });
                    }
                }


            }
        });
    }

    private Marker scaleMarker = null;
    private LocalBroadcastManager localBroadcastManager;
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
//        bindListener();
        //地图上设置定位数据源
        tencentMap.setLocationSource(this);
        //设置当前位置可见
        tencentMap.setMyLocationEnabled(true);


        /*UiSettings mapUiSettings = tencentMap.getUiSettings();

        *//**
         * 可以控制地图的缩放级别，每次点击改变1个级别，此控件默认打开，
         * 可以通过UiSettings.setZoomControlsEnabled(boolean)接口控制此控件的显示和隐藏。
         *//*
        mapUiSettings.setZoomControlsEnabled(false);

        *//**
         * 此控件可以指示地图的南北方向，默认的视图状态下不显示，只有在地图的偏航角或俯仰角不为0时才会显示，
         *  并且该控件的默认点击事件会将地图视图的俯仰角和偏航角动画到0的位置。
         *  可以通过UiSettings.setCompassEnabled(boolean)接口控制此控件的显示和隐藏。
         *//*
        mapUiSettings.setCompassEnabled(false);

        *//**
         * 当通过TencentMap.setLocationSource(locationSource)设置好地图的定位源后，
         * 点击此按钮可以在地图上标注一个蓝点指示用户的当前位置。
         * 可以通过UiSettings.setMyLocationButtonEnabled()接口设置此控件的显示和隐藏。
         *//*
        mapUiSettings.setMyLocationButtonEnabled(false);

        *//**
         * 旋转手势
         *//*
        mapUiSettings.setRotateGesturesEnabled(false);*/

        //设置定位图标样式
//        setLocMarkerStyle();
//        tencentMap.setMyLocationStyle(locationStyle);
        tencentMap.setOnMapLongClickListener(new TencentMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//                Log.d("xxxx11",x+" "+y);
                latLng1 = latLng;
//                LatLng latLng1 = new LatLng(30.588822,113.951175);
                if(markers != null){
                    markers.remove();
                }
                MapViewTagBean mapViewTagBean = new MapViewTagBean();
                mapViewTagBean.setType("orignal");
                markers = tencentMap.addMarker(new MarkerOptions().
                        position(latLng).
                        title("").
                        snippet("")
                        .tag(mapViewTagBean)
                );
//创建图标
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.drawable.location_icon)));//标注是有默认图标，这句代码是修改图标
//                marker.setIcon(BitmapDescriptorFactory.fromView(llyTenCentMapViewSearch));//标注是有默认图标，这句代码是修改图标

            }
        });
        tencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                Marker marker = tencentMap.
//                tencentMap.clearAllOverlays();
//                reGeocoder(latLng);
                rvTencentMapViewMessage.setVisibility(View.GONE);
                rlyTencentMapViewCollect.setVisibility(View.GONE);
                handler.removeCallbacks(runnable);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View v = getWindow().peekDecorView();
                if (null != v) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                rlyTencentMapViewBaiTang.setVisibility(View.GONE);
                if(markers != null){
                    markers.remove();
                    latLng1 = orginalLng;
                }
                if(scaleMarker != null){
                    Animation scaleAnimation2 = new ScaleAnimation((float)1.36,(float) 1,(float)1.36,(float)1);
                    scaleAnimation2.setDuration(100);
                    scaleMarker.setAnimation(scaleAnimation2);
                    scaleMarker.startAnimation();
                    scaleMarker = null;
//                    scaleMarker.refreshInfoWindow();
//                    scaleMarker.setFastLoad(true);
                }

//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.booth,40,40)));
            }
        });
        //下面是标注支持的事件   监听标注点击事件
        TencentMap.OnMarkerClickListener listener = new TencentMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
//                System.out.println(arg0+"被点击");
                if(System.currentTimeMillis() - clickTime > 2000) {

                    clickTime = System.currentTimeMillis();
                    MapViewTagBean tag = (MapViewTagBean) arg0.getTag();
//                String articleid = arg0.getSnippet();

//                animation.setDuration(2000);//设置动画持续时间
                    latLng1 = arg0.getPosition();
//                arg0.getPosition();
//                arg0.getTag();
                    if (flag) {

                        flag = false;
                    }
                    if (tag == null) {
                        return false;
                    }
                    if (tag.getType().equals("article")) {
//                    String[] tagString = tag.split(",");
//                    String articleAccount = tagString[0];
//                    String articleid = tagString[1];
                        Intent intent = new Intent(getBaseContext(), ForumDetailTwoActivity.class);
                        intent.putExtra("articleAccount", tag.getArticleidaccount());
                        intent.putExtra("articleid", tag.getArticleid());

//            intent.putExtra("title",title);

                        startActivity(intent);
                    }
                    if (tag.getType().equals("village")) {
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        xcCacheManager.writeCache(xcCacheSaveName.forumVillage, tag.getVillage());
                        xcCacheManager.writeCache(xcCacheSaveName.forumVillageAllName, tag.getLocate());
                        xcCacheManager.writeCache(xcCacheSaveName.forumVillageLat, tag.getLat());
                        xcCacheManager.writeCache(xcCacheSaveName.forumVillageLon, tag.getLon());
                        xcCacheManager.writeCache(xcCacheSaveName.forumTown, tag.getTown());
                        xcCacheManager.writeCache(xcCacheSaveName.forumDisc, tag.getDistrict());
                        xcCacheManager.writeCache(xcCacheSaveName.forumCity, tag.getCity());
                        xcCacheManager.writeCache(xcCacheSaveName.forumProv, tag.getProvince());
//                    Log.d("zzzz1111",tag.getTown()+" ,"+tag.getDistrict()+","+tag.getVillage()+","+tag.getLocate()+","+tag.getCity());
                        localBroadcastManager = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent2 = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
//                    intent2.putExtra("account",registerBean.getId());
                        localBroadcastManager.sendBroadcast(intent2); // 发送本地广播
//                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
                        finish();
                    }

                    if (tag.getType().equals("booth")) {
                        boothDisplay(tag);
                        CameraUpdate cameraSigma =
                                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                        arg0.getPosition(), //中心点坐标，地图目标经纬度
                                        16,  //目标缩放级别
                                        0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                        0)); //目标旋转角 0~360° (正北方为0)
                        tencentMap.animateCamera(cameraSigma);
                        if (scaleMarker != null) {
                            if (scaleMarker.getTag() != tag) {
                                Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                                scaleAnimation2.setDuration(100);
                                arg0.setAnimation(scaleAnimation2);
                                arg0.startAnimation();
                                scaleMarker = arg0;
                            }
                        } else {
                            Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.36, 1, (float) 1.36);
                            scaleAnimation2.setDuration(100);
                            arg0.setAnimation(scaleAnimation2);
                            arg0.startAnimation();
                            scaleMarker = arg0;
                        }
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.booth,80,80)));
                    }
                    if (tag.getType().equals("street")) {


                        if (scaleMarker != null) {
                            if (scaleMarker.getTag() != tag) {
                                Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.2, 1, (float) 1.2);
                                scaleAnimation2.setDuration(100);
                                arg0.setAnimation(scaleAnimation2);
                                arg0.startAnimation();
                                scaleMarker = arg0;
                            }
                        } else {
                            Animation scaleAnimation2 = new ScaleAnimation(1, (float) 1.2, 1, (float) 1.2);
                            scaleAnimation2.setDuration(100);
                            arg0.setAnimation(scaleAnimation2);
                            arg0.startAnimation();
                            scaleMarker = arg0;
                        }
                        Intent intent = new Intent(getBaseContext(), MineAllShopActivity.class);
                        String address = tag.getLocate();
                        intent.putExtra("address", address);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
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
//                Log.d("zzz12",""+cameraPosition.target.getLatitude());
            }

            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
//                Log.d("zzz11",""+cameraPosition.target.getLatitude());
                streetLoc(cameraPosition.target);
                float zoom = cameraPosition.zoom;
                if(zoom <= 15.8){
                    flag = true;
                    rlyTencentMapViewBaiTang.setVisibility(View.GONE);
                    for(Marker item:markerListBooth){
                        item.setVisible(false);
                    }
//                    tencentMap.setMyLocationEnabled(true);
                }else {
                    for(Marker item:markerListBooth){
                        item.setVisible(true);
                    }
//                    tencentMap.setMyLocationEnabled(false);
                }
                if(zoom>=10.1 && zoom <=14.73){
                    tencentMap.setMyLocationEnabled(true);
                }else {
                    tencentMap.setMyLocationEnabled(false);
                }
                if(zoom>=12.4 && zoom <=14.73){
                    for(Marker item:markerListApplyVillage){
                        item.setVisible(true);
                    }
                }else {
                    for(Marker item:markerListApplyVillage){
                        item.setVisible(false);
                    }
                }
                if(zoom>=9.14 && zoom <=14.73){
                    for(Marker item:markerListArticle){
                        item.setVisible(true);
                    }
                }else {
                    for(Marker item:markerListArticle){
                        item.setVisible(false);
                    }
                }
                if(zoom>13.40 && zoom <15.72){
                    for(Marker item:markerListStreet){
                        item.setVisible(true);
                    }
                }else {
                    for(Marker item:markerListStreet){
                        item.setVisible(false);
                    }
                }

                if(zoom>9.9 && zoom <15.73){
                    for(Marker item:markerListVillage){
                        item.setVisible(true);
                    }
                }else {
                    for(Marker item:markerListVillage){
                        item.setVisible(false);
                    }
                }
//                Log.d("zoom111",zoom+"");
//                Log.d("zoom111",markerListArticle.size()+"");
//                if(zoom < 16){
//
//                }
//                Log.d("zoomsize11",zoom+"");
            }
        });
    }
    protected void bindListener() {
//        int error = locationManager.requestLocationUpdates(
//                locationRequest,TecentMapViewActivity.this);
        locationManager= TencentLocationManager.getInstance(this);
        int error = locationManager
                .requestLocationUpdates(
                        TencentLocationRequest
                                .create().setInterval(3000)
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
        locationStyle.myLocationType(1);
        //创建图标
//        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getBitMap(R.drawable.location_icon,55,55));
//        locationStyle.icon(bitmapDescriptor);
        //设置定位圆形区域的边框宽度
//        locationStyle.strokeWidth(3);
        //设置圆区域的颜色
//        locationStyle.fillColor(R.color.yellow);
    }

    private Bitmap getBitMap(int resourceId,int iconwidth,int iconheight) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = iconwidth;
        int newHeight = iconheight;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }
    boolean isFirstloc = false;
    /**
     * 腾讯定位SDK位置变化回调
     */
    boolean locOne = false;
    @Override
    public void onLocationChanged(TencentLocation arg0, int i, String s) {
        if (i == TencentLocation.ERROR_OK ) {
            Location location = new Location(arg0.getProvider());
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
            String lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
            String village = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
            String villagedis = xcCacheManager.readCache(xcCacheSaveName.forumDisc);
            String villageCity = xcCacheManager.readCache(xcCacheSaveName.forumCity);
            String villageProv = xcCacheManager.readCache(xcCacheSaveName.forumProv);
            String allname = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
            String town = xcCacheManager.readCache(xcCacheSaveName.forumTown);
            LatLng latLng = null;
            LatLng latLng2 = null;
            if(allname != null && !allname.isEmpty()){
//                latLng =new LatLng(Double.parseDouble(lon),Double.parseDouble(lat));
                try {
                    latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
                }catch (Exception e){
//                    Log.d("result11lon",e+"");
//                    continue;
                }
                MapViewTagBean mapViewTagBean = new MapViewTagBean();
                mapViewTagBean.setTown(town);
                mapViewTagBean.setType("village");
                mapViewTagBean.setVillage(village);
                mapViewTagBean.setDistrict(villagedis);
                mapViewTagBean.setCity(villageCity);
                mapViewTagBean.setProvince(villageProv);
                mapViewTagBean.setLat(lat);
                mapViewTagBean.setLon(lon);
                mapViewTagBean.setLocate(allname);
//                mapViewTagBean.setAreacode(areacode);
                 Marker marker = tencentMap.addMarker(new MarkerOptions().
                                position(latLng).
                                title(village)
//                            snippet()
                                .tag(mapViewTagBean)
                                .icon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)))
//                            .tag(accountid+","+articleid)
                );
                markerListVillage.add(marker);

//                        markerList.add(marker);
//创建图标
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ease_ic_launcher));//标注是有默认图标，这句代码是修改图标
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.village)));
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.village));
//                if(marker != null) {
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.headimg, (int) 63, (int) 63)));
//                }
            }else {
//                Log.d("result11lon","this is loc");
//                latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                try {

                    latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                }catch (Exception e){
//                    Log.d("result11lon",e+"");
//                    continue;
                }
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng, //中心点坐标，地图目标经纬度
                                14,  //目标缩放级别
                                0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                0)); //目标旋转角 0~360° (正北方为0)
                tencentMap.animateCamera(cameraSigma);
               isFirstloc = true;
//                Log.d("result11lon","this is loc");
            }


            xcCacheManager.writeCache(xcCacheSaveName.currentLat,arg0.getLatitude()+"");
            xcCacheManager.writeCache(xcCacheSaveName.currentLon,arg0.getLongitude()+"");
//            latLng2 = new LatLng(arg0.getLatitude(), arg0.getLongitude());

            try {
                latLng2 = new LatLng(arg0.getLatitude(), arg0.getLongitude());
//                latLng = new LatLng(Double.parseDouble(lat.trim()), Double.parseDouble(lon.trim()));
            }catch (Exception e){
//                Log.d("result11lon",e+"");
//                continue;
            }
            orginalLng = latLng2;
            latLng1 = latLng2;
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


//            location.setBearing((float) arg0.getBearing());
//            location.setBearingAccuracyDegrees((float) arg0.get());
//            location.setAccuracy(arg0.getAccuracy());

//            locationChangedListener.onLocationChanged(location);
            if(province == null || province.isEmpty()) {
                province = arg0.getProvince();
            }
            if(city == null || city.isEmpty()) {
                city = arg0.getCity();
            }
            if(district == null || district.isEmpty()) {
                district = arg0.getDistrict();
            }
            if(this.town == null || this.town.isEmpty()) {
                this.town = arg0.getTown();
            }
//            Log.d("province11",province+"");
//            Log.d("province11",city+"");
//            Log.d("province11",district+"");
//            Log.d("province11",this.town+"");
            if(isFirstloc) {
                location.setLatitude(arg0.getLatitude());
                location.setLongitude(arg0.getLongitude());
//                Log.d("zzzaa1", location + "");
//                Log.d("zzzaa1", locationChangedListener + "");
                if (locationChangedListener != null && location != null) {
//                    Log.d("zzzaa1", "this is location");
                    if(locOne) {
                        locationChangedListener.onLocationChanged(location);
                        locOne = false;
                    }
                }
//                getVillage(province, city, district, this.town);
            }
            isFirstloc = true;
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
            if(type == 0) {
                if(fistloc) {
                    initVerticalTitle(city);

//                    tencentMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latLng.getLatitude(), latLng.getLongitude())));
                    fistloc = false;
                }
            }
//
//
//
//            locationChangedListener.onLocationChanged(location);
            if(locationManager != null) {
                locationManager.removeUpdates(this);
            }
//            locationManager.removeUpdates(this);
        }
    }

    private void initVerticalTitle(String city){

        SpreadNetWork spreadNetWork = new SpreadNetWork();
        Map<String,Object> map = new HashMap<>();
//        Log.d("verticalcity111",city);
        if(city!= null && city.indexOf("市")>=0){
            city = city.substring(0,city.indexOf("市"));
        }
        if(city == null || city.isEmpty()){
            city = "全国";
        }
        map.put("place",city);
        Log.d("zzzxx1",city);
        spreadNetWork.getMapVerticalTitleFromNet(map, new Observer<GetMapVerticalTitleListBean>() {
            @Override
            public void onCompleted() {
                Log.d("zzzxx1","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzzxx1",""+e);
            }

            @Override
            public void onNext(GetMapVerticalTitleListBean getMapVerticalTitleListBean) {
                list = new ArrayList<>();
                listBeans = new ArrayList<>();
//                listBeans.clear();
//                list.clear();
                 listBeans.addAll(getMapVerticalTitleListBean.getList());
                 if(listBeans != null) {
                     for (int i = 0; i < listBeans.size(); i++) {
                         String place = listBeans.get(i).getPlace();
                         if(place != null) {
                             if (place.equals("全国")){
                                 list.add(Html.fromHtml("<font color='#FFA200'>" + listBeans.get(i).getTitle() + "</font>" ));
                             }else {
                                 list.add(  listBeans.get(i).getTitle());
                             }
                         }
                     }
                 }
                // 初始化
                aUtil = new AutoVerticalScrollTextViewUtil(avtvTencentMapView, list);
                aUtil.setDuration(5000)// 设置上下滚动事件间隔
                        .start();
                aUtil.setOnMyClickListener(TecentMapViewActivity.this);
            }
        });
    }


    private void autoRunVertical(){
//        list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            if (i == 0 || i == 2 || i == 4) {
//                list.add(Html.fromHtml("<font color='#FF7198'>" + "测试垂直滚动" + "</font>" + i + "特殊"));
//            } else {
//                list.add("测试垂直滚动" + i);
//            }
//        }
//
//        // 初始化
//        aUtil = new AutoVerticalScrollTextViewUtil(avtvTencentMapView, list);
//        aUtil.setDuration(5000)// 设置上下滚动事件间隔
//                .start();
        // 点击事件监听
//        aUtil.setOnMyClickListener(this);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if(mvTencentMapView != null) {
            mvTencentMapView.onStart();
        }
    }

    private boolean isfist = true;
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(mvTencentMapView != null) {
            mvTencentMapView.onResume();
        }
        if(!isfist) {

//            tencentMap.clearAllOverlays();
            for(Marker item:markerListArticle){
//                item.remove();
                item.setVisible(false);
            }
            for(Marker item:markerListApplyVillage){
//                item.remove();
                item.setVisible(false);
            }
            for(Marker item:markerListBooth){
//                item.remove();
                item.setVisible(false);
            }
//            markerListArticle.clear();
//            markerListApplyVillage.clear();
//            markerListBooth.clear();
//            province1 = null;
            getMapArticle(1);
            initCollectVillageRV();
//            bindListener();
//            initLocation();


        }
        isfist =  false;


//        getApplyVillage();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(mvTencentMapView != null) {
            mvTencentMapView.onPause();
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(mvTencentMapView != null) {
            mvTencentMapView.onStop();
        }
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        if(mvTencentMapView != null) {
            mvTencentMapView.onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mvTencentMapView != null) {
            mvTencentMapView.onDestroy();
        }
        if(aUtil != null) {
            aUtil.stop();
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if(locationManager == null || locationRequest == null){
            return;
        }
        int err = locationManager.requestLocationUpdates(locationRequest, this, Looper.myLooper());
//        bindListener();
//        initLocation();
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
        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
//        locationManager = null;
//        locationRequest = null;
//        locationChangedListener=null;
    }

   /* @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            *//**
             * 点击的开始位置
             *//*
            case MotionEvent.ACTION_DOWN:
//                tvTouchShowStart.setText("起始位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            *//**
             * 触屏实时位置
             *//*
            case MotionEvent.ACTION_MOVE:
//                tvTouchShow.setText("实时位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            *//**
             * 离开屏幕的位置
             *//*
            case MotionEvent.ACTION_UP:
//                tvTouchShow.setText("结束位置：(" + event.getX() + "," + event.getY());
                x = event.getX();
                y = event.getY();
                break;
            default:
                break;
        }
        *//**
         *  注意返回值
         *  true：view继续响应Touch操作；
         *  false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
         *//*
        return true;
    }*/

    @Override
    public void onMyClickListener(int position, CharSequence title) {
        if(listBeans.size() <= position){
            return;
        }
        String shopid = listBeans.get(position).getShopid();
        Intent intent = new Intent(this, VisitOthersShopActivity.class);
        intent.putExtra("shopid",shopid);
        startActivity(intent);
//        Toast.makeText(this, list.get(position) + " --- " + title, Toast.LENGTH_SHORT).show();
//        if (aUtil.getIsRunning())
//            // 停止滚动
//            aUtil.stop();
//        else
//            // 开启滚动
//            aUtil.start();
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
