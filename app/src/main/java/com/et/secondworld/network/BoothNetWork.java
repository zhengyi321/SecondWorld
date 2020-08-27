package com.et.secondworld.network;



import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetBoothListBean;
import com.et.secondworld.bean.TecentMessageListBean;
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

public class BoothNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*获取广告*/
        @FormUrlEncoded
        @POST("booth/boothadd")
        Observable<BaseBean> addBoothToNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
        @FormUrlEncoded
        @POST("booth/getboothmessage")
        Observable<TecentMessageListBean> getBoothMessageFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
        @FormUrlEncoded
        @POST("booth/getbooth")
        Observable<GetBoothListBean> getBoothListFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
        @FormUrlEncoded
        @POST("booth/deletebooth")
        Observable<BaseBean> deleteBoothToNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/


    }


    public  void addBoothToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.addBoothToNet(paramMap),observer);
    }
    public  void getBoothMessageFromNet(Map<String, Object> paramMap,Observer<TecentMessageListBean> observer){
        setSubscribe(service.getBoothMessageFromNet(paramMap),observer);
    }

    public  void getBoothListFromNet(Map<String, Object> paramMap,Observer<GetBoothListBean> observer){
        setSubscribe(service.getBoothListFromNet(paramMap),observer);
    }

    public  void deleteBoothToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.deleteBoothToNet(paramMap),observer);
    }



}
