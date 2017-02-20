package sample.study.andy.andydemos.function.javaApi;

/**
 * Created by Andy.chen on 2016/12/13.
 */

public class Parent implements ITestMode {
    @Override
    public void testA() {
        System.out.println("Parent ---testA");
    }

    @Override
    public void testB() {
        System.out.println("Parent ---testB");
    }

    @Override
    public boolean testC() {
        System.out.println("Parent ---testC");
        return true;
    }
    public void testParams(int n,int array[]){
        n = 8;
        array[0] = 3;
        array[1] = 5;

    }
}
