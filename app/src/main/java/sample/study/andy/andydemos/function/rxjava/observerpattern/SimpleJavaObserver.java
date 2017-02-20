package sample.study.andy.andydemos.function.rxjava.observerpattern;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Andy.chen on 2016/8/12.
 * 观察者
 * 直接使用JAVA 自带API
 */
public class SimpleJavaObserver implements Observer {
    @Override
    public void update(Observable observable, Object data) {
        System.out.print("SimpleJavaObserver update data = " + data );
    }
}
