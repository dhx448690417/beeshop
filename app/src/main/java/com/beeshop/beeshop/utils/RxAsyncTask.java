package com.beeshop.beeshop.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author : cooper
 * Time :  2018/1/20 下午3:58
 * Description : 使用Rxjava实现线程异步操作
 */

public class RxAsyncTask<T> {

//    private static class SingletonHolder{
//        private final static RxAsyncTask instance = new RxAsyncTask();
//    }
//
//    public static RxAsyncTask getInstance() {
//        return SingletonHolder.instance;
//    }


    public void doSome(DoTask doTask,TaskCallBack taskCallBack) {
        Observable observable = Observable.create(doTask);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskCallBack);
    }

    public abstract class DoTask implements Observable.OnSubscribe<T> {

        @Override
        public void call(Subscriber<? super T> subscriber) {
            doInBackGround();
        }

        public abstract void doInBackGround();
    }

    public class TaskCallBack extends Subscriber<T> {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(T t) {

        }
    }

}
