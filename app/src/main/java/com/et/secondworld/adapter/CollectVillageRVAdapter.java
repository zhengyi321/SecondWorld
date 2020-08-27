package com.et.secondworld.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.MainActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetCollectVillageListBean;
import com.et.secondworld.bean.MapViewTagBean;
import com.et.secondworld.network.CollectVillageNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

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
 * @Date 2020/4/7
 **/
public class CollectVillageRVAdapter extends RecyclerView.Adapter<CollectVillageRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetCollectVillageListBean.ListBean> dataList = new ArrayList<>();
    private int selectpos = -1;
    private boolean flag = true;
    private boolean isMap = true;
    private Activity activity;
    private TencentMap tencentMap;
    public CollectVillageRVAdapter(Activity activity1, TencentMap tencentMap1){
        activity = activity1;
        tencentMap = tencentMap1;
    }


    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCollectVillageListBean.ListBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        for(GetCollectVillageListBean.ListBean item:dataList){
            int isSelect = item.getIsselect();
            if(isSelect == 1){
                isMap = false;
                break;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<GetCollectVillageListBean.ListBean> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectVillageRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tencent_mapview_collect_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;
        int pos = 0;
        @BindView(R.id.tv_tencentmapview_collect_rv_item)
        TextView tvTencentMapViewCollectRVItem;
        @BindView(R.id.iv_tencentmapview_collect_rv_item)
        ImageView ivTencentMapViewCollectRVItem;
        @BindView(R.id.lly_tencentmapview_collect_rv_item)
        LinearLayout llyTencentMapViewCollectRVItem;

        @OnClick(R.id.tv_tencentmapview_collect_rv_item)
        public void llyTencentMapViewCollectRVItemOnclick(){
            selectpos = pos;
            ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);

            String guid = dataList.get(pos).getGuid();
            String village = dataList.get(pos).getVillage();
            String allname = dataList.get(pos).getAllname();
            String lat = dataList.get(pos).getLat();
            String lon = dataList.get(pos).getLon();
            Map<String,Object>  map = new HashMap<>();

            TencentSearch tencentSearch = new TencentSearch(v.getContext());
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
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                    xcCacheManager.writeCache(xcCacheSaveName.forumVillage,village);
                    xcCacheManager.writeCache(xcCacheSaveName.forumVillageAllName,allname);
                    xcCacheManager.writeCache(xcCacheSaveName.forumVillageLat,lat);
                    xcCacheManager.writeCache(xcCacheSaveName.forumVillageLon,lon);
                    xcCacheManager.writeCache(xcCacheSaveName.forumTown,town);
                    xcCacheManager.writeCache(xcCacheSaveName.forumDisc,villagedis);
                    xcCacheManager.writeCache(xcCacheSaveName.forumCity,villageCity);
                    xcCacheManager.writeCache(xcCacheSaveName.forumProv,villageProv);
                    String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
                    map.put("accountid",accountid);
                    map.put("guid",guid);

                    CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
                    collectVillageNetWork.updateCollectVillageSelectToNet(map, new Observer<BaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseBean baseBean) {

                        }
                    });
                    LatLng latLng = null;
                    if(lat != null && !lat.isEmpty()){
                        latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                    }

                    CameraUpdate cameraSigma =
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    latLng, //中心点坐标，地图目标经纬度
                                    14,  //目标缩放级别
                                    0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                    0)); //目标旋转角 0~360° (正北方为0)
                    tencentMap.animateCamera(cameraSigma);
                    notifyDataSetChanged();
                    if(pos != 0){
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
//                activity.finish();
                    }
                }

                @Override
                public void onFailure(int arg0, String arg1, Throwable arg2) {
                    Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
                }
            });
//                if(guid != null && !guid.isEmpty()){


        }

        @OnClick(R.id.iv_tencentmapview_collect_rv_item)
        public void ivTencentMapViewCollectRVItemOnclick(){
            selectpos = pos;
            ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);
//                if(guid != null && !guid.isEmpty()){
            String guid = dataList.get(pos).getGuid();
            String village = dataList.get(pos).getVillage();
            String allname = dataList.get(pos).getAllname();
            String lat = dataList.get(pos).getLat();
            String lon = dataList.get(pos).getLon();
            Map<String,Object>  map = new HashMap<>();
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            xcCacheManager.writeCache(xcCacheSaveName.forumVillage,village);
            xcCacheManager.writeCache(xcCacheSaveName.forumVillageAllName,allname);
            xcCacheManager.writeCache(xcCacheSaveName.forumVillageLat,lat);
            xcCacheManager.writeCache(xcCacheSaveName.forumVillageLon,lon);
            xcCacheManager.writeCache(xcCacheSaveName.currentVillage,allname);
            String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
            map.put("accountid",accountid);
            map.put("guid",guid);

            CollectVillageNetWork collectVillageNetWork = new CollectVillageNetWork();
            collectVillageNetWork.updateCollectVillageSelectToNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {

                }
            });
            notifyDataSetChanged();
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String village = dataList.get(position).getVillage();

            int isSelect = dataList.get(position).getIsselect();
            String lat = dataList.get(position).getLat();
            String lon = dataList.get(position).getLon();

            LatLng latLng = null;
            if(lat != null && !lat.isEmpty()){
                latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            }

            tvTencentMapViewCollectRVItem.setText(village);
//            tvTencentMapViewCollectRVItem.setText("收藏地01");
            if(selectpos == pos){
//                ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);
                ivTencentMapViewCollectRVItem.setBackgroundResource(R.mipmap.markiconselect);
//                }
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng, //中心点坐标，地图目标经纬度
                                14,  //目标缩放级别
                                0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                                0)); //目标旋转角 0~360° (正北方为0)
                tencentMap.animateCamera(cameraSigma);
//                String village = dataList.get(pos).getVillage();
                String allname = dataList.get(pos).getAllname();
//                String lat = dataList.get(pos).getLat();
//                String lon = dataList.get(pos).getLon();

                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                xcCacheManager.writeCache(xcCacheSaveName.forumVillage,village);
                xcCacheManager.writeCache(xcCacheSaveName.forumVillageAllName,allname);
                xcCacheManager.writeCache(xcCacheSaveName.forumVillageLat,lat);
                xcCacheManager.writeCache(xcCacheSaveName.forumVillageLon,lon);
                xcCacheManager.writeCache(xcCacheSaveName.currentVillage,allname);
            }else {
//                ivTencentMapViewCollectRVItem.setVisibility(View.INVISIBLE);
                ivTencentMapViewCollectRVItem.setBackgroundResource(R.mipmap.markicon);
//                if(position == 0){
//                    ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);
//                }

            }
            if(isMap){
                if(position == 0){
                    if(flag) {
//                        ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);
                        ivTencentMapViewCollectRVItem.setBackgroundResource(R.mipmap.markiconselect);
                        flag = false;
                    }
                }
            }else {
                if(isSelect == 1){
                    if(flag){

                        ivTencentMapViewCollectRVItem.setBackgroundResource(R.mipmap.markiconselect);
//                        ivTencentMapViewCollectRVItem.setVisibility(View.VISIBLE);

                        flag = false;
                    }
                }
            }

        }
    }


}
