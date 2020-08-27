package com.et.secondworld.network;



import com.et.secondworld.bean.GetCommentAndAt;
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

public class CommentAndAtNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*开通店铺*/
        @FormUrlEncoded
        @POST("commentandat/getcommentandat")
        Observable<GetCommentAndAt> getCommentAndAtFromNet(@FieldMap Map<String, Object> paramMap);
        /*开通店铺*/
        /*开通店铺*/
//        @FormUrlEncoded
//        @POST("shop/getmyshop")
//        Observable<GetMineMyShopDataBean> getMyShopDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*开通店铺*/
        /*关注粉丝*/
//        @FormUrlEncoded
//        @POST("follow/addfans")
//        Observable<AddFansBean> addCancelFansToNet(@FieldMap Map<String, String> paramMap);
//        /*关注粉丝*/
//        /*获取关注*/
//        @FormUrlEncoded
//        @POST("follow/getguanzhu")
//        Observable<GetGuanZhuBean> getGuanZhuFromNet(@FieldMap Map<String, String> paramMap);
//        /*获取关注*/
//        /*取消关注*/
//        @FormUrlEncoded
//        @POST("follow/cancelguanzhu")
//        Observable<CancelGuanZhuBean> cancelGuanZhuToNet(@FieldMap Map<String, String> paramMap);
        /*取消关注*/
//
////        获取轮播图片
//        @FormUrlEncoded
//        @POST("forum/getcircle")
//        Observable<CircleImgBean> getCircleImgFromNet(@FieldMap Map<String, String> paramMap);
////        获取轮播图片
////        获取推荐热帖
//        @FormUrlEncoded
//        @POST("forum/getrecommend")
//        Observable<GetRecommendBean> getRecommendFromNet(@FieldMap Map<String, String> paramMap);
////        获取推荐热帖
////        获取个人动态
//        @FormUrlEncoded
//        @POST("forum/getdongtai")
//        Observable<GetDongTaiBean> getDongTaiFromNet(@FieldMap Map<String, String> paramMap);
////        获取个人动态
////        获取个人动态
//        @FormUrlEncoded
//        @POST("forum/getcollectarticle")
//        Observable<GetCollectBean> getCollectFromNet(@FieldMap Map<String, String> paramMap);
//        获取个人动态

//        获取保存信息
      /*  @FormUrlEncoded
        @POST("account/geteditdata")
        Observable<GetEditDataBean> getEditDataFromNet(@FieldMap Map<String, String> paramMap);*/
//        获取保存信息


    }


    public  void getCommentAndAtFromNet(Map<String,Object> paramMap,Observer<GetCommentAndAt> observer){
        setSubscribe(service.getCommentAndAtFromNet(paramMap),observer);
    }
//    public  void getMyShopDataFromNet(Map<String,Object> paramMap,Observer<GetMineMyShopDataBean> observer){
//        setSubscribe(service.getMyShopDataFromNet(paramMap),observer);
//    }
//    public  void addCancelFansToNet(Map<String,String> paramMap,Observer<AddFansBean> observer){
//        setSubscribe(service.addCancelFansToNet(paramMap),observer);
//    }
//    public  void getGuanZhuFromNet(Map<String,String> paramMap,Observer<GetGuanZhuBean> observer){
//        setSubscribe(service.getGuanZhuFromNet(paramMap),observer);
//    }
//    public  void cancelGuanZhuToNet(Map<String,String> paramMap,Observer<CancelGuanZhuBean> observer){
//        setSubscribe(service.cancelGuanZhuToNet(paramMap),observer);
//    }
//
//    public  void getCircleImgFromNet(Map<String,String> paramMap,Observer<CircleImgBean> observer){
//        setSubscribe(service.getCircleImgFromNet(paramMap),observer);
//    }
//
//    public  void getRecommendFromNet(Map<String,String> paramMap,Observer<GetRecommendBean> observer){
//        setSubscribe(service.getRecommendFromNet(paramMap),observer);
//    }
//
//    public  void getDongTaiFromNet(Map<String,String> paramMap,Observer<GetDongTaiBean> observer){
//        setSubscribe(service.getDongTaiFromNet(paramMap),observer);
//    }
//
//    public  void getCollectFromNet(Map<String,String> paramMap,Observer<GetCollectBean> observer){
//        setSubscribe(service.getCollectFromNet(paramMap),observer);
//    }



}
