package sample.study.andy.andydemos.function.eventbus;

import android.util.Log;

/**
 * Created by Andy.chen on 2016/11/6.
 * EventBus是一款针对Android优化的发布/订阅事件总线。
 * 主要功能是替代Intent,Handler,BroadCast在Fragment，Activity，Service，线程之间传递消息.优点是开销小，代码更优雅。
 * 以及将发送者和接收者解耦。
 * 下载EventBus的类库
    源码：https://github.com/greenrobot/EventBus

 封装传递事件类
 */

public class EventBusEventTypes {
    private String str;
    public EventBusEventTypes(String str) {
        this.str = str;
    }
    public void showStr() {
        Log.d("TEST","out eventbus type = "+str);
    }

}
