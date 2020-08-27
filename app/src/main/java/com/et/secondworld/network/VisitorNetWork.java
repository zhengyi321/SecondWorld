package com.et.secondworld.network;



import com.et.secondworld.bean.AddVisitorBean;
import com.et.secondworld.bean.GetVisitorBean;
import com.et.secondworld.network.BaseFile.BaseNetWork;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;

/**
 * Created by az on 2017/4/26.
 */

public class VisitorNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";



        /*添加来访记录*/
        @FormUrlEncoded
        @POST("visitor/addvisitor")
        Observable<AddVisitorBean> addVisitorToNet(@FieldMap Map<String, Object> paramMap);
        /*添加来访记录*/
        /*获取来访记录*/
        @FormUrlEncoded
        @POST("visitor/getvisitor")
        Observable<GetVisitorBean> getVisitorToNet(@FieldMap Map<String, Object> paramMap);
        /*获取来访记录*/



    }


    public  void addVisitorToNet(Map<String,Object> paramMap,Observer<AddVisitorBean> observer){
        setSubscribe(service.addVisitorToNet(paramMap),observer);
    }

    public  void getVisitorToNet(Map<String,Object> paramMap,Observer<GetVisitorBean> observer){
        setSubscribe(service.getVisitorToNet(paramMap),observer);
    }



}
