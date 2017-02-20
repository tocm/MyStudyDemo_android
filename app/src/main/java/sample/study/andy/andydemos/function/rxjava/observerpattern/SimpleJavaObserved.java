package sample.study.andy.andydemos.function.rxjava.observerpattern;

import java.util.Observable;

/**
 * Created by Andy.chen on 2016/8/12.
 *  被观察者类
 * 直接使用JAVA自带API
 */
public class SimpleJavaObserved extends Observable {

    /**
     * 构造方法，加入观察者
     *
     */
    public SimpleJavaObserved() {
    }


    public void setMsgNotifyObservers(String status) {
        setChanged();
        notifyObservers();
    }

}
