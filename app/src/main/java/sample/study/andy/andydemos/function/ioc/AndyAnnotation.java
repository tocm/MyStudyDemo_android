package sample.study.andy.andydemos.function.ioc;

/**
 * Created by Andy.chen on 2016/11/7.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关键字@interface ,是Java中表示声明一个注解类的关键字。
 使用@interface 表示我们已经继承了java.lang.annotation.Annotation类，这是一个注解的基类接口，就好像Object类，
 还有一条规定：在定义注解时，不能继承其他的注解或接口。
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AndyAnnotation {
    public int id();
    public boolean click() default false;
}
