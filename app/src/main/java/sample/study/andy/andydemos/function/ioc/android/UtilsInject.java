package sample.study.andy.andydemos.function.ioc.android;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Andy.chen on 2016/11/8.
 *
 * 其实安卓中的注解式绑定控件（也是所谓的IOC控制反转在安卓中的一种应用）其实本质的使用就是Java基础中反射的使用。
 * 值得一提的是，反射执行的效率是很低的,如果不是必要，应当尽量减少反射的使用，因为它会大大拖累你应用的执行效率。
 *
 */

public final class UtilsInject {
    private final static String TAG = UtilsInject.class.getSimpleName();

    public static void initInjectActivityBinder(Activity activity) {
        injectContentView(activity);
        injectViewsId(activity);
        injectViewClickEvent(activity);
    }
    /**
     * 注解 类/接口
     * AndyContentViewBinder 注解的逻辑处理
     * @Target(ElementType.TYPE)  //类，或者接口
     */
    private static void injectContentView(Activity activity) {
        Class<?> clazz = activity.getClass();
        //如果一个注解指定注释类型是存在于此元素上此方法返回true，否则返回false
        if (clazz.isAnnotationPresent(AndyContentViewBinder.class)) {
            // 得到activity这个类的注解
            AndyContentViewBinder andyContentView = clazz.getAnnotation(AndyContentViewBinder.class);//class get
            //获得layoutId 值
            int layoutId = andyContentView.value();
            //方法一： 直接用API
            activity.setContentView(layoutId);

            /*
            //方法二： 用反射调用Activity的 setContentView() 方法,exp: setContentView(R.layout.activity_test_ioc);
            try {
                Method method = clazz.getMethod("setContentView",int.class);
                method.setAccessible(true);// 是否取消对Java 语言访问检查，TRUE -性能有了20倍的提升,默认是false
                method.invoke(activity,layoutId);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            */
        }
    }

    /**
     * 注解字段
     * AndyViewIdBinder 注解逻辑类
     * @Target(ElementType.FIELD)
     *
     * @param activity
     */
    private static void injectViewsId(Activity activity) {
        Class<?> clazz = activity.getClass();
        //遍历当前 Activity 的所有字段
        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(AndyViewIdBinder.class)) {
                //取得注解 Filed
                AndyViewIdBinder andyInjectView = field.getAnnotation(AndyViewIdBinder.class);//filed get
                if (andyInjectView == null) {
                    continue;
                }
                int viewId = andyInjectView.value();
                try {
                    // 反射调用 findViewById 函数，并为字段设置值,exp: TextView textView = this.findViewById(R.id.xxx);
                    Method method = clazz.getMethod("findViewById",int.class);
                    method.setAccessible(true);
                    Object resObject = method.invoke(activity,viewId);
                    field.setAccessible(true);
                    //调用后的返回对象赋值给字段
                    field.set(activity,resObject);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 注解方法
     * @param activity
     */
    private static void injectViewClickEvent(Activity activity) {
        Log.d(TAG,"injectViewClickEvent begin");
        Class clazz = activity.getClass();

        Method[] methods = clazz.getDeclaredMethods();

      for (Method method : methods) {
            Log.d(TAG,"injectViewClickEvent line104");
            //取得定义注解的方法
            if (method.isAnnotationPresent(AndyViewClickBinder.class)) {
                //取得注解定义类对象
                AndyViewClickBinder andyViewClickBinder = method.getAnnotation(AndyViewClickBinder.class);
                int[] viewRds = andyViewClickBinder.value();
                //自定义ClickListener 然后反射调用传进来object 类中的注解方法。
                AndyInjectViewClickListener andyInjectViewClickListener = new AndyInjectViewClickListener(method,activity);
                if (viewRds != null) {
                    for (int id : viewRds) {
                        //注册监听事件
                        View decorView = activity.getWindow().getDecorView();
                        decorView.findViewById(id).setOnClickListener(andyInjectViewClickListener);
                        Log.d(TAG,"injectViewClickEvent setOnClickListener");
                    }
                }
            }
        }

    }

    /**
     * 自定义注册事件监听类
     * 通过反射去调用 传进来对象类中的已注解方法。
     */
    static class AndyInjectViewClickListener implements View.OnClickListener {
        private Method method;
        private Object objParentReceiver;
        private AndyInjectViewClickListener(Method method,Object object) {
            this.method = method;
            this.objParentReceiver = object;
        }
        @Override
        public void onClick(View v) {
            try {
                method.setAccessible(true);
                //反射调用已使用注解的方法
                method.invoke(objParentReceiver,v);
                Log.d(TAG,"AndyInjectViewClickListener invoke");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

}
