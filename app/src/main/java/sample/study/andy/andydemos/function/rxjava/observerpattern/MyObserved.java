package sample.study.andy.andydemos.function.rxjava.observerpattern;

/**
 * Created by Andy.chen on 2016/8/12.
 * 被观察者接口
 * 可以被多个观察者观看
 */
public interface MyObserved {

    /**
     * 加入观察者
     * @param observer
     */
    public void addObserver(MyObserver observer) ;
    /**
     * 移除观察者
     */
    public void removeObserver(MyObserver observer);

    /**
     * 通知观察者
     * @param status
     */
    public void notifyObserver(String status);
}
