package com.et.secondworld.network.BaseFile;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/8/27
 **/

public class RetryWhenNet implements Func1<Observable<? extends Throwable>, Observable<?>> {

    //重试延迟时间
    private int retryDelayTime = 3000;
    //重试次数
    private int retryDelayNum = 300;

    public RetryWhenNet(int retryDelayTime,int retryDelayNum){
        this.retryDelayTime = retryDelayTime;
        this.retryDelayNum = retryDelayNum;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, retryDelayNum), new Func2<Throwable, Integer, Integer>() {
            @Override
            public Integer call(Throwable throwable, Integer integer) {

                //在这里判断错误的类型

                return 1;
            }
        }).flatMap(new Func1<Integer, Observable<?>>() {
            @Override
            public Observable<?> call(Integer integer) {
                return Observable.just(null).delay(retryDelayTime, TimeUnit.MILLISECONDS);
            }
        });
    }
}
