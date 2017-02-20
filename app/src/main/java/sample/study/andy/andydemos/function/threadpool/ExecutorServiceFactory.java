package sample.study.andy.andydemos.function.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池构造工厂
 * Created by Andy.chen on 2016/10/24.
 */
public class ExecutorServiceFactory {
    private ExecutorService mExecutorService;//任务线程池
    private static ExecutorServiceFactory ourInstance = new ExecutorServiceFactory();

    public static ExecutorServiceFactory getInstance() {
        return ourInstance;
    }

    private ExecutorServiceFactory() {
    }

    /**
     *  创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     * @param count
     * @return
     */
    public ExecutorService createFixedThreadPool(int count) {
        mExecutorService = Executors.newFixedThreadPool(count,getThreadFactory());
        return mExecutorService;
    }

    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * @param count
     * @return
     */
    public ExecutorService createCacheThreadPool(int count) {
        mExecutorService = Executors.newCachedThreadPool(getThreadFactory());
        return mExecutorService;
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     * @return
     */
    public ExecutorService createScheduledThreadPool() {
        // CPU个数
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        mExecutorService = Executors.newScheduledThreadPool(availableProcessors * 10,getThreadFactory());
        return  mExecutorService;
    }

    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     * @return
     */
    public ExecutorService createSingleThreadPool() {
        mExecutorService = Executors.newSingleThreadExecutor(getThreadFactory());
        return mExecutorService;
    }


    /**
     * 获取线程池工厂
     *根据需要创建新线程的对象。使用线程工厂就无需再手工编写对 new Thread 的调用了，从而允许应用程序使用特殊的线程子类、属性等等。
     * @return
     */
    private ThreadFactory getThreadFactory() {
        return new ThreadFactory() {
            AtomicInteger sn = new AtomicInteger();
            public Thread newThread(Runnable r) {
                SecurityManager s = System.getSecurityManager();
                ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
                Thread t = new Thread(group, r);
                t.setName("任务线程 - " + sn.incrementAndGet());
                return t;
            }
        };
    }


}
