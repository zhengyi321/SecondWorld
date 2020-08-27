package com.et.secondworld.network;



import com.et.secondworld.bean.GetTradeBean;
import com.et.secondworld.bean.TradeOrderListBean;
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

public class TradeOrderNetWork extends BaseNetWork {

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
        @POST("tradeorder/getorderlistpage")
        Observable<TradeOrderListBean> getTradeOrderListBeanFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
//        @FormUrlEncoded
//        @POST("trade/getsorttrade")
//        Observable<GetTradeBean> getSortTradeFromNet(@FieldMap Map<String, Object> paramMap);
//        /*获取广告*/
//        /*获取广告*/
//        @FormUrlEncoded
//        @POST("trade/getsortletter")
//        Observable<GetTradeBean> getSortLetterFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/


    }


    public  void getTradeOrderListBeanFromNet(Map<String, Object> paramMap,Observer<TradeOrderListBean> observer){
        setSubscribe(service.getTradeOrderListBeanFromNet(paramMap),observer);
    }


   /* public  void getSortTradeFromNet(Map<String, Object> paramMap,Observer<GetTradeBean> observer){
        setSubscribe(service.getSortTradeFromNet(paramMap),observer);
    }


    public  void getSortLetterFromNet(Map<String, Object> paramMap,Observer<GetTradeBean> observer){
        setSubscribe(service.getSortLetterFromNet(paramMap),observer);
    }
*/


}
