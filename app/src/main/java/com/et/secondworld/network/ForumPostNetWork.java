package com.et.secondworld.network;



import com.et.secondworld.bean.AddBrowsHistoryBean;
import com.et.secondworld.bean.AddCommentBackBean;
import com.et.secondworld.bean.AddCommentBackGoodBean;
import com.et.secondworld.bean.AddCommentBean;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.CircleImgBean;
import com.et.secondworld.bean.CollectArticleBean;
import com.et.secondworld.bean.GetCollectBean;
import com.et.secondworld.bean.GetCommentBackBean;
import com.et.secondworld.bean.GetCommentBean;
import com.et.secondworld.bean.GetDongTaiBean;
import com.et.secondworld.bean.GetFindBean;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.bean.GetForumDetailBean;
import com.et.secondworld.bean.GetFourBean;
import com.et.secondworld.bean.GetFirstGuanZhuModuleBean;
import com.et.secondworld.bean.GetRecommendBean;
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

public class ForumPostNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*获取发现*/
        @FormUrlEncoded
        @POST("forum/getfind")
        Observable<GetFindBean> getFindFromNet(@FieldMap Map<String, String> paramMap);
        /*获取发现*/
        /*获取发现*/
        @FormUrlEncoded
        @POST("forum/getguanzhu")
        Observable<GetFirstGuanZhuModuleBean> getGuanZhuFromNet(@FieldMap Map<String, String> paramMap);
        /*获取发现*/
        /*获取发现*/
        @FormUrlEncoded
        @POST("forum/getfour")
        Observable<GetFourBean> getFourFromNet(@FieldMap Map<String, String> paramMap);
        /*获取发现*/
//        获取论坛
        @FormUrlEncoded
        @POST("forum/getforum")
        Observable<GetForumBean> getForumFromNet(@FieldMap Map<String, String> paramMap);
//        获取论坛
//        获取论坛
        @FormUrlEncoded
        @POST("forum/getforumsearch")
        Observable<GetForumBean> getForumSearchFromNet(@FieldMap Map<String, String> paramMap);
//        获取论坛

//        获取轮播图片
        @FormUrlEncoded
        @POST("spread/getbusinesscircle")
        Observable<CircleImgBean> getBusinessCircleImgFromNet(@FieldMap Map<String, Object> paramMap);
//        获取轮播图片
//        获取轮播图片
        @FormUrlEncoded
        @POST("spread/getfindcircle")
        Observable<CircleImgBean> getFindCircleImgFromNet(@FieldMap Map<String, Object> paramMap);
//        获取轮播图片
//        获取轮播图片
        @FormUrlEncoded
        @POST("forum/getcircle")
        Observable<CircleImgBean> getCircleImgFromNet(@FieldMap Map<String, String> paramMap);
//        获取轮播图片
//        获取推荐热帖
        @FormUrlEncoded
        @POST("forum/getrecommend")
        Observable<GetRecommendBean> getRecommendFromNet(@FieldMap Map<String, String> paramMap);
//        获取推荐热帖
//        获取推荐热帖
        @FormUrlEncoded
        @POST("forum/getrecommendmoreelite")
        Observable<GetRecommendBean> getRecommendEliteFromNet(@FieldMap Map<String, String> paramMap);
//        获取推荐热帖
//        获取推荐热帖
        @FormUrlEncoded
        @POST("forum/getrecommendmore")
        Observable<GetRecommendBean> getRecommendMoreFromNet(@FieldMap Map<String, String> paramMap);
//        获取推荐热帖
//        获取个人动态
        @FormUrlEncoded
        @POST("forum/getdongtai")
        Observable<GetDongTaiBean> getDongTaiFromNet(@FieldMap Map<String, String> paramMap);
//        获取个人动态
//        获取个人动态
        @FormUrlEncoded
        @POST("forum/getcollectarticle")
        Observable<GetCollectBean> getCollectFromNet(@FieldMap Map<String, String> paramMap);
//        获取个人动态

//        获取保存信息
        @FormUrlEncoded
        @POST("forum/getarticledetail")
        Observable<GetForumDetailBean> getForumDetailFromNet(@FieldMap Map<String, String> paramMap);
//        获取保存信息
//        获取保存信息
        @FormUrlEncoded
        @POST("forum/getarticledetailthree")
        Observable<GetForumDetailBean> getForumDetailThreeFromNet(@FieldMap Map<String, String> paramMap);
//        获取保存信息
//        获取保存信息
        @FormUrlEncoded
        @POST("forum/getarticledetailone")
        Observable<GetForumDetailBean> getForumDetailOneFromNet(@FieldMap Map<String, String> paramMap);
//        获取保存信息
//        获取评论
        @FormUrlEncoded
        @POST("forum/getarticlecomment")
        Observable<GetCommentBean> getArticleCommentFromNet(@FieldMap Map<String, String> paramMap);
//        获取评论
//        提交评论
        @FormUrlEncoded
        @POST("forum/addarticlecomment")
        Observable<AddCommentBean> addArticleCommentFromNet(@FieldMap Map<String, String> paramMap);
//        提交评论
//        提交评论
        @FormUrlEncoded
        @POST("forum/addarticlecommentgoodbad")
        Observable<AddCancelZanBean> addArticleCommentZanCaiToNet(@FieldMap Map<String, Object> paramMap);
//        提交评论
//        收藏文章
        @FormUrlEncoded
        @POST("collect/collectarticle")
        Observable<CollectArticleBean> addCollectArticleToNet(@FieldMap Map<String, Object> paramMap);
//        收藏文章
//        回复
        @FormUrlEncoded
        @POST("forum/getcommentback")
        Observable<GetCommentBackBean> getCommentBackFromNet(@FieldMap Map<String, Object> paramMap);
//        回复
//        提交回复评论
        @FormUrlEncoded
        @POST("forum/addcommentback")
        Observable<AddCommentBackBean> addCommentBackToNet(@FieldMap Map<String, Object> paramMap);
//        提交回复评论
//        提交回复评论赞
        @FormUrlEncoded
        @POST("forum/addcommentbackgood")
        Observable<AddCommentBackGoodBean> addCommentBackGoodToNet(@FieldMap Map<String, Object> paramMap);
//        提交回复评论赞
//        提交浏览记录
        @FormUrlEncoded
        @POST("history/addbrowshistory")
        Observable<AddBrowsHistoryBean> addBrowsHistoryToNet(@FieldMap Map<String, Object> paramMap);
//        提交浏览记录



    }


    public  void getFindFromNet(Map<String,String> paramMap,Observer<GetFindBean> observer){
        setSubscribe(service.getFindFromNet(paramMap),observer);
    }
    public  void getForumFromNet(Map<String,String> paramMap,Observer<GetForumBean> observer){
        setSubscribe(service.getForumFromNet(paramMap),observer);
    }
    public  void getForumSearchFromNet(Map<String,String> paramMap,Observer<GetForumBean> observer){
        setSubscribe(service.getForumSearchFromNet(paramMap),observer);
    }
    public  void getGuanZhuFromNet(Map<String,String> paramMap,Observer<GetFirstGuanZhuModuleBean> observer){
        setSubscribe(service.getGuanZhuFromNet(paramMap),observer);
    }
    public  void getFourFromNet(Map<String,String> paramMap,Observer<GetFourBean> observer){
        setSubscribe(service.getFourFromNet(paramMap),observer);
    }

    public  void getCircleImgFromNet(Map<String,String> paramMap,Observer<CircleImgBean> observer){
        setSubscribe(service.getCircleImgFromNet(paramMap),observer);
    }
    public  void getBusinessCircleImgFromNet(Map<String,Object> paramMap,Observer<CircleImgBean> observer){
        setSubscribe(service.getBusinessCircleImgFromNet(paramMap),observer);
    }
    public  void getFindCircleImgFromNet(Map<String,Object> paramMap,Observer<CircleImgBean> observer){
        setSubscribe(service.getFindCircleImgFromNet(paramMap),observer);
    }

    public  void getRecommendFromNet(Map<String,String> paramMap,Observer<GetRecommendBean> observer){
        setSubscribe(service.getRecommendFromNet(paramMap),observer);
    }
    public  void getRecommendEliteFromNet(Map<String,String> paramMap,Observer<GetRecommendBean> observer){
        setSubscribe(service.getRecommendEliteFromNet(paramMap),observer);
    }

    public  void getRecommendMoreFromNet(Map<String,String> paramMap,Observer<GetRecommendBean> observer){
        setSubscribe(service.getRecommendMoreFromNet(paramMap),observer);
    }

    public  void getDongTaiFromNet(Map<String,String> paramMap,Observer<GetDongTaiBean> observer){
        setSubscribe(service.getDongTaiFromNet(paramMap),observer);
    }

    public  void getCollectFromNet(Map<String,String> paramMap,Observer<GetCollectBean> observer){
        setSubscribe(service.getCollectFromNet(paramMap),observer);
    }

    public  void getForumDetailFromNet(Map<String,String> paramMap,Observer<GetForumDetailBean> observer){
        setSubscribe(service.getForumDetailFromNet(paramMap),observer);
    }
    public  void getForumDetailThreeFromNet(Map<String,String> paramMap,Observer<GetForumDetailBean> observer){
        setSubscribe(service.getForumDetailThreeFromNet(paramMap),observer);
    }
    public  void getForumDetailOneFromNet(Map<String,String> paramMap,Observer<GetForumDetailBean> observer){
        setSubscribe(service.getForumDetailOneFromNet(paramMap),observer);
    }

    public  void getArticleCommentFromNet(Map<String,String> paramMap,Observer<GetCommentBean> observer){
        setSubscribe(service.getArticleCommentFromNet(paramMap),observer);
    }

    public  void addArticleCommentFromNet(Map<String,String> paramMap,Observer<AddCommentBean> observer){
        setSubscribe(service.addArticleCommentFromNet(paramMap),observer);
    }

    public  void addArticleCommentZanCaiToNet(Map<String,Object> paramMap,Observer<AddCancelZanBean> observer){
        setSubscribe(service.addArticleCommentZanCaiToNet(paramMap),observer);
    }

    public  void addCollectArticleToNet(Map<String,Object> paramMap,Observer<CollectArticleBean> observer){
        setSubscribe(service.addCollectArticleToNet(paramMap),observer);
    }

    public  void getCommentBackFromNet(Map<String,Object> paramMap,Observer<GetCommentBackBean> observer){
        setSubscribe(service.getCommentBackFromNet(paramMap),observer);
    }

    public  void addCommentBackToNet(Map<String,Object> paramMap,Observer<AddCommentBackBean> observer){
        setSubscribe(service.addCommentBackToNet(paramMap),observer);
    }

    public  void addCommentBackGoodToNet(Map<String,Object> paramMap,Observer<AddCommentBackGoodBean> observer){
        setSubscribe(service.addCommentBackGoodToNet(paramMap),observer);
    }
    public  void addBrowsHistoryToNet(Map<String,Object> paramMap,Observer<AddBrowsHistoryBean> observer){
        setSubscribe(service.addBrowsHistoryToNet(paramMap),observer);
    }



}
