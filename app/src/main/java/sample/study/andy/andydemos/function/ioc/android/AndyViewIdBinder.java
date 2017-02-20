package sample.study.andy.andydemos.function.ioc.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Andy.chen on 2016/11/8.
 *
 * 对两个VIEW进行了注解，
 * 这样我们就是不用写findViewById方法
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AndyViewIdBinder {
    int value();

}
