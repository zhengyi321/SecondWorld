package com.et.secondworld.network;



import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetApplyVillageListBean;
import com.et.secondworld.bean.GetPayBean;
import com.et.secondworld.bean.VXPayBean;
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

public class PayNetWork extends BaseNetWork {

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
        @POST("alipay/paysubmit")
        Observable<GetPayBean> getZhiFuBaoPayDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
        @FormUrlEncoded
        @POST("vxpay/paysubmit")
        Observable<VXPayBean> getVXPayDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取广告*/
        /*获取广告*/
//        @FormUrlEncoded
//        @POST("apply/getapplyvillage")
//        Observable<GetApplyVillageListBean> getApplyVillageFromNet(@FieldMap Map<String, Object> paramMap);
//        /*获取广告*/
//        /*获取广告*/
//        @FormUrlEncoded
//        @POST("manage/applyformanage")
//        Observable<BaseBean> applyManageToNet(@FieldMap Map<String, Object> paramMap);
//        /*获取广告*/
//        /*获取广告*/
//        @FormUrlEncoded
//        @POST("manage/getapplyformanage")
//        Observable<BaseBean> getApplyForManageFromNet(@FieldMap Map<String, Object> paramMap);
//        /*获取广告*/
//        /*获取广告*/
//        @FormUrlEncoded
//        @POST("manage/getquerymanage")
//        Observable<BaseBean> getQueryManageFromNet(@FieldMap Map<String, Object> paramMap);
//        /*获取广告*/


    }


    public  void getZhiFuBaoPayDataFromNet(Map<String, Object> paramMap,Observer<GetPayBean> observer){
        setSubscribe(service.getZhiFuBaoPayDataFromNet(paramMap),observer);
    }


    public  void getVXPayDataFromNet(Map<String, Object> paramMap,Observer<VXPayBean> observer){
        setSubscribe(service.getVXPayDataFromNet(paramMap),observer);
    }

//    public  void getApplyVillageFromNet(Map<String, Object> paramMap,Observer<GetApplyVillageListBean> observer){
//        setSubscribe(service.getApplyVillageFromNet(paramMap),observer);
//    }
//
//    public  void applyManageToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
//        setSubscribe(service.applyManageToNet(paramMap),observer);
//    }
//    public  void getApplyForManageFromNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
//        setSubscribe(service.getApplyForManageFromNet(paramMap),observer);
//    }
//
//    public  void getQueryManageFromNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
//        setSubscribe(service.getQueryManageFromNet(paramMap),observer);
//    }



}
