package sample.study.andy.andydemos.function.rxjava.observerpattern;

/**
 * Created by Andy.chen on 2016/8/12.
 * 观察者
 * 需要接收被观察者的通知
 */
public interface MyObserver {

    public void receiveNotify(String status);

}
