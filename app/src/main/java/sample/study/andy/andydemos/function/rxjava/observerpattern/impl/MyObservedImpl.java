package sample.study.andy.andydemos.function.rxjava.observerpattern.impl;

import java.util.ArrayList;
import java.util.List;

import sample.study.andy.andydemos.function.rxjava.observerpattern.MyObserved;
import sample.study.andy.andydemos.function.rxjava.observerpattern.MyObserver;

/**
 * Created by Andy.chen on 2016/8/12.
 * 被观察者实现类
 */
public class MyObservedImpl implements MyObserved {

    List<MyObserver> list = new ArrayList<MyObserver>();
    @Override
    public void addObserver(MyObserver observer) {
        list.add(observer);

    }

    @Override
    public void removeObserver(MyObserver observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObserver(String status) {
        /**
         * 当被观察者发生状态变化，将通知所有观察者
         */
        for (MyObserver observer : list) {
            observer.receiveNotify(status);
        }
    }
}
