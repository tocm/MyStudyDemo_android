package sample.study.andy.andydemos.function.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Andy.chen on 2016/8/12.
 */
public class RxUtils {
    public final static String TAG = RxUtils.class.getSimpleName();

    /**
     * 创建被观察者 observable
     */
    public static void createRxObserver() {
        //创建被观察者，也可以使用just(),from(), 例如: Observable.just(T...);
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //定义事件触发规则
                //通知观察者
                subscriber.onNext("HELLO");
                subscriber.onCompleted();
            }
        });
        //订阅 观察者，然后事件计划开始执行 OnSubscribe call()自动执行
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }
    public static void createRxObserverJust() {
        Observable<Integer> observable = Observable.just(1);
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG,"createRxObserverJust call integer = "+integer);
            }
        });

        final String [] strEvents = {"a","b","c"};
        Observable<String[]> observable1 = (Observable<String[]>) Observable.just(strEvents);//等同于subscriber.onNext("a"),onNext("b"),onNext("c")
        observable1.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String[]>() {
            @Override
            public void call(String[] strings) {
                for (String str : strings) {
                    Log.d(TAG, "createRxObserverJust call string array call next = "+str);
                }
            }
        });

        /**
         * 测试 map(): 事件对象的直接变换，具体功能上面已经介绍过。它是 RxJava 最常用的变换。
         */
        Observable.just("test map new Func1 return value")//输入类型
                /**
                 * Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。另外，和 ActionX 一样， FuncX 也有多个，用于不同参数个数的方法。FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
                 */
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {//参数类型
                        Log.d(TAG, "test map new Func1 return value");
                        return null;//返回类型
                    }
                })
                .subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        });
    }

    /**
     * 1、map和flatMap都是接受一个函数作为参数(Func1)
     2、map函数只有一个参数，参数一般是Func1，Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据
     3、flatMap函数也只有一个参数，也是Func1,Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据，不过这里O为Observable类型
     */
    public static void testRxMap() {
        Log.d(TAG,"testRxFlatMap ");
        getTokenUseMap();
    }

    public static void testRxFlatMap() {
        Log.d(TAG,"testRxFlatMap ");
        getTokenUseFlatMap();
    }

    private static Observer<String> getTokenUseMap() {
        return (Observer<String>) Observable.just("token").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return "abbbbb";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"getToken s= "+s);
            }
        });

    }

    /**
     * 通过flatMap 返回Observer 一次性处理完所有event
     * @return
     */
    private static Observer<String> getTokenUseFlatMap() {
        return (Observer<String>) Observable.just("test flatmap step1 model")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        Log.d(TAG,"testRxFlatMap flatMap getToken s= "+s);
                        return Observable.just(s + "-> next event model");//继续处理下一个EVENT
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG,"testRxFlatMap subscribe getToken s= "+s);
                    }
                });
    }
    public static void testRxOkHttpDownload(String path,final ImageView imageView) {
        //使用HTTP协议获取数据
        /**
         * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
         Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
         Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
         Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
         Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
         另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
         有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。 * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。 * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
         */
        downloadImageOkHttp(path).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<byte[]>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError" +e.getMessage());
            }

            @Override
            public void onNext(byte[] bytes) {
                Log.d(TAG,"onNext");
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        });
    }

    private static Observable<byte[]> downloadImageOkHttp(final String path) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        Observable<byte[]> observable = Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                if(!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(path).build();

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            byte[] result =  response.body().bytes();
                            if (result != null) {
                                subscriber.onNext(result);
                            }

                            subscriber.onCompleted();
                        }
                    });

                }
            }
        });
        return observable;
    }


}
