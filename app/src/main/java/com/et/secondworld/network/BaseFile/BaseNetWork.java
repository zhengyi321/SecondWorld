package com.et.secondworld.network.BaseFile;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by admin on 2017/2/21.
 */

public class BaseNetWork extends RetrofitUtils{

    /**https://github.com/r17171709/Retrofit2Demo
     * 插入观察者
     * @param observable
     * @param observer
     * @param <T>
     */
    public  <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .retryWhen(new
                        //设置重新请求的间隔时间和次数
                        RetryWhenNet(3000,300))
                .subscribe(observer)
                ;

    }
}
