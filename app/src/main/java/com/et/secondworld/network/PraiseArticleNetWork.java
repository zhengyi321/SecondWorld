package com.et.secondworld.network;



import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetPraiseCollectBean;
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

public class PraiseArticleNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*文章点赞或取消*/
        @FormUrlEncoded
        @POST("praise/praisearticle")
        Observable<AddCancelZanBean> addCancelZanArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*文章点赞或取消*/
        /*文章点赞或取消*/
        @FormUrlEncoded
        @POST("praise/praiseandspreadarticle")
        Observable<BaseBean> praiseAndSpreadArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*文章点赞或取消*/
        /*评论点赞或取消*/
        @FormUrlEncoded
        @POST("praise/praisecomment")
        Observable<AddCancelZanBean> addCancelZanCommentToNet(@FieldMap Map<String, Object> paramMap);
        /*评论点赞或取消*/
        /*评论回复赞或取消*/
        @FormUrlEncoded
        @POST("praise/praisecommentback")
        Observable<AddCancelZanBean> addCancelZanCommentBackToNet(@FieldMap Map<String, Object> paramMap);
        /*评论回复赞或取消*/

        /*获取文章赞和收藏数量*/
        @FormUrlEncoded
        @POST("praise/praisecollect")
        Observable<GetPraiseCollectBean> getArticlePraiseCollectFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取文章赞和收藏数量*/


    }


    public  void addCancelZanArticleToNet(Map<String, Object> paramMap,Observer<AddCancelZanBean> observer){
        setSubscribe(service.addCancelZanArticleToNet(paramMap),observer);
    }

    public  void praiseAndSpreadArticleToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.praiseAndSpreadArticleToNet(paramMap),observer);
    }

    public  void addCancelZanCommentToNet(Map<String, Object> paramMap,Observer<AddCancelZanBean> observer){
        setSubscribe(service.addCancelZanCommentToNet(paramMap),observer);
    }
    public  void addCancelZanCommentBackToNet(Map<String, Object> paramMap,Observer<AddCancelZanBean> observer){
        setSubscribe(service.addCancelZanCommentBackToNet(paramMap),observer);
    }

    public  void getArticlePraiseCollectFromNet(Map<String, Object> paramMap,Observer<GetPraiseCollectBean> observer){
        setSubscribe(service.getArticlePraiseCollectFromNet(paramMap),observer);
    }



}
