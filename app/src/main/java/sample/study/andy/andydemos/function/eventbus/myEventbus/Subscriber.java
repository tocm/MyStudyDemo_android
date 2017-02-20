package sample.study.andy.andydemos.function.eventbus.myEventbus;

import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Andy.chen on 2016/11/7.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscriber {
    String tag() default EventType.DEFAULT_TAG;
    ThreadMode mode() default ThreadMode.MAIN;

}
