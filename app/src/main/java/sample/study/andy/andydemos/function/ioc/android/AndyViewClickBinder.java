package sample.study.andy.andydemos.function.ioc.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Andy.chen on 2016/11/9.
 * 绑定VIEW事件 注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AndyViewClickBinder {
    int[] value();//同时支持多个view
}
