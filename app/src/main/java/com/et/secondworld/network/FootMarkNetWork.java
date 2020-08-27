package com.et.secondworld.network;



import com.et.secondworld.bean.GetBrowsHistoryBean;
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

public class FootMarkNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*获取我的*/
        @FormUrlEncoded
        @POST("history/getbrowshistory")
        Observable<GetBrowsHistoryBean> getBrowsHistoryFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取我的*/
//        /*保存信息*/
//        @FormUrlEncoded
//        @POST("account/editdata")
//        Observable<SaveEditDataBean> saveEditDataToNet(@FieldMap Map<String, String> paramMap);
//        /*保存信息*/
//        /*获取保存信息*/
//        @FormUrlEncoded
//        @POST("account/geteditdata")
//        Observable<GetEditDataBean> getEditDataFromNet(@FieldMap Map<String, String> paramMap);
//        /*获取保存信息*/


    }


    public  void getBrowsHistoryFromNet(Map<String,Object> paramMap,Observer<GetBrowsHistoryBean> observer){
        setSubscribe(service.getBrowsHistoryFromNet(paramMap),observer);
    }
//    public  void saveEditDataToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
//        setSubscribe(service.saveEditDataToNet(paramMap),observer);
//    }
//    public  void getEditDataFromNet(Map<String,String> paramMap,Observer<GetEditDataBean> observer){
//        setSubscribe(service.getEditDataFromNet(paramMap),observer);
//    }



}
