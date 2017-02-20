package sample.study.andy.andydemos.function.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 测试类
 * Created by Andy.chen on 2016/10/24.
 */

public class ExecutorTest {

    public static void main(String[] string) {
        ExecutorPoolProcess executorPoolProcess = ExecutorPoolProcess.getInstance();

        for (int i = 0;i<20;i++) {
            executorPoolProcess.submit(new Task1("TASK1 THREAD i = "+i));
        }

        for (int j = 0;j<20;j++) {
            executorPoolProcess.submit(new Task2("TASK2 THREAD j = "+j));
        }

        //关闭线程池
        executorPoolProcess.shutdown();
    }

    /**
     * 测试任务线程1
     */
    static class Task1 implements Runnable {
        private String taskName;
        public Task1(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));// 1000毫秒以内的随机数，模拟业务逻辑处理
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-------------这里执行业务逻辑，Runnable TaskName = " + taskName + "-------------");
        }
    }

    static class Task2 implements Callable<String> {
        private String taskName;
        public Task2(String taskName) {
            this.taskName = taskName;
        }
        @Override
        public String call() throws Exception {
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));// 1000毫秒以内的随机数，模拟业务逻辑处理
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-------------这里执行业务逻辑，Callable TaskName = " + taskName + "-------------");
            return ">>>>>>线程返回值，Callable TaskName = " + taskName + "<<<<<<<<<";
        }
    }



}
