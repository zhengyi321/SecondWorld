package com.et.secondworld.network;



import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.RegisterBean;
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

public class RegisterLoginNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*注册*/
        @FormUrlEncoded
        @POST("account/register")
        Observable<RegisterBean> registerToNet(@FieldMap Map<String,String> paramMap);
        /*注册*/
        /*登陆*/
        @FormUrlEncoded
        @POST("account/login")
        Observable<RegisterBean> loginToNet(@FieldMap Map<String,Object> paramMap);
        /*登陆*/
        /*登陆*/
        @FormUrlEncoded
        @POST("account/resetpass")
        Observable<BaseBean> resetPassToNet(@FieldMap Map<String,Object> paramMap);
        /*登陆*/
        /*登陆*/
        @FormUrlEncoded
        @POST("account/checklogin")
        Observable<BaseBean> checkLoginFromNet(@FieldMap Map<String,Object> paramMap);
        /*登陆*/
        /*登陆*/
        @FormUrlEncoded
        @POST("account/registerbypass")
        Observable<RegisterBean> registerByPassToNet(@FieldMap Map<String,Object> paramMap);
        /*登陆*/


    }


    public  void registerToNet(Map<String,String> paramMap,Observer<RegisterBean> observer){
        setSubscribe(service.registerToNet(paramMap),observer);
    }
    public  void loginToNet(Map<String,Object> paramMap,Observer<RegisterBean> observer){
        setSubscribe(service.loginToNet(paramMap),observer);
    }
    public  void resetPassToNet(Map<String,Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.resetPassToNet(paramMap),observer);
    }

    public  void checkLoginFromNet(Map<String,Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.checkLoginFromNet(paramMap),observer);
    }

    public  void registerByPassToNet(Map<String,Object> paramMap,Observer<RegisterBean> observer){
        setSubscribe(service.registerByPassToNet(paramMap),observer);
    }



}
