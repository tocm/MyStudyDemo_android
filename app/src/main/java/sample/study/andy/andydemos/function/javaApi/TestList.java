package sample.study.andy.andydemos.function.javaApi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andy.chen on 2016/11/4.
 */

public class TestList {


    /**
     * 访问记录耗时
     * @param list
     * @return
     */
    private  static long compareListReadTime(List<String> list) {
        long start = System.currentTimeMillis();
        for(int i=0; i < 100000;i++) {
            String str = list.get(i);
        }
        return System.currentTimeMillis()-start;
    }

    /**
     * 添加，删除 耗时
     * @param list
     * @return
     */
    private static long compareListAddTime(List<String> list) {
        long start = System.currentTimeMillis();
        for (int i = 0;i<100000;i++) {
            list.add("add_"+i);
        }
        return System.currentTimeMillis() - start;
    }

    /**
     *
     1. 当操作一系列数据，只需要在后面添加数据，不需要在中间或者前面添加，并且频繁随机访问其中记录的，建议先使用ArrayList
     2. 当操作一系列数据，需要在前面，中间添加或者删除数据，并且是顺序访问，建议使用LinkedList;
     * @param strings
     */
    public static void main(String [] strings) {
        // 适合于随机访问记录 get,set
        List<String> list = new ArrayList<String>();
        //适合于添加，删除记录
        List<String> linkedList = new LinkedList<String>();

        System.out.println("ArrayList add spend time :: "+ compareListAddTime(list));
        System.out.println("LinkedList add spend time :: "+ compareListAddTime(linkedList));

        System.out.println("ArrayList read spend time :: "+ compareListReadTime(list));
        System.out.println("LinkedList read spend time :: "+ compareListReadTime(linkedList));


        int n = 0;
        int[] array = new int[2];
        array[0] = 1;
        array[1]=2;
        ITestMode iTestMode = new ChildClass();
        iTestMode.testA();
        iTestMode.testB();
        iTestMode.testC();
        iTestMode.testParams(n,array);
        System.out.println("n= "+n+",array[0] = "+array[0]+",array[1] = "+array[1]);
    }


}
