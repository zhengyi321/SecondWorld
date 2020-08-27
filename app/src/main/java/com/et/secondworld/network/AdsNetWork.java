package com.et.secondworld.network;



import com.et.secondworld.bean.Test;
import com.et.secondworld.network.BaseFile.BaseNetWork;

import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;

/**
 * Created by az on 2017/4/26.
 */

public class AdsNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*获取广告*/
        @GET("/api/weather/city/101030100")
        Observable<Test> getAdsFromNet();
        /*获取广告*/


    }


    public  void getAdsFromNet(Observer<Test> observer){
        setSubscribe(service.getAdsFromNet(),observer);
    }



}
