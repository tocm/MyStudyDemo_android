package sample.study.andy.andydemos.function.eventbus.myEventbus;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import rx.Subscription;

/**
 * Created by Andy.chen on 2016/11/7.
 */

public class SubsciberMethodHunter {
    private Map<EventType,CopyOnWriteArrayList<Subscription>> mSubscriberMap;
    public SubsciberMethodHunter(Map<EventType,CopyOnWriteArrayList<Subscription>> subscriberMap) {
        this.mSubscriberMap = subscriberMap;
    }

    public void findSubscriberMethods(Object subscriber) {
        if (mSubscriberMap == null || subscriber == null) {
            throw new NullPointerException("this mSubscriberMap is null");
        }
        Class<?> clazz = subscriber.getClass();
        while (clazz != null && !isSystemCalss(clazz.getName())) {
            //获取所有方法
            final Method[] methods = clazz.getMethods();
            for(Method method : methods) {
                //根据注解来解析函数
                Subscriber annotation = method.getAnnotation(Subscriber.class);
                if (annotation != null) {
                    //获取方法参数
                    Class<?>[] paramsTypeClass = method.getParameterTypes();
                    System.out.println("paramsTypeClass size = "+paramsTypeClass.length);
                    //只支持一个参数的订阅函数
                    if (paramsTypeClass != null && paramsTypeClass.length == 1) {
                        Class<?> paramType = convertType(paramsTypeClass[0]);

                    }
                }


            }
        }



    }

    private Class<?> convertType(Class<?> eventType) {
        Class<?> returnClass = eventType;
        if (eventType.equals(boolean.class)) {
            returnClass = Boolean.class;
        } else if (eventType.equals(int.class)) {
            returnClass = Integer.class;
        } else if (eventType.equals(float.class)) {
            returnClass = Float.class;
        } else if (eventType.equals(double.class)) {
            returnClass = Double.class;
        }

        return returnClass;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }
}
