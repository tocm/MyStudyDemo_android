package sample.study.andy.andydemos.function.eventbus.myEventbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import rx.Subscription;

/**
 * Created by Andy.chen on 2016/11/7.
 */

public final class EventBus {
    private final Map<EventType,CopyOnWriteArrayList<Subscription>> mSubscriberMap = new ConcurrentHashMap<EventType,CopyOnWriteArrayList<Subscription>>();


}
