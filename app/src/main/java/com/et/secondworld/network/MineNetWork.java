package com.et.secondworld.network;



import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetEditDataBean;
import com.et.secondworld.bean.GetOtherBean;
import com.et.secondworld.bean.GetPerfectDataBean;
import com.et.secondworld.bean.MineBean;
import com.et.secondworld.bean.SaveEditDataBean;
import com.et.secondworld.bean.UpdatePersonnalNoteBean;
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

public class MineNetWork extends BaseNetWork {

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
        @POST("account/getaccount")
        Observable<MineBean> getMineFromNet(@FieldMap Map<String, String> paramMap);
        /*获取我的*/
        /*获取他人我的*/
        @FormUrlEncoded
        @POST("account/getotheraccount")
        Observable<GetOtherBean> getOtherFromNet(@FieldMap Map<String, Object> paramMap);
        /*获取他人我的*/

        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdata")
        Observable<SaveEditDataBean> saveEditDataToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatahead")
        Observable<SaveEditDataBean> saveEditDataHeadToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatabg")
        Observable<SaveEditDataBean> saveEditDataBgToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatanick")
        Observable<SaveEditDataBean> saveEditDataNickToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatasex")
        Observable<SaveEditDataBean> saveEditDataSexToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatabirth")
        Observable<SaveEditDataBean> saveEditDataBirthToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatalocate")
        Observable<SaveEditDataBean> saveEditDataLocateToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*保存信息*/
        @FormUrlEncoded
        @POST("account/editdatatrade")
        Observable<SaveEditDataBean> saveEditDataTradeToNet(@FieldMap Map<String, String> paramMap);
        /*保存信息*/
        /*获取保存信息*/
        @FormUrlEncoded
        @POST("account/geteditdata")
        Observable<GetEditDataBean> getEditDataFromNet(@FieldMap Map<String, String> paramMap);
        /*获取保存信息*/
        /*获取保存信息*/
        @FormUrlEncoded
        @POST("account/editpersonnalnote")
        Observable<UpdatePersonnalNoteBean> updatePersonnalNoteFromNet(@FieldMap Map<String, String> paramMap);
        /*获取保存信息*/
        /*获取保存信息*/
        @FormUrlEncoded
        @POST("account/perfectdata")
        Observable<BaseBean> perfectDataToNet(@FieldMap Map<String, Object> paramMap);
        /*获取保存信息*/
        /*获取保存信息*/
        @FormUrlEncoded
        @POST("account/getperfectdata")
        Observable<GetPerfectDataBean> getPerfectDataToNet(@FieldMap Map<String, Object> paramMap);
        /*获取保存信息*/


    }


    public  void perfectDataToNet(Map<String,Object> paramMap,Observer<BaseBean> observer){
        setSubscribe(service.perfectDataToNet(paramMap),observer);
    }
    public  void getMineFromNet(Map<String,String> paramMap,Observer<MineBean> observer){
        setSubscribe(service.getMineFromNet(paramMap),observer);
    }
    public  void getOtherFromNet(Map<String,Object> paramMap,Observer<GetOtherBean> observer){
        setSubscribe(service.getOtherFromNet(paramMap),observer);
    }
    public  void saveEditDataToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataToNet(paramMap),observer);
    }
    public  void saveEditDataHeadToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataHeadToNet(paramMap),observer);
    }
    public  void saveEditDataBgToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataBgToNet(paramMap),observer);
    }
    public  void saveEditDataNickToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataNickToNet(paramMap),observer);
    }
    public  void saveEditDataSexToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataSexToNet(paramMap),observer);
    }
    public  void saveEditDataBirthToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataBirthToNet(paramMap),observer);
    }
    public  void saveEditDataLocateToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataLocateToNet(paramMap),observer);
    }
    public  void saveEditDataTradeToNet(Map<String,String> paramMap,Observer<SaveEditDataBean> observer){
        setSubscribe(service.saveEditDataTradeToNet(paramMap),observer);
    }
    public  void getEditDataFromNet(Map<String,String> paramMap,Observer<GetEditDataBean> observer){
        setSubscribe(service.getEditDataFromNet(paramMap),observer);
    }
    public  void updatePersonnalNoteFromNet(Map<String,String> paramMap,Observer<UpdatePersonnalNoteBean> observer){
        setSubscribe(service.updatePersonnalNoteFromNet(paramMap),observer);
    }
    public  void getPerfectDataToNet(Map<String,Object> paramMap,Observer<GetPerfectDataBean> observer){
        setSubscribe(service.getPerfectDataToNet(paramMap),observer);
    }



}
