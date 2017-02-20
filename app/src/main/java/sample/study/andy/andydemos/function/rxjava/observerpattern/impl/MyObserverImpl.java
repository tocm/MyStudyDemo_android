package sample.study.andy.andydemos.function.rxjava.observerpattern.impl;

import sample.study.andy.andydemos.function.rxjava.observerpattern.MyObserver;

/**
 * Created by Andy.chen on 2016/8/12.
 * 观察者实现类
 */
public class MyObserverImpl implements MyObserver {
    @Override
    public void receiveNotify(String status) {

        System.out.println("receiveNotify status = "+status);
    }
}
