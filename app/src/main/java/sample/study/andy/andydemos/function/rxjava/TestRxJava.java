package sample.study.andy.andydemos.function.rxjava;

import sample.study.andy.andydemos.function.rxjava.observerpattern.MyObserved;
import sample.study.andy.andydemos.function.rxjava.observerpattern.MyObserver;
import sample.study.andy.andydemos.function.rxjava.observerpattern.SimpleJavaObserved;
import sample.study.andy.andydemos.function.rxjava.observerpattern.SimpleJavaObserver;
import sample.study.andy.andydemos.function.rxjava.observerpattern.impl.MyObservedImpl;
import sample.study.andy.andydemos.function.rxjava.observerpattern.impl.MyObserverImpl;

/**
 * Created by Andy.chen on 2016/8/12.
 */
public class TestRxJava {
    public static void main(String ars[]) throws Exception {
        MyObserver observer1 = new MyObserverImpl();
        MyObserver observer2 = new MyObserverImpl();
        MyObserver observer3 = new MyObserverImpl();
        MyObserved myObserved = new MyObservedImpl();
        myObserved.addObserver(observer1);
        myObserved.addObserver(observer2);
        myObserved.addObserver(observer3);
        myObserved.notifyObserver("notify to observer by MySelf Observer" );

        //user java observer
        //观察者
        SimpleJavaObserver simpleJavaObserver1 = new SimpleJavaObserver();
        SimpleJavaObserver simpleJavaObserver2 = new SimpleJavaObserver();
        //被观察者
        SimpleJavaObserved simpleJavaObserved1 = new SimpleJavaObserved();
        simpleJavaObserved1.addObserver(simpleJavaObserver1);
        simpleJavaObserved1.addObserver(simpleJavaObserver2);

        System.out.println("notifyObservers");
        simpleJavaObserved1.setMsgNotifyObservers("SimpleJavaObserved notify ");



    }

}
