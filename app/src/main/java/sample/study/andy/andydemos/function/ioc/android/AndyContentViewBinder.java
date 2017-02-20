package sample.study.andy.andydemos.function.ioc.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Andy.chen on 2016/11/8.
 * Activity 布局文件注解
 */
@Target(ElementType.TYPE)  //类，或者接口
@Retention(RetentionPolicy.RUNTIME)
public @interface AndyContentViewBinder {
    int value();//存放layout id
}
