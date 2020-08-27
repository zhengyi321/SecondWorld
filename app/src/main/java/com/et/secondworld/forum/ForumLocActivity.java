package com.et.secondworld.forum;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.et.secondworld.R;
import com.et.secondworld.forum.adapter.ForumLocRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/27
 **/
public class ForumLocActivity extends AppCompatActivity {


    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    @BindView(R.id.rv_forum_loc)
    RecyclerView rvForumLoc;
    private ForumLocRVAdapter rvAdapter;
    private List<Poi> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_loc);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initRecycleView();
        initData();
    }

    private void initRecycleView(){
        rvAdapter = new ForumLocRVAdapter(dataList);
        rvForumLoc.setLayoutManager(new LinearLayoutManager(this));
        rvForumLoc.setAdapter(rvAdapter);
    }

    private void initData(){
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
        option.setAddrType("all");
        option.setIsNeedLocationPoiList(true);
//可选，是否需要周边POI信息，默认为不需要，即参数为false
//如果开发者需要获得周边POI信息，此处必须为true
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
            if (location == null ) {
                return;
            }
//            Toast.makeText(getBaseContext(),"getDistrict"+location.getAddrStr(),Toast.LENGTH_SHORT).show();
//            for(int i = 0;i<10;i++){
//                Poi p = location.getPoiList().get(i);
//                Log.d("ForumLoc11:",p.getName());
//            }
            /*if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                for (int i = 0; i < location.getPoiList().size(); i++) {
//                    String sb = "";
                    Poi poi = (Poi) location.getPoiList().get(i);
                    Log.d("ForumLoc11:",poi.getName());
                    Log.d("ForumLoc11:",poi.getTags());
                    Log.d("ForumLoc11:",poi.getAddr());
//                    sb.append("poiName:");
//                    sb.append(poi.getName() + ", ");
//                    sb.append("poiTag:");
//                    sb.append(poi.getTags() + "\n");
                }
            }else {
                Log.d("ForumLoc11:","this is null");
            }*/
//            searchNeayBy(location.getLatitude(),location.getLongitude());
            rvAdapter.replaceAll(location.getPoiList());
            mLocClient.stop();
//            Poi poi = location.getPoiList().get(0);
//            String poiName = poi.getName();    //获取POI名称
//            String poiTags = poi.getTag();    //获取POI类型
//            String poiAddr = poi.getAddr();

        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }


    /**
     * 搜索周边地理位置
     * by hankkin at:2015-11-01 22:54:49
     */
    private void searchNeayBy(double lat,double lon) {
        Log.d("ForumLoc11:","lat:"+lat+" lon:"+lon);
        PoiSearch mPoiSearch = PoiSearch.newInstance();
//        PoiNearbySearchOption option = new PoiNearbySearchOption();
//        option.keyword("写字楼");
//        option.sortType(PoiSortType.distance_from_near_to_far);
//        option.location(new LatLng(lat, lon));
//
//
//        option.pageCapacity(20);
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
//        mPoiSearch.searchInCity(new PoiCitySearchOption()
//                .city("温州") //必填
//                .keyword("美食") //必填
//                .pageNum(10));
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(lat, lon))
                .keyword("酒店")
                .radius(5000)
                .pageNum(10));


    }
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if(poiResult != null){
                Log.d("ForumLoc11:",""+poiResult.getTotalPoiNum());
//                rvAdapter.replaceAll(poiResult.getAllPoi());
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            Log.d("ForumLoc11:",""+poiDetailSearchResult.getPoiDetailInfoList().size());
        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };
}
