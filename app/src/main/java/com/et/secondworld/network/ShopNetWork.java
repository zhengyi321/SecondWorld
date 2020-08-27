package com.et.secondworld.network;



import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetMineMyShopDataBean;
import com.et.secondworld.bean.GetMyShopArticleHistoryListBean;
import com.et.secondworld.bean.GetOtherShopDataBean;
import com.et.secondworld.bean.GetShopEditDataBean;
import com.et.secondworld.bean.GetStreetShopListBean;
import com.et.secondworld.bean.OpenShopBean;
import com.et.secondworld.bean.UpdateShopEditDataBean;
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

public class ShopNetWork extends BaseNetWork {

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
        @POST("shop/openshop")
        Observable<OpenShopBean> openShopToNet(@FieldMap Map<String, Object> paramMap);
        /*开通店铺*/
        /*开通店铺*/
        @FormUrlEncoded
        @POST("shop/getmyshop")
        Observable<GetMineMyShopDataBean> getMyShopDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*开通店铺*/
        /*开通店铺*/
        @FormUrlEncoded
        @POST("shop/getothershop")
        Observable<GetOtherShopDataBean> getOtherShopDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*开通店铺*/
        /*获取店铺资料*/
        @FormUrlEncoded
        @POST("shop/getshopdata")
        Observable<GetShopEditDataBean> getShopEditDataFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdata")
        Observable<UpdateShopEditDataBean> updateShopEditDataToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdatalogo")
        Observable<UpdateShopEditDataBean> updateShopEditDataLogoToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdatabg")
        Observable<UpdateShopEditDataBean> updateShopEditDataBgToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdatatel")
        Observable<UpdateShopEditDataBean> updateShopEditDataTelToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdatabusinesshour")
        Observable<UpdateShopEditDataBean> updateShopEditDataBusinessHourToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/editshopdatachange")
        Observable<BaseBean> changeShopEditDataToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/updateshopfirst")
        Observable<BaseBean> updateShopFirstToNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shoparticle/gethistory")
        Observable<GetMyShopArticleHistoryListBean> getMyShopArticleHistoryFromNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/getstreetshop")
        Observable<GetStreetShopListBean> getStreetShopFromNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
        /*修改店铺资料*/
        @FormUrlEncoded
        @POST("shop/searchshopbyname")
        Observable<GetStreetShopListBean> searchShopNameFromNet(@FieldMap Map<String, Object> paramMap);
        /*修改店铺资料*/
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


    public  void openShopToNet(Map<String,Object> paramMap,Observer<OpenShopBean> observer){
        setSubscribe(service.openShopToNet(paramMap),observer);
    }
    public  void getMyShopDataFromNet(Map<String,Object> paramMap,Observer<GetMineMyShopDataBean> observer){
        setSubscribe(service.getMyShopDataFromNet(paramMap),observer);
    }
    public  void getOtherShopDataFromNet(Map<String,Object> paramMap,Observer<GetOtherShopDataBean> observer){
        setSubscribe(service.getOtherShopDataFromNet(paramMap),observer);
    }
    public  void getShopEditDataFromNet(Map<String,Object> paramMap,Observer<GetShopEditDataBean> observer){
        setSubscribe(service.getShopEditDataFromNet(paramMap),observer);
    }
    public  void updateShopEditDataToNet(Map<String,Object> paramMap,Observer<UpdateShopEditDataBean> observer){
        setSubscribe(service.updateShopEditDataToNet(paramMap),observer);
    }
    public  void updateShopEditDataLogoToNet(Map<String,Object> paramMap,Observer<UpdateShopEditDataBean> observer){
        setSubscribe(service.updateShopEditDataLogoToNet(paramMap),observer);
    }
    public  void updateShopEditDataBgToNet(Map<String,Object> paramMap,Observer<UpdateShopEditDataBean> observer){
        setSubscribe(service.updateShopEditDataBgToNet(paramMap),observer);
    }
    public  void updateShopEditDataTelToNet(Map<String,Object> paramMap,Observer<UpdateShopEditDataBean> observer){
        setSubscribe(service.updateShopEditDataTelToNet(paramMap),observer);
    }
    public  void updateShopEditDataBusinessHourToNet(Map<String,Object> paramMap,Observer<UpdateShopEditDataBean> observer){
        setSubscribe(service.updateShopEditDataBusinessHourToNet(paramMap),observer);
    }
    public  void changeShopEditDataToNet(Map<String,Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.changeShopEditDataToNet(paramMap),observer);
    }
    public  void updateShopFirstToNet(Map<String,Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.updateShopFirstToNet(paramMap),observer);
    }
    public  void getMyShopArticleHistoryFromNet(Map<String,Object> paramMap,Observer<GetMyShopArticleHistoryListBean> observer){
        setSubscribe(service.getMyShopArticleHistoryFromNet(paramMap),observer);
    }
    public  void getStreetShopFromNet(Map<String,Object> paramMap,Observer<GetStreetShopListBean> observer){
        setSubscribe(service.getStreetShopFromNet(paramMap),observer);
    }
    public  void searchShopNameFromNet(Map<String,Object> paramMap,Observer<GetStreetShopListBean> observer){
        setSubscribe(service.searchShopNameFromNet(paramMap),observer);
    }
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
