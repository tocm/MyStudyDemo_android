package sample.study.andy.andydemos.function.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 封装线程处理类
 * Created by Andy.chen on 2016/10/24.
 */
public class ExecutorPoolProcess {
    private ExecutorService executor;
    private final int threadMax = 5;

    private static ExecutorPoolProcess ourInstance = new ExecutorPoolProcess();

    public static ExecutorPoolProcess getInstance() {
        return ourInstance;
    }

    private ExecutorPoolProcess() {
        //创建线程池
        //executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(threadMax);
//        executor = ExecutorServiceFactory.getInstance().createCacheThreadPool(threadMax);
        executor = ExecutorServiceFactory.getInstance().createScheduledThreadPool();
    }

    /**
     * 关闭线程池
     * 注意：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    public Future<?> submit(Callable<?> task) {
        return executor.submit(task);
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
