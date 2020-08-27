package com.et.secondworld.network;



import com.et.secondworld.bean.AddArticleBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetArticleidLocBean;
import com.et.secondworld.bean.GetEditArticleBean;
import com.et.secondworld.bean.GetMapArticleListBean;
import com.et.secondworld.bean.GetTempArticleBean;
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

public class ArticleNetWork extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";


        /*添加文章*/
        @FormUrlEncoded
        @POST("article/updatesoluation")
        Observable<BaseBean> updateSoluationsToNet(@FieldMap Map<String, Object> paramMap);
        /*添加文章*/
        /*添加文章*/
        @FormUrlEncoded
        @POST("article/hasfreetime")
        Observable<BaseBean> isHasFreeTimeFromNet(@FieldMap Map<String, Object> paramMap);
        /*添加文章*/
        /*添加文章*/
        @FormUrlEncoded
        @POST("article/normalarticleadd")
        Observable<AddArticleBean> addNormalArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*添加文章*/
        /*添加文章*/
        @FormUrlEncoded
        @POST("map/maparticleadd")
        Observable<AddArticleBean> addMapArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*添加文章*/
        /*获取地图文章*/
        @FormUrlEncoded
        @POST("map/getmaparticle")
        Observable<GetMapArticleListBean> getMapArticleFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取地图文章*/
        /*获取地图文章*/
        @FormUrlEncoded
        @POST("map/getarticleloc")
        Observable<GetArticleidLocBean> getArticleLocFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取地图文章*/
        /*获取地图文章*/
        @FormUrlEncoded
        @POST("map/ismaparticleadd")
        Observable<BaseBean> getArticleStatusFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取地图文章*/
        /*添加草稿*/
        @FormUrlEncoded
        @POST("article/temparticleadd")
        Observable<BaseBean> addTempArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*添加草稿*/
        /*添加文章*/
        @FormUrlEncoded
        @POST("article/normalarticleedit")
        Observable<AddArticleBean> editNormalArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*添加文章*/
        /*添加商店文章*/
        @FormUrlEncoded
        @POST("article/typearticleadd")
        Observable<AddArticleBean> addTypeArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*添加商店文章*/
        /*删除评论*/
        @FormUrlEncoded
        @POST("articlecomment/deletecomment")
        Observable<BaseBean> deleteCommentToNet(@FieldMap Map<String, Object> paramMap);
        /*删除评论*/
        /*删除评论回复*/

        @FormUrlEncoded
        @POST("articlecomment/deletecommentback")
        Observable<BaseBean> deleteCommentBackToNet(@FieldMap Map<String, Object> paramMap);
        /*删除评论回复*/
        /*删除帖子*/
        @FormUrlEncoded
        @POST("article/deletearticle")
        Observable<BaseBean> deleteArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*删除帖子*/
        /*删除帖子*/
        @FormUrlEncoded
        @POST("article/deleteforumspread")
        Observable<BaseBean> deleteForumArticleToNet(@FieldMap Map<String, Object> paramMap);
        /*删除帖子*/
        /*获取编辑帖子资料*/
        @FormUrlEncoded
        @POST("article/geteditarticle")
        Observable<GetEditArticleBean> getEditArticleFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取编辑帖子资料*/
        /*获取草稿*/
        @FormUrlEncoded
        @POST("article/gettemparticle")
        Observable<GetTempArticleBean> getTempArticleFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取草稿*/
//        /*评论点赞或取消*/
//        @FormUrlEncoded
//        @POST("praise/praisecomment")
//        Observable<AddCancelZanBean> addCancelZanCommentToNet(@FieldMap Map<String, Object> paramMap);
//        /*评论点赞或取消*/
//        /*评论回复赞或取消*/
//        @FormUrlEncoded
//        @POST("praise/praisecommentback")
//        Observable<AddCancelZanBean> addCancelZanCommentBackToNet(@FieldMap Map<String, Object> paramMap);
//        /*评论回复赞或取消*/
//
//        /*获取文章赞和收藏数量*/
//        @FormUrlEncoded
//        @POST("praise/praisecollect")
//        Observable<GetPraiseCollectBean> getArticlePraiseCollectFromNet(@FieldMap Map<String, Object> paramMap);
//        /*获取文章赞和收藏数量*/


    }


    public  void updateSoluationsToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.updateSoluationsToNet(paramMap),observer);
    }
    public  void isHasFreeTimeFromNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.isHasFreeTimeFromNet(paramMap),observer);
    }
    public  void addNormalArticleToNet(Map<String, Object> paramMap,Observer<AddArticleBean> observer){
        setSubscribe(service.addNormalArticleToNet(paramMap),observer);
    }
    public  void addMapArticleToNet(Map<String, Object> paramMap,Observer<AddArticleBean> observer){
        setSubscribe(service.addMapArticleToNet(paramMap),observer);
    }
    public  void getMapArticleFromNet(Map<String, Object> paramMap,Observer<GetMapArticleListBean> observer){
        setSubscribe(service.getMapArticleFromNet(paramMap),observer);
    }
    public  void getArticleLocFromNet(Map<String, Object> paramMap,Observer<GetArticleidLocBean> observer){
        setSubscribe(service.getArticleLocFromNet(paramMap),observer);
    }
    public  void getArticleStatusFromNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.getArticleStatusFromNet(paramMap),observer);
    }
    public  void addTempArticleToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.addTempArticleToNet(paramMap),observer);
    }
    public  void addTypeArticleToNet(Map<String, Object> paramMap,Observer<AddArticleBean> observer){
        setSubscribe(service.addTypeArticleToNet(paramMap),observer);
    }
    public  void deleteCommentToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.deleteCommentToNet(paramMap),observer);
    }
    public  void deleteCommentBackToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.deleteCommentBackToNet(paramMap),observer);
    }
    public  void deleteArticleToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.deleteArticleToNet(paramMap),observer);
    }
    public  void deleteForumArticleToNet(Map<String, Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.deleteForumArticleToNet(paramMap),observer);
    }
    public  void getEditArticleFromNet(Map<String, Object> paramMap,Observer<GetEditArticleBean> observer){
        setSubscribe(service.getEditArticleFromNet(paramMap),observer);
    }
    public  void getTempArticleFromNet(Map<String, Object> paramMap,Observer<GetTempArticleBean> observer){
        setSubscribe(service.getTempArticleFromNet(paramMap),observer);
    }
    public  void editNormalArticleToNet(Map<String, Object> paramMap,Observer<AddArticleBean> observer){
        setSubscribe(service.editNormalArticleToNet(paramMap),observer);
    }
//
//    public  void addCancelZanCommentToNet(Map<String, Object> paramMap,Observer<AddCancelZanBean> observer){
//        setSubscribe(service.addCancelZanCommentToNet(paramMap),observer);
//    }
//    public  void addCancelZanCommentBackToNet(Map<String, Object> paramMap,Observer<AddCancelZanBean> observer){
//        setSubscribe(service.addCancelZanCommentBackToNet(paramMap),observer);
//    }
//
//    public  void getArticlePraiseCollectFromNet(Map<String, Object> paramMap,Observer<GetPraiseCollectBean> observer){
//        setSubscribe(service.getArticlePraiseCollectFromNet(paramMap),observer);
//    }



}
